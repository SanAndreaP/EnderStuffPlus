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
import de.sanandrew.mods.enderstuffp.enchantment.EnchantmentEnderChestTeleport;
import de.sanandrew.mods.enderstuffp.util.raincoat.RegistryRaincoats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

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

    @Instance(EnderStuffPlus.MOD_ID)
    public static EnderStuffPlus instance;
    @SidedProxy(modId = EnderStuffPlus.MOD_ID, clientSide = EnderStuffPlus.MOD_PROXY_CLIENT, serverSide = EnderStuffPlus.MOD_PROXY_COMMON)
    public static CommonProxy proxy;
    public static FMLEventChannel channel;

    public static Enchantment enderChestTel;

    public static HashMap<Integer, ItemStack> niobSet = Maps.newHashMap();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

//        ConfigRegistry.setConfig(event.getModConfigurationDirectory());
//        ESPModRegistry.espTabCoats = new CreativeTabs("ESPTabCoats") {
//            @Override
//            public Item getTabIconItem() {
//                return ModItemRegistry.rainCoat;
//            }
//        };
//
//        ModBlockRegistry.initialize();
        RegistryItems.initialize();
//        ModEntityRegistry.initialize();
//
//        espBiome = new BiomeGenSurfaceEnd(125);

////        WorldType.DEFAULT.addNewBiome(espBiome);
////        WorldType.LARGE_BIOMES.addNewBiome(espBiome);
//
        enderChestTel = new EnchantmentEnderChestTeleport(/*ConfigRegistry.enchID*/128, 5);
        Enchantment.addToBookList(enderChestTel);
//
        niobSet.put(0, new ItemStack(RegistryItems.niobBoots));
        niobSet.put(1, new ItemStack(RegistryItems.niobLegs));
        niobSet.put(2, new ItemStack(RegistryItems.niobPlate));
        niobSet.put(3, new ItemStack(RegistryItems.niobHelmet));
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
        OreDictionary.registerOre("ingotNiob", new ItemStack(RegistryItems.endIngot));
//        OreDictionary.registerOre("oreNiob", new ItemStack(ModBlockRegistry.endOre));
//        OreDictionary.registerOre("blockNiob", new ItemStack(ModBlockRegistry.endBlock));
//        OreDictionary.registerOre("logWood", new ItemStack(ModBlockRegistry.enderLog, 1, OreDictionary.WILDCARD_VALUE));
//        OreDictionary.registerOre("plankWood", ModBlockRegistry.enderPlanks);
//        OreDictionary.registerOre("treeSapling", new ItemStack(ModBlockRegistry.sapEndTree, 1, OreDictionary.WILDCARD_VALUE));
//
//        proxy.registerClientStuff();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(MOD_CHANNEL);
        proxy.init(event);
//        channelHandler.initialise();
//        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlockRegistry.endOre, 1, 0),
//                                                new ItemStack(ModItemRegistry.endIngot, 1, 0), 0.85F);
//        CraftingRegistry.initialize();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
//        channelHandler.postInitialise();
    }
}
