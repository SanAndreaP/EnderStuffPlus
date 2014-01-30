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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBiomeChanger extends BlockContainer
{
	public BlockBiomeChanger(int id, Material material) {
		super(id, material);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
		int id = 1;
		TileEntity var1Tile = world.getBlockTileEntity(x, y, z);
		if( var1Tile != null 
			&& (var1Tile instanceof TileEntityBiomeChanger)
			&& ((TileEntityBiomeChanger)var1Tile).isActive() )
		{
			id = 3;
		}
		player.openGui(ESPModRegistry.instance, id, world, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
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
	public void breakBlock(World world, int x, int y, int z, int oldID, int oldMeta) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if( tile != null 
			&& tile instanceof TileEntityBiomeChanger 
			&& !world.isRemote )
		{
			TileEntityBiomeChanger tileBiomeChgr = (TileEntityBiomeChanger)tile;
			for( int i = 0; i < tileBiomeChgr.getSizeInventory(); i++ ) {
				ItemStack stack = tileBiomeChgr.getStackInSlot(i);
				if( stack != null && stack.stackSize > 0 ) {
					world.spawnEntityInWorld(new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, stack));
				}
			}
			
			if( tileBiomeChgr.prevFuelItem != null ) {
				int remain = (tileBiomeChgr.getMaxRange() - tileBiomeChgr.getCurrRange()) * RegistryBiomeChanger.getMultiFromStack(tileBiomeChgr.prevFuelItem);
				while(remain > 0) {
					ItemStack stack = tileBiomeChgr.prevFuelItem.copy();
					if( remain > tileBiomeChgr.prevFuelItem.getMaxStackSize() ) {
						remain -= stack.stackSize = tileBiomeChgr.prevFuelItem.getMaxStackSize();
					} else {
						stack.stackSize = remain;
						remain = 0;
					}
					world.spawnEntityInWorld(new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, stack));
				}
			}
			tile.invalidate();
		}
		
		super.breakBlock(world, x, y, z, oldID, oldMeta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x+0.0625F, y, z+0.0625F, x+0.9375F, y+0.5625F, z+0.9375F);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x+0.0625F, y, z+0.0625F, x+0.9375F, y+0.5625F, z+0.9375F);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.5625F, 0.9375F);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister regIcon) {
		this.blockIcon = Block.obsidian.getIcon(0,0);
	}
}
