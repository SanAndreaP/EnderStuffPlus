package sanandreasp.mods.EnderStuffPlus.registry;

import net.minecraft.item.ItemStack;
import sanandreasp.core.manpack.managers.SAPLanguageManager;

public final class LangRegistry {
	
	
	public static void loadLangs(SAPLanguageManager langman) {
		langman.addLangProp(ESPModRegistry.avisEgg, "Avis Egg");
		langman.addLangProp(ESPModRegistry.biomeChanger, "Biome Changer");
		langman.addLangProp(ESPModRegistry.duplicator, "Duplicator");
		langman.addLangProp(ESPModRegistry.weatherAltar, "Weather Altar");
		langman.addLangProp(ESPModRegistry.enderLeaves, "Endertree Leaves");
		langman.addLangProp(ESPModRegistry.enderLog, "Endertree Log");
		langman.addLangProp(ESPModRegistry.enderPlanks, "Endertree Planks");
		langman.addLangProp(ESPModRegistry.sapEndTree, "Endertree Sapling");
		
		langman.addLangProp(new ItemStack(ESPModRegistry.endOre, 0, 0), "Niobium Ore");
		langman.addLangProp(new ItemStack(ESPModRegistry.endOre, 0, 1), "Tantalum Ore");
		langman.addLangProp(new ItemStack(ESPModRegistry.endBlock, 0, 0), "Block of Niobium");
		langman.addLangProp(new ItemStack(ESPModRegistry.endBlock, 0, 1), "Block of Tantalum");
		
		langman.addLangProp(ESPModRegistry.enderFlesh, "Ender Flesh");
		langman.addLangProp(ESPModRegistry.avisFeather, "Avis Feather");
		langman.addLangProp(ESPModRegistry.avisArrow, "Avis Arrow");
		langman.addLangProp(ESPModRegistry.avisCompass, "Avis Compass");
		langman.addLangProp(ESPModRegistry.enderPetEgg, "Spawn tamed");
		langman.addLangProp(ESPModRegistry.enderPetStaff, "Enderpet Staff");
		langman.addLangProp(ESPModRegistry.endIngot, "Niobium Ingot");
		langman.addLangProp(ESPModRegistry.niobBow, "Niobium Bow");
		langman.addLangProp(ESPModRegistry.rainCoat, "Ender-Raincoat");
		langman.addLangProp(ESPModRegistry.niobHelmet, "Niobium Helmet");
		langman.addLangProp(ESPModRegistry.niobPlate, "Niobium Chestplate");
		langman.addLangProp(ESPModRegistry.niobLegs, "Niobium Leggins");
		langman.addLangProp(ESPModRegistry.niobBoots, "Niobium Boots");
		langman.addLangProp(ESPModRegistry.niobPick, "Niobium Pickaxe");
		langman.addLangProp(ESPModRegistry.niobShovel, "Niobium Shovel");
		langman.addLangProp(ESPModRegistry.niobAxe, "Niobium Axe");
		langman.addLangProp(ESPModRegistry.niobHoe, "Niobium Hoe");
		langman.addLangProp(ESPModRegistry.niobSword, "Niobium Sword");
		langman.addLangProp(ESPModRegistry.niobShears, "Niobium Shears");
		langman.addLangProp(ESPModRegistry.endNugget, "Niobium Nugget");
		langman.addLangProp(ESPModRegistry.itemNiobDoor, "Niobium Door");
		langman.addLangProp(ESPModRegistry.enderStick, "Endertree Stick");
		
		langman.addLangProp(new ItemStack(ESPModRegistry.espPearls, 1, 0), "Nivis Pearl");
		langman.addLangProp(new ItemStack(ESPModRegistry.espPearls, 1, 1), "Ignis Pearl");
		langman.addLangProp(new ItemStack(ESPModRegistry.espPearls, 1, 2), "Endermiss Pearl");
		
		langman.addLangProp("entity.EnderNivis.name", "Ender Nivis");
		langman.addLangProp("entity.EnderIgnis.name", "Ender Ignis");
		langman.addLangProp("entity.EnderRay.name", "Ender Ray");
		langman.addLangProp("entity.EnderMiss.name", "Ender Miss");
		langman.addLangProp("entity.EnderAvis.name", "Ender Avis");
		langman.addLangProp("entity.EnderNemesis.name", "Ender Nemesis");
		
		langman.addLangProp("enderstuffplus.petegg.health", "Health");
		langman.addLangProp("enderstuffplus.petegg.hasSpecSkin", "Has special skin");
		langman.addLangProp("enderstuffplus.petegg.immuneToH2O", "Has Raincoat");
		langman.addLangProp("enderstuffplus.petegg.fallDmg", "Can get fall damage");
		langman.addLangProp("enderstuffplus.petegg.true", "yes");
		langman.addLangProp("enderstuffplus.petegg.false", "no");
		langman.addLangProp("enderstuffplus.guipet.title.miss", "I am %s, the Ender Miss");
		langman.addLangProp("enderstuffplus.guipet.title.avis", "I am %s, the Ender Avis");
		langman.addLangProp("enderstuffplus.guipet.mount", "mount");
		langman.addLangProp("enderstuffplus.guipet.sit", "sit");
		langman.addLangProp("enderstuffplus.guipet.standUp", "stand up");
		langman.addLangProp("enderstuffplus.guipet.stay", "stay");
		langman.addLangProp("enderstuffplus.guipet.follow", "follow");
		langman.addLangProp("enderstuffplus.guipet.putIntoEgg", "put me into an egg");
		langman.addLangProp("enderstuffplus.guipet.close", "Done");
		langman.addLangProp("enderstuffplus.guipet.rename", "Rename me!");
		langman.addLangProp("enderstuffplus.guipet.name", "Name:");
		langman.addLangProp("enderstuffplus.chat.name", "My name is %s.");
		langman.addLangProp("enderstuffplus.chat.missFriend", "I have %s%% Health and you are my friend.");
		langman.addLangProp("enderstuffplus.chat.avisFriend", "I have %s%% Health, %s%% stamina and you are my friend.");
		langman.addLangProp("enderstuffplus.chat.stranger", "My friend is %s");
		langman.addLangProp("enderstuffplus.biomeChanger.gui1.range", "Range");
		langman.addLangProp("enderstuffplus.biomeChanger.gui3.blocks", "Change blocks");
		langman.addLangProp("enderstuffplus.biomeChanger.gui3.periform", "Form of the area:");
		langman.addLangProp("enderstuffplus.biomeChanger.gui3.form1", "Circular");
		langman.addLangProp("enderstuffplus.biomeChanger.gui3.form2", "Rhombic");
		langman.addLangProp("enderstuffplus.biomeChanger.gui3.form3", "Square");
		langman.addLangProp("enderstuffplus.biomeChanger.gui3.stats", "Statistics:");
		langman.addLangProp("enderstuffplus.biomeChanger.gui3.remain", "Remaining: %s");
		langman.addLangProp("enderstuffplus.biomeChanger.gui3.processed", "Processed: %s");
		langman.addLangProp("enderstuffplus.duplicator.insertXP", "Insert XP-Levels");
		langman.addLangProp("enderstuffplus.duplicator.storedXP", "stored XP-Levels");
		langman.addLangProp("enderstuffplus.duplicator.neededXP", "needed XP-Levels");
		langman.addLangProp("enderstuffplus.weatherAltar.duration", "Duration");
		langman.addLangProp("enderstuffplus.weatherAltar.sun", "Sun");
		langman.addLangProp("enderstuffplus.weatherAltar.rain", "Rain");
		langman.addLangProp("enderstuffplus.weatherAltar.thunder", "Thunder");
		
		langman.addLangProp("itemGroup.ESPTab", "EnderStuff+ Items");
		langman.addLangProp("itemGroup.ESPTabCoats", "EnderStuff+ Ender Raincoats");
		langman.addLangProp("enchantment.enderChestTel", "Ender Chest Teleporter");
		
		langman.loadLangs();
	}
}
