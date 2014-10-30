package de.sanandrew.mods.enderstuffplus.packet;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityBiomeChanger;

public class PacketBCGUIAction
    implements IPacket
{
    private Triplet<Integer, Integer, Integer> tileCoords;
    private byte id;
    private int data;

    public PacketBCGUIAction() { }

    public PacketBCGUIAction(TileEntity tileEntity, int subId, int dataVal) {
        this.tileCoords = Triplet.with(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        this.id = (byte) subId;
        this.data = dataVal;
    }

    @Override
    public void readBytes(ByteBuf bytes) {
        this.tileCoords = Triplet.with(bytes.readInt(), bytes.readInt(), bytes.readInt());
        this.id = bytes.readByte();
        this.data = bytes.readInt();
    }

    @Override
    public void writeBytes(ByteBuf bytes) {
        bytes.writeInt(this.tileCoords.getValue0());
        bytes.writeInt(this.tileCoords.getValue1());
        bytes.writeInt(this.tileCoords.getValue2());
        bytes.writeByte(this.id);
        bytes.writeInt(this.data);
    }

    @Override
    public void handleClientSide(NetHandlerPlayClient nhClient) { }

    @Override
    public void handleServerSide(NetHandlerPlayServer nhServer) {
        World world = nhServer.playerEntity.worldObj;
        TileEntity tile = world.getTileEntity(this.tileCoords.getValue0(), this.tileCoords.getValue1(), this.tileCoords.getValue2());
        TileEntityBiomeChanger bcte = (TileEntityBiomeChanger) tile;
        switch( this.id ){
            case 0 :
                bcte.setActive(!bcte.isActive());
                break;
            case 1 :
                bcte.setBiomeID(this.data);
                bcte.setCurrRange(0);
                break;
            case 2 :
                bcte.setMaxRange(this.data);
                bcte.setCurrRange(0);
                break;
            case 3 :
                bcte.setRadForm((byte) (this.data & 255));
                break;
            case 4 :
                bcte.setReplacingBlocks(!bcte.isReplacingBlocks());
                break;
        }
        bcte.markDirty();
        world.markBlockForUpdate(this.tileCoords.getValue0(), this.tileCoords.getValue1(), this.tileCoords.getValue2());
    }
}
