package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static sanandreasp.core.manpack.helpers.CommonUsedStuff.CUS;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;



import com.google.common.collect.Maps;

public class RegistryDuplicator {
	private static Map<ItemStack, Integer> fuelItems = Maps.newHashMap();
	private static Map<ItemStack, Integer> dupableItems = Maps.newHashMap();
	
	public static boolean registerFuel(ItemStack par1Stack, int par2burnTime) {
		if( par1Stack == null )
			return false;
		if( !isFuelRegistered(par1Stack) && par2burnTime > 0 ) {
			fuelItems.put(par1Stack, par2burnTime);
			return true;
		}
		return false;
	}
	
	private static boolean isFuelRegistered(ItemStack par1Stack) {
		Iterator<ItemStack> iterator = fuelItems.keySet().iterator();
		while(iterator.hasNext()) {
			if( CUS.areStacksEqualWithWCV(par1Stack, iterator.next()) )
				return true;
		}
		return false;
	}
	
	public static int getBurnTime(ItemStack par1Stack) {
		if( par1Stack == null )
			return 0;
		Iterator<Entry<ItemStack, Integer>> iterator = fuelItems.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<ItemStack, Integer> var1Entry = iterator.next();
			if( CUS.areStacksEqualWithWCV(par1Stack, var1Entry.getKey()) ) {
				return var1Entry.getValue();
			}
		}
		return 0;
	}
	
	public static boolean registerDupableItem(ItemStack par1Stack, int experience) {
		if( par1Stack == null || experience < 0 )
			return false;
		if( !isItemDupable(par1Stack) ) {
			dupableItems.put(par1Stack, experience);
			return true;
		}
		return false;
	}
	
	public static int getNeededExp(ItemStack par1Stack) {
		if( par1Stack == null )
			return 0;
		Iterator<Entry<ItemStack, Integer>> iterator = dupableItems.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<ItemStack, Integer> var1Entry = iterator.next();
			if( CUS.areStacksEqualWithWCV(par1Stack, var1Entry.getKey()) )
				return var1Entry.getValue();
		}
		return 0;
	}

	public static boolean isItemDupable(ItemStack par1Stack) {
		if( par1Stack == null )
			return false;
		Iterator<ItemStack> iterator = dupableItems.keySet().iterator();
		while(iterator.hasNext()) {
			if( CUS.areStacksEqualWithWCV(par1Stack, iterator.next()) )
				return true;
		}
		return false;
	}
}
