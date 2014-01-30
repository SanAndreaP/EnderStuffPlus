package sanandreasp.mods.EnderStuffPlus.block;

import java.util.List;

import sanandreasp.core.manpack.helpers.client.IGlowBlockOverlay;
import sanandreasp.core.manpack.helpers.client.RenderBlockGlowOverlay;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
	
    public BlockEndOre(int id) {
        super(id);
    }
    
    @Override
    public boolean canDragonDestroy(World world, int x, int y, int z) {
    	return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister regIcon) {
    	this.baseTex = new Icon[2];
    	this.glowTex = new Icon[2];
    	this.blockIcon = this.baseTex[0] = regIcon.registerIcon("enderstuffp:niobOre");
    	this.baseTex[1] = regIcon.registerIcon("enderstuffp:tantalOre");
    	this.glowTex[0] = regIcon.registerIcon("enderstuffp:niobOre_glow");
    	this.glowTex[1] = regIcon.registerIcon("enderstuffp:tantalOre_glow");
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
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int id, CreativeTabs tab, List stacks) {
    	stacks.add(new ItemStack(this.blockID, 1, 0));
    	stacks.add(new ItemStack(this.blockID, 1, 1));
    }
}
