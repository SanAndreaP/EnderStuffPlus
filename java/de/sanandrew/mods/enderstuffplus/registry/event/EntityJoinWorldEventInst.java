package de.sanandrew.mods.enderstuffplus.registry.event;

import net.minecraft.entity.item.EntityItem;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import de.sanandrew.mods.enderstuffplus.entity.item.EntityItemTantal;
import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class EntityJoinWorldEventInst
{
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if( !event.world.isRemote && event.entity instanceof EntityItem && !(event.entity instanceof EntityItemTantal) ) {
            EntityItem eItem = (EntityItem) event.entity;
            if( eItem.getEntityItem() != null && eItem.getEntityItem().getItem() == ModItemRegistry.tantalPick ) {
                EntityItemTantal tantalItem = new EntityItemTantal(event.world, eItem, eItem.getEntityItem());
                event.world.spawnEntityInWorld(tantalItem);
                eItem.setDead();
            }
        }
    }
}
