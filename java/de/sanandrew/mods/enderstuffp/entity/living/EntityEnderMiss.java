package de.sanandrew.mods.enderstuffp.entity.living;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.core.manpack.util.javatuples.Unit;
import de.sanandrew.mods.enderstuffp.item.ItemRaincoat;
import de.sanandrew.mods.enderstuffp.util.*;
import de.sanandrew.mods.enderstuffp.util.manager.ReflectionManager;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager.CoatBaseEntry;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager.CoatColorEntry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

public class EntityEnderMiss
        extends EntityCreature
        implements IEnderPet<EntityEnderMiss>, IEnderCreature
{
    private static final AttributeModifier SPEED_TAMED = (new AttributeModifier(UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0"),
                                                                                "Tamerfollowing speed boost", 6.2D, 0)).setSaved(false);
    private static final int DW_BOOLEANS = 20;
    private static final int DW_BOW_CLR = 21;
    private static final int DW_COAT = 22;

    private static final ItemStack EMPTY_COAT_SLOT = new ItemStack(Items.golden_shovel);

    private int jumpTicks = 0;
    private ItemStack prevCoatBase = null;

    /**
     * cache for the getOwningPlayer method to prevent iterating through the entire players list when getting the owning player every time
     **/
    private EntityPlayer ownerInst = null;
    private UUID ownerUUID = null;
    private int teleportTimer = 0;

    public EntityEnderMiss(World world) {
        super(world);

        this.stepHeight = 1.0F;

        this.setSize(0.6F, 2.9F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }

    @Override
    protected void attackEntity(Entity entity, float distance) {}

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean attackEntityFrom(DamageSource dmgSource, float dmg) {
        if( !this.worldObj.isRemote ) {
            this.setSitting(false);
        }

        if( this.isRidden() && dmgSource != null && dmgSource.getEntity() instanceof EntityPlayer || dmgSource == null ) {
            return false;
        }

        if( dmgSource instanceof EntityDamageSourceIndirect && !this.isTamed() ) {
            for( int i = 0; i < 64; ++i ) {
                if( this.teleportRandomly() ) {
                    return false;
                }
            }

            return false;
        } else if( this.isTamed() && dmgSource == DamageSource.fall && this.hasAvisFeather() ) {
            return false;
        } else {
            if( dmgSource.getEntity() != null && dmgSource.getEntity() instanceof EntityPlayer && !this.isTamed() ) {
                List<Entity> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, (this.boundingBox.copy()).expand(16.0D, 16.0D, 16.0D));

                for( Entity entity : entities ) {
                    if( entity instanceof EntityEnderman ) {
                        ((EntityEnderman) entity).setTarget(dmgSource.getEntity());
                        ((EntityEnderman) entity).setScreaming(true);
                    }
                }
            }

            return super.attackEntityFrom(dmgSource, dmg);
        }
    }

    @Override
    public boolean canBeSteered() {
        return this.isRidden();
    }

    @Override
    protected boolean canDespawn() {
        return !this.isTamed();
    }

    public boolean hasAvisFeather() {
        return (this.dataWatcher.getWatchableObjectByte(DW_BOOLEANS) & 16) == 16;
    }

    @Override
    protected void dropFewItems(boolean hitByPlyr, int lootLvl) {
        Item itemId = this.getDropItem();

        if( itemId != null && !this.isTamed() ) {
            int k = this.rand.nextInt(2 + lootLvl);

            for( int l = 0; l < k; ++l ) {
                this.entityDropItem(new ItemStack(itemId, 1, 2), 0.0F);
            }
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(DW_BOW_CLR, (byte) this.rand.nextInt(ItemDye.field_150922_c.length));
        this.dataWatcher.addObject(DW_BOOLEANS, (byte) 0);
        this.dataWatcher.addObject(DW_COAT, EMPTY_COAT_SLOT);

        this.setSpecial(this.rand.nextInt(16) == 0);
//        this.setAvisFeather(true);

        if( this.rand.nextInt(2) == 0 ) {
            NBTTagCompound nbt = new NBTTagCompound();

            List<String> bases = RaincoatManager.getBaseList();
            List<String> colors = RaincoatManager.getColorList();
            nbt.setString("base", bases.get(rand.nextInt(bases.size())));
            nbt.setString("color", colors.get(rand.nextInt(colors.size())));
            ItemStack stack = new ItemStack(EspItems.rainCoat, 1, 0);
            stack.setTagCompound(nbt);
            this.setCoat(stack);
        }
    }

    @Override
    protected void fall(float fallDist) {
        if( !this.hasAvisFeather() ) {
            super.fall(fallDist);
        }
    }

    @Override
    protected Entity findPlayerToAttack() {
        return null;
    }

    @Override
    public float getAIMoveSpeed() {
        return this.isSitting() ? 0.0F : (this.isRidden() ? 0.2F + (this.getCoatBase() == RaincoatManager.baseGold ? 0.05F : 0.0F) : 0.1F);
    }

    @Override
    public float getBlockPathWeight(int x, int y, int z) {
        return 0.5F - this.worldObj.getLightBrightness(x, y, z) * (this.isTamed() ? -1 : 1);
    }

    public float[] getBowColorArr() {
        int color = ItemDye.field_150922_c[this.getBowColor()];

        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;

        return new float[] { red, green, blue };
    }

    public int getBowColor() {
        return this.dataWatcher.getWatchableObjectByte(DW_BOW_CLR);
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.isValidLightLevel() && super.getCanSpawnHere();
    }

    public ItemStack getCoat() {
        return this.dataWatcher.getWatchableObjectItemStack(DW_COAT);
    }

    public boolean hasCoat() {
        return this.getCoat().hasTagCompound() && this.getCoat().getItem() == EspItems.rainCoat;
    }

    public boolean isCoatApplicable(ItemStack coat) {
        if( !this.hasCoat() && coat.getItem() instanceof ItemRaincoat ) {
            return true;
        } else if( coat.hasTagCompound() ) {
            String base = coat.getTagCompound().getString("base");
            String color = coat.getTagCompound().getString("color");

            return !this.getCoatColor().getUUID().equals(color) || !this.getCoatBase().getUUID().equals(base);
        }

        return false;
    }

    @Override
    public CoatBaseEntry getCoatBase() {
        return this.hasCoat() ? RaincoatManager.getBase(this.getCoat().getTagCompound().getString("base")) : RaincoatManager.NULL_BASE;
    }

    @Override
    public CoatColorEntry getCoatColor() {
        return this.hasCoat() ? RaincoatManager.getColor(this.getCoat().getTagCompound().getString("color")) : RaincoatManager.NULL_COLOR;
    }

    @Override
    protected String getDeathSound() {
        return EnderStuffPlus.MOD_ID + ":mob.endermiss.death";
    }

    @Override
    protected Item getDropItem() {
        return EspItems.espPearls;
    }

    @Override
    public EntityEnderMiss getEntity() {
        return this;
    }

    @Override
    public ItemStack getHeldItem() {
        return this.isTamed() ? new ItemStack(Blocks.yellow_flower) : new ItemStack(Blocks.red_flower);
    }

    @Override
    protected String getHurtSound() {
        return EnderStuffPlus.MOD_ID + ":mob.endermiss.hurt";
    }

    @Override
    protected String getLivingSound() {
        return EnderStuffPlus.MOD_ID + ":mob.endermiss.idle";
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() + (this.isRidden() ? 0.8F : 0.0F);
    }

    @Override
    public String getName() {
        return this.getCustomNameTag();
    }

    @SuppressWarnings("unchecked")
    private EntityPlayer getOwningPlayerInRng(float distance) {
        if( !this.worldObj.isRemote && this.isTamed() ) {
            if( this.ownerInst == null || !this.ownerInst.getGameProfile().getId().equals(this.ownerUUID) ) {
                for( EntityPlayer currPlayer : (Iterable<EntityPlayer>) this.worldObj.playerEntities ) {
                    if( this.ownerUUID.equals(currPlayer.getGameProfile().getId()) ) {
                        if( currPlayer.isDead ) {
                            this.ownerInst = null;
                            return null;
                        }

                        this.ownerInst = currPlayer;

                        if( this.getDistanceToEntity(currPlayer) < distance ) {
                            return currPlayer;
                        } else {
                            return null;
                        }
                    }
                }
            } else {
                return this.ownerInst;
            }
        }

        return null;
    }

    @Override
    public boolean interact(EntityPlayer player) {
        if( !worldObj.isRemote ) {
            if( player.getCurrentEquippedItem() != null ) {
                ItemStack playerItem = player.getCurrentEquippedItem();

                if( SAPUtils.areItemInstEqual(playerItem, Blocks.yellow_flower) && (!this.isTamed() || this.ownerUUID == null) ) {
                    if( this.rand.nextInt(10) == 0 ) {
                        this.setTamed(true);
                        this.ownerUUID = player.getGameProfile().getId();

                        EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_TAME, this.posX, this.posY, this.posZ, this.dimension, null);
                        playerItem.stackSize--;

                        return true;
                    } else {
                        EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_REJECT, this.posX, this.posY, this.posZ, this.dimension, null);
                        playerItem.stackSize--;

                        return true;
                    }
                } else if( this.isTamed() && !this.isRidden() ) {
                    if( !this.worldObj.isRemote && this.ownerUUID.equals(player.getGameProfile().getId()) ) {
                        if( this.isCoatApplicable(playerItem) ) {
                            if( this.hasCoat() && !player.capabilities.isCreativeMode ) {
                                player.inventory.addItemStackToInventory(this.getCoat().copy());
                            }

                            player.inventoryContainer.detectAndSendChanges();
                            ItemStack coatItem = playerItem.copy();
                            coatItem.stackSize = 1;
                            this.setCoat(coatItem);
                            playerItem.stackSize--;

                            return true;
                        } else if( SAPUtils.areItemInstEqual(playerItem, EspItems.avisFeather) && !this.hasAvisFeather() ) {
                            this.setAvisFeather(true);
                            playerItem.stackSize--;

                            return true;
                        } else if( SAPUtils.areItemInstEqual(playerItem, Items.dye) && playerItem.getItemDamage() != this.getBowColor() ) {
                            this.setBowColor(playerItem.getItemDamage());
                            playerItem.stackSize--;

                            return true;
                        } else if( playerItem.getItem() instanceof ItemFood && this.getHealth() < this.getMaxHealth() ) {
                            this.heal(((ItemFood) playerItem.getItem()).func_150905_g(playerItem));
                            EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_TAME, this.posX, this.posY, this.posZ, this.dimension, null);
                            playerItem.stackSize--;

                            return true;
                        } else if( SAPUtils.areItemInstEqual(playerItem, EspItems.enderPetStaff) ) {
                            EnderStuffPlus.proxy.openGui(player, EnumGui.ENDERPET, this.getEntityId(), 0, 0);

                            return true;
                        }
                    }
                }
            } else if( this.isTamed() ) {
                if( this.ownerUUID.equals(player.getGameProfile().getId()) ) {
                    String s1 = String.format("%s[%s]%s %s", EnumChatFormatting.LIGHT_PURPLE, SAPUtils.translate("entity." + EnderStuffPlus.MOD_ID + ".EnderMiss.name"),
                                              EnumChatFormatting.WHITE, SAPUtils.translate(EnderStuffPlus.MOD_ID + ".chat.name")
                    );
                    String s2 = this.getName().isEmpty() ? EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET
                                                         : this.getName();
                    player.addChatMessage(new ChatComponentText(String.format(s1, s2)));

                    int percHealth = (int) (this.getHealth() / this.getMaxHealth() * 100.0F);
                    player.addChatMessage(new ChatComponentText("  " + SAPUtils.translatePostFormat(EnderStuffPlus.MOD_ID + ".chat.missFriend", percHealth)));
                } else {
                    String s = SAPUtils.translate(EnderStuffPlus.MOD_ID + ".chat.stranger.msgCount");
                    if( StringUtils.isNumeric(s) ) {
                        int cnt = Integer.parseInt(s);
                        player.addChatMessage(new ChatComponentText(SAPUtils.translate(EnderStuffPlus.MOD_ID + ".chat.stranger." + (this.rand.nextInt(cnt) + 1))));
                    }
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isFollowing() {
        return (this.dataWatcher.getWatchableObjectByte(DW_BOOLEANS) & 64) == 64;
    }

    @Override
    protected boolean isMovementCeased() {
        return this.isSitting();
    }

    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return this.getCoatBase() != RaincoatManager.baseNiob || !Potion.potionTypes[effect.getPotionID()].isBadEffect();
    }

    public boolean isRidden() {
        return this.isTamed() && this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer;
    }

    @Override
    public boolean isSitting() {
        try {
            return (this.dataWatcher.getWatchableObjectByte(DW_BOOLEANS) & 4) == 4;
        } catch( NullPointerException e ) {
            return false;
        }
    }

    public boolean isSpecial() {
        return (this.dataWatcher.getWatchableObjectByte(DW_BOOLEANS) & 8) == 8;
    }

    public boolean isTamed() {
        return (this.dataWatcher.getWatchableObjectByte(DW_BOOLEANS) & 1) == 1;
    }

    protected boolean isValidLightLevel() {
        int x = MathHelper.floor_double(this.posX);
        int y = MathHelper.floor_double(this.boundingBox.minY);
        int z = MathHelper.floor_double(this.posZ);

        if( this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, x, y, z) > this.rand.nextInt(32) ) {
            return false;
        } else {
            int blockLight = this.worldObj.getBlockLightValue(x, y, z);

            if( this.worldObj.isThundering() ) {
                int skyLight = this.worldObj.skylightSubtracted;

                this.worldObj.skylightSubtracted = 10;
                blockLight = this.worldObj.getBlockLightValue(x, y, z);
                this.worldObj.skylightSubtracted = skyLight;
            }

            return blockLight <= this.rand.nextInt(8);
        }
    }

    @Override
    protected void jump() {
        if( !this.isSitting() && this.isJumping ) {
            this.motionY = 0.41999998688697815D * (this.isRidden() ? 1.4D : 1.0D);

            if( this.isPotionActive(Potion.jump) ) {
                this.motionY += (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
            }

            if( this.getCoatBase() == RaincoatManager.baseRedstone ) {
                this.motionY += 0.1F;
            }

            if( this.isSprinting() ) {
                float rotYaw = this.rotationYaw * 0.017453292F;

                this.motionX -= MathHelper.sin(rotYaw) * 0.2F;
                this.motionZ += MathHelper.cos(rotYaw) * 0.2F;
            }

            this.isAirBorne = true;
        }

        this.isJumping = false;
    }

    @Override
    public void moveEntity(double x, double y, double z) {
        float hWidth = this.width / 2.0F;
        float yOff = (float) this.getMountedYOffset() + (this.isRidden() ? 1.25F : 0.5F) + this.yOffset;

        this.boundingBox.setBounds(this.posX - hWidth, this.posY - this.yOffset + this.ySize, this.posZ - hWidth,
                                   this.posX + hWidth, this.posY - this.yOffset + this.ySize + yOff, this.posZ + hWidth);

        super.moveEntity(x, y, z);

        yOff = this.height;

        this.boundingBox.setBounds(this.posX - hWidth, this.posY - this.yOffset + this.ySize, this.posZ - hWidth,
                                   this.posX + hWidth, this.posY - this.yOffset + this.ySize + yOff, this.posZ + hWidth);
    }

    public boolean needFood() {
        return this.getHealth() < (this.getMaxHealth() / 100.0F) * 20.0F;
    }

    @Override
    public void onLivingUpdate() {
        if( this.jumpTicks > 0 ) {
            this.jumpTicks--;
        }

        if( !this.worldObj.isRemote ) {
            if( this.prevCoatBase != this.getCoat() ) {
                if( this.hasCoat() && RaincoatManager.getBase(this.getCoat().getTagCompound().getString("base")) == RaincoatManager.baseObsidian ) {
                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
                } else {
                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
                    this.setHealth(Math.min(this.getHealth(), this.getMaxHealth()));
                }

                this.prevCoatBase = this.getCoat();
            }
        }

        if( this.teleportTimer > 0 ) {
            this.teleportTimer--;
        }

        if( this.isRidden() && this.riddenByEntity.isDead && this.isTamed() && !this.worldObj.isRemote ) {
            this.setSitting(true);
        }

        if( this.isSitting() ) {
            this.height = 0.9F;
        } else {
            this.height = 2.9F;
        }

        EntityPlayer ep = this.getOwningPlayerInRng(25.0F);

        IAttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

        attributeinstance.removeModifier(SPEED_TAMED);

        if( this.isTamed() && !this.isSitting() && !this.isRidden() && ep != null && this.getDistanceToEntity(ep) > 2.0F && this.isFollowing() ) {
            this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 24.0F, false, false, !this.hasCoat(), !this.hasCoat()));
            attributeinstance.applyModifier(SPEED_TAMED);

            if( this.getDistanceToEntity(ep) > 10.0F && this.teleportTimer <= 0 && Math.abs(ep.posY - this.posY) < 6.0F ) {
                this.teleportToEntity(ep);
            }
        } else if( this.isTamed() && !this.isRidden() && ep != null && ep.getCurrentEquippedItem() != null
                   && ep.getCurrentEquippedItem().getItem() instanceof ItemFood && this.getDistanceToEntity(ep) > 2.0F && this.needFood() && !this.isSitting() )
        {
            attributeinstance.applyModifier(SPEED_TAMED);
            this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 8.0F, false, false, !this.hasCoat(), !this.hasCoat()));
        }

        if( this.isWet() && !this.hasCoat() ) {
            this.attackEntityFrom(DamageSource.drown, 1);
        }

        this.entityToAttack = null;

        EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_MISS_BODY, this.posX, this.posY, this.posZ, this.dimension, Unit.with(this.isSitting()));

        if( this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isTamed() ) {
            float bright = this.getBrightness(1.0F);

            if( bright > 0.5F
                && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))
                && this.rand.nextFloat() * 30.0F < (bright - 0.4F) * 2.0F )
            {
                this.teleportRandomly();
            }
        }

        if( this.isWet() && !this.hasCoat() && !this.isTamed() ) {
            this.teleportRandomly();
        }

        if( !this.isSitting() ) {
            this.updateArmSwingProgress();
        }

        super.onLivingUpdate();

        if( this.isRidden() ) {
            attributeinstance.applyModifier(SPEED_TAMED);

            this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 5.0F;
            EntityPlayer player = (EntityPlayer) this.riddenByEntity;
            this.rotationYawHead = player.rotationYawHead;

            this.setRotation(player.rotationYaw, 0.0F);
            this.moveStrafing = player.moveStrafing;
            this.moveForward = player.moveForward;
            this.isJumping = ReflectionManager.isLivingJumping(player);

            this.stepHeight = 1.0F;
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        this.setTamed(nbt.getBoolean("tamed"));

        if( nbt.hasKey(EnumEnderPetEggInfo.NBT_COAT) ) {
            this.setCoat(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(EnumEnderPetEggInfo.NBT_COAT)));
        } else {
            this.setCoat(null);
        }

        this.setAvisFeather(nbt.getBoolean(EnumEnderPetEggInfo.NBT_MISS_AVISFEATHER));
        this.setSitting(nbt.getBoolean("sitting"));
        this.setSpecial(nbt.getBoolean(EnumEnderPetEggInfo.NBT_MISS_SPECIAL));
        this.setFollowing(nbt.getBoolean("follow"));
        if( nbt.hasKey("bowColor") ) {
            this.setBowColor(nbt.getByte("bowColor"));
        }

        if( nbt.hasKey("owner") ) {
            this.ownerUUID = UUID.fromString(nbt.getString("owner"));
        }

        if( nbt.hasKey("petName") ) {
            this.setName(nbt.getString("petName"));
        }

        if( this.getName().equals(EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET) ) {
            this.setName("");
        }
    }

    private void setBoolean(boolean b, int flag, int dwId) {
        byte prevByte = this.dataWatcher.getWatchableObjectByte(dwId);
        this.dataWatcher.updateObject(dwId, (byte) (b ? prevByte | flag : prevByte & ~flag));
    }

    public void setAvisFeather(boolean b) {
        this.setBoolean(b, 16, DW_BOOLEANS);
    }

    public void setCoat(ItemStack stack) {
        if( stack == null || stack.getItem() != EspItems.rainCoat ) {
            stack = EMPTY_COAT_SLOT;
        }

        this.dataWatcher.updateObject(DW_COAT, stack);
    }

    public void setBowColor(int clr) {
        this.dataWatcher.updateObject(DW_BOW_CLR, (byte) clr);
    }

    @Override
    public void setFollowing(boolean b) {
        this.setBoolean(b, 64, DW_BOOLEANS);
    }

    @Override
    public void setName(String name) {
        this.setCustomNameTag(name);
    }

    @Override
    public void setSitting(boolean b) {
        this.setBoolean(b, 4, DW_BOOLEANS);
    }

    public void setSpecial(boolean b) {
        this.setBoolean(b, 8, DW_BOOLEANS);
    }

    @Override
    public void setTamed(boolean tame) {
        this.setBoolean(tame, 1, DW_BOOLEANS);
    }

    @Override
    public boolean shouldRiderFaceForward(EntityPlayer player) {
        return true;
    }

//    public void spawnParticle(String type, double X, double Y, double Z) {
//        ESPModRegistry.sendPacketAllRng("fxPortal", this.posX, this.posY, this.posZ, 128.0D, this.dimension, this.posX,
//                                        this.posY, this.posZ, 1.0F, 0.5F, 0.7F, this.width, this.height, 1);
//    }

    protected boolean teleportRandomly() {
        double var1 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double var3 = this.posY + (this.rand.nextInt(64) - 32);
        double var5 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;

        return this.teleportTo(var1, var3, var5);
    }

    protected boolean teleportTo(double par1, double par3, double par5) {
        EnderTeleportEvent event = new EnderTeleportEvent(this, par1, par3, par5, 0);

        if( MinecraftForge.EVENT_BUS.post(event) ) {
            return false;
        }

        double prevPosX = this.posX;
        double prevPosY = this.posY;
        double prevPosZ = this.posZ;

        this.posX = event.targetX;
        this.posY = event.targetY;
        this.posZ = event.targetZ;

        boolean teleportSucceed = false;
        int blockX = MathHelper.floor_double(this.posX);
        int blockY = MathHelper.floor_double(this.posY);
        int blockZ = MathHelper.floor_double(this.posZ);
        Block block;

        if( this.worldObj.blockExists(blockX, blockY, blockZ) ) {
            boolean blockSolid = false;

            while( !blockSolid && blockY > 0 ) {
                block = this.worldObj.getBlock(blockX, blockY - 1, blockZ);

                if( block != null && block.getMaterial().blocksMovement() ) {
                    blockSolid = true;
                } else {
                    --this.posY;
                    --blockY;
                }
            }

            if( blockSolid ) {
                this.setPosition(this.posX, this.posY, this.posZ);

                if( this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()
                    && (!this.worldObj.isAnyLiquid(this.boundingBox) || this.hasCoat()) )
                {
                    teleportSucceed = true;
                }
            }
        }

        if( !teleportSucceed ) {
            this.setPosition(prevPosX, prevPosY, prevPosZ);

            return false;
        } else {
            EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_MISS_TELEPORT, this.posX, this.posY, this.posZ, this.dimension,
                                               Triplet.with(prevPosX, prevPosY, prevPosZ));

            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "mob.endermen.portal", 1.0F, 1.0F);
            this.worldObj.playSoundAtEntity(this, "mob.endermen.portal", 1.0F, 1.0F);

            return true;
        }
    }

    protected boolean teleportToEntity(Entity par1Entity) {
        Vec3 var2 = Vec3.createVectorHelper(this.posX - par1Entity.posX, this.boundingBox.minY + this.height / 2.0F
                                                                         - par1Entity.posY + par1Entity.getEyeHeight(),
                                            this.posZ - par1Entity.posZ);

        var2 = var2.normalize();

        double multi = 16.0D;
        double x = this.posX + (this.rand.nextDouble() - 0.5D) * 0.8D - var2.xCoord * multi;
        double y = this.posY + (this.rand.nextInt(16) - 8) - var2.yCoord * multi;
        double z = this.posZ + (this.rand.nextDouble() - 0.5D) * 0.8D - var2.zCoord * multi;

        return this.teleportTo(x, y, z);
    }

    @Override
    public void updateEntityActionState() {
        if( this.getEntityToAttack() != null ) {
            if( this.getEntityToAttack() != this.riddenByEntity ) {
                super.updateEntityActionState();
                return;
            }

            this.setTarget(null);
        }

        if( !this.isRidden() ) {
            this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 5.0F;
            super.updateEntityActionState();
        }
    }

    @Override
    public void writePetToNBT(NBTTagCompound nbt) {
        nbt.setByte(EnumEnderPetEggInfo.NBT_ID, (byte) EnumEnderPetEggInfo.ENDERMISS_INFO.ordinal());
        nbt.setFloat(EnumEnderPetEggInfo.NBT_HEALTH, this.getHealth());
        nbt.setFloat(EnumEnderPetEggInfo.NBT_MAX_HEALTH, this.getMaxHealth());
        nbt.setInteger("bowColor", this.getBowColor());
        nbt.setBoolean(EnumEnderPetEggInfo.NBT_MISS_AVISFEATHER, this.hasAvisFeather());
        nbt.setBoolean(EnumEnderPetEggInfo.NBT_MISS_SPECIAL, this.isSpecial());

        if( this.hasCoat() ) {
            NBTTagCompound item = new NBTTagCompound();
            this.getCoat().writeToNBT(item);
            nbt.setTag(EnumEnderPetEggInfo.NBT_COAT, item);
        }
    }

    @Override
    public void readPetFromNBT(NBTTagCompound nbt) {
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(nbt.getFloat(EnumEnderPetEggInfo.NBT_MAX_HEALTH));
        this.setHealth(nbt.getFloat(EnumEnderPetEggInfo.NBT_HEALTH));
        this.setBowColor(nbt.getInteger("bowColor"));
        this.setAvisFeather(nbt.getBoolean(EnumEnderPetEggInfo.NBT_MISS_AVISFEATHER));
        this.setSpecial(nbt.getBoolean(EnumEnderPetEggInfo.NBT_MISS_SPECIAL));
        if( nbt.hasKey(EnumEnderPetEggInfo.NBT_COAT) ) {
            this.setCoat(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(EnumEnderPetEggInfo.NBT_COAT)));
        } else {
            this.setCoat(new ItemStack(Blocks.air));
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeEntityToNBT(par1nbtTagCompound);

        par1nbtTagCompound.setBoolean("tamed", this.isTamed());
        par1nbtTagCompound.setBoolean(EnumEnderPetEggInfo.NBT_MISS_AVISFEATHER, this.hasAvisFeather());
        par1nbtTagCompound.setBoolean("sitting", this.isSitting());
        par1nbtTagCompound.setBoolean(EnumEnderPetEggInfo.NBT_MISS_SPECIAL, this.isSpecial());
        par1nbtTagCompound.setBoolean("follow", this.isFollowing());
        par1nbtTagCompound.setByte("bowColor", (byte)this.getBowColor());
        if( this.ownerUUID != null ) {
            par1nbtTagCompound.setString("owner", this.ownerUUID.toString());
        }

        if( this.hasCoat() ) {
            NBTTagCompound coatItem = new NBTTagCompound();
            this.getCoat().writeToNBT(coatItem);
            par1nbtTagCompound.setTag(EnumEnderPetEggInfo.NBT_COAT, coatItem);
        }
    }

    public UUID getOwner() {
        return this.ownerUUID;
    }

    @Override
    public void setOwner(UUID owner) {
        this.ownerUUID = owner;
    }

    @Override
    public String getGuiTitle() {
        return EnderStuffPlus.MOD_ID + ".guipet.title.miss";
    }

    @Override
    public int getGuiColor() {
        return 0xFF9090;
    }

    @Override
    public boolean canMount() {
        return !this.isSitting();
    }
}
