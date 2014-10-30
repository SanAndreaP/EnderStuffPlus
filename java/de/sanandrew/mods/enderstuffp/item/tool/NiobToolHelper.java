package de.sanandrew.mods.enderstuffp.item.tool;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.RegistryItems;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.stats.StatList;

import java.util.ArrayList;

public final class NiobToolHelper
{
    public static boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player, Block[] toolEffectives, boolean shouldDropNugget) {
        if( stack == null || player.worldObj.isRemote || player.capabilities.isCreativeMode ) {
            return false;
        }

        Block block = player.worldObj.getBlock(x, y, z);
        int blockMeta = player.worldObj.getBlockMetadata(x, y, z);

        if( !SAPUtils.isToolEffective(toolEffectives, block) && !stack.getItem().canHarvestBlock(block, stack) ) {
            return false;
        }

        player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(block)], 1); //vanilla code, so ¯\_(ツ)_/¯
        player.addExhaustion(0.025F);

        int fortune = EnchantmentHelper.getFortuneModifier(player);

        if( shouldDropNugget && SAPUtils.RNG.nextInt(50) == 0 ) {
            ItemStack nugget = new ItemStack(RegistryItems.endNugget, SAPUtils.RNG.nextInt(EnchantmentHelper.getFortuneModifier(player) + 1) + 1);
            nugget = EnchantmentHelper.getEnchantmentLevel(EnderStuffPlus.enderChestTel.effectId, stack) > 0
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
                ItemStack newStack = EnchantmentHelper.getEnchantmentLevel(EnderStuffPlus.enderChestTel.effectId, stack) > 0
                                        ? SAPUtils.addItemStackToInventory(silkedStack.copy(), player.getInventoryEnderChest())
                                        : silkedStack.copy();
                if( newStack != null ) {
                    newStack = SAPUtils.addItemStackToInventory(newStack.copy(), player.inventory);
                    if( newStack != null ) {
                        SAPUtils.dropBlockAsItem(block, player.worldObj, x, y, z, newStack);
                    } else {
                        spawnFX(x, y, z, player.dimension);
                    }
                } else {
                    spawnFX(x, y, z, player.dimension);
                }
            }
        } else {
            ArrayList<ItemStack> items = block.getDrops(player.worldObj, x, y, z, blockMeta, fortune);

            for( ItemStack item : items ) {
                ItemStack newStack = EnchantmentHelper.getEnchantmentLevel(EnderStuffPlus.enderChestTel.effectId, stack) > 0
                                        ? SAPUtils.addItemStackToInventory(item.copy(), player.getInventoryEnderChest())
                                        : item.copy();
                if( newStack != null ) {
                    newStack = SAPUtils.addItemStackToInventory(newStack.copy(), player.inventory);
                    if( newStack != null ) {
                        SAPUtils.dropBlockAsItem(block, player.worldObj, x, y, z, newStack);
                    } else {
                        spawnFX(x, y, z, player.dimension);
                    }
                } else {
                    spawnFX(x, y, z, player.dimension);
                }
            }
        }

        SAPUtils.dropBlockXP(block, player.worldObj, x, y, z, blockMeta, fortune);

        player.worldObj.setBlock(x, y, z, Blocks.air);

        boolean shouldItemDestroy = stack.getItemDamage() + 1 > stack.getMaxDamage();
        stack.damageItem(1, player);
        if( shouldItemDestroy || stack.stackSize == 0 ) {
            stack = null;
            player.destroyCurrentEquippedItem();
            player.inventoryContainer.detectAndSendChanges();
        }

        return true;
    }

    public static boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player, boolean shouldDropNugget) {
        if( !(stack.getItem() instanceof ItemTool) ) {
            return false;
        }

        return onBlockStartBreak(stack, x, y, z, player, SAPUtils.getToolBlocks((ItemTool) stack.getItem()), shouldDropNugget);
    }

    private static void spawnFX(int x, int y, int z, int dimId) {
        //TODO spawn particles
//        IPacket packet = new PacketFXCstPortal(x, y, z, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F, 10);
//        ESPModRegistry.channelHandler.sendToAllAround(packet, new TargetPoint(dimId, x + 0.5F, y + 0.5F, z + 0.5F, 64.0D));
    }
}
