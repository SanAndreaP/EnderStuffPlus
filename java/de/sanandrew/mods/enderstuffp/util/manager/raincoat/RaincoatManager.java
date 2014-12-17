package de.sanandrew.mods.enderstuffp.util.manager.raincoat;

import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class RaincoatManager
{
    private static final Map<String, CoatBaseEntry> BASE_LIST = Maps.newLinkedHashMap();
    private static final Map<String, CoatColorEntry> COLOR_LIST = Maps.newLinkedHashMap();

    public static final List<ItemStack> BASE_STACKS = new ArrayList<>();
    public static final List<ItemStack> COLOR_STACKS = new ArrayList<>();

    public static final CoatBaseEntry NULL_BASE = new CoatBaseEntry("NULL", 0xFFFFFF, new ResourceLocation("NULL"), new ResourceLocation("NULL"), null);
    public static final CoatColorEntry NULL_COLOR = new CoatColorEntry("NULL", 0xFFFFFF, new ResourceLocation("NULL"), new ResourceLocation("NULL"), null);

    public static CoatBaseEntry baseGold;
    public static CoatBaseEntry baseNiob;
    public static CoatBaseEntry baseIron;
    public static CoatBaseEntry baseRedstone;
    public static CoatBaseEntry baseObsidian;
    public static CoatBaseEntry baseTantal;

    public static void initialize() {
        baseGold = addBase(EnderStuffPlus.MOD_ID, "gold", 0xFFC545, "textures/entity/enderMiss_cape/stripes_gold.png",
                           "textures/entity/enderAvis_cape/stripes_gold.png", new ItemStack(Blocks.gold_block)
        );
        baseNiob = addBase(EnderStuffPlus.MOD_ID, "niobium", 0x3C408B, "textures/entity/enderMiss_cape/stripes_niob.png",
                           "textures/entity/enderAvis_cape/stripes_niob.png", new ItemStack(/*ModBlockRegistry.endBlock*/Blocks.grass, 1, 0)
        );
        baseIron = addBase(EnderStuffPlus.MOD_ID, "iron", 0xA9A9A9,
                           "textures/entity/enderMiss_cape/stripes_iron.png", "textures/entity/enderAvis_cape/stripes_iron.png", new ItemStack(Blocks.iron_block)
        );
        baseRedstone = addBase(EnderStuffPlus.MOD_ID, "redstone", 0xC62127, "textures/entity/enderMiss_cape/stripes_reds.png",
                               "textures/entity/enderAvis_cape/stripes_reds.png", new ItemStack(Blocks.redstone_block)
        );
        baseObsidian = addBase(EnderStuffPlus.MOD_ID, "obsidian", 0x1C1C22, "textures/entity/enderMiss_cape/stripes_obsd.png",
                               "textures/entity/enderAvis_cape/stripes_obsd.png", new ItemStack(Blocks.obsidian)
        );
        baseTantal = addBase(EnderStuffPlus.MOD_ID, "tantalum", 0xFF81E8, "textures/entity/enderMiss_cape/stripes_tant.png",
                             "textures/entity/enderAvis_cape/stripes_tant.png", new ItemStack(/*ModBlockRegistry.endBlock*/Blocks.grass, 1, 1)
        );

        ArrayList<Triplet<String, Integer, Integer>> colors = new ArrayList<>(18);
        colors.add(Triplet.with("black", 0x1A1515, 15));
        colors.add(Triplet.with("red", 0xCF3B37, 14));
        colors.add(Triplet.with("green", 0x3D591B, 13));
        colors.add(Triplet.with("brown", 0x663A20, 12));
        colors.add(Triplet.with("blue", 0x3343C6, 11));
        colors.add(Triplet.with("purple", 0xB54AE7, 10));
        colors.add(Triplet.with("cyan", 0x349EC1, 9));
        colors.add(Triplet.with("lightgray", 0xD3D8D8, 8));
        colors.add(Triplet.with("gray", 0x4D4D4D, 7));
        colors.add(Triplet.with("pink", 0xF4BBD1, 6));
        colors.add(Triplet.with("lime", 0x50E243, 5));
        colors.add(Triplet.with("yellow", 0xE4DC2A, 4));
        colors.add(Triplet.with("lightblue", 0x98C2F1, 3));
        colors.add(Triplet.with("magenta", 0xE66AEB, 2));
        colors.add(Triplet.with("orange", 0xF7B24C, 1));
        colors.add(Triplet.with("white", 0xF8F8F8, 0));
        for( Triplet<String, Integer, Integer> clr : colors ) {
            addColor(EnderStuffPlus.MOD_ID, clr.getValue0(), clr.getValue1(), "textures/entity/enderMiss_cape/" + clr.getValue0() + ".png",
                     "textures/entity/enderAvis_cape/" + clr.getValue0() + ".png", new ItemStack(Blocks.wool, 1, clr.getValue2())
            );
        }
        addColor(EnderStuffPlus.MOD_ID, "gold", 0xC5B600, "textures/entity/enderMiss_cape/gold.png", "textures/entity/enderAvis_cape/gold.png",
                 new ItemStack(Items.gold_nugget)
        );
        addColor(EnderStuffPlus.MOD_ID, "niobium", 0x141E61, "textures/entity/enderMiss_cape/niob.png", "textures/entity/enderAvis_cape/niob.png",
                 new ItemStack(EspItems.endNugget, 1, 0)
        );

        addSpecialColor(EnderStuffPlus.MOD_ID, new CoatColorTransparent("transparent", 0xCDCDCD,
                                                                        new ResourceLocation(EnderStuffPlus.MOD_ID, "textures/entity/enderMiss_cape/transp.png"),
                                                                        new ResourceLocation(EnderStuffPlus.MOD_ID, "textures/entity/enderAvis_cape/transp.png"),
                                                                        new ItemStack(Blocks.glass))
        );

        NULL_BASE.uuid = "NULL";
        NULL_COLOR.uuid = "NULL";
    }

    public static CoatBaseEntry addBase(String modId, String name, int color, String missTexture, String avisTexture, ItemStack ingredient) {
        ResourceLocation miss = new ResourceLocation(modId, missTexture);
        ResourceLocation avis = new ResourceLocation(modId, avisTexture);
        CoatBaseEntry coatBase = new CoatBaseEntry(name, color, miss, avis, ingredient);

        addSpecialBase(modId, coatBase);

        return coatBase;
    }

    public static void addColor(String modId, String name, int color, String missTexture, String avisTexture, ItemStack ingredient) {
        ResourceLocation miss = new ResourceLocation(modId, missTexture);
        ResourceLocation avis = new ResourceLocation(modId, avisTexture);
        addSpecialColor(modId, new CoatColorEntry(name, color, miss, avis, ingredient));
    }

    public static void addSpecialColor(String modid, CoatColorEntry entry) {
        ItemStack craftingIngredient = entry.craftingItem.copy();
        String uuid = UUID.nameUUIDFromBytes(ArrayUtils.addAll(modid.getBytes(), entry.name.getBytes())).toString();
        entry.uuid = uuid;
        COLOR_LIST.put(uuid, entry);
        COLOR_STACKS.add(craftingIngredient);
    }

    public static void addSpecialBase(String modid, CoatBaseEntry entry) {
        ItemStack craftingIngredient = entry.craftingItem.copy();
        String uuid = UUID.nameUUIDFromBytes(ArrayUtils.addAll(modid.getBytes(), entry.name.getBytes())).toString();
        entry.uuid = uuid;
        BASE_LIST.put(uuid, entry);
        BASE_STACKS.add(craftingIngredient);
    }

    public static CoatBaseEntry getBase(String uuid) {
        if( BASE_LIST.containsKey(uuid) ) {
            return BASE_LIST.get(uuid);
        }

        return NULL_BASE;
    }

    public static CoatColorEntry getColor(String uuid) {
        if( COLOR_LIST.containsKey(uuid) ) {
            return COLOR_LIST.get(uuid);
        }

        return NULL_COLOR;
    }

    public static CoatBaseEntry getBase(String modId, String name) {
        String uuid = UUID.nameUUIDFromBytes(ArrayUtils.addAll(modId.getBytes(), name.getBytes())).toString();
        if( BASE_LIST.containsKey(uuid) ) {
            return BASE_LIST.get(uuid);
        }

        return NULL_BASE;
    }

    public static CoatColorEntry getColor(String modId, String name) {
        String uuid = UUID.nameUUIDFromBytes(ArrayUtils.addAll(modId.getBytes(), name.getBytes())).toString();
        if( COLOR_LIST.containsKey(uuid) ) {
            return COLOR_LIST.get(uuid);
        }

        return NULL_COLOR;
    }

    public static List<String> getBaseList() {
        return new ArrayList<>(BASE_LIST.keySet());
    }

    public static List<String> getColorList() {
        return new ArrayList<>(COLOR_LIST.keySet());
    }

    public static class CoatBaseEntry
    {
        public final int color;
        public final String desc;
        public final String name;
        private final String unlocName;
        private String uuid;
        public final ResourceLocation missTexture;
        public final ResourceLocation avisTexture;
        public final ItemStack craftingItem;

        public CoatBaseEntry(String name, int color, ResourceLocation missTexture, ResourceLocation avisTexture, ItemStack ingredient) {
            this.color = color;
            this.name = name;
            this.unlocName = "item." + EnderStuffPlus.MOD_ID + ":rainCoat.base." + name;
            this.desc = unlocName + ".desc";
            this.missTexture = missTexture;
            this.avisTexture = avisTexture;
            this.craftingItem = ingredient;
        }

        public String getUUID() {
            return this.uuid;
        }

        public String getUnlocalizedName() {
            return this.unlocName;
        }

        @Override
        public boolean equals(Object obj) {
            if( obj instanceof String && BASE_LIST.containsKey(obj) ) {
                return this == BASE_LIST.get(obj);
            }

            return super.equals(obj);
        }

        @SideOnly(Side.CLIENT)
        public void preRender() {
        }

        @SideOnly(Side.CLIENT)
        public void postRender() {
        }
    }

    public static class CoatColorEntry
    {
        public final int color;
        private final String name;
        private final String unlocName;
        private String uuid;
        public final ResourceLocation missTexture;
        public final ResourceLocation avisTexture;
        public final ItemStack craftingItem;

        public CoatColorEntry(String name, int color, ResourceLocation missTexture, ResourceLocation avisTexture, ItemStack ingredient) {
            this.color = color;
            this.name = name;
            this.unlocName = "item." + EnderStuffPlus.MOD_ID + ":rainCoat.color." + name;
            this.missTexture = missTexture;
            this.avisTexture = avisTexture;
            this.craftingItem = ingredient;
        }

        public String getUUID() {
            return this.uuid;
        }

        public String getUnlocalizedName() {
            return this.unlocName;
        }

        @Override
        public boolean equals(Object obj) {
            if( obj instanceof String && COLOR_LIST.containsKey(obj) ) {
                return this == COLOR_LIST.get(obj);
            }

            return super.equals(obj);
        }

        @SideOnly(Side.CLIENT)
        public void preRender() {
        }

        @SideOnly(Side.CLIENT)
        public void postRender() {
        }
    }
}
