package sanandreasp.mods.EnderStuffPlus.client.packet.particle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    public byte[] getDataForPacket(Object... data) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        byte[] bytes;
        
        dos.writeDouble((Double) data[0]);          // posX
        dos.writeDouble((Double) data[1]);          // posY
        dos.writeDouble((Double) data[2]);          // posZ
        dos.writeFloat((Float) data[3]);            // colorR
        dos.writeFloat((Float) data[4]);            // colorG
        dos.writeFloat((Float) data[5]);            // colorB
        dos.writeFloat((Float) data[6]);            // width
        dos.writeFloat((Float) data[7]);            // height

        bytes = bos.toByteArray();

        dos.close();
        bos.close();

        return bytes;
    }

    @Override
    public void processData(INetworkManager manager, Player player, byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);

        ParticleFXFuncCollection.spawnPortalFX(Minecraft.getMinecraft().theWorld, dis.readDouble(), dis.readDouble(), dis.readDouble(), dis.readFloat(),
                                               dis.readFloat(), dis.readFloat(), dis.readFloat(), dis.readFloat());

        dis.close();
        bis.close();
    }

}
