/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.network.packet;

import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.enderstuffp.network.IPacket;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityWeatherAltar;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;

import java.io.IOException;

public class PacketSetWeather
        implements IPacket
{
    @Override
    public void process(ByteBufInputStream stream, ByteBuf rawData, INetHandler handler) throws IOException {
        if( handler instanceof NetHandlerPlayServer ) {
            EntityPlayerMP playerMP = ((NetHandlerPlayServer) handler).playerEntity;
            TileEntityWeatherAltar altar = (TileEntityWeatherAltar) playerMP.worldObj.getTileEntity(stream.readInt(), stream.readInt(), stream.readInt());
            altar.setWeather(stream.readByte(), stream.readInt());
        }
    }

    @Override
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
        stream.writeInt((int) dataTuple.getValue(0));
        stream.writeInt((int) dataTuple.getValue(1));
        stream.writeInt((int) dataTuple.getValue(2));
        stream.writeByte((byte) dataTuple.getValue(3));
        stream.writeInt((int) dataTuple.getValue(4));
    }
}
