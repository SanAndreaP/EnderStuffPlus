package sanandreasp.mods.EnderStuffPlus.item.tool;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemNiobBow extends ItemBow {
	
	private Icon normalBow, bowPowI, bowPowII, bowPowIII;

	public ItemNiobBow(int par1) {
		super(par1);
        this.setMaxDamage(768);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if( usingItem != null && usingItem.getItem().itemID == ESPModRegistry.niobBow.itemID )
        {
            int k = usingItem.getMaxItemUseDuration() - useRemaining;
            if( k >= 9 ) return this.bowPowIII;
            if( k > 6 ) return this.bowPowII;
            if( k > 0 ) return this.bowPowI;
        }
		return this.normalBow;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = this.normalBow = par1IconRegister.registerIcon("enderstuffp:niobBowI");
		this.bowPowI = par1IconRegister.registerIcon("enderstuffp:niobBowII");
		this.bowPowII = par1IconRegister.registerIcon("enderstuffp:niobBowIII");
		this.bowPowIII = par1IconRegister.registerIcon("enderstuffp:niobBowIV");
	}
	
	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.itemID == ESPModRegistry.endIngot.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
}
