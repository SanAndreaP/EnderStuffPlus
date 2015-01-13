/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util.modcompat.tconstruct;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.modcompatibility.IModInitHelper;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tconstruct.library.TConstructRegistry;

public class TinkersConstructInitHelper
        implements IModInitHelper
{
    private static Item niobBowstring;

    @Override
    public void preInitialize() {
        niobBowstring = new ItemNiobiumBowstring();

        SAPUtils.registerItems(niobBowstring);
    }

    @Override
    public void initialize() {
        TConstructRegistry.addBowstringMaterial(1, 2, new ItemStack(EspItems.enderIngot, 1, 0), new ItemStack(niobBowstring, 1, 0), 1F, 1F, 1F); // String
    }

    @Override
    public void postInitialize() {

    }
}
