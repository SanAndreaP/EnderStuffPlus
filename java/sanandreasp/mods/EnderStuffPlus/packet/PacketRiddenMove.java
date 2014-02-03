package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import cpw.mods.fml.common.network.Player;

public class PacketRiddenMove implements ISAPPacketHandler
{
	@Override
	public byte[] getDataForPacket(Object... data) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		
		dos.writeFloat((Float)data[0]);
		dos.writeFloat((Float)data[1]);
		
		byte[] bytes = bos.toByteArray();
		
		dos.close();
		bos.close();
		
		return bytes;
	}

	@Override
	public void processData(INetworkManager manager, Player player, byte[] data) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bis);
		
		EntityPlayer eplayer = (EntityPlayer)player;

		IEnderPet entity = (IEnderPet) (eplayer.ridingEntity != null && eplayer.ridingEntity instanceof IEnderPet ? eplayer.ridingEntity : null);
		if( entity != null ) {
			entity.setPetMoveForward(dis.readFloat());
			entity.setPetMoveStrafe(dis.readFloat());
		}
		
		dis.close();
		bis.close();
	}

}
