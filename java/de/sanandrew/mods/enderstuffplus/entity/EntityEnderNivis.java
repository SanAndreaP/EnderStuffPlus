// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst

package de.sanandrew.mods.enderstuffplus.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class EntityEnderNivis
    extends EntityEndermanESP
    implements IEnderCreature
{

    public EntityEnderNivis(World world) {
        super(world);
        this.setSize(0.6F, 2.9F);
        this.stepHeight = 1.0F;
        this.experienceValue = 8;
        this.isImmuneToWater = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(10.0D);
    }

    @Override
    public boolean attackEntityFrom(DamageSource dmgSource, float attackPts) {
        if( dmgSource.isFireDamage() ) {
            attackPts *= 5;
        }

        return super.attackEntityFrom(dmgSource, attackPts);
    }

    @Override
    public float getAIMoveSpeed() {
        return this.entityToAttack != null ? 0.15F : 0.1F;
    }

    @Override
    protected int getDamageDropped() {
        return 0;
    }

    @Override
    protected String getDeathSound() {
        return "enderstuffp:endernivis.death";
    }

    @Override
    protected int getDropItemId() {
        return ModItemRegistry.espPearls.itemID;
    }

    @Override
    public ItemStack getHeldItem() {
        return new ItemStack(Item.swordIron);
    }

    @Override
    protected String getHurtSound() {
        return "enderstuffp:endernivis.scream";
    }

    @Override
    protected String getLivingSound() {
        return "enderstuffp:endernivis.idle";
    }

    @Override
    public void onLivingUpdate() {
        if( this.ticksExisted % 5 == 0 && !this.worldObj.isRemote ) {
            this.setBlockIce();
        }

        super.onLivingUpdate();
    }

    private void setBlockIce() {
        int blockPosX = (int) Math.floor(this.posX);
        int blockPosY = (int) Math.floor(this.posY) - 1;
        int blockPosZ = (int) Math.floor(this.posZ);

        for( int x = -2; x <= 2; x++ ) {
            for( int z = -2; z <= 2; z++ ) {
                if( Math.sqrt((x * x) + (z * z)) <= 2.3D ) {
                    if( this.worldObj.getBlockId(blockPosX + x, blockPosY, blockPosZ + z) == Block.waterStill.blockID ) {
                        this.worldObj.setBlock(blockPosX + x, blockPosY, blockPosZ + z, Block.ice.blockID);
                    }
                }
            }
        }
    }

    @Override
    public void spawnParticle(String type, double X, double Y, double Z, float dataI, float dataII, float dataIII) {
        super.spawnParticle(type, X, Y, Z, 0.2F, 0.5F, 1.0F);
    }
}
