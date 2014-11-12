/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.core.manpack.util.javatuples.Unit;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public final class PacketProcessor
{
    public static void processPacket(ByteBuf data, Side side, INetHandler handler) {
        short packetId = -1;
        try( ByteBufInputStream bbis = new ByteBufInputStream(data) ) {
            packetId = bbis.readShort();
            if( SAPUtils.isIndexInRange(EnumPacket.VALUES, packetId) ) {
                IPacket pktInst = EnumPacket.VALUES[packetId].packetCls.getConstructor().newInstance();
                pktInst.process(bbis, data, handler);
            }
        } catch( IOException ioe ) {
            FMLLog.log(EnderStuffPlus.MOD_LOG, Level.ERROR, "The packet with the ID %d cannot be processed!", packetId);
            ioe.printStackTrace();
        } catch( IllegalAccessException | InstantiationException rex ) {
            FMLLog.log(EnderStuffPlus.MOD_LOG, Level.ERROR, "The packet with the ID %d cannot be instantiated!", packetId);
            rex.printStackTrace();
        } catch( NoSuchMethodException | InvocationTargetException e ) {
            e.printStackTrace();
        }
    }

    public static void sendToServer(EnumPacket packed, Tuple data) {
        sendPacketTo(packed, EnumPacketDirections.TO_SERVER, null, data);
    }

    public static void sendToAll(EnumPacket packed, Tuple data) {
        sendPacketTo(packed, EnumPacketDirections.TO_ALL, null, data);
    }

    public static void sendToPlayer(EnumPacket packed, EntityPlayerMP player, Tuple data) {
        sendPacketTo(packed, EnumPacketDirections.TO_PLAYER, Unit.with(player), data);
    }

    public static void sendToAllInDimension(EnumPacket packed, int dimensionId, Tuple data) {
        sendPacketTo(packed, EnumPacketDirections.TO_ALL_IN_DIMENSION, Unit.with(dimensionId), data);
    }

    public static void sendToAllAround(EnumPacket packed, int dimensionId, double x, double y, double z, double range, Tuple data) {
        sendPacketTo(packed, EnumPacketDirections.TO_ALL_IN_RANGE, Quintet.with(dimensionId, x, y, z, range), data);
    }

    private static void sendPacketTo(EnumPacket packet, EnumPacketDirections direction, Tuple dirData, Tuple packetData) {
        try( ByteBufOutputStream bbos = new ByteBufOutputStream(Unpooled.buffer()) ) {
            bbos.writeShort(packet.ordinal());
            IPacket pktInst = packet.packetCls.getConstructor().newInstance();
            pktInst.writeData(bbos, packetData);
            FMLProxyPacket proxyPacket = new FMLProxyPacket(bbos.buffer(), EnderStuffPlus.MOD_CHANNEL);
            switch( direction ) {
                case TO_SERVER:
                    EnderStuffPlus.channel.sendToServer(proxyPacket);
                    break;
                case TO_ALL:
                    EnderStuffPlus.channel.sendToAll(proxyPacket);
                    break;
                case TO_PLAYER:
                    EnderStuffPlus.channel.sendTo(proxyPacket, (EntityPlayerMP) dirData.getValue(0));
                    break;
                case TO_ALL_IN_RANGE:
                    EnderStuffPlus.channel.sendToAllAround(proxyPacket, new NetworkRegistry.TargetPoint((int) dirData.getValue(0), (double) dirData.getValue(1),
                                                                                                        (double) dirData.getValue(2), (double) dirData.getValue(3),
                                                                                                        (double) dirData.getValue(4))
                    );
                    break;
                case TO_ALL_IN_DIMENSION:
                    EnderStuffPlus.channel.sendToDimension(proxyPacket, (int) dirData.getValue(0));
                    break;
            }
        } catch( IOException ioe ) {
            FMLLog.log(EnderStuffPlus.MOD_LOG, Level.ERROR, "The packet %s cannot be processed!", packet.toString());
            ioe.printStackTrace();
        } catch( IllegalAccessException | InstantiationException rex ) {
            FMLLog.log(EnderStuffPlus.MOD_LOG, Level.ERROR, "The packet %s cannot be instantiated!", packet.toString());
            rex.printStackTrace();
        } catch( NoSuchMethodException | InvocationTargetException e ) {
            e.printStackTrace();
        }
    }

    private static enum EnumPacketDirections
    {
        TO_SERVER, TO_PLAYER, TO_ALL, TO_ALL_IN_RANGE, TO_ALL_IN_DIMENSION
    }
}
