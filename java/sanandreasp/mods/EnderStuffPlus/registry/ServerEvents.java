package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.ArrayList;
import java.util.Random;

import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.mods.EnderStuffPlus.block.BlockSaplingEndTree;
import sanandreasp.mods.EnderStuffPlus.entity.EntityAvisArrow;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderCreature;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityItemTantal;

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
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class ServerEvents {

	private final Random rand = new Random();

	@ForgeSubscribe
	public void onArrowLoose(ArrowLooseEvent evt) {
		if( evt.bow != null && evt.bow.getItem().itemID == ModItemRegistry.niobBow.itemID ) {
			evt.charge *= 2;
		}

		if( evt.entityPlayer.inventory.hasItem(ModItemRegistry.avisArrow.itemID) )
		{
			boolean var5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, evt.bow) > 0;

			int var6 = evt.charge;
			float var7 = var6 / 20.0F;
			var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

			if( var7 < 0.1D )
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
				var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
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
			evt.entityPlayer.worldObj.playSoundAtEntity(evt.entityPlayer, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);
			evt.entityPlayer.worldObj.playSoundAtEntity(evt.entityPlayer, "random.orb", 0.05F, 1.0F / (this.rand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

			if( !var5 && !evt.entityPlayer.capabilities.isCreativeMode )
			{
				evt.entityPlayer.inventory.consumeInventoryItem(ModItemRegistry.avisArrow.itemID);
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
		 if( evt.entityPlayer.inventory.hasItem(ModItemRegistry.avisArrow.itemID) && !evt.entityPlayer.capabilities.isCreativeMode )
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
			if( player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().isItemEqual(new ItemStack(ModItemRegistry.avisFeather)) ) {
                evt.ammount = 0F;
            }
		} else if( evt.source.getSourceOfDamage() != null
				&& evt.source.getSourceOfDamage() instanceof EntityPlayer
				&& evt.source.getSourceOfDamage().ridingEntity != null
				&& evt.source.getSourceOfDamage().ridingEntity instanceof IEnderPet
				&& ((IEnderPet)evt.source.getSourceOfDamage().ridingEntity).getCoatBase().equals(ESPModRegistry.MOD_ID + "_002") ) {
			evt.ammount *= 1.5F;
		}
	}

    @ForgeSubscribe
	public void onItemHurt(ItemExpireEvent evt) {
	    System.out.println("lillli");
	}

	@ForgeSubscribe
	public void onEntityDrop(LivingDropsEvent evt) {
		EntityLivingBase entity = evt.entityLiving;
		if( (entity instanceof EntityEnderman || entity instanceof IEnderCreature) && this.rand.nextInt(Math.max(1, 30 - (2 << evt.lootingLevel))) == 0 ) {
			evt.drops.add(new EntityItem(entity.worldObj, entity.posX, entity.posY+0.5F, entity.posZ, new ItemStack(ModItemRegistry.enderFlesh.itemID, this.rand.nextInt(2+evt.lootingLevel)+1, 0)));
		}

		EntityPlayer player = evt.source.getEntity() instanceof EntityPlayer ? ((EntityPlayer)evt.source.getEntity()) : null;
		ItemStack itemstack = player != null ? player.getHeldItem() : null;
		if( itemstack != null && itemstack.itemID == ModItemRegistry.niobSword.itemID ) {
			boolean spawnParticles = false;
			ArrayList<EntityItem> dropsCopy = new ArrayList<EntityItem>(evt.drops);
			for( EntityItem item : dropsCopy ) {
				ItemStack newStack = EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, itemstack) > 0
						? CommonUsedStuff.addItemStackToInventory(item.getEntityItem().copy(), player.getInventoryEnderChest())
						: item.getEntityItem().copy();
				if( newStack != null ) {
					newStack = CommonUsedStuff.addItemStackToInventory(newStack.copy(), player.inventory);
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
			if( spawnParticles ) {
				for( int i = 0; i < 20; i++ ) {
		        	ESPModRegistry.sendPacketAllRng("fxPortal",
		        			evt.entityLiving.posX, evt.entityLiving.posY, evt.entityLiving.posZ, 128.0D, player.dimension,
		        			evt.entityLiving.posX, evt.entityLiving.posY, evt.entityLiving.posZ, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F
		        	);
				}
			}
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
		if( entity.worldObj.isRemote || itemstack == null || itemstack.getItem().itemID != ModItemRegistry.niobShears.itemID ) {
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
							? CommonUsedStuff.addItemStackToInventory(item.copy(), event.entityPlayer.getInventoryEnderChest())
							: item.copy();
					if( newStack != null ) {
						newStack = CommonUsedStuff.addItemStackToInventory(newStack.copy(), event.entityPlayer.inventory);
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
		        	ESPModRegistry.sendPacketAllRng("fxPortal", entity.posX, entity.posY, entity.posZ,
		        			128.0D, entity.dimension, entity.posX, entity.posY, entity.posZ, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F
		        	);
					event.entityPlayer.inventoryContainer.detectAndSendChanges();
					if( event.entityPlayer.openContainer != null ) {
                        event.entityPlayer.inventoryContainer.detectAndSendChanges();
                    }
				}
				if( !event.entityPlayer.capabilities.isCreativeMode ) {
                    itemstack.damageItem(1, (EntityLiving)entity);
                }
			}
			event.setCanceled(true);
		}
	}

	@ForgeSubscribe
	public void onBonemeal(BonemealEvent event) {
		if(event.ID == ModBlockRegistry.sapEndTree.blockID) {
			if( !event.world.isRemote && event.world.rand.nextFloat() < 0.45D ) {
				((BlockSaplingEndTree)ModBlockRegistry.sapEndTree).markOrGrowMarked(event.world, event.X, event.Y, event.Z, event.world.rand);
			}
			event.setResult(Result.ALLOW);
		}
	}

    @ForgeSubscribe
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
	    if( event.entity instanceof EntityItem && !(event.entity instanceof EntityItemTantal) && !event.world.isRemote ) {
	        EntityItem eItem = (EntityItem) event.entity;
	        if( eItem.getEntityItem() != null && eItem.getEntityItem().getItem() == ModItemRegistry.tantalPick ) {
	            EntityItemTantal tantalItem = new EntityItemTantal(event.world, eItem, eItem.getEntityItem());
	            event.world.spawnEntityInWorld(tantalItem);
	            eItem.setDead();
	        }
	    }
	}
}
