/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public final class CreativeTabsEnderStuff
{
    public static final CreativeTabs ESP_TAB =
            new CreativeTabs(EnderStuffPlus.MOD_ID + ":creativeTab") {
                @Override
                public Item getTabIconItem() {
                    return RegistryItems.niobPick;
                }
            };

    public static final CreativeTabs ESP_TAB_COATS =
            new CreativeTabs(EnderStuffPlus.MOD_ID + ":creativeTabCoats") {
                @Override
                public Item getTabIconItem() {
                    return RegistryItems.rainCoat;
                }
            };

}
