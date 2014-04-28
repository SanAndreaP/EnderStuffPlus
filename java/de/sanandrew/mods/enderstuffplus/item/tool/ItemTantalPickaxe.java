package de.sanandrew.mods.enderstuffplus.item.tool;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.registry.ConfigRegistry;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;
import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class ItemTantalPickaxe
    extends ItemPickaxe
{
    @SideOnly(Side.CLIENT)
    private IIcon glowMap;

    public ItemTantalPickaxe(ToolMaterial toolMaterial) {
        super(toolMaterial);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 1 ? this.glowMap : super.getIcon(stack, pass);
    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return repairItem.getItem() == ModItemRegistry.endIngot ? true : super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        return NiobToolHelper.onBlockStartBreak(stack, x, y, z, player, true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        super.registerIcons(iconRegister);
        this.glowMap = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobPickGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
