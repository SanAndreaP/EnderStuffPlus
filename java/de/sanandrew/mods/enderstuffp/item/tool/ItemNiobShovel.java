package de.sanandrew.mods.enderstuffp.item.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.util.ConfigRegistry;
import de.sanandrew.mods.enderstuffp.util.CreativeTabsEnderStuff;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemNiobShovel
    extends ItemSpade
{
    @SideOnly(Side.CLIENT)
    private IIcon glowMap;

    public ItemNiobShovel(ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":niobShovel");
        this.setCreativeTab(CreativeTabsEnderStuff.ESP_TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 1 ? this.glowMap : super.getIcon(stack, pass);
    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return repairItem.getItem() == EspItems.endIngot || super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
        return NiobToolHelper.onBlockStartBreak(itemstack, x, y, z, player, true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":niobShovel");
        this.glowMap = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":niobShovelGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
