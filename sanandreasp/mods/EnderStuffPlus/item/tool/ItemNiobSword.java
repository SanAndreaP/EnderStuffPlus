package sanandreasp.mods.EnderStuffPlus.item.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Icon;

public class ItemNiobSword extends ItemSword {

	private Icon glowMap;
	
	public ItemNiobSword(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.itemID == ESPModRegistry.endIngot.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		this.glowMap = par1IconRegister.registerIcon("enderstuffp:niobSwordGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return pass == 1 ? glowMap : super.getIcon(stack, pass);
	}
}
