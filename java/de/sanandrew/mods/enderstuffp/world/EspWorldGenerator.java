package de.sanandrew.mods.enderstuffp.world;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import de.sanandrew.mods.enderstuffp.util.EspConfiguration;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import java.util.Random;

public class EspWorldGenerator
        implements IWorldGenerator
{
    @SubscribeEvent
    public void onPoopulateChunkPre(PopulateChunkEvent.Pre event) {
        switch( event.world.provider.dimensionId ){
            case 1 :
                this.populateEndPre(event.world.rand, event.chunkX, event.chunkZ, event.world, event.chunkProvider);
        }
    }

    @SubscribeEvent
    public void onPopulateChunkPost(PopulateChunkEvent.Post event) {
        if( !event.world.getWorldInfo().isMapFeaturesEnabled() ) {
            return;
        }

        switch( event.world.provider.dimensionId ){
            case 0 :
                this.populateSurfacePost(event.rand, event.chunkX, event.chunkZ, event.world, event.chunkProvider);
                break;
        }
    }

    public void populateEndPre(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkProvider) {
        int x, y, z;

//        if( chunkX % 3 == 0 && chunkZ % 3 == 0 ) {
//            (new WorldGenEndIsland()).generate(world, random, chunkX * 16, 64, chunkZ * 16);
//        }
        if( random.nextInt(16) == 0 && EspConfiguration.genEndlessEnd && (Math.abs(chunkX) > 10 || Math.abs(chunkZ) > 10) ) {
            x = chunkX * 16 + random.nextInt(16);
            z = chunkZ * 16 + random.nextInt(16);
            y = random.nextInt(72) + 40;

            (new WorldGenEndIsland()).generate(world, random, x, y, z);
        }

        for( int i = 0; i < 16 && EspConfiguration.genNiob; i++ ) {
            x = chunkX * 16 + random.nextInt(16);
            z = chunkZ * 16 + random.nextInt(16);
            y = random.nextInt(128);
            this.generateNiobium(world, random, x, y, z);
        }
    }

    public void populateSurfacePost(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkProvider) {
        int x, y, z;
        if( random.nextInt(10) == 0 && EspConfiguration.genAvisNest ) {
            x = chunkX * 16 + random.nextInt(16);
            z = chunkZ * 16 + random.nextInt(16);
            y = 64 + random.nextInt(64);

            (new WorldGenAvisNest()).generate(world, random, x, y, z);
        }

        if( random.nextInt(256) == 0 && EspConfiguration.genLeak ) {
            x = chunkX * 16 + random.nextInt(16);
            z = chunkZ * 16 + random.nextInt(16);
            y = world.getTopSolidOrLiquidBlock(x, z);

            (new WorldGenEndLeak()).generate(world, random, x, y, z);
        }
    }

    private boolean generateNiobium(World world, Random rand, int posX, int posY, int posZ) {
        if( world.getBlock(posX, posY, posZ) == Blocks.end_stone && !world.canBlockSeeTheSky(posX, posY, posZ) ) {
            world.setBlock(posX, posY, posZ, EspBlocks.enderOre, 0, 2);
            return true;
        }
        return false;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        int x, y, z;

        switch( world.provider.dimensionId ) {
            case 1:
                if( random.nextInt(64) == 0 && EspConfiguration.genEndlessEnd && (Math.abs(chunkX) > 10 || Math.abs(chunkZ) > 10) ) {
                    x = chunkX * 16 + random.nextInt(16);
                    z = chunkZ * 16 + random.nextInt(16);
                    y = random.nextInt(64) + 64;

                    (new WorldGenEndIsland()).generate(world, random, x, y, z);
                }
        }
    }
}
