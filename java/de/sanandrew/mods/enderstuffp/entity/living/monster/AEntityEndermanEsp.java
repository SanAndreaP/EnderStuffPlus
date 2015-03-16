/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.entity.living.monster;

import cpw.mods.fml.common.eventhandler.Cancelable;
import de.sanandrew.mods.enderstuffp.entity.living.ITeleportProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.IdentityHashMap;
import java.util.UUID;

/**
 * An abstraction attempt of the {@link net.minecraft.entity.monster.EntityEnderman} class for my own ender creatures,
 * featuring cleaned up code
 **/
public abstract class AEntityEndermanEsp
        extends EntityMob
        implements ITeleportProvider<AEntityEndermanEsp>
{
    private static final UUID ASB_MODIFIER_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier ASB_MODIFIER = (new AttributeModifier(ASB_MODIFIER_UUID, "Attacking speed boost", 6.199999809265137D, 0)).setSaved(false);

    private static final int DW_CARRYING_BLOCK = 16;
    private static final int DW_CARRYING_META = 17;
    private static final int DW_SCREAM_FLAG = 18;

    private int teleportDelay = 0;
    private int stareTimer = 0;
    private boolean isAggressive = false;
    private Entity lastEntityToAttack = null;
    private IdentityHashMap<Block, Boolean> carriable = new IdentityHashMap<>(4096);

    protected boolean isImmuneToWater = false;
    protected boolean isNightActive = true;

    public AEntityEndermanEsp(World world) {
        super(world);
        this.setSize(0.6F, 2.9F);
        this.stepHeight = 1.0F;
        this.initCarriables();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(DW_CARRYING_BLOCK, (short) 0);
        this.dataWatcher.addObject(DW_CARRYING_META, (byte) 0);
        this.dataWatcher.addObject(DW_SCREAM_FLAG, (byte) 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setShort("carried", (short) Block.getIdFromBlock(this.getCarryingBlock()));
        nbt.setShort("carriedData", (short) this.getCarryingData());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setCarryingBlock(Block.getBlockById(nbt.getShort("carried")));
        this.setCarryingData(nbt.getShort("carriedData"));
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer player = this.worldObj.getClosestVulnerablePlayerToEntity(this, 64.0D);

        if( player != null ) {
            if( this.shouldAttackPlayer(player) ) {
                this.isAggressive = true;

                if( this.stareTimer == 0 ) {
                    this.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.stare", 1.0F, 1.0F);
                }

                if( this.stareTimer++ == 5 ) {
                    this.stareTimer = 0;
                    this.setScreaming(true);
                    return player;
                }
            } else {
                this.stareTimer = 0;
            }
        }

        return null;
    }

    private boolean shouldAttackPlayer(EntityPlayer player) {
        ItemStack helmetStack = player.inventory.armorInventory[3];

        if( MinecraftForge.EVENT_BUS.post(new EnderFacingEventEsp(player, this)) ) {
            return false;
        } else if( helmetStack != null && helmetStack.getItem() == Item.getItemFromBlock(Blocks.pumpkin) ) {
            return false;
        } else {
            Vec3 lookVecNormal = player.getLook(1.0F).normalize();
            Vec3 positionVec = Vec3.createVectorHelper(this.posX - player.posX, this.boundingBox.minY + (this.height / 2.0F) - (player.posY + player.getEyeHeight()),
                                                       this.posZ - player.posZ);
            double posLength = positionVec.lengthVector();
            positionVec = positionVec.normalize();
            double lookDotProd = lookVecNormal.dotProduct(positionVec);
            return lookDotProd > 1.0D - 0.025D / posLength && player.canEntityBeSeen(this);
        }
    }

    @Override
    public void onLivingUpdate() {
        if( this.isWet() && !this.isImmuneToWater ) {
            this.attackEntityFrom(DamageSource.drown, 1.0F);
        }

        if( this.lastEntityToAttack != this.entityToAttack ) {
            IAttributeInstance moveSpeedAttr = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            moveSpeedAttr.removeModifier(ASB_MODIFIER);

            if( this.entityToAttack != null ) {
                moveSpeedAttr.applyModifier(ASB_MODIFIER);
            }
        }

        this.lastEntityToAttack = this.entityToAttack;

        if( !this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") ) {
            int blockX;
            int blockY;
            int blockZ;
            Block block;

            if( this.getCarryingBlock().getMaterial() == Material.air ) {
                if( this.rand.nextInt(20) == 0 ) {
                    blockX = MathHelper.floor_double(this.posX - 2.0D + this.rand.nextDouble() * 4.0D);
                    blockY = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 3.0D);
                    blockZ = MathHelper.floor_double(this.posZ - 2.0D + this.rand.nextDouble() * 4.0D);
                    block = this.worldObj.getBlock(blockX, blockY, blockZ);

                    if( isCarriable(block) ) {
                        this.setCarryingBlock(block);
                        this.setCarryingData(this.worldObj.getBlockMetadata(blockX, blockY, blockZ));
                        this.worldObj.setBlock(blockX, blockY, blockZ, Blocks.air);
                    }
                }
            } else if( this.rand.nextInt(2000) == 0 ) {
                blockX = MathHelper.floor_double(this.posX - 1.0D + this.rand.nextDouble() * 2.0D);
                blockY = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 2.0D);
                blockZ = MathHelper.floor_double(this.posZ - 1.0D + this.rand.nextDouble() * 2.0D);
                block = this.worldObj.getBlock(blockX, blockY, blockZ);
                Block supportBlock = this.worldObj.getBlock(blockX, blockY - 1, blockZ);

                if( block.getMaterial() == Material.air && supportBlock.getMaterial() != Material.air && supportBlock.renderAsNormalBlock() ) {
                    this.worldObj.setBlock(blockX, blockY, blockZ, this.getCarryingBlock(), this.getCarryingData(), 3);
                    this.setCarryingBlock(Blocks.air);
                }
            }
        }

        this.spawnIdleParticle();

        if( this.isNightActive && this.worldObj.isDaytime() && !this.worldObj.isRemote ) {
            float brightness = this.getBrightness(1.0F);

            if( brightness > 0.5F
                && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))
                && this.rand.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F )
            {
                this.entityToAttack = null;
                this.setScreaming(false);
                this.isAggressive = false;
                teleportRandomly(this);
            }
        }

        if( (this.isWet() && !this.isImmuneToWater) || (this.isBurning() && !this.isImmuneToFire) ) {
            this.entityToAttack = null;
            this.setScreaming(false);
            this.isAggressive = false;
            teleportRandomly(this);
        }

        if( this.isScreaming() && !this.isAggressive && this.rand.nextInt(100) == 0 ) {
            this.setScreaming(false);
        }

        this.isJumping = false;

        if( this.entityToAttack != null ) {
            this.faceEntity(this.entityToAttack, 100.0F, 100.0F);
        }

        if( !this.worldObj.isRemote && this.isEntityAlive() ) {
            if( this.entityToAttack != null ) {
                if( this.entityToAttack instanceof EntityPlayer && this.shouldAttackPlayer((EntityPlayer)this.entityToAttack) ) {
                    if( this.entityToAttack.getDistanceSqToEntity(this) < 16.0D ) {
                        teleportRandomly(this);
                    }

                    this.teleportDelay = 0;
                } else if( this.entityToAttack.getDistanceSqToEntity(this) > 256.0D && this.teleportDelay++ >= 30 && teleportToEntity(this, this.entityToAttack) ) {
                    this.teleportDelay = 0;
                }
            } else {
                this.setScreaming(false);
                this.teleportDelay = 0;
            }
        }

        super.onLivingUpdate();
    }

    protected void spawnIdleParticle() {
        for( int i = 0; i < 2; i++ ) {
            this.worldObj.spawnParticle("portal",
                                        this.posX + (this.rand.nextDouble() - 0.5D) * this.width,
                                        this.posY + this.rand.nextDouble() * this.height - 0.25D,
                                        this.posZ + (this.rand.nextDouble() - 0.5D) * this.width,
                                        (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
        }
    }

    public static boolean teleportRandomly(ITeleportProvider tProvider) {
        EntityLivingBase entity = tProvider.getEntity();
        double d0 = entity.posX + (entity.getRNG().nextDouble() - 0.5D) * 64.0D;
        double d1 = entity.posY + (entity.getRNG().nextInt(64) - 32);
        double d2 = entity.posZ + (entity.getRNG().nextDouble() - 0.5D) * 64.0D;
        return teleportTo(tProvider, d0, d1, d2);
    }

    public static boolean teleportToEntity(ITeleportProvider tProvider, Entity target) {
        EntityLivingBase entity = tProvider.getEntity();
        Vec3 targetVec = Vec3.createVectorHelper(entity.posX - target.posX, entity.boundingBox.minY + (entity.height / 2.0F) - target.posY + target.getEyeHeight(),
                                                 entity.posZ - target.posZ)
                             .normalize();
        double distance = 16.0D;
        double d1 = entity.posX + (entity.getRNG().nextDouble() - 0.5D) * 8.0D - targetVec.xCoord * distance;
        double d2 = entity.posY + (entity.getRNG().nextInt(16) - 8) - targetVec.yCoord * distance;
        double d3 = entity.posZ + (entity.getRNG().nextDouble() - 0.5D) * 8.0D - targetVec.zCoord * distance;
        return teleportTo(tProvider, d1, d2, d3);
    }

    protected static boolean teleportTo(ITeleportProvider tProvider, double x, double y, double z) {
        EntityLivingBase entity = tProvider.getEntity();
        EnderTeleportEvent event = new EnderTeleportEvent(entity, x, y, z, 0);

        if( MinecraftForge.EVENT_BUS.post(event) ) {
            return false;
        }

        double prevPosX = entity.posX;
        double prevPosY = entity.posY;
        double prevPosZ = entity.posZ;
        entity.posX = event.targetX;
        entity.posY = event.targetY;
        entity.posZ = event.targetZ;
        boolean doTeleport = false;
        int blockX = MathHelper.floor_double(entity.posX);
        int blockY = MathHelper.floor_double(entity.posY);
        int blockZ = MathHelper.floor_double(entity.posZ);

        if( entity.worldObj.blockExists(blockX, blockY, blockZ) ) {
            boolean canStandOn = false;

            while( !canStandOn && blockY > 0 ) {
                Block block = entity.worldObj.getBlock(blockX, blockY - 1, blockZ);

                if( block.getMaterial().blocksMovement() ) {
                    canStandOn = true;
                } else {
                    entity.posY--;
                    blockY--;
                }
            }

            if( canStandOn ) {
                entity.setPosition(entity.posX, entity.posY, entity.posZ);

                if( entity.worldObj.getCollidingBoundingBoxes(entity, entity.boundingBox).isEmpty() && !entity.worldObj.isAnyLiquid(entity.boundingBox) ) {
                    doTeleport = true;
                }
            }
        }

        if( !doTeleport ) {
            entity.setPosition(prevPosX, prevPosY, prevPosZ);
            return false;
        } else {
            short range = 128;

            for( int i = 0; i < range; i++ ) {
                double distance = i / (range - 1.0D);
                double partX = prevPosX + (entity.posX - prevPosX) * distance + (entity.getRNG().nextDouble() - 0.5D) * entity.width * 2.0D;
                double partY = prevPosY + (entity.posY - prevPosY) * distance + entity.getRNG().nextDouble() * entity.height;
                double partZ = prevPosZ + (entity.posZ - prevPosZ) * distance + (entity.getRNG().nextDouble() - 0.5D) * entity.width * 2.0D;
                tProvider.spawnParticles(partX, partY, partZ);
            }

            entity.worldObj.playSoundEffect(prevPosX, prevPosY, prevPosZ, "mob.endermen.portal", 1.0F, 1.0F);
            entity.playSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }

    @Override
    protected String getLivingSound() {
        return this.isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
    }

    @Override
    protected String getHurtSound() {
        return "mob.endermen.hit";
    }

    @Override
    protected String getDeathSound() {
        return "mob.endermen.death";
    }

    @Override
    protected Item getDropItem() {
        return Items.ender_pearl;
    }

    @Override
    protected void dropFewItems(boolean playerHit, int lootLevel) {
        Item dropItem = this.getDropItem();

        if( dropItem != null ) {
            int count = this.rand.nextInt(2 + lootLevel);

            for( int i = 0; i < count; i++ ) {
                this.dropItem(dropItem, 1);
            }
        }
    }

    public void setCarryingBlock(Block block) {
        this.dataWatcher.updateObject(DW_CARRYING_BLOCK, (short) (Block.getIdFromBlock(block) & 4095));
    }

    public Block getCarryingBlock() {
        return Block.getBlockById(this.dataWatcher.getWatchableObjectShort(DW_CARRYING_BLOCK));
    }

    public void setCarryingData(int meta) {
        this.dataWatcher.updateObject(DW_CARRYING_META, (byte) (meta & 255));
    }

    public int getCarryingData() {
        return this.dataWatcher.getWatchableObjectByte(DW_CARRYING_META);
    }

    public boolean attackEntityFrom(DamageSource source, float damage) {
        if( this.isEntityInvulnerable() ) {
            return false;
        } else {
            this.setScreaming(true);

            if( source instanceof EntityDamageSource && source.getEntity() instanceof EntityPlayer ) {
                this.isAggressive = true;
            }

            if( source instanceof EntityDamageSourceIndirect ) {
                this.isAggressive = false;

                for( int i = 0; i < 64; i++ ) {
                    if( teleportRandomly(this) ) {
                        return true;
                    }
                }

                return super.attackEntityFrom(source, damage);
            } else {
                return super.attackEntityFrom(source, damage);
            }
        }
    }

    public boolean isScreaming() {
        return this.dataWatcher.getWatchableObjectByte(DW_SCREAM_FLAG) > 0;
    }

    public void setScreaming(boolean isScreaming) {
        this.dataWatcher.updateObject(DW_SCREAM_FLAG, (byte) (isScreaming ? 1 : 0));
    }

    public void initCarriables() {
        setCarriable(Blocks.grass, true);
        setCarriable(Blocks.dirt, true);
        setCarriable(Blocks.sand, true);
        setCarriable(Blocks.gravel, true);
        setCarriable(Blocks.yellow_flower, true);
        setCarriable(Blocks.red_flower, true);
        setCarriable(Blocks.brown_mushroom, true);
        setCarriable(Blocks.red_mushroom, true);
        setCarriable(Blocks.tnt, true);
        setCarriable(Blocks.cactus, true);
        setCarriable(Blocks.clay, true);
        setCarriable(Blocks.pumpkin, true);
        setCarriable(Blocks.melon_block, true);
        setCarriable(Blocks.mycelium, true);
    }

    public void setCarriable(Block block, boolean canCarry) {
        this.carriable.put(block, canCarry);
    }

    public boolean isCarriable(Block block) {
        Boolean ret = this.carriable.get(block);
        return ret != null ? ret : false;
    }

    @Override
    public void spawnParticles(double x, double y, double z) {
        float clrR = (this.rand.nextFloat() - 0.5F) * 0.2F;
        float clrG = (this.rand.nextFloat() - 0.5F) * 0.2F;
        float clrB = (this.rand.nextFloat() - 0.5F) * 0.2F;

        this.worldObj.spawnParticle("portal", x, y, z, clrR, clrG, clrB);
    }

    @Override
    public AEntityEndermanEsp getEntity() {
        return this;
    }

    @Cancelable
    public static class EnderFacingEventEsp extends PlayerEvent {
        public final AEntityEndermanEsp endermanEsp;

        public EnderFacingEventEsp(EntityPlayer player, AEntityEndermanEsp entityEndermanEsp) {
            super(player);
            this.endermanEsp = entityEndermanEsp;
        }
    }
}
