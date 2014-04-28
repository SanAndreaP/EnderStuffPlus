package de.sanandrew.mods.enderstuffplus.registry.event;

import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;

public class EnderTeleportEventInst
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
}
