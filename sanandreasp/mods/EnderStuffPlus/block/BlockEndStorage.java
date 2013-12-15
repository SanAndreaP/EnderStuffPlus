package sanandreasp.mods.EnderStuffPlus.block;

import java.util.List;
import java.util.Random;

import sanandreasp.core.manpack.helpers.IGlowBlockOverlay;
import sanandreasp.core.manpack.helpers.RenderBlockGlowOverlay;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOreStorage;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndStorage extends BlockOreStorage implements IGlowBlockOverlay {
	
	@SideOnly(Side.CLIENT)
	protected Icon baseTex[];
	@SideOnly(Side.CLIENT)
	protected Icon glowTex[];

	public BlockEndStorage(int par1) {
		super(par1);
	}
	
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IconRegister par1IconRegister) {
//		this.blockIcon = par1IconRegister.registerIcon("enderstuffp:niobBlock");
//	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
    	this.baseTex = new Icon[2];
    	this.glowTex = new Icon[2];
    	this.blockIcon = this.baseTex[0] = par1IconRegister.registerIcon("enderstuffp:niobBlock");
    	this.baseTex[1] = par1IconRegister.registerIcon("enderstuffp:tantalBlock");
    	this.glowTex[0] = par1IconRegister.registerIcon("enderstuffp:niobBlock_glow");
    	this.glowTex[1] = par1IconRegister.registerIcon("enderstuffp:tantalBlock_glow");
    }
	
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		int thisBlockID = this.blockID;
		
		if( par1World.getBlockMetadata(par2, par3, par4) != 0 )
			return;
		
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
    
    @Override
    public Icon getIcon(int side, int meta) {
    	return this.baseTex[Math.min(this.baseTex.length, meta)];
    }
    
    @Override
    public int getRenderType() {
        return RenderBlockGlowOverlay.renderID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getOverlayTexture(IBlockAccess world, int x, int y, int z, int side) {
        return this.getOverlayInvTexture(side, world.getBlockMetadata(x, y, z));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getOverlayInvTexture(int side, int meta) {
        return this.glowTex[Math.min(this.glowTex.length, meta)];
    }
    
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
    	par3List.add(new ItemStack(this.blockID, 1, 0));
    	par3List.add(new ItemStack(this.blockID, 1, 1));
//    	super.getSubBlocks(par1, par2CreativeTabs, par3List);
    }
}
