package sanandreasp.mods.EnderStuffPlus.block;

import java.util.List;

import sanandreasp.core.manpack.helpers.IGlowBlockOverlay;
import sanandreasp.core.manpack.helpers.RenderBlockGlowOverlay;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndOre extends BlockOre implements IGlowBlockOverlay
{
	@SideOnly(Side.CLIENT)
	protected Icon baseTex[];
	@SideOnly(Side.CLIENT)
	protected Icon glowTex[];
	
    public BlockEndOre(int par1) {
        super(par1);
    }
    
    @Override
    public boolean canDragonDestroy(World world, int x, int y, int z) {
    	return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
    	this.baseTex = new Icon[2];
    	this.glowTex = new Icon[2];
    	this.blockIcon = this.baseTex[0] = par1IconRegister.registerIcon("enderstuffp:niobOre");
    	this.baseTex[1] = par1IconRegister.registerIcon("enderstuffp:tantalOre");
    	this.glowTex[0] = par1IconRegister.registerIcon("enderstuffp:niobOre_glow");
    	this.glowTex[1] = par1IconRegister.registerIcon("enderstuffp:tantalOre_glow");
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
