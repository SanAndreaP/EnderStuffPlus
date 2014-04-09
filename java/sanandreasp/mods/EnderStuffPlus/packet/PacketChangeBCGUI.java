package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;

import cpw.mods.fml.common.network.Player;

public class PacketChangeBCGUI
    implements ISAPPacketHandler
{
    @Override
    public byte[] getDataForPacket(Object... data) throws Exception {
        int id = (Integer) data[0];
        int x = (Integer) data[1];
        int y = (Integer) data[2];
        int z = (Integer) data[3];
        EntityPlayer player = (EntityPlayer) data[4];

        if( player.worldObj.isRemote ) {
            player.openGui(ESPModRegistry.instance, id, player.worldObj, x, y, z);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        dos.writeInt(id);
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeInt(z);

        byte[] bytes = bos.toByteArray();

        dos.close();
        bos.close();

        return bytes;
    }

    @Override
    public void processData(INetworkManager manager, Player player, byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);

        int id = dis.readInt();
        int x = dis.readInt();
        int y = dis.readInt();
        int z = dis.readInt();

        ((EntityPlayer) player).openGui(ESPModRegistry.instance, id, ((EntityPlayer) player).worldObj, x, y, z);

        dis.close();
        bis.close();
    }
}
