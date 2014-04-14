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
public class PacketFXSpawnPortalFX
    implements ISAPPacketHandler
{
    @Override
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Exception {
        doStream.writeDouble((Double) data[0]);          // posX
        doStream.writeDouble((Double) data[1]);          // posY
        doStream.writeDouble((Double) data[2]);          // posZ
        doStream.writeFloat((Float) data[3]);            // colorR
        doStream.writeFloat((Float) data[4]);            // colorG
        doStream.writeFloat((Float) data[5]);            // colorB
        doStream.writeFloat((Float) data[6]);            // width
        doStream.writeFloat((Float) data[7]);            // height
        doStream.writeInt((Integer) data[8]);            // FOR-counter-max
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Exception {
        ParticleFXFuncCollection.spawnPortalFX(Minecraft.getMinecraft().theWorld, diStream.readDouble(), diStream.readDouble(),
                                               diStream.readDouble(), diStream.readFloat(), diStream.readFloat(), diStream.readFloat(),
                                               diStream.readFloat(), diStream.readFloat(), diStream.readInt());
    }
}
