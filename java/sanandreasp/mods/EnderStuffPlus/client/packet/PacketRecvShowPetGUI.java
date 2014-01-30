package sanandreasp.mods.EnderStuffPlus.client.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.packet.PacketBase;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketRecvShowPetGUI extends PacketBase {
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		Entity entity = ((WorldClient)((EntityPlayer)player).worldObj).getEntityByID(iStream.readInt());
		if( entity instanceof IEnderPet ) {
			player.openGui(ESPModRegistry.instance, 0, player.worldObj, entity.entityId, 0, 0);
		}
	}
	
	public static void send(Entity entity, EntityPlayer player) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x101);
			dos.writeInt(entity.entityId);
			
			PacketDispatcher.sendPacketToPlayer(new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()), (Player)player);
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvJump to server!");
		}
	}

}
