package sanandreasp.mods.EnderStuffPlus.item;

import sanandreasp.mods.EnderStuffPlus.registry.ModBlockRegistry;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEndLeaves
    extends ItemBlock
{
    public ItemEndLeaves(int id) {
        super(id);
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
    public Icon getIconFromDamage(int damage) {
        return ModBlockRegistry.enderLeaves.getIcon(0, damage);
    }

    @Override
    public int getMetadata(int meta) {
        return meta | 4;
    }
}
