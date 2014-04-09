package sanandreasp.mods.EnderStuffPlus.item.tool;

import java.util.ArrayList;
import java.util.Random;

import sanandreasp.core.manpack.helpers.SAPUtils;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.stats.StatList;

public final class NiobToolHelper
{
    private static final Random rand = new Random();

    public static final boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player, Block[] toolEffectives,
                                                  boolean shouldDropNugget) {
        if( stack == null || player.worldObj.isRemote || player.capabilities.isCreativeMode ) {
            return false;
        }

        int blockID = player.worldObj.getBlockId(x, y, z);
        int blockMeta = player.worldObj.getBlockMetadata(x, y, z);
        Block block = Block.blocksList[blockID];

        if( !SAPUtils.isToolEffective(toolEffectives, block) && !stack.getItem().canHarvestBlock(block) ) {
            return false;
        }

        player.addStat(StatList.mineBlockStatArray[blockID], 1);
        player.addExhaustion(0.025F);

        int fortune = EnchantmentHelper.getFortuneModifier(player);

        if( shouldDropNugget && rand.nextInt(50) == 0 ) {
            ItemStack nugget = new ItemStack(ModItemRegistry.endNugget,
                                             rand.nextInt(EnchantmentHelper.getFortuneModifier(player) + 1) + 1);
            nugget = EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, stack) > 0
                        ? SAPUtils.addItemStackToInventory(nugget, player.getInventoryEnderChest())
                        : nugget;
            if( nugget != null ) {
                nugget = SAPUtils.addItemStackToInventory(nugget, player.inventory);
                if( nugget != null ) {
                    SAPUtils.dropBlockAsItem(block, player.worldObj, x, y, z, nugget);
                }
            }
        }

        if( block.canSilkHarvest(player.worldObj, player, x, y, z, blockMeta) && EnchantmentHelper.getSilkTouchModifier(player) ) {
            ItemStack silkedStack = SAPUtils.getSilkBlock(block, blockMeta);

            if( silkedStack != null ) {
                ItemStack newStack = EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, stack) > 0
                                        ? SAPUtils.addItemStackToInventory(silkedStack.copy(), player.getInventoryEnderChest())
                                        : silkedStack.copy();
                if( newStack != null ) {
                    newStack = SAPUtils.addItemStackToInventory(newStack.copy(), player.inventory);
                    if( newStack != null ) {
                        SAPUtils.dropBlockAsItem(block, player.worldObj, x, y, z, newStack);
                    } else {
                        ESPModRegistry.sendPacketAllRng("fxPortal", x, y, z, 128.0D, player.dimension, x + 0.5F,
                                                        y + 0.5F, z + 0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F, 10);
                    }
                } else {
                    ESPModRegistry.sendPacketAllRng("fxPortal", x, y, z, 128.0D, player.dimension, x + 0.5F, y + 0.5F,
                                                    z + 0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F, 10);
                }
            }
        } else {
            ArrayList<ItemStack> items = block.getBlockDropped(player.worldObj, x, y, z, blockMeta, fortune);

            for( ItemStack item : items ) {
                ItemStack newStack = EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, stack) > 0
                                        ? SAPUtils.addItemStackToInventory(item.copy(), player.getInventoryEnderChest())
                                        : item.copy();
                if( newStack != null ) {
                    newStack = SAPUtils.addItemStackToInventory(newStack.copy(), player.inventory);
                    if( newStack != null ) {
                        SAPUtils.dropBlockAsItem(block, player.worldObj, x, y, z, newStack);
                    } else {
                        ESPModRegistry.sendPacketAllRng("fxPortal", x, y, z, 128.0D, player.dimension, x + 0.5F,
                                                        y + 0.5F, z + 0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F, 10);
                    }
                } else {
                    ESPModRegistry.sendPacketAllRng("fxPortal", x, y, z, 128.0D, player.dimension, x + 0.5F, y + 0.5F,
                                                    z + 0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F, 10);
                }
            }
        }

        SAPUtils.dropBlockXP(block, player.worldObj, x, y, z, blockMeta, fortune);

        player.worldObj.setBlock(x, y, z, 0);

        boolean shouldItemDestroy = stack.getItemDamage() + 1 > stack.getMaxDamage();
        stack.damageItem(1, player);
        if( shouldItemDestroy || stack.stackSize == 0 ) {
            stack = null;
            player.destroyCurrentEquippedItem();
            player.inventoryContainer.detectAndSendChanges();
        }

        return true;
    }

    public static final boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player, boolean shouldDropNugget) {
        if( !(stack.getItem() instanceof ItemTool) ) {
            return false;
        }

        return onBlockStartBreak(stack, x, y, z, player, SAPUtils.getToolBlocks((ItemTool) stack.getItem()), shouldDropNugget);
    }
}
