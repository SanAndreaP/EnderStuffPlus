package de.sanandrew.mods.enderstuffp.item.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.util.CreativeTabsEnderStuff;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.RegistryItems;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemNiobBow
        extends ItemBow
{
    @SideOnly(Side.CLIENT)
    private IIcon bowPowI;
    @SideOnly(Side.CLIENT)
    private IIcon bowPowII;
    @SideOnly(Side.CLIENT)
    private IIcon bowPowIII;

    public ItemNiobBow() {
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":bowNiob");
        this.setCreativeTab(CreativeTabsEnderStuff.ESP_TAB);
        this.setMaxDamage(768);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if( usingItem != null && usingItem.getItem() == RegistryItems.niobBow ) {
            int k = usingItem.getMaxItemUseDuration() - useRemaining;
            if( k >= 9 ) {
                return this.bowPowIII;
            }
            if( k > 6 ) {
                return this.bowPowII;
            }
            if( k > 0 ) {
                return this.bowPowI;
            }
        }
        return this.itemIcon;
    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return repairItem.getItem() == RegistryItems.endIngot || super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":niobBowI");
        this.bowPowI = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":niobBowII");
        this.bowPowII = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":niobBowIII");
        this.bowPowIII = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":niobBowIV");
    }
}
