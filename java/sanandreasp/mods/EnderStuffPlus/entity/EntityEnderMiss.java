package sanandreasp.mods.EnderStuffPlus.entity;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import sanandreasp.core.manpack.mod.packet.PacketRegistry;
import sanandreasp.mods.EnderStuffPlus.client.packet.PacketShowPetGUI;
import sanandreasp.mods.EnderStuffPlus.item.ItemRaincoat;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRiddenMove;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityEnderMiss extends EntityCreature implements IEnderPet, IEnderCreature
{
	private static final UUID entityUUID = UUID.fromString("2B4AD668-5FE7-4338-B476-B035D7B5E80A");
    private static final AttributeModifier attributes = (new AttributeModifier(entityUUID, "Attacking speed boost", 6.199999809265137D, 0)).setSaved(false);
    
	public String ownerName = "";
	public int jumpTicks = 0;

	protected float walkSpeed;
	protected int attackStrength = 0;
	protected static boolean[] canCarryBlocks = new boolean[Block.blocksList.length];

	protected int teleportDelay = 0;
	public int teleportTimer = 0;
	
	private int prevBaseClr = -1;
	
	static {
		canCarryBlocks[Block.grass.blockID] = true;
		canCarryBlocks[Block.dirt.blockID] = true;
		canCarryBlocks[Block.sand.blockID] = true;
		canCarryBlocks[Block.gravel.blockID] = true;
		canCarryBlocks[Block.plantYellow.blockID] = true;
		canCarryBlocks[Block.plantRed.blockID] = true;
		canCarryBlocks[Block.mushroomBrown.blockID] = true;
		canCarryBlocks[Block.mushroomRed.blockID] = true;
		canCarryBlocks[Block.tnt.blockID] = true;
		canCarryBlocks[Block.cactus.blockID] = true;
		canCarryBlocks[Block.blockClay.blockID] = true;
		canCarryBlocks[Block.pumpkin.blockID] = true;
		canCarryBlocks[Block.melon.blockID] = true;
		canCarryBlocks[Block.mycelium.blockID] = true;
	}
	
	public EntityEnderMiss(World par1World) {
		super(par1World);
		this.attackStrength = 0;
		this.setSize(0.6F, 2.9F);
		this.stepHeight = 1.0F;
		this.walkSpeed = this.getAIMoveSpeed() * 1.6F;
		
		this.dataWatcher.addObject(18, new Byte((byte) this.rand.nextInt(ItemRaincoat.colorList.size() - 3)));
		this.dataWatcher.addObject(21, new Byte((byte) 0));
		this.dataWatcher.addObject(22, -1);
		
		if( this.rand.nextInt(1024) == 0 ) {
			setCoat(this.rand.nextInt(2), this.rand.nextInt(ItemRaincoat.colorList.size()));
		}
		setSpecial(this.rand.nextInt(16) == 0);
		setCanGetFallDmg(true);
	}
	
	@Override
	protected void attackEntity(Entity par1Entity, float par2) {

	}
	
	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if( !this.worldObj.isRemote )
			setSitting(false);
		
		if( this.isRidden() && par1DamageSource != null && par1DamageSource.getEntity() instanceof EntityPlayer )
			return false;

		if( par1DamageSource instanceof EntityDamageSourceIndirect && !this.isTamed() ) {
			for( int var3 = 0; var3 < 64; ++var3 ) {
				if( this.teleportRandomly() ) {
					return false;
				}
			}

			return false;
		} else if( this.isTamed() && par1DamageSource.equals(DamageSource.fall) && !this.canGetFallDmg() ) {
			return false;
		} else {
			if( par1DamageSource.getEntity() != null && par1DamageSource.getEntity() instanceof EntityPlayer && !this.isTamed() ) {
				List<Entity> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, (this.boundingBox.copy()).expand(16D, 16D, 16D));
				for( Entity entity : entities ) {
					if( entity != null && (entity instanceof EntityEnderman) ) {
						((EntityEnderman) entity).setTarget((EntityPlayer)par1DamageSource.getEntity());
						((EntityEnderman) entity).setScreaming(true);
					} else if( entity != null && (entity instanceof EntityEndermanESP) ) {
						((EntityEndermanESP) entity).setTarget((EntityPlayer)par1DamageSource.getEntity());
						((EntityEndermanESP) entity).setScreaming(true);
					}
				}
			}
			
			return super.attackEntityFrom(par1DamageSource, par2);
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
	
	protected void decrStackSize(EntityPlayer ep, ItemStack item) {
		item.stackSize--;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte) 0));
		this.dataWatcher.addObject(17, new Byte((byte) 0));
	}
	
	@Override
	protected void fall(float par1) {
		if( this.canGetFallDmg()) super.fall(par1 );
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
	public float getBlockPathWeight(int par1, int par2, int par3) {
		return 0.5F - this.worldObj.getLightBrightness(par1, par2, par3) * (this.isTamed() ? -1 : 1);
	}
	
	public float[] getBowColor() {
		int color = ItemRaincoat.colorList.get(this.dataWatcher.getWatchableObjectByte(18)).getColor();
		
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;

		return new float[] { red, green, blue };
	}
	
	@Override
	public float getBrightness(float par1) {
		return super.getBrightness(par1);
	}
	
	@Override
	public int getBrightnessForRender(float par1) {
		return super.getBrightnessForRender(par1);
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
		return getCoat() == -1 ? -1 : this.getCoat() >> 5;
	}

	public int getCoatColor() {
		return getCoat() == -1 ? -1 : this.getCoat() & 31;
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
		return ESPModRegistry.espPearls.itemID;
	}

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int j = this.getDropItemId();

        if( j > 0 && !this.isTamed() ) {
            int k = this.rand.nextInt(2 + par2);

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
	private EntityPlayer getOwningPlayer(float distance) {
		if( !this.worldObj.isRemote && this.isTamed() ) {
			Iterator<EntityPlayer> players = this.worldObj.playerEntities.iterator();
			while(players.hasNext()) {
				EntityPlayer currPlayer = players.next();
				if( this.ownerName.equals(currPlayer.username) ) {
					if( this.getDistanceToEntity(currPlayer) < distance )
						return currPlayer;
					else
						return null;
				}
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
	public boolean interact(EntityPlayer par1EntityPlayer) {
		if( par1EntityPlayer.getCurrentEquippedItem() != null ) {
			ItemStack playerItem = par1EntityPlayer.getCurrentEquippedItem();
			if( playerItem.itemID == Block.plantYellow.blockID && !this.worldObj.isRemote && (!this.isTamed() || ownerName.isEmpty()) ) {
				if( this.rand.nextInt(10) == 0 ) {
					this.setTamed(true);
					this.ownerName = par1EntityPlayer.username;
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
			} else if( this.isTamed() && this.ownerName.equals(par1EntityPlayer.username) && !isRidden() && !worldObj.isRemote ) {
				if( playerItem.itemID == ESPModRegistry.rainCoat.itemID && this.getCoat() != playerItem.getItemDamage() ) {
					if( this.getCoat() >= 0 && !par1EntityPlayer.capabilities.isCreativeMode)
						par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(ESPModRegistry.rainCoat, 1, this.getCoat()));
					par1EntityPlayer.inventoryContainer.detectAndSendChanges();
					this.setCoat(playerItem.getItemDamage() >> 5, playerItem.getItemDamage() & 31);
					playerItem.stackSize--;
					return true;
				} else if( playerItem.getItem() == ESPModRegistry.avisFeather && this.canGetFallDmg() ) {
					setCanGetFallDmg(false);
					playerItem.stackSize--;
					return true;
				} else if( playerItem.getItem() == Item.dyePowder && playerItem.getItemDamage() != this.getColor() ) {
					setColor(playerItem.getItemDamage());
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
				} else if( playerItem.getItem() == ESPModRegistry.enderPetStaff ) {
					PacketRegistry.sendPacketToPlayer(ESPModRegistry.modID, "showPetGui", (Player)par1EntityPlayer, this);
					return true;
				}
			}
		}

		if( this.isRidden() && !this.worldObj.isRemote ) {
			par1EntityPlayer.mountEntity(null);
			return true;
		}
		
		if( this.isTamed() && !this.worldObj.isRemote ) {
			par1EntityPlayer.addChatMessage(
				String.format("\247d[%s]\247f " + StatCollector.translateToLocal("enderstuffplus.chat.name"),
				StatCollector.translateToLocal("entity.EnderMiss.name"), this.getName().isEmpty() ? EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET : this.getName())
			);
			if( this.ownerName.equals(par1EntityPlayer.username) ) {
				int percHealth = (int)(this.getHealth() / this.getMaxHealth() * 100F);
				par1EntityPlayer.addChatMessage("  "+String.format(StatCollector.translateToLocal("enderstuffplus.chat.missFriend"), percHealth));
			} else {
				par1EntityPlayer.addChatMessage("  "+String.format(StatCollector.translateToLocal("enderstuffplus.chat.stranger"), ownerName));
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
	public boolean isPotionApplicable(PotionEffect par1PotionEffect) {
		return this.getCoatBaseColor() != 1 || !Potion.potionTypes[par1PotionEffect.getPotionID()].isBadEffect();
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
				float var1 = this.rotationYaw * 0.017453292F;
				this.motionX -= MathHelper.sin(var1) * 0.2F;
				this.motionZ += MathHelper.cos(var1) * 0.2F;
			}

			this.isAirBorne = true;
		}
		this.isJumping = false;
	}

	@Override
	public void moveEntity(double var1, double var3, double var5) {
		float var7 = this.width / 2.0F;

		float var8 = (float) this.getMountedYOffset() + (this.isRidden() ? 1.2F : 0.5F) + this.yOffset;
		this.boundingBox.setBounds(this.posX - var7,
				this.posY - this.yOffset + this.ySize,
				this.posZ - var7,
				this.posX + var7,
				this.posY - this.yOffset + this.ySize + var8,
				this.posZ + var7);
		super.moveEntity(var1, var3, var5);
		var8 = this.height;
		this.boundingBox.setBounds(this.posX - var7,
				this.posY - this.yOffset + this.ySize,
				this.posZ - var7,
				this.posX + var7,
				this.posY - this.yOffset + this.ySize + var8,
				this.posZ + var7);
	}

	public boolean needFood() {
		return this.getHealth() < ((float)this.getMaxHealth() / 100F) * 20F;
	}
	
	@Override
	public void onLivingUpdate() {
		if( !this.worldObj.isRemote ) {
			if( (this.getCoat() & 31) >= ItemRaincoat.colorList.size() ) {
				this.dataWatcher.updateObject(22, -1);
			}
			
			if( this.prevBaseClr != this.getCoatBaseColor() ) {
				if( this.getCoatBaseColor() == 4 ) {
			        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(60.0D);
				} else {
			        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(40.0D);
			        this.setHealth(Math.min(this.getPetHealth(), 40.0F));
				}
				this.prevBaseClr = this.getCoatBaseColor();
			}
		}
		
		if( jumpTicks > 0 ) jumpTicks--;
		
		if( !worldObj.isRemote ) setRiddenDW(isRidden());
		
		if( this.teleportTimer > 0 ) this.teleportTimer--;
		
		if( this.isRidden() && this.riddenByEntity.isDead && this.isTamed() && !this.worldObj.isRemote )
			this.setSitting(true);

		if( this.isSitting() ) {
			this.height = 0.9F;
		} else {
			this.height = 2.9F;
		}

		EntityPlayer ep = this.getOwningPlayer(25F);
        AttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        attributeinstance.removeModifier(attributes);

		if( this.isTamed() && !this.isSitting() && !this.isRidden() && ep != null && this.getDistanceToEntity(ep) > 2F
				&& this.ownerName.equals(ep.username)
				&& this.isFollowing() )
		{
			this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 24F, false, false, !this.isImmuneToWater(), !this.isImmuneToWater()));
            attributeinstance.applyModifier(attributes);
			if( this.getDistanceToEntity(ep) > 10F && this.teleportTimer <= 0 && Math.abs(ep.posY - this.posY) < 6F ) {
				this.teleportToEntity(ep);
			}
		} else if( this.isTamed() && !this.isRidden() && ep != null && ep.getCurrentEquippedItem() != null
				&& ep.getCurrentEquippedItem().getItem() instanceof ItemFood
				&& this.getDistanceToEntity(ep) > 2F
				&& this.needFood() && !this.isSitting() )
		{
            attributeinstance.applyModifier(attributes);
			this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 8F, false, false, !this.isImmuneToWater(), !this.isImmuneToWater()));
		}

		if( this.isWet() && !this.isImmuneToWater() ) {
			this.attackEntityFrom(DamageSource.drown, 1);
		}

		this.entityToAttack = null;

        if( !this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") ) {
        	int i;
            int j;
            int k;
            int l;

            if( this.getCarried() == 0 && !this.isTamed()) {
                if( this.rand.nextInt(20) == 0 ) {
                    i = MathHelper.floor_double(this.posX - 2.0D + this.rand.nextDouble() * 4.0D);
                    j = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 3.0D);
                    k = MathHelper.floor_double(this.posZ - 2.0D + this.rand.nextDouble() * 4.0D);
                    l = this.worldObj.getBlockId(i, j, k);

                    if( canCarryBlocks[l] ) {
                        this.setCarried(this.worldObj.getBlockId(i, j, k));
                        this.setCarryingData(this.worldObj.getBlockMetadata(i, j, k));
                        this.worldObj.setBlock(i, j, k, 0);
                    }
                }
            } else if( (this.rand.nextInt(2000) == 0 || this.isTamed()) && this.getCarried() != 0 ) {
                i = MathHelper.floor_double(this.posX - 1.0D + this.rand.nextDouble() * 2.0D);
                j = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 2.0D);
                k = MathHelper.floor_double(this.posZ - 1.0D + this.rand.nextDouble() * 2.0D);
                l = this.worldObj.getBlockId(i, j, k);
                int i1 = this.worldObj.getBlockId(i, j - 1, k);

                if( l == 0 && i1 > 0 && Block.blocksList[i1].renderAsNormalBlock() ) {
                    this.worldObj.setBlock(i, j, k, this.getCarried(), this.getCarryingData(), 3);
                    this.setCarried(0);
                }
            }
        }

       	this.spawnParticle("livingUpd", this.posX, this.posY, this.posZ);

		if( this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isTamed() ) {
			float var6 = this.getBrightness(1.0F);

			if (var6 > 0.5F
					&& this.worldObj.canBlockSeeTheSky(
							MathHelper.floor_double(this.posX),
							MathHelper.floor_double(this.posY),
							MathHelper.floor_double(this.posZ))
					&& this.rand.nextFloat() * 30.0F < (var6 - 0.4F) * 2.0F) {
				this.teleportRandomly();
			}
		}

		if( this.isWet() && !this.isTamed() ) {
			this.teleportRandomly();
		}

		if( !this.worldObj.isRemote && this.isEntityAlive() ) {
			this.teleportDelay = 0;
		}
		
		super.onLivingUpdate();
		
		if( isRiddenDW() && riddenByEntity != null ) {
            attributeinstance.applyModifier(attributes);
			this.jumpMovementFactor = this.walkSpeed / 5.0F;
			EntityPlayer var1 = (EntityPlayer) this.riddenByEntity;
			this.rotationYawHead = var1.rotationYawHead;
			this.setRotation(var1.rotationYaw, 0.0F);
			
			this.stepHeight = 1.0F;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void processRiding(EntityPlayerSP player) {
		if( ESPModRegistry.isJumping(player) && jumpTicks == 0 ) {
			jumpTicks = 15;
			ESPModRegistry.proxy.setJumping(true, this);
		}
		PacketRegistry.sendPacketToServer(ESPModRegistry.modID, "riddenMove", player.movementInput.moveForward, player.movementInput.moveStrafe);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readEntityFromNBT(par1nbtTagCompound);
		this.setTamed(par1nbtTagCompound.getBoolean("tamed"));
		if( par1nbtTagCompound.hasKey("coatColor") )
			this.dataWatcher.updateObject(22, par1nbtTagCompound.getInteger("coatColor"));
		else
			this.setCoat(0, par1nbtTagCompound.getBoolean("immuneToWater") ? 16 : -1);
		this.setCanGetFallDmg(par1nbtTagCompound.getBoolean("fallDmg"));
		this.setSitting(par1nbtTagCompound.getBoolean("sitting"));
		this.setSpecial(par1nbtTagCompound.getBoolean("special"));
		this.setFollowing(par1nbtTagCompound.getBoolean("follow"));
		this.setCarried(par1nbtTagCompound.getShort("carried"));
		this.setCarryingData(par1nbtTagCompound.getShort("carriedData"));
		this.setColor(par1nbtTagCompound.getByte("clrID"));
		this.ownerName = (par1nbtTagCompound.getString("owner"));
		if( par1nbtTagCompound.hasKey("petName"))
			this.setName(par1nbtTagCompound.getString("petName"));
		if( this.getName().equals(EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET) )
			this.setName("");
	}
	
	private void setBoolean(boolean b, int flag, int dwId) {
		byte prevByte = this.dataWatcher.getWatchableObjectByte(dwId);
		this.dataWatcher.updateObject(dwId, (byte)(b ? prevByte | flag : prevByte & ~flag));
	}

	public void setCanGetFallDmg(boolean b) {
		setBoolean(b, 16, 21);
	}
	public void setCarried(int par1) {
		this.dataWatcher.updateObject(16, Byte.valueOf((byte) (par1 & 255)));
	}
	
	public void setCarryingData(int par1) {
		this.dataWatcher.updateObject(17, Byte.valueOf((byte) (par1 & 255)));
	}

	public void setCoat(int base, int color) {
		this.dataWatcher.updateObject(22, color == -1 ? -1 : (color & 31) | (base << 5));
	}
	
	public void setColor(int clr) {
		this.dataWatcher.updateObject(18, new Byte((byte)clr));
	}
	
	@Override
	public void setFollowing(boolean b) {
		setBoolean(b, 64, 21);
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
		setBoolean(b, 32, 21);
	}
	
	public void setSitting(boolean b) {
		setBoolean(b, 4, 21);
	}

	public void setSpecial(boolean b) {
		setBoolean(b, 8, 21);
	}

	public void setTamed(boolean b) {
		setBoolean(b, 1, 21);
	}

	@Override
	public boolean shouldRiderFaceForward(EntityPlayer player) {
		return true;
	}

    public void spawnParticle(String type, double X, double Y, double Z) {
		ESPModRegistry.sendPacketAllRng(
				"fxPortal", this.posX, this.posY, this.posZ, 128.0D, this.dimension, this.posX, this.posY,
				this.posZ, 1.0F, 0.5F, 0.7F, this.width, this.height);
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

        if( this.worldObj.blockExists(i, j, k)) {
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

                if( this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && (!this.worldObj.isAnyLiquid(this.boundingBox) || this.isImmuneToWater()) ) {
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
                double d6 = (double)l / ((double)short1 - 1.0D);
                double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * (double)this.height;
                double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                this.spawnParticle("teleport", d7, d8, d9);
            }

			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "mob.endermen.portal", 1.0F, 1.0F);
			this.worldObj.playSoundAtEntity(this, "mob.endermen.portal", 1.0F, 1.0F);
			return true;
		}
	}

	protected boolean teleportToEntity(Entity par1Entity) {
		Vec3 var2 = Vec3.createVectorHelper(this.posX - par1Entity.posX,
				this.boundingBox.minY + this.height / 2.0F - par1Entity.posY + par1Entity.getEyeHeight(),
				this.posZ - par1Entity.posZ);
		var2 = var2.normalize();
		double var3 = 16.0D;
		double var5 = this.posX + (this.rand.nextDouble() - 0.5D) * 0.8D - var2.xCoord * var3;
		double var7 = this.posY + (this.rand.nextInt(16) - 8) - var2.yCoord * var3;
		double var9 = this.posZ + (this.rand.nextDouble() - 0.5D) * 0.8D - var2.zCoord * var3;
		return this.teleportTo(var5, var7, var9);
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
			this.jumpMovementFactor = this.walkSpeed / 5.0F;

			super.updateEntityActionState();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setBoolean("tamed", this.isTamed());
		par1nbtTagCompound.setInteger("coatColor", this.getCoat());
		par1nbtTagCompound.setBoolean("fallDmg", this.canGetFallDmg());
		par1nbtTagCompound.setBoolean("sitting", this.isSitting());
		par1nbtTagCompound.setBoolean("special", this.isSpecial());
		par1nbtTagCompound.setBoolean("follow", this.isFollowing());
		par1nbtTagCompound.setShort("carried", (short) this.getCarried());
		par1nbtTagCompound.setShort("carriedData", (short) this.getCarryingData());
		par1nbtTagCompound.setByte("clrID", this.getColor());
		par1nbtTagCompound.setString("owner", this.ownerName);
	}
}
