package de.sanandrew.mods.enderstuffplus.packet;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;

public class PacketChangeBCGUI
    implements IPacket
{
    private Triplet<Integer, Integer, Integer> pos;
    private int theId;

    public PacketChangeBCGUI() { }

    public PacketChangeBCGUI(int id, int x, int y, int z) {
        this.theId = id;
        this.pos = Triplet.with(x, y, z);
    }

    @Override
    public void readBytes(ByteBuf bytes) {
        this.theId = bytes.readInt();
        this.pos = Triplet.with(bytes.readInt(), bytes.readInt(), bytes.readInt());
    }

    @Override
    public void writeBytes(ByteBuf bytes) {
        bytes.writeInt(this.theId);
        bytes.writeInt(this.pos.getValue0());
        bytes.writeInt(this.pos.getValue1());
        bytes.writeInt(this.pos.getValue2());
    }

    @Override
    public void handleClientSide(NetHandlerPlayClient nhClient) { }

    @Override
    public void handleServerSide(NetHandlerPlayServer nhServer) {
        nhServer.playerEntity.openGui(ESPModRegistry.instance, this.theId, nhServer.playerEntity.worldObj, this.pos.getValue0(), this.pos.getValue1(),
                                      this.pos.getValue2());
    }
}
