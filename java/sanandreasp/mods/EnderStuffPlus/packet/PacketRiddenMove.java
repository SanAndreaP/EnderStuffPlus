package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;

import cpw.mods.fml.common.network.Player;

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
