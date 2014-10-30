/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util;

import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabEnderStuff
        extends CreativeTabs
{
    public CreativeTabEnderStuff() {
        super(EnderStuffPlus.MOD_ID + ":csm_tab");
    }

    @Override
    public Item getTabIconItem() {
        return RegistryItems.dollSoldier;
    }
}
