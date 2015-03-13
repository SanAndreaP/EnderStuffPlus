/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.entity.projectile;

import de.sanandrew.mods.enderstuffp.util.EspItems;
import de.sanandrew.mods.enderstuffp.util.manager.ReflectionManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAvisArrow
    extends EntityArrow
{
    private boolean hasGravity = true;

    public EntityAvisArrow(World world) {
        super(world);
    }

    public EntityAvisArrow(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityAvisArrow(World world, EntityLivingBase shooter, EntityLivingBase target, float motionMulti, float precision) {
        super(world, shooter, target, motionMulti, precision);

        double deltaX = target.posX - shooter.posX;
        double deltaY = target.boundingBox.minY + target.height / 3.0F - this.posY;
        double deltaZ = target.posZ - shooter.posZ;
        double vectorXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);

        if( vectorXZ > 0.0D ) {
            this.setThrowableHeading(deltaX, deltaY, deltaZ, motionMulti, precision);
        }
    }

    public EntityAvisArrow(World world, EntityLivingBase shooter, float motionMulti) {
        super(world, shooter, motionMulti);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        if( !this.worldObj.isRemote ) {
            if( ReflectionManager.isArrowInGround(this) && this.arrowShake <= 0
                && ((this.canBePickedUp == 1 && player.inventory.addItemStackToInventory(new ItemStack(EspItems.avisArrow, 1)))
                    || player.capabilities.isCreativeMode) )
            {
                this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    @Override
    public void onUpdate() {
        if( this.hasGravity ) {
            double prevMotionX = this.motionX;
            double prevMotionY = this.motionY;
            double prevMotionZ = this.motionZ;

            super.onUpdate();

            this.hasGravity = ReflectionManager.getTicksInAir(this) != 0;

            this.motionX = prevMotionX;
            this.motionY = prevMotionY * 0.99F;
            this.motionZ = prevMotionZ;
        } else {
            super.onUpdate();
        }
    }
}
