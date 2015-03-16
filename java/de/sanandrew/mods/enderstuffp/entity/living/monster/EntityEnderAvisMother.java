/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.entity.living.monster;

import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffp.entity.living.AEntityEnderAvis;
import de.sanandrew.mods.enderstuffp.entity.projectile.EntityAvisArrow;
import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
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

    public void setEggPosition(int x, int y, int z) {
        this.eggPos = Triplet.with(x, y, z);
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

        if( this.worldObj.getBlock(this.eggPos.getValue0(), this.eggPos.getValue1(), this.eggPos.getValue2()) != EspBlocks.avisEgg ) {
            this.attackEntityFrom(DamageSource.magic, 1.0F);
        }

        if( this.entityToAttack instanceof EntityLivingBase ) {
            if( !this.canEntityBeSeen(this.entityToAttack) || this.entityToAttack.isDead ) {
                this.entityToAttack = null;
            } else if( ticksExisted % 20 == 0 && !this.worldObj.isRemote && this.getDistanceSqToEntity(this.entityToAttack) > 12.0D && !this.isDead ) {
                EntityAvisArrow projectile = new EntityAvisArrow(this.worldObj, this, (EntityLivingBase) this.entityToAttack, 1.6F,
                                                                 (14 - this.worldObj.difficultySetting.getDifficultyId() * 4)
                );
                projectile.setDamage(4.0D + this.rand.nextGaussian() * 0.25D + (this.worldObj.difficultySetting.getDifficultyId() * 0.11D));

                this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
                this.worldObj.spawnEntityInWorld(projectile);
            }
        } else {
            @SuppressWarnings("unchecked")
            List<EntityLivingBase> livings = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
                                                                                 AxisAlignedBB.getBoundingBox(this.eggPos.getValue0() - 16.0F,
                                                                                                              this.eggPos.getValue1() - 16.0F,
                                                                                                              this.eggPos.getValue2() - 16.0F,
                                                                                                              this.eggPos.getValue0() + 16.0F,
                                                                                                              this.eggPos.getValue1() + 16.0F,
                                                                                                              this.eggPos.getValue2() + 16.0F));
            if( livings.size() > 0 ) {
                this.entityToAttack = livings.get(this.rand.nextInt(livings.size()));

                if( this.entityToAttack.isDead || this.entityToAttack == this || this.entityToAttack.isEntityInvulnerable()
                    || !this.canEntityBeSeen(this.entityToAttack) )
                {
                    this.entityToAttack = null;
                } else {
                    this.setPathToEntity(null);
                }
            } else {
                this.entityToAttack = null;
            }

            if( this.entityToAttack == null && !this.hasPath() && this.getDistanceSq(this.eggPos.getValue0(), this.eggPos.getValue1(), this.eggPos.getValue2()) > 64.0D ) {
                this.setPathToEntity(this.worldObj.getEntityPathToXYZ(this, this.eggPos.getValue0(), this.eggPos.getValue1(), this.eggPos.getValue2(), 96.0F,
                                                                      false, false, true, true));
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);

        if( this.eggPos != null ) {
            tagCompound.setInteger("eggPosX", this.eggPos.getValue0());
            tagCompound.setInteger("eggPosY", this.eggPos.getValue1());
            tagCompound.setInteger("eggPosZ", this.eggPos.getValue2());
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompound) {
        super.readEntityFromNBT(tagCompound);

        if( tagCompound.hasKey("eggPosX") && tagCompound.hasKey("eggPosY") && tagCompound.hasKey("eggPosZ") ) {
            this.eggPos = Triplet.with(tagCompound.getInteger("eggPosX"), tagCompound.getInteger("eggPosY"), tagCompound.getInteger("eggPosZ"));
        }
    }

    @Override
    public float getAIMoveSpeed() {
        return this.entityToAttack != null || this.hasPath() ? 0.5F + (this.getCoatBase() == RaincoatManager.baseGold ? 0.05F : 0.0F) : 0.1F;
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

    @Override
    public double getDefaultMaxHealth() {
        return 160.0D;
    }
}
