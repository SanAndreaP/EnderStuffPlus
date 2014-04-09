package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    public byte[] getDataForPacket(Object... data) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        dos.writeInt(((TileEntity) data[0]).xCoord);
        dos.writeInt(((TileEntity) data[0]).yCoord);
        dos.writeInt(((TileEntity) data[0]).zCoord);

        byte[] bytes = bos.toByteArray();

        dos.close();
        bos.close();

        return bytes;
    }

    @Override
    public void processData(INetworkManager manager, Player player, byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);

        EntityPlayerMP playerMP = (EntityPlayerMP) player;

        WorldServer serverWorld = (WorldServer) playerMP.worldObj;
        int posX = dis.readInt();
        int posY = dis.readInt();
        int posZ = dis.readInt();
        TileEntityDuplicator dupe = (TileEntityDuplicator) serverWorld.getBlockTileEntity(posX, posY, posZ);

        if( playerMP.capabilities.isCreativeMode ) {
            dupe.addLevels(50);
        } else {
            dupe.addLevels(Math.min(playerMP.experienceLevel, 10));
            playerMP.addExperienceLevel(-Math.min(playerMP.experienceLevel, 10));
        }

        dupe.onInventoryChanged();
        serverWorld.markBlockForUpdate(posX, posY, posZ);

        dis.close();
        bis.close();
    }
}
