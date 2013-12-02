package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.WorldServer;

public class PacketRecvMove extends PacketBase {
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		IEnderPet entity = (IEnderPet) (player.ridingEntity != null && player.ridingEntity instanceof IEnderPet ? player.ridingEntity : null);
		if( entity != null ) {
			entity.setPetMoveForward(iStream.readFloat());
			entity.setPetMoveStrafe(iStream.readFloat());
		}
	}
	
	public static void send(float moveFwd, float moveStf) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x004);
			dos.writeFloat(moveFwd);
			dos.writeFloat(moveStf);
			
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()));
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvJump to server!");
		}
	}

}
