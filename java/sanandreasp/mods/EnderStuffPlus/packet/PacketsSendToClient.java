package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;

public class PacketsSendToClient {
	public static void sendParticle(Entity entity, byte subID, double posX, double posY, double posZ, double dataI, double dataII, double dataIII) {

		
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x100);
			dos.writeInt(entity.entityId);
			dos.writeByte(subID);
			dos.writeDouble(posX);
			dos.writeDouble(posY);
			dos.writeDouble(posZ);
			dos.writeDouble(dataI);
			dos.writeDouble(dataII);
			dos.writeDouble(dataIII);
			
			if( entity.worldObj.isRemote ) {
				ESPModRegistry.proxy.spawnParticleFromDIS(new DataInputStream(new ByteArrayInputStream(bos.toByteArray())));
			} else {
				PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 128D, entity.dimension, new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()));
			}
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvSpawnParticle to server!");
		}
	}
	
	public static void sendParticle(Entity entity, byte subID, double posX, double posY, double posZ) {
		PacketsSendToClient.sendParticle(entity, subID, posX, posY, posZ, 0D, 0D, 0D);
	}
	
	public static void sendParticle(World worldObj, int X, int Y, int Z, byte subID) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x100);
			dos.writeInt(-10);
			dos.writeInt(X);
			dos.writeInt(Y);
			dos.writeInt(Z);
			dos.writeByte(subID);
			
			PacketDispatcher.sendPacketToAllAround(X, Y, Z, 128D, worldObj.provider.dimensionId, new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()));
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvSpawnParticle to server!");
		}
	}
}
