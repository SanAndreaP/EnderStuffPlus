package sanandreasp.mods.EnderStuffPlus.entity.item;

import java.util.List;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if( this.ticksExisted++ >= 3000 || this.isBurning() ) {
			this.setDead();
		}
		
		ESPModRegistry.sendPacketAllRng("fxPortal", this.posX, this.posY, this.posZ, 128.0D, this.dimension, this.posX, this.posY, this.posZ, 1.0F, 0.5F, 0.7F, this.width, this.height);
	    
	    if( !this.worldObj.isRemote ) {
	    	AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
	    			this.posX - 32.0D, this.posY - 32.0D, this.posZ - 32.0D,
	    			this.posX + 32.0D, this.posY + 32.0D, this.posZ + 32.0D
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
