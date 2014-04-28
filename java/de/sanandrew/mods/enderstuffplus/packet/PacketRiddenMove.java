package de.sanandrew.mods.enderstuffplus.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import de.sanandrew.core.manpack.mod.packet.ISAPPacketHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;

import cpw.mods.fml.common.network.Player;

import de.sanandrew.mods.enderstuffplus.entity.IEnderPet;

public class PacketRiddenMove
    implements ISAPPacketHandler
{
    @Override
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Throwable {
        doStream.writeFloat((Float) data[0]); // moveForward
        doStream.writeFloat((Float) data[1]); // moveStrafing
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Throwable {
        EntityPlayer eplayer = (EntityPlayer) player;
        IEnderPet entity = (IEnderPet) (eplayer.ridingEntity instanceof IEnderPet ? eplayer.ridingEntity : null);

        if( entity != null ) {
            entity.setPetMoveForward(diStream.readFloat());
            entity.setPetMoveStrafe(diStream.readFloat());
        }
    }
}
