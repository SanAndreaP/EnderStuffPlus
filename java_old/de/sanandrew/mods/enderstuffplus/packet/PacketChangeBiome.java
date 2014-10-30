package de.sanandrew.mods.enderstuffplus.packet;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityBiomeChanger;

public class PacketChangeBiome
    implements IPacket
{
    private Triplet<Integer, Integer, Integer> pos;
    private int rng;

    public PacketChangeBiome() { }

    public PacketChangeBiome(TileEntity tile, int range) {
        this.pos = Triplet.with(tile.xCoord, tile.yCoord, tile.zCoord);
        this.rng = range;
    }

    @Override
    public void readBytes(ByteBuf bytes) {
        this.pos = Triplet.with(bytes.readInt(), bytes.readInt(), bytes.readInt());
        this.rng = bytes.readInt();
    }

    @Override
    public void writeBytes(ByteBuf bytes) {
        bytes.writeInt(this.pos.getValue0());
        bytes.writeInt(this.pos.getValue1());
        bytes.writeInt(this.pos.getValue2());
        bytes.writeInt(this.rng);
    }

    @Override
    public void handleClientSide(NetHandlerPlayClient nhClient) {
        TileEntity tile = Minecraft.getMinecraft().theWorld.getTileEntity(this.pos.getValue0(), this.pos.getValue1(), this.pos.getValue2());
        TileEntityBiomeChanger teBiomeChanger = (TileEntityBiomeChanger) tile;
        teBiomeChanger.changeBiome(this.rng, false);
        teBiomeChanger.setCurrRange(this.rng + 1);
    }

    @Override
    public void handleServerSide(NetHandlerPlayServer nhServer) { }
}
