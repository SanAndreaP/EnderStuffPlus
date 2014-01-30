package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;

public class PacketRecvSetWeather extends PacketBase {

	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		int weatherID = iStream.readInt();
		int duration = iStream.readInt();
		TileEntityWeatherAltar altar = (TileEntityWeatherAltar) player.worldObj.getBlockTileEntity(iStream.readInt(), iStream.readInt(), iStream.readInt());
		if( altar != null )
			altar.setWeather(weatherID, duration);
	}

	public static void send(int weatherID, int duration, int teX, int teY, int teZ, World world) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x006);
			dos.writeInt(weatherID);
			dos.writeInt(duration);
			dos.writeInt(teX);
			dos.writeInt(teY);
			dos.writeInt(teZ);
			
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()));
		} catch(IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvSetWeather to server!");
		} finally {
			try {
				if( dos != null) dos.close( );
				if( bos != null) bos.close( );
			} catch (IOException e) {
				;
			}
		}
	}
}
