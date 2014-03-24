package sanandreasp.mods.EnderStuffPlus.item;

import sanandreasp.mods.EnderStuffPlus.registry.BlockRegistry;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEndLeaves extends ItemBlock {
    public ItemEndLeaves(int par1)
    {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1 | 4;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int par1)
    {
        return BlockRegistry.enderLeaves.getIcon(0, par1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return 0xFFFFFF;
    }
}
