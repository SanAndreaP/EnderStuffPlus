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
import de.sanandrew.core.manpack.util.SAPReflectionHelper;
import de.sanandrew.core.manpack.util.modcompatibility.ModInitHelperInst;
import de.sanandrew.mods.enderstuffp.enchantment.EnchantmentEnderChestTeleport;
import de.sanandrew.mods.enderstuffp.util.raincoat.RegistryRaincoats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

@Mod(modid = EnderStuffPlus.MOD_ID, version = EnderStuffPlus.VERSION)
public class EnderStuffPlus
{
    public static final String MOD_ID = "enderstuffp";
    public static final String VERSION = "2.0.0";
    public static final String MOD_LOG = "EnderStuffP";
    public static final String MOD_CHANNEL = "EnderStuffPNWCH";

    private static final String MOD_PROXY_CLIENT = "de.sanandrew.mods.enderstuffp.client.util.ClientProxy";
    private static final String MOD_PROXY_COMMON = "de.sanandrew.mods.enderstuffp.util.CommonProxy";

    private static final String THERMAL_EXP_HELPER_CLS = "de.sanandrew.mods.enderstuffp.util.modcompat.ThermalExpansionInitHelper";

    private ModInitHelperInst thermalExpInitHelper;

    @Instance(EnderStuffPlus.MOD_ID)
    public static EnderStuffPlus instance;
    @SidedProxy(modId = EnderStuffPlus.MOD_ID, clientSide = EnderStuffPlus.MOD_PROXY_CLIENT, serverSide = EnderStuffPlus.MOD_PROXY_COMMON)
    public static CommonProxy proxy;
    public static FMLEventChannel channel;

    public static Enchantment enderChestTel;

    public static HashMap<Integer, ItemStack> niobSet = Maps.newHashMap();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.thermalExpInitHelper = ModInitHelperInst.loadWhenModAvailable("ThermalExpansion", THERMAL_EXP_HELPER_CLS);
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
        RegistryRaincoats.initialize();
//        RegistryDuplicator.initialize();
//        RegistryBiomeChanger.initialize();
//
//        endAcid = SAPUtils.getNewDamageSource("enderstuffp:endAcid");
//
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
//
//        OreDictionary.registerOre("ingotNiob", new ItemStack(RegistryItems.endIngot));
//        OreDictionary.registerOre("oreNiob", new ItemStack(ModBlockRegistry.endOre));
//        OreDictionary.registerOre("blockNiob", new ItemStack(ModBlockRegistry.endBlock));
//        OreDictionary.registerOre("logWood", new ItemStack(ModBlockRegistry.enderLog, 1, OreDictionary.WILDCARD_VALUE));
//        OreDictionary.registerOre("plankWood", ModBlockRegistry.enderPlanks);
//        OreDictionary.registerOre("treeSapling", new ItemStack(ModBlockRegistry.sapEndTree, 1, OreDictionary.WILDCARD_VALUE));
//
//        proxy.registerClientStuff();
        proxy.preInit(event);

        this.thermalExpInitHelper.preInitialize();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(MOD_CHANNEL);
        proxy.init(event);
//        channelHandler.initialise();
//        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlockRegistry.endOre, 1, 0),
//                                                new ItemStack(ModItemRegistry.endIngot, 1, 0), 0.85F);
//        CraftingRegistry.initialize();
        GameRegistry.addRecipe(new ItemStack(Items.stone_sword, 1), "XX", 'X', Blocks.sand);

        this.thermalExpInitHelper.initialize();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
//        channelHandler.postInitialise();
        this.thermalExpInitHelper.postInitialize();
    }

    //TODO: check the MCP name for its correctness before release!
    public static boolean isJumping(EntityLivingBase livingBase) {
        return SAPReflectionHelper.getCachedFieldValue(EntityLivingBase.class, livingBase, "isJumping", "field_70703_bu");
    }
}
