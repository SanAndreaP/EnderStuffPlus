package sanandreasp.mods.EnderStuffPlus.item.tool;

import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ItemRegistry;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNiobAxe extends ItemAxe {

	private Icon glowMap;

	public ItemNiobAxe(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
		return NiobToolHelper.onBlockStartBreak(itemstack, X, Y, Z, player, true);
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.itemID == ItemRegistry.endIngot.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		this.glowMap = par1IconRegister.registerIcon("enderstuffp:niobAxeGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return pass == 1 ? this.glowMap : super.getIcon(stack, pass);
	}
}
