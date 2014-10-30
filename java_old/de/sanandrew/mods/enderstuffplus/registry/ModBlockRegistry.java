package de.sanandrew.mods.enderstuffplus.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import de.sanandrew.core.manpack.item.ItemBlockNamedMeta;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.enderstuffplus.block.*;
import de.sanandrew.mods.enderstuffplus.item.ItemEndLeaves;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityAvisEgg;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityBiomeChanger;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityDuplicator;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityWeatherAltar;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public final class ModBlockRegistry
{
    public static Block avisEgg;

    public static Block biomeChanger;
    public static Block corruptES;
    public static Block duplicator;
    public static Block endBlock;
    public static Block enderLeaves;
    public static Block enderLog;
    public static Block enderPlanks;
    public static Fluid endFluid;
    public static Block endFluidBlock;
    public static Block endOre;
    public static Block weatherAltar;
    public static BlockEnderDoor blockEndDoor;
    public static BlockSaplingEndTree sapEndTree;

    public static void initialize() {
        initRegisterFluids();
        initBlocks();
        registerBlocks();

        Blocks.fire.setFireInfo(enderLeaves, 30, 60);
        Blocks.fire.setFireInfo(enderLog, 5, 5);
    }

    private static void initBlocks() {
        avisEgg       = new BlockAvisEgg();
        endOre        = new BlockEndOre();
        endBlock      = new BlockEndStorage();
        biomeChanger  = new BlockBiomeChanger(Material.rock);
        duplicator    = new BlockDuplicator();
        weatherAltar  = new BlockWeatherAltar();
        blockEndDoor  = new BlockEnderDoor(Material.iron);
        enderLeaves   = new BlockEndLeaves();
        enderLog      = new BlockEndLog();
        sapEndTree    = new BlockSaplingEndTree();
        enderPlanks   = new BlockEnderWood();
        corruptES     = new BlockCorruptEndStone();
        endFluidBlock = new BlockEndFluid(endFluid, Material.water);

        avisEgg.setBlockName(ESPModRegistry.MOD_ID + ":avisEgg")
               .setCreativeTab(ESPModRegistry.espTab)
               .setHardness(1.0F)
               .setBlockTextureName("enderstuffp:eggAvis");
        endOre.setBlockName(ESPModRegistry.MOD_ID + ":oreNiob")
              .setCreativeTab(ESPModRegistry.espTab)
              .setHardness(3.0F)
              .setResistance(5.0F)
              .setStepSound(Block.soundTypeStone);
        endBlock.setBlockName(ESPModRegistry.MOD_ID + ":blockNiob")
                .setCreativeTab(ESPModRegistry.espTab)
                .setHardness(5.0F)
                .setResistance(10.0F)
                .setStepSound(Block.soundTypeMetal);
        biomeChanger.setBlockName(ESPModRegistry.MOD_ID + ":biomeChanger")
                    .setCreativeTab(ESPModRegistry.espTab)
                    .setHardness(1.0F);
        duplicator.setBlockName(ESPModRegistry.MOD_ID + ":duplicator")
                  .setCreativeTab(ESPModRegistry.espTab)
                  .setHardness(1.0F);
        weatherAltar.setBlockName(ESPModRegistry.MOD_ID + ":weatherAltar")
                    .setCreativeTab(ESPModRegistry.espTab)
                    .setHardness(1.0F);
        blockEndDoor.disableStats()
                    .setBlockName(ESPModRegistry.MOD_ID + ":enderDoor")
                    .setHardness(5.0F)
                    .setStepSound(Block.soundTypeMetal);
        enderLeaves.setBlockName(ESPModRegistry.MOD_ID + ":enderLeaves")
                   .setCreativeTab(ESPModRegistry.espTab)
                   .setHardness(0.2F)
                   .setStepSound(Block.soundTypeGrass)
                   .setLightOpacity(1);
        enderLog.setBlockName(ESPModRegistry.MOD_ID + ":enderLog")
                .setCreativeTab(ESPModRegistry.espTab)
                .setHardness(2.0F)
                .setStepSound(Block.soundTypeWood)
                .setLightLevel(4.1F / 15.0F);
        sapEndTree.setBlockName(ESPModRegistry.MOD_ID + ":enderSapling")
                  .setCreativeTab(ESPModRegistry.espTab)
                  .setHardness(0.0F)
                  .setStepSound(Block.soundTypeGrass)
                  .setBlockTextureName(ESPModRegistry.MOD_ID + ":sapling_end");
        enderPlanks.setBlockName(ESPModRegistry.MOD_ID + ":enderWood")
                   .setCreativeTab(ESPModRegistry.espTab)
                   .setHardness(2.0F)
                   .setResistance(5.0F)
                   .setStepSound(Block.soundTypeWood)
                   .setBlockTextureName(ESPModRegistry.MOD_ID + ":enderWood" + (!ConfigRegistry.useAnimations ? "_NA" : ""));
        corruptES.setBlockName(ESPModRegistry.MOD_ID + ":corruptES")
                 .setCreativeTab(ESPModRegistry.espTab)
                 .setHardness(3.0F)
                 .setResistance(15.0F)
                 .setStepSound(Block.soundTypePiston);

        endFluid.setBlock(endFluidBlock);
    }

    private static void initRegisterFluids() {
        endFluid = new Fluid(ESPModRegistry.MOD_ID + ":endfluid");
        endFluid.setDensity(-500).setTemperature(150).setLuminosity(8).setViscosity(500).setRarity(EnumRarity.uncommon).setGaseous(true);

        FluidRegistry.registerFluid(endFluid);
    }

    private static void registerBlocks() {
        GameRegistry.registerTileEntity(TileEntityAvisEgg.class, "avisEggTE");
        GameRegistry.registerTileEntity(TileEntityBiomeChanger.class, "biomeChangerTE");
        GameRegistry.registerTileEntity(TileEntityDuplicator.class, "duplicatorTE");
        GameRegistry.registerTileEntity(TileEntityWeatherAltar.class, "weatherAltarTE");

        SAPUtils.registerBlocks(avisEgg, biomeChanger, duplicator, weatherAltar, blockEndDoor, enderLog, sapEndTree, enderPlanks,
                                corruptES, endFluidBlock);

        GameRegistry.registerBlock(enderLeaves, ItemEndLeaves.class, ESPModRegistry.MOD_ID + ":blockEndLeaves");
        GameRegistry.registerBlock(endOre, ItemBlockNamedMeta.class, ESPModRegistry.MOD_ID + ":blockEndOre");
        GameRegistry.registerBlock(endBlock, ItemBlockNamedMeta.class, ESPModRegistry.MOD_ID + ":blockEndStorg");

        endOre.setHarvestLevel("pickaxe", 2, 0);
    }
}
