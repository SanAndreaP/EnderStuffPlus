package sanandreasp.mods.EnderStuffPlus.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNemesis;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryDungeonLoot;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenEndLeak extends WorldGenerator {
	private static final List<Short> replaceableBlocks = new ArrayList<Short>();
	private static final List<Short> biomes = new ArrayList<Short>();
	private final boolean isInEnd;
	
	public WorldGenEndLeak() {
		super();
		this.isInEnd = false;
	}
	
	public WorldGenEndLeak(boolean inEnd) {
		super();
		this.isInEnd = inEnd;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) 
	{
		if( !world.canBlockSeeTheSky(x, y, z) )
			return false;
		if( !isInEnd && !biomes.contains((short) world.getBiomeGenForCoords(x, z).biomeID) )
			return false;
		if( !isInEnd && Math.abs(x - world.getSpawnPoint().posX ) < 256
				&& Math.abs(z - world.getSpawnPoint().posZ) < 256)
			return false;
		
		int radius = 8 + random.nextInt(16);
		
		if( y <= radius || y + radius >= 256  )
			return false;
		
		int chunkX = x >> 4;
		int chunkY = y >> 4;
		
	    if( !world.doChunksNearChunkExist(x, y, z, radius) ) {
	        for( int i = -(radius >> 4); i <= (radius >> 4); i++) {
	            for( int j = -(radius >> 4); j <= (radius >> 4); j++) {
	                world.getChunkProvider().loadChunk(chunkX+i, chunkY+j);
	            }
	        }
	    }
		
		for( int i = -radius; i <= radius; i++ ) {
			for( int k = -radius; k <= radius; k++ ) {
				for( int j = -radius-1; j < 0; j++ ) {
					double radVec = Math.pow(i, 2)+Math.pow(j, 2)+Math.pow(k, 2);
					if( radVec > Math.pow(radius, 2) )
						continue;
					if(world.getHeightValue(x+i, z+k) < y+j)
						continue;
					
					double rad1 = Math.pow(radius-1, 2);
					double rad2 = Math.pow(radius-3, 2);
					double rad3 = Math.pow(radius-4, 2);
					double rad4 = Math.pow(radius-5, 2);
					double rad5 = Math.pow(radius-7, 2);
					double rad6 = Math.pow(radius-9, 2);
					
					if( radVec <= rad6 ) {
						if( !this.replace(world, x+i, y+j, z+k, 0) ) {
							if( isInEnd && world.getBlockId(x+i, y+j, z+k) == ESPModRegistry.corruptES.blockID )
								world.setBlockToAir(x+i, y+j, z+k);
							else
								this.replace(world, x+i, y+j, z+k, ESPModRegistry.corruptES.blockID);
						}
					} else if( radVec > rad6 && radVec <= rad5 ) {
						if( random.nextInt(3) > 0 && !this.replace(world, x+i, y+j, z+k, 0) ) {
							if( isInEnd && world.getBlockId(x+i, y+j, z+k) == ESPModRegistry.corruptES.blockID )
								world.setBlockToAir(x+i, y+j, z+k);
							else
								this.replace(world, x+i, y+j, z+k, ESPModRegistry.corruptES.blockID);
						}
					} else if( radVec > rad5 && radVec <= rad4 ) {
						if( random.nextInt(5) == 0 )
							this.replace(world, x+i, y+j, z+k, ESPModRegistry.corruptES.blockID);
						else if( random.nextInt(3) == 0 ) {
							if( !this.replace(world, x+i, y+j, z+k, 0) && world.getBlockId(x+i, y+j, z+k) != Block.obsidian.blockID ) {
								if( isInEnd && world.getBlockId(x+i, y+j, z+k) == ESPModRegistry.corruptES.blockID )
									world.setBlockToAir(x+i, y+j, z+k);
								else
									world.setBlock(x+i, y+j, z+k, ESPModRegistry.corruptES.blockID);
							}
						}
					} else if( radVec > rad4 && radVec <= rad3 ) {
						if( random.nextInt(2) == 0 )
							this.replace(world, x+i, y+j, z+k, ESPModRegistry.corruptES.blockID);
					} else if( radVec > rad3 && radVec <= rad2 ) {
						this.replace(world, x+i, y+j, z+k, ESPModRegistry.corruptES.blockID);
						if( random.nextInt(32) == 0 ) {
							this.createPillar(random, world, x+i, y+j+1, z+k);
						}
					} else if( radVec > rad2 && radVec <= rad1 ) {
						if( random.nextInt(2) == 0 )
							this.replace(world, x+i, y+j, z+k, ESPModRegistry.corruptES.blockID);
					} else if( radVec > rad1 ) {
						if( random.nextInt(5) == 0 )
							this.replace(world, x+i, y+j, z+k, ESPModRegistry.corruptES.blockID);
					}
				}
			}
		}
		
		boolean spawnerSet = false;
		boolean chestSet = false;
		int maxHgt = random.nextInt(8)+8;
		for( int i = -3; i <= 3; i++ ) {
			for( int k = -3; k <= 3; k++ ) {
				for( int j = 0; j <= maxHgt+radius; j++ ) {
				int radVec = Math.abs(i) + Math.abs(j-radius) + Math.abs(k);
					if( j > radius || radVec <= radius ) {
						radVec = Math.abs(i) + Math.round(((float)Math.abs(j)/((float)maxHgt+(float)radius))*3F) + Math.abs(k);
						if( radVec <= 3 ) {
							if( i == 0 && k == 0 && radVec == 2 && !spawnerSet ) {
								this.placeMobSpawner(random, world, x+i, y+j-radius-1, z+k);
								world.setBlock(x+i, y+j-radius, z+k, ESPModRegistry.corruptES.blockID);
								spawnerSet = true;
							} else if( i == 0 && k == 0 && j == maxHgt+radius ) {
								RegistryDungeonLoot.placeLootChest(world, x+i, y+j-radius, z+k, RegistryDungeonLoot.ENDLEAK_CHEST, random, 8);
							} else {
//								if( random.nextInt(256) == 0 ) 
//									world.setBlock(x+i, y+j-radius, z+k, ESPModRegistry.niobOre.blockID);
//								else
									world.setBlock(x+i, y+j-radius, z+k, ESPModRegistry.corruptES.blockID);
							}
						}
					}
				}
			}
		}

		return true;
	}
	
	private void createPillar(Random rand, World world, int x, int y, int z) {
		int hgt = 8 + rand.nextInt(4);
		for( int i = 0; i < hgt; i++ ) {
			if( world.getBlockId(x+1, y+i, z) == Block.obsidian.blockID )
				return;
			if( world.getBlockId(x-1, y+i, z) == Block.obsidian.blockID )
				return;
			if( world.getBlockId(x, y+i, z+1) == Block.obsidian.blockID )
				return;
			if( world.getBlockId(x, y+i, z-1) == Block.obsidian.blockID )
				return;
		}
		
		for( int i = 0; i < hgt; i++ ) {
			world.setBlock(x, y+i, z, Block.obsidian.blockID);
		}
	}
	
	private void placeMobSpawner(Random rand, World world, int x, int y, int z) {
		world.setBlock(x, y, z, Block.mobSpawner.blockID);
		TileEntityMobSpawner tile = (TileEntityMobSpawner) world.getBlockTileEntity(x, y, z);
		if( tile == null ) {
			tile = (TileEntityMobSpawner) Block.mobSpawner.createTileEntity(world, 0);
		}
		tile.getSpawnerLogic().setMobID((String) EntityList.classToStringMapping.get(EntityEnderNemesis.class));
		NBTTagCompound nbt = new NBTTagCompound();
		tile.getSpawnerLogic().writeToNBT(nbt);
		nbt.setShort("MinSpawnDelay", (short)50);
		nbt.setShort("MaxSpawnDelay", (short)125);
		nbt.setShort("SpawnRange", (short)16);
		tile.getSpawnerLogic().readFromNBT(nbt);
	}
	
	private boolean replace(World world, int x, int y, int z, int blockID) {
		return this.replace(world, x, y, z, blockID, 0);
	}
	
	private boolean replace(World world, int x, int y, int z, int blockID, int meta) {
		return this.replace(world, x, y, z, blockID, meta, 3);
	}
	
	private boolean replace(World world, int x, int y, int z, int blockID, int meta, int replaceFlag) {
		short currBlockID = (short) world.getBlockId(x, y, z);
		Block currBlock = Block.blocksList[currBlockID];
		if( currBlock == null || currBlock.isAirBlock(world, x, y, z) || replaceableBlocks.contains(currBlockID) ) {
			world.setBlock(x, y, z, blockID, meta, replaceFlag);
			return true;
		}
		return false;
	}
	
	static {
		replaceableBlocks.add((short) Block.grass.blockID);
		replaceableBlocks.add((short) Block.stone.blockID);
		replaceableBlocks.add((short) Block.dirt.blockID);
		replaceableBlocks.add((short) Block.sand.blockID);
		replaceableBlocks.add((short) Block.sandStone.blockID);
		replaceableBlocks.add((short) Block.waterStill.blockID);
		replaceableBlocks.add((short) Block.waterMoving.blockID);
		replaceableBlocks.add((short) Block.lavaStill.blockID);
		replaceableBlocks.add((short) Block.lavaMoving.blockID);
		replaceableBlocks.add((short) Block.whiteStone.blockID);
		
		biomes.add((short) BiomeGenBase.plains.biomeID);
		biomes.add((short) BiomeGenBase.forest.biomeID);
		biomes.add((short) BiomeGenBase.icePlains.biomeID);
		biomes.add((short) BiomeGenBase.extremeHillsEdge.biomeID);
		biomes.add((short) BiomeGenBase.desert.biomeID);
		biomes.add((short) BiomeGenBase.jungle.biomeID);
		biomes.add((short) BiomeGenBase.taiga.biomeID);
		biomes.add((short) BiomeGenBase.sky.biomeID);
	}
}
