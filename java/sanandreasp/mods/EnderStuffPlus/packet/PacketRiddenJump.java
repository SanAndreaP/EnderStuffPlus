package sanandreasp.mods.EnderStuffPlus.packet;

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
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Throwable {
        doStream.writeInt((Integer) data[0]); // entityId
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Throwable {
        WorldServer serverWorld = (WorldServer) ((EntityPlayerMP) player).worldObj;
        EntityLivingBase entity = (EntityLivingBase) serverWorld.getEntityByID(diStream.readInt());

        if( entity != null ) {
            ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, entity, true, "isJumping", "field_70703_bu");
        }
    }
}
