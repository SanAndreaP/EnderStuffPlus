package de.sanandrew.mods.enderstuffplus.entity.living.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import de.sanandrew.mods.enderstuffplus.entity.living.IEnderCreature;
import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class EntityEnderNivis
    extends EntityEnderman
    implements IEnderCreature
{
    public EntityEnderNivis(World world) {
        super(world);
        this.setSize(0.6F, 2.9F);
        this.stepHeight = 1.0F;
        this.experienceValue = 8;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
    }

    @Override
    public boolean attackEntityFrom(DamageSource dmgSource, float attackPts) {
        if( dmgSource.equals(DamageSource.drown) && this.getAir() > -20 ) return false;
        
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
    protected String getDeathSound() {
        return "enderstuffp:endernivis.death";
    }

    @Override
    protected void dropFewItems(boolean recentlyHit, int lootingLvl) {
        int j = this.rand.nextInt(3);

        if( lootingLvl > 0 ) {
            j += this.rand.nextInt(lootingLvl + 1);
        }
        this.entityDropItem(new ItemStack(ModItemRegistry.espPearls, j, 0), 0.0F);
    }

    @Override
    public ItemStack getHeldItem() {
        return new ItemStack(Items.iron_sword);
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
                if( Math.sqrt((x * x) + (z * z)) <= 2.3D && this.worldObj.getBlock(blockPosX + x, blockPosY, blockPosZ + z) == Blocks.water ) {
                    this.worldObj.setBlock(blockPosX + x, blockPosY, blockPosZ + z, Blocks.ice);
                }
            }
        }
    }
}
