/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util.modcompat.thermalexpansion;

import de.sanandrew.core.manpack.util.UsedByReflection;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.modcompatibility.IModInitHelper;
import de.sanandrew.mods.enderstuffp.util.EnumEnderOres;
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
        PulverizerManager.addOreToDustRecipe(4000, new ItemStack(EspBlocks.enderOre, 1, EnumEnderOres.NIOBIUM.ordinal()),
                                             new ItemStack(endPulvers, 2, ItemEnderPulver.NIOBIUM), TFItems.dustEnderium, 5);
        PulverizerManager.addOreToDustRecipe(4000, new ItemStack(EspBlocks.enderOre, 1, EnumEnderOres.TANTALUM.ordinal()),
                                             new ItemStack(endPulvers, 2, ItemEnderPulver.TANTALUM), TFItems.dustEnderium, 5);
        PulverizerManager.addRecipe(2400, new ItemStack(EspItems.enderIngot, 1, EnumEnderOres.NIOBIUM.ordinal()),
                                    new ItemStack(endPulvers, 1, ItemEnderPulver.NIOBIUM));
        PulverizerManager.addRecipe(2400, new ItemStack(EspItems.enderIngot, 1, EnumEnderOres.TANTALUM.ordinal()),
                                    new ItemStack(endPulvers, 1, ItemEnderPulver.TANTALUM));

        FurnaceRecipes.smelting().func_151394_a(new ItemStack(endPulvers, 1, EnumEnderOres.NIOBIUM.ordinal()),
                                                new ItemStack(EspItems.enderIngot, 1, EnumEnderOres.NIOBIUM.ordinal()), 1.1F);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(endPulvers, 1, EnumEnderOres.TANTALUM.ordinal()),
                                                new ItemStack(EspItems.enderIngot, 1, EnumEnderOres.TANTALUM.ordinal()), 1.1F);
    }

    @Override
    public void postInitialize() {

    }
}
