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

public class PacketRecvChngBiome extends PacketBase {
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		TileEntity tile = ((WorldClient)((EntityPlayer)player).worldObj).getBlockTileEntity(iStream.readInt(), iStream.readInt(), iStream.readInt());
		if( tile != null && tile instanceof TileEntityBiomeChanger ) {
		    int range = iStream.readInt();
		    TileEntityBiomeChanger bcte = (TileEntityBiomeChanger)tile;
		    bcte.changeBiome(range, false);
            bcte.setCurrRange( range+1 );
		}
	}
	
	public static void send(TileEntityBiomeChanger entity, int range) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x102);
			dos.writeInt(entity.xCoord);
            dos.writeInt(entity.yCoord);
            dos.writeInt(entity.zCoord);
            dos.writeInt(range);
			
			PacketDispatcher.sendPacketToAllAround(entity.xCoord, entity.yCoord, entity.zCoord, 256, entity.worldObj.provider.dimensionId, new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()));
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvJump to server!");
		}
	}

}
