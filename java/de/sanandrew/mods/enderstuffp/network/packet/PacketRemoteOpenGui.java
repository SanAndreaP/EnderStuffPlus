/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.network.packet;

import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.enderstuffp.network.IPacket;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumGui;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.network.INetHandler;

import java.io.IOException;

public class PacketRemoteOpenGui
        implements IPacket
{
    @Override
    public void process(ByteBufInputStream stream, ByteBuf rawData, INetHandler handler) throws IOException {
        EnderStuffPlus.proxy.openGui(null, EnumGui.VALUES[stream.readByte()], stream.readInt(), stream.readInt(), stream.readInt());
    }

    @Override
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
        stream.writeByte((byte) dataTuple.getValue(0));
        stream.writeInt((int) dataTuple.getValue(1));
        stream.writeInt((int) dataTuple.getValue(2));
        stream.writeInt((int) dataTuple.getValue(3));
    }
}
