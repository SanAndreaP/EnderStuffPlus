package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

public class RegistryBiomeChanger {
	private static HashMap<Integer, Entry<ItemStack, Integer>> fuelList = Maps.newHashMap();
	private static List<Integer> disabledBiomesList = new ArrayList<Integer>();


	private static int getNewFuelID() {
		return fuelList.size();
	}

	public static HashMap<Integer, Entry<ItemStack, Integer>> getFuelList() {
		return Maps.newHashMap(fuelList);
	}

	public static boolean addNewFuel(ItemStack par1Stack, int par2Multiplicator) {
		if( getMultiFromStack(par1Stack) > 0 ) {
            return false;
        } else {
            fuelList.put(getNewFuelID(), new AbstractMap.SimpleEntry<ItemStack, Integer>(par1Stack, par2Multiplicator));
        }
		return true;
	}

	public static int getMultiFromStack(ItemStack par1Stack) {
		Iterator<Entry<Integer, Entry<ItemStack, Integer>>> iterator = fuelList.entrySet().iterator();
		while(iterator.hasNext() && par1Stack != null) {
			Entry<ItemStack, Integer> entry = iterator.next().getValue();
			if( entry.getKey().isItemEqual(par1Stack) ) {
                return entry.getValue();
            }
		}
		return 0;
	}


	public static boolean disableBiome(int biomeID) {
		if( disabledBiomesList.contains(biomeID) ) {
            return false;
        }

		disabledBiomesList.add(biomeID);
		return true;
	}

	public static List<Integer> getDisabledBiomes() {
		return new ArrayList<Integer>(disabledBiomesList);
	}

	static {
		addNewFuel(new ItemStack(Item.diamond), 1);
		addNewFuel(new ItemStack(ItemRegistry.endIngot), 2);
		addNewFuel(new ItemStack(Item.ingotGold), 2);
		addNewFuel(new ItemStack(Item.emerald), 4);
		addNewFuel(new ItemStack(Item.ingotIron), 8);
		addNewFuel(new ItemStack(Item.redstone), 16);

		disableBiome(BiomeGenBase.ocean.biomeID);
		disableBiome(BiomeGenBase.river.biomeID);
		disableBiome(BiomeGenBase.frozenOcean.biomeID);
		disableBiome(BiomeGenBase.frozenRiver.biomeID);
		disableBiome(BiomeGenBase.mushroomIslandShore.biomeID);
		disableBiome(BiomeGenBase.beach.biomeID);
		disableBiome(BiomeGenBase.iceMountains.biomeID);
		disableBiome(BiomeGenBase.desertHills.biomeID);
		disableBiome(BiomeGenBase.forestHills.biomeID);
		disableBiome(BiomeGenBase.taigaHills.biomeID);
		disableBiome(BiomeGenBase.extremeHillsEdge.biomeID);
		disableBiome(BiomeGenBase.jungleHills.biomeID);
	}
}
