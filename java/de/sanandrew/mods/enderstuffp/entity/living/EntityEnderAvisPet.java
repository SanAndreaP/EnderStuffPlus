/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.entity.living;

import de.sanandrew.core.manpack.util.UsedByReflection;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.util.*;
import de.sanandrew.mods.enderstuffp.util.raincoat.RegistryRaincoats;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import java.util.UUID;

public class EntityEnderAvisPet
        extends AEntityEnderAvis
{
//    private static final AttributeModifier SPEED_TAMED = (new AttributeModifier(UUID.fromString("8856E883-562A-4A2F-85D0-D34D70BD8AB2"),
//                                                                                "Tamerfollowing speed boost", 6.2D, 0)).setSaved(false);
    private UUID ownerUUID = null;
    /**
     * buffer for the getOwningPlayer method to prevent iterating through the
     * entire players list when getting the owning player
     **/
    private EntityPlayer ownerInst = null;

    @UsedByReflection
    public EntityEnderAvisPet(World world) {
        super(world);
    }

    @UsedByReflection
    public boolean _SAP_canDismountWithLSHIFT(EntityPlayer player) {
        return this.onGround;
    }

    @Override
    public boolean attackEntityFrom(DamageSource dmgSource, float dmg) {
        return !(this.isRidden() && dmgSource != null && dmgSource.getEntity() instanceof EntityPlayer) && super.attackEntityFrom(dmgSource, dmg);
    }

    @Override
    public boolean canBeSteered() {
        return this.isRidden();
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public String getName() {
        return this.getCustomNameTag();
    }

    @Override
    public boolean isFollowing() {
        return (this.dataWatcher.getWatchableObjectByte(DW_BOOLEANS) & 64) == 64;
    }

    @Override
    public boolean isSitting() {
        return (this.dataWatcher.getWatchableObjectByte(DW_BOOLEANS) & 4) == 4;
    }

    public boolean isSaddled() {
        return (this.dataWatcher.getWatchableObjectByte(DW_BOOLEANS) & 2) == 2;
    }

    public void setSaddled(boolean b) {
        this.setBoolean(b, 2, DW_BOOLEANS);
    }

    @Override
    public boolean shouldRiderFaceForward(EntityPlayer player) {
        return true;
    }

    @Override
    public void moveEntity(double var1, double var3, double var5) {
        float hWidth = this.width / 2.0F;
        float yOff = (float) this.getMountedYOffset() + (this.isRidden() ? 1.3F : 0.5F) + this.yOffset;

        this.boundingBox.setBounds(this.posX - hWidth, this.posY - this.yOffset + this.ySize, this.posZ - hWidth,
                                   this.posX + hWidth, this.posY - this.yOffset + this.ySize + yOff, this.posZ + hWidth);

        super.moveEntity(var1, var3, var5);

        yOff = this.height;

        this.boundingBox.setBounds(this.posX - hWidth, this.posY - this.yOffset + this.ySize, this.posZ - hWidth,
                                   this.posX + hWidth, this.posY - this.yOffset + this.ySize + yOff, this.posZ + hWidth
        );
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
        if( this.isRidden() ) {
            this.riddenByEntity.mountEntity(null);
        }

        super.onDeath(par1DamageSource);
    }

    @Override
    public void setFollowing(boolean b) {
        this.setBoolean(b, 64, DW_BOOLEANS);
    }

    @Override
    protected boolean isMovementCeased() {
        return this.isSitting();
    }

    @Override
    public void setName(String name) {
        this.setCustomNameTag(name);
    }

    @Override
    public void setSitting(boolean b) {
        this.setBoolean(b, 4, DW_BOOLEANS);
    }

    @Override
    public void setOwner(UUID owner) {
        this.ownerUUID = owner;
    }

    @Override
    public String getGuiTitle() {
        return EnderStuffPlus.MOD_ID + ".guipet.title.avis";
    }

    @Override
    public int getGuiColor() {
        return 0x8040FF;
    }

    @Override
    public boolean canMount() {
        return !this.isSitting() && this.isSaddled();
    }

    @Override
    public float getAIMoveSpeed() {
        return this.isSitting() ? 0.0F : (this.isRidden() ? 0.2F + (this.getCoatBase() == RegistryRaincoats.baseGold ? 0.05F : 0.0F) : 0.1F);
    }

    @Override
    protected Item getDropItem() {
        return null;
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() - (this.isRidden() ? 0.2F : 0.0F);
    }

    @SuppressWarnings("unchecked")
    private EntityPlayer getOwningPlayerInRng(float distance) {
        if( !this.worldObj.isRemote ) {
            if( this.ownerInst == null || !this.ownerInst.getGameProfile().getId().equals(this.ownerUUID) ) {
                for( EntityPlayer currPlayer : (Iterable<EntityPlayer>) this.worldObj.playerEntities ) {
                    if( this.ownerUUID != null && this.ownerUUID.equals(currPlayer.getGameProfile().getId()) ) {
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

    public boolean needFood() {
        return this.getHealth() < (this.getMaxHealth() / 100.0F) * 20.0F;
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

                if( this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && (!this.worldObj.isAnyLiquid(this.boundingBox) || this.hasCoat()) ) {
                    teleportSucceed = true;
                }
            }
        }

        if( !teleportSucceed ) {
            this.setPosition(prevPosX, prevPosY, prevPosZ);
            return false;
        } else {
            return true;
        }
    }

    protected boolean teleportToEntity(Entity par1Entity) {
        Vec3 var2 = Vec3.createVectorHelper(this.posX - par1Entity.posX, this.boundingBox.minY + this.height / 2.0F - par1Entity.posY + par1Entity.getEyeHeight(),
                                            this.posZ - par1Entity.posZ);

        var2 = var2.normalize();

        double multi = 16.0D;
        double x = this.posX + (this.rand.nextDouble() - 0.5D) * 0.8D - var2.xCoord * multi;
        double y = this.posY + (this.rand.nextInt(16) - 8) - var2.yCoord * multi;
        double z = this.posZ + (this.rand.nextDouble() - 0.5D) * 0.8D - var2.zCoord * multi;

        return this.teleportTo(x, y, z);
    }

    @Override
    public void onLivingUpdate() {
        if( this.riddenByEntity != null && !this.isSaddled() ) {
            this.riddenByEntity.mountEntity(null);
        }

        if( !this.worldObj.isRemote && this.ownerUUID == null ) {
            this.setDead();
            return;
        }

        if( this.isRidden() && this.riddenByEntity.isDead ) {
            this.setSitting(true);
        }

        if( this.isSitting() ) {
            this.height = 0.9F;
        } else {
            this.height = 1.8F;
        }

        EntityPlayer ep = this.getOwningPlayerInRng(25.0F);

//        IAttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

//        attributeinstance.removeModifier(SPEED_TAMED);

        if( !this.isSitting() && !this.isRidden() && ep != null && this.getDistanceToEntity(ep) > 2.0F && this.isFollowing() ) {
            this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 24.0F, false, false, !this.hasCoat(), !this.hasCoat()));
//            attributeinstance.applyModifier(SPEED_TAMED);

            if( this.getDistanceToEntity(ep) > 10.0F && Math.abs(ep.posY - this.posY) < 6.0F ) {
                this.teleportToEntity(ep);
            }
        } else if( !this.isRidden() && ep != null && ep.getCurrentEquippedItem() != null
                && ep.getCurrentEquippedItem().getItem() instanceof ItemFood && this.getDistanceToEntity(ep) > 2.0F && this.needFood() && !this.isSitting() )
        {
//            attributeinstance.applyModifier(SPEED_TAMED);
            this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 8.0F, false, false, !this.hasCoat(), !this.hasCoat()));
        }

        if( this.isWet() && !this.hasCoat() ) {
            this.attackEntityFrom(DamageSource.drown, 1);
        }

        super.onLivingUpdate();

        if( this.isRidden() ) {
//            attributeinstance.applyModifier(SPEED_TAMED);
            this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 5.0F;
            EntityPlayer player = (EntityPlayer) this.riddenByEntity;
            this.rotationYawHead = player.rotationYawHead;

            this.setRotation(player.rotationYaw, 0.0F);
            this.moveStrafing = player.moveStrafing;
            this.moveForward = player.moveForward;

            if( EnderStuffPlus.isJumping(player) ) {
                if( this.canFly() && this.posY < 253.0D ) {
                    this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 3.0F;
                    this.motionY = 0.4D;

                    if( this.getCoatBase() == RegistryRaincoats.baseRedstone ) {
                        this.motionY += 0.1F;
                    }

                    this.increaseFlightCondition(-0.02F);
                } else {
                    this.isJumping = true;
                }
            } else {
                this.isJumping = false;
            }
        }
    }

    @Override
    public boolean interact(EntityPlayer player) {
        if( !worldObj.isRemote ) {
            if( player.getCurrentEquippedItem() != null ) {
                ItemStack playerItem = player.getCurrentEquippedItem();

                if( !this.isRidden() ) {
                    if( this.ownerUUID.equals(player.getGameProfile().getId()) ) {
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
                        } else if( SAPUtils.areItemInstEqual(playerItem, Items.saddle) && !this.isSaddled() ) {
                            this.setSaddled(true);
                            playerItem.stackSize--;

                            return true;
                        } else if( SAPUtils.areItemInstEqual(playerItem, Items.sugar) && this.getFlightCondition() < 19.5F ) {
                            this.increaseFlightCondition(2.0F);
                            playerItem.stackSize--;

                            return true;
                        } else if( SAPUtils.areItemInstEqual(playerItem, Items.dye) && playerItem.getItemDamage() != this.getCollarColor() ) {
                            this.setCollarColor(playerItem.getItemDamage());
                            playerItem.stackSize--;

                            return true;
                        } else if( playerItem.getItem() instanceof ItemFood && this.getHealth() < this.getMaxHealth() ) {
                            this.heal(((ItemFood) playerItem.getItem()).func_150905_g(playerItem));
                            //TODO: spawn avis hearts
                            EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_TAME, this.posX, this.posY, this.posZ, this.dimension, null);
                            playerItem.stackSize--;

                            return true;
                        } else if( SAPUtils.areItemInstEqual(playerItem, EspItems.enderPetStaff) ) {
                            EnderStuffPlus.proxy.openGui(player, EnumGui.ENDERPET, this.getEntityId(), 0, 0);

                            return true;
                        }
                    }
                }
            } else {
                //TODO: use avis chat!
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
                    if( org.apache.commons.lang3.StringUtils.isNumeric(s) ) {
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
    protected boolean isTamed() {
        return true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        nbt.setBoolean(EnumEnderPetEggInfo.NBT_AVIS_SADDLED, this.isSaddled());
        nbt.setString("owner", this.ownerUUID.toString());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        this.setSaddled(nbt.getBoolean(EnumEnderPetEggInfo.NBT_AVIS_SADDLED));
        this.ownerUUID = UUID.fromString(nbt.getString("owner"));
    }

    @Override
    protected void updateEntityActionState() {
        if( !this.isRidden() ) {
            this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 5.0F;
            super.updateEntityActionState();
        }
    }

    @Override
    public void writePetToNBT(NBTTagCompound nbt) {
        nbt.setByte(EnumEnderPetEggInfo.NBT_ID, (byte) EnumEnderPetEggInfo.ENDERAVIS_INFO.ordinal());
        nbt.setFloat(EnumEnderPetEggInfo.NBT_HEALTH, this.getHealth());
        nbt.setFloat(EnumEnderPetEggInfo.NBT_MAX_HEALTH, this.getMaxHealth());
        nbt.setInteger("collarColor", this.getCollarColor());
        nbt.setBoolean(EnumEnderPetEggInfo.NBT_AVIS_SADDLED, this.isSaddled());
        nbt.setFloat(EnumEnderPetEggInfo.NBT_AVIS_STAMINA, this.getFlightCondition());

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
        this.setCollarColor(nbt.getInteger("collarColor"));
        this.setSaddled(nbt.getBoolean(EnumEnderPetEggInfo.NBT_AVIS_SADDLED));
        this.setFlightCondition(nbt.getFloat(EnumEnderPetEggInfo.NBT_AVIS_STAMINA));
        if( nbt.hasKey(EnumEnderPetEggInfo.NBT_COAT) ) {
            this.setCoat(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(EnumEnderPetEggInfo.NBT_COAT)));
        } else {
            this.setCoat(new ItemStack(Blocks.air));
        }
    }
}
