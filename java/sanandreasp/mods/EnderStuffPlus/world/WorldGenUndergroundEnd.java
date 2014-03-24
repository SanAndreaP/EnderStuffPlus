package sanandreasp.mods.EnderStuffPlus.world;

import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.registry.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenUndergroundEnd extends WorldGenerator {

	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		//Begins the recursive generation
		boolean b = this.recursiveGenerateSpheres(world,random,i,j,k,16);

		world.setBlock(i, j, k, BlockRegistry.avisEgg.blockID);
		return b;
	}

	/**Yea boi*/
	public boolean recursiveGenerateSpheres(World world, Random random, int i, int j, int k, int initialRadius){
		if( initialRadius < 9 ){
			return true;
		}
		int radius =  initialRadius - 4 + random.nextInt(9);
		this.createSphere(world, random, i - 5 + random.nextInt(11), j ,k , radius, 4);
		for( int c = 0;c<3+random.nextInt(3);c++ ){
			int i1 = 0;
			int j1 =2 + random.nextInt(10);
			int k1 = 0;
			while(Math.abs(i1) + Math.abs(k1) < radius*3/2 && world.getBlockId(i+i1,j-j1 ,k+k1 ) != Block.stone.blockID){
				i1 += (2 - random.nextInt(5));
				k1 += (2 - random.nextInt(5));
			}
			this.recursiveGenerateSpheres(world,random,i+i1,j-j1,k+k1,initialRadius - 3);
		}
		return true;
	}

	/**Creates a single hollow sphere together with the floor and Small Mushrooms.**/
	public void createSphere(World world, Random random,int i, int j, int k, int radius, int randomFactor)
	{
		//Used for creating mushroom grass floor at the bottom
		int minimum = -radius - randomFactor/2 + random.nextInt(randomFactor+1);

		//Nested loop that creates the sphere etc. the random things are just to randomise the shape, all that reall matters is radius and -radius
		for( int i1=-radius - randomFactor/2 + random.nextInt(randomFactor+1); i1<radius - randomFactor/2 + random.nextInt(randomFactor+1); i1++ ){
			for( int k1=-radius - randomFactor/2 + random.nextInt(randomFactor+1); k1<radius - randomFactor/2 + random.nextInt(randomFactor+1); k1++ ){
				for( int j1= radius - randomFactor/2 + random.nextInt(randomFactor+1); j1>minimum; j1-- ){

					//Part which makes the loop cube into a sphere
					if( Math.pow(i1, 2) + Math.pow(j1, 2) + Math.pow(k1, 2) < Math.pow(radius, 2) ){

						if( this.canEdit(world,i + i1, j + j1, k + k1) ){
							//Puts set liquid if too close to bottom of world (right now it's lava)
							if( j+j1 <10 ){
								world.setBlock(i + i1, j + j1, k + k1, Block.waterStill.blockID, 0, random.nextBoolean() ? 2 : 3);
							}
							//Hollowing area and filling in the bottom middle (minimum is used here)
							else if (
									j1 == minimum+2 && world.getBlockId(i + i1, j + j1+1, k + k1) == 0 && world.getBlockId(i + i1, j + j1, k + k1) != 0){
								if( random.nextInt(42) == 0 ){
									this.plantObsidianPillar(world,random,i + i1, j + j1+1, k + k1, radius - random.nextInt(5));
								}


								world.setBlock(i + i1, j + j1, k + k1, Block.whiteStone.blockID, 0, random.nextBoolean() ? 2 : 3);
								world.setBlock(i + i1, j + j1-1, k + k1, Block.whiteStone.blockID, 0, random.nextBoolean() ? 2 : 3);
//								world.setBlock(i + i1, j + j1-1, k + k1, mod_Shroom.mushroomDirt.blockID);
							}
							else{
								//Hollows out
								if( j1 != minimum+1 ){
									boolean b = false;
									for( int y = 1; y < radius && world.getBlockId(i + i1, j + j1 + y - 1, k + k1) != Block.obsidian.blockID; y++ ) {
										if( world.getBlockId(i + i1, j + j1 + y + 1, k + k1) == Block.obsidian.blockID ) {
											b = true;
											break;
										}
									}
									if( b ) {
                                        world.setBlock(i + i1, j + j1, k + k1, Block.obsidian.blockID, 0, random.nextBoolean() ? 2 : 3);
                                    } else if( world.getBlockId(i + i1, j + j1 + 1, k + k1) != Block.waterStill.blockID && world.getBlockId(i + i1, j + j1 + 1, k + k1) != Block.waterMoving.blockID ) {
                                        world.setBlock(i + i1, j + j1, k + k1, 0, 0, random.nextBoolean() ? 2 : 3);
                                    }
								}
							}
						}
					}
					//Putting mushroom grass and dirt etc. onto the side areas
					else{
						if( this.canEdit(world,i + i1, j + j1, k + k1) ){
							if( world.getBlockId(i + i1, j + j1+1, k + k1) == 0 && world.getBlockId(i + i1, j + j1, k + k1) != 0 && j1<6 ){
								if( j+j1 <10 ){
									world.setBlock(i + i1, j + j1, k + k1, Block.waterStill.blockID, 0, random.nextBoolean() ? 2 : 3);
								}
								else {
//									if( random.nextInt(42) == 0 ){
//										plantSmallMushroom(world,random,i + i1, j + j1+1, k + k1);
//									}

									world.setBlock(i + i1, j + j1, k + k1, Block.whiteStone.blockID, 0, random.nextBoolean() ? 2 : 3);
									world.setBlock(i + i1, j + j1-1, k + k1, Block.whiteStone.blockID, 0, random.nextBoolean() ? 2 : 3);
//									world.setBlock(i + i1, j + j1 -1, k + k1, mod_Shroom.mushroomDirt.blockID);
								}
							}
						}
					}

				}

			}
		}

	}

	/**Generate a small random mushroom block. */
	public void plantObsidianPillar(World world, Random random, int i, int j, int k, int maxHeight) {
		for( int i1 = 0; i1 < maxHeight; i1++ ) {
			world.setBlock(i, j+i1, k, Block.obsidian.blockID, 0, random.nextBoolean() ? 2 : 3);
		}
		if( maxHeight > 1) {
            world.setBlock(i, j+maxHeight, k, Block.glowStone.blockID, 0, random.nextBoolean() ? 2 : 3 );
        }
	}
//	public void plantSmallMushroom(World world, Random random, int i, int j, int k){
////		if( random.nextInt(5)==0 ){
////			world.setBlock(i,j,k,mod_Shroom.purpleMushroom.blockID);
////		}
////		else{
////			world.setBlock(i,j,k,mod_Shroom.blueMushroom.blockID, 1, 3);
////		}
//	}
	/** Generate a Large mushroom.*/
	/*public void plantLargeMushroom(World world, Random random, int i, int j, int k){
		if( random.nextInt(5)==0 ){
			bigPurpleMush.generate(world, random, i, j, k);
		}
		else{
			bigBlueMush.generate(world, random, i, j, k);
		}
	}*/

	/**Checks if the area has any blocks that should be changed */
	public boolean canEdit(World world, int i, int j, int k){
		int blockID = world.getBlockId(i, j, k);
		return blockID != Block.glowStone.blockID && blockID != Block.obsidian.blockID;
//		return world.getBlockId(i, j, k) != mod_Shroom.mushroomCapBlue.blockID || world.getBlockId(i, j, k) != mod_Shroom.mushroomCapPurple.blockID ;
//		return true;

	}

//	@Override
//	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
//		recursiveGenerate(world,random,chunkX*16 + random.nextInt(16),40 + random.nextInt(10),chunkZ*16 + random.nextInt(16),16);
//	}
}
