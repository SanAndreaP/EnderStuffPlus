package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.Player;

public class PacketSetWeather
    implements ISAPPacketHandler
{
    @Override
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Throwable {
        doStream.writeInt((Integer) data[0]); // x
        doStream.writeInt((Integer) data[1]); // y
        doStream.writeInt((Integer) data[2]); // z
        doStream.writeInt((Integer) data[3]); // weather ID
        doStream.writeInt((Integer) data[4]); // duration
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Throwable {
        int weatherID = diStream.readInt();
        int duration = diStream.readInt();
        TileEntity tile = ((EntityPlayer) player).worldObj.getBlockTileEntity(diStream.readInt(), diStream.readInt(), diStream.readInt());
        if( tile instanceof TileEntityWeatherAltar ) {
            ((TileEntityWeatherAltar) tile).setWeather(weatherID, duration);
        }
    }
}
