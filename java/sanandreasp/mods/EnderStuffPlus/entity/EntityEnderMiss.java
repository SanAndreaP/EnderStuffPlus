package sanandreasp.mods.EnderStuffPlus.entity;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import sanandreasp.core.manpack.helpers.CUS;
import sanandreasp.core.manpack.mod.packet.PacketRegistry;
import sanandreasp.mods.EnderStuffPlus.item.ItemRaincoat;
import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityEnderMiss extends EntityCreature implements IEnderPet, IEnderCreature
{
    private static final AttributeModifier speedyTamed = (new AttributeModifier(UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0"), "Tamerfollowing speed boost", 6.199999809265137D, 0)).setSaved(false);
	private int prevBaseClr = -1;

	/** buffer for the getOwningPlayer method to prevent iterating through the entire players list when getting the owning player **/
	private EntityPlayer ownerInst	= null;

	protected int attackStrength = 0;
	protected static boolean[] carriableBlocks = new boolean[Block.blocksList.length];
	protected int teleportDelay = 0;

    public String ownerName = "";
	public int jumpTicks = 0;
	public int teleportTimer = 0;

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

	public EntityEnderMiss(World world) {
		super(world);

		this.attackStrength = 0;
		this.stepHeight = 1.0F;

		this.setSize(0.6F, 2.9F);
		this.dataWatcher.addObject(18, new Byte((byte) this.rand.nextInt(ItemRaincoat.colorList.size() - 3)));
		this.dataWatcher.addObject(21, new Byte((byte) 0));
		this.dataWatcher.addObject(22, -1);
		this.setSpecial(this.rand.nextInt(16) == 0);
		this.setCanGetFallDmg(true);

		if( this.rand.nextInt(2) == 0 ) {
			this.setCoat(this.rand.nextInt(ItemRaincoat.baseList.size()), this.rand.nextInt(ItemRaincoat.colorList.size()));
		}
	}

	@Override
	protected void attackEntity(Entity entity, float dmg) { }

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

		if( this.isRidden() && dmgSource != null && dmgSource.getEntity() instanceof EntityPlayer ) {
			return false;
		}

		if( dmgSource instanceof EntityDamageSourceIndirect && !this.isTamed() ) {
			for( int i = 0; i < 64; ++i ) {
				if( this.teleportRandomly() ) {
					return false;
				}
			}

			return false;
		} else if( this.isTamed() && dmgSource.equals(DamageSource.fall) && !this.canGetFallDmg() ) {
			return false;
		} else {
			if( dmgSource.getEntity() != null && dmgSource.getEntity() instanceof EntityPlayer && !this.isTamed() ) {
				List<Entity> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(
											this, (this.boundingBox.copy()).expand(16D, 16D, 16D)
										);

				for( Entity entity : entities ) {
					if( entity != null && (entity instanceof EntityEnderman) ) {
						((EntityEnderman) entity).setTarget(dmgSource.getEntity());
						((EntityEnderman) entity).setScreaming(true);
					} else if( entity != null && (entity instanceof EntityEndermanESP) ) {
						((EntityEndermanESP) entity).setTarget(dmgSource.getEntity());
						((EntityEndermanESP) entity).setScreaming(true);
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

	public boolean canGetFallDmg() {
		return (this.dataWatcher.getWatchableObjectByte(21) & 16) == 16;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte) 0));
		this.dataWatcher.addObject(17, new Byte((byte) 0));
	}

	@Override
	protected void fall(float fallDist) {
		if( this.canGetFallDmg() ) {
			super.fall(fallDist);
		}
	}

	@Override
	protected Entity findPlayerToAttack() {
		return null;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.3D);
	}

	@Override
	public float getAIMoveSpeed() {
		return this.isSitting() ? 0.0F : (this.isRiddenDW() ? 0.2F + (this.getCoatBaseColor() == 0 ? 0.05F : 0F) : 0.1F);
	}

	@Override
	public float getBlockPathWeight(int x, int y, int z) {
		return 0.5F - this.worldObj.getLightBrightness(x, y, z) * (this.isTamed() ? -1 : 1);
	}

	public float[] getBowColor() {
		int color = ItemRaincoat.colorList.get(this.dataWatcher.getWatchableObjectByte(18)).getColor();

		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;

		return new float[] { red, green, blue };
	}

	@Override
	public boolean getCanSpawnHere() {
		return this.isValidLightLevel() && super.getCanSpawnHere();
	}

	public int getCarried() {
		return this.dataWatcher.getWatchableObjectByte(16);
	}

	public int getCarryingData() {
		return this.dataWatcher.getWatchableObjectByte(17);
	}

	public int getCoat() {
		return this.dataWatcher.getWatchableObjectInt(22);
	}

	@Override
	public int getCoatBaseColor() {
		return this.getCoat() == -1 ? -1 : this.getCoat() >> 5;
	}

	public int getCoatColor() {
		return this.getCoat() == -1 ? -1 : this.getCoat() & 31;
	}

	public byte getColor() {
		return this.dataWatcher.getWatchableObjectByte(18);
	}

	@Override
	protected String getDeathSound() {
		return "enderstuffp:endermiss.death";
	}

	@Override
	protected int getDropItemId() {
		return ItemRegistry.espPearls.itemID;
	}

    @Override
    protected void dropFewItems(boolean hitByPlyr, int lootLvl) {
        int j = this.getDropItemId();

        if( j > 0 && !this.isTamed() ) {
            int k = this.rand.nextInt(2 + lootLvl);

            for( int l = 0; l < k; ++l ) {
            	this.entityDropItem(new ItemStack(j, 1, 2), 0.0F);
            }
        }
    }

	@Override
	public int getEggDmg() {
		return 0;
	}

	@Override
	public EntityCreature getEntity() {
		return this;
	}

	@Override
	public ItemStack getHeldItem() {
		return this.isTamed() ? new ItemStack(Block.plantYellow) : new ItemStack(Block.plantRed);
	}

	@Override
	protected String getHurtSound() {
		return "enderstuffp:endermiss.hit";
	}

	@Override
	protected String getLivingSound() {
		return "enderstuffp:endermiss.idle";
	}

	@Override
	public double getMountedYOffset() {
		return super.getMountedYOffset() + (this.isRidden() ? 0.6F : 0.0F);
	}

	@Override
	public String getName() {
		return this.getCustomNameTag();
	}

    @SuppressWarnings("unchecked")
	private EntityPlayer getOwningPlayerInRng(float distance) {
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
		return this.getHealth();
	}

	@Override
	public float getPetMaxHealth() {
		return this.getMaxHealth();
	}

	@Override
	public boolean interact(EntityPlayer player) {
		if( player.getCurrentEquippedItem() != null ) {
			ItemStack playerItem = player.getCurrentEquippedItem();

			if( CUS.areItemInstEqual(playerItem, Block.plantYellow) && !this.worldObj.isRemote && (!this.isTamed() || this.ownerName.isEmpty()) ) {
				if( this.rand.nextInt(10) == 0 ) {
					this.setTamed(true);
					this.ownerName = player.username;

					if( !this.worldObj.isRemote ) {
						ESPModRegistry.sendPacketAllRng("fxTameAcc", this.posX, this.posY, this.posZ, 128.0D,
							this.dimension, this.posX, this.posY, this.posZ, this.width, this.height
						);
					}

					playerItem.stackSize--;
					return true;
				} else {
					if( !this.worldObj.isRemote ) {
						ESPModRegistry.sendPacketAllRng("fxTameRef", this.posX, this.posY, this.posZ, 128.0D,
							this.dimension, this.posX, this.posY, this.posZ, this.width, this.height
						);
					}

					playerItem.stackSize--;

					return true;
				}
			} else if( this.isTamed() && this.ownerName.equals(player.username) && !this.isRidden() && !this.worldObj.isRemote ) {
				if( CUS.areItemInstEqual(playerItem, ItemRegistry.rainCoat) && this.getCoat() != playerItem.getItemDamage() ) {
					if( this.getCoat() >= 0 && !player.capabilities.isCreativeMode) {
						player.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.rainCoat, 1, this.getCoat()));
					}

					player.inventoryContainer.detectAndSendChanges();
					this.setCoat(playerItem.getItemDamage() >> 5, playerItem.getItemDamage() & 31);

					playerItem.stackSize--;

					return true;
				} else if( CUS.areItemInstEqual(playerItem, ItemRegistry.avisFeather) && this.canGetFallDmg() ) {
					this.setCanGetFallDmg(false);

					playerItem.stackSize--;

					return true;
				} else if( CUS.areItemInstEqual(playerItem, Item.dyePowder) && playerItem.getItemDamage() != this.getColor() ) {
					this.setColor(playerItem.getItemDamage());

					playerItem.stackSize--;

					return true;
				} else if( playerItem.getItem() instanceof ItemFood && this.getHealth() < this.getMaxHealth() ) {
					this.heal(((ItemFood)playerItem.getItem()).getHealAmount());

					if( !this.worldObj.isRemote ) {
						ESPModRegistry.sendPacketAllRng("fxTameAcc", this.posX, this.posY, this.posZ, 128.0D,
							this.dimension, this.posX, this.posY, this.posZ, this.width, this.height
						);
					}

					playerItem.stackSize--;

					return true;
				} else if( CUS.areItemInstEqual(playerItem, ItemRegistry.enderPetStaff) ) {
					PacketRegistry.sendPacketToPlayer(ESPModRegistry.MOD_ID, "showPetGui", (Player)player, this);

					return true;
				}
			}
		}

		if( this.isRidden() && !this.worldObj.isRemote ) {
			player.mountEntity(null);

			return true;
		}

		if( this.isTamed() && !this.worldObj.isRemote ) {
			player.addChatMessage(
				CUS.getTranslated("\247d[%s]\247f " + CUS.getTranslated("enderstuffplus.chat.name"),
						CUS.getTranslated("entity.EnderMiss.name"),
						this.getName().isEmpty() ? EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET : this.getName()
				)
			);

			if( this.ownerName.equals(player.username) ) {
				int percHealth = (int)(this.getHealth() / this.getMaxHealth() * 100F);

				player.addChatMessage("  " + CUS.getTranslated("enderstuffplus.chat.missFriend", percHealth));
			} else {
				player.addChatMessage("  " + CUS.getTranslated("enderstuffplus.chat.stranger" , this.ownerName));
			}

			return true;
		}

		return false;
	}

	@Override
	public boolean isFollowing() {
		return (this.dataWatcher.getWatchableObjectByte(21) & 64) == 64;
	}

	public boolean isImmuneToWater() {
		return this.dataWatcher.getWatchableObjectInt(22) >= 0;
	}

	@Override
	protected boolean isMovementCeased() {
		return this.isSitting();
	}

	@Override
	public boolean isPotionApplicable(PotionEffect effect) {
		return this.getCoatBaseColor() != 1 || !Potion.potionTypes[effect.getPotionID()].isBadEffect();
	}

	public boolean isRidden() {
		return this.isTamed() && this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer;
	}

	public boolean isRiddenDW() {
		try {
			return (this.dataWatcher.getWatchableObjectByte(21) & 32) == 32;
		} catch(NullPointerException e) {
			return false;
		}
	}

	@Override
	public boolean isSitting() {
		try {
			return (this.dataWatcher.getWatchableObjectByte(21) & 4) == 4;
		} catch(NullPointerException e) {
			return false;
		}
	}

	public boolean isSpecial() {
		return (this.dataWatcher.getWatchableObjectByte(21) & 8) == 8;
	}

	public boolean isTamed() {
		return (this.dataWatcher.getWatchableObjectByte(21) & 1) == 1;
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
			this.motionY = 0.41999998688697815D * (this.isRiddenDW() ? 1.4D : 1.0D);

			if( this.isPotionActive(Potion.jump) ) {
				this.motionY += (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
			}

			if( this.getCoatBaseColor() == 3 ) {
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
		float yOff = (float) this.getMountedYOffset() + (this.isRidden() ? 1.2F : 0.5F) + this.yOffset;

		this.boundingBox.setBounds(this.posX - hWidth, this.posY - this.yOffset + this.ySize, this.posZ - hWidth,
				this.posX + hWidth, this.posY - this.yOffset + this.ySize + yOff, this.posZ + hWidth
		);

		super.moveEntity(x, y, z);

		yOff = this.height;

		this.boundingBox.setBounds(this.posX - hWidth, this.posY - this.yOffset + this.ySize, this.posZ - hWidth,
				this.posX + hWidth, this.posY - this.yOffset + this.ySize + yOff, this.posZ + hWidth
		);
	}

	public boolean needFood() {
		return this.getHealth() < (this.getMaxHealth() / 100F) * 20F;
	}

	@Override
	public void onLivingUpdate() {
		if( !this.worldObj.isRemote ) {
			if( (this.getCoat() & 31) >= ItemRaincoat.colorList.size() ) {
				this.dataWatcher.updateObject(22, -1);
			}
		}

		if( this.jumpTicks > 0 ) {
			this.jumpTicks--;
		}

		if( !this.worldObj.isRemote ) {
			this.setRiddenDW(this.isRidden());
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

		EntityPlayer ep = this.getOwningPlayerInRng(25F);

        AttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

        attributeinstance.removeModifier(speedyTamed);

		if( this.isTamed()
			&& !this.isSitting()
			&& !this.isRidden()
			&& ep != null
			&& this.getDistanceToEntity(ep) > 2F
			&& this.isFollowing() )
		{
			this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 24F, false, false, !this.isImmuneToWater(), !this.isImmuneToWater()));
            attributeinstance.applyModifier(speedyTamed);

            if( this.getDistanceToEntity(ep) > 10F && this.teleportTimer <= 0 && Math.abs(ep.posY - this.posY) < 6F ) {
				this.teleportToEntity(ep);
			}
		} else if( this.isTamed()
				   && !this.isRidden()
				   && ep != null
				   && ep.getCurrentEquippedItem() != null
				   && ep.getCurrentEquippedItem().getItem() instanceof ItemFood
				   && this.getDistanceToEntity(ep) > 2F
				   && this.needFood()
				   && !this.isSitting() )
		{
            attributeinstance.applyModifier(speedyTamed);
			this.setPathToEntity(
					this.worldObj.getPathEntityToEntity(this, ep, 8F, false, false, !this.isImmuneToWater(), !this.isImmuneToWater())
			);
		}

		if( this.isWet() && !this.isImmuneToWater() ) {
			this.attackEntityFrom(DamageSource.drown, 1);
		}

		this.entityToAttack = null;

        if( !this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") && ConfigRegistry.griefing ) {
        	int x, y, z, blockID;

            if( this.getCarried() == 0 && !this.isTamed() ) {
                if( this.rand.nextInt(20) == 0 ) {
                    x = MathHelper.floor_double(this.posX - 2.0D + this.rand.nextDouble() * 4.0D);
                    y = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 3.0D);
                    z = MathHelper.floor_double(this.posZ - 2.0D + this.rand.nextDouble() * 4.0D);
                    blockID = this.worldObj.getBlockId(x, y, z);

                    if( carriableBlocks[blockID] ) {
                        this.setCarried(this.worldObj.getBlockId(x, y, z));
                        this.setCarryingData(this.worldObj.getBlockMetadata(x, y, z));
                        this.worldObj.setBlock(x, y, z, 0);
                    }
                }
            } else if( (this.rand.nextInt(2000) == 0 || this.isTamed()) && this.getCarried() != 0 ) {
                x = MathHelper.floor_double(this.posX - 1.0D + this.rand.nextDouble() * 2.0D);
                y = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 2.0D);
                z = MathHelper.floor_double(this.posZ - 1.0D + this.rand.nextDouble() * 2.0D);
                blockID = this.worldObj.getBlockId(x, y, z);

                int belowBID = this.worldObj.getBlockId(x, y - 1, z);

                if( blockID == 0 && belowBID > 0 && Block.blocksList[belowBID].renderAsNormalBlock() ) {
                    this.worldObj.setBlock(x, y, z, this.getCarried(), this.getCarryingData(), 3);
                    this.setCarried(0);
                }
            }
        }

       	this.spawnParticle("livingUpd", this.posX, this.posY, this.posZ);

		if( this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isTamed() ) {
			float bright = this.getBrightness(1.0F);

			if( bright > 0.5F
					&& this.worldObj.canBlockSeeTheSky(
							MathHelper.floor_double(this.posX),
							MathHelper.floor_double(this.posY),
							MathHelper.floor_double(this.posZ)
					   )
					&& this.rand.nextFloat() * 30.0F < (bright - 0.4F) * 2.0F )
			{
				this.teleportRandomly();
			}
		}

		if( this.isWet() && this.getCoat() < 0 && !this.isTamed() ) {
			this.teleportRandomly();
		}

		if( !this.worldObj.isRemote && this.isEntityAlive() ) {
			this.teleportDelay = 0;
		}

        this.updateArmSwingProgress();
		super.onLivingUpdate();

		if( this.isRiddenDW() && this.riddenByEntity != null ) {
            attributeinstance.applyModifier(speedyTamed);

			this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 5.0F;
			EntityPlayer player = (EntityPlayer) this.riddenByEntity;
			this.rotationYawHead = player.rotationYawHead;

			this.setRotation(player.rotationYaw, 0.0F);

			this.stepHeight = 1.0F;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void processRiding(EntityPlayerSP player) {
		if( ESPModRegistry.isJumping(player) && this.jumpTicks == 0 ) {
			this.jumpTicks = 15;

			ESPModRegistry.proxy.setJumping(true, this);
		}

		PacketRegistry.sendPacketToServer(
				ESPModRegistry.MOD_ID, "riddenMove", player.movementInput.moveForward, player.movementInput.moveStrafe
		);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readEntityFromNBT(par1nbtTagCompound);

		this.setTamed(par1nbtTagCompound.getBoolean("tamed"));

		if( par1nbtTagCompound.hasKey("coatColor") ) {
			this.dataWatcher.updateObject(22, par1nbtTagCompound.getInteger("coatColor"));
		} else {
			this.setCoat(0, par1nbtTagCompound.getBoolean("immuneToWater") ? 16 : -1);
		}

		this.setCanGetFallDmg(par1nbtTagCompound.getBoolean("fallDmg"));
		this.setSitting(par1nbtTagCompound.getBoolean("sitting"));
		this.setSpecial(par1nbtTagCompound.getBoolean("special"));
		this.setFollowing(par1nbtTagCompound.getBoolean("follow"));
		this.setCarried(par1nbtTagCompound.getShort("carried"));
		this.setCarryingData(par1nbtTagCompound.getShort("carriedData"));
		this.setColor(par1nbtTagCompound.getByte("clrID"));

		this.ownerName = (par1nbtTagCompound.getString("owner"));

		if( par1nbtTagCompound.hasKey("petName") ) {
			this.setName(par1nbtTagCompound.getString("petName"));
		}

		if( this.getName().equals(EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET) ) {
			this.setName("");
		}
	}

	private void setBoolean(boolean b, int flag, int dwId) {
		byte prevByte = this.dataWatcher.getWatchableObjectByte(dwId);

		this.dataWatcher.updateObject(dwId, (byte)(b ? prevByte | flag : prevByte & ~flag));
	}

	public void setCanGetFallDmg(boolean b) {
		this.setBoolean(b, 16, 21);
	}

	public void setCarried(int par1) {
		this.dataWatcher.updateObject(16, Byte.valueOf((byte) (par1 & 255)));
	}

	public void setCarryingData(int par1) {
		this.dataWatcher.updateObject(17, Byte.valueOf((byte) (par1 & 255)));
	}

	public void setCoat(int base, int color) {
		if( base == 4 ) {
	        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(60.0D);
		} else {
	        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(40.0D);
	        this.setHealth(Math.min(this.getPetHealth(), 40.0F));
		}

		this.dataWatcher.updateObject(22, color == -1 ? -1 : (color & 31) | (base << 5));
	}

	public void setColor(int clr) {
		this.dataWatcher.updateObject(18, new Byte((byte)clr));
	}

	@Override
	public void setFollowing(boolean b) {
		this.setBoolean(b, 64, 21);
	}

	@Override
	public void setName(String name) {
		this.setCustomNameTag(name);
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
		this.setBoolean(b, 32, 21);
	}

	@Override
    public void setSitting(boolean b) {
		this.setBoolean(b, 4, 21);
	}

	public void setSpecial(boolean b) {
		this.setBoolean(b, 8, 21);
	}

	public void setTamed(boolean b) {
		this.setBoolean(b, 1, 21);
	}

	@Override
	public boolean shouldRiderFaceForward(EntityPlayer player) {
		return true;
	}

    public void spawnParticle(String type, double X, double Y, double Z) {
		ESPModRegistry.sendPacketAllRng("fxPortal", this.posX, this.posY, this.posZ, 128.0D, this.dimension,
				this.posX, this.posY, this.posZ, 1.0F, 0.5F, 0.7F, this.width, this.height
		);
    }

	protected boolean teleportRandomly() {
		double var1 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
		double var3 = this.posY + (this.rand.nextInt(64) - 32);
		double var5 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;

		return this.teleportTo(var1, var3, var5);
	}

	protected boolean teleportTo(double par1, double par3, double par5) {
        EnderTeleportEvent event = new EnderTeleportEvent(this, par1, par3, par5, 0);

        if( MinecraftForge.EVENT_BUS.post(event) ){
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
        int l;

        if( this.worldObj.blockExists(blockX, blockY, blockZ)) {
            boolean blockSolid = false;

            while( !blockSolid && blockY > 0 ) {
                l = this.worldObj.getBlockId(blockX, blockY - 1, blockZ);

                if( l != 0 && Block.blocksList[l].blockMaterial.blocksMovement() ) {
                    blockSolid = true;
                } else {
                    --this.posY;
                    --blockY;
                }
            }

            if( blockSolid ) {
                this.setPosition(this.posX, this.posY, this.posZ);

                if( this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && (!this.worldObj.isAnyLiquid(this.boundingBox) || this.isImmuneToWater()) ) {
                    teleportSucceed = true;
                }
            }
        }

        if( !teleportSucceed ) {
            this.setPosition(prevPosX, prevPosY, prevPosZ);

            return false;
		} else {
            short partCnt = 128;

            for( l = 0; l < partCnt; ++l ) {
                double posMulti = l / (partCnt - 1.0D);
                double partX = prevPosX + (this.posX - prevPosX) * posMulti + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
                double partY = prevPosY + (this.posY - prevPosY) * posMulti + this.rand.nextDouble() * this.height;
                double partZ = prevPosZ + (this.posZ - prevPosZ) * posMulti + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;

                this.spawnParticle("teleport", partX, partY, partZ);
            }

			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "mob.endermen.portal", 1.0F, 1.0F);
			this.worldObj.playSoundAtEntity(this, "mob.endermen.portal", 1.0F, 1.0F);

			return true;
		}
	}

	protected boolean teleportToEntity(Entity par1Entity) {
		Vec3 var2 = Vec3.createVectorHelper(this.posX - par1Entity.posX,
						this.boundingBox.minY + this.height / 2.0F - par1Entity.posY + par1Entity.getEyeHeight(), this.posZ - par1Entity.posZ
					);

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

			this.setTarget((Entity) null);
		}

		if( !this.isRiddenDW() ) {
			this.jumpMovementFactor = (this.getAIMoveSpeed() * 1.6F) / 5.0F;

			super.updateEntityActionState();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeEntityToNBT(par1nbtTagCompound);

		par1nbtTagCompound.setBoolean("tamed", this.isTamed());
		par1nbtTagCompound.setBoolean("fallDmg", this.canGetFallDmg());
		par1nbtTagCompound.setBoolean("sitting", this.isSitting());
		par1nbtTagCompound.setBoolean("special", this.isSpecial());
		par1nbtTagCompound.setBoolean("follow", this.isFollowing());
		par1nbtTagCompound.setInteger("coatColor", this.getCoat());
		par1nbtTagCompound.setShort("carried", (short) this.getCarried());
		par1nbtTagCompound.setShort("carriedData", (short) this.getCarryingData());
		par1nbtTagCompound.setByte("clrID", this.getColor());
		par1nbtTagCompound.setString("owner", this.ownerName);
	}
}
