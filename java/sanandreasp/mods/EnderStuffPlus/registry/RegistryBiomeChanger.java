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

public class RegistryBiomeChanger
{
    private static final List<Integer> disabledBiomesList = new ArrayList<Integer>();
    private static final HashMap<Integer, Entry<ItemStack, Integer>> fuelList = Maps.newHashMap();

    public static void initialize() {
        addNewFuel(new ItemStack(Item.diamond), 1);
        addNewFuel(new ItemStack(ModItemRegistry.endIngot), 2);
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

    public static boolean addNewFuel(ItemStack stack, int multiplicator) {
        if( getMultiFromStack(stack) > 0 ) {
            return false;
        } else {
            fuelList.put(getNewFuelID(), new AbstractMap.SimpleEntry<ItemStack, Integer>(stack, multiplicator));
        }

        return true;
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

    public static HashMap<Integer, Entry<ItemStack, Integer>> getFuelList() {
        return Maps.newHashMap(fuelList);
    }

    public static int getMultiFromStack(ItemStack stack) {
        Iterator<Entry<Integer, Entry<ItemStack, Integer>>> iterator = fuelList.entrySet().iterator();
        while( iterator.hasNext() && stack != null ) {
            Entry<ItemStack, Integer> entry = iterator.next().getValue();

            if( entry.getKey().isItemEqual(stack) ) {
                return entry.getValue();
            }
        }

        return 0;
    }

    private static int getNewFuelID() {
        return fuelList.size();
    }
}
