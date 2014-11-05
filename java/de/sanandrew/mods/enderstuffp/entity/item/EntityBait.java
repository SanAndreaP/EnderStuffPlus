package de.sanandrew.mods.enderstuffp.entity.item;

import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumParticleFx;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class EntityBait
        extends Entity
{
    public EntityBait(World world) {
        super(world);
        this.setSize(0.25F, 0.25F);
    }

    @Override
    protected void entityInit() {}

    @Override
    @SuppressWarnings("unchecked")
    public void onUpdate() {
        super.onUpdate();

        if( this.ticksExisted++ >= 3000 || this.isBurning() ) {
            this.setDead();
        }

        if( this.ticksExisted % 10 == 0 ) {
            EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_PEARL_BAIT, this.posX, this.posY, this.posZ, this.dimension, null);
        }

        if( !this.worldObj.isRemote ) {
            AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.posX - 32.0D, this.posY - 32.0D, this.posZ - 32.0D,
                                                              this.posX + 32.0D, this.posY + 32.0D, this.posZ + 32.0D);

            List<EntityBait> myBaits = this.worldObj.getEntitiesWithinAABB(EntityBait.class, aabb);
            for( EntityBait bait : myBaits ) {
                if( bait != this && bait != null && bait.isEntityAlive() && bait.ticksExisted <= this.ticksExisted ) {
                    this.setDead();
                    break;
                }
            }

            List<EntityCreature> animals = this.worldObj.getEntitiesWithinAABB(EntityCreature.class, aabb);

            for( EntityCreature animal : animals ) {
                if( animal == this.ridingEntity ) {
                    continue;
                }
                if( this.isEntityAlive() && (animal.getNavigator().noPath() || !animal.hasPath()) ) {
                    PathEntity path = this.worldObj.getPathEntityToEntity(animal, this, 32.5F, true, false, false, true);
                    animal.getNavigator().setPath(path, 1.0D);
                    animal.setPathToEntity(path);
                } else if( !this.isEntityAlive() ) {
                    animal.getNavigator().clearPathEntity();
                    animal.setPathToEntity(null);
                }
            }
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        this.ticksExisted = nbt.getInteger("ticksExisted");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setInteger("ticksExisted", this.ticksExisted);
    }

}
