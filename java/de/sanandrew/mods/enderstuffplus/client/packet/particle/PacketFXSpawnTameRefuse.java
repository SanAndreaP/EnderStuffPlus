package de.sanandrew.mods.enderstuffplus.client.packet.particle;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import de.sanandrew.core.manpack.mod.packet.ISAPPacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.network.INetworkManager;

import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.client.particle.ParticleFXFuncCollection;

@SideOnly(Side.CLIENT)
public class PacketFXSpawnTameRefuse
    implements ISAPPacketHandler
{
    @Override
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Throwable {
        doStream.writeDouble((Double) data[0]);          // posX
        doStream.writeDouble((Double) data[1]);          // posY
        doStream.writeDouble((Double) data[2]);          // posZ
        doStream.writeFloat((Float) data[6]);            // width
        doStream.writeFloat((Float) data[7]);            // height
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Throwable {
        ParticleFXFuncCollection.spawnRefuseTameFX(Minecraft.getMinecraft().theWorld, diStream.readDouble(), diStream.readDouble(),
                                                   diStream.readDouble(), diStream.readFloat(), diStream.readFloat());
    }
}
