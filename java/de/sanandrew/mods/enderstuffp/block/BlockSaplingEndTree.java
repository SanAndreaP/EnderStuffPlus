package de.sanandrew.mods.enderstuffp.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.world.WorldGenEndTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BlockSaplingEndTree
    extends BlockBush
{
    public BlockSaplingEndTree() {
        super();

        float size = 0.4F;
        this.setBlockBounds(0.5F - size, 0.0F, 0.5F - size, 0.5F + size, size * 2.0F, 0.5F + size);
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return world.getBlock(x, y - 1, z) == Blocks.end_stone;
    }

    @Override
    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.end_stone;
    }

    @Override
    public int damageDropped(int meta) {
        return meta & 3;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.blockIcon;
    }

    public void growTree(World world, int x, int y, int z, Random random) {
        if( !TerrainGen.saplingGrowTree(world, random, x, y, z) ) {
            return;
        }

        world.setBlock(x, y, z, Blocks.air, 0, 4);

        if( !(new WorldGenEndTree(true)).generate(world, random, x, y, z) ) {
            int meta = world.getBlockMetadata(x, y, z) & 3;

            world.setBlock(x, y, z, this, meta, 4);
        }
    }

    public void markOrGrowMarked(World world, int x, int y, int z, Random random) {
        int meta = world.getBlockMetadata(x, y, z);

        if( (meta & 8) == 0 ) {
            world.setBlockMetadataWithNotify(x, y, z, meta | 8, 4);
        } else {
            this.growTree(world, x, y, z, random);
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if( !world.isRemote ) {
            super.updateTick(world, x, y, z, random);

            if( (world.getBlockLightValue(x, y + 1, z) >= 3) && (random.nextInt(7) == 0) ) {
                this.markOrGrowMarked(world, x, y, z, random);
            }
        }
    }
}
