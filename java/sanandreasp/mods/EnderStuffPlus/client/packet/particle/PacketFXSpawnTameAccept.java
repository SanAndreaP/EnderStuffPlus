package sanandreasp.mods.EnderStuffPlus.client.packet.particle;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.client.particle.ParticleFXFuncCollection;

import net.minecraft.client.Minecraft;
import net.minecraft.network.INetworkManager;

import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PacketFXSpawnTameAccept
    implements ISAPPacketHandler
{
    @Override
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Throwable {
        doStream.writeDouble((Double) data[0]);          // posX
        doStream.writeDouble((Double) data[1]);          // posY
        doStream.writeDouble((Double) data[2]);          // posZ
        doStream.writeFloat((Float) data[3]);            // width
        doStream.writeFloat((Float) data[4]);            // height
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Throwable {
        ParticleFXFuncCollection.spawnAcceptTameFX(Minecraft.getMinecraft().theWorld, diStream.readDouble(), diStream.readDouble(),
                                                   diStream.readDouble(), diStream.readFloat(), diStream.readFloat());
    }
}
