/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.item;

import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.item.Item;

public class ItemStandard
        extends Item
{
    public ItemStandard(String name, String texture) {
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ':' + name);
        this.setTextureName(EnderStuffPlus.MOD_ID + ':' + texture);
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
    }
}
