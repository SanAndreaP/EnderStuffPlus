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
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;

public class PacketRecvBCGUIAction extends PacketBase {
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		WorldServer serverWorld = (WorldServer) ((EntityPlayerMP)player).worldObj;
		int posX = iStream.readInt();
		int posY = iStream.readInt();
		int posZ = iStream.readInt();
		TileEntityBiomeChanger bcte = (TileEntityBiomeChanger) serverWorld.getBlockTileEntity(posX, posY, posZ);
		switch(iStream.readByte()) {
			case 0:
				bcte.setActive(!bcte.isActive());
				break;
			case 1:
				bcte.setBiomeID(iStream.readInt());
				bcte.setCurrRange(0);
				break;
			case 2:
				bcte.setMaxRange(iStream.readInt());
				bcte.setCurrRange(0);
				break;
			case 3:
				bcte.setRadForm((byte)(iStream.readInt() & 255));
				break;
			case 4:
				bcte.isReplacingBlocks = !bcte.isReplacingBlocks;
				break;
		}
		bcte.onInventoryChanged();
		serverWorld.markBlockForUpdate(posX, posY, posZ);
	}
	
	public static void send(TileEntity tile, byte subID, int data) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x003);
			dos.writeInt(tile.xCoord);
			dos.writeInt(tile.yCoord);
			dos.writeInt(tile.zCoord);
			dos.writeByte(subID);
			dos.writeInt(data);
			
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()));
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvJump to server!");
		}
	}

}
