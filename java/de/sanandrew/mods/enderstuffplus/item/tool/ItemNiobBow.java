package de.sanandrew.mods.enderstuffplus.item.tool;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;
import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class ItemNiobBow
    extends ItemBow
{
    @SideOnly(Side.CLIENT)
    private IIcon normalBow;
    @SideOnly(Side.CLIENT)
    private IIcon bowPowI;
    @SideOnly(Side.CLIENT)
    private IIcon bowPowII;
    @SideOnly(Side.CLIENT)
    private IIcon bowPowIII;

    public ItemNiobBow() {
        super();
        this.setMaxDamage(768);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if( usingItem != null && usingItem.getItem() == ModItemRegistry.niobBow ) {
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
        return this.normalBow;
    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return repairItem.getItem() == ModItemRegistry.endIngot ? true : super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobBowI");
        this.normalBow = this.itemIcon;
        this.bowPowI = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobBowII");
        this.bowPowII = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobBowIII");
        this.bowPowIII = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobBowIV");
    }
}
