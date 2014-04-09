package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import sanandreasp.core.manpack.helpers.SAPUtils;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RegistryDuplicator
{
    private static final Map<ItemStack, Integer> dupableItems = Maps.newHashMap();
    private static final Map<ItemStack, Integer> fuelItems = Maps.newHashMap();

    static {
        registerFuel(new ItemStack(ModItemRegistry.endIngot, 1, 0), 100);
        registerFuel(new ItemStack(Item.diamond), 160);
        registerFuel(new ItemStack(Item.ingotGold), 60);
        registerFuel(new ItemStack(Item.emerald), 40);
        registerFuel(new ItemStack(Item.ingotIron), 20);

        registerDupableItem(new ItemStack(Block.cobblestoneMossy), 0);
        registerDupableItem(new ItemStack(Block.stoneBrick, 1, 1), 0);
        registerDupableItem(new ItemStack(Block.stoneBrick, 1, 2), 0);
        registerDupableItem(new ItemStack(Block.stoneBrick, 1, 3), 0);
        registerDupableItem(new ItemStack(Block.blockClay), 0);
        registerDupableItem(new ItemStack(Block.sand), 0);
        registerDupableItem(new ItemStack(Block.whiteStone), 2);
        registerDupableItem(new ItemStack(Block.oreLapis), 5);
        registerDupableItem(new ItemStack(Block.oreIron), 10);
        registerDupableItem(new ItemStack(Block.oreRedstone), 15);
        registerDupableItem(new ItemStack(Block.oreGold), 15);
        registerDupableItem(new ItemStack(ModBlockRegistry.endOre, 1, 0), 20);
        registerDupableItem(new ItemStack(Block.oreEmerald), 25);
        registerDupableItem(new ItemStack(Block.oreDiamond), 30);
    }

    public static boolean areInputsEqual(ItemStack slot1, ItemStack slot2) {
        if( slot1 == null || slot2 == null ) {
            return false;
        }
        return slot1.isItemEqual(slot2);
    }

    public static int getBurnTime(ItemStack stack) {
        if( stack == null ) {
            return 0;
        }

        Iterator<Entry<ItemStack, Integer>> iterator = fuelItems.entrySet().iterator();
        while( iterator.hasNext() ) {
            Entry<ItemStack, Integer> var1Entry = iterator.next();
            if( SAPUtils.areStacksEqualWithWCV(stack, var1Entry.getKey()) ) {
                return var1Entry.getValue();
            }
        }

        return 0;
    }

    public static int getNeededExp(ItemStack stack) {
        if( stack == null ) {
            return 0;
        }

        Iterator<Entry<ItemStack, Integer>> iterator = dupableItems.entrySet().iterator();
        while( iterator.hasNext() ) {
            Entry<ItemStack, Integer> var1Entry = iterator.next();
            if( SAPUtils.areStacksEqualWithWCV(stack, var1Entry.getKey()) ) {
                return var1Entry.getValue();
            }
        }

        return 0;
    }

    private static boolean isFuelRegistered(ItemStack stack) {
        Iterator<ItemStack> iterator = fuelItems.keySet().iterator();
        while( iterator.hasNext() ) {
            if( SAPUtils.areStacksEqualWithWCV(stack, iterator.next()) ) {
                return true;
            }
        }

        return false;
    }

    public static boolean isItemDupable(ItemStack stack) {
        if( stack == null ) {
            return false;
        }

        Iterator<ItemStack> iterator = dupableItems.keySet().iterator();
        while( iterator.hasNext() ) {
            if( SAPUtils.areStacksEqualWithWCV(stack, iterator.next()) ) {
                return true;
            }
        }

        return false;
    }

    public static boolean isOutputValid(ItemStack input, ItemStack output) {
        if( output == null ) {
            return true;
        }

        return input.isItemEqual(output) && output.stackSize < input.getMaxStackSize();
    }

    public static boolean registerDupableItem(ItemStack stack, int experience) {
        if( stack == null || experience < 0 ) {
            return false;
        }

        if( !isItemDupable(stack) ) {
            dupableItems.put(stack, experience);
            return true;
        }

        return false;
    }

    public static boolean registerFuel(ItemStack stack, int burnTime) {
        if( stack == null ) {
            return false;
        }

        if( !isFuelRegistered(stack) && burnTime > 0 ) {
            fuelItems.put(stack, burnTime);
            return true;
        }

        return false;
    }
}
