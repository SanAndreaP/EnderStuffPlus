package sanandreasp.mods.EnderStuffPlus.tileentity;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.item.ItemAvisCompass;

public class TileEntityAvisEgg extends TileEntity {
	private int ticksUntilHatch;
	private int ticksUntilEnemy;
	private Random rand = new Random();
	public String playerName = "";
	public boolean belongsToPlayer = false;

	public TileEntityAvisEgg() {
		this.setEnemySpawnTicks();
	}
	
	private void setEnemySpawnTicks() {
		this.ticksUntilEnemy = rand.nextInt(200) + 100;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void onChunkUnload() {
		if( this.worldObj.isRemote) ItemAvisCompass.remTile(this );
		super.onChunkUnload();
	}
	
	@Override
	public void invalidate() {
		if( this.worldObj.isRemote) ItemAvisCompass.remTile(this );
		super.invalidate();
	}
	
	@Override
	public void updateEntity() {
		if( this.worldObj.isRemote ) {
			if( !this.belongsToPlayer ) {
				ItemAvisCompass.addTile(this);
			} else if( this.isValidDarkness() ) {
				this.worldObj.spawnParticle("portal",
					this.xCoord+0.5D + (this.rand.nextDouble() - 0.5D) * 1.5D,
					this.yCoord + this.rand.nextDouble() * 1.5D - 0.25D,
					this.zCoord+0.5D + (this.rand.nextDouble() - 0.5D) * 1.5D,
					(this.rand.nextDouble() - 0.5D) * 2D,
					-this.rand.nextDouble(),
					(this.rand.nextDouble() - 0.5D) * 2D);
				ItemAvisCompass.remTile(this);
			}
		} else {
			if( this.isValidDarkness() && !this.playerName.isEmpty() ) {
				if( ++this.ticksUntilHatch >= 12000 ) {
					if( !this.worldObj.isRemote ) {
						EntityEnderAvis avis = new EntityEnderAvis(this.worldObj);
						avis.setTamed(true);
						avis.setOwner(this.playerName);
						avis.setPosition(this.xCoord+0.5D, this.yCoord, this.zCoord+0.5D);
						this.worldObj.spawnEntityInWorld(avis);
						avis.playLivingSound();
					}
					this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
					this.worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
				} 
			} else if( this.playerName.isEmpty() && this.worldObj.getBlockLightValue(xCoord, yCoord, zCoord) <= 7 ) {
				AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(this.xCoord - 5D, yCoord, zCoord-5D, xCoord+6D, yCoord+3D, zCoord+6D);
				if( this.ticksUntilEnemy <= 0 && (this.worldObj.getEntitiesWithinAABB(EntityEnderAvis.class, bb)).size() < 5 ) {
					for( int i = 0; i <= this.rand.nextInt(3); i++ ) {
						EntityEnderAvis avis = new EntityEnderAvis(this.worldObj);
						avis.setAggressive(true);
						avis.setPosition(xCoord - this.rand.nextInt(3) + 1, yCoord, zCoord - this.rand.nextInt(3) + 1);
						this.worldObj.spawnEntityInWorld(avis);
						avis.spawnExplosionParticle();
						ticksUntilEnemy = rand.nextInt(200) + 100;
					}
				} else {
					this.ticksUntilEnemy--;
				}
			}
//			
//			if( worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) != ESPModRegistry.avisEgg.blockID ) {
//				this.invalidate();
//			}
		}
	}
	
	private boolean isValidDarkness() {
		return this.worldObj.getBlockLightValue(xCoord, yCoord, zCoord) <= 1;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		
		par1nbtTagCompound.setInteger("ticksHatch", this.ticksUntilHatch);
		par1nbtTagCompound.setString("playerName", this.playerName);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		
		this.ticksUntilHatch = par1nbtTagCompound.getInteger("ticksHatch");
		this.playerName = par1nbtTagCompound.getString("playerName");
	}
	
	@Override
	public Packet getDescriptionPacket() {
		if( !this.worldObj.isRemote ) {
			NBTTagCompound nbt = new NBTTagCompound("teAvisEggNBT");
			nbt.setBoolean("belongsToPlayer", !this.playerName.isEmpty());
			Packet132TileEntityData packet = new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
			return packet;
		} else return null;
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		if( this.worldObj.isRemote ) {
			this.belongsToPlayer = pkt.data.getBoolean("belongsToPlayer");
		}
	}

}
