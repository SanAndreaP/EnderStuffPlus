package sanandreasp.mods.EnderStuffPlus.registry;

import sanandreasp.core.manpack.helpers.ItemBlockNamedMeta;
import sanandreasp.core.manpack.helpers.SAPUtils;
import sanandreasp.mods.EnderStuffPlus.block.BlockAvisEgg;
import sanandreasp.mods.EnderStuffPlus.block.BlockBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.block.BlockCorruptEndStone;
import sanandreasp.mods.EnderStuffPlus.block.BlockDuplicator;
import sanandreasp.mods.EnderStuffPlus.block.BlockEndFluid;
import sanandreasp.mods.EnderStuffPlus.block.BlockEndLeaves;
import sanandreasp.mods.EnderStuffPlus.block.BlockEndLog;
import sanandreasp.mods.EnderStuffPlus.block.BlockEndOre;
import sanandreasp.mods.EnderStuffPlus.block.BlockEndStorage;
import sanandreasp.mods.EnderStuffPlus.block.BlockEnderDoor;
import sanandreasp.mods.EnderStuffPlus.block.BlockEnderWood;
import sanandreasp.mods.EnderStuffPlus.block.BlockSaplingEndTree;
import sanandreasp.mods.EnderStuffPlus.block.BlockWeatherAltar;
import sanandreasp.mods.EnderStuffPlus.item.ItemEndLeaves;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityAvisEgg;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import cpw.mods.fml.common.registry.GameRegistry;

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

        Block.setBurnProperties(enderLeaves.blockID, 30, 60);
        Block.setBurnProperties(enderLog.blockID, 5, 5);
    }

    private static final void initBlocks() {
        avisEgg       = new BlockAvisEgg(ConfigRegistry.blockIDs.get("Avis Egg").intValue());
        endOre        = new BlockEndOre(ConfigRegistry.blockIDs.get("Niobium Ore").intValue());
        endBlock      = new BlockEndStorage(ConfigRegistry.blockIDs.get("Niobium Block").intValue());
        biomeChanger  = new BlockBiomeChanger(ConfigRegistry.blockIDs.get("Biome Changer").intValue(), Material.rock);
        duplicator    = new BlockDuplicator(ConfigRegistry.blockIDs.get("Duplicator").intValue());
        weatherAltar  = new BlockWeatherAltar(ConfigRegistry.blockIDs.get("Weather Altar").intValue());
        blockEndDoor  = new BlockEnderDoor(ConfigRegistry.blockIDs.get("Ender Door").intValue(), Material.iron);
        enderLeaves   = new BlockEndLeaves(ConfigRegistry.blockIDs.get("Ender Leaves").intValue());
        enderLog      = new BlockEndLog(ConfigRegistry.blockIDs.get("Ender Log").intValue());
        sapEndTree    = new BlockSaplingEndTree(ConfigRegistry.blockIDs.get("Ender Sapling").intValue());
        enderPlanks   = new BlockEnderWood(ConfigRegistry.blockIDs.get("Ender Planks").intValue());
        corruptES     = new BlockCorruptEndStone(ConfigRegistry.blockIDs.get("Corrupt End Stone").intValue());
        endFluidBlock = new BlockEndFluid(ConfigRegistry.blockIDs.get("End Fluid"), endFluid, Material.water);

        avisEgg     .setUnlocalizedName("esp:avisEgg").setCreativeTab(ESPModRegistry.espTab).setHardness(1F);
        endOre      .setUnlocalizedName("esp:oreNiob").setCreativeTab(ESPModRegistry.espTab).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep);
        endBlock    .setUnlocalizedName("esp:blockNiob").setCreativeTab(ESPModRegistry.espTab).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep);
        biomeChanger.setUnlocalizedName("esp:biomeChanger").setCreativeTab(ESPModRegistry.espTab).setHardness(1F);
        duplicator  .setUnlocalizedName("esp:duplicator").setCreativeTab(ESPModRegistry.espTab).setHardness(1F);
        weatherAltar.setUnlocalizedName("esp:weatherAltar").setCreativeTab(ESPModRegistry.espTab).setHardness(1F);
        blockEndDoor.disableStats().setUnlocalizedName("esp:enderDoor").setHardness(5.0F).setStepSound(Block.soundMetalFootstep);
        enderLeaves .setUnlocalizedName("esp:enderLeaves").setCreativeTab(ESPModRegistry.espTab).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setLightOpacity(1);
        enderLog    .setUnlocalizedName("esp:enderLog").setCreativeTab(ESPModRegistry.espTab).setHardness(2.0F).setStepSound(Block.soundWoodFootstep).setLightValue(4.1F / 15F);
        sapEndTree  .setUnlocalizedName("esp:enderSapling").setCreativeTab(ESPModRegistry.espTab).setHardness(0.0F).setStepSound(Block.soundGrassFootstep);
        enderPlanks .setUnlocalizedName("esp:enderWood").setCreativeTab(ESPModRegistry.espTab).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep);
        corruptES   .setUnlocalizedName("esp:corruptES").setCreativeTab(ESPModRegistry.espTab).setHardness(Block.whiteStone.blockHardness).setResistance(Block.whiteStone.blockResistance).setStepSound(Block.soundStoneFootstep);
    }

    private static final void initRegisterFluids() {
        endFluid = new Fluid("enderstuffp:endfluid");
        endFluid.setBlockID(ConfigRegistry.blockIDs.get("End Fluid")).setDensity(-500).setTemperature(150).setLuminosity(8).setViscosity(500).setRarity(EnumRarity.uncommon).setGaseous(true);

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
    }
}
