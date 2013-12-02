package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.Map;

import sanandreasp.core.manpack.managers.SAPConfigManager;
import sanandreasp.mods.ManagerPackHelper;

import com.google.common.collect.Maps;

public final class ConfigRegistry {
	public static Map<String, Integer> blockIDs = Maps.newHashMap();
	public static Map<String, Integer> itemIDs = Maps.newHashMap();
	public static Map<String, Integer[]> spawnConditions = Maps.newHashMap();
	
	public static int enchID = 128;

	public static boolean genNiob = true;
	public static boolean genLeak = true;
	public static boolean genEndlessEnd = true;
	public static boolean genAvisNest = true;
	public static boolean griefing = true;
	public static boolean useNiobHDGlow = true;
	public static boolean useAnimations = true;
	
	static {
		blockIDs.put("Avis Egg", 			1000);
		blockIDs.put("Niobium Ore",			1001);
		blockIDs.put("Niobium Block",		1002);
		blockIDs.put("Biome Changer",		1003);
		blockIDs.put("Duplicator",			1004);
		blockIDs.put("Weather Altar",		1005);
		blockIDs.put("Ender Door",			1006);
		blockIDs.put("Ender Leaves",		1007);
		blockIDs.put("Ender Log",			1008);
		blockIDs.put("Ender Planks",		1009);
		blockIDs.put("Ender Sapling",		1010);
		blockIDs.put("Corrupt End Stone",	1011);
		
		itemIDs.put("ESP Pearls", 			10000);
		itemIDs.put("Ender Flesh",			10002);
		itemIDs.put("Avis Feather", 		10003);
		itemIDs.put("Avis Arrow", 			10004);
		itemIDs.put("Avis Compass", 		10005);
		itemIDs.put("Enderpet Egg", 		10006);
		itemIDs.put("Enderpet Staff", 		10007);
		itemIDs.put("Niobium Ingot", 		10008);
		itemIDs.put("Niobium Bow", 			10009);
		itemIDs.put("Ender-Raincoat",		10010);
		itemIDs.put("Niobium Helmet",		10011);
		itemIDs.put("Niobium Chestplate",	10012);
		itemIDs.put("Niobium Leggings",		10013);
		itemIDs.put("Niobium Boots",		10014);
		itemIDs.put("Niobium Pickaxe",		10015);
		itemIDs.put("Niobium Shovel",		10016);
		itemIDs.put("Niobium Axe",			10017);
		itemIDs.put("Niobium Hoe",			10018);
		itemIDs.put("Niobium Sword",		10019);
		itemIDs.put("Niobium Shears",		10020);
		itemIDs.put("Niobium Nugget",		10021);
		itemIDs.put("Ender Door",			10022);
		itemIDs.put("Ender Stick",			10023);
		
		spawnConditions.put("EnderNivis",	new Integer[] { 1, 1, 4 });
		spawnConditions.put("EnderIgnis",	new Integer[] { 1, 1, 4 });
		spawnConditions.put("EnderRay",		new Integer[] { 1, 1, 4 });
		spawnConditions.put("EnderAvis",	new Integer[] { 1, 1, 4 });
		spawnConditions.put("EnderMiss",	new Integer[] { 1, 1, 4 });
	}

	public static void setConfig(ManagerPackHelper manHelper) {
		SAPConfigManager cfgMan = manHelper.getCfgMan();
		cfgMan.addStaticBlockIDs(blockIDs.keySet().toArray(new String[blockIDs.size()]), blockIDs.values().toArray(new Integer[blockIDs.size()]));
		cfgMan.addStaticItemIDs(itemIDs.keySet().toArray(new String[itemIDs.size()]), itemIDs.values().toArray(new Integer[itemIDs.size()]));
		
		cfgMan.addProperty("Ender Chest Teleport", "Enchantment IDs", enchID);
		
		cfgMan.addGroup("Spawn Settings");
		for( String propName : spawnConditions.keySet() ) {
			cfgMan.addProperty(propName+"_Weighted", "Spawn Settings", spawnConditions.get(propName)[0].intValue());
			cfgMan.addProperty(propName+"_Minimum",	 "Spawn Settings", spawnConditions.get(propName)[1].intValue());
			cfgMan.addProperty(propName+"_Maximum",	 "Spawn Settings", spawnConditions.get(propName)[2].intValue());
		}
		
		cfgMan.addGroup("Other Settings");
		 cfgMan.addProperty("generate Niobium", 			"Other Settings", genNiob);
		 cfgMan.addProperty("generate End Leak", 			"Other Settings", genLeak);
		 cfgMan.addProperty("generate endless End",	 		"Other Settings", genEndlessEnd);
		 cfgMan.addProperty("generate Avis Nest",	 		"Other Settings", genAvisNest);
		 cfgMan.addProperty("griefing Ender Mobs",			"Other Settings", griefing);
		 cfgMan.addProperty("use HD Niobium-Tool glow", 	"Other Settings", useNiobHDGlow);
		 cfgMan.addProperty("use animated textures", 		"Other Settings", useAnimations);
		
		cfgMan.loadConfig();
		
		for( String propName : itemIDs.keySet() ) {
			itemIDs.put(propName, cfgMan.getItemID(propName));
		}
		for( String propName : blockIDs.keySet() ) {
			blockIDs.put(propName, cfgMan.getBlockID(propName));
		}
		enchID = cfgMan.getSAPProperty("Ender Chest Teleport", "Enchantment IDs").getInt();
		
		for( String propName : spawnConditions.keySet() ) {
			spawnConditions.put(propName, new Integer[] {
					cfgMan.getSAPProperty(propName+"_Weighted", "Spawn Settings").getInt(),
					cfgMan.getSAPProperty(propName+"_Minimum", 	"Spawn Settings").getInt(),
					cfgMan.getSAPProperty(propName+"_Maximum", 	"Spawn Settings").getInt()
			});
		}
		
		genNiob 		= cfgMan.getSAPProperty("generate Niobium", 		"Other Settings").getBool();
		genLeak 		= cfgMan.getSAPProperty("generate End Leak", 		"Other Settings").getBool();
		genEndlessEnd 	= cfgMan.getSAPProperty("generate endless End", 	"Other Settings").getBool();
		genAvisNest 	= cfgMan.getSAPProperty("generate Avis Nest", 		"Other Settings").getBool();
		griefing 		= cfgMan.getSAPProperty("griefing Ender Mobs", 		"Other Settings").getBool();
		useNiobHDGlow 	= cfgMan.getSAPProperty("use HD Niobium-Tool glow", "Other Settings").getBool();
		useAnimations 	= cfgMan.getSAPProperty("use animated textures", 	"Other Settings").getBool();
	}

}
