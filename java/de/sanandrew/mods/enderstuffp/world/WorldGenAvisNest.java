package de.sanandrew.mods.enderstuffp.world;

import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenHills;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenAvisNest
    extends WorldGenerator
{
    @Override
    public boolean generate(World world, Random rand, int posX, int posY, int posZ) {
        // is place valid?
        for( int x = posX - 3; x <= posX + 3; x++ ) {
            for( int y = posY - 4; y <= posY; y++ ) {
                for( int z = posZ - 3; z <= posZ + 3; z++ ) {
                    if( !(world.getBlock(x, y, z)).getMaterial().isSolid()
                        || !(world.getBiomeGenForCoords(x, z) instanceof BiomeGenHills) ) {
                        return false;
                    }
                }
            }
        }

        // empty room
        for( int x = posX - 2; x <= posX + 2; x++ ) {
            for( int y = posY - 3; y <= posY - 1; y++ ) {
                for( int z = posZ - 2; z <= posZ + 2; z++ ) {
                    if( world.getBlock(x, y, z) != null && !world.isAirBlock(x, y, z) ) {
                        world.setBlockToAir(x, y, z);
                    }
                }
            }
        }

        // ceiling
        for( int x = posX - 2; x <= posX + 2; x++ ) {
            for( int z = posZ - 2; z <= posZ + 2; z++ ) {
                world.setBlock(x, posY, z, Blocks.stonebrick, rand.nextInt(3), 3);
            }
        }

        // floor
        for( int x = posX - 2; x <= posX + 2; x++ ) {
            for( int z = posZ - 2; z <= posZ + 2; z++ ) {
                world.setBlock(x, posY - 4, z, Blocks.stonebrick, rand.nextInt(3), 3);
            }
        }

        // walls
        for( int y = posY - 3; y <= posY - 1; y++ ) {
            for( int x = posX - 2; x <= posX + 2; x++ ) {
                world.setBlock(x, y, posZ - 3, Blocks.stonebrick, y == posY - 2 ? 3 : rand.nextInt(3), 3);
                world.setBlock(x, y, posZ + 3, Blocks.stonebrick, y == posY - 2 ? 3 : rand.nextInt(3), 3);
            }
            for( int z = posZ - 2; z <= posZ + 2; z++ ) {
                world.setBlock(posX - 3, y, z, Blocks.stonebrick, y == posY - 2 ? 3 : rand.nextInt(3), 3);
                world.setBlock(posX + 3, y, z, Blocks.stonebrick, y == posY - 2 ? 3 : rand.nextInt(3), 3);
            }
        }

        // place avis egg
        world.setBlock(posX, posY - 3, posZ, EspBlocks.avisEgg);

        // place chest in corner
        int chestX = posX + 2 * (rand.nextBoolean() ? -1 : 1);
        int chestY = posY - 3;
        int chestZ = posZ + 2 * (rand.nextBoolean() ? -1 : 1);
//        RegistryDungeonLoot.placeLootChest(world, chestX, chestY, chestZ, RegistryDungeonLoot.AVIS_CHEST, rand, 8);

        return true;
    }
}
