package de.sanandrew.mods.enderstuffplus.registry;

import java.util.HashMap;

import com.google.common.collect.Maps;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.BiomeGenBase;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

import de.sanandrew.core.manpack.managers.SAPUpdateManager;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.enderstuffplus.enchantment.EnchantmentEnderChestTeleport;
import de.sanandrew.mods.enderstuffplus.item.ItemEnderPetEgg;
import de.sanandrew.mods.enderstuffplus.registry.raincoat.RegistryRaincoats;
import de.sanandrew.mods.enderstuffplus.world.biome.BiomeGenSurfaceEnd;

@Mod(modid = ESPModRegistry.MOD_ID, name = ESPModRegistry.MOD_NAME, version = ESPModRegistry.VERSION, dependencies = "required-after:sapmanpack")
//@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ESPModRegistry
{

    public static final String MOD_ID = "enderstuffp";
    public static final String MOD_NAME = "EnderStuff+";
    public static final String PROXY_CLIENT = "de.sanandrew.mods.enderstuffplus.client.registry.ClientProxy";
    public static final String PROXY_COMMON = "de.sanandrew.mods.enderstuffplus.registry.CommonProxy";
    public static final String VERSION = "1.6.4-1.1.0";

    @Instance(MOD_ID)
    public static ESPModRegistry instance;
    @SidedProxy(clientSide = ESPModRegistry.PROXY_CLIENT, serverSide = ESPModRegistry.PROXY_COMMON)
    public static CommonProxy proxy;

    public static ConfigRegistry conf;
    public static DamageSource endAcid;
    public static Enchantment enderChestTel;
    public static CreativeTabs espTab;
    public static CreativeTabs espTabCoats;
    public static SAPUpdateManager updMan;
    public static HashMap<Integer, ItemStack> niobSet = Maps.newHashMap();
    public static BiomeGenBase espBiome;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        try {
            updMan =  new SAPUpdateManager("EnderStuffPlus", 1, 1, 0,
                                           "http://dl.dropbox.com/u/56920617/EnderStuffPMod_latest.txt",
                                           "http://www.minecraftforum.net/topic/936911-");
        } catch( NoClassDefFoundError ex ) {
            throw new NoManpackFoundException(ex);
        }

        ConfigRegistry.setConfig(event.getModConfigurationDirectory());

        ESPModRegistry.espTab = new CreativeTabs("ESPTab") {
//            @Override
//            public ItemStack getIconItemStack() {
//                return new ItemStack(ModBlockRegistry.biomeChanger);
//            }

            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(ModBlockRegistry.biomeChanger);
            }
        };
        ESPModRegistry.espTabCoats = new CreativeTabs("ESPTabCoats") {
//            @Override
//            public ItemStack getIconItemStack() {
//                return new ItemStack(ModItemRegistry.rainCoat, 1, 16 | 32);
//            }

            @Override
            public Item getTabIconItem() {
                return ModItemRegistry.rainCoat;
            }
        };

        ModBlockRegistry.initialize();
        ModItemRegistry.initialize();
//        ModEntityRegistry.initialize();

        espBiome = new BiomeGenSurfaceEnd(125);
        //TODO find a reasonable way to register biomes
//        WorldType.DEFAULT.addNewBiome(espBiome);
//        WorldType.LARGE_BIOMES.addNewBiome(espBiome);

        ESPModRegistry.enderChestTel = new EnchantmentEnderChestTeleport(ConfigRegistry.enchID, 5);
        Enchantment.addToBookList(ESPModRegistry.enderChestTel);

        niobSet.put(0, new ItemStack(ModItemRegistry.niobBoots));
        niobSet.put(1, new ItemStack(ModItemRegistry.niobLegs));
        niobSet.put(2, new ItemStack(ModItemRegistry.niobPlate));
        niobSet.put(3, new ItemStack(ModItemRegistry.niobHelmet));

        proxy.registerHandlers();
        proxy.registerPackets();
        RegistryDungeonLoot.initialize();
        RegistryRaincoats.initialize();
        RegistryDuplicator.initialize();

        endAcid = SAPUtils.getNewDamageSource("enderstuffp:endAcid");

        ItemEnderPetEgg.addPet(0, "EnderMiss", 0xffbbdd, 0x303030);
        ItemEnderPetEgg.addPet(1, "EnderAvis", 0x606060, 0xFF00FF);

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        OreDictionary.registerOre("ingotNiob", new ItemStack(ModItemRegistry.endIngot));
        OreDictionary.registerOre("oreNiob", new ItemStack(ModBlockRegistry.endOre));
        OreDictionary.registerOre("blockNiob", new ItemStack(ModBlockRegistry.endBlock));
        OreDictionary.registerOre("logWood", new ItemStack(ModBlockRegistry.enderLog, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("plankWood", ModBlockRegistry.enderPlanks);
        OreDictionary.registerOre("treeSapling", new ItemStack(ModBlockRegistry.sapEndTree, 1, OreDictionary.WILDCARD_VALUE));

        proxy.registerClientStuff();

    }

    @EventHandler
    public void init(FMLInitializationEvent evt) {
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlockRegistry.endOre, 1, 0),
                                                new ItemStack(ModItemRegistry.endIngot, 1, 0), 0.85F);
        CraftingRegistry.initialize();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent evt) {

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

    public static boolean isJumping(EntityPlayer thePlayer) {
        return ObfuscationReflectionHelper.getPrivateValue(EntityLivingBase.class, thePlayer, "isJumping", "field_70703_bu");
    }

    public static boolean isShiftPressed(EntityPlayer ep) {
        return ep != null ? ep.isSneaking() : false;
    }

    public static boolean isSprintActivated(EntityPlayer ep) {
        return ep != null ? ep.isSprinting() : false;
    }

    //TODO use new packet system!
    public static void sendPacketAllPlyr(String name, Object... data) {
//        PacketRegistry.sendPacketToAllPlayers(MOD_ID, name, data);
    }

    public static void sendPacketAllRng(String name, double x, double y, double z, double rng, int dimID, Object... data) {
//        PacketRegistry.sendPacketToAllAround(MOD_ID, name, x, y, z, rng, dimID, data);
    }

    public static void sendPacketPlyr(String name, EntityPlayer player, Object... data) {
//        PacketRegistry.sendPacketToPlayer(MOD_ID, name, (Player) player, data);
    }

    public static void sendPacketSrv(String name, Object... data) {
//        PacketRegistry.sendPacketToServer(MOD_ID, name, data);
    }

    private static class NoManpackFoundException
        extends RuntimeException
    {
        private static final long serialVersionUID = -8341920921113749378L;

        public NoManpackFoundException(Throwable throwable) {
            super(throwable);
        }
    }
}
