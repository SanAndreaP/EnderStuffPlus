package de.sanandrew.mods.enderstuffplus.packet;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityDuplicator;

public class PacketDupeInsertLevels
    implements IPacket
{
    private Triplet<Integer, Integer, Integer> pos;

    public PacketDupeInsertLevels() { }

    public PacketDupeInsertLevels(TileEntity tile) {
        this.pos = Triplet.with(tile.xCoord, tile.yCoord, tile.zCoord);
    }

    @Override
    public void readBytes(ByteBuf bytes) {
        this.pos = Triplet.with(bytes.readInt(), bytes.readInt(), bytes.readInt());
    }

    @Override
    public void writeBytes(ByteBuf bytes) {
        bytes.writeInt(this.pos.getValue0());
        bytes.writeInt(this.pos.getValue1());
        bytes.writeInt(this.pos.getValue2());
    }

    @Override
    public void handleClientSide(NetHandlerPlayClient nhClient) { }

    @Override
    public void handleServerSide(NetHandlerPlayServer nhServer) {
        TileEntity tile = nhServer.playerEntity.worldObj.getTileEntity(this.pos.getValue0(), this.pos.getValue1(), this.pos.getValue2());
        TileEntityDuplicator dupe = (TileEntityDuplicator) tile;

        if( nhServer.playerEntity.capabilities.isCreativeMode ) {
            dupe.addLevels(50);
        } else {
            dupe.addLevels(Math.min(nhServer.playerEntity.experienceLevel, 10));
            nhServer.playerEntity.addExperienceLevel(-Math.min(nhServer.playerEntity.experienceLevel, 10));
        }

        dupe.markDirty();
        nhServer.playerEntity.worldObj.markBlockForUpdate(this.pos.getValue0(), this.pos.getValue1(), this.pos.getValue2());
    }
}
