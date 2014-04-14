package sanandreasp.mods.EnderStuffPlus.registry;

import sanandreasp.core.manpack.helpers.SAPUtils;
import sanandreasp.mods.EnderStuffPlus.item.ItemAvisCompass;
import sanandreasp.mods.EnderStuffPlus.item.ItemCustomEnderPearl;
import sanandreasp.mods.EnderStuffPlus.item.ItemEndHorseArmor;
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
import sanandreasp.mods.EnderStuffPlus.item.tool.ItemTantalPickaxe;
import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry.CfgNames;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;

import net.minecraftforge.common.EnumHelper;

public class ModItemRegistry
{
    public static EnumArmorMaterial ARMOR_NIOBIUM;
    public static EnumToolMaterial TOOL_NIOBIUM;

    public static Item avisArrow;
    public static Item avisCompass;
    public static Item avisFeather;
    public static Item enderFlesh;
    public static ItemEnderPetEgg enderPetEgg;
    public static Item enderPetStaff;
    public static Item enderStick;
    public static Item endHorseArmor;
    public static Item endIngot;
    public static Item endNugget;
    public static Item espPearls;
    public static Item itemNiobDoor;
    public static Item niobAxe;
    public static Item niobBoots;
    public static Item niobBow;
    public static Item niobHelmet;
    public static Item niobHoe;
    public static Item niobLegs;
    public static Item niobPick;
    public static Item niobPlate;
    public static Item niobShears;
    public static Item niobShovel;
    public static Item niobSword;
    public static Item rainCoat;
    public static Item tantalPick;

    public static final void initialize() {
        initMaterials();
        initItems();
        registerItems();
    }

    private static final void initItems() {
        espPearls     = new ItemCustomEnderPearl(ConfigRegistry.itemIDs.get(CfgNames.ESP_PEARLS).intValue() - 256);
        enderFlesh    = new ItemEnderFlesh(ConfigRegistry.itemIDs.get(CfgNames.ENDER_FLESH).intValue() - 256);
        avisFeather   = new Item(ConfigRegistry.itemIDs.get(CfgNames.AVIS_FEATHER).intValue() - 256);
        avisArrow     = new Item(ConfigRegistry.itemIDs.get(CfgNames.AVIS_ARROW).intValue() - 256);
        avisCompass   = new ItemAvisCompass(ConfigRegistry.itemIDs.get(CfgNames.AVIS_COMPASS).intValue() - 256);
        enderPetEgg   = new ItemEnderPetEgg(ConfigRegistry.itemIDs.get(CfgNames.ENDERPET_EGG).intValue() - 256);
        enderPetStaff = new Item(ConfigRegistry.itemIDs.get(CfgNames.ENDERPET_STAFF).intValue() - 256);
        endIngot      = new Item(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_INGOT).intValue() - 256);
        niobBow       = new ItemNiobBow(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_BOW).intValue() - 256);
        rainCoat      = new ItemRaincoat(ConfigRegistry.itemIDs.get(CfgNames.ENDER_RAINCOAT).intValue() - 256);
        niobHelmet    = new ItemNiobArmor(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_HELMET).intValue() - 256, ARMOR_NIOBIUM, 0);
        niobPlate     = new ItemNiobArmor(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_CHESTPLATE).intValue() - 256, ARMOR_NIOBIUM, 1);
        niobLegs      = new ItemNiobArmor(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_LEGGINGS).intValue() - 256, ARMOR_NIOBIUM, 2);
        niobBoots     = new ItemNiobArmor(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_BOOTS).intValue() - 256, ARMOR_NIOBIUM, 3);
        niobPick      = new ItemNiobPickaxe(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_PICKAXE).intValue() - 256, TOOL_NIOBIUM);
        niobShovel    = new ItemNiobShovel(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_SHOVEL).intValue() - 256, TOOL_NIOBIUM);
        niobAxe       = new ItemNiobAxe(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_AXE).intValue() - 256, TOOL_NIOBIUM);
        niobHoe       = new ItemNiobHoe(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_HOE).intValue() - 256, TOOL_NIOBIUM);
        niobSword     = new ItemNiobSword(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_SWORD).intValue() - 256, TOOL_NIOBIUM);
        niobShears    = new ItemNiobShears(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_SHEARS).intValue() - 256);
        endNugget     = new Item(ConfigRegistry.itemIDs.get(CfgNames.NIOBIUM_NUGGET).intValue() - 256);
        itemNiobDoor  = new ItemNiobDoor(ConfigRegistry.itemIDs.get(CfgNames.ENDER_DOOR_ITEM).intValue() - 256);
        enderStick    = new Item(ConfigRegistry.itemIDs.get(CfgNames.ENDER_STICK).intValue() - 256);
        endHorseArmor = new ItemEndHorseArmor(10240);
        tantalPick    = new ItemTantalPickaxe(10241, TOOL_NIOBIUM);

        espPearls    .setUnlocalizedName("esp:espPearls").setCreativeTab(ESPModRegistry.espTab);
        enderFlesh   .setUnlocalizedName("esp:enderFlesh").setTextureName("enderstuffp:enderFlesh").setCreativeTab(ESPModRegistry.espTab);
        avisFeather  .setUnlocalizedName("esp:avisFeather").setTextureName("enderstuffp:avisFeather").setCreativeTab(ESPModRegistry.espTab);
        avisArrow    .setUnlocalizedName("esp:avisArrow").setTextureName("enderstuffp:avisArrow").setCreativeTab(ESPModRegistry.espTab);
        avisCompass  .setUnlocalizedName("esp:avisCompass").setTextureName("enderstuffp:avisCompass").setCreativeTab(ESPModRegistry.espTab);
        enderPetEgg  .setUnlocalizedName("esp:enderPetEgg").setCreativeTab(ESPModRegistry.espTab);
        enderPetStaff.setUnlocalizedName("esp:petStaff").setTextureName("enderstuffp:petStaff").setCreativeTab(ESPModRegistry.espTab).setFull3D();
        endIngot     .setUnlocalizedName("esp:niobIngot").setTextureName("enderstuffp:niobIngot").setCreativeTab(ESPModRegistry.espTab);
        niobBow      .setUnlocalizedName("esp:bowNiob").setTextureName("enderstuffp:bowNiob").setCreativeTab(ESPModRegistry.espTab);
        rainCoat     .setUnlocalizedName("esp:rainCoat").setTextureName("enderstuffp:rainCoat").setCreativeTab(ESPModRegistry.espTabCoats);
        niobHelmet   .setUnlocalizedName("esp:niobHelmet").setTextureName("enderstuffp:niobHelmet").setCreativeTab(ESPModRegistry.espTab);
        niobPlate    .setUnlocalizedName("esp:niobChestplate").setTextureName("enderstuffp:niobChestplate").setCreativeTab(ESPModRegistry.espTab);
        niobLegs     .setUnlocalizedName("esp:niobLeggings").setTextureName("enderstuffp:niobLeggings").setCreativeTab(ESPModRegistry.espTab);
        niobBoots    .setUnlocalizedName("esp:niobBoots").setTextureName("enderstuffp:niobBoots").setCreativeTab(ESPModRegistry.espTab);
        niobPick     .setUnlocalizedName("esp:niobPick").setTextureName("enderstuffp:niobPick").setCreativeTab(ESPModRegistry.espTab);
        niobShovel   .setUnlocalizedName("esp:niobShovel").setTextureName("enderstuffp:niobShovel").setCreativeTab(ESPModRegistry.espTab);
        niobAxe      .setUnlocalizedName("esp:niobAxe").setTextureName("enderstuffp:niobAxe").setCreativeTab(ESPModRegistry.espTab);
        niobHoe      .setUnlocalizedName("esp:niobHoe").setTextureName("enderstuffp:niobHoe").setCreativeTab(ESPModRegistry.espTab);
        niobSword    .setUnlocalizedName("esp:niobSword").setTextureName("enderstuffp:niobSword").setCreativeTab(ESPModRegistry.espTab);
        niobShears   .setUnlocalizedName("esp:niobShears").setTextureName("enderstuffp:niobShears").setCreativeTab(ESPModRegistry.espTab);
        endNugget    .setUnlocalizedName("esp:niobNugget").setTextureName("enderstuffp:niobNugget").setCreativeTab(ESPModRegistry.espTab);
        itemNiobDoor .setUnlocalizedName("esp:doorNiob").setTextureName("enderstuffp:doorNiob").setCreativeTab(ESPModRegistry.espTab);
        enderStick   .setUnlocalizedName("esp:enderStick").setTextureName("enderstuffp:enderStick").setCreativeTab(ESPModRegistry.espTab);
        endHorseArmor.setUnlocalizedName("esp:enderHorseArmor").setTextureName("enderstuffp:enderStick").setCreativeTab(ESPModRegistry.espTab);
        tantalPick   .setUnlocalizedName("esp:tantalPick").setTextureName("enderstuffp:tantalPick").setCreativeTab(ESPModRegistry.espTab);
    }

    private static final void initMaterials() {
        TOOL_NIOBIUM = EnumHelper.addToolMaterial("NIOBIUM",
                                                  EnumToolMaterial.IRON.getHarvestLevel(),
                                                  EnumToolMaterial.IRON.getMaxUses(),
                                                  EnumToolMaterial.IRON.getEfficiencyOnProperMaterial(),
                                                  EnumToolMaterial.IRON.getDamageVsEntity(),
                                                  EnumToolMaterial.GOLD.getEnchantability());

        ARMOR_NIOBIUM = EnumHelper.addArmorMaterial("NIOBIUM",
                                                    SAPUtils.getMaxDmgFactorETM(),
                                                    new int[] { EnumArmorMaterial.IRON.getDamageReductionAmount(0),
                                                                EnumArmorMaterial.IRON.getDamageReductionAmount(1),
                                                                EnumArmorMaterial.IRON.getDamageReductionAmount(2),
                                                                EnumArmorMaterial.IRON.getDamageReductionAmount(3) },
                                                    EnumArmorMaterial.GOLD.getEnchantability());
    }

    private static final void registerItems() {
        SAPUtils.registerItems("enderstuffp:item",
                               espPearls, avisFeather, avisArrow, avisCompass, enderPetEgg, enderPetStaff, endIngot, niobBow, niobHelmet,
                               niobPlate, niobLegs, niobBoots, niobPick, niobShovel, niobAxe, niobHoe, niobSword, niobShears, enderFlesh,
                               rainCoat, itemNiobDoor, endNugget, enderStick, endHorseArmor);
    }
}
