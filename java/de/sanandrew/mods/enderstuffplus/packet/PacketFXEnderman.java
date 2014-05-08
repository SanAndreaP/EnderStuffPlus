package de.sanandrew.mods.enderstuffplus.packet;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffplus.client.particle.ParticleFXFuncCollection;

public class PacketFXEnderman
    implements IPacket
{
    private Triplet<Double, Double, Double> pos;
    private Quintet<Float, Float, Float, Float, Float> data;
    private short forcnt;
    
    public PacketFXEnderman() { }
    
    public PacketFXEnderman(double x, double y, double z, float data1, float data2, float data3, float width, float height, int forCntMax) {
        this.pos = Triplet.with(x, y, z);
        this.data = Quintet.with(data1,  data2,  data3, width, height);
        this.forcnt = (short)forCntMax;
    }
    
    public PacketFXEnderman(double x, double y, double z, float data1, float data2, float data3, float width, float height) {
        this(x, y, z, data1, data2, data3, width, height, 1);
    }

    @Override
    public void readBytes(ByteBuf bytes) {
        this.pos = Triplet.with(bytes.readDouble(), bytes.readDouble(), bytes.readDouble());
        this.data = Quintet.with(bytes.readFloat(), bytes.readFloat(), bytes.readFloat(), bytes.readFloat(), bytes.readFloat());
        this.forcnt = bytes.readShort();
    }

    @Override
    public void writeBytes(ByteBuf bytes) {
        bytes.writeDouble(this.pos.getValue0());
        bytes.writeDouble(this.pos.getValue1());
        bytes.writeDouble(this.pos.getValue2());
        bytes.writeFloat(this.data.getValue0());
        bytes.writeFloat(this.data.getValue1());
        bytes.writeFloat(this.data.getValue2());
        bytes.writeFloat(this.data.getValue3());
        bytes.writeFloat(this.data.getValue4());
        bytes.writeShort(this.forcnt);
    }

    @Override
    public void handleClientSide(NetHandlerPlayClient nhClient) {
        ParticleFXFuncCollection.spawnPortalFX(Minecraft.getMinecraft().theWorld, this.pos.getValue0(), this.pos.getValue1(), this.pos.getValue2(),
                                               this.data.getValue0(), this.data.getValue1(), this.data.getValue2(),
                                               this.data.getValue3(), this.data.getValue4(), this.forcnt);
    }

    @Override
    public void handleServerSide(NetHandlerPlayServer nhServer) { }
}
