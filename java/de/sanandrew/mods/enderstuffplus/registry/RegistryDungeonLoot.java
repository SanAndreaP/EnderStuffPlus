package de.sanandrew.mods.enderstuffplus.registry;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import net.minecraftforge.common.ChestGenHooks;

import de.sanandrew.mods.enderstuffplus.item.ItemRaincoat;
import de.sanandrew.mods.enderstuffplus.registry.raincoat.RegistryRaincoats;

public final class RegistryDungeonLoot
{
    public static final String AVIS_CHEST = "AvisChest";
    public static final String ENDLEAK_CHEST = "EndLeakChest";

    public static void initialize() {
        {
            ChestGenHooks leakLoot = ChestGenHooks.getInfo(ENDLEAK_CHEST);
            addLoot(leakLoot, new ItemStack(Blocks.diamond_block), 1, 2, 1);
            addLoot(leakLoot, new ItemStack(Items.ender_pearl), 1, 4, 100);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.espPearls, 1, 0), 1, 2, 50);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.espPearls, 1, 1), 1, 2, 50);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.espPearls, 1, 2), 1, 2, 50);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.niobPick), 1, 1, 2);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.niobAxe), 1, 1, 2);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.niobShovel), 1, 1, 2);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.niobHoe), 1, 1, 2);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.enderFlesh, 1, 0), 1, 4, 25);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.enderFlesh, 1, 1), 1, 4, 12);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.enderFlesh, 1, 2), 1, 2, 6);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.niobShears), 1, 1, 2);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.niobSword), 1, 1, 2);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.niobBow), 1, 1, 2);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.niobHelmet), 1, 1, 2);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.niobPlate), 1, 1, 2);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.niobLegs), 1, 1, 2);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.niobBoots), 1, 1, 2);
            addLoot(leakLoot, new ItemStack(ModBlockRegistry.avisEgg), 1, 1, 2);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.endNugget), 1, 2, 5);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.avisCompass), 1, 1, 30);
            addLoot(leakLoot, new ItemStack(ModItemRegistry.rainCoat), 1, 1, 20);
            addLoot(leakLoot, Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(ESPModRegistry.enderChestTel, 1)), 1, 1, 5);
            addLoot(leakLoot, new ItemStack(Blocks.end_stone), 1, 8, 30);
            addLoot(leakLoot, new ItemStack(ModBlockRegistry.sapEndTree), 1, 1, 50);
        }

        {
            ChestGenHooks avisLoot = ChestGenHooks.getInfo(AVIS_CHEST);
            addLoot(avisLoot, new ItemStack(Items.ender_pearl), 1, 4, 50);
            addLoot(avisLoot, new ItemStack(ModItemRegistry.avisFeather), 1, 4, 100);
            addLoot(avisLoot, new ItemStack(Blocks.obsidian), 1, 8, 60);
            addLoot(avisLoot, new ItemStack(Blocks.end_stone), 1, 8, 30);
            addLoot(avisLoot, new ItemStack(Items.ender_eye), 1, 2, 10);
            addLoot(avisLoot, new ItemStack(ModItemRegistry.espPearls, 1, 0), 1, 2, 50);
            addLoot(avisLoot, new ItemStack(ModItemRegistry.espPearls, 1, 1), 1, 2, 50);
            addLoot(avisLoot, new ItemStack(ModItemRegistry.espPearls, 1, 2), 1, 2, 50);
            addLoot(avisLoot, new ItemStack(ModItemRegistry.avisArrow), 1, 16, 25);
            addLoot(avisLoot, new ItemStack(ModItemRegistry.enderFlesh, 1, 0), 1, 4, 25);
            addLoot(avisLoot, new ItemStack(ModItemRegistry.enderFlesh, 1, 1), 1, 4, 12);
            addLoot(avisLoot, new ItemStack(ModItemRegistry.enderFlesh, 1, 2), 1, 2, 6);
            addLoot(avisLoot, new ItemStack(Blocks.gold_block), 1, 2, 5);
            addLoot(avisLoot, new ItemStack(ModBlockRegistry.sapEndTree), 1, 1, 50);
        }
    }

    public static boolean placeLootChest(World world, int x, int y, int z, String lootID, Random rand, int attempts) {
        world.setBlock(x, y, z, Blocks.chest);
        TileEntityChest chest = (TileEntityChest) world.getTileEntity(x, y, z);
        if( chest != null ) {
            ChestGenHooks info = ChestGenHooks.getInfo(lootID);
            for( int j = 0; j < attempts; ++j ) {
                WeightedRandom.Item weightedItem = WeightedRandom.getRandomItem(rand, info.getItems(rand));
                WeightedRandomChestContent weightedContent = (WeightedRandomChestContent) weightedItem;
                ItemStack[] stacks = ChestGenHooks.generateStacks(rand, weightedContent.theItemId,
                                                                  weightedContent.theMinimumChanceToGenerateItem,
                                                                  weightedContent.theMaximumChanceToGenerateItem);

                for( ItemStack item : stacks ) {
                    if( item.getItem() instanceof ItemRaincoat ) {
                        NBTTagCompound nbt = new NBTTagCompound();
                        int rndBase = rand.nextInt(RegistryRaincoats.BASE_LIST.size());
                        int rndColor = rand.nextInt(RegistryRaincoats.COLOR_LIST.size());

                        nbt.setString("base", RegistryRaincoats.BASE_LIST.keySet().toArray(new String[0])[rndBase]);
                        nbt.setString("color", RegistryRaincoats.COLOR_LIST.keySet().toArray(new String[0])[rndColor]);
                        item.setTagCompound(nbt);
                    }

                    chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
                }
            }
        }

        return false;
    }

    private static void addLoot(ChestGenHooks lootList, ItemStack stack, int min, int max, int weight) {
        lootList.addItem(new WeightedRandomChestContent(stack, min, max, weight));
    }
}
