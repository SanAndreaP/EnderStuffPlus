package de.sanandrew.mods.enderstuffp.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.enderstuffp.entity.item.EntityItemFireproof;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntityJoinWorldHandler
{
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if( !event.world.isRemote && event.entity instanceof EntityItem && !(event.entity instanceof EntityItemFireproof) ) {
            EntityItem eItem = (EntityItem) event.entity;
            ItemStack stack = eItem.getEntityItem();
            if( stack != null && stack.hasTagCompound() && stack.getTagCompound().getBoolean(EnderStuffPlus.MOD_ID + ":fireproof")) {
                EntityItemFireproof tantalItem = new EntityItemFireproof(event.world, eItem, eItem.getEntityItem());
                event.world.spawnEntityInWorld(tantalItem);
                event.setCanceled(true);
            }
        }
    }
}
