/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util.manager;

import cpw.mods.fml.common.FMLLog;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class OreGeneratorManager
{
    private static final Map<ItemStack, Triplet<Integer, Integer, Boolean>> FUELS = new HashMap<>();

    public static void initialize() {
        addFuel(new ItemStack(Items.coal, 1, 0), 40, 250, true);
        addFuel(new ItemStack(Items.iron_ingot, 1, 0), 40, 500, true);
    }

    /**
     * Adds an item to the list of possible fuels for the Ore Generator.
     *
     * @param stack         - The item which should be registered.
     *                      Use OreDictionary.WILDCARD_VALUE as damage value to tell the generator to ignore the damage value of the item.
     * @param fluxPerTick   - The RF this item will generate per tick (1/20th of a second)
     * @param burnTime      - The time until the item burns out, in seconds (20 ticks)
     * @param ignoreNbt     - True, if the generator should also compare its NBT, otherwise false. Useful for items whose type is defined inside the NBT.
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

    public static boolean isFuelValid(ItemStack stack) {
        for( Entry<ItemStack, Triplet<Integer, Integer, Boolean>> fuel : FUELS.entrySet() ) {
            if( SAPUtils.areStacksEqual(fuel.getKey(), stack, fuel.getValue().getValue2()) ) {
                return true;
            }
        }

        return false;
    }
}
