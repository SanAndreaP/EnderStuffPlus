package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import cpw.mods.fml.common.network.Player;

public class PacketDupeInsertLevels
    implements ISAPPacketHandler
{
    @Override
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Exception {
        doStream.writeInt(((TileEntity) data[0]).xCoord);
        doStream.writeInt(((TileEntity) data[0]).yCoord);
        doStream.writeInt(((TileEntity) data[0]).zCoord);
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Exception {
        EntityPlayerMP playerMP = (EntityPlayerMP) player;

        WorldServer serverWorld = (WorldServer) playerMP.worldObj;
        int posX = diStream.readInt();
        int posY = diStream.readInt();
        int posZ = diStream.readInt();
        TileEntityDuplicator dupe = (TileEntityDuplicator) serverWorld.getBlockTileEntity(posX, posY, posZ);

        if( playerMP.capabilities.isCreativeMode ) {
            dupe.addLevels(50);
        } else {
            dupe.addLevels(Math.min(playerMP.experienceLevel, 10));
            playerMP.addExperienceLevel(-Math.min(playerMP.experienceLevel, 10));
        }

        dupe.onInventoryChanged();
        serverWorld.markBlockForUpdate(posX, posY, posZ);
    }
}
