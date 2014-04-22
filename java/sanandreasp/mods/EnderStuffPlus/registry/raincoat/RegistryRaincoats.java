package sanandreasp.mods.EnderStuffPlus.registry.raincoat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ModBlockRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RegistryRaincoats
{
    public static final Map<String, CoatBaseEntry> BASE_LIST = Maps.newLinkedHashMap();
    public static final Map<String, CoatColorEntry> COLOR_LIST = Maps.newLinkedHashMap();

    public static final List<ItemStack> BASE_STACKS = new ArrayList<ItemStack>();
    public static final List<ItemStack> COLOR_STACKS = new ArrayList<ItemStack>();

    public static final CoatBaseEntry NULL_BASE = new CoatBaseEntry("NULL", 0, "NULL", null, null, null);
    public static final CoatColorEntry NULL_COLOR = new CoatColorEntry("NULL", 0, null, null, null);

    public static void initialize() {
        addBase(ESPModRegistry.MOD_ID, 0, "item.esp:rainCoat.base.gold",     "item.esp:rainCoat.base.gold.desc",     0xFFC545, "textures/entity/enderMiss_cape/stripes_gold.png", "textures/entity/enderAvis_cape/stripes_gold.png", new ItemStack(Block.blockGold));
        addBase(ESPModRegistry.MOD_ID, 1, "item.esp:rainCoat.base.niobium",  "item.esp:rainCoat.base.niobium.desc",  0x3C408B, "textures/entity/enderMiss_cape/stripes_niob.png", "textures/entity/enderAvis_cape/stripes_niob.png", new ItemStack(ModBlockRegistry.endBlock, 1, 0));
        addBase(ESPModRegistry.MOD_ID, 2, "item.esp:rainCoat.base.iron",     "item.esp:rainCoat.base.iron.desc",     0xA9A9A9, "textures/entity/enderMiss_cape/stripes_iron.png", "textures/entity/enderAvis_cape/stripes_iron.png", new ItemStack(Block.blockIron));
        addBase(ESPModRegistry.MOD_ID, 3, "item.esp:rainCoat.base.redstone", "item.esp:rainCoat.base.redstone.desc", 0xC62127, "textures/entity/enderMiss_cape/stripes_reds.png", "textures/entity/enderAvis_cape/stripes_reds.png", new ItemStack(Block.blockRedstone));
        addBase(ESPModRegistry.MOD_ID, 4, "item.esp:rainCoat.base.obsidian", "item.esp:rainCoat.base.obsidian.desc", 0x1C1C22, "textures/entity/enderMiss_cape/stripes_obsd.png", "textures/entity/enderAvis_cape/stripes_obsd.png", new ItemStack(Block.obsidian));
        addBase(ESPModRegistry.MOD_ID, 5, "item.esp:rainCoat.base.tantalum", "item.esp:rainCoat.base.tantalum.desc", 0xFF81E8, "textures/entity/enderMiss_cape/stripes_tant.png", "textures/entity/enderAvis_cape/stripes_tant.png", new ItemStack(ModBlockRegistry.endBlock, 1, 1));

        addColor(ESPModRegistry.MOD_ID,  0, "item.esp:rainCoat.color.black",       0x1A1515, "textures/entity/enderMiss_cape/black.png",     "textures/entity/enderAvis_cape/black.png",     new ItemStack(Block.cloth, 1, 15));
        addColor(ESPModRegistry.MOD_ID,  1, "item.esp:rainCoat.color.red",         0xCF3B37, "textures/entity/enderMiss_cape/red.png",       "textures/entity/enderAvis_cape/red.png",       new ItemStack(Block.cloth, 1, 14));
        addColor(ESPModRegistry.MOD_ID,  2, "item.esp:rainCoat.color.green",       0x3D591B, "textures/entity/enderMiss_cape/green.png",     "textures/entity/enderAvis_cape/green.png",     new ItemStack(Block.cloth, 1, 13));
        addColor(ESPModRegistry.MOD_ID,  3, "item.esp:rainCoat.color.brown",       0x663A20, "textures/entity/enderMiss_cape/brown.png",     "textures/entity/enderAvis_cape/brown.png",     new ItemStack(Block.cloth, 1, 12));
        addColor(ESPModRegistry.MOD_ID,  4, "item.esp:rainCoat.color.blue",        0x3343C6, "textures/entity/enderMiss_cape/blue.png",      "textures/entity/enderAvis_cape/blue.png",      new ItemStack(Block.cloth, 1, 11));
        addColor(ESPModRegistry.MOD_ID,  5, "item.esp:rainCoat.color.purple",      0xB54AE7, "textures/entity/enderMiss_cape/purple.png",    "textures/entity/enderAvis_cape/purple.png",    new ItemStack(Block.cloth, 1, 10));
        addColor(ESPModRegistry.MOD_ID,  6, "item.esp:rainCoat.color.cyan",        0x349EC1, "textures/entity/enderMiss_cape/cyan.png",      "textures/entity/enderAvis_cape/cyan.png",      new ItemStack(Block.cloth, 1, 9));
        addColor(ESPModRegistry.MOD_ID,  7, "item.esp:rainCoat.color.silver",      0xD3D8D8, "textures/entity/enderMiss_cape/lightgray.png", "textures/entity/enderAvis_cape/lightgray.png", new ItemStack(Block.cloth, 1, 8));
        addColor(ESPModRegistry.MOD_ID,  8, "item.esp:rainCoat.color.gray",        0x4D4D4D, "textures/entity/enderMiss_cape/gray.png",      "textures/entity/enderAvis_cape/gray.png",      new ItemStack(Block.cloth, 1, 7));
        addColor(ESPModRegistry.MOD_ID,  9, "item.esp:rainCoat.color.pink",        0xF4BBD1, "textures/entity/enderMiss_cape/pink.png",      "textures/entity/enderAvis_cape/pink.png",      new ItemStack(Block.cloth, 1, 6));
        addColor(ESPModRegistry.MOD_ID, 10, "item.esp:rainCoat.color.lime",        0x50E243, "textures/entity/enderMiss_cape/lime.png",      "textures/entity/enderAvis_cape/lime.png",      new ItemStack(Block.cloth, 1, 5));
        addColor(ESPModRegistry.MOD_ID, 11, "item.esp:rainCoat.color.yellow",      0xE4DC2A, "textures/entity/enderMiss_cape/yellow.png",    "textures/entity/enderAvis_cape/yellow.png",    new ItemStack(Block.cloth, 1, 4));
        addColor(ESPModRegistry.MOD_ID, 12, "item.esp:rainCoat.color.lightBlue",   0x98C2F1, "textures/entity/enderMiss_cape/lightblue.png", "textures/entity/enderAvis_cape/lightblue.png", new ItemStack(Block.cloth, 1, 3));
        addColor(ESPModRegistry.MOD_ID, 13, "item.esp:rainCoat.color.magenta",     0xE66AEB, "textures/entity/enderMiss_cape/magenta.png",   "textures/entity/enderAvis_cape/magenta.png",   new ItemStack(Block.cloth, 1, 2));
        addColor(ESPModRegistry.MOD_ID, 14, "item.esp:rainCoat.color.orange",      0xF7B24C, "textures/entity/enderMiss_cape/orange.png",    "textures/entity/enderAvis_cape/orange.png",    new ItemStack(Block.cloth, 1, 1));
        addColor(ESPModRegistry.MOD_ID, 15, "item.esp:rainCoat.color.white",       0xF8F8F8, "textures/entity/enderMiss_cape/white.png",     "textures/entity/enderAvis_cape/white.png",     new ItemStack(Block.cloth, 1, 0));
        addColor(ESPModRegistry.MOD_ID, 16, "item.esp:rainCoat.color.gold",        0xC5B600, "textures/entity/enderMiss_cape/gold.png",      "textures/entity/enderAvis_cape/gold.png",      new ItemStack(Item.goldNugget));
        addColor(ESPModRegistry.MOD_ID, 17, "item.esp:rainCoat.color.niobium",     0x141E61, "textures/entity/enderMiss_cape/niob.png",      "textures/entity/enderAvis_cape/niob.png",      new ItemStack(ModItemRegistry.endNugget, 1, 0));

        addSpecialColor(ESPModRegistry.MOD_ID, 18,
                        new CoatColorTransparent("item.esp:rainCoat.color.transparent", 0xCDCDCD,
                                                 new ResourceLocation("enderstuffp", "textures/entity/enderMiss_cape/transp.png"),
                                                 new ResourceLocation("enderstuffp", "textures/entity/enderAvis_cape/transp.png"),
                                                 new ItemStack(Block.glass)));
    }

    public static void addBase(String modid, int baseId, String baseName, String baseDesc, int itemColor, String missTexture,
                                       String avisTexture, ItemStack craftingIngredient) {
        ResourceLocation miss = new ResourceLocation(modid, missTexture);
        ResourceLocation avis = new ResourceLocation(modid, avisTexture);
        addSpecialBase(modid, baseId, new CoatBaseEntry(baseName, itemColor, baseDesc, miss, avis, craftingIngredient));
    }

    public static void addColor(String modid, int colorId, String colorName, int itemColor, String missTexture,
                                        String avisTexture, ItemStack craftingIngredient) {
        ResourceLocation miss = new ResourceLocation(modid, missTexture);
        ResourceLocation avis = new ResourceLocation(modid, avisTexture);
        addSpecialColor(modid, colorId, new CoatColorEntry(colorName, itemColor, miss, avis, craftingIngredient));
    }

    public static void addSpecialColor(String modid, int colorId, CoatColorEntry entry) {
        ItemStack craftingIngredient = entry.craftingItem.copy();
        COLOR_LIST.put(modid + String.format("_%03d", colorId), entry);
        COLOR_STACKS.add(craftingIngredient);
    }

    public static void addSpecialBase(String modid, int baseId, CoatBaseEntry entry) {
        ItemStack craftingIngredient = entry.craftingItem.copy();
        BASE_LIST.put(modid + String.format("_%03d", baseId), entry);
        BASE_STACKS.add(craftingIngredient);
    }

    public static CoatBaseEntry getCoatBase(String baseName) {
        if( BASE_LIST.containsKey(baseName) ) {
            return BASE_LIST.get(baseName);
        }

        return NULL_BASE;
    }

    public static CoatColorEntry getCoatColor(String baseName) {
        if( COLOR_LIST.containsKey(baseName) ) {
            return COLOR_LIST.get(baseName);
        }

        return NULL_COLOR;
    }

    public static class CoatBaseEntry
    {
        public final int color;
        public final String desc;
        public final String name;
        public final ResourceLocation missTexture;
        public final ResourceLocation avisTexture;
        public final ItemStack craftingItem;

        public CoatBaseEntry(String baseName, int itemColor, String baseDesc, ResourceLocation baseMissTexture,
                             ResourceLocation baseAvisTexture, ItemStack craftingIngredient) {
            this.color = itemColor;
            this.desc = baseDesc;
            this.name = baseName;
            this.missTexture = baseMissTexture;
            this.avisTexture = baseAvisTexture;
            this.craftingItem = craftingIngredient;
        }

        @Override
        public boolean equals(Object obj) {
            if( obj instanceof String && BASE_LIST.containsKey(obj) ) {
                return this == BASE_LIST.get(obj);
            }

            return super.equals(obj);
        }

        @SideOnly(Side.CLIENT)
        public void preRender() { }

        @SideOnly(Side.CLIENT)
        public void postRender() { }
    }

    public static class CoatColorEntry
    {
        public final int color;
        public final String name;
        public final ResourceLocation missTexture;
        public final ResourceLocation avisTexture;
        public final ItemStack craftingItem;

        public CoatColorEntry(String colorName, int itemColor, ResourceLocation colorMissTexture, ResourceLocation colorAvisTexture,
                              ItemStack craftingIngredient) {
            this.color = itemColor;
            this.name = colorName;
            this.missTexture = colorMissTexture;
            this.avisTexture = colorAvisTexture;
            this.craftingItem = craftingIngredient;
        }

        @Override
        public boolean equals(Object obj) {
            if( obj instanceof String && COLOR_LIST.containsKey(obj) ) {
                return this == COLOR_LIST.get(obj);
            }

            return super.equals(obj);
        }

        @SideOnly(Side.CLIENT)
        public void preRender() { }

        @SideOnly(Side.CLIENT)
        public void postRender() { }
    }
}
