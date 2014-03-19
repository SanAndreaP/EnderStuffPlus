package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import sanandreasp.core.manpack.helpers.CUS;
import sanandreasp.core.manpack.managers.SAPConfigManager;
import sanandreasp.core.manpack.managers.SAPUpdateManager;
import sanandreasp.core.manpack.mod.packet.PacketRegistry;
import sanandreasp.mods.ManagerPackHelper;
import sanandreasp.mods.EnderStuffPlus.enchantment.EnchantmentEnderChestTeleport;
import sanandreasp.mods.EnderStuffPlus.entity.EntityAvisArrow;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNemesis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNivis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderRay;
import sanandreasp.mods.EnderStuffPlus.entity.EntityRayball;
import sanandreasp.mods.EnderStuffPlus.entity.EntityWeatherAltarFirework;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityBait;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlMiss;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlNivis;
import sanandreasp.mods.EnderStuffPlus.item.ItemEnderPetEgg;
import com.google.common.collect.Maps;
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

@Mod(modid=ESPModRegistry.MOD_ID, name="EnderStuff+", version=ESPModRegistry.VERSION, dependencies="required-after:sapmanpack")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class ESPModRegistry
{
	public static final String MOD_ID = "EnderStuffPlus";
	public static final String PROXY_COMMON = "sanandreasp.mods.EnderStuffPlus.registry.CommonProxy";
	public static final String PROXY_CLIENT = "sanandreasp.mods.EnderStuffPlus.client.registry.ClientProxy";
	public static final String VERSION = "1.6.4-1.1.0";
	
	public static ConfigRegistry conf;
	
	@Instance(MOD_ID)
	public static ESPModRegistry instance;
	
	@SidedProxy(clientSide=ESPModRegistry.PROXY_CLIENT, serverSide=ESPModRegistry.PROXY_COMMON)
	public static CommonProxy proxy;
	
	public static Enchantment enderChestTel;

	public static HashMap<Integer, ItemStack> niobSet = Maps.newHashMap();

	public static CreativeTabs espTab, espTabCoats;
	
	public static ManagerPackHelper manHelper = new ManagerPackHelper();
	
	public static DamageSource endAcid;

	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		manHelper.checkManPack(evt.getModMetadata().name);
		
		if( !manHelper.loading ) return;
		
		manHelper.initMan(
				new SAPConfigManager("EnderStuffPlus", "EnderStuffPlus.txt", "/sanandreasp/"),
				new SAPUpdateManager("EnderStuffPlus", 1, 1, 0, "http://dl.dropbox.com/u/56920617/EnderStuffPMod_latest.txt", "http://www.minecraftforum.net/topic/936911-")
		);
		
		ConfigRegistry.setConfig(ESPModRegistry.manHelper);
		
		ESPModRegistry.espTab = new CreativeTabs("ESPTab") {
			@Override
			public ItemStack getIconItemStack() {
				return new ItemStack(BlockRegistry.biomeChanger);
			}
		};
		ESPModRegistry.espTabCoats = new CreativeTabs("ESPTabCoats") {
			@Override
			public ItemStack getIconItemStack() {
				return new ItemStack(ItemRegistry.rainCoat, 1, 16 | 32);
			}
		};
		
		BlockRegistry.init();
		ItemRegistry.init();
		
	// Enchantments
		ESPModRegistry.enderChestTel = new EnchantmentEnderChestTeleport(ConfigRegistry.enchID, 5);
		Enchantment.addToBookList(ESPModRegistry.enderChestTel);
		
	// Armor Set
		niobSet.put(0, new ItemStack(ItemRegistry.niobBoots));
		niobSet.put(1, new ItemStack(ItemRegistry.niobLegs));
		niobSet.put(2, new ItemStack(ItemRegistry.niobPlate));
		niobSet.put(3, new ItemStack(ItemRegistry.niobHelmet));
		
	// Misc registering
		proxy.registerHandlers();
		proxy.registerPackets();
		RegistryDungeonLoot.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent evt) {
		if( !manHelper.loading ) return;
		
		endAcid = CUS.getNewDmgSrc("enderstuffp:endAcid");

		RegistryDuplicator.registerFuel(new ItemStack(ItemRegistry.endIngot, 1, 0), 100);
		RegistryDuplicator.registerFuel(new ItemStack(Item.diamond), 160);
		RegistryDuplicator.registerFuel(new ItemStack(Item.ingotGold), 60);
		RegistryDuplicator.registerFuel(new ItemStack(Item.emerald), 40);
		RegistryDuplicator.registerFuel(new ItemStack(Item.ingotIron), 20);
		
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.cobblestoneMossy), 0);
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.stoneBrick, 1, 1), 0);
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.stoneBrick, 1, 2), 0);
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.stoneBrick, 1, 3), 0);
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.blockClay), 0);
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.sand), 0);
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.whiteStone), 2);
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.oreLapis), 5);
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.oreIron), 10);
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.oreRedstone), 15);
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.oreGold), 15);
		RegistryDuplicator.registerDupableItem(new ItemStack(BlockRegistry.endOre, 1, 0), 20);
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.oreEmerald), 25);
		RegistryDuplicator.registerDupableItem(new ItemStack(Block.oreDiamond), 30);
	
	// Entities
		short entityID = 0;
		
		proxy.registerEntity(EntityAvisArrow.class, "EnderAvisArrow", entityID++, this, 256, 1, true);		
		proxy.registerEntityWithEgg(EntityEnderNivis.class, "EnderNivis", entityID++, this, 128, 1, true, 0xFFFFFF, 0x66FFFF);
		proxy.registerEntityWithEgg(EntityEnderIgnis.class, "EnderIgnis", entityID++, this, 128, 1, true, 0xFF0000, 0xFFFF00);
		proxy.registerEntityWithEgg(EntityEnderRay.class, "EnderRay", entityID++, this, 128, 1, true, 0x222222, 0x8800AA);
		proxy.registerEntityWithEgg(EntityEnderMiss.class, "EnderMiss", entityID++, this, 128, 1, true, 0xffbbdd, 0x303030);
		proxy.registerEntityWithEgg(EntityEnderAvis.class, "EnderAvis", entityID++, this, 128, 1, true, 0x606060, 0xFF00FF);
		proxy.registerEntity(EntityRayball.class, "EnderRayBall", entityID++, this, 256, 1, true);
		proxy.registerEntity(EntityWeatherAltarFirework.class, "WAltarFirework", entityID++, this, 256, 1, true);
		proxy.registerEntityWithEgg(EntityEnderNemesis.class, "EnderNemesis", entityID++, this, 128, 1, true, 0x606060, 0x3A3AAE);
        proxy.registerEntity(EntityPearlNivis.class, "EnderNivisPearl", entityID++, this, 256, 1, true);
        proxy.registerEntity(EntityPearlIgnis.class, "EnderIgnisPearl", entityID++, this, 256, 1, true);
        proxy.registerEntity(EntityPearlMiss.class, "EnderMissPearl", entityID++, this, 256, 1, true);
        proxy.registerEntity(EntityBait.class, "EnderMissBait", entityID++, this, 256, 10, false);
		
		ItemEnderPetEgg.addPet(0, "EnderMiss", 0xffbbdd, 0x303030);
		ItemEnderPetEgg.addPet(1, "EnderAvis", 0x606060, 0xFF00FF);
	
	// Registry
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		
		OreDictionary.registerOre("ingotNiob", new ItemStack(ItemRegistry.endIngot));
		OreDictionary.registerOre("oreNiob", new ItemStack(BlockRegistry.endOre));
		OreDictionary.registerOre("blockNiob", new ItemStack(BlockRegistry.endBlock));
		OreDictionary.registerOre("logWood", new ItemStack(BlockRegistry.enderLog, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("plankWood", BlockRegistry.enderPlanks);
		OreDictionary.registerOre("treeSapling", new ItemStack(BlockRegistry.sapEndTree, 1, OreDictionary.WILDCARD_VALUE));
		
		MinecraftForge.setBlockHarvestLevel(BlockRegistry.endOre, 0, "pickaxe", 2);
		
		proxy.registerClientStuff();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		if( !ESPModRegistry.manHelper.loading ) return;
		
	// Craftings & Smeltings
		FurnaceRecipes.smelting().addSmelting(BlockRegistry.endOre.blockID, 0, new ItemStack(ItemRegistry.endIngot, 1, 0), 0.85F);
	
		CraftingRegistry.initCraftings();
		
	// Spawnings
		EntityRegistryESP.init();
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
	
	public static void sendPacketSrv(String name, Object...data) {
		PacketRegistry.sendPacketToServer(MOD_ID, name, data);
	}
	
	public static void sendPacketAllRng(String name, double x, double y, double z, double rng, int dimID, Object...data) {
		PacketRegistry.sendPacketToAllAround(MOD_ID, name, x, y, z, rng, dimID, data);
	}
	
	public static void sendPacketAllPlyr(String name, Object...data) {
		PacketRegistry.sendPacketToAllPlayers(MOD_ID, name, data);
	}
	
	public static void sendPacketPlyr(String name, EntityPlayer player, Object...data) {
		PacketRegistry.sendPacketToPlayer(MOD_ID, name, (Player)player, data);
	}
}
