/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util.modcompat;

import de.sanandrew.core.manpack.util.UsedByReflection;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.modcompatibility.IModInitHelper;
import de.sanandrew.mods.enderstuffp.block.BlockEndOre;
import de.sanandrew.mods.enderstuffp.item.ItemEnderIngot;
import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import thermalexpansion.util.crafting.PulverizerManager;
import thermalfoundation.item.TFItems;

@UsedByReflection
public final class ThermalExpansionInitHelper
        implements IModInitHelper
{
    private static Item endPulvers;

    @Override
    public void preInitialize() {
        endPulvers = new ItemEnderPulver();

        SAPUtils.registerItems(endPulvers);
    }

    public void initialize() {
        PulverizerManager.addOreToDustRecipe(4000, new ItemStack(EspBlocks.endOre, 1, BlockEndOre.NIOBIUM),
                                             new ItemStack(endPulvers, 2, ItemEnderPulver.NIOBIUM), TFItems.dustEnderium, 5);
        PulverizerManager.addOreToDustRecipe(4000, new ItemStack(EspBlocks.endOre, 1, BlockEndOre.TANTALUM),
                                             new ItemStack(endPulvers, 2, ItemEnderPulver.TANTALUM), TFItems.dustEnderium, 5);
        PulverizerManager.addRecipe(2400, new ItemStack(EspItems.endIngot, 1, ItemEnderIngot.NIOBIUM), new ItemStack(endPulvers, 1, ItemEnderPulver.NIOBIUM));
        PulverizerManager.addRecipe(2400, new ItemStack(EspItems.endIngot, 1, ItemEnderIngot.TANTALUM), new ItemStack(endPulvers, 1, ItemEnderPulver.TANTALUM));

        FurnaceRecipes.smelting().func_151394_a(new ItemStack(endPulvers, 1, ItemEnderPulver.NIOBIUM), new ItemStack(EspItems.endIngot, 1, ItemEnderIngot.NIOBIUM),
                                                1.1F);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(endPulvers, 1, ItemEnderPulver.TANTALUM), new ItemStack(EspItems.endIngot, 1, ItemEnderIngot.TANTALUM),
                                                1.1F);
    }

    @Override
    public void postInitialize() {

    }
}
