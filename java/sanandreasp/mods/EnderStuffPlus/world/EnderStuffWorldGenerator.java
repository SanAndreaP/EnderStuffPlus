package sanandreasp.mods.EnderStuffPlus.world;
import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ModBlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class EnderStuffWorldGenerator {

    @ForgeSubscribe
    public void onPopulateChunkPost(PopulateChunkEvent.Post event) {
        if( !event.world.getWorldInfo().isMapFeaturesEnabled() ) {
            return;
        }

        switch( event.world.provider.dimensionId ) {
            case 0:
                this.populateSurfacePost(event.rand, event.chunkX, event.chunkZ, event.world, event.chunkProvider);
                break;
        }
    }

    @ForgeSubscribe
    public void onPoopulateChunkPre(PopulateChunkEvent.Pre event) {
        switch( event.world.provider.dimensionId ) {
            case 1:
                this.populateEndPre(event.rand, event.chunkX, event.chunkZ, event.world, event.chunkProvider);
        }
    }

    public void populateEndPre(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkProvider) {
        int x, y, z;

        if( random.nextInt(128) == 0 && ConfigRegistry.genEndlessEnd && (Math.abs(chunkX) > 10 || Math.abs(chunkZ) > 10) ) {
            x = chunkX * 16 + random.nextInt(16);
            z = chunkZ * 16 + random.nextInt(16);
            y = random.nextInt(64) + 64;

            (new WorldGenEndIsland()).generate(world, random, x, y, z);
        }

        for( int i = 0; i < 16 && ConfigRegistry.genNiob; i++ ) {
            x = chunkX * 16 + random.nextInt(16);
            z = chunkZ * 16 + random.nextInt(16);
            y = random.nextInt(128);
            if( this.generateOre(world, random, x, y, z) ) {
                ;
            }
        }
    }

    public void populateSurfacePost(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkProvider) {
        int x, y, z;
        if( random.nextInt(10) == 0 && ConfigRegistry.genAvisNest ) {
            x = chunkX * 16 + random.nextInt(16);
            z = chunkZ * 16 + random.nextInt(16);
            y = 64 + random.nextInt(64);

            (new WorldGenAvisNest()).generate(world, random, x, y, z);
        }

        if( random.nextInt(256) == 0 && ConfigRegistry.genLeak ) {
            x = chunkX * 16 + random.nextInt(16);
            z = chunkZ * 16 + random.nextInt(16);
            y = world.getTopSolidOrLiquidBlock(x, z);

            (new WorldGenEndLeak()).generate(world, random, x, y, z);
        }
    }

    private boolean generateOre(World world, Random rand, int posX, int posY, int posZ) {
        if( world.getBlockId(posX, posY, posZ) == Block.whiteStone.blockID && !world.canBlockSeeTheSky(posX, posY, posZ) ) {
            world.setBlock(posX, posY, posZ, ModBlockRegistry.endOre.blockID);
            return true;
        }
        return false;
    }
}
