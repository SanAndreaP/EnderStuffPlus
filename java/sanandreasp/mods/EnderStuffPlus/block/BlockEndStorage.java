package sanandreasp.mods.EnderStuffPlus.block;

import java.util.List;

import sanandreasp.core.manpack.helpers.client.IGlowBlockOverlay;
import sanandreasp.core.manpack.helpers.client.RenderBlockGlowOverlay;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOreStorage;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndStorage extends BlockOreStorage implements IGlowBlockOverlay
{
	@SideOnly(Side.CLIENT)
	protected Icon baseTex[];
	@SideOnly(Side.CLIENT)
	protected Icon glowTex[];
	@SideOnly(Side.CLIENT)
	protected Icon baseTexTop[];
	@SideOnly(Side.CLIENT)
	protected Icon glowTexTop[];

	public BlockEndStorage(int id) {
		super(id);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister regIcon) {
    	this.baseTex = new Icon[2];
    	this.glowTex = new Icon[2];
    	this.baseTexTop = new Icon[2];
    	this.glowTexTop = new Icon[2];
    	this.blockIcon = this.baseTex[0] = regIcon.registerIcon("enderstuffp:niobBlock");
    	this.baseTex[1] = regIcon.registerIcon("enderstuffp:tantalBlock");
    	this.glowTex[0] = regIcon.registerIcon("enderstuffp:niobBlock_glow");
    	this.glowTex[1] = regIcon.registerIcon("enderstuffp:tantalBlock_glow");
    	this.baseTexTop[0] = regIcon.registerIcon("enderstuffp:niobBlockTop");
    	this.baseTexTop[1] = regIcon.registerIcon("enderstuffp:tantalBlockTop");
    	this.glowTexTop[0] = regIcon.registerIcon("enderstuffp:niobBlockTop_glow");
    	this.glowTexTop[1] = regIcon.registerIcon("enderstuffp:tantalBlockTop_glow");
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int side) {
		int thisBlockID = this.blockID;
		
		if( world.getBlockMetadata(x, y, z) != 0 ) {
			return;
		}
		
		if( world.getIndirectPowerOutput(x, y, z + 1, 3 )
			|| (Block.blocksList[world.getBlockId(x, y, z + 1)] == Block.redstoneWire
				&& world.getBlockMetadata(x, y, z + 1) > 0) )
		{
			if( world.isAirBlock(x, y, z - 1) ) {
				world.setBlock(x, y, z, 0, 0, 2);
				world.setBlock(x, y, z - 1, thisBlockID, 0, 2);
			}
		} else if( world.getIndirectPowerOutput(x, y, z - 1, 2 )
				   || (Block.blocksList[world.getBlockId(x, y, z - 1)] == Block.redstoneWire
				       && world.getBlockMetadata(x, y, z - 1) > 0) )
		{
			if( world.isAirBlock(x, y, z + 1) ) {
				world.setBlock(x, y, z, 0, 0, 2);
				world.setBlock(x, y, z + 1, thisBlockID, 0, 2);
			}
		} else if( world.getIndirectPowerOutput(x + 1, y, z, 5 )
				   || (Block.blocksList[world.getBlockId(x + 1, y, z)] == Block.redstoneWire
				       && world.getBlockMetadata(x + 1, y, z) > 0) )
		{
			if( world.isAirBlock(x - 1, y, z) ) {
				world.setBlock(x, y, z, 0, 0, 2);
				world.setBlock(x - 1, y, z, thisBlockID, 0, 2);
			}
		} else if( world.getIndirectPowerOutput(x - 1, y, z, 4 )
				   || (Block.blocksList[world.getBlockId(x - 1, y, z)] == Block.redstoneWire
				       && world.getBlockMetadata(x - 1, y, z) > 0) )
		{
			if( world.isAirBlock(x + 1, y, z) ) {
				world.setBlock(x, y, z, 0, 0, 2);
				world.setBlock(x + 1, y, z, thisBlockID, 0, 2);
			}
		} else if( world.getIndirectPowerOutput(x, y - 1, z, 0) ) {
			if( world.isAirBlock(x, y + 1, z) ) {
				world.setBlock(x, y, z, 0, 0, 2);
				world.setBlock(x, y + 1, z, thisBlockID, 0, 2);
			}
		}
	}
	
	@Override
	public boolean isBeaconBase(World worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return true;
	}
	
	@Override
	public boolean canDragonDestroy(World world, int x, int y, int z) {
		return false;
	}
    
    @Override
    public Icon getIcon(int side, int meta) {
    	return side < 2 
    			? this.baseTexTop[Math.min(this.baseTexTop.length, meta)] 
    			: this.baseTex[Math.min(this.baseTex.length, meta)];
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
        return side < 2 ? this.glowTexTop[Math.min(this.glowTexTop.length, meta)] : this.glowTex[Math.min(this.glowTex.length, meta)];
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int id, CreativeTabs tabs, List stacks) {
    	stacks.add(new ItemStack(this.blockID, 1, 0));
    	stacks.add(new ItemStack(this.blockID, 1, 1));
    }
}
