/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.entity.living.monster;

import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffp.entity.living.AEntityEnderAvis;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class EntityEnderAvisMother
        extends AEntityEnderAvis
        implements IBossDisplayData
{
    private Triplet<Integer, Integer, Integer> eggPos;

    public EntityEnderAvisMother(World world) {
        super(world);
        setSize(1.0F, 2.0F);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        float damage = 10.0F;

        if( this.isPotionActive(Potion.damageBoost) ) {
            damage += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }

        if( this.isPotionActive(Potion.weakness) ) {
            damage -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
        }

        if( entity instanceof EntityLivingBase ) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 0));
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 300, 0));
        }

        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
    }

    @Override
    public void onLivingUpdate() {
        if( this.eggPos == null ) {
            this.eggPos = Triplet.with((int) this.posX, (int) this.posY, (int) this.posZ);
        }

        if( this.entityToAttack == null ) {
            @SuppressWarnings("unchecked")
            List<EntityLivingBase> livings = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
                                                                                 AxisAlignedBB.getBoundingBox(this.eggPos.getValue0() - 8.0F,
                                                                                                              this.eggPos.getValue1() - 8.0F,
                                                                                                              this.eggPos.getValue2() - 8.0F,
                                                                                                              this.eggPos.getValue0() + 8.0F,
                                                                                                              this.eggPos.getValue1() + 8.0F,
                                                                                                              this.eggPos.getValue2() + 8.0F));
            if( livings.size() > 0 ) {
                this.entityToAttack = livings.get(this.rand.nextInt(livings.size()));

                if( this.entityToAttack.isDead || this.entityToAttack == this ) {
                    this.entityToAttack = null;
                } else {
                    this.setPathToEntity(null);
                }
            }

            if( this.entityToAttack == null && !this.hasPath() && this.getDistanceSq(this.eggPos.getValue0(), this.eggPos.getValue1(), this.eggPos.getValue2()) > 64.0D ) {
                this.setPathToEntity(this.worldObj.getEntityPathToXYZ(this, this.eggPos.getValue0(), this.eggPos.getValue1(), this.eggPos.getValue2(), 96.0F,
                                                                      false, false, true, true));
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public float getAIMoveSpeed() {
        return this.entityToAttack != null || this.hasPath() ? 0.3F + (this.getCoatBase() == RaincoatManager.baseGold ? 0.05F : 0.0F) : 0.1F;
    }

    @Override
    protected Item getDropItem() {
        return null;
    }

    @Override
    protected boolean isTamed() {
        return false;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public boolean isWet() {
        return false;
    }
}
