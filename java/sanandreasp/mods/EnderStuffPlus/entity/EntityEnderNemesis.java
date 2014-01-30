package sanandreasp.mods.EnderStuffPlus.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityEnderNemesis extends EntityEndermanESP implements Textures {

	public EntityEnderNemesis(World par1World) {
		super(par1World);
		
		this.calmInDaylight = false;
		this.isImmuneToFire = true;
		this.isImmuneToWater = true;
	}
	
	@Override
	public void spawnParticle(int id, double X, double Y, double Z, double dataI, double dataII, double dataIII) {
    	if( id == 0 ) {
    		dataI = 0F;
    		dataII = 0F;
    		dataIII = 1F;
    	}
		super.spawnParticle(id, X, Y, Z, dataI, dataII, dataIII);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		
		ItemStack holdItm = new ItemStack(ESPModRegistry.niobSword);
		switch(this.rand.nextInt(8)) {
			case 4: holdItm = new ItemStack(ESPModRegistry.niobPick); break;
			case 5: holdItm = new ItemStack(ESPModRegistry.niobAxe); break;
			case 6: holdItm = new ItemStack(ESPModRegistry.niobShovel); break;
			case 7: holdItm = new ItemStack(ESPModRegistry.niobHoe); break;
		}
		this.dataWatcher.addObject(20, holdItm);
	}
	
	@Override
	protected void onDeathUpdate() {
		this.deathTime = 19;
		super.onDeathUpdate();
	}
	
	@Override
	protected boolean shouldAttackPlayer(EntityPlayer par1EntityPlayer) {
	    return this.entityToAttack == null;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setCompoundTag("CarryingWeapon", this.dataWatcher.getWatchableObjectItemStack(20).writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		if( par1nbtTagCompound.hasKey("CarryingWeapon") )
			this.dataWatcher.updateObject(20, ItemStack.loadItemStackFromNBT((NBTTagCompound) par1nbtTagCompound.getTag("CarryingWeapon")));
	}

	@Override
	protected void dropRareDrop(int par1) {
		 this.dropItem(this.dataWatcher.getWatchableObjectItemStack(20).itemID, 1);
	}
	
	@Override
	public ItemStack getHeldItem() {
		return this.dataWatcher.getWatchableObjectItemStack(20);
	}
	
	@Override
	protected int getDropItemId() {
		return -1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getItemIcon(ItemStack par1ItemStack, int par2) {
		return par1ItemStack.getItem().getIcon(par1ItemStack, par2);
	}
	
    public boolean getCanSpawnHere() {
        return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }
    
    @Override
    protected boolean teleportTo(int cause, double par1, double par3, double par5) {
    	if( cause == TP_DAYTIME )
    		return false;
    	return super.teleportTo(cause, par1, par3, par5);
    }
}
