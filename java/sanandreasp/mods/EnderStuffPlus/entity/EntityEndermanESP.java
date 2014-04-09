package sanandreasp.mods.EnderStuffPlus.entity;

import java.util.UUID;

import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EntityEndermanESP
    extends EntityMob
{
    private static final AttributeModifier SPEED_ATTACK =
            (new AttributeModifier(UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0"),
                                   "Attacking speed boost", 6.199999809265137D, 0)).setSaved(false);

    protected static boolean[] carriableBlocks = new boolean[Block.blocksList.length];

    protected static final int TP_ATTACK = 0;
    protected static final int TP_DAYTIME = 2;
    protected static final int TP_LIQUID = 1;
    protected static final int TP_PROJECTILE = 3;
    protected boolean calmInDaylight = true;
    protected boolean canSpawn = false;

    protected boolean hasTarget;

    protected boolean isImmuneToWater = false;
    protected int jarOpeningCounter;
    protected Entity prevTarget;
    protected int teleportDelay;

    static {
        carriableBlocks[Block.grass.blockID] = true;
        carriableBlocks[Block.dirt.blockID] = true;
        carriableBlocks[Block.sand.blockID] = true;
        carriableBlocks[Block.gravel.blockID] = true;
        carriableBlocks[Block.plantYellow.blockID] = true;
        carriableBlocks[Block.plantRed.blockID] = true;
        carriableBlocks[Block.mushroomBrown.blockID] = true;
        carriableBlocks[Block.mushroomRed.blockID] = true;
        carriableBlocks[Block.tnt.blockID] = true;
        carriableBlocks[Block.cactus.blockID] = true;
        carriableBlocks[Block.blockClay.blockID] = true;
        carriableBlocks[Block.pumpkin.blockID] = true;
        carriableBlocks[Block.melon.blockID] = true;
        carriableBlocks[Block.mycelium.blockID] = true;
    }

    public EntityEndermanESP(World world) {
        super(world);
        this.setSize(0.6F, 2.9F);
        this.stepHeight = 1.0F;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.30000001192092896D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(7.0D);
    }

    @Override
    public boolean attackEntityFrom(DamageSource dmgSource, float attackPts) {
        if( dmgSource.equals(DamageSource.drown) && this.isImmuneToWater ) {
            return false;
        }

        if( this.isEntityInvulnerable() ) {
            return false;
        } else {
            this.setScreaming(true);

            if( dmgSource instanceof EntityDamageSource && dmgSource.getEntity() instanceof EntityPlayer ) {
                this.hasTarget = true;
            }

            if( dmgSource instanceof EntityDamageSourceIndirect ) {
                this.hasTarget = false;

                for( int i = 0; i < 64; ++i ) {
                    if( this.teleportRandomly(TP_PROJECTILE) ) {
                        return true;
                    }
                }

                return super.attackEntityFrom(dmgSource, attackPts);
            } else {
                return super.attackEntityFrom(dmgSource, attackPts);
            }
        }
    }

    @Override
    protected void dropFewItems(boolean recentlyHit, int fortuneLvl) {
        int itemId = this.getDropItemId();

        if( itemId > 0 ) {
            int k = this.rand.nextInt(2 + fortuneLvl);

            for( int l = 0; l < k; ++l ) {
                this.entityDropItem(new ItemStack(itemId, 1, this.getDamageDropped()), 0.0F);
            }
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte) 0));
        this.dataWatcher.addObject(17, new Byte((byte) 0));
        this.dataWatcher.addObject(18, new Byte((byte) 0));
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer player = this.worldObj.getClosestVulnerablePlayerToEntity(this, 64.0D);

        if( player != null ) {
            if( this.shouldAttackPlayer(player) ) {
                this.hasTarget = true;

                if( this.jarOpeningCounter == 0 ) {
                    this.worldObj.playSoundAtEntity(player, this.getScreamSound(), 1.0F, 1.0F);
                }

                if( this.jarOpeningCounter++ == 5 ) {
                    this.jarOpeningCounter = 0;
                    this.setScreaming(true);
                    return player;
                }
            } else {
                this.jarOpeningCounter = 0;
            }
        }

        return null;
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() || this.canSpawn;
    }

    public int getCarried() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    public int getCarryingData() {
        return this.dataWatcher.getWatchableObjectByte(17);
    }

    protected int getDamageDropped() {
        return 0;
    }

    @Override
    protected String getDeathSound() {
        return "mob.endermen.death";
    }

    @Override
    protected int getDropItemId() {
        return Item.enderPearl.itemID;
    }

    @Override
    protected String getHurtSound() {
        return "mob.endermen.hit";
    }

    @Override
    protected String getLivingSound() {
        return this.isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
    }

    protected String getPortalSound() {
        return "mob.endermen.portal";
    }

    protected String getScreamSound() {
        return "mob.endermen.stare";
    }

    public boolean isScreaming() {
        return this.dataWatcher.getWatchableObjectByte(18) > 0;
    }

    @Override
    public void onLivingUpdate() {
        if( this.isWet() && !this.isImmuneToWater ) {
            this.attackEntityFrom(DamageSource.drown, 1.0F);
        }

        if( this.prevTarget != this.entityToAttack ) {
            AttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            attributeinstance.removeModifier(SPEED_ATTACK);

            if( this.entityToAttack != null ) {
                attributeinstance.applyModifier(SPEED_ATTACK);
            }
        }

        this.prevTarget = this.entityToAttack;

        if( !this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") && ConfigRegistry.griefing ) {
            int x;
            int y;
            int z;
            int blockId;

            if( this.getCarried() == 0 ) {
                if( this.rand.nextInt(20) == 0 ) {
                    x = MathHelper.floor_double(this.posX - 2.0D + this.rand.nextDouble() * 4.0D);
                    y = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 3.0D);
                    z = MathHelper.floor_double(this.posZ - 2.0D + this.rand.nextDouble() * 4.0D);
                    blockId = this.worldObj.getBlockId(x, y, z);

                    if( carriableBlocks[blockId] ) {
                        this.setCarried(this.worldObj.getBlockId(x, y, z));
                        this.setCarryingData(this.worldObj.getBlockMetadata(x, y, z));
                        this.worldObj.setBlock(x, y, z, 0);
                    }
                }
            } else if( this.rand.nextInt(2000) == 0 ) {
                x = MathHelper.floor_double(this.posX - 1.0D + this.rand.nextDouble() * 2.0D);
                y = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 2.0D);
                z = MathHelper.floor_double(this.posZ - 1.0D + this.rand.nextDouble() * 2.0D);
                blockId = this.worldObj.getBlockId(x, y, z);
                int i1 = this.worldObj.getBlockId(x, y - 1, z);

                if( blockId == 0 && i1 > 0 && Block.blocksList[i1].renderAsNormalBlock() ) {
                    this.worldObj.setBlock(x, y, z, this.getCarried(), this.getCarryingData(), 3);
                    this.setCarried(0);
                }
            }
        }

        this.spawnParticle("livingUpd", this.posX, this.posY, this.posZ, 0.0F, 0.0F, 0.0F);

        if( this.worldObj.isDaytime() && !this.worldObj.isRemote && this.calmInDaylight ) {
            float bright = this.getBrightness(1.0F);

            if( bright > 0.5F
                && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY),
                                                   MathHelper.floor_double(this.posZ))
                && this.rand.nextFloat() * 30.0F < (bright - 0.4F) * 2.0F ) {
                this.entityToAttack = null;
                this.setScreaming(false);
                this.hasTarget = false;
                this.teleportRandomly(EntityEndermanESP.TP_DAYTIME);
            }
        }

        if( (this.isWet() && !this.isImmuneToWater) || (this.isBurning() && !this.isImmuneToFire) ) {
            this.entityToAttack = null;
            this.setScreaming(false);
            this.hasTarget = false;
            this.teleportRandomly(EntityEndermanESP.TP_LIQUID);
        }

        if( this.isScreaming() && !this.hasTarget && this.rand.nextInt(100) == 0 ) {
            this.setScreaming(false);
        }

        this.isJumping = false;

        if( this.entityToAttack != null ) {
            this.faceEntity(this.entityToAttack, 100.0F, 100.0F);
        }

        if( !this.worldObj.isRemote && this.isEntityAlive() ) {
            if( this.entityToAttack != null ) {
                if( this.entityToAttack instanceof EntityPlayer && this.shouldAttackPlayer((EntityPlayer) this.entityToAttack) ) {
                    if( this.entityToAttack.getDistanceSqToEntity(this) < 16.0D ) {
                        this.teleportRandomly(EntityEndermanESP.TP_ATTACK);
                    }

                    this.teleportDelay = 0;
                } else if( this.entityToAttack.getDistanceSqToEntity(this) > 256.0D && this.teleportDelay++ >= 30
                           && this.teleportToEntity(EntityEndermanESP.TP_ATTACK, this.entityToAttack) )
                {
                    this.teleportDelay = 0;
                }
            } else {
                this.setScreaming(false);
                this.teleportDelay = 0;
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setCarried(nbt.getShort("carried"));
        this.setCarryingData(nbt.getShort("carriedData"));
    }

    public void setCarried(int blockId) {
        this.dataWatcher.updateObject(16, Byte.valueOf((byte) (blockId & 255)));
    }

    public void setCarryingData(int meta) {
        this.dataWatcher.updateObject(17, Byte.valueOf((byte) (meta & 255)));
    }

    public void setScreaming(boolean isScream) {
        this.dataWatcher.updateObject(18, Byte.valueOf((byte) (isScream ? 1 : 0)));
    }

    protected boolean shouldAttackPlayer(EntityPlayer player) {
        if( ESPModRegistry.hasPlayerFullNiob(player) ) {
            return false;
        }

        ItemStack stack = player.inventory.armorInventory[3];

        if( stack != null && stack.itemID == Block.pumpkin.blockID ) {
            return false;
        } else {
            Vec3 lookVec = player.getLook(1.0F).normalize();
            Vec3 vector = this.worldObj.getWorldVec3Pool()
                                      .getVecFromPool(this.posX - player.posX, this.boundingBox.minY  + this.height / 2.0F
                                                                               - (player.posY + player.getEyeHeight()),
                                                      this.posZ - player.posZ);
            double lengthVec = vector.lengthVector();
            vector = vector.normalize();
            double dotProd = lookVec.dotProduct(vector);
            return dotProd > 1.0D - 0.025D / lengthVec ? player.canEntityBeSeen(this) : false;
        }
    }

    public void spawnParticle(String type, double X, double Y, double Z, float dataI, float dataII, float dataIII) {
        ESPModRegistry.sendPacketAllRng("fxPortal", this.posX, this.posY, this.posZ, 128.0D, this.dimension, this.posX,
                                        this.posY, this.posZ, dataI, dataII, dataIII, this.width, this.height, 1);
    }

    protected boolean teleportRandomly(int cause) {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.posY + (this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.teleportTo(cause, d0, d1, d2);
    }

    protected boolean teleportTo(int cause, double par1, double par3, double par5) {
        EnderTeleportEvent event = new EnderTeleportEvent(this, par1, par3, par5, 0);
        if( MinecraftForge.EVENT_BUS.post(event) ) {
            return false;
        }

        double d3 = this.posX;
        double d4 = this.posY;
        double d5 = this.posZ;
        this.posX = event.targetX;
        this.posY = event.targetY;
        this.posZ = event.targetZ;
        boolean flag = false;
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posY);
        int k = MathHelper.floor_double(this.posZ);
        int l;

        if( this.worldObj.blockExists(i, j, k) ) {
            boolean flag1 = false;

            while( !flag1 && j > 0 ) {
                l = this.worldObj.getBlockId(i, j - 1, k);

                if( l != 0 && Block.blocksList[l].blockMaterial.blocksMovement() ) {
                    flag1 = true;
                } else {
                    --this.posY;
                    --j;
                }
            }

            if( flag1 ) {
                this.setPosition(this.posX, this.posY, this.posZ);

                if( this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()
                    && (!this.worldObj.isAnyLiquid(this.boundingBox) || this.isImmuneToWater) ) {
                    flag = true;
                }
            }
        }

        if( !flag ) {
            this.setPosition(d3, d4, d5);
            return false;
        } else {
            short short1 = 128;

            for( l = 0; l < short1; ++l ) {
                double d6 = l / (short1 - 1.0D);
                double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
                double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * this.height;
                double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
                this.spawnParticle("teleport", d7, d8, d9, 0.0F, 0.0F, 0.0F);
            }

            this.worldObj.playSoundEffect(d3, d4, d5, this.getPortalSound(), 1.0F, 1.0F);
            this.playSound(this.getPortalSound(), 1.0F, 1.0F);
            return true;
        }
    }

    protected boolean teleportToEntity(int cause, Entity par1Entity) {
        Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - par1Entity.posX,
                                                                    this.boundingBox.minY + this.height / 2.0F
                                                                            - par1Entity.posY
                                                                            + par1Entity.getEyeHeight(),
                                                                    this.posZ - par1Entity.posZ);
        vec3 = vec3.normalize();
        double d0 = 16.0D;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
        double d2 = this.posY + (this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
        return this.teleportTo(cause, d1, d2, d3);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("carried", (short) this.getCarried());
        par1NBTTagCompound.setShort("carriedData", (short) this.getCarryingData());
    }
}
