package de.sanandrew.mods.enderstuffp.entity.living.monster;

import de.sanandrew.core.manpack.util.javatuples.Unit;
import de.sanandrew.mods.enderstuffp.entity.living.IEnderCreature;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumParticleFx;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityEnderIgnis
    extends AEntityEndermanEsp
    implements IEnderCreature
{
    public EntityEnderIgnis(World world) {
        super(world);
        this.experienceValue = 8;
        this.isImmuneToFire = true;
        this.isNightActive = false;
    }

    @Override
    public void initCarriables() {
        // empty, because it can't carry anything
    }

    @Override
    public Block getCarryingBlock() {
        return Blocks.air;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if( super.attackEntityAsMob(entity) ) {
            entity.setFire(3);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void dropFewItems(boolean playerHit, int lootLevel) {
        int j = this.rand.nextInt(3);

        if( lootLevel > 0 ) {
            j += this.rand.nextInt(lootLevel + 1);
        }
        this.entityDropItem(new ItemStack(EspItems.espPearls, j, 1), 0.0F);
    }

    @Override
    protected String getDeathSound() {
        return EnderStuffPlus.MOD_ID + ":enderignis.death";
    }

    @Override
    public ItemStack getHeldItem() {
        return new ItemStack(Items.iron_sword);
    }

    @Override
    protected String getHurtSound() {
        return EnderStuffPlus.MOD_ID + ":enderignis.hit";
    }

    @Override
    protected String getLivingSound() {
        return EnderStuffPlus.MOD_ID + ":enderignis.idle";
    }

    @Override
    protected void spawnIdleParticle() {
        EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_IGNIS_BODY, this.posX, this.posY, this.posZ, this.dimension, Unit.with(false));
    }

    @Override
    public void spawnParticles(double x, double y, double z) {
        EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_IGNIS_BODY, x, y, z, this.dimension, Unit.with(false));
    }

    private void setBlockFire() {
        int blockPosX = (int) Math.floor(this.posX);
        int blockPosY = (int) Math.floor(this.posY);
        int blockPosZ = (int) Math.floor(this.posZ);

        for( int x = -2; x <= 2; x++ ) {
            for( int y = -2; y <= 2; y++ ) {
                for( int z = -2; z <= 2; z++ ) {
                    if( Math.sqrt((x * x) + (y * y) + (z * z)) <= 2.0D ) {
                        if( this.worldObj.isAirBlock(blockPosX + x, blockPosY + y, blockPosZ + z) ) {
                            this.worldObj.setBlock(blockPosX + x, blockPosY + y, blockPosZ + z, Blocks.fire);
                        }
                    }
                }
            }
        }
    }
}
