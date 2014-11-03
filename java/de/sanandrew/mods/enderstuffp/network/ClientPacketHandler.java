/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.client.network.NetHandlerPlayClient;

public class ClientPacketHandler
        extends ServerPacketHandler
{
    @SubscribeEvent
    public void onClientPacket(ClientCustomPacketEvent event) {
        NetHandlerPlayClient netHandlerPlayClient = (NetHandlerPlayClient) event.handler;

        if( event.packet.channel().equals(EnderStuffPlus.MOD_CHANNEL) ) {
            PacketProcessor.processPacket(event.packet.payload(), event.packet.getTarget(), netHandlerPlayClient);
        }
    }
}
