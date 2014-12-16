package de.sanandrew.mods.enderstuffp.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.enderstuffp.entity.item.EntityItemTantal;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntityJoinWorldHandler
{
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if( !event.world.isRemote && event.entity instanceof EntityItem && !(event.entity instanceof EntityItemTantal) ) {
            EntityItem eItem = (EntityItem) event.entity;
            if( eItem.getEntityItem() != null && eItem.getEntityItem().getItem() == EspItems.tantalPick ) {
                EntityItemTantal tantalItem = new EntityItemTantal(event.world, eItem, eItem.getEntityItem());
                event.world.spawnEntityInWorld(tantalItem);
                event.setCanceled(true);
            }
        }
    }
}
