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
//    @Override
//    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Throwable {
//        doStream.writeInt(((TileEntity) data[0]).xCoord);
//        doStream.writeInt(((TileEntity) data[0]).yCoord);
//        doStream.writeInt(((TileEntity) data[0]).zCoord);
//        doStream.writeByte(((Byte) data[1]));
//        doStream.writeInt(((Integer) data[2]));
//    }
//
//    @Override
//    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Throwable {
//        WorldServer serverWorld = (WorldServer) ((EntityPlayerMP) player).worldObj;
//        int posX = diStream.readInt();
//        int posY = diStream.readInt();
//        int posZ = diStream.readInt();
//        TileEntityBiomeChanger bcte = (TileEntityBiomeChanger) serverWorld.getBlockTileEntity(posX, posY, posZ);
//        switch( diStream.readByte() ){
//            case 0 :
//                bcte.setActive(!bcte.isActive());
//                break;
//            case 1 :
//                bcte.setBiomeID(diStream.readInt());
//                bcte.setCurrRange(0);
//                break;
//            case 2 :
//                bcte.setMaxRange(diStream.readInt());
//                bcte.setCurrRange(0);
//                break;
//            case 3 :
//                bcte.setRadForm((byte) (diStream.readInt() & 255));
//                break;
//            case 4 :
//                bcte.setReplacingBlocks(!bcte.isReplacingBlocks());
//                break;
//        }
//        bcte.onInventoryChanged();
//        serverWorld.markBlockForUpdate(posX, posY, posZ);
//    }

}
