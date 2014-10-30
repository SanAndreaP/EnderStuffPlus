package de.sanandrew.mods.enderstuffplus.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.core.manpack.util.client.IconFlippedFixed;

public class BlockCorruptEndStone
    extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockCorruptEndStone() {
        super(Blocks.end_stone.getMaterial());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return this.icons[Math.min(3, blockAccess.getBlockMetadata(x, y, z))];
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, world.rand.nextInt(4), 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icons = new IIcon[4];
        this.blockIcon = iconRegister.registerIcon("enderstuffp:corrupt_end_stone");
        this.icons[0] = this.blockIcon;
        this.icons[1] = new IconFlippedFixed(this.blockIcon, true, false);
        this.icons[2] = new IconFlippedFixed(this.blockIcon, false, true);
        this.icons[3] = new IconFlippedFixed(this.blockIcon, true, true);
    }
}
