package de.sanandrew.mods.enderstuffp.item.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumEnderOres;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemNiobiumBow
        extends ItemBow
{
    @SideOnly(Side.CLIENT)
    private IIcon bowPow1;
    @SideOnly(Side.CLIENT)
    private IIcon bowPow2;
    @SideOnly(Side.CLIENT)
    private IIcon bowPow3;

    public ItemNiobiumBow() {
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":bowNiobium");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
        this.setMaxDamage(768);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if( usingItem != null && usingItem.getItem() == EspItems.niobBow ) {
            int k = usingItem.getMaxItemUseDuration() - useRemaining;

            if( k >= 9 ) {
                return this.bowPow3;
            } else if( k > 6 ) {
                return this.bowPow2;
            } else if( k > 0 ) {
                return this.bowPow1;
            }
        }
        return this.itemIcon;
    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return SAPUtils.areStacksEqual(repairItem, EnumEnderOres.REPAIR_ITEM_NIOBIUM, false) || super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":bow_niobium_1");
        this.bowPow1 = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":bow_niobium_2");
        this.bowPow2 = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":bow_niobium_3");
        this.bowPow3 = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":bow_niobium_4");
    }
}
