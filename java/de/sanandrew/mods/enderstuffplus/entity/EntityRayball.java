package de.sanandrew.mods.enderstuffplus.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;

public class EntityRayball
    extends EntityFireball
{
    public EntityRayball(World world) {
        super(world);
    }

    public EntityRayball(World world, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(world, x, y, z, accelX, accelY, accelZ);
    }

    public EntityRayball(World world, EntityLiving shooter, double accelX, double accelY, double accelZ) {
        super(world, shooter, accelX, accelY, accelZ);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjPos) {
        if( !this.worldObj.isRemote ) {
            if( movingObjPos.entityHit != null
                && movingObjPos.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 4) )
            {
                if( (movingObjPos.entityHit instanceof EntityLivingBase) && !(movingObjPos.entityHit instanceof EntityEnderRay) ) {
                    ((EntityLivingBase) movingObjPos.entityHit).addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 0));
                    ((EntityLivingBase) movingObjPos.entityHit).addPotionEffect(new PotionEffect(Potion.poison.id, 100, 0));
                }
            }

            this.worldObj.createExplosion((Entity) null, this.posX, this.posY, this.posZ, 1.0F, false);
            this.setDead();
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.extinguish();
        ESPModRegistry.sendPacketAllRng("fxRayball", this.posX, this.posY, this.posZ, 128.0D, this.dimension,
                                        this.posX, this.posY, this.posZ);
    }
}
