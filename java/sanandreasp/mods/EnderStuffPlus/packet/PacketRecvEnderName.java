package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.WorldServer;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;

public class PacketRecvEnderName extends PacketBase {
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		WorldServer serverWorld = (WorldServer) ((EntityPlayerMP)player).worldObj;
		EntityLiving entity = (EntityLiving) serverWorld.getEntityByID(iStream.readInt());
		if( entity != null ) {
			if( entity instanceof IEnderPet ) {
				((IEnderPet)entity).setName(iStream.readUTF());
			}
		}
	}
	
	public static void send(int entityID, String name) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x001);
			dos.writeInt(entityID);
			dos.writeUTF(name);
			
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()));
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvJump to server!");
		}
	}

}
