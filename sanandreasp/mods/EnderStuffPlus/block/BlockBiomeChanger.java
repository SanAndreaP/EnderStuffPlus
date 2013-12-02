package sanandreasp.mods.EnderStuffPlus.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkModHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBiomeChanger extends BlockContainer {
	public BlockBiomeChanger(int par1, Material par3Material) {
		super(par1, par3Material);
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		int id = 1;
		TileEntity var1Tile = par1World.getBlockTileEntity(par2, par3, par4);
		if( var1Tile != null && var1Tile instanceof TileEntityBiomeChanger && ((TileEntityBiomeChanger)var1Tile).isActive() )
			id = 3;
		par5EntityPlayer.openGui(ESPModRegistry.instance, id, par1World, par2, par3, par4);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityBiomeChanger();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		TileEntity var1Tile = par1World.getBlockTileEntity(par2, par3, par4);
		if( var1Tile != null && var1Tile instanceof TileEntityBiomeChanger && !par1World.isRemote ) {
			TileEntityBiomeChanger var2TileBC = (TileEntityBiomeChanger)var1Tile;
			for( int i = 0; i < var2TileBC.getSizeInventory(); i++ ) {
				ItemStack var3Stack = var2TileBC.getStackInSlot(i);
				if( var3Stack != null && var3Stack.stackSize > 0 ) {
					par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, var3Stack));
				}
			}
			
			if( var2TileBC.prevFuelItem != null ) {
				int remain = (var2TileBC.getMaxRange() - var2TileBC.getCurrRange()) * RegistryBiomeChanger.getMultiFromStack(var2TileBC.prevFuelItem);
				while(remain > 0) {
					ItemStack var3Stack = var2TileBC.prevFuelItem.copy();
					if( remain > var2TileBC.prevFuelItem.getMaxStackSize() ) {
						remain -= var3Stack.stackSize = var2TileBC.prevFuelItem.getMaxStackSize();
					} else {
						var3Stack.stackSize = remain;
						remain = 0;
					}
					par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, var3Stack));
				}
			}
			var1Tile.invalidate();
		}
		
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return AxisAlignedBB.getBoundingBox(par2+0.0625F, par3, par4+0.0625F, par2+0.9375F, par3+0.5625F, par4+0.9375F);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return AxisAlignedBB.getBoundingBox(par2+0.0625F, par3, par4+0.0625F, par2+0.9375F, par3+0.5625F, par4+0.9375F);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess, int par2, int par3, int par4) {
		this.setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.5625F, 0.9375F);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = Block.obsidian.getIcon(0,0);
	}
}
