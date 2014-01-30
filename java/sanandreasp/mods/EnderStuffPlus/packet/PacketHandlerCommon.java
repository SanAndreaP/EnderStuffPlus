package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandlerCommon implements IPacketHandler {
	
	private Map<Integer, PacketBase> packetTypes = Maps.newHashMap();

	public PacketHandlerCommon() {
		this.packetTypes.put(0x000, new PacketRecvJump());
		this.packetTypes.put(0x001, new PacketRecvEnderName());
		this.packetTypes.put(0x002, new PacketRecvEnderPetGUIAction());
		this.packetTypes.put(0x003, new PacketRecvBCGUIAction());
		this.packetTypes.put(0x004, new PacketRecvMove());
		this.packetTypes.put(0x005, new PacketRecvChangeBCGUI());
		this.packetTypes.put(0x006, new PacketRecvSetWeather());
		this.packetTypes.put(0x007, new PacketRecvDupeInsertLevels());
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player par3Player) {
        EntityPlayer player = (EntityPlayer)par3Player;
        try {
        	DataInputStream iStream = new DataInputStream(new ByteArrayInputStream(packet.data));
        	int packetID = iStream.readInt();
			this.packetTypes.get(packetID).handle(iStream, player);
		} catch (IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to handle server-packet!");
		}
	}

}
