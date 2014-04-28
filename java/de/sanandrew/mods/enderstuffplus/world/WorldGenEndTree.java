package de.sanandrew.mods.enderstuffplus.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;
import de.sanandrew.mods.enderstuffplus.registry.ModBlockRegistry;

public class WorldGenEndTree
    extends WorldGenAbstractTree
{

    public WorldGenEndTree(boolean doBlockNotify) {
        super(doBlockNotify);
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if( world.getBlock(x, y - 1, z) != Blocks.end_stone ) {
            if( world.getBiomeGenForCoords(x, z) != ESPModRegistry.espBiome || world.getBlock(x, y - 1, z) != Blocks.grass) {
                return false;
            }
        }

        int height = 6 + random.nextInt(10);

        for( int y1 = 0; y1 <= height; y1++ ) {
            Block block = world.getBlock(x, y + y1, z);
            if( block != null && !block.isAir(world, x, y + y1, z) ) {
                return false;
            }
        }

        for( int y1 = 0; y1 < height; y1++ ) {
            this.func_150515_a(world, x, y + y1, z, ModBlockRegistry.enderLog);
        }

        for( int x1 = -5; x1 < 5; x1++ ) {
            for( int z1 = -5; z1 < 5; z1++ ) {
                for( int y1 = height; y1 > height - 5; y1-- ) {
                    if( Math.abs(x1) + Math.abs(z1) + (5 - (height - y1)) > 5 ) {
                        continue;
                    }

                    Block block = world.getBlock(x + x1, y + y1, z + z1);
                    if( block == null || block.isAir(world, x + x1, y + y1, z + z1)
                        || block.canBeReplacedByLeaves(world, x + x1, y + y1, z + z1) )
                    {
                        this.func_150515_a(world, x + x1, y + y1, z + z1, ModBlockRegistry.enderLeaves);
                    }
                }
            }
        }

        for( int x1 = -3; x1 < 3; x1++ ) {
            for( int z1 = -3; z1 < 3; z1++ ) {
                for( int y1 = height - 5; y1 > height - 8; y1-- ) {
                    if( Math.abs(x1) + Math.abs(z1) + (8 - (height - y1)) > 3 ) {
                        continue;
                    }

                    Block block = world.getBlock(x + x1, y + y1, z + z1);
                    if( block == null || block.isAir(world, x + x1, y + y1, z + z1)
                        || block.canBeReplacedByLeaves(world, x + x1, y + y1, z + z1) ) {
                        this.func_150515_a(world, x + x1, y + y1, z + z1, ModBlockRegistry.enderLeaves);
                    }
                }
            }
        }

        return true;
    }

}
