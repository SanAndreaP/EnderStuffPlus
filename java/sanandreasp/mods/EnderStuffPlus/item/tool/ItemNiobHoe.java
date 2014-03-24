package sanandreasp.mods.EnderStuffPlus.item.tool;

import java.util.ArrayList;
import java.util.List;

import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import net.minecraftforge.common.IPlantable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNiobHoe extends ItemHoe {

	private List<Block> effectiveBlocks = new ArrayList<Block>();
	private Icon glowMap;

	public ItemNiobHoe(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
		return NiobToolHelper.onBlockStartBreak(itemstack, X, Y, Z, player, this.getPlants(), false);
	}

	private Block[] getPlants() {
		if( this.effectiveBlocks.size() > 0 ) {
            return this.effectiveBlocks.toArray(CommonUsedStuff.getArrayFromList(this.effectiveBlocks, Block.class));
        }
		List<Block> blocks = new ArrayList<Block>();
		for( int i = 0; i < Block.blocksList.length; i++ ) {
			if( Block.blocksList[i] != null && Block.blocksList[i] instanceof IPlantable ) {
                blocks.add(Block.blocksList[i]);
            }
		}
		this.effectiveBlocks = new ArrayList<Block>(blocks);
		return blocks.toArray(CommonUsedStuff.getArrayFromList(blocks, Block.class));
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.itemID == ItemRegistry.endIngot.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@Override
    public int getItemEnchantability()
    {
        return this.theToolMaterial.getEnchantability();
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		this.glowMap = par1IconRegister.registerIcon("enderstuffp:niobHoeGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
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
