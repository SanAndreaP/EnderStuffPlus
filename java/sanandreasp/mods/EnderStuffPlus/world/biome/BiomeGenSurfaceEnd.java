package sanandreasp.mods.EnderStuffPlus.world.biome;

import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.world.WorldGenEndTree;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenSurfaceEnd
    extends BiomeGenBase
{
    private WorldGenEndTree worldGenEndTree;

    public BiomeGenSurfaceEnd(int par1) {
        super(par1);
        this.topBlock = (byte) Block.grass.blockID;
        this.fillerBlock = (byte) Block.dirt.blockID;

        this.theBiomeDecorator.grassPerChunk = 0;
        this.theBiomeDecorator.treesPerChunk = 20;
        this.theBiomeDecorator.flowersPerChunk = 20;

        this.minHeight = -2.0F;
        this.maxHeight = 10.0F;

        this.biomeName = "Surface End";
        this.color = 0x33FF33;

        this.rainfall = 10.0F;

        this.setDisableRain();
        this.setTemperatureRainfall(2.0F, 0.0F);

        this.worldGenEndTree = new WorldGenEndTree(false);
    }

    @Override
    public WorldGenerator getRandomWorldGenForTrees(Random par1Random) {
        return par1Random.nextInt(10) == 0 ? this.worldGeneratorBigTree : this.worldGenEndTree;
    }

    @Override
    public int getSkyColorByTemp(float temp) {
        return 0xFFFFFF;
    }

    @Override
    public int getBiomeFoliageColor() {
        return 0x800080;
    }

    @Override
    public int getBiomeGrassColor() {
        return 0xFFFFC0;
    }

}
