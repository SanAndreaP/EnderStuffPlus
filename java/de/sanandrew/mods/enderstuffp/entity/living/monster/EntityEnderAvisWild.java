/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.entity.living.monster;

import de.sanandrew.core.manpack.util.UsedByReflection;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import de.sanandrew.mods.enderstuffp.entity.living.AEntityEnderAvis;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityEnderAvisWild
        extends AEntityEnderAvis
{
    @UsedByReflection
    public EntityEnderAvisWild(World world) {
        super(world);
        setSize(1.0F, 2.0F);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damageVal) {
        if( this.entityToAttack == null && source.getEntity() != null ) {
            this.entityToAttack = source.getEntity();
        }

        return super.attackEntityFrom(source, damageVal);
    }

    @Override
    public void onUpdate() {
        if( !this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL ) {
            this.setDead();
        }

        super.onUpdate();
    }

    @Override
    public float getAIMoveSpeed() {
        return this.entityToAttack != null || this.hasPath() ? 0.3F + (this.getCoatBase() == RaincoatManager.baseGold ? 0.05F : 0.0F) : 0.1F;
    }

    @Override
    protected boolean isTamed() {
        return false;
    }

    @Override
    protected Item getDropItem() {
        return EspItems.avisFeather;
    }

    private boolean isValidLightLevel() {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.boundingBox.minY);
        int var3 = MathHelper.floor_double(this.posZ);

        if( this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > this.rand.nextInt(32) ) {
            return false;
        } else {
            int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);

            if( this.worldObj.isThundering() ) {
                int var5 = this.worldObj.skylightSubtracted;
                this.worldObj.skylightSubtracted = 10;
                var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
                this.worldObj.skylightSubtracted = var5;
            }

            return var4 <= this.rand.nextInt(8);
        }
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.isValidLightLevel() && super.getCanSpawnHere();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }
}
