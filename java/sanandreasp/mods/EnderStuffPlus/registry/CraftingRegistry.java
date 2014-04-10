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

        // Weather Altar
        GameRegistry.addRecipe(new ItemStack(ModBlockRegistry.weatherAltar, 1),
                               "rbr", "nnn", "qnq",
                               'r', new ItemStack(Item.redstone, 1),
                               'b', new ItemStack(Block.blockRedstone, 1),
                               'n', new ItemStack(ModItemRegistry.endIngot, 1, 0),
                               'q', new ItemStack(Block.blockNetherQuartz, 1, OreDictionary.WILDCARD_VALUE));

        /** Deco- and Building-Blocks **/
        // Niobium Door
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.itemNiobDoor, 1),
                               "nn", "nn", "nn",
                               'n', new ItemStack(ModItemRegistry.endIngot, 1, 0));

        // Ender Wood
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlockRegistry.enderPlanks, 4),
                                        new ItemStack(ModBlockRegistry.enderLog, 1));

        /** Tools and Weapons **/
        // Avis Compass
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.avisCompass, 1),
                               " f ", "fcf", " f ",
                               'c', new ItemStack(Item.compass, 1),
                               'f', new ItemStack(ModItemRegistry.avisFeather, 1));

        // Niobium Bow
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobBow, 1),
                               " nr", "n r", " nr",
                               'n', new ItemStack(ModItemRegistry.endIngot, 1, 0),
                               'r', new ItemStack(Item.silk, 1));

        // Avis Arrow
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.avisArrow, 2),
                               "e", "s", "f",
                               'e', new ItemStack(Item.enderPearl, 1),
                               's', new ItemStack(Item.stick, 1),
                               'f', new ItemStack(ModItemRegistry.avisFeather));

        // Niobium Pickaxe
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobPick, 1),
                               "###", " s ", " s ",
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0),
                               's', new ItemStack(ModItemRegistry.enderStick, 1));

        // Niobium Axe
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobAxe, 1),
                               "##", "#s", " s",
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0),
                               's', new ItemStack(ModItemRegistry.enderStick, 1));

        // Niobium Shovel
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobShovel, 1),
                               "#", "s", "s",
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0),
                               's', new ItemStack(ModItemRegistry.enderStick, 1));

        // Niobium Hoe
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobHoe, 1),
                               "##", " s", " s",
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0),
                               's', new ItemStack(ModItemRegistry.enderStick, 1));

        // Niobium Shears
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobShears, 1),
                               " #", "# ",
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0));

        // Niobium Sword
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobSword, 1),
                               "#", "#", "s",
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0),
                               's', new ItemStack(ModItemRegistry.enderStick, 1));

        /** Armor **/
        // Niobium Helmet
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobHelmet, 1),
                               "###", "# #",
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0));

        // Niobium Chestplate
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobPlate, 1),
                               "# #", "###", "###",
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0));

        // Niobium Leggings
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobLegs, 1),
                               "###", "# #", "# #",
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0));

        // Niobium Boots
        GameRegistry.addRecipe(new ItemStack(ModItemRegistry.niobBoots, 1),
                               "# #", "# #",
                               '#', new ItemStack(ModItemRegistry.endIngot, 1, 0));
    }

    @SuppressWarnings("unchecked")
    private static void addEnderStick() {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();

        for( int i = 0; i < recipes.size(); i++ ) {
            IRecipe sRecipe = recipes.get(i);
            if( sRecipe instanceof ShapedOreRecipe && sRecipe.getRecipeOutput().getItem() == Item.stick ) {
                recipes.add(i, new ShapedRecipes(1, 2, new ItemStack[] { new ItemStack(ModBlockRegistry.enderPlanks),
                                                                         new ItemStack(ModBlockRegistry.enderPlanks) },
                                                 new ItemStack(ModItemRegistry.enderStick, 4)));
                break;
            }
        }
    }

    public static class raincoatCraft               //   [ ][l][l]
        implements IRecipe                          //   [l][c][l]
    {                                               //   [l][b][l]
        @Override
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
