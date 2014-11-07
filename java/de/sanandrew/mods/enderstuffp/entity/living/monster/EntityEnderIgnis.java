package de.sanandrew.mods.enderstuffp.entity.living.monster;

import de.sanandrew.mods.enderstuffp.entity.living.IEnderCreature;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.RegistryItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityEnderIgnis
    extends EntityEnderman
    implements IEnderCreature
{
    public EntityEnderIgnis(World world) {
        super(world);
        this.experienceValue = 8;
        this.isImmuneToFire = true;
    }

    @Override
    public Block func_146080_bZ() {
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
    protected void dropFewItems(boolean recentlyHit, int lootingLvl) {
        int j = this.rand.nextInt(3);

        if( lootingLvl > 0 ) {
            j += this.rand.nextInt(lootingLvl + 1);
        }
        this.entityDropItem(new ItemStack(RegistryItems.espPearls, j, 1), 0.0F);
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

    @SuppressWarnings("unused")
    private void setBlockFire() {
        int blockPosX = (int) Math.floor(this.posX);
        int blockPosY = (int) Math.floor(this.posY);
        int blockPosZ = (int) Math.floor(this.posZ);

        for( int x = -2; x <= 2; x++ ) {
            for( int y = -2; y <= 2; y++ ) {
                for( int z = -2; z <= 2; z++ ) {
                    if( Math.sqrt((x * x) + (y * y) + (z * z)) <= 2D ) {
                        if( this.worldObj.isAirBlock(blockPosX + x, blockPosY + y, blockPosZ + z) ) {
                            this.worldObj.setBlock(blockPosX + x, blockPosY + y, blockPosZ + z, Blocks.fire);
                        }
                    }
                }
            }
        }
    }
}
