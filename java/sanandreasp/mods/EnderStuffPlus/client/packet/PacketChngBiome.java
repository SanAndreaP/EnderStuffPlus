package sanandreasp.mods.EnderStuffPlus.client.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.network.Player;
import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;

public class PacketChngBiome implements ISAPPacketHandler
{
	@Override
	public byte[] getDataForPacket(Object... data) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
	
		dos.writeInt(((TileEntity)data[0]).xCoord);
		dos.writeInt(((TileEntity)data[0]).yCoord);
		dos.writeInt(((TileEntity)data[0]).zCoord);
		dos.writeInt(((Integer)data[1]));
		
		byte[] bytes = bos.toByteArray();
		
		dos.close();
		bos.close();
		
		return bytes;
	}

	@Override
	public void processData(INetworkManager manager, Player player, byte[] data) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bis);

		TileEntity tile = ((WorldClient)((EntityPlayer)player).worldObj).getBlockTileEntity(dis.readInt(), dis.readInt(), dis.readInt());
		if( tile != null && tile instanceof TileEntityBiomeChanger ) {
		    int range = dis.readInt();
		    TileEntityBiomeChanger bcte = (TileEntityBiomeChanger)tile;
		    bcte.changeBiome(range, false);
            bcte.setCurrRange( range+1 );
		}
		
		dis.close();
		bis.close();
	}
}
