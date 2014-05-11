package de.sanandrew.mods.enderstuffplus.item.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import net.minecraftforge.common.IShearable;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.enderstuffplus.packet.PacketFXCstPortal;
import de.sanandrew.mods.enderstuffplus.registry.ConfigRegistry;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;
import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class ItemNiobShears
    extends ItemShears
{
    private List<Block> effectiveBlocks = new ArrayList<Block>();
    @SideOnly(Side.CLIENT)
    private IIcon glowMap;

    public ItemNiobShears() {
        super();
        this.setMaxDamage(476);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 1 ? this.glowMap : super.getIcon(stack, pass);
    }

    @Override
    public int getItemEnchantability() {
        return ModItemRegistry.TOOL_NIOBIUM.getEnchantability();
    }

    private List<Block> getShearable(ItemStack stack, World worldObj, int x, int y, int z) {
        if( this.effectiveBlocks.size() > 0 ) {
            return new ArrayList<Block>(this.effectiveBlocks);
        }
        List<Block> blocks = new ArrayList<Block>();
        for( Object blockKey : Block.blockRegistry.getKeys() ) {
            Block block = (Block) Block.blockRegistry.getObject(blockKey);
            if( block instanceof IShearable ) {
                if( ((IShearable) block).isShearable(stack, worldObj, x, y, z) ) {
                    blocks.add(block);
                }
            }
        }
        this.effectiveBlocks = new ArrayList<Block>(blocks);
        return new ArrayList<Block>(blocks);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
        return block != Blocks.deadbush ? super.onBlockDestroyed(stack, world, block, x, y, z, entity) : true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        if( player.worldObj.isRemote || player.capabilities.isCreativeMode ) {
            return false;
        }

        Block block = player.worldObj.getBlock(x, y, z);
        if( this.getShearable(stack, player.worldObj, x, y, z).contains(block) || block == Blocks.deadbush ) {
            ArrayList<ItemStack> drops;
            if( block == Blocks.deadbush ) {
                drops = new ArrayList<ItemStack>();
                drops.add(new ItemStack(Blocks.deadbush, 1, stack.getItemDamage()));
            } else {
                IShearable target = (IShearable) block;
                drops = target.onSheared(stack, player.worldObj, x, y, z,
                                         EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
            }
            Random rand = new Random();
            boolean hasTransported = false;
            for( ItemStack item : drops ) {
                ItemStack newStack = EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, stack) > 0
                                        ? SAPUtils.addItemStackToInventory(item.copy(), player.getInventoryEnderChest())
                                        : item.copy();
                if( newStack != null ) {
                    newStack = SAPUtils.addItemStackToInventory(newStack.copy(), player.inventory);
                    if( newStack == null ) {
                        hasTransported = true;
                    } else {
                        float offset = 0.7F;
                        double eX = rand.nextFloat() * offset + (1.0F - offset) * 0.5D;
                        double eY = rand.nextFloat() * offset + (1.0F - offset) * 0.5D;
                        double eZ = rand.nextFloat() * offset + (1.0F - offset) * 0.5D;
                        EntityItem entityItem = new EntityItem(player.worldObj, x + eX, y + eY, z + eZ, item);
                        entityItem.delayBeforeCanPickup = 10;
                        player.worldObj.spawnEntityInWorld(entityItem);
                    }
                } else {
                    hasTransported = true;
                }
            }
            stack.damageItem(1, player);
            player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(block)], 1);    // shouldn't use this, but vanilla does it
            if( hasTransported ) {                                                          // this way, so meh...
                IPacket packet = new PacketFXCstPortal(x + 0.5F, y + 0.5F, z + 0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F, 10);
                ESPModRegistry.channelHandler.sendToAllAround(packet, new TargetPoint(player.dimension, x, y, z, 64D));

                player.inventoryContainer.detectAndSendChanges();

                if( player.openContainer != null ) {
                    player.openContainer.detectAndSendChanges();
                }
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        super.registerIcons(iconRegister);
        this.glowMap = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobShearsGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
