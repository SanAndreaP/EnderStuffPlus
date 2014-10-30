package de.sanandrew.mods.enderstuffplus.packet;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffplus.client.particle.ParticleFXFuncCollection;

public class PacketFXRayball
    implements IPacket
{
    private Triplet<Double, Double, Double> pos;

    public PacketFXRayball() { }

    public PacketFXRayball(double posX, double posY, double posZ) {
        this.pos = Triplet.with(posX, posY, posZ);
    }

    @Override
    public void readBytes(ByteBuf bytes) {
        this.pos = Triplet.with(bytes.readDouble(), bytes.readDouble(), bytes.readDouble());
    }

    @Override
    public void writeBytes(ByteBuf bytes) {
        bytes.writeDouble(this.pos.getValue0());
        bytes.writeDouble(this.pos.getValue1());
        bytes.writeDouble(this.pos.getValue2());
    }

    @Override
    public void handleClientSide(NetHandlerPlayClient nhClient) {
        ParticleFXFuncCollection.spawnRayballFX(Minecraft.getMinecraft().theWorld, this.pos.getValue0(), this.pos.getValue1(), this.pos.getValue2());
    }

    @Override
    public void handleServerSide(NetHandlerPlayServer nhServer) { }
}
