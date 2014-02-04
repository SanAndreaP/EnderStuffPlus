package sanandreasp.mods.EnderStuffPlus.client.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;

@SideOnly(Side.CLIENT)
public class PacketShowPetGUI implements ISAPPacketHandler
{
	@Override
	public byte[] getDataForPacket(Object... data) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		dos.writeInt(((Entity)data[0]).entityId);
		
		byte[] bytes = bos.toByteArray();
		
		dos.close();
		bos.close();
		
		return bytes;
	}

	@Override
	public void processData(INetworkManager manager, Player player, byte[] data) throws Exception {
		EntityPlayer ePlayer = (EntityPlayer)player;
		
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bis);
		
		Entity entity = ((WorldClient)((EntityPlayer)player).worldObj).getEntityByID(dis.readInt());
		if( entity instanceof IEnderPet ) {
			ePlayer.openGui(ESPModRegistry.instance, 0, ePlayer.worldObj, entity.entityId, 0, 0);
		}

		dis.close();
		bis.close();
	}

}
