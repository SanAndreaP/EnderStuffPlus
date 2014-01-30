package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;

public class PacketRecvDupeInsertLevels extends PacketBase {
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		WorldServer serverWorld = (WorldServer) ((EntityPlayerMP)player).worldObj;
		int posX = iStream.readInt();
		int posY = iStream.readInt();
		int posZ = iStream.readInt();
		TileEntityDuplicator dupe = (TileEntityDuplicator) serverWorld.getBlockTileEntity(posX, posY, posZ);
		if( player.capabilities.isCreativeMode ) {
			dupe.addLevels(50);
		} else {
			dupe.addLevels(Math.min(player.experienceLevel, 10));
			player.addExperienceLevel(-Math.min(player.experienceLevel, 10));
		}
		
		dupe.onInventoryChanged();
		serverWorld.markBlockForUpdate(posX, posY, posZ);
	}
	
	public static void send(TileEntity tile) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x007);
			dos.writeInt(tile.xCoord);
			dos.writeInt(tile.yCoord);
			dos.writeInt(tile.zCoord);
			
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()));
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvDupeInsertLevels to server!");
		}
	}

}
