package de.sanandrew.mods.enderstuffplus.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.registry.ModBlockRegistry;

public class ItemEndLeaves
    extends ItemBlock
{
    public ItemEndLeaves(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return 0xFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return ModBlockRegistry.enderLeaves.getIcon(0, damage);
    }

    @Override
    public int getMetadata(int meta) {
        return meta | 4;
    }
}
