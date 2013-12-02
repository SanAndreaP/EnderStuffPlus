package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.PacketDispatcher;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.WorldServer;

public class PacketRecvJump extends PacketBase {
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		WorldServer serverWorld = (WorldServer) ((EntityPlayerMP)player).worldObj;
		EntityLivingBase entity = (EntityLivingBase) serverWorld.getEntityByID(iStream.readInt());
		if( entity != null ) {
			ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, entity, true, "isJumping", "field_70703_bu");
		}
	}
	
	public static void send(int entityID) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x000);
			dos.writeInt(entityID);
			
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()));
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvJump to server!");
		}
	}

}
