package de.sanandrew.mods.enderstuffp.item.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemAhrahSword
        extends ItemSword
{
//    @SideOnly(Side.CLIENT)
//    private IIcon glowMap;

    public ItemAhrahSword(ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":sword_ahrah");
        this.setTextureName(EnderStuffPlus.MOD_ID + ":ahrah");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public IIcon getIcon(ItemStack stack, int pass) {
//        return pass == 1 ? this.glowMap : super.getIcon(stack, pass);
//    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return repairItem.getItem() == EspItems.enderIngot || super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        super.registerIcons(iconRegister);
//        this.glowMap = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobSwordGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
