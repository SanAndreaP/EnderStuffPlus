package de.sanandrew.mods.enderstuffp.util;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.enderstuffp.item.*;
import de.sanandrew.mods.enderstuffp.item.tool.*;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class RegistryItems
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
        avisFeather   = new ItemStandard("avisFeather");
        avisArrow     = new ItemStandard("avisArrow");
        avisCompass   = new ItemAvisCompass();
        enderPetEgg   = new ItemEnderPetEgg();
        enderPetStaff = new ItemStandard("petStaff");
        endIngot      = new ItemStandard("niobIngot");
        niobBow       = new ItemNiobBow();
        rainCoat      = new ItemRaincoat();
        niobHelmet    = new ItemNiobArmor("niobHelmet", armorNiobium, 0);
        niobPlate     = new ItemNiobArmor("niobChestplate", armorNiobium, 1);
        niobLegs      = new ItemNiobArmor("niobLeggings", armorNiobium, 2);
        niobBoots     = new ItemNiobArmor("niobBoots", armorNiobium, 3);
        niobPick      = new ItemNiobPickaxe(toolNiobium);
        niobShovel    = new ItemNiobShovel(toolNiobium);
        niobAxe       = new ItemNiobAxe(toolNiobium);
        niobHoe       = new ItemNiobHoe(toolNiobium);
        niobSword     = new ItemNiobSword(toolNiobium);
        niobShears    = new ItemNiobShears();
        endNugget     = new ItemStandard("niobNugget");
        itemNiobDoor  = new ItemNiobDoor();
        enderStick    = new ItemStandard("enderStick");
        endHorseArmor = new ItemEndHorseArmor();
        tantalPick    = new ItemTantalPickaxe(toolNiobium);
        ahrahSword    = new ItemAhrahSword(toolNiobium);

        enderPetStaff.setFull3D();
    }

    private static void initMaterials() {
        toolNiobium = EnumHelper.addToolMaterial("NIOBIUM", 2, 250, 6.0F, 2.0F, 22);
        armorNiobium = EnumHelper.addArmorMaterial("NIOBIUM", 15, new int[]{2, 6, 5, 2}, 25);
    }

    private static void registerItems() {
        SAPUtils.registerItems(espPearls, avisFeather, avisArrow, avisCompass, enderPetEgg, enderPetStaff, endIngot, niobBow, niobHelmet,
                               niobPlate, niobLegs, niobBoots, niobPick, niobShovel, niobAxe, niobHoe, niobSword, niobShears, enderFlesh,
                               rainCoat, itemNiobDoor, endNugget, enderStick, endHorseArmor, tantalPick, ahrahSword);
    }
}
