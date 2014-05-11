package de.sanandrew.mods.enderstuffplus.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import cpw.mods.fml.common.registry.GameRegistry;

import de.sanandrew.core.manpack.util.ItemBlockNamedMeta;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.enderstuffplus.block.BlockAvisEgg;
import de.sanandrew.mods.enderstuffplus.block.BlockBiomeChanger;
import de.sanandrew.mods.enderstuffplus.block.BlockCorruptEndStone;
import de.sanandrew.mods.enderstuffplus.block.BlockDuplicator;
import de.sanandrew.mods.enderstuffplus.block.BlockEndFluid;
import de.sanandrew.mods.enderstuffplus.block.BlockEndLeaves;
import de.sanandrew.mods.enderstuffplus.block.BlockEndLog;
import de.sanandrew.mods.enderstuffplus.block.BlockEndOre;
import de.sanandrew.mods.enderstuffplus.block.BlockEndStorage;
import de.sanandrew.mods.enderstuffplus.block.BlockEnderDoor;
import de.sanandrew.mods.enderstuffplus.block.BlockEnderWood;
import de.sanandrew.mods.enderstuffplus.block.BlockSaplingEndTree;
import de.sanandrew.mods.enderstuffplus.block.BlockWeatherAltar;
import de.sanandrew.mods.enderstuffplus.item.ItemEndLeaves;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityAvisEgg;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityBiomeChanger;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityDuplicator;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityWeatherAltar;

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

    public static final void initialize() {
        initRegisterFluids();
        initBlocks();
        registerBlocks();

        Blocks.fire.setFireInfo(enderLeaves, 30, 60);
        Blocks.fire.setFireInfo(enderLog, 5, 5);
    }

    private static final void initBlocks() {
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

        avisEgg     .setBlockName("esp:avisEgg").setCreativeTab(ESPModRegistry.espTab).setHardness(1F).setBlockTextureName("enderstuffp:eggAvis");
        endOre      .setBlockName("esp:oreNiob").setCreativeTab(ESPModRegistry.espTab).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone);
        endBlock    .setBlockName("esp:blockNiob").setCreativeTab(ESPModRegistry.espTab).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);
        biomeChanger.setBlockName("esp:biomeChanger").setCreativeTab(ESPModRegistry.espTab).setHardness(1F);
        duplicator  .setBlockName("esp:duplicator").setCreativeTab(ESPModRegistry.espTab).setHardness(1F);
        weatherAltar.setBlockName("esp:weatherAltar").setCreativeTab(ESPModRegistry.espTab).setHardness(1F);
        blockEndDoor.disableStats().setBlockName("esp:enderDoor").setHardness(5.0F).setStepSound(Block.soundTypeMetal);
        enderLeaves .setBlockName("esp:enderLeaves").setCreativeTab(ESPModRegistry.espTab).setHardness(0.2F).setStepSound(Block.soundTypeGrass).setLightOpacity(1);
        enderLog    .setBlockName("esp:enderLog").setCreativeTab(ESPModRegistry.espTab).setHardness(2.0F).setStepSound(Block.soundTypeWood).setLightLevel(4.1F / 15F);
        sapEndTree  .setBlockName("esp:enderSapling").setCreativeTab(ESPModRegistry.espTab).setHardness(0.0F).setStepSound(Block.soundTypeGrass).setBlockTextureName("enderstuffp:sapling_end");
        enderPlanks .setBlockName("esp:enderWood").setCreativeTab(ESPModRegistry.espTab).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockTextureName("enderstuffp:enderWood" + (!ConfigRegistry.useAnimations ? "_NA" : ""));
        corruptES   .setBlockName("esp:corruptES").setCreativeTab(ESPModRegistry.espTab).setHardness(3.0F).setResistance(15.0F).setStepSound(Block.soundTypePiston);

        endFluid.setBlock(endFluidBlock);
    }

    private static final void initRegisterFluids() {
        endFluid = new Fluid("enderstuffp:endfluid");
        endFluid.setDensity(-500).setTemperature(150).setLuminosity(8).setViscosity(500).setRarity(EnumRarity.uncommon).setGaseous(true);

        FluidRegistry.registerFluid(endFluid);
    }

    private static final void registerBlocks() {
        GameRegistry.registerTileEntity(TileEntityAvisEgg.class, "avisEggTE");
        GameRegistry.registerTileEntity(TileEntityBiomeChanger.class, "biomeChangerTE");
        GameRegistry.registerTileEntity(TileEntityDuplicator.class, "duplicatorTE");
        GameRegistry.registerTileEntity(TileEntityWeatherAltar.class, "weatherAltarTE");

        SAPUtils.registerBlocks("enderstuffp:block",
                                avisEgg, biomeChanger, duplicator, weatherAltar, blockEndDoor, enderLog, sapEndTree, enderPlanks,
                                corruptES, endFluidBlock);

        GameRegistry.registerBlock(enderLeaves, ItemEndLeaves.class, "enderstuffp:blockEndLeaves");
        GameRegistry.registerBlock(endOre, ItemBlockNamedMeta.class, "enderstuffp:blockEndOre");
        GameRegistry.registerBlock(endBlock, ItemBlockNamedMeta.class, "enderstuffp:blockEndStorg");

        endOre.setHarvestLevel("pickaxe", 2, 0);
    }
}
