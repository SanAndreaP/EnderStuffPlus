package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import cpw.mods.fml.common.network.Player;

public class PacketBCGUIAction
    implements ISAPPacketHandler
{
    @Override
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Throwable {
        doStream.writeInt(((TileEntity) data[0]).xCoord);
        doStream.writeInt(((TileEntity) data[0]).yCoord);
        doStream.writeInt(((TileEntity) data[0]).zCoord);
        doStream.writeByte(((Byte) data[1]));
        doStream.writeInt(((Integer) data[2]));
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Throwable {
        WorldServer serverWorld = (WorldServer) ((EntityPlayerMP) player).worldObj;
        int posX = diStream.readInt();
        int posY = diStream.readInt();
        int posZ = diStream.readInt();
        TileEntityBiomeChanger bcte = (TileEntityBiomeChanger) serverWorld.getBlockTileEntity(posX, posY, posZ);
        switch( diStream.readByte() ){
            case 0 :
                bcte.setActive(!bcte.isActive());
                break;
            case 1 :
                bcte.setBiomeID(diStream.readInt());
                bcte.setCurrRange(0);
                break;
            case 2 :
                bcte.setMaxRange(diStream.readInt());
                bcte.setCurrRange(0);
                break;
            case 3 :
                bcte.setRadForm((byte) (diStream.readInt() & 255));
                break;
            case 4 :
                bcte.setReplacingBlocks(!bcte.isReplacingBlocks());
                break;
        }
        bcte.onInventoryChanged();
        serverWorld.markBlockForUpdate(posX, posY, posZ);
    }
}
