/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util.manager;

import cpw.mods.fml.common.FMLLog;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumEnderOres;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class OreGeneratorManager
{
    private static boolean isInitialized = false;
    private static final Map<ItemStack, Triplet<Integer, Integer, Boolean>> FUELS = new HashMap<>();

    public static void initialize() {
        if( !isInitialized ) {
            isInitialized = true;

            addFuel(new ItemStack(Items.coal, 1, 0), 40, 250, true);
            addFuel(new ItemStack(Items.iron_ingot, 1, OreDictionary.WILDCARD_VALUE), 40, 500, true);
            addFuel(new ItemStack(Items.gold_ingot, 1, OreDictionary.WILDCARD_VALUE), 80, 1200, true);
            addFuel(new ItemStack(Items.dye, 1, 4), 40, 300, true);
            addFuel(new ItemStack(Items.redstone, 1, OreDictionary.WILDCARD_VALUE), 5, 250, true);
            addFuel(new ItemStack(Items.emerald, 1, OreDictionary.WILDCARD_VALUE), 60, 900, true);
            addFuel(new ItemStack(Items.diamond, 1, OreDictionary.WILDCARD_VALUE), 120, 900, true);
            addFuel(new ItemStack(EspItems.enderIngot, 1, EnumEnderOres.NIOBIUM.ordinal()), 80, 600, true);
            addFuel(new ItemStack(EspItems.enderIngot, 1, EnumEnderOres.TANTALUM.ordinal()), 120, 800, true);
        } else {
            FMLLog.log(Level.WARN, EnderStuffPlus.MOD_LOG, "Can not initialize the OreGeneratorManager multiple times!");
        }
    }

    /**
     * Adds an item to the list of possible fuels for the Ore Generator.
     *
     * @param stack         The item which should be registered.
     *                      Use OreDictionary.WILDCARD_VALUE as damage value to tell the generator to ignore the damage value of the item.
     * @param fluxPerTick   The RF this item will generate per tick (1/20th of a second)
     * @param burnTime      The time until the item burns out, in seconds (20 ticks)
     * @param ignoreNbt     True, if the generator should also compare its NBT, otherwise false. Useful for items whose type is defined inside the NBT.
     */
    public static void addFuel(ItemStack stack, int fluxPerTick, int burnTime, boolean ignoreNbt) {
        if( stack == null ) {
            FMLLog.log(Level.WARN, EnderStuffPlus.MOD_LOG, "OreGeneratorManager: stack cannot be null!");
            return;
        }

        if( fluxPerTick <= 0 ) {
            FMLLog.log(Level.WARN, EnderStuffPlus.MOD_LOG, "OreGeneratorManager: fluxPerTick must be at least 1!");
            return;
        }

        if( burnTime <= 0 ) {
            FMLLog.log(Level.WARN, EnderStuffPlus.MOD_LOG, "OreGeneratorManager: burnTime must be at least 1!");
            return;
        }

        FUELS.put(stack.copy(), Triplet.with(fluxPerTick, burnTime, ignoreNbt));
    }

    /**
     * Gets the fuel values for the specific item.
     * @param stack The stack
     * @return a Pair&lt;&gt; holding the flux/tick and the burn time (in s). If the item is not registered as fuel, it will return null.
     */
    public static Pair<Integer, Integer> getFuelValues(ItemStack stack) {
        for( Entry<ItemStack, Triplet<Integer, Integer, Boolean>> fuel : FUELS.entrySet() ) {
            Triplet<Integer, Integer, Boolean> val = fuel.getValue();
            if( SAPUtils.areStacksEqual(fuel.getKey(), stack, val.getValue2()) ) {
                return Pair.with(val.getValue0(), val.getValue1());
            }
        }

        return null;
    }
}
