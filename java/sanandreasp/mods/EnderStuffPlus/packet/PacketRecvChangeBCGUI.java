package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;

public class PacketRecvChangeBCGUI extends PacketBase {

	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		int id = iStream.readInt(), x = iStream.readInt(), y = iStream.readInt(), z = iStream.readInt();
		player.openGui(ESPModRegistry.instance, id, player.worldObj, x, y, z);
	}

	public static void send(int id, int x, int y, int z, EntityPlayer player) {
		if( player.worldObj.isRemote )
			player.openGui(ESPModRegistry.instance, id, player.worldObj, x, y, z);
		
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x005);
			dos.writeInt(id);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()));
		} catch (IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvChangeBCGUI to server!");
		} finally {
			try {
				if( bos != null) bos.close( );
				if( dos != null) dos.close( );
			} catch (IOException e) {
				;
			}
		}
	}
}
