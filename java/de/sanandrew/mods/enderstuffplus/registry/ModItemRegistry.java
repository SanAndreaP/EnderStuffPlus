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
    public static ItemArmor.ArmorMaterial ARMOR_NIOBIUM;
    public static Item.ToolMaterial TOOL_NIOBIUM;

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
    //TODO unfinished!
    public static Item tantalPick;
    public static Item ahrahSword;


    public static final void initialize() {
        initMaterials();
        initItems();
        registerItems();
    }

    private static final void initItems() {
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
        niobHelmet    = new ItemNiobArmor(ARMOR_NIOBIUM, 0);
        niobPlate     = new ItemNiobArmor(ARMOR_NIOBIUM, 1);
        niobLegs      = new ItemNiobArmor(ARMOR_NIOBIUM, 2);
        niobBoots     = new ItemNiobArmor(ARMOR_NIOBIUM, 3);
        niobPick      = new ItemNiobPickaxe(TOOL_NIOBIUM);
        niobShovel    = new ItemNiobShovel(TOOL_NIOBIUM);
        niobAxe       = new ItemNiobAxe(TOOL_NIOBIUM);
        niobHoe       = new ItemNiobHoe(TOOL_NIOBIUM);
        niobSword     = new ItemNiobSword(TOOL_NIOBIUM);
        niobShears    = new ItemNiobShears();
        endNugget     = new Item();
        itemNiobDoor  = new ItemNiobDoor();
        enderStick    = new Item();
        endHorseArmor = new ItemEndHorseArmor();
        tantalPick    = new ItemTantalPickaxe(TOOL_NIOBIUM);
        ahrahSword    = new ItemAhrahSword(TOOL_NIOBIUM);

        espPearls    .setUnlocalizedName("esp:espPearls").setCreativeTab(ESPModRegistry.espTab);
        enderFlesh   .setUnlocalizedName("esp:enderFlesh").setTextureName("enderstuffp:enderFlesh").setCreativeTab(ESPModRegistry.espTab);
        avisFeather  .setUnlocalizedName("esp:avisFeather").setTextureName("enderstuffp:avisFeather").setCreativeTab(ESPModRegistry.espTab);
        avisArrow    .setUnlocalizedName("esp:avisArrow").setTextureName("enderstuffp:avisArrow").setCreativeTab(ESPModRegistry.espTab);
        avisCompass  .setUnlocalizedName("esp:avisCompass").setTextureName("enderstuffp:compass").setCreativeTab(ESPModRegistry.espTab);
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
        ahrahSword   .setUnlocalizedName("esp:ahrahSword").setTextureName("enderstuffp:ahrah").setCreativeTab(ESPModRegistry.espTab);
    }

    private static final void initMaterials() {
        TOOL_NIOBIUM = EnumHelper.addToolMaterial("NIOBIUM",
                                                  ToolMaterial.IRON.getHarvestLevel(),
                                                  ToolMaterial.IRON.getMaxUses(),
                                                  ToolMaterial.IRON.getEfficiencyOnProperMaterial(),
                                                  ToolMaterial.IRON.getDamageVsEntity(),
                                                  ToolMaterial.GOLD.getEnchantability());

        ARMOR_NIOBIUM = EnumHelper.addArmorMaterial("NIOBIUM",
                                                    SAPUtils.getMaxDmgFactorAM(ArmorMaterial.IRON),
                                                    new int[] { ArmorMaterial.IRON.getDamageReductionAmount(0),
                                                                ArmorMaterial.IRON.getDamageReductionAmount(1),
                                                                ArmorMaterial.IRON.getDamageReductionAmount(2),
                                                                ArmorMaterial.IRON.getDamageReductionAmount(3) },
                                                    ArmorMaterial.GOLD.getEnchantability());
    }

    private static final void registerItems() {
        SAPUtils.registerItems("enderstuffp:item",
                               espPearls, avisFeather, avisArrow, avisCompass, enderPetEgg, enderPetStaff, endIngot, niobBow, niobHelmet,
                               niobPlate, niobLegs, niobBoots, niobPick, niobShovel, niobAxe, niobHoe, niobSword, niobShears, enderFlesh,
                               rainCoat, itemNiobDoor, endNugget, enderStick, endHorseArmor);
    }
}
