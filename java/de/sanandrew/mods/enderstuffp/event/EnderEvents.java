package de.sanandrew.mods.enderstuffp.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.core.manpack.util.event.entity.EnderFacingEvent;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EnderEvents
{
    @SubscribeEvent
    public void onEnderTeleport(EnderTeleportEvent evt) {
        if( evt.entityLiving instanceof EntityPlayer ) {
            EntityPlayer player = (EntityPlayer) evt.entityLiving;
            if( EnderStuffPlus.hasPlayerFullNiob(player) ) {
                evt.attackDamage = 0;
                player.inventory.damageArmor(1);
            }
        }
    }

    @SubscribeEvent
    public void onEnderFacing(EnderFacingEvent event) {
        event.setCanceled(EnderStuffPlus.hasPlayerFullNiob(event.entityPlayer));
    }
}
