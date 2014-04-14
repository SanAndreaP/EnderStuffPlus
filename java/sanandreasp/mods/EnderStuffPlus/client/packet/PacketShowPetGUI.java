package sanandreasp.mods.EnderStuffPlus.client.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;

import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PacketShowPetGUI
    implements ISAPPacketHandler
{
    @Override
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Throwable {
        doStream.writeInt(((Entity) data[0]).entityId);
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Throwable {
        EntityPlayer entityPlayer = (EntityPlayer) player;
        Entity entity = entityPlayer.worldObj.getEntityByID(diStream.readInt());

        if( entity instanceof IEnderPet ) {
            entityPlayer.openGui(ESPModRegistry.instance, 0, entityPlayer.worldObj, entity.entityId, 0, 0);
        }
    }
}
