package sanandreasp.mods.EnderStuffPlus.item.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import net.minecraftforge.common.IShearable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNiobShears
    extends ItemShears
{
    private List<Block> effectiveBlocks = new ArrayList<Block>();
    @SideOnly(Side.CLIENT)
    private Icon glowMap;

    public ItemNiobShears(int par1) {
        super(par1);
        this.setMaxDamage(476);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(ItemStack stack, int pass) {
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
        for( Block block : Block.blocksList ) {
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
    public boolean onBlockDestroyed(ItemStack stack, World world, int x, int y, int z, int side, EntityLivingBase entity) {
        return x != Block.deadBush.blockID ? super.onBlockDestroyed(stack, world, x, y, z, side, entity) : true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        if( player.worldObj.isRemote || player.capabilities.isCreativeMode ) {
            return false;
        }

        int id = player.worldObj.getBlockId(x, y, z);
        if( this.getShearable(stack, player.worldObj, x, y, z).contains(Block.blocksList[id]) || id == Block.deadBush.blockID ) {
            ArrayList<ItemStack> drops;
            if( id == Block.deadBush.blockID ) {
                drops = new ArrayList<ItemStack>();
                drops.add(new ItemStack(Block.deadBush, 1, stack.getItemDamage()));
            } else {
                IShearable target = (IShearable) Block.blocksList[id];
                drops = target.onSheared(stack, player.worldObj, x, y, z,
                                         EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
            }
            Random rand = new Random();
            boolean hasTransported = false;
            for( ItemStack item : drops ) {
                ItemStack newStack = EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, stack) > 0
                                        ? CommonUsedStuff.addItemStackToInventory(item.copy(), player.getInventoryEnderChest())
                                        : item.copy();
                if( newStack != null ) {
                    newStack = CommonUsedStuff.addItemStackToInventory(newStack.copy(), player.inventory);
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
            player.addStat(StatList.mineBlockStatArray[id], 1);
            if( hasTransported ) {
                ESPModRegistry.sendPacketAllRng("fxPortal", x, y, z, 128.0D, player.dimension, x + 0.5F, y + 0.5F,
                                                z + 0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
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
    public void registerIcons(IconRegister iconRegister) {
        super.registerIcons(iconRegister);
        this.glowMap = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobShearsGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
