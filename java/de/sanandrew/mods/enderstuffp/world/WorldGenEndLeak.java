package de.sanandrew.mods.enderstuffp.world;

import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenEndLeak
    extends WorldGenerator
{
    private static final List<Short> BIOMES = new ArrayList<Short>();
    private static final List<Block> REPLACEABLE_BLOCKS = new ArrayList<Block>();
    private final boolean isInEnd;

    static {
        REPLACEABLE_BLOCKS.add(Blocks.grass);
        REPLACEABLE_BLOCKS.add(Blocks.stone);
        REPLACEABLE_BLOCKS.add(Blocks.dirt);
        REPLACEABLE_BLOCKS.add(Blocks.sand);
        REPLACEABLE_BLOCKS.add(Blocks.sandstone);
        REPLACEABLE_BLOCKS.add(Blocks.water);
        REPLACEABLE_BLOCKS.add(Blocks.flowing_water);
        REPLACEABLE_BLOCKS.add(Blocks.lava);
        REPLACEABLE_BLOCKS.add(Blocks.flowing_lava);
        REPLACEABLE_BLOCKS.add(Blocks.end_stone);

        BIOMES.add((short) BiomeGenBase.plains.biomeID);
        BIOMES.add((short) BiomeGenBase.forest.biomeID);
        BIOMES.add((short) BiomeGenBase.icePlains.biomeID);
        BIOMES.add((short) BiomeGenBase.extremeHillsEdge.biomeID);
        BIOMES.add((short) BiomeGenBase.desert.biomeID);
        BIOMES.add((short) BiomeGenBase.jungle.biomeID);
        BIOMES.add((short) BiomeGenBase.taiga.biomeID);
        BIOMES.add((short) BiomeGenBase.sky.biomeID);
    }

    public WorldGenEndLeak() {
        super();
        this.isInEnd = false;
    }

    public WorldGenEndLeak(boolean inEnd) {
        super();
        this.isInEnd = inEnd;
    }

    private void createPillar(Random rand, World world, int x, int y, int z) {
        int hgt = 8 + rand.nextInt(4);
        for( int i = 0; i < hgt; i++ ) {
            if( world.getBlock(x + 1, y + i, z) == Blocks.obsidian ) {
                return;
            }
            if( world.getBlock(x - 1, y + i, z) == Blocks.obsidian ) {
                return;
            }
            if( world.getBlock(x, y + i, z + 1) == Blocks.obsidian ) {
                return;
            }
            if( world.getBlock(x, y + i, z - 1) == Blocks.obsidian ) {
                return;
            }
        }

        for( int i = 0; i < hgt; i++ ) {
            world.setBlock(x, y + i, z, Blocks.obsidian);
        }
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if( !world.canBlockSeeTheSky(x, y, z) ) {
            return false;
        }

        if( !this.isInEnd && !BIOMES.contains((short) world.getBiomeGenForCoords(x, z).biomeID) ) {
            return false;
        }

        if( !this.isInEnd && Math.abs(x - world.getSpawnPoint().posX) < 256
            && Math.abs(z - world.getSpawnPoint().posZ) < 256 ) {
            return false;
        }

        int radius = 8 + random.nextInt(16);

        if( y <= radius || y + radius >= 256 ) {
            return false;
        }

        System.out.println("GENERATE @x=" + x + " @y=" + y + " @z=" + z);

        int chunkX = x >> 4;
        int chunkY = y >> 4;

        // if( !world.doChunksNearChunkExist(x, y, z, radius) ) {
        IChunkProvider chunkProvider = world.getChunkProvider();
        int radChunk = MathHelper.ceiling_double_int(radius / 16D);
        for( int i = -radChunk; i <= radChunk; i++ ) {
            for( int j = -radChunk; j <= radChunk; j++ ) {
                if( !chunkProvider.chunkExists(chunkX + i, chunkY + j) ) {
                    chunkProvider.loadChunk(chunkX + i, chunkY + j);
                }
            }
        }
        // }

        for( int i = -radius; i <= radius; i++ ) {
            for( int k = -radius; k <= radius; k++ ) {
                for( int j = -radius - 1; j < 0; j++ ) {
                    double radVec = Math.pow(i, 2) + Math.pow(j, 2) + Math.pow(k, 2);

                    if( radVec > Math.pow(radius, 2) ) {
                        continue;
                    }

                    if( world.getHeightValue(x + i, z + k) < y + j ) {
                        continue;
                    }

                    double rad1 = Math.pow(radius - 1, 2);
                    double rad2 = Math.pow(radius - 3, 2);
                    double rad3 = Math.pow(radius - 4, 2);
                    double rad4 = Math.pow(radius - 5, 2);
                    double rad5 = Math.pow(radius - 7, 2);
                    double rad6 = Math.pow(radius - 9, 2);

                    Block baseBlock = Blocks.end_stone;//random.nextInt(10) == 0 ? Blocks.end_stone : EspBlocks.corruptES;

                    if( radVec <= rad6 ) {
                        if( !this.replace(world, x + i, y + j, z + k, Blocks.air) ) {
                            if( this.isInEnd && world.getBlock(x + i, y + j, z + k) == EspBlocks.corruptES ) {
                                // world.setBlockToAir(x+i, y+j, z+k);
                            } else {
                                this.replace(world, x + i, y + j, z + k, baseBlock);
                            }
                        }
                    } else if( radVec > rad6 && radVec <= rad5 ) {
                        if( random.nextInt(3) > 0 && !this.replace(world, x + i, y + j, z + k, Blocks.air) ) {
                            if( this.isInEnd && world.getBlock(x + i, y + j, z + k) == EspBlocks.corruptES ) {
                                // world.setBlockToAir(x+i, y+j, z+k);
                            } else {
                                this.replace(world, x + i, y + j, z + k, baseBlock);
                            }
                        }
                    } else if( radVec > rad5 && radVec <= rad4 ) {
                        if( random.nextInt(5) == 0 ) {
                            this.replace(world, x + i, y + j, z + k, baseBlock);
                        } else if( random.nextInt(3) == 0 ) {
                            if( !this.replace(world, x + i, y + j, z + k, Blocks.air)
                                && world.getBlock(x + i, y + j, z + k) != Blocks.obsidian ) {
                                if( this.isInEnd
                                    && world.getBlock(x + i, y + j, z + k) == EspBlocks.corruptES ) {
                                    // world.setBlockToAir(x+i, y+j, z+k);;
                                } else {
                                    world.setBlock(x + i, y + j, z + k, baseBlock);
                                }
                            }
                        }
                    } else if( radVec > rad4 && radVec <= rad3 ) {
                        if( random.nextInt(2) == 0 ) {
                            this.replace(world, x + i, y + j, z + k, baseBlock);
                        }
                    } else if( radVec > rad3 && radVec <= rad2 ) {
                        this.replace(world, x + i, y + j, z + k, baseBlock);
                        if( random.nextInt(32) == 0 ) {
                            this.createPillar(random, world, x + i, y + j + 1, z + k);
                        }
                    } else if( radVec > rad2 && radVec <= rad1 ) {
                        if( random.nextInt(2) == 0 ) {
                            this.replace(world, x + i, y + j, z + k, baseBlock);
                        }
                    } else if( radVec > rad1 ) {
                        if( random.nextInt(5) == 0 ) {
                            this.replace(world, x + i, y + j, z + k, baseBlock);
                        }
                    }
                }
            }
        }

        boolean spawnerSet = false;
        int maxHgt = random.nextInt(8) + 8;
        for( int i = -3; i <= 3; i++ ) {
            for( int k = -3; k <= 3; k++ ) {
                for( int j = 0; j <= maxHgt + radius; j++ ) {
                    Block baseBlock = /*random.nextInt(10) == 0 ?*/ Blocks.end_stone /*: EspBlocks.corruptES*/;
                    int radVec = Math.abs(i) + Math.abs(j - radius) + Math.abs(k);
                    if( j > radius || radVec <= radius ) {
                        radVec = Math.abs(i) + Math.round((Math.abs(j) / ((float) maxHgt + (float) radius)) * 3F) + Math.abs(k);
                        if( radVec <= 3 ) {
                            if( i == 0 && k == 0 && radVec == 2 && !spawnerSet ) {
                                this.placeMobSpawner(random, world, x + i, y + j - radius - 1, z + k);
                                world.setBlock(x + i, y + j - radius, z + k, baseBlock);
                                spawnerSet = true;
                            } else if( i == 0 && k == 0 && j == maxHgt + radius ) {
//                                RegistryDungeonLoot.placeLootChest(world, x + i, y + j - radius, z + k, RegistryDungeonLoot.ENDLEAK_CHEST,
//                                                                   random, 8);
                            } else {
                                world.setBlock(x + i, y + j - radius, z + k, baseBlock);
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private void placeMobSpawner(Random rand, World world, int x, int y, int z) {
        world.setBlock(x, y, z, Blocks.mob_spawner);
        TileEntityMobSpawner tile = (TileEntityMobSpawner) world.getTileEntity(x, y, z);
        NBTTagCompound nbt = new NBTTagCompound();

        if( tile == null ) {
            tile = (TileEntityMobSpawner) Blocks.mob_spawner.createTileEntity(world, 0);
        }

//        tile.func_145881_a().setEntityName((String) EntityList.classToStringMapping.get(EntityEnderNemesis.class));
        tile.func_145881_a().writeToNBT(nbt);
        nbt.setShort("MinSpawnDelay", (short) 50);
        nbt.setShort("MaxSpawnDelay", (short) 125);
        nbt.setShort("SpawnRange", (short) 16);
        tile.func_145881_a().readFromNBT(nbt);
    }

    private boolean replace(World world, int x, int y, int z, Block block) {
        return this.replace(world, x, y, z, block, 0);
    }

    private boolean replace(World world, int x, int y, int z, Block block, int meta) {
        return this.replace(world, x, y, z, block, meta, 3);
    }

    private boolean replace(World world, int x, int y, int z, Block block, int meta, int replaceFlag) {
        if( block != null ) {
            Block currBlock = world.getBlock(x, y, z);

            if( currBlock == null || currBlock.isAir(world, x, y, z) || REPLACEABLE_BLOCKS.contains(currBlock) ) {
                world.setBlock(x, y, z, block, meta, replaceFlag);
                return true;
            }
        }
        return false;
    }
}
