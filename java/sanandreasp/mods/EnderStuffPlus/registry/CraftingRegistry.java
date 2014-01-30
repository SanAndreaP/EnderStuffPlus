package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.List;

import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingRegistry {
	public static void initCraftings() {
		/** Ender Flesh **/
		// EnderFlesh type II
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.enderFlesh, 1, 1),
			" o ", "ofo", " o ",
			'o', new ItemStack(Item.enderPearl, 1),
			'f', new ItemStack(ESPModRegistry.enderFlesh, 1, 0)
		);
	
		// EnderFlesh type III
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.enderFlesh, 1, 2),
			" o ", "ofo", " o ",
			'o', new ItemStack(Item.eyeOfEnder),
			'f', new ItemStack(ESPModRegistry.enderFlesh, 1, 1)
		);
		
		/** Pet stuff **/
		// Enderpet Staff
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.enderPetStaff, 1),
			"e", "g", "s",
			'e', new ItemStack(Item.eyeOfEnder, 1),
			'g', new ItemStack(Item.ingotGold, 1),
			's', new ItemStack(Item.stick, 1)
		);
		
		// Raincoats
		GameRegistry.addRecipe(new raincoatCraft());
		
		/** Niobium Element **/
		// Niobium Storage Block
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.endBlock, 1),
			"###", "###", "###",
			'#', new ItemStack(ESPModRegistry.endIngot, 1)
		);
		
		// Niobium Ingot (from block)
		GameRegistry.addShapelessRecipe(new ItemStack(ESPModRegistry.endIngot, 9), new ItemStack(ESPModRegistry.endBlock, 1));
		
		// Niobium Ingot (from nuggets)
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.endIngot, 1),
			"###", "###", "###",
			'#', new ItemStack(ESPModRegistry.endNugget, 1)
		);
		
		// Niobium Nugget (from ingots)
		GameRegistry.addShapelessRecipe(new ItemStack(ESPModRegistry.endNugget, 9), new ItemStack(ESPModRegistry.endIngot, 1));
		
		/** Utillity Blocks **/
		// Biome Changer
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.biomeChanger, 1),
			" E ", "NON", "OOO",
			'E', new ItemStack(Item.eyeOfEnder, 1),
			'N', new ItemStack(ESPModRegistry.endBlock, 1),
			'O', new ItemStack(Block.obsidian, 1)
		);
		
		// Duplicator
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.duplicator, 1),
			"NWN", "WOW", "WWW",
			'W', new ItemStack(Block.whiteStone, 1),
			'N', new ItemStack(ESPModRegistry.endBlock, 1),
			'O', new ItemStack(Block.blockDiamond, 1)
		);
		
		// Weather Altar
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.weatherAltar, 1),
			"RBR", "NNN", "QNQ",
			'R', new ItemStack(Item.redstone, 1),
			'B', new ItemStack(Block.blockRedstone, 1),
			'N', new ItemStack(ESPModRegistry.endIngot, 1),
			'Q', new ItemStack(Block.blockNetherQuartz, 1, OreDictionary.WILDCARD_VALUE)
		);
		
		// Niobium Door
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.itemNiobDoor, 1),
			"NN", "NN", "NN",
			'N', new ItemStack(ESPModRegistry.endIngot, 1)
		);
		
		/** Deco- and Building-Blocks **/
		// Ender Wood
		GameRegistry.addShapelessRecipe(new ItemStack(ESPModRegistry.enderPlanks, 4),
			new ItemStack(ESPModRegistry.enderLog, 1)
		);
		
		/** Tools and Weapons **/
		// Avis Compass
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.avisCompass, 1),
			" f ", "fcf", " f ",
			'c', new ItemStack(Item.compass, 1),
			'f', new ItemStack(ESPModRegistry.avisFeather, 1)
		);
		
		// Niobium Bow
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.niobBow, 1),
			" NR", "N R", " NR",
			'N', new ItemStack(ESPModRegistry.endIngot, 1),
			'R', new ItemStack(Item.silk, 1)
		);
		
		// Avis Arrow
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.avisArrow, 2),
			"e","s","f",
			'e', new ItemStack(Item.enderPearl, 1),
			's', new ItemStack(Item.stick, 1),
			'f', new ItemStack(ESPModRegistry.avisFeather)
		);
		
		// Niobium Pickaxe
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.niobPick, 1),
			"###"," s "," s ",
			'#', new ItemStack(ESPModRegistry.endIngot, 1),
			's', new ItemStack(ESPModRegistry.enderStick, 1)
		);
		
		// Niobium Axe
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.niobAxe, 1),
			"##","#s"," s",
			'#', new ItemStack(ESPModRegistry.endIngot, 1),
			's', new ItemStack(ESPModRegistry.enderStick, 1)
		);
		
		// Niobium Shovel
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.niobShovel, 1),
			"#","s","s",
			'#', new ItemStack(ESPModRegistry.endIngot, 1),
			's', new ItemStack(ESPModRegistry.enderStick, 1)
		);
		
		// Niobium Hoe
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.niobHoe, 1),
			"##"," s"," s",
			'#', new ItemStack(ESPModRegistry.endIngot, 1),
			's', new ItemStack(ESPModRegistry.enderStick, 1)
		);
		
		// Niobium Shears
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.niobShears, 1),
			" #","# ",
			'#', new ItemStack(ESPModRegistry.endIngot, 1)
		);
		
		// Niobium Sword
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.niobSword, 1),
			"#","#","s",
			'#', new ItemStack(ESPModRegistry.endIngot, 1),
			's', new ItemStack(ESPModRegistry.enderStick, 1)
		);
		
		/** Armor **/
		// Niobium Helmet
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.niobHelmet, 1),
			"###","# #",
			'#', new ItemStack(ESPModRegistry.endIngot, 1)
		);
		
		// Niobium Chestplate
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.niobPlate, 1),
			"# #","###","###",
			'#', new ItemStack(ESPModRegistry.endIngot, 1)
		);
		
		// Niobium Leggings
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.niobLegs, 1),
			"###","# #","# #",
			'#', new ItemStack(ESPModRegistry.endIngot, 1)
		);
		
		// Niobium Boots
		GameRegistry.addRecipe(new ItemStack(ESPModRegistry.niobBoots, 1),
			"# #","# #",
			'#', new ItemStack(ESPModRegistry.endIngot, 1)
		);
		
		/** Misc stuff **/
		// Ender Sticks
		addEnderStick();
	}
	
	@SuppressWarnings("unchecked")
	private static void addEnderStick() {
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		
		for(int i = 0; i < recipes.size(); i++) {
			IRecipe sRecipe = recipes.get(i);
			if(sRecipe instanceof ShapedOreRecipe && sRecipe.getRecipeOutput().itemID == Item.stick.itemID) {
				recipes.add(i, new ShapedRecipes(1, 2,
									new ItemStack[] {new ItemStack(ESPModRegistry.enderPlanks), new ItemStack(ESPModRegistry.enderPlanks)},
								new ItemStack(ESPModRegistry.enderStick, 4)));
				break;
			}
		}
	}
	
	public static class raincoatCraft implements IRecipe {
		private ItemStack[] colors = new ItemStack[] {
			new ItemStack(Block.cloth, 1, OreDictionary.WILDCARD_VALUE),
			new ItemStack(Item.goldNugget),
			new ItemStack(ESPModRegistry.endNugget),
			new ItemStack(Block.glass)
		};
		private ItemStack leather = new ItemStack(Item.leather);
		private ItemStack[] base = new ItemStack[] {
			new ItemStack(Block.blockGold),
			new ItemStack(ESPModRegistry.endBlock),
			new ItemStack(Block.blockIron),
			new ItemStack(Block.blockRedstone),
			new ItemStack(Block.obsidian)
		};

		@Override
		public boolean matches(InventoryCrafting inventorycrafting, World world) {
			ItemStack slot = null;
			if(inventorycrafting.getStackInRowAndColumn(2, 0) != null)
				return false;
			if(inventorycrafting.getStackInRowAndColumn(1, 2) != null)
				return false;
			
			for( int col = 0; col < 3; col++ ) {
				for( int row = 0; row < 3; row++ ) {
					slot = inventorycrafting.getStackInRowAndColumn(col, row);
					if( slot == null && !(row == 0 && col == 2) && !(row == 2 && col == 1) )
						return false;
					
					if( (row == 0 && col != 2 && !slot.isItemEqual(this.leather)) || (row == 1 && col != 1 && !slot.isItemEqual(this.leather)) )
						return false;
					
					if( row == 1 && col == 1 && !CommonUsedStuff.isItemInStackArray(slot, this.base) )
						return false;
				}
			}
			
			ItemStack bl = inventorycrafting.getStackInRowAndColumn(0, 2);
			ItemStack br = inventorycrafting.getStackInRowAndColumn(2, 2);
			
			if( !bl.isItemEqual(br) || !CommonUsedStuff.isItemInStackArray(bl, colors) )
				return false;
			
			return true;
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
			ItemStack itmBase = inventorycrafting.getStackInRowAndColumn(1, 1);
			ItemStack itmColor = inventorycrafting.getStackInRowAndColumn(0, 2);
			
			int intBase = 0;
			for( int i = 0; i < this.base.length; i++ ) {
				if( itmBase.isItemEqual(this.base[i]) ) {
					intBase = i;
					break;
				}
			}
			
			int intColor = 0;
			if( itmColor.itemID == Block.cloth.blockID ) {
				intColor = 15-itmColor.getItemDamage();
			} else if( itmColor.itemID == Item.goldNugget.itemID ) {
				intColor = 16;
			} else if( itmColor.itemID == ESPModRegistry.endNugget.itemID ) {
				intColor = 17;
			} else if( itmColor.itemID == Block.glass.blockID ) {
				intColor = 18;
			}
			
			return new ItemStack(ESPModRegistry.rainCoat, 1, intColor | (intBase << 5));
		}

		@Override
		public int getRecipeSize() {
			return 9;
		}

		@Override
		public ItemStack getRecipeOutput() {
			return null;
		}
	}
}
