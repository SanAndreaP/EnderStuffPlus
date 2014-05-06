package de.sanandrew.mods.enderstuffplus.registry.event;

import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import de.sanandrew.core.manpack.util.event.entity.EnderFacingEvent;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;

public class EnderEvents
{
    @SubscribeEvent
    public void onEnderTeleport(EnderTeleportEvent evt) {
        if( evt.entityLiving instanceof EntityPlayer ) {
            EntityPlayer player = (EntityPlayer) evt.entityLiving;
            if( ESPModRegistry.hasPlayerFullNiob(player) ) {
                evt.attackDamage = 0;
                player.inventory.damageArmor(1);
            }
        }
    }
    
    @SubscribeEvent
    public void onEnderFacing(EnderFacingEvent event) {
//        event.entityEnderman.motionY = 0.2F;
        event.setCanceled(ESPModRegistry.hasPlayerFullNiob(event.entityPlayer));
    }
}
