package sanandreasp.mods.EnderStuffPlus.registry.event;

import sanandreasp.mods.EnderStuffPlus.entity.item.EntityItemTantal;
import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import net.minecraft.entity.item.EntityItem;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntityJoinWorldEventInst
{
    @ForgeSubscribe
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
