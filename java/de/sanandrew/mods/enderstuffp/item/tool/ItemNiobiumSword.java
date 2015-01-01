package de.sanandrew.mods.enderstuffp.item.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.util.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;

public class ItemNiobiumSword
    extends ItemSword
{
    @SideOnly(Side.CLIENT)
    private IIcon glowMap;

    public ItemNiobiumSword(ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":swordNiobium");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 1 ? this.glowMap : this.itemIcon;
    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return SAPUtils.areStacksEqual(repairItem, EnumEnderOres.REPAIR_ITEM_NIOBIUM, false) || super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":sword_niobium");
        this.glowMap = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":sword_niobium_glow" + (EspConfiguration.useNiobHDGlow ? "_hd" : ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
