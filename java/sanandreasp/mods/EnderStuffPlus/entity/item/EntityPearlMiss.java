package sanandreasp.mods.EnderStuffPlus.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPearlMiss extends EntityThrowable {
    
    public EntityPearlMiss(World par1World) {
        super(par1World);
    }
    
    public EntityPearlMiss(World par1World, EntityLivingBase par2EntityLivingBase) {
        super(par1World, par2EntityLivingBase);
    }

    @SideOnly(Side.CLIENT)
    public EntityPearlMiss(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    }

    @Override
    protected void onImpact(MovingObjectPosition movingobjectposition) {
        if( movingobjectposition.entityHit != null ) {
            movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }
        
        if( !this.worldObj.isRemote ) {
            if( movingobjectposition.typeOfHit == EnumMovingObjectType.TILE && this.getThrower() instanceof EntityPlayer ) {
                EntityBait bait = new EntityBait(this.worldObj);
                bait.setPosition(this.posX, Math.floor(this.posY), this.posZ);
                this.worldObj.spawnEntityInWorld(bait);
            } else if( movingobjectposition.typeOfHit == EnumMovingObjectType.ENTITY && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityLivingBase ) {
                EntityBait bait = new EntityBait(this.worldObj);
                bait.setPosition(this.posX, Math.floor(this.posY), this.posZ);
                bait.mountEntity(movingobjectposition.entityHit);
                this.worldObj.spawnEntityInWorld(bait);
            }
            
            this.setDead();
        }
    }
    
    public boolean canImpactOnLiquid() {
        return true;
    }

}
