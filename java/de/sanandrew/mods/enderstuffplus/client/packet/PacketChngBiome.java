package de.sanandrew.mods.enderstuffplus.client.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import de.sanandrew.core.manpack.mod.packet.ISAPPacketHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityBiomeChanger;

@SideOnly(Side.CLIENT)
public class PacketChngBiome
    implements ISAPPacketHandler
{
    @Override
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Throwable {
        doStream.writeInt(((TileEntity) data[0]).xCoord);
        doStream.writeInt(((TileEntity) data[0]).yCoord);
        doStream.writeInt(((TileEntity) data[0]).zCoord);
        doStream.writeInt(((Integer) data[1]));
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Throwable {
        TileEntity tile = (((EntityPlayer) player).worldObj).getBlockTileEntity(diStream.readInt(), diStream.readInt(), diStream.readInt());
        if( tile instanceof TileEntityBiomeChanger ) {
            int range = diStream.readInt();
            TileEntityBiomeChanger bcte = (TileEntityBiomeChanger) tile;

            bcte.changeBiome(range, false);
            bcte.setCurrRange(range + 1);
        }
    }
}
