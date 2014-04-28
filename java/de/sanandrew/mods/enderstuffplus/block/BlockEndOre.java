package de.sanandrew.mods.enderstuffplus.block;

import java.util.List;

import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.core.manpack.util.IGlowBlockOverlay;
import de.sanandrew.core.manpack.util.client.RenderBlockGlowOverlay;

public class BlockEndOre
    extends BlockOre
    implements IGlowBlockOverlay
{
    @SideOnly(Side.CLIENT)
    private IIcon[] baseIcons = new IIcon[2];
    @SideOnly(Side.CLIENT)
    private IIcon[] glowIcons = new IIcon[2];

    public BlockEndOre() {
        super();
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess blockAccess, int x, int y, int z, Entity entity) {
        return false;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return this.baseIcons[Math.min(this.baseIcons.length, meta)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getOverlayInvTexture(int side, int meta) {
        return this.glowIcons[Math.min(this.glowIcons.length, meta)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getOverlayTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return this.getOverlayInvTexture(side, blockAccess.getBlockMetadata(x, y, z));
    }

    @Override
    public int getRenderType() {
        return RenderBlockGlowOverlay.renderID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(Item item, CreativeTabs creativeTab, List stacks) {
        stacks.add(new ItemStack(this, 1, 0));
        stacks.add(new ItemStack(this, 1, 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.baseIcons[0] = iconRegister.registerIcon("enderstuffp:niobOre");
        this.baseIcons[1] = iconRegister.registerIcon("enderstuffp:tantalOre");
        this.glowIcons[0] = iconRegister.registerIcon("enderstuffp:niobOre_glow");
        this.glowIcons[1] = iconRegister.registerIcon("enderstuffp:tantalOre_glow");

        this.blockIcon = this.baseIcons[0];
    }
}
