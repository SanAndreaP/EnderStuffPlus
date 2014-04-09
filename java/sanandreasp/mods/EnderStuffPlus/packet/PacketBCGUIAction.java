package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    public byte[] getDataForPacket(Object... data) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        dos.writeInt(((TileEntity) data[0]).xCoord);
        dos.writeInt(((TileEntity) data[0]).yCoord);
        dos.writeInt(((TileEntity) data[0]).zCoord);
        dos.writeByte(((Byte) data[1]));
        dos.writeInt(((Integer) data[2]));

        byte[] bytes = bos.toByteArray();

        dos.close();
        bos.close();

        return bytes;
    }

    @Override
    public void processData(INetworkManager manager, Player player, byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);

        WorldServer serverWorld = (WorldServer) ((EntityPlayerMP) player).worldObj;
        int posX = dis.readInt();
        int posY = dis.readInt();
        int posZ = dis.readInt();
        TileEntityBiomeChanger bcte = (TileEntityBiomeChanger) serverWorld.getBlockTileEntity(posX, posY, posZ);
        switch( dis.readByte() ){
            case 0 :
                bcte.setActive(!bcte.isActive());
                break;
            case 1 :
                bcte.setBiomeID(dis.readInt());
                bcte.setCurrRange(0);
                break;
            case 2 :
                bcte.setMaxRange(dis.readInt());
                bcte.setCurrRange(0);
                break;
            case 3 :
                bcte.setRadForm((byte) (dis.readInt() & 255));
                break;
            case 4 :
                bcte.isReplacingBlocks = !bcte.isReplacingBlocks;
                break;
        }
        bcte.onInventoryChanged();
        serverWorld.markBlockForUpdate(posX, posY, posZ);

        dis.close();
        bis.close();
    }

}
