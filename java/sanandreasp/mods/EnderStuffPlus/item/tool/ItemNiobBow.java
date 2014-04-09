package sanandreasp.mods.EnderStuffPlus.item.tool;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNiobBow
    extends ItemBow
{
    @SideOnly(Side.CLIENT)
    private Icon normalBow;
    @SideOnly(Side.CLIENT)
    private Icon bowPowI;
    @SideOnly(Side.CLIENT)
    private Icon bowPowII;
    @SideOnly(Side.CLIENT)
    private Icon bowPowIII;

    public ItemNiobBow(int id) {
        super(id);
        this.setMaxDamage(768);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(ItemStack stack, int pass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if( usingItem != null && usingItem.getItem().itemID == ModItemRegistry.niobBow.itemID ) {
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
        return repairItem.itemID == ModItemRegistry.endIngot.itemID ? true : super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobBowI");
        this.normalBow = this.itemIcon;
        this.bowPowI = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobBowII");
        this.bowPowII = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobBowIII");
        this.bowPowIII = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobBowIV");
    }
}
