package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import sanandreasp.core.manpack.helpers.ItemBlockNamedMeta;
import sanandreasp.core.manpack.managers.SAPConfigManager;
import sanandreasp.core.manpack.managers.SAPLanguageManager;
import sanandreasp.core.manpack.managers.SAPUpdateManager;
import sanandreasp.mods.ManagerPackHelper;
import sanandreasp.mods.EnderStuffPlus.block.BlockAvisEgg;
import sanandreasp.mods.EnderStuffPlus.block.BlockBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.block.BlockDuplicator;
import sanandreasp.mods.EnderStuffPlus.block.BlockEndLeaves;
import sanandreasp.mods.EnderStuffPlus.block.BlockEndLog;
import sanandreasp.mods.EnderStuffPlus.block.BlockEndOre;
import sanandreasp.mods.EnderStuffPlus.block.BlockEndStorage;
import sanandreasp.mods.EnderStuffPlus.block.BlockEnderDoor;
import sanandreasp.mods.EnderStuffPlus.block.BlockEnderWood;
import sanandreasp.mods.EnderStuffPlus.block.BlockSaplingEndTree;
import sanandreasp.mods.EnderStuffPlus.block.BlockWeatherAltar;
import sanandreasp.mods.EnderStuffPlus.client.packet.PacketHandlerClient;
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
import sanandreasp.mods.EnderStuffPlus.item.ItemAvisCompass;
import sanandreasp.mods.EnderStuffPlus.item.ItemESPPearls;
import sanandreasp.mods.EnderStuffPlus.item.ItemEndLeaves;
import sanandreasp.mods.EnderStuffPlus.item.ItemEnderFlesh;
import sanandreasp.mods.EnderStuffPlus.item.ItemEnderPetEgg;
import sanandreasp.mods.EnderStuffPlus.item.ItemNiobArmor;
import sanandreasp.mods.EnderStuffPlus.item.ItemNiobDoor;
import sanandreasp.mods.EnderStuffPlus.item.ItemRaincoat;
import sanandreasp.mods.EnderStuffPlus.item.tool.ItemNiobAxe;
import sanandreasp.mods.EnderStuffPlus.item.tool.ItemNiobBow;
import sanandreasp.mods.EnderStuffPlus.item.tool.ItemNiobHoe;
import sanandreasp.mods.EnderStuffPlus.item.tool.ItemNiobPickaxe;
import sanandreasp.mods.EnderStuffPlus.item.tool.ItemNiobShears;
import sanandreasp.mods.EnderStuffPlus.item.tool.ItemNiobShovel;
import sanandreasp.mods.EnderStuffPlus.item.tool.ItemNiobSword;
import sanandreasp.mods.EnderStuffPlus.packet.PacketHandlerCommon;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityAvisEgg;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;

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
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=ESPModRegistry.modID, name="EnderStuff+", version="1.1.0", dependencies="required-after:sapmanpackcore")
@NetworkMod(clientSideRequired=true, serverSideRequired=false,
	clientPacketHandlerSpec = @SidedPacketHandler(channels=ESPModRegistry.channelID, packetHandler=PacketHandlerClient.class),
	serverPacketHandlerSpec = @SidedPacketHandler(channels=ESPModRegistry.channelID, packetHandler=PacketHandlerCommon.class))
public class ESPModRegistry {
	
	public static final String modID = "EnderStuffPlus";
	public static final String channelID = "EnderStuffP";
	public static final String proxyCmn = "sanandreasp.mods.EnderStuffPlus.registry.CommonProxy";
	public static final String proxyClt = "sanandreasp.mods.EnderStuffPlus.client.registry.ClientProxy";
	
	public static ConfigRegistry conf;
	
	@Instance(modID)
	public static ESPModRegistry instance;
	
	@SidedProxy(clientSide=ESPModRegistry.proxyClt, serverSide=ESPModRegistry.proxyCmn)
	public static CommonProxy proxy;
	
	public static Item	espPearls, enderFlesh, avisFeather,
						avisArrow, avisCompass, enderPetStaff, endIngot, niobBow,
						rainCoat, niobHelmet, niobPlate, niobLegs, niobBoots,
						niobPick, niobShovel, niobAxe, niobHoe, niobSword,
						niobShears, endNugget, itemNiobDoor, enderStick;
	public static ItemEnderPetEgg enderPetEgg;

	public static Block	avisEgg, endOre, endBlock, biomeChanger, duplicator,
						weatherAltar, blockEndDoor, enderLeaves, enderLog, sapEndTree,
						enderPlanks, corruptES;
	
	public static Enchantment enderChestTel;

	public static HashMap<Integer, ItemStack> niobSet = Maps.newHashMap();
	
	public static EnumToolMaterial TOOL_NIOBIUM;
	public static EnumArmorMaterial ARMOR_NIOBIUM;

	public static CreativeTabs espTab, espTabCoats;
	
	public static ManagerPackHelper manHelper = new ManagerPackHelper();

	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		manHelper.checkManPack(evt.getModMetadata().name);
		
		if( !manHelper.loading ) return;
		
		manHelper.initMan(
				new SAPConfigManager("EnderStuffPlus", "EnderStuffPlus.txt", "/sanandreasp/"),
				new SAPLanguageManager("/sanandreasp/EnderStuffPlus/", "1.1", "EnderStuff+"),
				new SAPUpdateManager("EnderStuffPlus", 1, 1, 0, "http://dl.dropbox.com/u/56920617/EnderStuffPMod_latest.txt", "http://www.minecraftforum.net/topic/936911-")
		);
		
		ConfigRegistry.setConfig(ESPModRegistry.manHelper);
		
		ESPModRegistry.espTab = new CreativeTabs("ESPTab") {
			@Override
			public ItemStack getIconItemStack() {
				return new ItemStack(ESPModRegistry.biomeChanger);
			}
		};
		ESPModRegistry.espTabCoats = new CreativeTabs("ESPTabCoats") {
			@Override
			public ItemStack getIconItemStack() {
				return new ItemStack(ESPModRegistry.rainCoat, 1, 16 | 32);
			}
		};
		
		ESPModRegistry.TOOL_NIOBIUM = EnumHelper.addToolMaterial(
				"NIOBIUM",
				EnumToolMaterial.IRON.getHarvestLevel(),
				EnumToolMaterial.IRON.getMaxUses(),
				EnumToolMaterial.IRON.getEfficiencyOnProperMaterial(),
				EnumToolMaterial.IRON.getDamageVsEntity(),
				EnumToolMaterial.GOLD.getEnchantability()
			);
		
		ESPModRegistry.ARMOR_NIOBIUM = EnumHelper.addArmorMaterial(
				"NIOBIUM",
				(Integer)ObfuscationReflectionHelper.getPrivateValue(EnumArmorMaterial.class, EnumArmorMaterial.IRON, "maxDamageFactor", "field_78048_f"),
				new int[] {
					EnumArmorMaterial.IRON.getDamageReductionAmount(0),
					EnumArmorMaterial.IRON.getDamageReductionAmount(1),
					EnumArmorMaterial.IRON.getDamageReductionAmount(2),
					EnumArmorMaterial.IRON.getDamageReductionAmount(3)
				},
				EnumArmorMaterial.GOLD.getEnchantability()
			);
		
	// Blocks
		ESPModRegistry.avisEgg			= new BlockAvisEgg(ConfigRegistry.blockIDs.get("Avis Egg").intValue())
											.setUnlocalizedName("enderstuffp:avisEgg")
											.setHardness(1F)
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.endOre			= new BlockEndOre(ConfigRegistry.blockIDs.get("Niobium Ore").intValue())
											.setUnlocalizedName("enderstuffp:oreNiob")
											.setHardness(3.0F)
											.setResistance(5.0F)
											.setStepSound(Block.soundStoneFootstep)
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.endBlock 		= new BlockEndStorage(ConfigRegistry.blockIDs.get("Niobium Block").intValue())
											.setUnlocalizedName("enderstuffp:blockNiob")
											.setHardness(5.0F)
											.setResistance(10.0F)
											.setStepSound(Block.soundMetalFootstep)
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.biomeChanger		= new BlockBiomeChanger(ConfigRegistry.blockIDs.get("Biome Changer").intValue(), Material.rock)
											.setUnlocalizedName("enderstuffp:biomeChanger")
											.setHardness(1F)
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.duplicator 		= new BlockDuplicator(ConfigRegistry.blockIDs.get("Duplicator").intValue())
											.setUnlocalizedName("enderstuffp:duplicator")
											.setHardness(1F)
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.weatherAltar 	= new BlockWeatherAltar(ConfigRegistry.blockIDs.get("Weather Altar").intValue())
											.setUnlocalizedName("enderstuffp:weatherAltar")
											.setHardness(1F)
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.blockEndDoor		= new BlockEnderDoor(ConfigRegistry.blockIDs.get("Ender Door").intValue(), Material.iron)
											.setUnlocalizedName("enderstuffp:enderDoor")
											.setHardness(5.0F)
											.setStepSound(Block.soundMetalFootstep);
		ESPModRegistry.enderLeaves 		= new BlockEndLeaves(ConfigRegistry.blockIDs.get("Ender Leaves").intValue())
											.setUnlocalizedName("enderstuffp:enderLeaves")
											.setHardness(0.2F)
											.setLightOpacity(1)
											.setStepSound(Block.soundGrassFootstep)
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.enderLog 		= new BlockEndLog(ConfigRegistry.blockIDs.get("Ender Log").intValue())
											.setUnlocalizedName("enderstuffp:enderLog")
											.setHardness(2.0F)
											.setStepSound(Block.soundWoodFootstep)
											.setCreativeTab(ESPModRegistry.espTab)
											.setLightValue(4.1F / 15F);
		ESPModRegistry.sapEndTree		= new BlockSaplingEndTree(ConfigRegistry.blockIDs.get("Ender Sapling").intValue())
											.setUnlocalizedName("enderstuffp:enderSapling")
											.setHardness(0.0F)
											.setStepSound(Block.soundGrassFootstep)
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.enderPlanks		= new BlockEnderWood(ConfigRegistry.blockIDs.get("Ender Planks").intValue())
											.setUnlocalizedName("enderstuffp:enderWood")
											.setHardness(2.0F)
											.setResistance(5.0F)
											.setStepSound(Block.soundWoodFootstep)
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.corruptES		= new Block(ConfigRegistry.blockIDs.get("Corrupt End Stone").intValue(), Block.whiteStone.blockMaterial)
											.setTextureName("enderstuffp:corrupt_end_stone")
											.setUnlocalizedName("enderstuffp:corruptES")
											.setHardness(Block.whiteStone.blockHardness)
											.setResistance(Block.whiteStone.blockResistance)
											.setStepSound(Block.soundStoneFootstep)
											.setCreativeTab(ESPModRegistry.espTab);
		GameRegistry.registerTileEntity(TileEntityAvisEgg.class, "avisEggTE");
		GameRegistry.registerTileEntity(TileEntityBiomeChanger.class, "biomeChangerTE");
		GameRegistry.registerTileEntity(TileEntityDuplicator.class, "duplicatorTE");
		GameRegistry.registerTileEntity(TileEntityWeatherAltar.class, "weatherAltarTE");
		
		this.registerBlocks(
				ESPModRegistry.avisEgg,			ESPModRegistry.biomeChanger,
				ESPModRegistry.duplicator,		ESPModRegistry.weatherAltar,
				ESPModRegistry.blockEndDoor,	ESPModRegistry.enderLog, 
				ESPModRegistry.sapEndTree,		ESPModRegistry.enderPlanks, 
				ESPModRegistry.corruptES
			);
		GameRegistry.registerBlock(ESPModRegistry.enderLeaves, ItemEndLeaves.class, "enderstuffp:blockEndLeaves");
		GameRegistry.registerBlock(ESPModRegistry.endOre, ItemBlockNamedMeta.class, "enderstuffp:blockEndOre");
		GameRegistry.registerBlock(ESPModRegistry.endBlock, ItemBlockNamedMeta.class, "enderstuffp:blockEndStorg");
		
		Block.setBurnProperties(ESPModRegistry.enderLeaves.blockID, 30, 60);
		Block.setBurnProperties(ESPModRegistry.enderLog.blockID, 5, 5);
	
	// Items
		ESPModRegistry.espPearls		= new ItemESPPearls(ConfigRegistry.itemIDs.get("ESP Pearls").intValue() - 256)
											.setUnlocalizedName("enderstuffp:espPearls")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.enderFlesh		= new ItemEnderFlesh(ConfigRegistry.itemIDs.get("Ender Flesh").intValue() - 256)
											.setUnlocalizedName("enderstuffp:enderFlesh")
											.setTextureName("enderstuffp:enderFlesh")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.avisFeather		= new Item(ConfigRegistry.itemIDs.get("Avis Feather").intValue() - 256)
											.setUnlocalizedName("enderstuffp:avisFeather")
											.setTextureName("enderstuffp:avisFeather")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.avisArrow		= new Item(ConfigRegistry.itemIDs.get("Avis Arrow").intValue() - 256)
											.setUnlocalizedName("enderstuffp:avisArrow")
											.setTextureName("enderstuffp:avisArrow")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.avisCompass		= new ItemAvisCompass(ConfigRegistry.itemIDs.get("Avis Compass").intValue() - 256)
											.setUnlocalizedName("enderstuffp:avisCompass")
											.setTextureName("enderstuffp:avisCompass")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.enderPetEgg		= (ItemEnderPetEgg) new ItemEnderPetEgg(ConfigRegistry.itemIDs.get("Enderpet Egg").intValue() - 256)
											.setUnlocalizedName("enderPetEgg")
											.setTextureName("enderPetEgg")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.enderPetStaff	= new Item(ConfigRegistry.itemIDs.get("Enderpet Staff").intValue() - 256)
											.setUnlocalizedName("enderstuffp:petStaff")
											.setTextureName("enderstuffp:petStaff")
											.setCreativeTab(ESPModRegistry.espTab)
											.setFull3D();
		ESPModRegistry.endIngot			= new Item(ConfigRegistry.itemIDs.get("Niobium Ingot").intValue() - 256)
											.setUnlocalizedName("enderstuffp:niobIngot")
											.setTextureName("enderstuffp:niobIngot")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.niobBow			= new ItemNiobBow(ConfigRegistry.itemIDs.get("Niobium Bow").intValue() - 256)
											.setUnlocalizedName("enderstuffp:bowNiob")
											.setTextureName("enderstuffp:bowNiob")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.rainCoat			= new ItemRaincoat(ConfigRegistry.itemIDs.get("Ender-Raincoat").intValue() - 256)
											.setUnlocalizedName("enderstuffp:rainCoat")
											.setTextureName("enderstuffp:rainCoat")
											.setCreativeTab(ESPModRegistry.espTabCoats);
		ESPModRegistry.niobHelmet 		= new ItemNiobArmor(ConfigRegistry.itemIDs.get("Niobium Helmet").intValue() - 256, ESPModRegistry.ARMOR_NIOBIUM, 0)
											.setUnlocalizedName("enderstuffp:niobHelmet")
											.setTextureName("enderstuffp:niobHelmet")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.niobPlate 		= new ItemNiobArmor(ConfigRegistry.itemIDs.get("Niobium Chestplate").intValue() - 256, ESPModRegistry.ARMOR_NIOBIUM, 1)
											.setUnlocalizedName("enderstuffp:niobChestplate")
											.setTextureName("enderstuffp:niobChestplate")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.niobLegs 		= new ItemNiobArmor(ConfigRegistry.itemIDs.get("Niobium Leggings").intValue() - 256, ESPModRegistry.ARMOR_NIOBIUM, 2)
											.setUnlocalizedName("enderstuffp:niobLeggings")
											.setTextureName("enderstuffp:niobLeggings")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.niobBoots 		= new ItemNiobArmor(ConfigRegistry.itemIDs.get("Niobium Boots").intValue() - 256, ESPModRegistry.ARMOR_NIOBIUM, 3)
											.setUnlocalizedName("enderstuffp:niobBoots")
											.setTextureName("enderstuffp:niobBoots")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.niobPick			= new ItemNiobPickaxe(ConfigRegistry.itemIDs.get("Niobium Pickaxe").intValue() - 256, ESPModRegistry.TOOL_NIOBIUM)
											.setUnlocalizedName("enderstuffp:niobPick")
											.setTextureName("enderstuffp:niobPick")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.niobShovel 		= new ItemNiobShovel(ConfigRegistry.itemIDs.get("Niobium Shovel").intValue() - 256, ESPModRegistry.TOOL_NIOBIUM)
											.setUnlocalizedName("enderstuffp:niobShovel")
											.setTextureName("enderstuffp:niobShovel")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.niobAxe 			= new ItemNiobAxe(ConfigRegistry.itemIDs.get("Niobium Axe").intValue() - 256, ESPModRegistry.TOOL_NIOBIUM)
											.setUnlocalizedName("enderstuffp:niobAxe")
											.setTextureName("enderstuffp:niobAxe")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.niobHoe 			= new ItemNiobHoe(ConfigRegistry.itemIDs.get("Niobium Hoe").intValue() - 256, ESPModRegistry.TOOL_NIOBIUM)
											.setUnlocalizedName("enderstuffp:niobHoe")
											.setTextureName("enderstuffp:niobHoe")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.niobSword 		= new ItemNiobSword(ConfigRegistry.itemIDs.get("Niobium Sword").intValue() - 256, ESPModRegistry.TOOL_NIOBIUM)
											.setUnlocalizedName("enderstuffp:niobSword")
											.setTextureName("enderstuffp:niobSword")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.niobShears 		= new ItemNiobShears(ConfigRegistry.itemIDs.get("Niobium Shears").intValue() - 256)
											.setUnlocalizedName("enderstuffp:niobShears")
											.setTextureName("enderstuffp:niobShears")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.endNugget 		= new Item(ConfigRegistry.itemIDs.get("Niobium Nugget").intValue() - 256)
											.setUnlocalizedName("enderstuffp:niobNugget")
											.setTextureName("enderstuffp:niobNugget")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.itemNiobDoor 	= new ItemNiobDoor(ConfigRegistry.itemIDs.get("Ender Door").intValue() - 256)
											.setUnlocalizedName("enderstuffp:doorNiob")
											.setTextureName("enderstuffp:doorNiob")
											.setCreativeTab(ESPModRegistry.espTab);
		ESPModRegistry.enderStick 		= new Item(ConfigRegistry.itemIDs.get("Ender Stick").intValue() - 256)
											.setUnlocalizedName("enderstuffp:enderStick")
											.setTextureName("enderstuffp:enderStick")
											.setCreativeTab(ESPModRegistry.espTab);

		this.registerItems(
				ESPModRegistry.espPearls,		ESPModRegistry.avisFeather, 
				ESPModRegistry.avisArrow,		ESPModRegistry.avisCompass, 
				ESPModRegistry.enderPetEgg,		ESPModRegistry.enderPetStaff, 
				ESPModRegistry.endIngot,		ESPModRegistry.niobBow, 
				ESPModRegistry.niobHelmet,		ESPModRegistry.niobPlate, 
				ESPModRegistry.niobLegs,		ESPModRegistry.niobBoots,
				ESPModRegistry.niobPick,		ESPModRegistry.niobShovel, 
				ESPModRegistry.niobAxe,			ESPModRegistry.niobHoe, 
				ESPModRegistry.niobSword,		ESPModRegistry.niobShears, 
				ESPModRegistry.enderFlesh,		ESPModRegistry.rainCoat,
				ESPModRegistry.itemNiobDoor,	ESPModRegistry.endNugget, 
				ESPModRegistry.enderStick
		);
		
	// Enchantments
		ESPModRegistry.enderChestTel = new EnchantmentEnderChestTeleport(ConfigRegistry.enchID, 5);
		Enchantment.addToBookList(ESPModRegistry.enderChestTel);
		
	// Armor Set
		niobSet.put(0, new ItemStack(ESPModRegistry.niobBoots));
		niobSet.put(1, new ItemStack(ESPModRegistry.niobLegs));
		niobSet.put(2, new ItemStack(ESPModRegistry.niobPlate));
		niobSet.put(3, new ItemStack(ESPModRegistry.niobHelmet));
		
	// Misc registering
		proxy.registerHandlers();
		RegistryDungeonLoot.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent evt) {
		if( !manHelper.loading ) return;

		RegistryDuplicator.registerFuel(new ItemStack(ESPModRegistry.endIngot), 100);
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
		RegistryDuplicator.registerDupableItem(new ItemStack(ESPModRegistry.endOre), 20);
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
		
		OreDictionary.registerOre("ingotNiob", new ItemStack(ESPModRegistry.endIngot));
		OreDictionary.registerOre("oreNiob", new ItemStack(ESPModRegistry.endOre));
		OreDictionary.registerOre("blockNiob", new ItemStack(ESPModRegistry.endBlock));
		OreDictionary.registerOre("logWood", new ItemStack(ESPModRegistry.enderLog, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("plankWood", ESPModRegistry.enderPlanks);
		OreDictionary.registerOre("treeSapling", new ItemStack(ESPModRegistry.sapEndTree, 1, OreDictionary.WILDCARD_VALUE));
		
		MinecraftForge.setBlockHarvestLevel(ESPModRegistry.endOre, "pickaxe", 2);
		
		proxy.registerClientStuff();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		if( !ESPModRegistry.manHelper.loading ) return;
	
	// Language localizations
		LangRegistry.loadLangs(manHelper.getLangMan());
		
	// Craftings & Smeltings
		FurnaceRecipes.smelting().addSmelting(ESPModRegistry.endOre.blockID, 0, new ItemStack(ESPModRegistry.endIngot, 1, 0), 0.85F);
	
		CraftingRegistry.initCraftings();
		
	// Spawnings
		EntityRegistry.addSpawn(EntityEnderNivis.class, 
				ConfigRegistry.spawnConditions.get("EnderNivis")[0].intValue(),
				ConfigRegistry.spawnConditions.get("EnderNivis")[1].intValue(),
				ConfigRegistry.spawnConditions.get("EnderNivis")[2].intValue(),
				EnumCreatureType.monster, getEnderNivisBiomes()
		);
		EntityRegistry.addSpawn(EntityEnderIgnis.class, 
				ConfigRegistry.spawnConditions.get("EnderIgnis")[0].intValue(),
				ConfigRegistry.spawnConditions.get("EnderIgnis")[1].intValue(),
				ConfigRegistry.spawnConditions.get("EnderIgnis")[2].intValue(),
				EnumCreatureType.monster,
				new BiomeGenBase[] {
					BiomeGenBase.sky, BiomeGenBase.desert,
					BiomeGenBase.hell, BiomeGenBase.desertHills
				}
		);
		EntityRegistry.addSpawn(EntityEnderRay.class, 
				ConfigRegistry.spawnConditions.get("EnderRay")[0].intValue(),
				ConfigRegistry.spawnConditions.get("EnderRay")[1].intValue(),
				ConfigRegistry.spawnConditions.get("EnderRay")[2].intValue(),
				EnumCreatureType.monster,
				new BiomeGenBase[] {
					BiomeGenBase.sky
				}
		);
		EntityRegistry.addSpawn(EntityEnderMiss.class, 
				ConfigRegistry.spawnConditions.get("EnderMiss")[0].intValue(),
				ConfigRegistry.spawnConditions.get("EnderMiss")[1].intValue(),
				ConfigRegistry.spawnConditions.get("EnderMiss")[2].intValue(),
				EnumCreatureType.monster, getEnderManBiomes());
		EntityRegistry.addSpawn(EntityEnderAvis.class, 
				ConfigRegistry.spawnConditions.get("EnderAvis")[0].intValue(),
				ConfigRegistry.spawnConditions.get("EnderAvis")[1].intValue(),
				ConfigRegistry.spawnConditions.get("EnderAvis")[2].intValue(),
				EnumCreatureType.monster,
				new BiomeGenBase[] {
					BiomeGenBase.extremeHills,
					BiomeGenBase.extremeHillsEdge, BiomeGenBase.sky,
					BiomeGenBase.desertHills, BiomeGenBase.forestHills,
					BiomeGenBase.iceMountains, BiomeGenBase.taigaHills,
					BiomeGenBase.jungleHills
				}
		);
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

	@SuppressWarnings("unchecked")
	private BiomeGenBase[] getEnderManBiomes() {
		List<BiomeGenBase> biomes = new ArrayList<BiomeGenBase>();

		for( BiomeGenBase currBiome : BiomeGenBase.biomeList ) {
			if( currBiome == null )
				continue;

			List<SpawnListEntry> monsterList = currBiome.getSpawnableList(EnumCreatureType.monster);
			for( SpawnListEntry entry : monsterList ) {
				if( entry.entityClass.equals(EntityEnderman.class) ) {
					biomes.add(currBiome);
					break;
				}
			}
		}

		return biomes.toArray(new BiomeGenBase[0]);
	}
	
	private BiomeGenBase[] getEnderNivisBiomes() {
		List<BiomeGenBase> biomes = new ArrayList<BiomeGenBase>();
		biomes.add(BiomeGenBase.sky);
		
		for( BiomeGenBase currBiome : BiomeGenBase.biomeList ) {
			if( currBiome == null )
				continue;
			
			if( currBiome.getEnableSnow() && currBiome.temperature < 0.1F )
				biomes.add(currBiome);
			if( BiomeDictionary.isBiomeOfType(currBiome, BiomeDictionary.Type.FROZEN) )
				biomes.add(currBiome);
		}
		
		return biomes.toArray(new BiomeGenBase[0]);
	}
	
	/** registers the Items **/
	private void registerItems(Item... items) {
		for( int i = 0; i < items.length; i++) GameRegistry.registerItem(items[i], "enderstuffp:item_"+i );
	}
	
	/** registers the Blocks **/
	private void registerBlocks(Block... blocks) {
		for( int i = 0; i < blocks.length; i++) GameRegistry.registerBlock(blocks[i], "enderstuffp:block_"+i );
	}
	
	public static boolean hasPlayerFullNiob(EntityPlayer player) {
		boolean b = true;
		for( int i = 0; i < 4; i++ ) {
			if( player.getCurrentArmor(i) == null ) {
				b = false;
				break;
			} else if( player.getCurrentArmor(i).itemID != ESPModRegistry.niobSet.get(i).itemID ) {
				b = false;
				break;
			}
		}
		return b;
	}
}
