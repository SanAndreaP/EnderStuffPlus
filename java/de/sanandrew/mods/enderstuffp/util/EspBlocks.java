package de.sanandrew.mods.enderstuffp.util;

import cpw.mods.fml.common.registry.GameRegistry;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.block.*;
import de.sanandrew.mods.enderstuffp.item.block.ItemBlockBiomeDataCrystal;
import de.sanandrew.mods.enderstuffp.item.block.ItemBlockEnderOre;
import de.sanandrew.mods.enderstuffp.item.block.ItemBlockEnderStorage;
import de.sanandrew.mods.enderstuffp.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public final class EspBlocks
{
    public static Block avisEgg;

    public static Block biomeChanger;
    public static Block corruptES;
    public static Block duplicator;
    public static Block enderBlock;
    public static Block enderLeaves;
    public static Block enderLog;
    public static Block enderPlanks;
    public static Fluid endFluid;
    public static Block endFluidBlock;
    public static Block enderOre;
    public static Block weatherAltar;
    public static BlockEnderDoor blockEndDoor;
    public static BlockSaplingEndTree sapEndTree;
    public static Block oreGenerator;
    public static Block biomeDataCrystal;
    public static Block fertilizer;


    public static void initialize() {
        initRegisterFluids();
        initBlocks();
        registerBlocks();

//        Blocks.fire.setFireInfo(enderLeaves, 30, 60);
//        Blocks.fire.setFireInfo(enderLog, 5, 5);
    }

    private static void initBlocks() {
        avisEgg = new BlockAvisEgg();
        enderOre = new BlockEnderOre();
        enderBlock = new BlockEnderStorage();
        biomeChanger = new BlockBiomeChanger(Material.rock);
//        duplicator    = new BlockDuplicator();
        weatherAltar = new BlockWeatherAltar();
        blockEndDoor = new BlockEnderDoor(Material.iron);
        enderLeaves   = new BlockEndLeaves();
        enderLog      = new BlockEndLog();
        sapEndTree    = new BlockSaplingEndTree();
        enderPlanks   = new BlockEnderWood();
//        corruptES     = new BlockCorruptEndStone();
//        endFluidBlock = new BlockEndFluid(endFluid, Material.water);
        oreGenerator = new BlockOreGenerator();
        biomeDataCrystal = new BlockBiomeDataCrystal();
        fertilizer = new BlockFertilizer();

        avisEgg.setBlockName(EnderStuffPlus.MOD_ID + ":avisEgg")
               .setCreativeTab(EspCreativeTabs.ESP_TAB)
               .setHardness(1.0F)
               .setBlockTextureName(EnderStuffPlus.MOD_ID + ":eggAvis");
        enderOre.setBlockName(EnderStuffPlus.MOD_ID + ":oreNiob")
              .setCreativeTab(EspCreativeTabs.ESP_TAB)
              .setHardness(3.0F)
              .setResistance(5.0F)
              .setStepSound(Block.soundTypeStone);
        enderBlock.setBlockName(EnderStuffPlus.MOD_ID + ":blockNiob")
                .setCreativeTab(EspCreativeTabs.ESP_TAB)
                .setHardness(5.0F)
                .setResistance(10.0F)
                .setStepSound(Block.soundTypeMetal);
        biomeChanger.setBlockName(EnderStuffPlus.MOD_ID + ":biomeChanger")
                    .setCreativeTab(EspCreativeTabs.ESP_TAB)
                    .setHardness(1.0F);
//        duplicator.setBlockName(ESPModRegistry.MOD_ID + ":duplicator")
//                  .setCreativeTab(ESPModRegistry.espTab)
//                  .setHardness(1.0F);
        weatherAltar.setBlockName(EnderStuffPlus.MOD_ID + ":weatherAltar")
                    .setCreativeTab(EspCreativeTabs.ESP_TAB)
                    .setHardness(1.0F);
        blockEndDoor.disableStats()
                    .setBlockName(EnderStuffPlus.MOD_ID + ":enderDoor")
                    .setHardness(5.0F)
                    .setStepSound(Block.soundTypeMetal);
        enderLeaves.setBlockName(EnderStuffPlus.MOD_ID + ":enderLeaves")
                   .setCreativeTab(EspCreativeTabs.ESP_TAB)
                   .setHardness(0.2F)
                   .setStepSound(Block.soundTypeGrass)
                   .setLightOpacity(1);
        enderLog.setBlockName(EnderStuffPlus.MOD_ID + ":enderLog")
                .setCreativeTab(EspCreativeTabs.ESP_TAB)
                .setHardness(2.0F)
                .setStepSound(Block.soundTypeWood)
                .setLightLevel(4.1F / 15.0F);
        sapEndTree.setBlockName(EnderStuffPlus.MOD_ID + ":enderSapling")
                  .setCreativeTab(EspCreativeTabs.ESP_TAB)
                  .setHardness(0.0F)
                  .setStepSound(Block.soundTypeGrass)
                  .setBlockTextureName(EnderStuffPlus.MOD_ID + ":sapling_end");
        enderPlanks.setBlockName(EnderStuffPlus.MOD_ID + ":enderWood")
                   .setCreativeTab(EspCreativeTabs.ESP_TAB)
                   .setHardness(2.0F)
                   .setResistance(5.0F)
                   .setStepSound(Block.soundTypeWood)
                   .setBlockTextureName(EnderStuffPlus.MOD_ID + ":enderWood");
//        corruptES.setBlockName(ESPModRegistry.MOD_ID + ":corruptES")
//                 .setCreativeTab(ESPModRegistry.espTab)
//                 .setHardness(3.0F)
//                 .setResistance(15.0F)
//                 .setStepSound(Block.soundTypePiston);
        oreGenerator.setBlockName(EnderStuffPlus.MOD_ID + ":oreGenerator")
                    .setCreativeTab(EspCreativeTabs.ESP_TAB)
                    .setHardness(1.0F)
                    .setStepSound(Block.soundTypeMetal);
        biomeDataCrystal.setBlockName(EnderStuffPlus.MOD_ID + ":biomeDataCrystal")
                        .setBlockTextureName(EnderStuffPlus.MOD_ID + ":biome_crystal")
                        .setCreativeTab(EspCreativeTabs.ESP_TAB_BIOMEDC)
                        .setHardness(1.0F)
                        .setStepSound(Block.soundTypeMetal);
        fertilizer.setBlockName(EnderStuffPlus.MOD_ID + ":fertilizer")
                    .setCreativeTab(EspCreativeTabs.ESP_TAB)
                    .setHardness(1.0F);
//
//        endFluid.setBlock(endFluidBlock);
    }

    private static void initRegisterFluids() {
//        endFluid = new Fluid(ESPModRegistry.MOD_ID + ":endfluid");
//        endFluid.setDensity(-500).setTemperature(150).setLuminosity(8).setViscosity(500).setRarity(EnumRarity.uncommon).setGaseous(true);
//
//        FluidRegistry.registerFluid(endFluid);
    }

    private static void registerBlocks() {
        GameRegistry.registerTileEntity(TileEntityAvisEgg.class, EnderStuffPlus.MOD_ID + ":avisEggTile");
        GameRegistry.registerTileEntity(TileEntityBiomeChanger.class, EnderStuffPlus.MOD_ID + ":biomeChangerTile");
//        GameRegistry.registerTileEntity(TileEntityDuplicator.class, "duplicatorTE");
        GameRegistry.registerTileEntity(TileEntityWeatherAltar.class, EnderStuffPlus.MOD_ID + ":weatherAltarTile");
        GameRegistry.registerTileEntity(TileEntityOreGenerator.class, EnderStuffPlus.MOD_ID + ":oreGeneratorTile");
        GameRegistry.registerTileEntity(TileEntityBiomeDataCrystal.class, EnderStuffPlus.MOD_ID + ":biomeDataCrystalTile");
        GameRegistry.registerTileEntity(TileEntityFertilizer.class, EnderStuffPlus.MOD_ID + ":fertilizer");

//        SAPUtils.registerBlocks(avisEgg, biomeChanger, duplicator, weatherAltar, blockEndDoor, enderLog, sapEndTree, enderPlanks,
//                                corruptES, endFluidBlock);

        SAPUtils.registerBlocks(avisEgg, biomeChanger, weatherAltar, blockEndDoor, oreGenerator, fertilizer, enderLog, sapEndTree, enderPlanks, enderLeaves);

//        GameRegistry.registerBlock(enderLeaves, ItemEndLeaves.class, ESPModRegistry.MOD_ID + ":blockEndLeaves");
        GameRegistry.registerBlock(biomeDataCrystal, ItemBlockBiomeDataCrystal.class, "biomeDataCrystal");
        GameRegistry.registerBlock(enderOre, ItemBlockEnderOre.class, "blockEnderOre");
        GameRegistry.registerBlock(enderBlock, ItemBlockEnderStorage.class, "blockEnderStorage");

        enderOre.setHarvestLevel("pickaxe", 2, 0);
    }
}
