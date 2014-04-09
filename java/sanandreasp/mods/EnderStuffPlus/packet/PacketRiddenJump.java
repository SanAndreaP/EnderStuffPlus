package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.world.WorldServer;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.Player;

public class PacketRiddenJump
    implements ISAPPacketHandler
{
    @Override
    public byte[] getDataForPacket(Object... data) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        dos.writeInt((Integer) data[0]); // entityId

        byte[] bytes = bos.toByteArray();

        dos.close();
        bos.close();

        return bytes;
    }

    @Override
    public void processData(INetworkManager manager, Player player, byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);

        WorldServer serverWorld = (WorldServer) ((EntityPlayerMP) player).worldObj;
        EntityLivingBase entity = (EntityLivingBase) serverWorld.getEntityByID(dis.readInt());
        if( entity != null ) {
            ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, entity, true, "isJumping", "field_70703_bu");
        }

        dis.close();
        bis.close();
    }
}
