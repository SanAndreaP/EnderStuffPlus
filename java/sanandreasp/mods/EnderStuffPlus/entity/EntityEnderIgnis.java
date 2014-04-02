package sanandreasp.mods.EnderStuffPlus.entity;

import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityEnderIgnis
    extends EntityEndermanESP
    implements IEnderCreature
{

    public EntityEnderIgnis(World world) {
        super(world);
        this.experienceValue = 8;
        this.isImmuneToFire = true;

        carriableBlocks[Block.grass.blockID] = false;
        carriableBlocks[Block.dirt.blockID] = false;
        carriableBlocks[Block.sand.blockID] = false;
        carriableBlocks[Block.gravel.blockID] = false;
        carriableBlocks[Block.plantYellow.blockID] = false;
        carriableBlocks[Block.plantRed.blockID] = false;
        carriableBlocks[Block.mushroomBrown.blockID] = false;
        carriableBlocks[Block.mushroomRed.blockID] = false;
        carriableBlocks[Block.tnt.blockID] = false;
        carriableBlocks[Block.cactus.blockID] = false;
        carriableBlocks[Block.blockClay.blockID] = false;
        carriableBlocks[Block.pumpkin.blockID] = false;
        carriableBlocks[Block.melon.blockID] = false;
        carriableBlocks[Block.mycelium.blockID] = false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(10.0D);
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
    protected int getDamageDropped() {
        return 1;
    }

    @Override
    protected String getDeathSound() {
        return "enderstuffp:enderignis.death";
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
        return "enderstuffp:enderignis.hit";
    }

    @Override
    protected String getLivingSound() {
        return "enderstuffp:enderignis.idle";
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
                        if( this.worldObj.getBlockId(blockPosX + x, blockPosY + y, blockPosZ + z) == 0 ) {
                            this.worldObj.setBlock(blockPosX + x, blockPosY + y, blockPosZ + z, Block.fire.blockID);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void spawnParticle(String type, double X, double Y, double Z, float dataI, float dataII, float dataIII) {
        super.spawnParticle(type, X, Y, Z, 1.0F, 1.0F, 0.0F);
    }
}
