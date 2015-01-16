/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.network;

import de.sanandrew.core.manpack.network.NetworkManager;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.enderstuffp.network.packet.*;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.entity.player.EntityPlayerMP;

public final class PacketManager
{
    public static final short PKG_PARTICLES = 0;
    public static final short PKG_OPEN_CLIENT_GUI = 1;
    public static final short ENDERPET_ACTION = 2;
    public static final short WEATHERALTAR_SET = 3;
    public static final short TILE_DATA_SYNC = 4;
    public static final short BIOME_CHANGER_ACTIONS = 5;

    public static void registerPackets() {
        NetworkManager.registerModHandler(EnderStuffPlus.MOD_ID, EnderStuffPlus.MOD_CHANNEL);

        NetworkManager.registerModPacketCls(EnderStuffPlus.MOD_ID, PKG_PARTICLES, PacketParticleFX.class);
        NetworkManager.registerModPacketCls(EnderStuffPlus.MOD_ID, PKG_OPEN_CLIENT_GUI, PacketRemoteOpenGui.class);
        NetworkManager.registerModPacketCls(EnderStuffPlus.MOD_ID, ENDERPET_ACTION, PacketEnderPetGuiAction.class);
        NetworkManager.registerModPacketCls(EnderStuffPlus.MOD_ID, WEATHERALTAR_SET, PacketSetWeather.class);
        NetworkManager.registerModPacketCls(EnderStuffPlus.MOD_ID, TILE_DATA_SYNC, PacketTileDataSync.class);
        NetworkManager.registerModPacketCls(EnderStuffPlus.MOD_ID, BIOME_CHANGER_ACTIONS, PacketBiomeChangerActions.class);
    }

    public static void sendToServer(short packet, Tuple data) {
        NetworkManager.sendToServer(EnderStuffPlus.MOD_ID, packet, data);
    }

    public static void sendToAll(short packed, Tuple data) {
        NetworkManager.sendToAll(EnderStuffPlus.MOD_ID, packed, data);
    }

    public static void sendToPlayer(short packed, EntityPlayerMP player, Tuple data) {
        NetworkManager.sendToPlayer(EnderStuffPlus.MOD_ID, packed, player, data);
    }

    public static void sendToAllInDimension(short packed, int dimensionId, Tuple data) {
        NetworkManager.sendToAllInDimension(EnderStuffPlus.MOD_ID, packed, dimensionId, data);
    }

    public static void sendToAllAround(short packed, int dimensionId, double x, double y, double z, double range, Tuple data) {
        NetworkManager.sendToAllAround(EnderStuffPlus.MOD_ID, packed, dimensionId, x, y, z, range, data);
    }
}
