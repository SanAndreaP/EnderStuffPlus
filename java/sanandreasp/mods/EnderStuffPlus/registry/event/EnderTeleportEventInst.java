package sanandreasp.mods.EnderStuffPlus.registry.event;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EnderTeleportEventInst
{
    @ForgeSubscribe
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
