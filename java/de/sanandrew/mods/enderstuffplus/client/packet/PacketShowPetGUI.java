package de.sanandrew.mods.enderstuffplus.client.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import de.sanandrew.core.manpack.mod.packet.ISAPPacketHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;

import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.entity.IEnderPet;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;

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
