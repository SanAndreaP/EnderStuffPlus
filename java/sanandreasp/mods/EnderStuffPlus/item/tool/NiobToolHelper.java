package sanandreasp.mods.EnderStuffPlus.item.tool;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.stats.StatList;
import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

public final class NiobToolHelper {
	private static final Random rand = new Random();
	
	public static final boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player, boolean shouldDropNugget) {
		if( itemstack == null || !(itemstack.getItem() instanceof ItemTool) )
			return false;
		
		return onBlockStartBreak(itemstack, X, Y, Z, player, CommonUsedStuff.getToolBlocks((ItemTool)itemstack.getItem()), shouldDropNugget);
	}
	
	public static final boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player, Block[] toolEffectives, boolean shouldDropNugget) {
		if( itemstack == null || player.worldObj.isRemote || player.capabilities.isCreativeMode )
			return false;
		
		int blockID = player.worldObj.getBlockId(X, Y, Z);
		int blockMeta = player.worldObj.getBlockMetadata(X, Y, Z);
		Block block = Block.blocksList[blockID];
		
		if( !CommonUsedStuff.isToolEffective(toolEffectives, block) && !itemstack.getItem().canHarvestBlock(block) )
			return false;
		
		player.addStat(StatList.mineBlockStatArray[blockID], 1);
		player.addExhaustion(0.025F);
		
        int fortune = EnchantmentHelper.getFortuneModifier(player);
        
        if( shouldDropNugget && rand.nextInt(50) == 0 ) {
        	ItemStack nugget = new ItemStack(ESPModRegistry.endNugget, rand.nextInt(EnchantmentHelper.getFortuneModifier(player)+1)+1);
        	nugget = EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, itemstack) > 0
        				? CommonUsedStuff.addItemStackToInventory(nugget, player.getInventoryEnderChest())
        				: nugget;
			if( nugget != null ) {
				nugget = CommonUsedStuff.addItemStackToInventory(nugget, player.inventory);
        		if( nugget != null ) {
        			CommonUsedStuff.dropBlockAsItem(block, player.worldObj, X, Y, Z, nugget);
        		}
			}
        }

        if( block.canSilkHarvest(player.worldObj, player, X, Y, Z, blockMeta) && EnchantmentHelper.getSilkTouchModifier(player) ) {
            ItemStack itemstack1 = CommonUsedStuff.getSilkBlock(block, blockMeta);

            if( itemstack1 != null ) {
            	ItemStack newStack = EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, itemstack) > 0
            			? CommonUsedStuff.addItemStackToInventory(itemstack1.copy(), player.getInventoryEnderChest())
            			: itemstack1.copy();
            	if( newStack != null ) {
            		newStack = CommonUsedStuff.addItemStackToInventory(newStack.copy(), player.inventory);
            		if( newStack != null ) {
            			CommonUsedStuff.dropBlockAsItem(block, player.worldObj, X, Y, Z, newStack);
            		} else {
                    	ESPModRegistry.sendPacketAllRng("fxPortal", X, Y, Z, 128.0D, player.dimension, 
                    			X+0.5F, Y+0.5F, Z+0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F
                    	);
            		}
            	} else {
                	ESPModRegistry.sendPacketAllRng("fxPortal", X, Y, Z, 128.0D, player.dimension,
                			X+0.5F, Y+0.5F, Z+0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F
                	);
            	}
            }
        } else {
            ArrayList<ItemStack> items = block.getBlockDropped(player.worldObj, X, Y, Z, blockMeta, fortune);

            for( ItemStack item : items ) {
            	ItemStack newStack = EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, itemstack) > 0
            			? CommonUsedStuff.addItemStackToInventory(item.copy(), player.getInventoryEnderChest())
            			: item.copy();
            	if( newStack != null ) {
            		newStack = CommonUsedStuff.addItemStackToInventory(newStack.copy(), player.inventory);
            		if( newStack != null ) {
            			CommonUsedStuff.dropBlockAsItem(block, player.worldObj, X, Y, Z, newStack);
            		} else {
                    	ESPModRegistry.sendPacketAllRng("fxPortal", X, Y, Z, 128.0D, player.dimension,
                    			X+0.5F, Y+0.5F, Z+0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F
                    	);
            		}
            	} else {
                	ESPModRegistry.sendPacketAllRng("fxPortal", X, Y, Z, 128.0D, player.dimension,  X+0.5F, Y+0.5F, Z+0.5F,
                			0.5F, 0.0F, 1.0F, 1.0F, 1.0F
                	);
            	}
            }
        }
        
        CommonUsedStuff.dropBlockXP(block, player.worldObj, X, Y, Z, blockMeta, fortune);
        
        player.worldObj.setBlock(X, Y, Z, 0);
        
        boolean nullingItem = itemstack.getItemDamage()+1 > itemstack.getMaxDamage();
        itemstack.damageItem(1, player);
        if( nullingItem || itemstack.stackSize == 0 ) {
        	itemstack = null;
        	player.destroyCurrentEquippedItem();
        	player.inventoryContainer.detectAndSendChanges();
        }
        
		
		return true;
	}
}
