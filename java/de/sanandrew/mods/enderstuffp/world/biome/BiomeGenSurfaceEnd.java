package de.sanandrew.mods.enderstuffp.world.biome;

import de.sanandrew.mods.enderstuffp.world.WorldGenEndTree;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeGenSurfaceEnd
    extends BiomeGenBase
{
    private final WorldGenEndTree worldGenEndTree;

    public BiomeGenSurfaceEnd(int par1) {
        super(par1);
        this.topBlock = Blocks.grass;
        this.fillerBlock = Blocks.dirt;

        this.theBiomeDecorator.grassPerChunk = 0;
        this.theBiomeDecorator.treesPerChunk = 20;
        this.theBiomeDecorator.flowersPerChunk = 20;

//        this.minHeight = -1.0F;
//        this.maxHeight = 10.0F;
        this.setHeight(new Height(-2.0F, 1.0F));

        this.biomeName = "Surface End";
        this.color = 0x33FF33;

        this.rainfall = 10.0F;

        this.setDisableRain();
        this.setTemperatureRainfall(2.0F, 0.0F);

        this.worldGenEndTree = new WorldGenEndTree(false);
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random par1Random) {
        return par1Random.nextInt(10) == 0 ? this.worldGeneratorBigTree : this.worldGenEndTree;
    }

    @Override
    public int getSkyColorByTemp(float temp) {
        return 0xFFFFFF;
    }

    @Override
    public int getBiomeFoliageColor(int i, int j, int k) {
        return 0x800080;
    }

    @Override
    public int getBiomeGrassColor(int i, int j, int k) {
        return 0xFFFFC0;
    }

}
