package de.sanandrew.mods.enderstuffp.event;

import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderIgnis;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderNivis;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.core.manpack.util.event.entity.EnderFacingEvent;
import de.sanandrew.core.manpack.util.event.entity.EnderSpawnParticleEvent;
import de.sanandrew.core.manpack.util.event.entity.EnderSpawnParticleEvent.EnderParticleType;
import de.sanandrew.mods.enderstuffplus.entity.living.monster.EntityEnderIgnis;
import de.sanandrew.mods.enderstuffplus.entity.living.monster.EntityEnderNivis;
import de.sanandrew.mods.enderstuffplus.packet.PacketFXCstPortal;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;

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

    @SubscribeEvent
    public void onEnderParticle(EnderSpawnParticleEvent event) {
        float[] particleData = null;
        if( event.entityEnderman instanceof EntityEnderNivis ) {
            particleData = new float[] {0F, 0.2F, 1F};
        } else if( event.entityEnderman instanceof EntityEnderIgnis ) {
            particleData = new float[] {1F, 0.9F, 0F};
        }

        if( particleData != null ) {
            if( !event.entityEnderman.worldObj.isRemote ) {
                if( event.particleType == EnderParticleType.IDLE_FX ) {
                    IPacket packet = new PacketFXCstPortal(event.entityEnderman.posX, event.entityEnderman.posY, event.entityEnderman.posZ, particleData[0],
                                                          particleData[1], particleData[2], event.entityEnderman.width, event.entityEnderman.height, 2);
                    ESPModRegistry.channelHandler.sendToAllAround(packet, new TargetPoint(event.entityEnderman.dimension, event.entityEnderman.posX,
                                                                                          event.entityEnderman.posY, event.entityEnderman.posZ, 64));
                } else if( event.particleType == EnderParticleType.TELEPORT_FX ) {
                    IPacket packet = new PacketFXCstPortal(event.getX(), event.getY(), event.getZ(), particleData[0], particleData[1], particleData[2],
                                                          event.entityEnderman.width, event.entityEnderman.height);
                    ESPModRegistry.channelHandler.sendToAllAround(packet, new TargetPoint(event.entityEnderman.dimension, event.getX(), event.getY(),
                                                                                          event.getZ(), 64));
                }
            }
            event.setCanceled(true);
        }
    }
}
