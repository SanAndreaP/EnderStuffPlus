package de.sanandrew.mods.enderstuffplus.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;

import net.minecraftforge.common.util.EnumHelper;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.enderstuffplus.item.ItemAvisCompass;
import de.sanandrew.mods.enderstuffplus.item.ItemCustomEnderPearl;
import de.sanandrew.mods.enderstuffplus.item.ItemEndHorseArmor;
import de.sanandrew.mods.enderstuffplus.item.ItemEnderFlesh;
import de.sanandrew.mods.enderstuffplus.item.ItemEnderPetEgg;
import de.sanandrew.mods.enderstuffplus.item.ItemNiobArmor;
import de.sanandrew.mods.enderstuffplus.item.ItemNiobDoor;
import de.sanandrew.mods.enderstuffplus.item.ItemRaincoat;
import de.sanandrew.mods.enderstuffplus.item.tool.ItemAhrahSword;
import de.sanandrew.mods.enderstuffplus.item.tool.ItemNiobAxe;
import de.sanandrew.mods.enderstuffplus.item.tool.ItemNiobBow;
import de.sanandrew.mods.enderstuffplus.item.tool.ItemNiobHoe;
import de.sanandrew.mods.enderstuffplus.item.tool.ItemNiobPickaxe;
import de.sanandrew.mods.enderstuffplus.item.tool.ItemNiobShears;
import de.sanandrew.mods.enderstuffplus.item.tool.ItemNiobShovel;
import de.sanandrew.mods.enderstuffplus.item.tool.ItemNiobSword;
import de.sanandrew.mods.enderstuffplus.item.tool.ItemTantalPickaxe;

public class ModItemRegistry
{
    public static ItemArmor.ArmorMaterial armorNiobium;
    public static Item.ToolMaterial toolNiobium;

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
    //FIXME unfinished!
    public static Item tantalPick;
    public static Item ahrahSword;


    public static void initialize() {
        initMaterials();
        initItems();
        registerItems();
    }

    private static void initItems() {
        espPearls     = new ItemCustomEnderPearl();
        enderFlesh    = new ItemEnderFlesh();
        avisFeather   = new Item();
        avisArrow     = new Item();
        avisCompass   = new ItemAvisCompass();
        enderPetEgg   = new ItemEnderPetEgg();
        enderPetStaff = new Item();
        endIngot      = new Item();
        niobBow       = new ItemNiobBow();
        rainCoat      = new ItemRaincoat();
        niobHelmet    = new ItemNiobArmor(armorNiobium, 0);
        niobPlate     = new ItemNiobArmor(armorNiobium, 1);
        niobLegs      = new ItemNiobArmor(armorNiobium, 2);
        niobBoots     = new ItemNiobArmor(armorNiobium, 3);
        niobPick      = new ItemNiobPickaxe(toolNiobium);
        niobShovel    = new ItemNiobShovel(toolNiobium);
        niobAxe       = new ItemNiobAxe(toolNiobium);
        niobHoe       = new ItemNiobHoe(toolNiobium);
        niobSword     = new ItemNiobSword(toolNiobium);
        niobShears    = new ItemNiobShears();
        endNugget     = new Item();
        itemNiobDoor  = new ItemNiobDoor();
        enderStick    = new Item();
        endHorseArmor = new ItemEndHorseArmor();
        tantalPick    = new ItemTantalPickaxe(toolNiobium);
        ahrahSword    = new ItemAhrahSword(toolNiobium);

        espPearls    .setUnlocalizedName(ESPModRegistry.MOD_ID + ":espPearls").setCreativeTab(ESPModRegistry.espTab);
        enderFlesh   .setUnlocalizedName(ESPModRegistry.MOD_ID + ":enderFlesh").setTextureName(ESPModRegistry.MOD_ID + ":enderFlesh").setCreativeTab(ESPModRegistry.espTab);
        avisFeather  .setUnlocalizedName(ESPModRegistry.MOD_ID + ":avisFeather").setTextureName(ESPModRegistry.MOD_ID + ":avisFeather").setCreativeTab(ESPModRegistry.espTab);
        avisArrow    .setUnlocalizedName(ESPModRegistry.MOD_ID + ":avisArrow").setTextureName(ESPModRegistry.MOD_ID + ":avisArrow").setCreativeTab(ESPModRegistry.espTab);
        avisCompass  .setUnlocalizedName(ESPModRegistry.MOD_ID + ":avisCompass").setTextureName(ESPModRegistry.MOD_ID + ":compass").setCreativeTab(ESPModRegistry.espTab);
        enderPetEgg  .setUnlocalizedName(ESPModRegistry.MOD_ID + ":enderPetEgg").setCreativeTab(ESPModRegistry.espTab);
        enderPetStaff.setUnlocalizedName(ESPModRegistry.MOD_ID + ":petStaff").setTextureName(ESPModRegistry.MOD_ID + ":petStaff").setCreativeTab(ESPModRegistry.espTab).setFull3D();
        endIngot     .setUnlocalizedName(ESPModRegistry.MOD_ID + ":niobIngot").setTextureName(ESPModRegistry.MOD_ID + ":niobIngot").setCreativeTab(ESPModRegistry.espTab);
        niobBow      .setUnlocalizedName(ESPModRegistry.MOD_ID + ":bowNiob").setTextureName(ESPModRegistry.MOD_ID + ":bowNiob").setCreativeTab(ESPModRegistry.espTab);
        rainCoat     .setUnlocalizedName(ESPModRegistry.MOD_ID + ":rainCoat").setTextureName(ESPModRegistry.MOD_ID + ":rainCoat").setCreativeTab(ESPModRegistry.espTabCoats);
        niobHelmet   .setUnlocalizedName(ESPModRegistry.MOD_ID + ":niobHelmet").setTextureName(ESPModRegistry.MOD_ID + ":niobHelmet").setCreativeTab(ESPModRegistry.espTab);
        niobPlate    .setUnlocalizedName(ESPModRegistry.MOD_ID + ":niobChestplate").setTextureName(ESPModRegistry.MOD_ID + ":niobChestplate").setCreativeTab(ESPModRegistry.espTab);
        niobLegs     .setUnlocalizedName(ESPModRegistry.MOD_ID + ":niobLeggings").setTextureName(ESPModRegistry.MOD_ID + ":niobLeggings").setCreativeTab(ESPModRegistry.espTab);
        niobBoots    .setUnlocalizedName(ESPModRegistry.MOD_ID + ":niobBoots").setTextureName(ESPModRegistry.MOD_ID + ":niobBoots").setCreativeTab(ESPModRegistry.espTab);
        niobPick     .setUnlocalizedName(ESPModRegistry.MOD_ID + ":niobPick").setTextureName(ESPModRegistry.MOD_ID + ":niobPick").setCreativeTab(ESPModRegistry.espTab);
        niobShovel   .setUnlocalizedName(ESPModRegistry.MOD_ID + ":niobShovel").setTextureName(ESPModRegistry.MOD_ID + ":niobShovel").setCreativeTab(ESPModRegistry.espTab);
        niobAxe      .setUnlocalizedName(ESPModRegistry.MOD_ID + ":niobAxe").setTextureName(ESPModRegistry.MOD_ID + ":niobAxe").setCreativeTab(ESPModRegistry.espTab);
        niobHoe      .setUnlocalizedName(ESPModRegistry.MOD_ID + ":niobHoe").setTextureName(ESPModRegistry.MOD_ID + ":niobHoe").setCreativeTab(ESPModRegistry.espTab);
        niobSword    .setUnlocalizedName(ESPModRegistry.MOD_ID + ":niobSword").setTextureName(ESPModRegistry.MOD_ID + ":niobSword").setCreativeTab(ESPModRegistry.espTab);
        niobShears   .setUnlocalizedName(ESPModRegistry.MOD_ID + ":niobShears").setTextureName(ESPModRegistry.MOD_ID + ":niobShears").setCreativeTab(ESPModRegistry.espTab);
        endNugget    .setUnlocalizedName(ESPModRegistry.MOD_ID + ":niobNugget").setTextureName(ESPModRegistry.MOD_ID + ":niobNugget").setCreativeTab(ESPModRegistry.espTab);
        itemNiobDoor .setUnlocalizedName(ESPModRegistry.MOD_ID + ":doorNiob").setTextureName(ESPModRegistry.MOD_ID + ":doorNiob").setCreativeTab(ESPModRegistry.espTab);
        enderStick   .setUnlocalizedName(ESPModRegistry.MOD_ID + ":enderStick").setTextureName(ESPModRegistry.MOD_ID + ":enderStick").setCreativeTab(ESPModRegistry.espTab);
        endHorseArmor.setUnlocalizedName(ESPModRegistry.MOD_ID + ":enderHorseArmor").setTextureName(ESPModRegistry.MOD_ID + ":enderStick").setCreativeTab(ESPModRegistry.espTab);
        tantalPick   .setUnlocalizedName(ESPModRegistry.MOD_ID + ":tantalPick").setTextureName(ESPModRegistry.MOD_ID + ":tantalPick").setCreativeTab(ESPModRegistry.espTab);
        ahrahSword   .setUnlocalizedName(ESPModRegistry.MOD_ID + ":ahrahSword").setTextureName(ESPModRegistry.MOD_ID + ":ahrah").setCreativeTab(ESPModRegistry.espTab);
    }

    private static void initMaterials() {
        toolNiobium = EnumHelper.addToolMaterial("NIOBIUM",
                                                  ToolMaterial.IRON.getHarvestLevel(),
                                                  ToolMaterial.IRON.getMaxUses(),
                                                  ToolMaterial.IRON.getEfficiencyOnProperMaterial(),
                                                  ToolMaterial.IRON.getDamageVsEntity(),
                                                  ToolMaterial.GOLD.getEnchantability());

        armorNiobium = EnumHelper.addArmorMaterial("NIOBIUM",
                                                    SAPUtils.getMaxDmgFactorAM(ArmorMaterial.IRON),
                                                    new int[] { ArmorMaterial.IRON.getDamageReductionAmount(0),
                                                                ArmorMaterial.IRON.getDamageReductionAmount(1),
                                                                ArmorMaterial.IRON.getDamageReductionAmount(2),
                                                                ArmorMaterial.IRON.getDamageReductionAmount(3) },
                                                    ArmorMaterial.GOLD.getEnchantability());
    }

    private static void registerItems() {
        SAPUtils.registerItems(espPearls, avisFeather, avisArrow, avisCompass, enderPetEgg, enderPetStaff, endIngot, niobBow, niobHelmet,
                               niobPlate, niobLegs, niobBoots, niobPick, niobShovel, niobAxe, niobHoe, niobSword, niobShears, enderFlesh,
                               rainCoat, itemNiobDoor, endNugget, enderStick, endHorseArmor);
    }
}
