package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.List;
import java.util.Map.Entry;

import sanandreasp.core.manpack.helpers.SAPUtils;

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
    public static void initialize() {
        addEnderStick();

        GameRegistry.addRecipe(new raincoatCraft());

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.enderFlesh, 1, 1),                                 // EnderFlesh (type II)
                               " o ", "ofo", " o ",                                                             //   [ ][o][ ]
                               'o', new ItemStack(Item.enderPearl, 1),                                          //   [o][f][o]
                               'f', new ItemStack(ModItemRegistry.enderFlesh, 1, 0));                           //   [ ][o][ ]


        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.enderFlesh, 1, 2),                                 // EnderFlesh (type III)
                               " o ", "ofo", " o ",                                                             //   [ ][o][ ]
                               'o', new ItemStack(Item.eyeOfEnder),                                             //   [o][f][o]
                               'f', new ItemStack(ModItemRegistry.enderFlesh, 1, 1));                           //   [ ][o][ ]

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.enderPetStaff, 1),                                 // Enderpet Staff
                           "e", "g", "s",                                                                       //   [ ][e][ ]
                               'e', new ItemStack(Item.eyeOfEnder, 1),                                          //   [ ][g][ ]
                               'g', new ItemStack(Item.ingotGold, 1),                                           //   [ ][s][ ]
                               's', new ItemStack(Item.stick, 1));

        GameRegistry.addRecipe(new ItemStack(ModBlockRegistry.endBlock, 1, 0),                                  // Block of Niobium
                               "###", "###", "###",                                                             //   [#][#][#]
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0));                             //   [#][#][#]
                                                                                                                //   [#][#][#]

        GameRegistry.addShapelessRecipe(new ItemStack(ModItemRegistry.endIngot, 9, 0),                          // Niobium Ingot
                                        new ItemStack(ModBlockRegistry.endBlock, 1, 0));


        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.endIngot, 1, 0),                                   // Niobium Ingot
                               "###", "###", "###",                                                             //   [#][#][#]
                               '#', new ItemStack(ModItemRegistry.endNugget, 1, 0));                            //   [#][#][#]
                                                                                                                //   [#][#][#]

        GameRegistry.addShapelessRecipe(new ItemStack(ModItemRegistry.endNugget, 9, 0),                         // Niobium Nugget
                                        new ItemStack(ModItemRegistry.endIngot, 1, 0));


        GameRegistry.addRecipe(new ItemStack(ModBlockRegistry.biomeChanger, 1),                                 // Biome Changer
                               " e ", "non", "ooo",                                                             //   [ ][e][ ]
                               'e', new ItemStack(Item.eyeOfEnder, 1),                                          //   [n][o][n]
                               'n', new ItemStack(ModBlockRegistry.endBlock, 1, 0),                             //   [o][o][o]
                               'o', new ItemStack(Block.obsidian, 1));

        GameRegistry.addRecipe(new ItemStack(ModBlockRegistry.duplicator, 1),                                   // Duplicator
                               "nwn", "wdw", "www",                                                             //   [n][w][n]
                               'w', new ItemStack(Block.whiteStone, 1),                                         //   [w][d][w]
                               'n', new ItemStack(ModBlockRegistry.endBlock, 1, 0),                             //   [w][w][w]
                               'd', new ItemStack(Block.blockDiamond, 1));

        GameRegistry.addRecipe(new ItemStack(ModBlockRegistry.weatherAltar, 1),                                 // Weather Altar
                               "rbr", "nnn", "qnq",                                                             //   [r][b][r]
                               'r', new ItemStack(Item.redstone, 1),                                            //   [n][n][n]
                               'b', new ItemStack(Block.blockRedstone, 1),                                      //   [q][n][q]
                               'n', new ItemStack(ModItemRegistry.endIngot, 1, 0),
                               'q', new ItemStack(Block.blockNetherQuartz, 1, OreDictionary.WILDCARD_VALUE));

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.itemNiobDoor, 1),                                  // Niobium Door
                               "nn", "nn", "nn",                                                                //   [n][n][ ]
                               'n', new ItemStack(ModItemRegistry.endIngot, 1, 0));                             //   [n][n][ ]
                                                                                                                //   [n][n][ ]

        GameRegistry.addShapelessRecipe(new ItemStack(ModBlockRegistry.enderPlanks, 4),                         // Ender Wood
                                        new ItemStack(ModBlockRegistry.enderLog, 1));

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.avisCompass, 1),                                   // Avis Compass
                               " f ", "fcf", " f ",                                                             //   [ ][f][ ]
                               'c', new ItemStack(Item.compass, 1),                                             //   [f][c][f]
                               'f', new ItemStack(ModItemRegistry.avisFeather, 1));                             //   [ ][f][ ]

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobBow, 1),                                       // Niobium Bow
                               " nr", "n r", " nr",                                                             //   [ ][n][r]
                               'n', new ItemStack(ModItemRegistry.endIngot, 1, 0),                              //   [n][ ][r]
                               'r', new ItemStack(Item.silk, 1));                                               //   [ ][n][r]

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.avisArrow, 2),                                     // Avis Arrow
                               "e", "s", "f",                                                                   //   [ ][e][ ]
                               'e', new ItemStack(Item.enderPearl, 1),                                          //   [ ][s][ ]
                               's', new ItemStack(Item.stick, 1),                                               //   [ ][f][ ]
                               'f', new ItemStack(ModItemRegistry.avisFeather));

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobPick, 1),                                      // Niobium Pickaxe
                               "###", " s ", " s ",                                                             //   [#][#][#]
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0),                              //   [ ][s][ ]
                               's', new ItemStack(ModItemRegistry.enderStick, 1));                              //   [ ][s][ ]

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobAxe, 1),                                       // Niobium Axe
                               "##", "#s", " s",                                                                //   [#][#][ ]
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0),                              //   [#][s][ ]
                               's', new ItemStack(ModItemRegistry.enderStick, 1));                              //   [ ][s][ ]

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobShovel, 1),                                    // Niobium Shovel
                               "#", "s", "s",                                                                   //   [ ][#][ ]
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0),                              //   [ ][s][ ]
                               's', new ItemStack(ModItemRegistry.enderStick, 1));                              //   [ ][s][ ]

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobHoe, 1),                                       // Niobium Hoe
                               "##", " s", " s",                                                                //   [#][#][ ]
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0),                              //   [ ][s][ ]
                               's', new ItemStack(ModItemRegistry.enderStick, 1));                              //   [ ][s][ ]


        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobShears, 1),                                    // Niobium Shears
                               " #", "# ",                                                                      //   [ ][#][ ]
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0));                             //   [#][ ][ ]
                                                                                                                //   [ ][ ][ ]

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobSword, 1),                                     // Niobium Sword
                               "#", "#", "s",                                                                   //   [ ][#][ ]
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0),                              //   [ ][#][ ]
                               's', new ItemStack(ModItemRegistry.enderStick, 1));                              //   [ ][s][ ]

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobHelmet, 1),                                    // Niobium Helmet
                               "###", "# #",                                                                    //   [#][#][#]
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0));                             //   [#][ ][#]
                                                                                                                //   [ ][ ][ ]

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobPlate, 1),                                     // Niobium Chestplate
                               "# #", "###", "###",                                                             //   [#][ ][#]
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0));                             //   [#][#][#]
                                                                                                                //   [#][#][#]

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobLegs, 1),                                      // Niobium Leggings
                               "###", "# #", "# #",                                                             //   [#][#][#]
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0));                             //   [#][ ][#]
                                                                                                                //   [#][ ][#]

        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobBoots, 1),                                     // Niobium Boots
                               "# #", "# #",                                                                    //   [#][ ][#]
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0));                             //   [#][ ][#]
    }                                                                                                           //   [ ][ ][ ]

    @SuppressWarnings("unchecked")
    private static void addEnderStick() {                                                                       // Ender Sticks
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();                                  //   [ ][p][ ]
                                                                                                                //   [ ][p][ ]
        for( int i = 0; i < recipes.size(); i++ ) {                                                             //   [ ][ ][ ]
            IRecipe sRecipe = recipes.get(i);
            if( sRecipe instanceof ShapedOreRecipe && sRecipe.getRecipeOutput().getItem() == Item.stick ) {
                recipes.add(i, new ShapedRecipes(1, 2, new ItemStack[] { new ItemStack(ModBlockRegistry.enderPlanks),
                                                                         new ItemStack(ModBlockRegistry.enderPlanks) },
                                                 new ItemStack(ModItemRegistry.enderStick, 4)));
                break;
            }
        }
    }

    public static class raincoatCraft                                                                           // Ender Raincoats
        implements IRecipe                                                                                      //   [ ][l][l]
    {                                                                                                           //   [l][c][l]
        @Override                                                                                               //   [l][b][l]
        public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
            ItemStack baseItem = inventorycrafting.getStackInRowAndColumn(1, 1);
            ItemStack colorItem = inventorycrafting.getStackInRowAndColumn(0, 2);
            NBTTagCompound nbt = new NBTTagCompound();
            ItemStack raincoat = new ItemStack(ModItemRegistry.rainCoat, 1);

            for( Entry<String, RegistryRaincoats.CoatBaseEntry> base : RegistryRaincoats.BASE_LIST.entrySet() ) {
                if( SAPUtils.areStacksEqualWithWCV(baseItem, base.getValue().craftingItem) ) {
                    nbt.setString("base", base.getKey());
                    break;
                }
            }

            for( Entry<String, RegistryRaincoats.CoatColorEntry> color : RegistryRaincoats.COLOR_LIST.entrySet() ) {
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
            ItemStack[] baseStacks = RegistryRaincoats.BASE_STACKS.toArray(new ItemStack[0]);
            ItemStack[] colorStacks = RegistryRaincoats.COLOR_STACKS.toArray(new ItemStack[0]);
            ItemStack[][] invStacks = new ItemStack[3][3];

            for( int row = 0, col = 0, i = 0; i < 9; ++i, row = i / 3, col = i % 3 ) {
                invStacks[row][col] = inventorycrafting.getStackInRowAndColumn(col, row);
            }

            if( invStacks[0][0] != null || invStacks[1][2] != null ) {
                return false;
            }

            if( invStacks[0][1] == null || invStacks[0][2] == null
                || invStacks[0][1].getItem() != Item.leather || invStacks[0][2].getItem() != Item.leather )
            {
                return false;
            }

            if( invStacks[1][0] == null || invStacks[1][1] == null
                || invStacks[1][0].getItem() != Item.leather || !SAPUtils.isItemInStackArray(invStacks[1][1], colorStacks) )
            {
                return false;
            }

            if( invStacks[2][0] == null || invStacks[2][1] == null || invStacks[2][2] == null
                || invStacks[2][0].getItem() != Item.leather || invStacks[2][2].getItem() != Item.leather
                || !SAPUtils.isItemInStackArray(invStacks[2][1], baseStacks) )
            {
                return false;
            }

            return true;
        }
    }
}
