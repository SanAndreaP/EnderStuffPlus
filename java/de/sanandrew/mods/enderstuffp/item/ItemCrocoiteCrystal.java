/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.item;

import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemCrocoiteCrystal
        extends Item
{
    public ItemCrocoiteCrystal() {
        super();
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":crocoiteCrystal");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":crocoite");
    }
}
