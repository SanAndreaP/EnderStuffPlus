package de.sanandrew.mods.enderstuffp.util;

import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.*;
import java.util.Map.Entry;

public class RegistryBiomeChanger
{
    private static final List<Integer> disabledBiomesList = new ArrayList<>();
    private static final HashMap<Integer, Entry<ItemStack, Integer>> fuelList = Maps.newHashMap();

    public static void initialize() {
//        addNewFuel(new ItemStack(Items.diamond), 1);
//        addNewFuel(new ItemStack(RegistryItems.endIngot, 1, 0), 2);
//        addNewFuel(new ItemStack(Items.gold_ingot), 2);
//        addNewFuel(new ItemStack(Items.emerald), 4);
//        addNewFuel(new ItemStack(Items.iron_ingot), 8);
//        addNewFuel(new ItemStack(Items.redstone), 16);

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
        return new ArrayList<>(disabledBiomesList);
    }

    public static boolean isBiomeDisabled(BiomeGenBase biome) {
        return disabledBiomesList.contains(biome.biomeID);
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
