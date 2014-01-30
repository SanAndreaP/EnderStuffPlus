package sanandreasp.mods.EnderStuffPlus.entity;
import java.util.Iterator;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
import sanandreasp.mods.EnderStuffPlus.client.packet.PacketRecvShowPetGUI;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.item.ItemRaincoat;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvMove;
import sanandreasp.mods.EnderStuffPlus.packet.PacketsSendToClient;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityEnderAvis extends EntityCreature implements IEnderPet, IEnderCreature, Textures {
	public int ticksFlying = 0;
	public float field_752_b = 0.0F;
	public float destPos = 0.0F;
	public float field_757_d;
	public float field_756_e;
	public float field_755_h = 1.0F;
	protected float walkSpeed;
	public float currFlightCondition = 10.0F;
	private String ownerName = "";
	protected double prevMotionY = 0D;
	
	private int prevBaseClr = -1;
	
	public EntityEnderAvis(World par1World) {
		super(par1World);
		this.walkSpeed = this.getAIMoveSpeed() * 1.6F;
		
		this.dataWatcher.addObject(15, new Byte((byte) 0));
		this.dataWatcher.addObject(16, new Byte((byte) this.rand.nextInt(ItemRaincoat.dyeColors.length - 3)));
		this.dataWatcher.addObject(17, (int)20);
		this.dataWatcher.addObject(22, -1);
	}
	
	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		if (this.attackTime <= 0 && par2 < 2.0F
		&& par1Entity.boundingBox.maxY > this.boundingBox.minY
		&& par1Entity.boundingBox.minY < this.boundingBox.maxY) {
			this.attackTime = 20;
			this.attackEntityAsMob(par1Entity);
		}
	}
	
	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		float var2 = 2F;

		if( this.isPotionActive(Potion.damageBoost) ) {
			var2 += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
		}

		if( this.isPotionActive(Potion.weakness) ) {
			var2 -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
		}

		if( par1Entity instanceof EntityLivingBase ) {
			((EntityLivingBase) par1Entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 0));
			((EntityLivingBase) par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, 300, 0));
		}

		return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if( this.isRidden() && par1DamageSource != null && par1DamageSource.getEntity() instanceof EntityPlayer )
			return false;
		
		if( super.attackEntityFrom(par1DamageSource, par2) ) {
			Entity var3 = par1DamageSource.getEntity();

			if( this.riddenByEntity != var3 && this.ridingEntity != var3 && !this.isTamed() ) {
				if( var3 != this ) {
					this.entityToAttack = var3;
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
		return !isTamed();
	}
	
	public boolean canFly() {
		return (this.dataWatcher.getWatchableObjectByte(15) & 64) == 64;
	}
	
	@Override
	protected void fall(float par1) {
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.3D);
	}
	
	@Override
	public float getAIMoveSpeed() {
		return this.isSitting() ? 0.0F : (this.entityToAttack != null || this.isRiddenDW() ? 0.175F + (this.getCoatBaseColor() == 0 ? 0.05F : 0F) : 0.1F);
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return this.isValidLightLevel() && super.getCanSpawnHere();
	}
	
	public int getCoat() {
		return this.dataWatcher.getWatchableObjectInt(22);
	}
	
	public int getCoatBaseColor() {
		return getCoat() == -1 ? -1 : this.getCoat() >> 5;
	}
	
	public int getCoatColor() {
		return getCoat() == -1 ? -1 : this.getCoat() & 31;
	}
	
	public float[] getCollarColor(int id) {
		float red = (ItemRaincoat.dyeColors[id] >> 16 & 255) / 255.0F;
		float green = (ItemRaincoat.dyeColors[id] >> 8 & 255) / 255.0F;
		float blue = (ItemRaincoat.dyeColors[id] & 255) / 255.0F;

		return new float[] { red, green, blue };
	}
	
	public int getColor() {
		return this.dataWatcher.getWatchableObjectByte(16);
	}
	
	public int getCurrFlightCondInt() {
		return this.dataWatcher.getWatchableObjectInt(17);
	}
	
	@Override
	protected int getDropItemId() {
		return ESPModRegistry.avisFeather.itemID;
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
		return super.getHealth();
	}

	@Override
	public float getPetMaxHealth() {
		return this.getMaxHealth();
	}

	private void increaseCondition(float amount) {
		this.currFlightCondition += amount;
		
		if( this.currFlightCondition > 10.0F ) {
			this.currFlightCondition = 10.0F;
		}
	}
	
	@Override
	public boolean interact(EntityPlayer par1EntityPlayer) {
		if( par1EntityPlayer.getCurrentEquippedItem() != null ) {
			ItemStack playerItem = par1EntityPlayer.getCurrentEquippedItem();
			if( this.isTamed() && this.ownerName.equals(par1EntityPlayer.username) && !worldObj.isRemote && !isRidden() ) {
				if( playerItem.itemID == ESPModRegistry.rainCoat.itemID && this.getCoat() != playerItem.getItemDamage() ) {
					if( this.getCoat() >= 0 && !par1EntityPlayer.capabilities.isCreativeMode )
						par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(ESPModRegistry.rainCoat, 1, this.getCoat()));
					par1EntityPlayer.inventoryContainer.detectAndSendChanges();
					this.setCoat(playerItem.getItemDamage() >> 5, playerItem.getItemDamage() & 31);
					playerItem.stackSize--;
					return true;
				} else if( playerItem.itemID == Item.dyePowder.itemID && playerItem.getItemDamage() != this.getColor() ) {
					setColor(playerItem.getItemDamage());
					playerItem.stackSize--;
					return true;
				} else if( playerItem.getItem() instanceof ItemFood && this.getHealth() < this.getMaxHealth() ) {
					this.heal(((ItemFood)playerItem.getItem()).getHealAmount());
					PacketsSendToClient.sendParticle(this, (byte) 2, this.posX, this.posY, this.posZ);
					playerItem.stackSize--;
					return true;
				} else if( playerItem.itemID == ESPModRegistry.enderPetStaff.itemID ) {
					PacketRecvShowPetGUI.send(this, par1EntityPlayer);
					return true;
				} else if( playerItem.itemID == Item.saddle.itemID && !this.isSaddled() ) {
					this.setSaddled(true);
					playerItem.stackSize--;
					return true;
				} else if( playerItem.itemID == Item.sugar.itemID && currFlightCondition < 9.8F ) {
					this.increaseCondition(2F);
					playerItem.stackSize--;
					return true;
				}
			}
		}

		if( this.isRidden() && !this.worldObj.isRemote ) {
			par1EntityPlayer.mountEntity(null);
			return true;
		}
		
		if( this.isTamed() && !this.worldObj.isRemote ) {
			par1EntityPlayer.addChatMessage(String.format(
				"\247d[%s]\247f " + StatCollector.translateToLocal("enderstuffplus.chat.name"),
				StatCollector.translateToLocal("entity.EnderAvis.name"), this.getName().isEmpty() ? EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET : this.getName())
			);
			if( this.ownerName.equals(par1EntityPlayer.username) ) {
				int percHealth = (int)(this.getHealth() / this.getMaxHealth() * 100F);
				int percCondit = (int)(this.currFlightCondition * 10F);
				par1EntityPlayer.addChatMessage("  "+String.format(StatCollector.translateToLocal("enderstuffplus.chat.avisFriend"), percHealth, percCondit));
			} else {
				par1EntityPlayer.addChatMessage("  "+String.format(StatCollector.translateToLocal("enderstuffplus.chat.stranger"), ownerName));
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
		return this.dataWatcher.getWatchableObjectInt(22) >= 0;
	}

	@Override
	protected boolean isMovementCeased() {
		return this.isSitting() && this.isTamed();
	}
	
	@Override
	public boolean isPotionApplicable(PotionEffect par1PotionEffect) {
		return (this.getCoat() >> 5) != 1 || !Potion.potionTypes[par1PotionEffect.getPotionID()].isBadEffect();
	}

	public boolean isRidden() {
		return this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer;
	}

	public boolean isRiddenDW() {
		try {
			return (this.dataWatcher.getWatchableObjectByte(15) & 32) == 32;
		} catch(NullPointerException e) {
			return false;
		}
	}
	
	public boolean isSaddled() {
		return (this.dataWatcher.getWatchableObjectByte(15) & 2) == 2;
	}
	
	public boolean isSitting() {
		try {
			return (this.dataWatcher.getWatchableObjectByte(15) & 8) == 8;
		} catch(NullPointerException e) {
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
	
	@Override
	public void onDeath(DamageSource par1DamageSource) {

		if( this.isRidden() ) {
			((EntityPlayer)this.riddenByEntity).mountEntity(null);
		}
		
		super.onDeath(par1DamageSource);
	}
	
	@Override
	public void onLivingUpdate() {
		if( this.isAggressive() && !this.isTamed() && entityToAttack == null ) {
			EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 16F);
			this.entityToAttack = player;
		}
		
		if( !worldObj.isRemote ) {
			if( (this.dataWatcher.getWatchableObjectInt(22) & 31) >= ItemRaincoat.dyeColors.length )
				this.dataWatcher.updateObject(22, -1);
			setRiddenDW(isRidden());
			
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
		
		if( this.isWet() && !this.isImmuneToWater() ) {
			this.attackEntityFrom(DamageSource.drown, 1);
		}

		this.field_756_e = this.field_752_b;
		this.field_757_d = this.destPos;
		this.destPos = (float) (this.destPos + (this.onGround ? -1 : 4) * 0.3D);

		if( this.destPos < 0.0F ) {
			this.destPos = 0.0F;
		}

		if( this.destPos > 1.0F ) {
			this.destPos = 1.0F;
		}

		if( !this.onGround && this.field_755_h < 1.0F ) {
			this.field_755_h = 1.0F;
		}

		this.field_755_h = (float) (this.field_755_h * 0.9D);

		if( !this.onGround ) {
			if( this.motionY < 0F && !(this.isRiddenDW() && ESPModRegistry.isShiftPressed((EntityPlayer) this.riddenByEntity)) && (this.canFly() || !this.isTamed()) )
				this.motionY *= 0.6D;
			if( this.ticksFlying < 5 )
				this.ticksFlying++;
		} else {
			if( this.ticksFlying > 0 )
				this.ticksFlying--;
		}

		this.field_752_b += this.field_755_h * 2.0F;
		
		if( this.isRidden() && this.riddenByEntity.isDead && this.isTamed() )
			this.setSitting(true);
		

		if( this.isSitting() ) {
			this.height = 0.9F;
		} else {
			this.height = 1.8F;
		}
		
		EntityPlayer ep = this.getOwningPlayer(25F);

//        AttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
//        attributeinstance.removeModifier(attributes);
//        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.3D);
		if( this.isTamed( )
				&& !this.isSitting()
				&& !this.isRidden()
				&& ep != null && this.isFollowing()
				&& this.getDistanceToEntity(ep) > 2F
				&& this.ownerName.equals(ep.username)) {
			this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 24F, false, false, !this.isImmuneToWater(), !this.isImmuneToWater()));
//            attributeinstance.applyModifier(attributes);
		} else if( this.isTamed( )
				&& !this.isSitting()
				&& !this.isRidden()
				&& ep != null
				&& ep.getCurrentEquippedItem() != null
				&& ep.getCurrentEquippedItem().getItem() instanceof ItemFood
				&& this.getDistanceToEntity(ep) > 2F
				&& this.getHealth() < this.getMaxHealth()) {
//            attributeinstance.applyModifier(attributes);
			this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, ep, 8F, false, false, !this.isImmuneToWater(), !this.isImmuneToWater()));
		}
		
//		if( this.entityToAttack != null )
//            attributeinstance.applyModifier(attributes);
		
		if( isRiddenDW() && riddenByEntity != null ) {
//            attributeinstance.applyModifier(attributes);
			this.jumpMovementFactor = this.walkSpeed / 5.0F;
			EntityPlayer var1 = (EntityPlayer) this.riddenByEntity;
			this.rotationYawHead = var1.rotationYawHead;
			this.setRotation(var1.rotationYaw, 0.0F);
			
			this.stepHeight = 1.0F;
			
			if( this.isJumping && this.posY < 253D ) {
				if( this.canFly() ) {
					this.jumpMovementFactor = this.walkSpeed / 3.0F;
					this.motionY = 0.4D;
					if( this.getCoatBaseColor() == 3 ) {
						this.motionY += 0.1F;
					}
					
					this.currFlightCondition -= 0.01F;
				} else {
					this.jumpMovementFactor = this.walkSpeed / 5.0F;
				}
			}
		}
		
		super.onLivingUpdate();
		
		this.isJumping = false;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if( this.currFlightCondition < 0.2 && !this.worldObj.isRemote )
			setBoolean(false, 64, 15);
		else if( !this.worldObj.isRemote )
			setBoolean(true, 64, 15);
		
		if( !this.worldObj.isRemote ) {
			this.dataWatcher.updateObject(17, Math.round(this.currFlightCondition*2F));
		}

		if( !this.worldObj.isRemote && this.worldObj.difficultySetting == 0 && !isTamed() ) {
			this.setDead();
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void processRiding(EntityPlayerSP player) {
		if( ESPModRegistry.isJumping(player) ) {
			ESPModRegistry.proxy.setJumping(true, this);
		}
		PacketRecvMove.send(player.movementInput.moveForward, player.movementInput.moveStrafe);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readEntityFromNBT(par1nbtTagCompound);
		this.setColor(par1nbtTagCompound.getByte("colorID"));
		this.setTamed(par1nbtTagCompound.getBoolean("isTamed"));
		this.setSaddled(par1nbtTagCompound.getBoolean("isSaddled"));
		if( par1nbtTagCompound.hasKey("coatColor") )
			this.dataWatcher.updateObject(22, par1nbtTagCompound.getInteger("coatColor"));
		else
			this.setCoat(0, par1nbtTagCompound.getBoolean("isImmToWater") ? 16 : -1);
		this.setSitting(par1nbtTagCompound.getBoolean("isSitting"));
		this.setFollowing(par1nbtTagCompound.getBoolean("isFollowing"));
		this.currFlightCondition = par1nbtTagCompound.getFloat("currFlightCond");
		this.ownerName = par1nbtTagCompound.getString("owner");
		if( par1nbtTagCompound.hasKey("petName"))
			this.setName(par1nbtTagCompound.getString("petName"));
		if( this.getName().equals(EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET) )
			this.setName("");
	}
	
	public void setAggressive(boolean b) {
		setBoolean(b, 16, 15);
	}
	
	private void setBoolean(boolean b, int flag, int dwId) {
		byte prevByte = this.dataWatcher.getWatchableObjectByte(dwId);
		this.dataWatcher.updateObject(dwId, (byte)(b ? prevByte | flag : prevByte & ~flag));
	}
	
	public void setCoat(int base, int color) {
		this.dataWatcher.updateObject(22, color == -1 ? -1 : (color & 31) | (base << 5));
	}

	public void setColor(int clr) {
		this.dataWatcher.updateObject(16, (byte)clr);
	}

	public void setCondition(float par1) {
		this.currFlightCondition = Math.min(10.0F, par1);
	}

	@Override
	public void setFollowing(boolean b) {
		setBoolean(b, 128, 15);
	}

	public void setImmuneToWater(boolean b) {
		setBoolean(b, 4, 15);
	}

	@Override
	public void setName(String s) {
		this.setCustomNameTag(s);
	}

	public void setOwner(String s) {
		ownerName = s;
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
		setBoolean(b, 32, 15);
	}
	
	public void setSaddled(boolean b) {
		setBoolean(b, 2, 15);
	}
	
	public void setSitting(boolean b) {
		setBoolean(b, 8, 15);
	}

	public void setTamed(boolean b) {
		setBoolean(b, 1, 15);
	}

	@Override
	public boolean shouldRiderFaceForward(EntityPlayer player) {
		return true;
	}

	@Override
	public void updateEntityActionState() {
		if( !this.isRiddenDW() ) {
			this.jumpMovementFactor = this.walkSpeed / 5.0F;
			super.updateEntityActionState();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setByte("colorID", (byte)this.getColor());
		par1nbtTagCompound.setBoolean("isTamed", this.isTamed());
		par1nbtTagCompound.setBoolean("isSaddled", this.isSaddled());
		par1nbtTagCompound.setInteger("coatColor", this.getCoat());
		par1nbtTagCompound.setBoolean("isSitting", this.isSitting());
		par1nbtTagCompound.setBoolean("isFollowing", this.isFollowing());
		par1nbtTagCompound.setFloat("currFlightCond", this.currFlightCondition);
		par1nbtTagCompound.setString("owner", this.ownerName);
	}
}
