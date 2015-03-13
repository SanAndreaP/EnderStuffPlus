/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.entity.living;

import de.sanandrew.core.manpack.util.UsedByReflection;
import de.sanandrew.mods.enderstuffp.item.ItemRaincoat;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumEnderPetEggInfo;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager.CoatBaseEntry;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager.CoatColorEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class AEntityEnderAvis
    extends EntityCreature implements IEnderPet<AEntityEnderAvis>, IEnderCreature
{
    protected static final int DW_BOOLEANS = 20;
    protected static final int DW_CURR_FLIGHT_COND = 21;
    protected static final int DW_COLLAR_CLR = 22;
    protected static final int DW_COAT = 23;

    protected static final ItemStack EMPTY_COAT_SLOT = new ItemStack(Items.golden_shovel);

    public float destPos = 0.0F;
    public float prevDestPos;
    public float wingRot = 0.0F;
    public float prevWingRot;
    public float wingSpread = 1.0F;
    public float prevWingSpread;

    protected int ticksFlying = 0;
    protected ItemStack prevCoatBase = null;

    @UsedByReflection
    public AEntityEnderAvis(World world) {
        super(world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getDefaultMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataWatcher.addObject(DW_BOOLEANS, (byte) 0);
        this.dataWatcher.addObject(DW_CURR_FLIGHT_COND, 20.0F);
        this.dataWatcher.addObject(DW_COLLAR_CLR, (byte) this.rand.nextInt(ItemDye.field_150922_c.length));
        this.dataWatcher.addObject(DW_COAT, EMPTY_COAT_SLOT);
    }

    @Override
    protected void attackEntity(Entity entity, float distance) {
        if( this.attackTime <= 0 && distance < 2.0F /*&& entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY*/ ) {
            this.attackTime = 20;
            this.attackEntityAsMob(entity);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        float damage = 5.0F;

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

    public ItemStack getCoat() {
        return this.dataWatcher.getWatchableObjectItemStack(DW_COAT);
    }

    public void setCoat(ItemStack stack) {
        if( stack == null || stack.getItem() != EspItems.rainCoat ) {
            stack = EMPTY_COAT_SLOT;
        }

        this.dataWatcher.updateObject(DW_COAT, stack);
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
    protected abstract Item getDropItem();

    @Override
    protected void fall(float par1) {}

    @Override
    public CoatBaseEntry getCoatBase() {
        return this.hasCoat() ? RaincoatManager.getBase(this.getCoat().getTagCompound().getString("base")) : RaincoatManager.NULL_BASE;
    }

    @Override
    public CoatColorEntry getCoatColor() {
        return this.hasCoat() ? RaincoatManager.getColor(this.getCoat().getTagCompound().getString("color")) : RaincoatManager.NULL_COLOR;
    }

    public float[] getCollarColorArr() {
        int color = ItemDye.field_150922_c[this.getCollarColor()];

        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;

        return new float[] { red, green, blue };
    }

    public int getCollarColor() {
        return this.dataWatcher.getWatchableObjectByte(DW_COLLAR_CLR);
    }

    public void setCollarColor(int clr) {
        this.dataWatcher.updateObject(DW_COLLAR_CLR, (byte) clr);
    }

    public float getFlightCondition() {
        return this.dataWatcher.getWatchableObjectFloat(DW_CURR_FLIGHT_COND);
    }

    @Override
    public AEntityEnderAvis getEntity() {
        return this;
    }

    @Override
    protected String getHurtSound() {
        return EnderStuffPlus.MOD_ID + ":mob.enderavis.hurt";
    }

    @Override
    protected String getLivingSound() {
        return EnderStuffPlus.MOD_ID + ":mob.enderavis.idle";
    }

    protected void setFlightCondition(float condition) {
        this.dataWatcher.updateObject(DW_CURR_FLIGHT_COND, condition);
    }

    protected void increaseFlightCondition(float amount) {
        this.dataWatcher.updateObject(DW_CURR_FLIGHT_COND, Math.max(0.0F, Math.min(20.0F, this.getFlightCondition() + amount)));
    }

    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return this.getCoatBase() != RaincoatManager.baseNiob || !Potion.potionTypes[effect.getPotionID()].isBadEffect();
    }

    @Override
    public void setTamed(boolean tame) { }

    @Override
    public void onLivingUpdate() {
//        if( this.isAggressive() && !this.isTamed() && this.entityToAttack == null ) {
//            EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 16F);
//            this.entityToAttack = player;
//        }

        if( !this.worldObj.isRemote ) {
            if( this.prevCoatBase != this.getCoat() ) {
                if( this.hasCoat() && RaincoatManager.getBase(this.getCoat().getTagCompound().getString("base")) == RaincoatManager.baseObsidian ) {
                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getDefaultMaxHealth() * 1.5D);
                } else {
                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getDefaultMaxHealth());
                    this.setHealth(Math.min(this.getHealth(), this.getMaxHealth()));
                }

                this.prevCoatBase = this.getCoat();
            }
        }

        if( this.isWet() && !this.hasCoat() ) {
            this.attackEntityFrom(DamageSource.drown, 1);
        }

//        this.prevWingRot = this.wingRot;
//        this.prevWingSpread = this.wingSpread;

        if( !this.onGround ) {
            this.wingRot += 0.261799388F * (this.motionY > 0.0F ? 4.0F : 1.0F);

            if( this.wingSpread > 0.0F ) {
                this.wingSpread = Math.max(0.0F, this.wingSpread - 0.785398163F);
            }

            if( this.wingRot >= 6.283185307F ) {
                this.prevWingRot -= 6.283185307F;
                this.wingRot -= 6.283185307F;
            }
        } else if( this.wingRot > 0.0F ) {
            this.wingRot *= 0.2F;

            if( this.wingSpread < 1.570796327F ) {
                this.wingSpread = Math.min(1.570796327F, this.wingSpread + 0.785398163F);
            }
        }

//        this.destPos = Math.max(0.0F, Math.min(1.0F, (float) (this.destPos + (this.onGround ? -1 : 4) * 0.3D)));
//
//        if( !this.onGround && this.wingSpread < 1.0F ) {
//            this.wingSpread = 1.0F;
//        }
//
//        this.wingSpread = (float) (this.wingSpread * 0.9D);
//
        if( !this.onGround ) {
            if( this.motionY < 0.0F && !(this.isRidden() && this.riddenByEntity.isSneaking()) && (this.canFly() || !this.isTamed()) ) {
                this.motionY *= 0.6D;
            }
//            if( this.ticksFlying < 5 ) {
//                this.ticksFlying++;
//            }
        } //else {
//            if( this.ticksFlying > 0 ) {
//                this.ticksFlying--;
//            }
//        }
//
//        this.wingRot += this.wingSpread * 2.0F;

//        if( this.isRidden() && this.riddenByEntity.isDead && this.isTamed() ) {
//            this.setSitting(true);
//        }

//        if( this.isSitting() ) {
//            this.height = 0.9F;
//        } else {
//            this.height = 1.8F;
//        }

//        EntityPlayer ep = this.getOwningPlayer(25F);
//        IAttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
//        attributeinstance.removeModifier(SPEED_TAMED);
//        attributeinstance.removeModifier(SPEED_AGGRO);
//
//        if( !this.isTamed() && this.entityToAttack != null ) {
//            attributeinstance.applyModifier(SPEED_AGGRO);
//        }
//
//        if( this.isTamed() && !this.isSitting() && !this.isRidden() && ep != null && this.isFollowing()
//                && this.getDistanceToEntity(ep) > 2F && this.ownerName.equals(ep.getCommandSenderName()) ) {
//            attributeinstance.applyModifier(SPEED_TAMED);
//            this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 24F, false, false,
//                                                                     !this.hasCoat(), !this.hasCoat()));
//        } else if( this.isTamed() && !this.isSitting() && !this.isRidden() && ep != null
//                && ep.getCurrentEquippedItem() != null && ep.getCurrentEquippedItem().getItem() instanceof ItemFood
//                && this.getDistanceToEntity(ep) > 2F && this.getHealth() < this.getMaxHealth() ) {
//            attributeinstance.applyModifier(SPEED_TAMED);
//            this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 8F, false, false,
//                                                                     !this.hasCoat(), !this.hasCoat()));
//        }

//        if( this.isRidden() ) {
//            attributeinstance.applyModifier(SPEED_TAMED);
//            this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 5.0F;
//            EntityPlayer var1 = (EntityPlayer) this.riddenByEntity;
//            this.rotationYawHead = var1.rotationYawHead;
//            this.setRotation(var1.rotationYaw, 0.0F);
//
//            this.stepHeight = 1.0F;
//
//            if( this.isJumping && this.posY < 253D ) {
//                if( this.canFly() ) {
//                    this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 3.0F;
//                    this.motionY = 0.4D;
//                    if( this.getBase().equals(ESPModRegistry.MOD_ID + "_003") ) {
//                        this.motionY += 0.1F;
//                    }
//
//                    this.currFlightCondition -= 0.01F;
//                } else {
//                    this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 5.0F;
//                }
//            }
//        }

        super.onLivingUpdate();
    }

    public boolean isRidden() {
        return this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer;
    }

    public boolean canFly() {
        return this.getFlightCondition() >= 0.2F;
    }

    protected abstract boolean isTamed();

    @Override
    public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readEntityFromNBT(par1nbtTagCompound);
        this.setCollarColor(par1nbtTagCompound.getByte("colorID"));

        if( par1nbtTagCompound.hasKey(EnumEnderPetEggInfo.NBT_COAT) ) {
            ItemStack is = ItemStack.loadItemStackFromNBT(par1nbtTagCompound.getCompoundTag(EnumEnderPetEggInfo.NBT_COAT));
            this.setCoat(is == null ? EMPTY_COAT_SLOT : is);
        } else {
            this.setCoat(new ItemStack(Items.iron_shovel));
        }

        this.setSitting(par1nbtTagCompound.getBoolean("isSitting"));
        this.setFollowing(par1nbtTagCompound.getBoolean("isFollowing"));
        if( par1nbtTagCompound.hasKey(EnumEnderPetEggInfo.NBT_AVIS_STAMINA) )
        this.setFlightCondition(par1nbtTagCompound.getFloat(EnumEnderPetEggInfo.NBT_AVIS_STAMINA));
//        this.ownerName = par1nbtTagCompound.getString("owner");
//        if( par1nbtTagCompound.hasKey("petName") ) {
//            this.setName(par1nbtTagCompound.getString("petName"));
//        }
//        if( this.getInventoryName().equals(EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET) ) {
//            this.setName("");
//        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeEntityToNBT(par1nbtTagCompound);
        par1nbtTagCompound.setByte("colorID", (byte) this.getCollarColor());
//        par1nbtTagCompound.setBoolean("isTamed", this.isTamed());
//        par1nbtTagCompound.setBoolean("isSaddled", this.isSaddled());

        NBTTagCompound coatItem = new NBTTagCompound();
        this.getCoat().writeToNBT(coatItem);
        par1nbtTagCompound.setTag(EnumEnderPetEggInfo.NBT_COAT, coatItem);

        par1nbtTagCompound.setBoolean("isSitting", this.isSitting());
        par1nbtTagCompound.setBoolean("isFollowing", this.isFollowing());
        par1nbtTagCompound.setFloat(EnumEnderPetEggInfo.NBT_AVIS_STAMINA, this.getFlightCondition());
//        par1nbtTagCompound.setString("owner", this.ownerName);
    }

    protected void setBoolean(boolean b, int flag, int dwId) {
        byte prevByte = this.dataWatcher.getWatchableObjectByte(dwId);
        this.dataWatcher.updateObject(dwId, (byte) (b ? prevByte | flag : prevByte & ~flag));
    }
    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isFollowing() {
        return false;
    }

    @Override
    public boolean isSitting() {
        return false;
    }

    @Override
    public void setFollowing(boolean b) { }

    @Override
    public void setName(String name) { }

    @Override
    public void setSitting(boolean b) { }

    @Override
    public void readPetFromNBT(NBTTagCompound nbt) { }

    @Override
    public void writePetToNBT(NBTTagCompound nbt) { }

    @Override
    public void setOwner(UUID owner) { }

    public abstract double getDefaultMaxHealth();

    @Override
    public String getGuiTitle() {
        return null;
    }

    @Override
    public int getGuiColor() {
        return 0;
    }

    @Override
    public boolean canMount() {
        return false;
    }
}
