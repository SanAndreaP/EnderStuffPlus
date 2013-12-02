package sanandreasp.mods.EnderStuffPlus.entity.item;

import java.util.List;

import sanandreasp.mods.EnderStuffPlus.packet.PacketsSendToClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityBait extends Entity {

	public EntityBait(World par1World) {
		super(par1World);
        this.setSize(0.25F, 0.25F);
	}

	@Override
	protected void entityInit() {

	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if( this.ticksExisted++ >= 3000 || this.isBurning() ) {
			this.setDead();
		}
		
	    PacketsSendToClient.sendParticle(this, (byte)0, this.posX, this.posY, this.posZ, 1D, 0.5D, 0.7D);
	    
	    if( !this.worldObj.isRemote ) {
	    	AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
	    			this.posX - 32, this.posY - 32, this.posZ - 32,
	    			this.posX + 32, this.posY + 32, this.posZ + 32
	    	);
	    	
	    	List<EntityBait> myBaits = this.worldObj.getEntitiesWithinAABB(EntityBait.class, aabb);
	    	for( EntityBait bait : myBaits ) {
	    		if( bait != this && bait != null && bait.isEntityAlive() && bait.ticksExisted <= this.ticksExisted ) {
	    			this.setDead();
	    			break;
	    		}
	    	}
	    	
	    	List<EntityCreature> animals = this.worldObj.getEntitiesWithinAABB(EntityCreature.class, aabb);
	    	
	    	for( EntityCreature animal : animals ) {
	    		if( animal == this.ridingEntity )
	    			continue;
	    		if( this.isEntityAlive() && (animal.getNavigator().noPath() || !animal.hasPath()) ) {
	                PathEntity path = this.worldObj.getPathEntityToEntity(animal, this, 32.5F, true, false, false, true);
	                animal.getNavigator().setPath(path, 1.2F);
	                animal.setPathToEntity(path);
	    		} else if( !this.isEntityAlive() ) {
	                animal.getNavigator().clearPathEntity();
	                animal.setPathToEntity(null);
	    		}
	    	}
	    }
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		this.ticksExisted = nbttagcompound.getInteger("ticksExisted");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("ticksExisted", this.ticksExisted);
	}

}
