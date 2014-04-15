package sanandreasp.mods.EnderStuffPlus.item.tool;

import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAhrahSword
    extends ItemSword
{
//    @SideOnly(Side.CLIENT)
//    private Icon glowMap;

    public ItemAhrahSword(int id, EnumToolMaterial toolMaterial) {
        super(id, toolMaterial);
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public Icon getIcon(ItemStack stack, int pass) {
//        return pass == 1 ? this.glowMap : super.getIcon(stack, pass);
//    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return repairItem.itemID == ModItemRegistry.endIngot.itemID ? true : super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        super.registerIcons(iconRegister);
//        this.glowMap = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobSwordGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
