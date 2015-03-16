package de.sanandrew.mods.enderstuffp.entity.living.monster;

import de.sanandrew.core.manpack.util.javatuples.Unit;
import de.sanandrew.mods.enderstuffp.entity.living.IEnderCreature;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumParticleFx;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityEnderNivis
    extends AEntityEndermanEsp
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if( source.isFireDamage() ) {
            damage *= 5.0F;
        }

        return super.attackEntityFrom(source, damage);
    }

    @Override
    public float getAIMoveSpeed() {
        return this.entityToAttack != null ? 0.15F : 0.1F;
    }

    @Override
    protected String getDeathSound() {
        return EnderStuffPlus.MOD_ID + ":endernivis.death";
    }

    @Override
    protected void dropFewItems(boolean playerHit, int lootLevel) {
        int j = this.rand.nextInt(3);

        if( lootLevel > 0 ) {
            j += this.rand.nextInt(lootLevel + 1);
        }

        this.entityDropItem(new ItemStack(EspItems.espPearls, j, 0), 0.0F);
    }

    @Override
    public ItemStack getHeldItem() {
        return new ItemStack(Items.iron_sword);
    }

    @Override
    protected String getHurtSound() {
        return EnderStuffPlus.MOD_ID + ":endernivis.scream";
    }

    @Override
    protected String getLivingSound() {
        return EnderStuffPlus.MOD_ID + ":endernivis.idle";
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
                if( Math.sqrt((x * x) + (z * z)) <= 2.3D && this.worldObj.getBlock(blockPosX + x, blockPosY, blockPosZ + z) == Blocks.water ) {
                    this.worldObj.setBlock(blockPosX + x, blockPosY, blockPosZ + z, Blocks.ice);
                }
            }
        }
    }

    @Override
    protected void spawnIdleParticle() {
        EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_NIVIS_BODY, this.posX, this.posY, this.posZ, this.dimension, Unit.with(false));
    }

    @Override
    public void spawnParticles(double x, double y, double z) {
        EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_NIVIS_BODY, x, y, z, this.dimension, Unit.with(false));
    }
}
