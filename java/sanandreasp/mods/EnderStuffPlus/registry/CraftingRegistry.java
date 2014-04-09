package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.List;
import java.util.Map.Entry;

import sanandreasp.core.manpack.helpers.SAPUtils;
import sanandreasp.mods.EnderStuffPlus.item.ItemRaincoat;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingRegistry
{
    public static class raincoatCraft
        implements IRecipe
    {
        private ItemStack leather = new ItemStack(Item.leather);

        @Override
        public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
            ItemStack baseItem = inventorycrafting.getStackInRowAndColumn(1, 1);
            ItemStack colorItem = inventorycrafting.getStackInRowAndColumn(0, 2);
            NBTTagCompound nbt = new NBTTagCompound();
            ItemStack raincoat = new ItemStack(ModItemRegistry.rainCoat, 1);

            for( Entry<String, ItemRaincoat.CoatBaseEntry> base : ItemRaincoat.BASE_LIST.entrySet() ) {
                if( SAPUtils.areStacksEqualWithWCV(baseItem, base.getValue().craftingItem) ) {
                    nbt.setString("base", base.getKey());
                    break;
                }
            }

            for( Entry<String, ItemRaincoat.CoatColorEntry> color : ItemRaincoat.COLOR_LIST.entrySet() ) {
                if( SAPUtils.areStacksEqualWithWCV(colorItem, color.getValue().craftingItem) ) {
                    nbt.setString("color", color.getKey());
                    break;
                }
            }

            raincoat.setTagCompound(nbt);

            return raincoat;
        }

        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }

        @Override
        public int getRecipeSize() {
            return 9;
        }

        @Override
        public boolean matches(InventoryCrafting inventorycrafting, World world) {
            ItemStack[] baseStacks = ItemRaincoat.BASE_STACKS.toArray(new ItemStack[0]);
            ItemStack[] colorStacks = ItemRaincoat.COLOR_STACKS.toArray(new ItemStack[0]);

            for( int row = 0, col = 0, i = 0; i < 9; ++i, row = i / 3, col = i % 3 ) {
                ItemStack slot = inventorycrafting.getStackInRowAndColumn(col, row);

                if( (row == 0 && col == 0) || (row == 1 && col == 2) ) {                                        // [O][ ][ ]
                    if( slot != null ) {                                                                        // [ ][ ][O]
                        return false;                                                                           // [ ][ ][ ]
                    } else {
                        continue;
                    }
                }

                if( row == 0 && (col == 1 || col == 2) ) {                                                      // [ ][X][X]
                    if( slot == null || !slot.isItemEqual(this.leather) ) {                                     // [ ][ ][ ]
                        return false;                                                                           // [ ][ ][ ]
                    }
                } else if( row == 1 ) {                                                                         // [ ][ ][ ]
                    if( col == 0 && (slot == null || !slot.isItemEqual(this.leather)) ) {                       // [x][x][ ]
                        return false;                                                                           // [ ][ ][ ]
                    } else if( col == 1 && !SAPUtils.isItemInStackArray(slot, colorStacks) ) {
                        return false;
                    }
                } else if( row == 2 ) {
                    if( (col == 0 || col == 2) && (slot == null || !slot.isItemEqual(this.leather)) ) {         // [ ][ ][ ]
                        return false;                                                                           // [ ][ ][ ]
                    } else if( col == 1 && !SAPUtils.isItemInStackArray(slot, baseStacks) ) {                   // [x][x][x]
                        return false;
                    }
                }
            }

            return true;
        }
    }

    @SuppressWarnings("unchecked")
    private static void addEnderStick() {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();

        for( int i = 0; i < recipes.size(); i++ ) {
            IRecipe sRecipe = recipes.get(i);
            if( sRecipe instanceof ShapedOreRecipe && sRecipe.getRecipeOutput().itemID == Item.stick.itemID ) {
                recipes.add(i, new ShapedRecipes(1, 2, new ItemStack[] { new ItemStack(ModBlockRegistry.enderPlanks),
                                                                        new ItemStack(ModBlockRegistry.enderPlanks) },
                                                 new ItemStack(ModItemRegistry.enderStick, 4)));
                break;
            }
        }
    }

    public static void initCraftings() {
        /** Ender Flesh **/
        // EnderFlesh type II
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.enderFlesh, 1, 1), " o ", "ofo", " o ", 'o',
                               new ItemStack(Item.enderPearl, 1), 'f', new ItemStack(ModItemRegistry.enderFlesh, 1, 0));

        // EnderFlesh type III
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.enderFlesh, 1, 2), " o ", "ofo", " o ", 'o',
                               new ItemStack(Item.eyeOfEnder), 'f', new ItemStack(ModItemRegistry.enderFlesh, 1, 1));

        /** Pet stuff **/
        // Enderpet Staff
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.enderPetStaff, 1), "e", "g", "s", 'e',
                               new ItemStack(Item.eyeOfEnder, 1), 'g', new ItemStack(Item.ingotGold, 1), 's',
                               new ItemStack(Item.stick, 1));

        // Raincoats
        GameRegistry.addRecipe(new raincoatCraft());

        /** Niobium Element **/
        // Niobium Storage Block
        GameRegistry.addRecipe(new ItemStack(ModBlockRegistry.endBlock, 1), "###", "###", "###", '#',
                               new ItemStack(ModItemRegistry.endIngot, 1));

        // Niobium Ingot (from block)
        GameRegistry.addShapelessRecipe(new ItemStack(ModItemRegistry.endIngot, 9),
                                        new ItemStack(ModBlockRegistry.endBlock, 1));

        // Niobium Ingot (from nuggets)
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.endIngot, 1), "###", "###", "###", '#',
                               new ItemStack(ModItemRegistry.endNugget, 1));

        // Niobium Nugget (from ingots)
        GameRegistry.addShapelessRecipe(new ItemStack(ModItemRegistry.endNugget, 9),
                                        new ItemStack(ModItemRegistry.endIngot, 1));

        /** Utillity Blocks **/
        // Biome Changer
        GameRegistry.addRecipe(new ItemStack(ModBlockRegistry.biomeChanger, 1), " E ", "NON", "OOO", 'E',
                               new ItemStack(Item.eyeOfEnder, 1), 'N', new ItemStack(ModBlockRegistry.endBlock, 1),
                               'O', new ItemStack(Block.obsidian, 1));

        // Duplicator
        GameRegistry.addRecipe(new ItemStack(ModBlockRegistry.duplicator, 1), "NWN", "WOW", "WWW", 'W',
                               new ItemStack(Block.whiteStone, 1), 'N', new ItemStack(ModBlockRegistry.endBlock, 1),
                               'O', new ItemStack(Block.blockDiamond, 1));

        // Weather Altar
        GameRegistry.addRecipe(new ItemStack(ModBlockRegistry.weatherAltar, 1), "RBR", "NNN", "QNQ", 'R',
                               new ItemStack(Item.redstone, 1), 'B', new ItemStack(Block.blockRedstone, 1), 'N',
                               new ItemStack(ModItemRegistry.endIngot, 1), 'Q',
                               new ItemStack(Block.blockNetherQuartz, 1, OreDictionary.WILDCARD_VALUE));

        // Niobium Door
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.itemNiobDoor, 1), "NN", "NN", "NN", 'N',
                               new ItemStack(ModItemRegistry.endIngot, 1));

        /** Deco- and Building-Blocks **/
        // Ender Wood
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlockRegistry.enderPlanks, 4),
                                        new ItemStack(ModBlockRegistry.enderLog, 1));

        /** Tools and Weapons **/
        // Avis Compass
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.avisCompass, 1), " f ", "fcf", " f ", 'c',
                               new ItemStack(Item.compass, 1), 'f', new ItemStack(ModItemRegistry.avisFeather, 1));

        // Niobium Bow
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobBow, 1), " NR", "N R", " NR", 'N',
                               new ItemStack(ModItemRegistry.endIngot, 1), 'R', new ItemStack(Item.silk, 1));

        // Avis Arrow
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.avisArrow, 2), "e", "s", "f", 'e',
                               new ItemStack(Item.enderPearl, 1), 's', new ItemStack(Item.stick, 1), 'f',
                               new ItemStack(ModItemRegistry.avisFeather));

        // Niobium Pickaxe
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobPick, 1), "###", " s ", " s ", '#',
                               new ItemStack(ModItemRegistry.endIngot, 1), 's',
                               new ItemStack(ModItemRegistry.enderStick, 1));

        // Niobium Axe
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobAxe, 1), "##", "#s", " s", '#',
                               new ItemStack(ModItemRegistry.endIngot, 1), 's',
                               new ItemStack(ModItemRegistry.enderStick, 1));

        // Niobium Shovel
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobShovel, 1), "#", "s", "s", '#',
                               new ItemStack(ModItemRegistry.endIngot, 1), 's',
                               new ItemStack(ModItemRegistry.enderStick, 1));

        // Niobium Hoe
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobHoe, 1), "##", " s", " s", '#',
                               new ItemStack(ModItemRegistry.endIngot, 1), 's',
                               new ItemStack(ModItemRegistry.enderStick, 1));

        // Niobium Shears
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobShears, 1), " #", "# ", '#',
                               new ItemStack(ModItemRegistry.endIngot, 1));

        // Niobium Sword
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobSword, 1), "#", "#", "s", '#',
                               new ItemStack(ModItemRegistry.endIngot, 1), 's',
                               new ItemStack(ModItemRegistry.enderStick, 1));

        /** Armor **/
        // Niobium Helmet
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobHelmet, 1), "###", "# #", '#',
                               new ItemStack(ModItemRegistry.endIngot, 1));

        // Niobium Chestplate
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobPlate, 1), "# #", "###", "###", '#',
                               new ItemStack(ModItemRegistry.endIngot, 1));

        // Niobium Leggings
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobLegs, 1), "###", "# #", "# #", '#',
                               new ItemStack(ModItemRegistry.endIngot, 1));

        // Niobium Boots
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobBoots, 1), "# #", "# #", '#',
                               new ItemStack(ModItemRegistry.endIngot, 1));

        /** Misc stuff **/
        // Ender Sticks
        addEnderStick();
    }
}
