package sanandreasp.mods.EnderStuffPlus.entity;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import sanandreasp.core.manpack.helpers.SAPUtils;
import sanandreasp.core.manpack.mod.packet.PacketRegistry;
import sanandreasp.mods.EnderStuffPlus.item.ItemRaincoat;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityEnderAvis
    extends EntityCreature
    implements IEnderPet, IEnderCreature
{
    private static final AttributeModifier SPEED_AGGRO =
            (new AttributeModifier(UUID.fromString("8856E883-562A-4A2F-85D0-D34D70BD8AB2"),
                                   "Attacking speed boost", 0.25D, 0)).setSaved(false);
    private static final AttributeModifier SPEED_TAMED =
            (new AttributeModifier(UUID.fromString("8856E883-562A-4A2F-85D0-D34D70BD8AB2"),
                                   "Tamerfollowing speed boost", 6D, 0)).setSaved(false);

    private float currFlightCondition = 10.0F;
    private float destPos = 0.0F;
    private float wingRot = 0.0F;
    private float wingSpread = 1.0F;
    private float prevWingRot;
    private float prevDestPos;
    private ItemStack prevCoatBase = null;
    private String ownerName = "";
    /**
     * buffer for the getOwningPlayer method to prevent iterating through the
     * entire players list when getting the owning player
     **/
    private EntityPlayer ownerInst = null;
    private int ticksFlying = 0;

    public EntityEnderAvis(World world) {
        super(world);
    }

    public boolean _SAP_canDismountWithLSHIFT(EntityPlayer player) {
        return this.onGround;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.3D);
    }

    @Override
    protected void attackEntity(Entity entity, float distance) {
        if( this.attackTime <= 0 && distance < 2.0F && entity.boundingBox.maxY > this.boundingBox.minY
            && entity.boundingBox.minY < this.boundingBox.maxY )
        {
            this.attackTime = 20;
            this.attackEntityAsMob(entity);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        float damage = 2F;

        if( this.isPotionActive(Potion.damageBoost) ) {
            damage += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }

        if( this.isPotionActive(Potion.weakness) ) {
            damage -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
        }

        if( entity instanceof EntityLivingBase ) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 0));
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 300, 0));
        }

        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
    }

    @Override
    public boolean attackEntityFrom(DamageSource dmgSource, float dmg) {
        if( this.isRidden() && dmgSource != null && dmgSource.getEntity() instanceof EntityPlayer ) {
            return false;
        }

        if( super.attackEntityFrom(dmgSource, dmg) ) {
            Entity hitter = dmgSource.getEntity();

            if( this.riddenByEntity != hitter && this.ridingEntity != hitter && !this.isTamed() ) {
                if( hitter != this ) {
                    this.entityToAttack = hitter;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canBeSteered() {
        return this.isRiddenDW();
    }

    @Override
    protected boolean canDespawn() {
        return !this.isTamed();
    }

    public boolean canFly() {
        return (this.dataWatcher.getWatchableObjectByte(15) & 64) == 64;
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataWatcher.addObject(15, new Byte((byte) 0));
        this.dataWatcher.addObject(16, new Byte((byte) this.rand.nextInt(ItemDye.dyeColors.length)));
        this.dataWatcher.addObject(17, 20);
        this.dataWatcher.addObject(22, new ItemStack(Item.shovelIron));
    }

    @Override
    protected void fall(float par1) {}

    @Override
    public float getAIMoveSpeed() {
        return this.isSitting() ? 0.0F : (this.entityToAttack != null || this.isRiddenDW()
                                                ? 0.175F + (this.getCoatBase().equals(ESPModRegistry.MOD_ID + "_000") ? 0.05F : 0F)
                                                : 0.1F);
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.isValidLightLevel() && super.getCanSpawnHere();
    }

    public ItemStack getCoat() {
        return this.dataWatcher.getWatchableObjectItemStack(22);
    }

    public boolean isCoatApplicable(ItemStack coat) {
        if( !this.hasCoat() && coat.getItem() instanceof ItemRaincoat ) {
            return true;
        } else if( coat.hasTagCompound() ) {
            String base = coat.getTagCompound().getString("base");
            String color = coat.getTagCompound().getString("color");

            return !this.getCoatColor().equals(color) || !this.getCoatBase().equals(base);
        }

        return false;
    }

    @Override
    public String getCoatBase() {
        return this.hasCoat() ? this.getCoat().getTagCompound().getString("base") : "";
    }

    @Override
    public String getCoatColor() {
        return this.hasCoat() ? this.getCoat().getTagCompound().getString("color") : "";
    }

    public boolean hasCoat() {
        return this.getCoat().hasTagCompound() && this.getCoat().getItem() == ModItemRegistry.rainCoat;
    }

    public float[] getCollarColorArr() {
        int color = ItemDye.dyeColors[this.getCollarColor()];

        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;

        return new float[] { red, green, blue };
    }

    public int getCollarColor() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    public int getCurrFlightCondInt() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }

    public float getCurrFlightCondition() {
        return this.currFlightCondition;
    }

    public float getDestPos() {
        return this.destPos;
    }

    @Override
    protected int getDropItemId() {
        return ModItemRegistry.avisFeather.itemID;
    }

    @Override
    public int getEggDmg() {
        return 1;
    }

    @Override
    public EntityCreature getEntity() {
        return this;
    }

    @Override
    protected String getHurtSound() {
        return "enderstuffp:enderavis.hit";
    }

    @Override
    protected String getLivingSound() {
        return "enderstuffp:enderavis.idle";
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() - (this.isRidden() ? 0.2F : 0.0F);
    }

    @Override
    public String getName() {
        return this.getCustomNameTag();
    }

    @SuppressWarnings("unchecked")
    private EntityPlayer getOwningPlayer(float distance) {
        if( !this.worldObj.isRemote && this.isTamed() ) {
            if( this.ownerInst == null || !this.ownerInst.username.equals(this.ownerName) ) {
                Iterator<EntityPlayer> players = this.worldObj.playerEntities.iterator();
                while( players.hasNext() ) {
                    EntityPlayer currPlayer = players.next();

                    if( this.ownerName.equals(currPlayer.username) ) {
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
    public float getPetHealth() {
        return super.getHealth();
    }

    @Override
    public float getPetMaxHealth() {
        return this.getMaxHealth();
    }

    public float getPrevDestPos() {
        return this.prevDestPos;
    }

    public float getPrevWingRot() {
        return this.prevWingRot;
    }

    public int getTicksFlying() {
        return this.ticksFlying;
    }

    public float getWingRot() {
        return this.wingRot;
    }

    private void increaseCondition(float amount) {
        this.currFlightCondition += amount;

        if( this.currFlightCondition > 10.0F ) {
            this.currFlightCondition = 10.0F;
        }
    }

    @Override
    public boolean interact(EntityPlayer player) {
        if( player.getCurrentEquippedItem() != null ) {
            ItemStack playerItem = player.getCurrentEquippedItem();

            if( !this.worldObj.isRemote && this.isTamed() && this.ownerName.equals(player.username) && !this.isRidden() ) {
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
                } else if( playerItem.getItem() == Item.dyePowder && playerItem.getItemDamage() != this.getCollarColor() ) {
                    this.setCollarColor(playerItem.getItemDamage());
                    playerItem.stackSize--;
                    return true;
                } else if( playerItem.getItem() instanceof ItemFood && this.getHealth() < this.getMaxHealth() ) {
                    this.heal(((ItemFood) playerItem.getItem()).getHealAmount());
                    ESPModRegistry.sendPacketAllRng("fxTameAcc", this.posX, this.posY, this.posZ, 128.0D,
                                                    this.dimension, this.posX, this.posY, this.posZ, this.width,
                                                    this.height);
                    playerItem.stackSize--;

                    return true;
                } else if( playerItem.getItem() == ModItemRegistry.enderPetStaff ) {
                    PacketRegistry.sendPacketToPlayer(ESPModRegistry.MOD_ID, "showPetGui", (Player) player, this);

                    return true;
                } else if( playerItem.getItem() == Item.saddle && !this.isSaddled() ) {
                    this.setSaddled(true);
                    playerItem.stackSize--;

                    return true;
                } else if( playerItem.getItem() == Item.sugar && this.currFlightCondition < 9.8F ) {
                    this.increaseCondition(2F);
                    playerItem.stackSize--;

                    return true;
                }
            }
        }

        if( this.isTamed() && !this.worldObj.isRemote ) {
            player.addChatMessage(String.format("\247d[%s]\247f "
                                                        + StatCollector.translateToLocal("enderstuffplus.chat.name"),
                                                StatCollector.translateToLocal("entity.EnderAvis.name"),
                                                this.getName().isEmpty() ? EnumChatFormatting.OBFUSCATED + "RANDOM"
                                                                           + EnumChatFormatting.RESET : this.getName()));
            if( this.ownerName.equals(player.username) ) {
                int percHealth = (int) (this.getHealth() / this.getMaxHealth() * 100F);
                int percCondit = (int) (this.currFlightCondition * 10F);
                player.addChatMessage("  "
                                      + String.format(StatCollector.translateToLocal("enderstuffplus.chat.avisFriend"),
                                                      percHealth, percCondit));
            } else {
                player.addChatMessage("  "
                                      + String.format(StatCollector.translateToLocal("enderstuffplus.chat.stranger"),
                                                      this.ownerName));
            }
            return true;
        }
        return false;
    }

    public boolean isAggressive() {
        return (this.dataWatcher.getWatchableObjectByte(15) & 16) == 16;
    }

    @Override
    public boolean isFollowing() {
        return (this.dataWatcher.getWatchableObjectByte(15) & 128) == 128;
    }

    public boolean isImmuneToWater() {
        return this.hasCoat();
    }

    @Override
    protected boolean isMovementCeased() {
        return this.isSitting() && this.isTamed();
    }

    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return !this.getCoatBase().equals(ESPModRegistry.MOD_ID + "_001") || !Potion.potionTypes[effect.getPotionID()].isBadEffect();
    }

    public boolean isRidden() {
        return this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer;
    }

    public boolean isRiddenDW() {
        try {
            return (this.dataWatcher.getWatchableObjectByte(15) & 32) == 32;
        } catch( NullPointerException e ) {
            return false;
        }
    }

    public boolean isSaddled() {
        return (this.dataWatcher.getWatchableObjectByte(15) & 2) == 2;
    }

    @Override
    public boolean isSitting() {
        try {
            return (this.dataWatcher.getWatchableObjectByte(15) & 8) == 8;
        } catch( NullPointerException e ) {
            return false;
        }
    }

    public boolean isTamed() {
        return (this.dataWatcher.getWatchableObjectByte(15) & 1) == 1;
    }

    protected boolean isValidLightLevel() {
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
    public void moveEntity(double var1, double var3, double var5) {
        float var7 = this.width / 2.0F;

        float var8 = (float) this.getMountedYOffset() + (this.isRidden() ? 1.3F : 0.5F) + this.yOffset;
        this.boundingBox.setBounds(this.posX - var7, this.posY - this.yOffset + this.ySize, this.posZ - var7, this.posX
                                                                                                              + var7,
                                   this.posY - this.yOffset + this.ySize + var8, this.posZ + var7);
        super.moveEntity(var1, var3, var5);
        var8 = this.height;
        this.boundingBox.setBounds(this.posX - var7, this.posY - this.yOffset + this.ySize, this.posZ - var7, this.posX
                                                                                                              + var7,
                                   this.posY - this.yOffset + this.ySize + var8, this.posZ + var7);
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {

        if( this.isRidden() ) {
            ((EntityPlayer) this.riddenByEntity).mountEntity(null);
        }

        super.onDeath(par1DamageSource);
    }

    @Override
    public void onLivingUpdate() {
        if( this.isAggressive() && !this.isTamed() && this.entityToAttack == null ) {
            EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 16F);
            this.entityToAttack = player;
        }

        if( !this.worldObj.isRemote ) {
            if( this.prevCoatBase != this.getCoat() ) {
                if( this.hasCoat() && this.getCoat().getTagCompound().getString("base").equals(ESPModRegistry.MOD_ID + "_004") ) {
                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(60.0D);
                } else {
                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(40.0D);
                    this.setHealth(Math.min(this.getHealth(), this.getMaxHealth()));
                }

                this.prevCoatBase = this.getCoat();
            }

            this.setRiddenDW(this.isRidden());
        }

        if( this.isWet() && !this.isImmuneToWater() ) {
            this.attackEntityFrom(DamageSource.drown, 1);
        }

        this.prevWingRot = this.wingRot;
        this.prevDestPos = this.destPos;
        this.destPos = (float) (this.destPos + (this.onGround ? -1 : 4) * 0.3D);

        if( this.destPos < 0.0F ) {
            this.destPos = 0.0F;
        }

        if( this.destPos > 1.0F ) {
            this.destPos = 1.0F;
        }

        if( !this.onGround && this.wingSpread < 1.0F ) {
            this.wingSpread = 1.0F;
        }

        this.wingSpread = (float) (this.wingSpread * 0.9D);

        if( !this.onGround ) {
            if( this.motionY < 0F
                && !(this.isRiddenDW() && ESPModRegistry.isShiftPressed((EntityPlayer) this.riddenByEntity))
                && (this.canFly() || !this.isTamed()) ) {
                this.motionY *= 0.6D;
            }
            if( this.ticksFlying < 5 ) {
                this.ticksFlying++;
            }
        } else {
            if( this.ticksFlying > 0 ) {
                this.ticksFlying--;
            }
        }

        this.wingRot += this.wingSpread * 2.0F;

        if( this.isRidden() && this.riddenByEntity.isDead && this.isTamed() ) {
            this.setSitting(true);
        }

        if( this.isSitting() ) {
            this.height = 0.9F;
        } else {
            this.height = 1.8F;
        }

        EntityPlayer ep = this.getOwningPlayer(25F);
        AttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        attributeinstance.removeModifier(SPEED_TAMED);
        attributeinstance.removeModifier(SPEED_AGGRO);

        if( !this.isTamed() && this.entityToAttack != null ) {
            attributeinstance.applyModifier(SPEED_AGGRO);
        }

        if( this.isTamed() && !this.isSitting() && !this.isRidden() && ep != null && this.isFollowing()
            && this.getDistanceToEntity(ep) > 2F && this.ownerName.equals(ep.username) ) {
            attributeinstance.applyModifier(SPEED_TAMED);
            this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 24F, false, false,
                                                                     !this.isImmuneToWater(), !this.isImmuneToWater()));
        } else if( this.isTamed() && !this.isSitting() && !this.isRidden() && ep != null
                   && ep.getCurrentEquippedItem() != null && ep.getCurrentEquippedItem().getItem() instanceof ItemFood
                   && this.getDistanceToEntity(ep) > 2F && this.getHealth() < this.getMaxHealth() ) {
            attributeinstance.applyModifier(SPEED_TAMED);
            this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 8F, false, false,
                                                                     !this.isImmuneToWater(), !this.isImmuneToWater()));
        }

        if( this.isRiddenDW() && this.riddenByEntity != null ) {
            attributeinstance.applyModifier(SPEED_TAMED);
            this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 5.0F;
            EntityPlayer var1 = (EntityPlayer) this.riddenByEntity;
            this.rotationYawHead = var1.rotationYawHead;
            this.setRotation(var1.rotationYaw, 0.0F);

            this.stepHeight = 1.0F;

            if( this.isJumping && this.posY < 253D ) {
                if( this.canFly() ) {
                    this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 3.0F;
                    this.motionY = 0.4D;
                    if( this.getCoatBase().equals(ESPModRegistry.MOD_ID + "_003") ) {
                        this.motionY += 0.1F;
                    }

                    this.currFlightCondition -= 0.01F;
                } else {
                    this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 5.0F;
                }
            }
        }

        super.onLivingUpdate();

        this.isJumping = false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if( this.currFlightCondition < 0.2 && !this.worldObj.isRemote ) {
            this.setBoolean(false, 64, 15);
        } else if( !this.worldObj.isRemote ) {
            this.setBoolean(true, 64, 15);
        }

        if( !this.worldObj.isRemote ) {
            this.dataWatcher.updateObject(17, Math.round(this.currFlightCondition * 2F));
        }

        if( !this.worldObj.isRemote && this.worldObj.difficultySetting == 0 && !this.isTamed() ) {
            this.setDead();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void processRiding(EntityPlayerSP player) {
        if( ESPModRegistry.isJumping(player) ) {
            ESPModRegistry.proxy.setJumping(true, this);
        } else {
            ESPModRegistry.proxy.setJumping(false, this);
        }
        PacketRegistry.sendPacketToServer(ESPModRegistry.MOD_ID, "riddenMove", player.movementInput.moveForward,
                                          player.movementInput.moveStrafe);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readEntityFromNBT(par1nbtTagCompound);
        this.setCollarColor(par1nbtTagCompound.getByte("colorID"));
        this.setTamed(par1nbtTagCompound.getBoolean("isTamed"));
        this.setSaddled(par1nbtTagCompound.getBoolean("isSaddled"));

        if( par1nbtTagCompound.hasKey("coatItem") ) {
            ItemStack is = ItemStack.loadItemStackFromNBT(par1nbtTagCompound.getCompoundTag("coatItem"));
            this.setCoat(is == null ? new ItemStack(Item.shovelIron) : is);
        } else {
            this.setCoat(new ItemStack(Item.shovelIron));
        }

        this.setSitting(par1nbtTagCompound.getBoolean("isSitting"));
        this.setFollowing(par1nbtTagCompound.getBoolean("isFollowing"));
        this.currFlightCondition = par1nbtTagCompound.getFloat("currFlightCond");
        this.ownerName = par1nbtTagCompound.getString("owner");
        if( par1nbtTagCompound.hasKey("petName") ) {
            this.setName(par1nbtTagCompound.getString("petName"));
        }
        if( this.getName().equals(EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET) ) {
            this.setName("");
        }
    }

    public void setAggressive(boolean b) {
        this.setBoolean(b, 16, 15);
    }

    private void setBoolean(boolean b, int flag, int dwId) {
        byte prevByte = this.dataWatcher.getWatchableObjectByte(dwId);
        this.dataWatcher.updateObject(dwId, (byte) (b ? prevByte | flag : prevByte & ~flag));
    }

    public void setCollarColor(int clr) {
        this.dataWatcher.updateObject(16, (byte) clr);
    }

    public void setCondition(float par1) {
        this.currFlightCondition = Math.min(10.0F, par1);
    }

    @Override
    public void setFollowing(boolean b) {
        this.setBoolean(b, 128, 15);
    }

    public void setImmuneToWater(boolean b) {
        this.setBoolean(b, 4, 15);
    }

    @Override
    public void setName(String s) {
        this.setCustomNameTag(s);
    }

    @Override
    public void setOwnerName(String owner) {
        this.ownerName = owner;
    }

    @Override
    public void setPetMoveForward(float f) {
        this.moveForward = f;
    }

    @Override
    public void setPetMoveStrafe(float f) {
        this.moveStrafing = f;
    }

    public void setRiddenDW(boolean b) {
        this.setBoolean(b, 32, 15);
    }

    public void setSaddled(boolean b) {
        this.setBoolean(b, 2, 15);
    }

    @Override
    public void setSitting(boolean b) {
        this.setBoolean(b, 8, 15);
    }

    @Override
    public void setTamed(boolean b) {
        this.setBoolean(b, 1, 15);
    }

    @Override
    public boolean shouldRiderFaceForward(EntityPlayer player) {
        return true;
    }

    @Override
    public void updateEntityActionState() {
        if( !this.isRiddenDW() ) {
            this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 5.0F;
            super.updateEntityActionState();
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeEntityToNBT(par1nbtTagCompound);
        par1nbtTagCompound.setByte("colorID", (byte) this.getCollarColor());
        par1nbtTagCompound.setBoolean("isTamed", this.isTamed());
        par1nbtTagCompound.setBoolean("isSaddled", this.isSaddled());

        NBTTagCompound coatItem = new NBTTagCompound();
        this.getCoat().writeToNBT(coatItem);
        par1nbtTagCompound.setTag("coatItem", coatItem);

        par1nbtTagCompound.setBoolean("isSitting", this.isSitting());
        par1nbtTagCompound.setBoolean("isFollowing", this.isFollowing());
        par1nbtTagCompound.setFloat("currFlightCond", this.currFlightCondition);
        par1nbtTagCompound.setString("owner", this.ownerName);
    }

    @Override
    public void writePetToNBT(NBTTagCompound nbt) {
        nbt.setByte("petID", (byte)1);
        nbt.setFloat("health", this.getHealth());
        nbt.setFloat("condition", this.getCurrFlightCondition());
        nbt.setInteger("collarColor", this.getCollarColor());
        nbt.setBoolean("saddled", this.isSaddled());

        if( this.getCoat().getItem() != Item.shovelIron ) {
            NBTTagCompound item = new NBTTagCompound();
            this.getCoat().writeToNBT(item);
            nbt.setCompoundTag("coat", item);
        }
    }

    public void setCoat(ItemStack stack) {
        if( stack == null || stack.getItem() != ModItemRegistry.rainCoat ) {
            stack = new ItemStack(Item.shovelIron);
        }

        this.dataWatcher.updateObject(22, stack);
    }

    @Override
    public void readPetFromNBT(NBTTagCompound nbt) {
        this.setHealth(nbt.getFloat("health"));
        this.setCondition(nbt.getFloat("condition"));
        this.setCollarColor(nbt.getInteger("collarColor"));
        this.setSaddled(nbt.getBoolean("saddled"));
        if( nbt.hasKey("coat") ) {
            this.setCoat(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("coat")));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void getEggInfo(ItemStack stack, EntityPlayer player, List<String> infos, boolean hasAdvancedInfo) {
        if( stack.hasTagCompound() ) {
            NBTTagCompound nbt = stack.getTagCompound();
            infos.add(String.format("%s: \2473%s",
                                    SAPUtils.getTranslated("enderstuffplus.petegg.health"),
                                    (int) (nbt.getFloat("health") / 40F * 100F) + "%"));
            infos.add(String.format("%s: \2473%s",
                                    SAPUtils.getTranslated("enderstuffplus.petegg.condition"),
                                    (int) (nbt.getFloat("condition") * 10F) + "%"));
            infos.add(String.format("%s: \2473%s",
                                    SAPUtils.getTranslated("enderstuffplus.petegg.saddle"),
                                    nbt.getBoolean("saddled") ? SAPUtils.getTranslated("enderstuffplus.petegg.true")
                                                              : SAPUtils.getTranslated("enderstuffplus.petegg.false")));
            infos.add(String.format("%s: \2473%s",
                                    SAPUtils.getTranslated("enderstuffplus.petegg.immuneToH2O"),
                                    nbt.hasKey("coat") ? SAPUtils.getTranslated("enderstuffplus.petegg.true")
                                                       : SAPUtils.getTranslated("enderstuffplus.petegg.false")));
        }
    }
}
