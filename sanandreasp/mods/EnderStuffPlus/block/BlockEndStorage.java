package sanandreasp.mods.EnderStuffPlus.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOreStorage;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class BlockEndStorage extends BlockOreStorage {

	public BlockEndStorage(int par1) {
		super(par1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("enderstuffp:niobBlock");
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		int thisBlockID = par1World.getBlockId(par2, par3, par4);
		if( par1World.getIndirectPowerOutput(par2, par3, par4 + 1, 3 ) || 
		par1World.getBlockId(par2, par3, par4 + 1) == Block.redstoneWire.blockID && par1World.getBlockMetadata(par2, par3, par4 + 1) > 0) {
			if( par1World.isAirBlock(par2, par3, par4 - 1) ) {
				par1World.setBlock(par2, par3, par4, 0, 0, 2);
				par1World.setBlock(par2, par3, par4 - 1, thisBlockID, 0, 2);
			}
		}
		else if( par1World.getIndirectPowerOutput(par2, par3, par4 - 1, 2 ) ||
		par1World.getBlockId(par2, par3, par4 - 1) == Block.redstoneWire.blockID && par1World.getBlockMetadata(par2, par3, par4 - 1) > 0) {
			if( par1World.isAirBlock(par2, par3, par4 + 1) ) {
				par1World.setBlock(par2, par3, par4, 0, 0, 2);
				par1World.setBlock(par2, par3, par4 + 1, thisBlockID, 0, 2);
			}
		}
		else if( par1World.getIndirectPowerOutput(par2 + 1, par3, par4, 5 ) ||
		par1World.getBlockId(par2 + 1, par3, par4) == Block.redstoneWire.blockID && par1World.getBlockMetadata(par2 + 1, par3, par4) > 0) {
			if( par1World.isAirBlock(par2 - 1, par3, par4) ) {
				par1World.setBlock(par2, par3, par4, 0, 0, 2);
				par1World.setBlock(par2 - 1, par3, par4, thisBlockID, 0, 2);
			}
		}
		else if( par1World.getIndirectPowerOutput(par2 - 1, par3, par4, 4 ) ||
		par1World.getBlockId(par2 - 1, par3, par4) == Block.redstoneWire.blockID && par1World.getBlockMetadata(par2 - 1, par3, par4) > 0) {
			if( par1World.isAirBlock(par2 + 1, par3, par4) ) {
				par1World.setBlock(par2, par3, par4, 0, 0, 2);
				par1World.setBlock(par2 + 1, par3, par4, thisBlockID, 0, 2);
			}
		}
		else if( par1World.getIndirectPowerOutput(par2, par3 - 1, par4, 0) ) {
			if( par1World.isAirBlock(par2, par3 + 1, par4) ) {
				par1World.setBlock(par2, par3, par4, 0, 0, 2);
				par1World.setBlock(par2, par3 + 1, par4, thisBlockID, 0, 2);
			}
		}
	}
	
	@Override
	public boolean isBeaconBase(World worldObj, int x, int y, int z,
			int beaconX, int beaconY, int beaconZ) {
		return true;
	}
	
	@Override
	public boolean canDragonDestroy(World world, int x, int y, int z) {
		return false;
	}
}
