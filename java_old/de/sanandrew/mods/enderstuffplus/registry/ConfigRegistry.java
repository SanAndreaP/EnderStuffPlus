package de.sanandrew.mods.enderstuffplus.registry;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Maps;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public final class ConfigRegistry
{
//    public static Map<CfgNames, Integer> blockIDs = Maps.newEnumMap(CfgNames.class);
//    public static Map<CfgNames, Integer> itemIDs = Maps.newEnumMap(CfgNames.class);
    public static Map<String, Integer[]> spawnConditions = Maps.newHashMap();

    public static int enchID = 128;
    public static boolean genAvisNest = true;
    public static boolean genEndlessEnd = true;
    public static boolean genLeak = true;
    public static boolean genNiob = true;
    public static boolean griefing = true;
    public static boolean useAnimations = true;
    public static boolean useNiobHDGlow = true;

    private static final String CATEGORY_SPAWNINGS = "spawnings";
    private static final String CATEGORY_WORLDGEN = "worldgen";

    static {
//        blockIDs.put(CfgNames.AVIS_EGG, 1000);
//        blockIDs.put(CfgNames.NIOBIUM_ORE, 1001);
//        blockIDs.put(CfgNames.NIOBIUM_BLOCK, 1002);
//        blockIDs.put(CfgNames.BIOME_CHANGER, 1003);
//        blockIDs.put(CfgNames.DUPLICATOR, 1004);
//        blockIDs.put(CfgNames.WEATHER_ALTAR, 1005);
//        blockIDs.put(CfgNames.ENDER_DOOR_BLOCK, 1006);
//        blockIDs.put(CfgNames.ENDER_LEAVES, 1007);
//        blockIDs.put(CfgNames.ENDER_LOG, 1008);
//        blockIDs.put(CfgNames.ENDER_PLANKS, 1009);
//        blockIDs.put(CfgNames.ENDER_SAPLING, 1010);
//        blockIDs.put(CfgNames.CORRUPT_END_STONE, 1011);
//        blockIDs.put(CfgNames.END_FLUID, 1012);
//
//        itemIDs.put(CfgNames.ESP_PEARLS, 10000);
//        itemIDs.put(CfgNames.ENDER_FLESH, 10002);
//        itemIDs.put(CfgNames.AVIS_FEATHER, 10003);
//        itemIDs.put(CfgNames.AVIS_ARROW, 10004);
//        itemIDs.put(CfgNames.AVIS_COMPASS, 10005);
//        itemIDs.put(CfgNames.ENDERPET_EGG, 10006);
//        itemIDs.put(CfgNames.ENDERPET_STAFF, 10007);
//        itemIDs.put(CfgNames.NIOBIUM_INGOT, 10008);
//        itemIDs.put(CfgNames.NIOBIUM_BOW, 10009);
//        itemIDs.put(CfgNames.ENDER_RAINCOAT, 10010);
//        itemIDs.put(CfgNames.NIOBIUM_HELMET, 10011);
//        itemIDs.put(CfgNames.NIOBIUM_CHESTPLATE, 10012);
//        itemIDs.put(CfgNames.NIOBIUM_LEGGINGS, 10013);
//        itemIDs.put(CfgNames.NIOBIUM_BOOTS, 10014);
//        itemIDs.put(CfgNames.NIOBIUM_PICKAXE, 10015);
//        itemIDs.put(CfgNames.NIOBIUM_SHOVEL, 10016);
//        itemIDs.put(CfgNames.NIOBIUM_AXE, 10017);
//        itemIDs.put(CfgNames.NIOBIUM_HOE, 10018);
//        itemIDs.put(CfgNames.NIOBIUM_SWORD, 10019);
//        itemIDs.put(CfgNames.NIOBIUM_SHEARS, 10020);
//        itemIDs.put(CfgNames.NIOBIUM_NUGGET, 10021);
//        itemIDs.put(CfgNames.ENDER_DOOR_ITEM, 10022);
//        itemIDs.put(CfgNames.ENDER_STICK, 10023);

        spawnConditions.put("EnderNivis", new Integer[] { 1, 1, 4 });
        spawnConditions.put("EnderIgnis", new Integer[] { 1, 1, 4 });
        spawnConditions.put("EnderRay", new Integer[] { 1, 1, 4 });
        spawnConditions.put("EnderAvis", new Integer[] { 1, 1, 4 });
        spawnConditions.put("EnderMiss", new Integer[] { 1, 1, 4 });
    }

    public static void setConfig(File modCfgDir) {
        Configuration config = new Configuration(new File(modCfgDir, "sanandreasp/" + ESPModRegistry.MOD_ID + ".cfg"));

        config.load();

//        for( Entry<CfgNames, Integer> block : blockIDs.entrySet() ) {
//            blockIDs.put(block.getKey(), config.getBlock(block.getKey().toString(), block.getValue()).getInt());
//        }
//
//        for( Entry<CfgNames, Integer> item : itemIDs.entrySet() ) {
//            itemIDs.put(item.getKey(), config.getItem(item.getKey().toString(), item.getValue()).getInt());
//        }

        config.addCustomCategoryComment(CATEGORY_SPAWNINGS, "The values in this category are arrays. They represent following pattern:"
                                                            + "\n  value #1 is the minimum spawn count per spawn loop"
                                                            + "\n  value #2 is the maximum spawn count per spawn loop"
                                                            + "\n  value #3 is the spawn rate weight"
                                                            + "\n  >>The Entity can spawn #1 to #2 times per loop with"
                                                            + " a weighted probability of #3<<");
        for( Entry<String, Integer[]> spawns : spawnConditions.entrySet() ) {
            Property prop = config.get(CATEGORY_SPAWNINGS, spawns.getKey(), ArrayUtils.toPrimitive(spawns.getValue()));
            spawnConditions.put(spawns.getKey(), ArrayUtils.toObject(prop.getIntList()));
        }

        config.addCustomCategoryComment(CATEGORY_WORLDGEN, "Several settings regarding world generation");
        genNiob = config.get(CATEGORY_WORLDGEN, "Generate Niobium Ore", true).getBoolean(true);
        genLeak = config.get(CATEGORY_WORLDGEN, "Generate Ender Leaks", true).getBoolean(true);
        genEndlessEnd = config.get(CATEGORY_WORLDGEN, "Generate End Islands", true).getBoolean(true);
        genAvisNest = config.get(CATEGORY_WORLDGEN, "Generate Avis Nests", true).getBoolean(true);

        enchID = config.get(Configuration.CATEGORY_GENERAL, "EC-Teleport Enchantment-ID", enchID).getInt();
        griefing = config.get(Configuration.CATEGORY_GENERAL, "Can Mod-Ender-Mobs grief", true).getBoolean(true);
        useNiobHDGlow = config.get(Configuration.CATEGORY_GENERAL, "Use HD Tool glow effect", true).getBoolean(true);
        useAnimations = config.get(Configuration.CATEGORY_GENERAL, "Use animated textures", true).getBoolean(true);

        config.save();
    }

    public static enum CfgNames {
        AVIS_EGG,               // blocks
        NIOBIUM_ORE,
        NIOBIUM_BLOCK,
        BIOME_CHANGER,
        DUPLICATOR,
        WEATHER_ALTAR,
        ENDER_DOOR_BLOCK,
        ENDER_LEAVES,
        ENDER_LOG,
        ENDER_PLANKS,
        ENDER_SAPLING,
        CORRUPT_END_STONE,
        END_FLUID,
        ESP_PEARLS,             // items
        ENDER_FLESH,
        AVIS_FEATHER,
        AVIS_ARROW,
        AVIS_COMPASS,
        ENDERPET_EGG,
        ENDERPET_STAFF,
        NIOBIUM_INGOT,
        NIOBIUM_BOW,
        ENDER_RAINCOAT,
        NIOBIUM_HELMET,
        NIOBIUM_CHESTPLATE,
        NIOBIUM_LEGGINGS,
        NIOBIUM_BOOTS,
        NIOBIUM_PICKAXE,
        NIOBIUM_SHOVEL,
        NIOBIUM_AXE,
        NIOBIUM_HOE,
        NIOBIUM_SWORD,
        NIOBIUM_SHEARS,
        NIOBIUM_NUGGET,
        ENDER_DOOR_ITEM,
        ENDER_STICK;

        /**
         * Returns a config-friendly name of the enum value.
         *
         * @return the name of the enum value without underscores
         *         and formatted in Pascal Case
         */
        @Override
        public String toString() {
            String name = "";
            Pattern pattern = Pattern.compile("(\\w+?)(_|$)");
            Matcher matcher = pattern.matcher(super.toString());

            if( !matcher.find() ) {
                return super.toString();
            }

            do {
                name += matcher.group(1).substring(0, 1).toUpperCase() + matcher.group(1).substring(1).toLowerCase();
            } while ( matcher.find() );

            return name;
        }
    }

}
