package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.world.WorldServer;
import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import cpw.mods.fml.common.network.Player;

public class PacketSetEnderName implements ISAPPacketHandler
{
	@Override
	public byte[] getDataForPacket(Object... data) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		
		dos.writeInt((Integer)data[0]);
		dos.writeUTF((String)data[1]);
		
		byte[] bytes = bos.toByteArray();
		
		dos.close();
		bos.close();
		
		return bytes;
	}

	@Override
	public void processData(INetworkManager manager, Player player, byte[] data) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bis);
		
		WorldServer serverWorld = (WorldServer) ((EntityPlayerMP)player).worldObj;
		EntityLiving entity = (EntityLiving) serverWorld.getEntityByID(dis.readInt());
		if( entity != null ) {
			if( entity instanceof IEnderPet ) {
				((IEnderPet)entity).setName(dis.readUTF());
			}
		}
		
		dis.close();
		bis.close();
	}
}
