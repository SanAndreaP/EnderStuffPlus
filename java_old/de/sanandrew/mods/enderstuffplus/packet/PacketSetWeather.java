package de.sanandrew.mods.enderstuffplus.packet;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityWeatherAltar;

public class PacketSetWeather
    implements IPacket
{
    private Triplet<Integer, Integer, Integer> altarPos;
    private byte wthId;
    private int dur;

    public PacketSetWeather() { }

    public PacketSetWeather(TileEntityWeatherAltar altar, int weatherId, int duration) {
        this.altarPos = Triplet.with(altar.xCoord, altar.yCoord, altar.zCoord);
        this.wthId = (byte) weatherId;
        this.dur = duration;
    }

    @Override
    public void readBytes(ByteBuf bytes) {
        this.altarPos = Triplet.with(bytes.readInt(), bytes.readInt(), bytes.readInt());
        this.wthId = bytes.readByte();
        this.dur = bytes.readInt();
    }

    @Override
    public void writeBytes(ByteBuf bytes) {
        bytes.writeInt(this.altarPos.getValue0());
        bytes.writeInt(this.altarPos.getValue1());
        bytes.writeInt(this.altarPos.getValue2());
        bytes.writeByte(this.wthId);
        bytes.writeInt(this.dur);
    }

    @Override
    public void handleClientSide(NetHandlerPlayClient nhClient) { }

    @Override
    public void handleServerSide(NetHandlerPlayServer nhServer) {
        TileEntityWeatherAltar teAltar = (TileEntityWeatherAltar) nhServer.playerEntity.worldObj.getTileEntity(this.altarPos.getValue0(), this.altarPos.getValue1(), this.altarPos.getValue2());
        teAltar.setWeather(this.wthId, this.dur);
    }
}
