package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;
import cpw.mods.fml.common.network.Player;

public class PacketSetWeather implements ISAPPacketHandler
{
	@Override
	public byte[] getDataForPacket(Object... data) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		
		dos.writeInt((Integer)data[0]);
		dos.writeInt((Integer)data[1]);
		dos.writeInt((Integer)data[2]);
		dos.writeInt((Integer)data[3]);
		dos.writeInt((Integer)data[4]);
		
		byte[] bytes = bos.toByteArray();
		
		dos.close();
		bos.close();
		
		return bytes;
	}

	@Override
	public void processData(INetworkManager manager, Player player, byte[] data) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bis);

		int weatherID = dis.readInt();
		int duration = dis.readInt();
		TileEntityWeatherAltar altar = (TileEntityWeatherAltar) ((EntityPlayer)player).worldObj.getBlockTileEntity(dis.readInt(), dis.readInt(), dis.readInt());
		if( altar != null )
			altar.setWeather(weatherID, duration);
		
		dis.close();
		bis.close();
	}
}
