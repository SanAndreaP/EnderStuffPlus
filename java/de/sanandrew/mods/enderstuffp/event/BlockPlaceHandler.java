/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.BlockEvent;

public class BlockPlaceHandler
{

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        if( !event.world.isRemote && event.block == Blocks.dragon_egg && event.player != null ) {
//            event.getPlayer().entityDropItem(new ItemStack(Blocks.dragon_egg, 1), 0.0F);
        }
    }
}
