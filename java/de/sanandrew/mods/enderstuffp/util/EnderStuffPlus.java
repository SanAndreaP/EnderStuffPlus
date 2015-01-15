/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import de.sanandrew.core.manpack.managers.SAPUpdateManager;
import de.sanandrew.core.manpack.util.modcompatibility.ModInitHelperInst;
import de.sanandrew.mods.enderstuffp.enchantment.EnchantmentEnderChestTeleport;
import de.sanandrew.mods.enderstuffp.util.manager.IslandManager;
import de.sanandrew.mods.enderstuffp.util.manager.OreGeneratorManager;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Map;

@Mod(modid = EnderStuffPlus.MOD_ID, version = EnderStuffPlus.VERSION, name = "EnderStuff+")
public class EnderStuffPlus
{
    public static final String MOD_ID = "enderstuffp";
    public static final String VERSION = "2.0.0";
    public static final String MOD_LOG = "EnderStuffP";
    public static final String MOD_CHANNEL = "EnderStuffPNWCH";

    private static final String MOD_PROXY_CLIENT = "de.sanandrew.mods.enderstuffp.client.util.ClientProxy";
    private static final String MOD_PROXY_COMMON = "de.sanandrew.mods.enderstuffp.util.CommonProxy";

    private static final String THERMAL_EXP_HELPER_CLS = "de.sanandrew.mods.enderstuffp.util.modcompat.thermalexpansion.ThermalExpansionInitHelper";
    private static final String TCONSTRUCT_HELPER_CLS = "de.sanandrew.mods.enderstuffp.util.modcompat.tconstruct.TinkersConstructInitHelper";

    private ModInitHelperInst thermalExpInitHelper;
    private ModInitHelperInst tConstructInitHelper;

    @Instance(EnderStuffPlus.MOD_ID)
    public static EnderStuffPlus instance;
    @SidedProxy(modId = EnderStuffPlus.MOD_ID, clientSide = EnderStuffPlus.MOD_PROXY_CLIENT, serverSide = EnderStuffPlus.MOD_PROXY_COMMON)
    public static CommonProxy proxy;
    public static FMLEventChannel channel;

    public static Enchantment enderChestTel;

    public static Map<Integer, ItemStack> niobSet = Maps.newHashMap();

    public static BiomeGenBase surfaceEnd;

    public static DamageSource endAcid;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        SAPUpdateManager.createUpdateManager(MOD_ID, 2, 0, 0, "https://raw.githubusercontent.com/SanAndreasP/EnderStuffPlus/master/update.json",
                                             "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/1286957", event.getSourceFile());

        this.thermalExpInitHelper = ModInitHelperInst.loadWhenModAvailable("ThermalExpansion", THERMAL_EXP_HELPER_CLS);
        this.tConstructInitHelper = ModInitHelperInst.loadWhenModAvailable("TConstruct", TCONSTRUCT_HELPER_CLS);
//        ConfigRegistry.setConfig(event.getModConfigurationDirectory());
//        ESPModRegistry.espTabCoats = new CreativeTabs("ESPTabCoats") {
//            @Override
//            public Item getTabIconItem() {
//                return ModItemRegistry.rainCoat;
//            }
//        };
//
//        ModBlockRegistry.initialize();
        EspBlocks.initialize();
        EspItems.initialize();
//        ModEntityRegistry.initialize();
//
//        espBiome = new BiomeGenSurfaceEnd(125);

////        WorldType.DEFAULT.addNewBiome(espBiome);
////        WorldType.LARGE_BIOMES.addNewBiome(espBiome);
//
        enderChestTel = new EnchantmentEnderChestTeleport(/*ConfigRegistry.enchID*/128, 5);
        Enchantment.addToBookList(enderChestTel);
//
        niobSet.put(0, new ItemStack(EspItems.niobBoots));
        niobSet.put(1, new ItemStack(EspItems.niobLegs));
        niobSet.put(2, new ItemStack(EspItems.niobPlate));
        niobSet.put(3, new ItemStack(EspItems.niobHelmet));
//
//        proxy.registerHandlers();
//
//        channelHandler.registerPacket(PacketBCGUIAction.class);
//        channelHandler.registerPacket(PacketChangeBCGUI.class);
//        channelHandler.registerPacket(PacketChangeBiome.class);
//        channelHandler.registerPacket(PacketDupeInsertLevels.class);
//        channelHandler.registerPacket(PacketFXRayball.class);
//        channelHandler.registerPacket(PacketFXCstPortal.class);
//        channelHandler.registerPacket(PacketSetWeather.class);
//
//        RegistryDungeonLoot.initialize();
//        RegistryDuplicator.initialize();
//        RegistryBiomeChanger.initialize();
//
        endAcid = new DamageSource("enderstuffp:endAcid");
//
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
//
//        OreDictionary.registerOre("ingotNiob", new ItemStack(RegistryItems.enderIngot));
//        OreDictionary.registerOre("oreNiob", new ItemStack(ModBlockRegistry.enderOre));
//        OreDictionary.registerOre("blockNiob", new ItemStack(ModBlockRegistry.enderBlock));
//        OreDictionary.registerOre("logWood", new ItemStack(ModBlockRegistry.enderLog, 1, OreDictionary.WILDCARD_VALUE));
//        OreDictionary.registerOre("plankWood", ModBlockRegistry.enderPlanks);
//        OreDictionary.registerOre("treeSapling", new ItemStack(ModBlockRegistry.sapEndTree, 1, OreDictionary.WILDCARD_VALUE));
//
//        proxy.registerClientStuff();
        proxy.preInit(event);

        RaincoatManager.initialize();
        OreGeneratorManager.initialize();
        IslandManager.initialize();

        this.thermalExpInitHelper.preInitialize();
        this.tConstructInitHelper.preInitialize();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(MOD_CHANNEL);
        proxy.init(event);
//        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlockRegistry.enderOre, 1, 0),
//                                                new ItemStack(ModItemRegistry.enderIngot, 1, 0), 0.85F);
//        CraftingRegistry.initialize();
        GameRegistry.addRecipe(new ItemStack(Items.stone_sword, 1), "XX", 'X', Blocks.sand);

        this.thermalExpInitHelper.initialize();
        this.tConstructInitHelper.initialize();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        this.thermalExpInitHelper.postInitialize();
        this.tConstructInitHelper.postInitialize();
    }

    public static boolean hasPlayerFullNiob(EntityPlayer player) {
        boolean b = true;

        for( int i = 0; i < 4; i++ ) {
            if( player.getCurrentArmor(i) == null || (player.getCurrentArmor(i).getItem() != niobSet.get(i).getItem()) ) {
                b = false;
                break;
            }
        }

        return b;
    }
}
