package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.item.ItemRaincoat;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import net.minecraftforge.common.ChestGenHooks;

public final class RegistryDungeonLoot {
	public static final String AVIS_CHEST = "AvisChest";
	public static final String ENDLEAK_CHEST = "EndLeakChest";

	public static void init() {
		{
			ChestGenHooks leakLoot = ChestGenHooks.getInfo(ENDLEAK_CHEST);
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(Block.blockDiamond), 1, 2, 1));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(Item.enderPearl), 1, 4, 100));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.espPearls, 1, 0), 1, 2, 50));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.espPearls, 1, 1), 1, 2, 50));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.espPearls, 1, 2), 1, 2, 50));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.niobPick), 1, 1, 2));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.niobAxe), 1, 1, 2));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.niobShovel), 1, 1, 2));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.niobHoe), 1, 1, 2));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.enderFlesh, 1, 0), 1, 4, 25));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.enderFlesh, 1, 1), 1, 4, 12));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.enderFlesh, 1, 2), 1, 2, 6));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.niobShears), 1, 1, 2));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.niobSword), 1, 1, 2));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.niobBow), 1, 1, 2));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.niobHelmet), 1, 1, 2));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.niobPlate), 1, 1, 2));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.niobLegs), 1, 1, 2));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.niobBoots), 1, 1, 2));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(BlockRegistry.avisEgg), 1, 1, 2));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.endNugget), 1, 2, 5));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.avisCompass), 1, 1, 30));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.rainCoat), 1, 1, 20));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(Item.enchantedBook), 1, 1, 5));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(Block.whiteStone), 1, 8, 30));
			  leakLoot.addItem(new WeightedRandomChestContent(new ItemStack(BlockRegistry.sapEndTree), 1, 1, 50));
		}
		{
			ChestGenHooks avisLoot = ChestGenHooks.getInfo(AVIS_CHEST);
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(Item.enderPearl), 1, 4, 50));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.avisFeather), 1, 4, 100));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(Block.obsidian), 1, 8, 60));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(Block.whiteStone), 1, 8, 30));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(Item.eyeOfEnder), 1, 2, 10));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.espPearls, 1, 0), 1, 2, 50));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.espPearls, 1, 1), 1, 2, 50));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.espPearls, 1, 2), 1, 2, 50));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.avisArrow), 1, 16, 25));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.enderFlesh, 1, 0), 1, 4, 25));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.enderFlesh, 1, 1), 1, 4, 12));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(ItemRegistry.enderFlesh, 1, 2), 1, 2, 6));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(Block.blockGold), 1, 2, 5));
			  avisLoot.addItem(new WeightedRandomChestContent(new ItemStack(BlockRegistry.sapEndTree), 1, 1, 50));
		}
	}

	public static boolean placeLootChest(World world, int x, int y, int z, String lootID, Random rand, int attempts) {
		world.setBlock(x, y, z, Block.chest.blockID);
		TileEntityChest chest = (TileEntityChest)world.getBlockTileEntity(x, y, z);
		if( chest != null ) {
            ChestGenHooks info = ChestGenHooks.getInfo(lootID);
            for( int j = 0; j < attempts; ++j ) {
            	WeightedRandomChestContent weightedItem = (WeightedRandomChestContent)WeightedRandom.getRandomItem(rand, info.getItems(rand));
                ItemStack[] stacks = ChestGenHooks.generateStacks(rand, weightedItem.theItemId, weightedItem.theMinimumChanceToGenerateItem, weightedItem.theMaximumChanceToGenerateItem);

                for( ItemStack item : stacks ) {
                	if( item.getItem() instanceof ItemRaincoat ) {
                		item.setItemDamage(rand.nextInt(19) | (rand.nextInt(5) << 5));
                	} else if( item.getItem() instanceof ItemEnchantedBook ) {
                		item = Item.enchantedBook.getEnchantedItemStack(new EnchantmentData(ESPModRegistry.enderChestTel, 1));
                	}

                	chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);
                }
            }
		}

		return false;
	}
}
