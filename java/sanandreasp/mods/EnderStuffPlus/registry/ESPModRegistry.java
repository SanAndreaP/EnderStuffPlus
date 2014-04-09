package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.HashMap;

import sanandreasp.core.manpack.helpers.SAPUtils;
import sanandreasp.core.manpack.managers.SAPConfigManager;
import sanandreasp.core.manpack.managers.SAPUpdateManager;
import sanandreasp.core.manpack.mod.packet.PacketRegistry;
import sanandreasp.mods.ManagerPackHelper;
import sanandreasp.mods.EnderStuffPlus.enchantment.EnchantmentEnderChestTeleport;
import sanandreasp.mods.EnderStuffPlus.item.ItemEnderPetEgg;

import com.google.common.collect.Maps;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.DamageSource;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;

@Mod(modid = ESPModRegistry.MOD_ID, name = ESPModRegistry.MOD_NAME, version = ESPModRegistry.VERSION, dependencies = "required-after:sapmanpack")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ESPModRegistry
{

    public static final String MOD_ID = "enderstuffp";
    public static final String MOD_NAME = "EnderStuff+";
    public static final String PROXY_CLIENT = "sanandreasp.mods.EnderStuffPlus.client.registry.ClientProxy";
    public static final String PROXY_COMMON = "sanandreasp.mods.EnderStuffPlus.registry.CommonProxy";
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
    public static ManagerPackHelper manHelper = new ManagerPackHelper();
    public static HashMap<Integer, ItemStack> niobSet = Maps.newHashMap();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        manHelper.checkManPack(event.getModMetadata().name);

        if( !manHelper.loading ) {
            return;
        }

        manHelper.initMan(new SAPConfigManager("EnderStuffPlus", "EnderStuffPlus.txt", "/sanandreasp/"),
                          new SAPUpdateManager("EnderStuffPlus", 1, 1, 0,
                                               "http://dl.dropbox.com/u/56920617/EnderStuffPMod_latest.txt",
                                               "http://www.minecraftforum.net/topic/936911-"));

        ConfigRegistry.setConfig(ESPModRegistry.manHelper);

        ESPModRegistry.espTab = new CreativeTabs("ESPTab") {
            @Override
            public ItemStack getIconItemStack() {
                return new ItemStack(ModBlockRegistry.biomeChanger);
            }
        };
        ESPModRegistry.espTabCoats = new CreativeTabs("ESPTabCoats") {
            @Override
            public ItemStack getIconItemStack() {
                return new ItemStack(ModItemRegistry.rainCoat, 1, 16 | 32);
            }
        };

        ModBlockRegistry.initiate();
        ModItemRegistry.initiate();
        ModEntityRegistry.initiate();

        ESPModRegistry.enderChestTel = new EnchantmentEnderChestTeleport(ConfigRegistry.enchID, 5);
        Enchantment.addToBookList(ESPModRegistry.enderChestTel);

        niobSet.put(0, new ItemStack(ModItemRegistry.niobBoots));
        niobSet.put(1, new ItemStack(ModItemRegistry.niobLegs));
        niobSet.put(2, new ItemStack(ModItemRegistry.niobPlate));
        niobSet.put(3, new ItemStack(ModItemRegistry.niobHelmet));

        proxy.registerHandlers();
        proxy.registerPackets();
        RegistryDungeonLoot.init();

        endAcid = SAPUtils.getNewDmgSrc("enderstuffp:endAcid");

        ItemEnderPetEgg.addPet(0, "EnderMiss", 0xffbbdd, 0x303030);
        ItemEnderPetEgg.addPet(1, "EnderAvis", 0x606060, 0xFF00FF);

        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

        OreDictionary.registerOre("ingotNiob", new ItemStack(ModItemRegistry.endIngot));
        OreDictionary.registerOre("oreNiob", new ItemStack(ModBlockRegistry.endOre));
        OreDictionary.registerOre("blockNiob", new ItemStack(ModBlockRegistry.endBlock));
        OreDictionary.registerOre("logWood", new ItemStack(ModBlockRegistry.enderLog, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("plankWood", ModBlockRegistry.enderPlanks);
        OreDictionary.registerOre("treeSapling", new ItemStack(ModBlockRegistry.sapEndTree, 1, OreDictionary.WILDCARD_VALUE));

        MinecraftForge.setBlockHarvestLevel(ModBlockRegistry.endOre, 0, "pickaxe", 2);

        proxy.registerClientStuff();
    }

    @EventHandler
    public void init(FMLInitializationEvent evt) {
        if( !manHelper.loading ) {
            return;
        }

        FurnaceRecipes.smelting().addSmelting(ModBlockRegistry.endOre.blockID, 0, new ItemStack(ModItemRegistry.endIngot, 1, 0), 0.85F);
        CraftingRegistry.initCraftings();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        if( !ESPModRegistry.manHelper.loading ) {
            return;
        }
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

    public static void sendPacketAllPlyr(String name, Object... data) {
        PacketRegistry.sendPacketToAllPlayers(MOD_ID, name, data);
    }

    public static void sendPacketAllRng(String name, double x, double y, double z, double rng, int dimID, Object... data) {
        PacketRegistry.sendPacketToAllAround(MOD_ID, name, x, y, z, rng, dimID, data);
    }

    public static void sendPacketPlyr(String name, EntityPlayer player, Object... data) {
        PacketRegistry.sendPacketToPlayer(MOD_ID, name, (Player) player, data);
    }

    public static void sendPacketSrv(String name, Object... data) {
        PacketRegistry.sendPacketToServer(MOD_ID, name, data);
    }
}
