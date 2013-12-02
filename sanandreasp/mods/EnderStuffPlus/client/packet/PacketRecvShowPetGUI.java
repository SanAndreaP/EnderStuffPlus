package sanandreasp.mods.EnderStuffPlus.client.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import sanandreasp.mods.EnderStuffPlus.client.particle.ParticleFX_EnderMob;
import sanandreasp.mods.EnderStuffPlus.client.particle.ParticleFX_Rayball;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNivis;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.packet.PacketBase;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

public class PacketRecvShowPetGUI extends PacketBase {
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		Entity entity = ((WorldClient)((EntityPlayer)player).worldObj).getEntityByID(iStream.readInt());
		if( entity instanceof IEnderPet ) {
			player.openGui(ESPModRegistry.instance, 0, player.worldObj, entity.entityId, 0, 0);
		}
	}
	
	public static void send(Entity entity, EntityPlayer player) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x101);
			dos.writeInt(entity.entityId);
			
			PacketDispatcher.sendPacketToPlayer(new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()), (Player)player);
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvJump to server!");
		}
	}

}
