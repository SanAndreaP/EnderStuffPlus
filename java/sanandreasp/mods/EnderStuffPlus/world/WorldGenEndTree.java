package sanandreasp.mods.EnderStuffPlus.world;

import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.registry.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenEndTree extends WorldGenerator {

	public WorldGenEndTree(boolean b) {
		super(b);
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		if( world.getBlockId(x, y-1, z) != Block.whiteStone.blockID ) {
            return false;
        }

		int height = 6 + random.nextInt(10);

		for( int y1 = 0; y1 <= height; y1++ ) {
			Block block = Block.blocksList[world.getBlockId(x, y+y1, z)];
			if( block != null && !block.isAirBlock(world, x, y+y1, z) ) {
                return false;
            }
		}

		for( int y1 = 0; y1 < height; y1++ ) {
			this.setBlock(world, x, y+y1, z, BlockRegistry.enderLog.blockID);
		}


		for( int x1 = -5; x1 < 5; x1++ ) {
			for( int z1 = -5; z1 < 5; z1++ ) {
				for( int y1 = height; y1 > height - 5; y1-- ) {
					//i*i+k*k > (rad-Math.abs(j))*(rad-Math.abs(j))
					if( Math.abs(x1) + Math.abs(z1) + (5-(height-y1)) > 5 ) {
                        continue;
                    }

					Block block = Block.blocksList[world.getBlockId(x+x1, y+y1, z+z1)];
					if( block == null || block.isAirBlock(world, x+x1, y+y1, z+z1) || block.canBeReplacedByLeaves(world, x+x1, y+y1, z+z1) ) {
                        this.setBlock(world, x+x1, y+y1, z+z1, BlockRegistry.enderLeaves.blockID);
                    }
				}
			}
		}

		for( int x1 = -3; x1 < 3; x1++ ) {
			for( int z1 = -3; z1 < 3; z1++ ) {
				for( int y1 = height-5; y1 > height - 8; y1-- ) {
					//i*i+k*k > (rad-Math.abs(j))*(rad-Math.abs(j))
					if( Math.abs(x1) + Math.abs(z1) + (8-(height-y1)) > 3 ) {
                        continue;
                    }

					Block block = Block.blocksList[world.getBlockId(x+x1, y+y1, z+z1)];
					if( block == null || block.isAirBlock(world, x+x1, y+y1, z+z1) || block.canBeReplacedByLeaves(world, x+x1, y+y1, z+z1) ) {
                        this.setBlock(world, x+x1, y+y1, z+z1, BlockRegistry.enderLeaves.blockID);
                    }
				}
			}
		}

		return true;
	}

}
