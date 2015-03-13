package de.sanandrew.mods.enderstuffp.world;

import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderAvisMother;
import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenAvisNest
    extends WorldGenerator
{
    @Override
    public boolean generate(World world, Random rand, int posX, int posY, int posZ) {
        generateRoom(world, rand, posX, posY, posZ);
        generateEntrance(world, rand, posX, posY, posZ);
        generateEgg(world, rand, posX, posY, posZ);
        System.out.println(String.format("Avis Nest at %d, %d, %d", posX, posY, posZ));
//        // is place valid?
//        for( int x = posX - 3; x <= posX + 3; x++ ) {
//            for( int y = posY - 4; y <= posY; y++ ) {
//                for( int z = posZ - 3; z <= posZ + 3; z++ ) {
//                    if( !(world.getBlock(x, y, z)).getMaterial().isSolid()
//                        || !(world.getBiomeGenForCoords(x, z) instanceof BiomeGenHills) ) {
//                        return false;
//                    }
//                }
//            }
//        }
//
//        // empty room
//        for( int x = posX - 2; x <= posX + 2; x++ ) {
//            for( int y = posY - 3; y <= posY - 1; y++ ) {
//                for( int z = posZ - 2; z <= posZ + 2; z++ ) {
//                    if( world.getBlock(x, y, z) != null && !world.isAirBlock(x, y, z) ) {
//                        world.setBlockToAir(x, y, z);
//                    }
//                }
//            }
//        }
//
//        // ceiling
//        for( int x = posX - 2; x <= posX + 2; x++ ) {
//            for( int z = posZ - 2; z <= posZ + 2; z++ ) {
//                world.setBlock(x, posY, z, Blocks.stonebrick, rand.nextInt(3), 3);
//            }
//        }
//
//        // floor
//        for( int x = posX - 2; x <= posX + 2; x++ ) {
//            for( int z = posZ - 2; z <= posZ + 2; z++ ) {
//                world.setBlock(x, posY - 4, z, Blocks.stonebrick, rand.nextInt(3), 3);
//            }
//        }
//
//        // walls
//        for( int y = posY - 3; y <= posY - 1; y++ ) {
//            for( int x = posX - 2; x <= posX + 2; x++ ) {
//                world.setBlock(x, y, posZ - 3, Blocks.stonebrick, y == posY - 2 ? 3 : rand.nextInt(3), 3);
//                world.setBlock(x, y, posZ + 3, Blocks.stonebrick, y == posY - 2 ? 3 : rand.nextInt(3), 3);
//            }
//            for( int z = posZ - 2; z <= posZ + 2; z++ ) {
//                world.setBlock(posX - 3, y, z, Blocks.stonebrick, y == posY - 2 ? 3 : rand.nextInt(3), 3);
//                world.setBlock(posX + 3, y, z, Blocks.stonebrick, y == posY - 2 ? 3 : rand.nextInt(3), 3);
//            }
//        }
//
//        // place avis egg
//        world.setBlock(posX, posY - 3, posZ, EspBlocks.avisEgg);
//
//        // place chest in corner
//        int chestX = posX + 2 * (rand.nextBoolean() ? -1 : 1);
//        int chestY = posY - 3;
//        int chestZ = posZ + 2 * (rand.nextBoolean() ? -1 : 1);
////        RegistryDungeonLoot.placeLootChest(world, chestX, chestY, chestZ, RegistryDungeonLoot.AVIS_CHEST, rand, 8);
//
        return true;
    }

    private static void generateRoom(World world, Random rand, int posX, int posY, int posZ) {
        for( int x = -2; x <= 2; x++ ) {
            for( int y = -1; y <= 4; y++ ) {
                for( int z = -2; z <= 2; z++ ) {
                    if( Math.abs(x) == 2 && Math.abs(z) == 2 || y == 4 ) {
                        world.setBlock(posX + x, posY + y, posZ + z, Blocks.stained_hardened_clay, 2, new Random().nextInt(2) + 2);
                    } else if( y == -1 && Math.abs(x) != 2 && Math.abs(z) != 2 ) {
                        world.setBlock(posX + x, posY + y, posZ + z, Blocks.hay_block, 0, new Random().nextInt(2) + 2);
                    } else {
                        world.setBlock(posX + x, posY + y, posZ + z, Blocks.air, 0, new Random().nextInt(2) + 2);
                    }
                }
            }
        }
    }

    private void generateEgg(World world, Random rand, int posX, int posY, int posZ) {
        world.setBlock(posX, posY, posZ, EspBlocks.avisEgg, 0, 3);

        EntityEnderAvisMother boss = new EntityEnderAvisMother(world);
        boss.setPositionAndUpdate(posX, posY + 1, posZ);
        boss.setEggPosition(posX, posY, posZ);
        world.spawnEntityInWorld(boss);
    }

    private void generateEntrance( World world, Random rand, int posX, int posY, int posZ ) {
        int currPosX = posX + 5;
        int currPosY = posY;
        int currPosZ = posZ;

        for( int y = 0; y <= 2; y++ ) {
            for( int x = -2; x <= 2; x++ ) {
                for( int z = -2; z <= 2; z++ ) {
                    world.setBlock(currPosX + x, currPosY + y, currPosZ + z, Blocks.air, 0, new Random().nextInt(2) + 2);
                }
            }
        }
        currPosY += 1;
        currPosX += 1;

        boolean reachedSurface = world.canBlockSeeTheSky(currPosX, currPosY, currPosZ);
        while( !reachedSurface ) {
            for( int x = -2; x <= 2; x++ ) {
                for( int z = -2; z <= 2; z++ ) {
                    world.setBlock(currPosX + x, currPosY, currPosZ + z, Blocks.air, 0, new Random().nextInt(2) + 2);
                }
            }
            currPosX += 1;
            currPosY += 1;

            reachedSurface = world.canBlockSeeTheSky(currPosX, currPosY, currPosZ);
        }
    }
}
