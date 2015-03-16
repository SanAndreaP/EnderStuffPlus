/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.item;

import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import de.sanandrew.mods.enderstuffp.world.WorldGenAvisNest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class ItemWorldGenSpawner
        extends Item
{
    public ItemWorldGenSpawner() {
        super();
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":testItm");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float offX, float offY, float offZ) {
        if( !world.isRemote ) {
            new WorldGenAvisNest().generate(world, new Random(), x, y, z);
        }

        return super.onItemUse(stack, player, world, x, y, z, side, offX, offY, offZ);
    }
}
