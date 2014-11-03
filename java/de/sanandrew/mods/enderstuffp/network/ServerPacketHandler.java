/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.network.NetHandlerPlayServer;

public class ServerPacketHandler
{
    @SubscribeEvent
    public void onServerPacket(ServerCustomPacketEvent event) {
        NetHandlerPlayServer netHandlerPlayServer = (NetHandlerPlayServer) event.handler;

        if( event.packet.channel().equals(EnderStuffPlus.MOD_CHANNEL) ) {
            PacketProcessor.processPacket(event.packet.payload(), event.packet.getTarget(), netHandlerPlayServer);
        }
    }
}
