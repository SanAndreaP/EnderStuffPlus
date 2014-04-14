package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.world.WorldServer;

import cpw.mods.fml.common.network.Player;

public class PacketSetEnderName
    implements ISAPPacketHandler
{
    @Override
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Exception {
        doStream.writeInt((Integer) data[0]); // entityId
        doStream.writeUTF((String) data[1]);  // name
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Exception {
        WorldServer serverWorld = (WorldServer) ((EntityPlayerMP) player).worldObj;
        EntityLiving entity = (EntityLiving) serverWorld.getEntityByID(diStream.readInt());

        if( entity instanceof IEnderPet ) {
            ((IEnderPet) entity).setName(diStream.readUTF());
        }
    }
}
