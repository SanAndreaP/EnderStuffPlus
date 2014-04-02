package sanandreasp.mods.EnderStuffPlus.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPearlMiss
    extends EntityThrowable
{
    public EntityPearlMiss(World world) {
        super(world);
    }

    public EntityPearlMiss(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityPearlMiss(World world, EntityLivingBase livingBase) {
        super(world, livingBase);
    }

    public boolean _SAP_canImpactOnLiquid() {
        return true;
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjPos) {
        if( movingObjPos.entityHit != null ) {
            movingObjPos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }

        if( !this.worldObj.isRemote ) {
            if( movingObjPos.typeOfHit == EnumMovingObjectType.TILE && this.getThrower() instanceof EntityPlayer ) {
                EntityBait bait = new EntityBait(this.worldObj);
                bait.setPosition(this.posX, Math.floor(this.posY), this.posZ);
                this.worldObj.spawnEntityInWorld(bait);
            } else if( movingObjPos.typeOfHit == EnumMovingObjectType.ENTITY && movingObjPos.entityHit != null
                       && movingObjPos.entityHit instanceof EntityLivingBase ) {
                EntityBait bait = new EntityBait(this.worldObj);
                bait.setPosition(this.posX, Math.floor(this.posY), this.posZ);
                bait.mountEntity(movingObjPos.entityHit);
                this.worldObj.spawnEntityInWorld(bait);
            }

            this.setDead();
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }
}
