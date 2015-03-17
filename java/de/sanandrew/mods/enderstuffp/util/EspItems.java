package de.sanandrew.mods.enderstuffp.util;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.entity.item.ItemEspMonsterPlacer;
import de.sanandrew.mods.enderstuffp.item.*;
import de.sanandrew.mods.enderstuffp.item.tool.*;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class EspItems
{
    public static ArmorMaterial armorNiobium;
    public static ToolMaterial toolNiobium;

    public static Item avisArrow;
    public static Item avisCompass;
    public static Item avisFeather;
    public static Item enderFlesh;
    public static ItemEnderPetEgg enderPetEgg;
    public static Item enderPetStaff;
    public static Item enderStick;
    public static Item endHorseArmor;
    public static Item enderIngot;
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
    public static Item monsterEgg;
    //FIXME unfinished!
    public static Item tantalPick;
    public static Item ahrahSword;


    public static void initialize() {
        initMaterials();
        initItems();
        registerItems();
    }

    private static void initMaterials() {
        armorNiobium = EnumHelper.addArmorMaterial(EnderStuffPlus.MOD_ID.toUpperCase() + "_NIOBIUM", 20, new int[]{2, 7, 5, 2}, 18);
        toolNiobium = EnumHelper.addToolMaterial(EnderStuffPlus.MOD_ID.toUpperCase() + "_NIOBIUM", 3, 450, 7.0F, 2.5F, 18);
    }

    private static void initItems() {
        espPearls     = new ItemCustomEnderPearl();
        enderFlesh    = new ItemEnderFlesh();
        avisFeather   = new ItemStandard("avisFeather", "feather_avis");
        avisArrow     = new ItemStandard("avisArrow", "arrow_avis");
        avisCompass   = new ItemAvisCompass();
        enderPetEgg   = new ItemEnderPetEgg();
        enderPetStaff = new ItemStandard("petStaff", "pet_staff");
        enderIngot    = new ItemEnderIngot();
        niobBow       = new ItemNiobiumBow();
        rainCoat      = new ItemRaincoat();
        niobHelmet    = new ItemNiobArmor("helmetNiobium", "helmet_niobium", armorNiobium, 0);
        niobPlate     = new ItemNiobArmor("chestplateNiobium", "chestplate_niobium", armorNiobium, 1);
        niobLegs      = new ItemNiobArmor("leggingsNiobium", "leggings_niobium", armorNiobium, 2);
        niobBoots     = new ItemNiobArmor("bootsNiobium", "boots_niobium", armorNiobium, 3);
        niobPick      = new ItemNiobiumPickaxe(toolNiobium);
        niobShovel    = new ItemNiobiumShovel(toolNiobium);
        niobAxe       = new ItemNiobiumAxe(toolNiobium);
        niobHoe       = new ItemNiobiumHoe(toolNiobium);
        niobSword     = new ItemNiobiumSword(toolNiobium);
        niobShears    = new ItemNiobiumShears();
        endNugget     = new ItemEnderNugget();
        itemNiobDoor  = new ItemNiobDoor();
        enderStick    = new ItemStandard("enderStick", "stick_ender");
        endHorseArmor = new ItemEndHorseArmor();
        tantalPick    = new ItemTantalumPickaxe(toolNiobium);
        ahrahSword    = new ItemAhrahSword(toolNiobium);
        monsterEgg    = new ItemEspMonsterPlacer();

        enderPetStaff.setFull3D();
    }

    private static void registerItems() {
        SAPUtils.registerItems(espPearls, avisFeather, avisArrow, avisCompass, enderPetEgg, enderPetStaff, enderIngot, niobBow, niobHelmet,
                               niobPlate, niobLegs, niobBoots, niobPick, niobShovel, niobAxe, niobHoe, niobSword, niobShears, enderFlesh,
                               rainCoat, itemNiobDoor, endNugget, enderStick, endHorseArmor, tantalPick, ahrahSword, monsterEgg, new ItemWorldGenSpawner());
    }
}
