package sanandreasp.mods.EnderStuffPlus.registry;

import static sanandreasp.core.manpack.helpers.CommonUsedStuff.CUS;

import java.awt.Event;
import java.util.ArrayList;
import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.block.BlockSaplingEndTree;
import sanandreasp.mods.EnderStuffPlus.entity.EntityAvisArrow;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderCreature;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.packet.PacketsSendToClient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ServerEvents {
	
	private Random rand = new Random();
	
	@ForgeSubscribe
	public void onArrowLoose(ArrowLooseEvent evt) {
		if( evt.bow != null && evt.bow.getItem().itemID == ESPModRegistry.niobBow.itemID ) {
			evt.charge *= 2;
		}
		
		if( evt.entityPlayer.inventory.hasItem(ESPModRegistry.avisArrow.itemID) )
		{
			boolean var5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, evt.bow) > 0;
			
			int var6 = evt.charge;
			float var7 = (float)var6 / 20.0F;
			var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

			if( (double)var7 < 0.1D )
			{
				return;
			}

			if( var7 > 1.0F )
			{
				var7 = 1.0F;
			}

			EntityArrow var8 = new EntityAvisArrow(evt.entityPlayer.worldObj, evt.entityPlayer, var7 * 2.0F);

			if( var7 == 1.0F )
			{
				var8.setIsCritical(true);
			}

			int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, evt.bow);

			if( var9 > 0 )
			{
				var8.setDamage(var8.getDamage() + (double)var9 * 0.5D + 0.5D);
			}

			int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, evt.bow);

			if( var10 > 0 )
			{
				var8.setKnockbackStrength(var10);
			}

			if( EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, evt.bow) > 0 )
			{
				var8.setFire(100);
			}

			evt.bow.damageItem(1, evt.entityPlayer);
			evt.entityPlayer.worldObj.playSoundAtEntity(evt.entityPlayer, "random.bow", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);
			evt.entityPlayer.worldObj.playSoundAtEntity(evt.entityPlayer, "random.orb", 0.05F, 1.0F / (rand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

			if( !var5 && !evt.entityPlayer.capabilities.isCreativeMode )
			{
				evt.entityPlayer.inventory.consumeInventoryItem(ESPModRegistry.avisArrow.itemID);
			}
			else
			{
				var8.canBePickedUp = 0;
			}

			if( !evt.entityPlayer.worldObj.isRemote )
			{
				evt.entityPlayer.worldObj.spawnEntityInWorld(var8);
			}
			
			evt.setCanceled(true);
		}
	}
	
	@ForgeSubscribe
	public void onArrowNock(ArrowNockEvent evt) {
		 if( evt.entityPlayer.inventory.hasItem(ESPModRegistry.avisArrow.itemID) && !evt.entityPlayer.capabilities.isCreativeMode )
		 {
			 evt.entityPlayer.setItemInUse(evt.result, Item.bow.getMaxItemUseDuration(evt.result));
		 }
	}
	
	@ForgeSubscribe
	public void onEntityHit(LivingHurtEvent evt) {
		if( (evt.source.getSourceOfDamage() instanceof EntityAvisArrow) && !(evt.entityLiving instanceof EntityEnderAvis) ) {
			evt.entityLiving.addPotionEffect(new PotionEffect(Potion.poison.id, 100, ((EntityAvisArrow)evt.source.getSourceOfDamage()).getIsCritical() ? 1 : 0));
		} else if( evt.source.equals(DamageSource.fall) && evt.entityLiving instanceof EntityPlayer ) {
			EntityPlayer player = (EntityPlayer) evt.entityLiving;
			if( player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().isItemEqual(new ItemStack(ESPModRegistry.avisFeather)) )
				evt.ammount = 0F;
		} else if( evt.source.getSourceOfDamage() != null
				&& evt.source.getSourceOfDamage() instanceof EntityPlayer
				&& evt.source.getSourceOfDamage().ridingEntity != null
				&& evt.source.getSourceOfDamage().ridingEntity instanceof IEnderPet
				&& ((IEnderPet)evt.source.getSourceOfDamage().ridingEntity).getCoatBaseColor() == 2 ) {
			evt.ammount *= 1.5F;
		}
	}
	
	@ForgeSubscribe
	public void onEntityDrop(LivingDropsEvent evt) {
		EntityLivingBase entity = evt.entityLiving;
		if( (entity instanceof EntityEnderman || entity instanceof IEnderCreature) && rand.nextInt(Math.max(1, 30 - (2 << evt.lootingLevel))) == 0 ) {
			evt.drops.add(new EntityItem(entity.worldObj, entity.posX, entity.posY+0.5F, entity.posZ, new ItemStack(ESPModRegistry.enderFlesh.itemID, rand.nextInt(2+evt.lootingLevel)+1, 0)));
		}

		EntityPlayer player = evt.source.getEntity() instanceof EntityPlayer ? ((EntityPlayer)evt.source.getEntity()) : null;
		ItemStack itemstack = player != null ? player.getHeldItem() : null;
		if( itemstack != null && itemstack.itemID == ESPModRegistry.niobSword.itemID ) {
			boolean spawnParticles = false;
			ArrayList<EntityItem> dropsCopy = new ArrayList<EntityItem>(evt.drops);
			for( EntityItem item : dropsCopy ) {
				ItemStack newStack = EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, itemstack) > 0
						? CUS.addItemStackToInventory(item.getEntityItem().copy(), player.getInventoryEnderChest())
						: item.getEntityItem().copy();
				if( newStack != null ) {
					newStack = CUS.addItemStackToInventory(newStack.copy(), player.inventory);
					if( newStack == null ) {
						evt.drops.remove(item);
						spawnParticles = true;
					} else {
						item.setEntityItemStack(newStack.copy());
					}
				} else {
					evt.drops.remove(item);
					spawnParticles = true;
				}
			}
			if( spawnParticles) PacketsSendToClient.sendParticle(evt.entityLiving, (byte)5, evt.entityLiving.posX, evt.entityLiving.posY, evt.entityLiving.posZ );
		}
	}
	
	@ForgeSubscribe
	public void onEnderTeleport(EnderTeleportEvent evt) {
		if( evt.entityLiving instanceof EntityPlayer ) {
			EntityPlayer player = (EntityPlayer)evt.entityLiving;
			if( ESPModRegistry.hasPlayerFullNiob(player) ) {
				evt.attackDamage = 0;
				player.inventory.damageArmor(1);
			}
		}
	}
	
	@ForgeSubscribe
	public void onEntityInteract(EntityInteractEvent event) {
		Entity entity = event.target;
		ItemStack itemstack = event.entityPlayer.getCurrentEquippedItem();
		if( entity.worldObj.isRemote || itemstack == null || itemstack.getItem().itemID != ESPModRegistry.niobShears.itemID ) {
			;
		} else if( entity instanceof IShearable && entity instanceof EntityLiving ) {
			IShearable target = (IShearable)entity;
			Random rand = new Random();
			if( target.isShearable(itemstack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ) ) {
				ArrayList<ItemStack> drops = target.onSheared(itemstack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ,
						EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
				boolean transportSucceed = false;
				for( ItemStack item : drops ) {
					ItemStack newStack = EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, itemstack) > 0
							? CUS.addItemStackToInventory(item.copy(), event.entityPlayer.getInventoryEnderChest())
							: item.copy();
					if( newStack != null ) {
						newStack = CUS.addItemStackToInventory(newStack.copy(), event.entityPlayer.inventory);
						if( newStack == null ) {
							transportSucceed = true;
						} else {
							EntityItem ent = entity.entityDropItem(item, 1.0F);
							ent.motionY += rand.nextFloat() * 0.05F;
							ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
							ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
						}
					} else {
						transportSucceed = true;
					}
				}
				if( transportSucceed ) {
					PacketsSendToClient.sendParticle(entity, (byte)5, entity.posX, entity.posY, entity.posZ);
					event.entityPlayer.inventoryContainer.detectAndSendChanges();
					if( event.entityPlayer.openContainer != null )
						event.entityPlayer.inventoryContainer.detectAndSendChanges();
				}
				if( !event.entityPlayer.capabilities.isCreativeMode )
					itemstack.damageItem(1, (EntityLiving)entity);
			}
			event.setCanceled(true);
		}
	}
	
	@ForgeSubscribe
	public void onBonemeal(BonemealEvent event) {
		if(event.ID == ESPModRegistry.sapEndTree.blockID) {
			if( !event.world.isRemote && (double)event.world.rand.nextFloat() < 0.45D ) {
				((BlockSaplingEndTree)ESPModRegistry.sapEndTree).markOrGrowMarked(event.world, event.X, event.Y, event.Z, event.world.rand);
			}
			event.setResult(Result.ALLOW);
		}
	}
}
