package sanandreasp.mods.EnderStuffPlus.packet;

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
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Exception {
        int id = (Integer) data[0];
        int x = (Integer) data[1];
        int y = (Integer) data[2];
        int z = (Integer) data[3];
        EntityPlayer player = (EntityPlayer) data[4];

        if( player.worldObj.isRemote ) {
            player.openGui(ESPModRegistry.instance, id, player.worldObj, x, y, z);
        }

        doStream.writeInt(id);
        doStream.writeInt(x);
        doStream.writeInt(y);
        doStream.writeInt(z);
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Exception {
        int id = diStream.readInt();
        int x = diStream.readInt();
        int y = diStream.readInt();
        int z = diStream.readInt();

        ((EntityPlayer) player).openGui(ESPModRegistry.instance, id, ((EntityPlayer) player).worldObj, x, y, z);
    }
}
