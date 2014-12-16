package de.sanandrew.mods.enderstuffp.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.client.IGlowBlockOverlay;
import de.sanandrew.core.manpack.util.client.RenderBlockGlowOverlay;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public class BlockEndOre
    extends BlockOre
    implements IGlowBlockOverlay
{
    public static final int NIOBIUM = 0;
    public static final int TANTALUM = 1;

    private final String[][] types = new String[][] {
            new String[] {"oreNiobium", "ore_niobium", "ore_niobium_glow"},
            new String[] {"oreTantalum", "ore_tantalum", "ore_tantalum_glow"}
    };

    @SideOnly(Side.CLIENT)
    private IIcon[] baseIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] glowIcons;

    public BlockEndOre() {
        super();
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess blockAccess, int x, int y, int z, Entity entity) {
        return false;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return this.baseIcons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getOverlayInvTexture(int side, int meta) {
        return this.glowIcons[meta];
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
        for( int i = 0; i < this.types.length; i++ ) {
            stacks.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.baseIcons = new IIcon[this.types.length];
        this.glowIcons = new IIcon[this.types.length];

        for( int i = 0; i < this.types.length; i++ ) {
            this.baseIcons[i] = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ':' + this.types[i][1]);
            this.glowIcons[i] = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ':' + this.types[i][2]);
        }

        this.blockIcon = Blocks.end_stone.getIcon(0, 0);
    }
}
