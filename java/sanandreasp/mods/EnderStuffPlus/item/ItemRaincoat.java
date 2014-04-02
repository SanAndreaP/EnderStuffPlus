package sanandreasp.mods.EnderStuffPlus.item;

import java.util.List;
import java.util.Map;

import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import com.google.common.collect.Maps;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRaincoat
    extends Item
{
    public static class CoatBaseEntry
    {
        public final int color;
        public final String desc;
        public final String name;
        public final ResourceLocation missTexture;
        public final ResourceLocation avisTexture;

        public CoatBaseEntry(String baseName, int itemColor, String baseDesc, ResourceLocation baseMissTexture, ResourceLocation baseAvisTexture) {
            this.color = itemColor;
            this.desc = baseDesc;
            this.name = baseName;
            this.missTexture = baseMissTexture;
            this.avisTexture = baseAvisTexture;
        }
    }

    public static class CoatColorEntry
    {
        public final int color;
        public final String name;
        public final ResourceLocation missTexture;
        public final ResourceLocation avisTexture;

        public CoatColorEntry(String colorName, int itemColor, ResourceLocation colorMissTexture, ResourceLocation colorAvisTexture) {
            this.color = itemColor;
            this.name = colorName;
            this.missTexture = colorMissTexture;
            this.avisTexture = colorAvisTexture;
        }
    }

    public static final Map<String, CoatBaseEntry> BASE_LIST = Maps.newLinkedHashMap();
    public static final Map<String, CoatColorEntry> COLOR_LIST = Maps.newLinkedHashMap();

    @SideOnly(Side.CLIENT)
    private Icon iconBase;
    @SideOnly(Side.CLIENT)
    private Icon iconOver;

    static {
        addRaincoatBase(ESPModRegistry.MOD_ID, 0, "item.esp:rainCoat.base.gold",     "item.esp:rainCoat.base.gold.desc",     0xFFC545, "textures/entities/enderMiss_cape/stripes_gold.png", "textures/entities/enderAvis_cape/stripes_gold.png");
        addRaincoatBase(ESPModRegistry.MOD_ID, 1, "item.esp:rainCoat.base.niobium",  "item.esp:rainCoat.base.niobium.desc",  0x3C408B, "textures/entities/enderMiss_cape/stripes_niob.png", "textures/entities/enderAvis_cape/stripes_niob.png");
        addRaincoatBase(ESPModRegistry.MOD_ID, 2, "item.esp:rainCoat.base.iron",     "item.esp:rainCoat.base.iron.desc",     0xA9A9A9, "textures/entities/enderMiss_cape/stripes_iron.png", "textures/entities/enderAvis_cape/stripes_iron.png");
        addRaincoatBase(ESPModRegistry.MOD_ID, 3, "item.esp:rainCoat.base.redstone", "item.esp:rainCoat.base.redstone.desc", 0xC62127, "textures/entities/enderMiss_cape/stripes_reds.png", "textures/entities/enderAvis_cape/stripes_reds.png");
        addRaincoatBase(ESPModRegistry.MOD_ID, 4, "item.esp:rainCoat.base.obsidian", "item.esp:rainCoat.base.obsidian.desc", 0x1C1C22, "textures/entities/enderMiss_cape/stripes_obsd.png", "textures/entities/enderAvis_cape/stripes_obsd.png");
        addRaincoatBase(ESPModRegistry.MOD_ID, 5, "item.esp:rainCoat.base.tantalum", "item.esp:rainCoat.base.tantalum.desc", 0xFF81E8, "textures/entities/enderMiss_cape/stripes_tant.png", "textures/entities/enderAvis_cape/stripes_tant.png");

        addRaincoatColor(ESPModRegistry.MOD_ID,  0, "item.esp:rainCoat.color.black",       0x1A1515, "textures/entities/enderMiss_cape/black.png",     "textures/entities/enderAvis_cape/black.png");
        addRaincoatColor(ESPModRegistry.MOD_ID,  1, "item.esp:rainCoat.color.red",         0xCF3B37, "textures/entities/enderMiss_cape/red.png",       "textures/entities/enderAvis_cape/red.png");
        addRaincoatColor(ESPModRegistry.MOD_ID,  2, "item.esp:rainCoat.color.green",       0x3D591B, "textures/entities/enderMiss_cape/green.png",     "textures/entities/enderAvis_cape/green.png");
        addRaincoatColor(ESPModRegistry.MOD_ID,  3, "item.esp:rainCoat.color.brown",       0x663A20, "textures/entities/enderMiss_cape/brown.png",     "textures/entities/enderAvis_cape/brown.png");
        addRaincoatColor(ESPModRegistry.MOD_ID,  4, "item.esp:rainCoat.color.blue",        0x3343C6, "textures/entities/enderMiss_cape/blue.png",      "textures/entities/enderAvis_cape/blue.png");
        addRaincoatColor(ESPModRegistry.MOD_ID,  5, "item.esp:rainCoat.color.purple",      0xB54AE7, "textures/entities/enderMiss_cape/purple.png",    "textures/entities/enderAvis_cape/purple.png");
        addRaincoatColor(ESPModRegistry.MOD_ID,  6, "item.esp:rainCoat.color.cyan",        0x349EC1, "textures/entities/enderMiss_cape/cyan.png",      "textures/entities/enderAvis_cape/cyan.png");
        addRaincoatColor(ESPModRegistry.MOD_ID,  7, "item.esp:rainCoat.color.silver",      0xD3D8D8, "textures/entities/enderMiss_cape/lightgray.png", "textures/entities/enderAvis_cape/lightgray.png");
        addRaincoatColor(ESPModRegistry.MOD_ID,  8, "item.esp:rainCoat.color.gray",        0x4D4D4D, "textures/entities/enderMiss_cape/gray.png",      "textures/entities/enderAvis_cape/gray.png");
        addRaincoatColor(ESPModRegistry.MOD_ID,  9, "item.esp:rainCoat.color.pink",        0xF4BBD1, "textures/entities/enderMiss_cape/pink.png",      "textures/entities/enderAvis_cape/pink.png");
        addRaincoatColor(ESPModRegistry.MOD_ID, 10, "item.esp:rainCoat.color.lime",        0x50E243, "textures/entities/enderMiss_cape/lime.png",      "textures/entities/enderAvis_cape/lime.png");
        addRaincoatColor(ESPModRegistry.MOD_ID, 11, "item.esp:rainCoat.color.yellow",      0xE4DC2A, "textures/entities/enderMiss_cape/yellow.png",    "textures/entities/enderAvis_cape/yellow.png");
        addRaincoatColor(ESPModRegistry.MOD_ID, 12, "item.esp:rainCoat.color.lightBlue",   0x98C2F1, "textures/entities/enderMiss_cape/lightblue.png", "textures/entities/enderAvis_cape/lightblue.png");
        addRaincoatColor(ESPModRegistry.MOD_ID, 13, "item.esp:rainCoat.color.magenta",     0xE66AEB, "textures/entities/enderMiss_cape/magenta.png",   "textures/entities/enderAvis_cape/magenta.png");
        addRaincoatColor(ESPModRegistry.MOD_ID, 14, "item.esp:rainCoat.color.orange",      0xF7B24C, "textures/entities/enderMiss_cape/orange.png",    "textures/entities/enderAvis_cape/orange.png");
        addRaincoatColor(ESPModRegistry.MOD_ID, 15, "item.esp:rainCoat.color.white",       0xF8F8F8, "textures/entities/enderMiss_cape/white.png",     "textures/entities/enderAvis_cape/white.png");
        addRaincoatColor(ESPModRegistry.MOD_ID, 16, "item.esp:rainCoat.color.gold",        0xC5B600, "textures/entities/enderMiss_cape/gold.png",      "textures/entities/enderAvis_cape/gold.png");
        addRaincoatColor(ESPModRegistry.MOD_ID, 17, "item.esp:rainCoat.color.niobium",     0x141E61, "textures/entities/enderMiss_cape/niob.png",      "textures/entities/enderAvis_cape/niob.png");
        addRaincoatColor(ESPModRegistry.MOD_ID, 18, "item.esp:rainCoat.color.transparent", 0xCDCDCD, "textures/entities/enderMiss_cape/transp.png",    "textures/entities/enderAvis_cape/transp.png");
    }

    public ItemRaincoat(int id) {
        super(id);
        this.setMaxDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addInformation(ItemStack stack, EntityPlayer player, List infos, boolean isAdvancedInfo) {
        if( stack.hasTagCompound() ) {
            String base = stack.getTagCompound().getString("base");
            if( BASE_LIST.containsKey(base) ) {
                CoatBaseEntry entry = BASE_LIST.get(base);
                infos.add("\247o" + CommonUsedStuff.getTranslated(entry.name));
                String[] split = entry.desc.split("\n");
                for( String effect : split ) {
                    infos.add("\2473" + effect);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if( stack.hasTagCompound() ) {
            String base = stack.getTagCompound().getString("base");
            String color = stack.getTagCompound().getString("color");
            if( pass == 0 && COLOR_LIST.containsKey(color) ) {
                return COLOR_LIST.get(color).color;
            } else if( pass > 0 && BASE_LIST.containsKey(base) ) {
                return BASE_LIST.get(base).color;
            }
        }

        return super.getColorFromItemStack(stack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamageForRenderPass(int damage, int pass) {
        return pass == 0 ? this.iconBase : this.iconOver;
    }

    @Override
    public String getItemDisplayName(ItemStack par1ItemStack) {
        if( par1ItemStack.getTagCompound() != null ) {
            String clr = par1ItemStack.getTagCompound().getString("color");
            if( COLOR_LIST.containsKey(clr) ) {
                return String.format(super.getItemDisplayName(par1ItemStack), CommonUsedStuff.getTranslated(COLOR_LIST.get(clr).name));
            }
        }
        return String.format(super.getItemDisplayName(par1ItemStack), "[UNKNOWN]");
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for( String base : BASE_LIST.keySet() ) {
            for( String color : COLOR_LIST.keySet() ) {
                NBTTagCompound nbt = new NBTTagCompound();
                ItemStack is = new ItemStack(this, 1, 0);
                nbt.setString("base", base);
                nbt.setString("color", color);
                is.setTagCompound(nbt);
                par3List.add(is);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = this.iconBase = par1IconRegister.registerIcon("enderstuffp:rainCoatBase");
        this.iconOver = par1IconRegister.registerIcon("enderstuffp:rainCoatStripes");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public static void addRaincoatBase(String modid, int baseId, String baseName, String baseDesc, int itemColor, String missTexture,
                                       String avisTexture) {
        ResourceLocation miss = new ResourceLocation(modid, missTexture);
        ResourceLocation avis = new ResourceLocation(modid, avisTexture);
        BASE_LIST.put(modid + String.format("_%03d", baseId), new CoatBaseEntry(baseName, itemColor, baseDesc, miss, avis));
    }

    public static void addRaincoatColor(String modid, int colorId, String colorName, int itemColor, String missTexture,
                                        String avisTexture) {
        ResourceLocation miss = new ResourceLocation(modid, missTexture);
        ResourceLocation avis = new ResourceLocation(modid, avisTexture);
        COLOR_LIST.put(modid + String.format("_%03d", colorId), new CoatColorEntry(colorName, itemColor, miss, avis));
    }
}
