package sanandreasp.mods.EnderStuffPlus.block;

import java.util.List;

import sanandreasp.core.manpack.helpers.IGlowBlockOverlay;
import sanandreasp.core.manpack.helpers.client.RenderBlockGlowOverlay;

import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEndOre
    extends BlockOre
    implements IGlowBlockOverlay
{
    @SideOnly(Side.CLIENT)
    private Icon[] baseIcons = new Icon[2];
    @SideOnly(Side.CLIENT)
    private Icon[] glowIcons = new Icon[2];

    public BlockEndOre(int id) {
        super(id);
    }

    @Override
    public boolean canDragonDestroy(World world, int x, int y, int z) {
        return false;
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return this.baseIcons[Math.min(this.baseIcons.length, meta)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getOverlayInvTexture(int side, int meta) {
        return this.glowIcons[Math.min(this.glowIcons.length, meta)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getOverlayTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return this.getOverlayInvTexture(side, blockAccess.getBlockMetadata(x, y, z));
    }

    @Override
    public int getRenderType() {
        return RenderBlockGlowOverlay.renderID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int id, CreativeTabs creativeTab, List stacks) {
        stacks.add(new ItemStack(this.blockID, 1, 0));
        stacks.add(new ItemStack(this.blockID, 1, 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.baseIcons[0] = iconRegister.registerIcon("enderstuffp:niobOre");
        this.baseIcons[1] = iconRegister.registerIcon("enderstuffp:tantalOre");
        this.glowIcons[0] = iconRegister.registerIcon("enderstuffp:niobOre_glow");
        this.glowIcons[1] = iconRegister.registerIcon("enderstuffp:tantalOre_glow");

        this.blockIcon = this.baseIcons[0];
    }
}
