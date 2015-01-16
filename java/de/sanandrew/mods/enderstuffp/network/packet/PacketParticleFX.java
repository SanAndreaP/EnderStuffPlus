/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.network.packet;

import de.sanandrew.core.manpack.network.IPacket;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumParticleFx;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.network.INetHandler;

import java.io.IOException;

public class PacketParticleFX
        implements IPacket
{
    @Override
    public void process(ByteBufInputStream stream, ByteBuf rawData, INetHandler handler) throws IOException {
        EnderStuffPlus.proxy.handleParticle(EnumParticleFx.VALUES[stream.readByte()], stream.readDouble(), stream.readDouble(), stream.readDouble(),
                                           Tuple.readFromByteBufStream(stream));
    }

    @Override
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
        stream.writeByte((byte) dataTuple.getValue(0));
        stream.writeDouble((double) dataTuple.getValue(1));
        stream.writeDouble((double) dataTuple.getValue(2));
        stream.writeDouble((double) dataTuple.getValue(3));
        Tuple.writeToByteBufStream((Tuple) dataTuple.getValue(4), stream);
    }
}
