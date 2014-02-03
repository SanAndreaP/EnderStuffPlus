package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.world.WorldServer;
import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import cpw.mods.fml.common.network.Player;

public class PacketEnderPetGUIAction implements ISAPPacketHandler
{
	@Override
	public byte[] getDataForPacket(Object... data) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		
		dos.writeInt((Integer)data[0]);
		dos.writeByte((Byte)data[1]);
		
		byte[] bytes = bos.toByteArray();
		
		dos.close();
		bos.close();
		
		return bytes;
	}

	@Override
	public void processData(INetworkManager manager, Player player, byte[] data) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bis);

		WorldServer serverWorld = (WorldServer) ((EntityPlayerMP)player).worldObj;
		EntityLiving entity = (EntityLiving) serverWorld.getEntityByID(dis.readInt());
		if( entity != null && (entity instanceof IEnderPet) ) {
			IEnderPet pet = ((IEnderPet)entity);
			switch(dis.readByte()) {
				case 0: {
					((EntityPlayerMP)player).mountEntity(entity);
				} break;
				case 1: {
					pet.setSitting(!pet.isSitting());
				} break;
				case 2: {
					pet.setFollowing(!pet.isFollowing());
				} break;
				case 3: {
					ItemStack is = new ItemStack(ESPModRegistry.enderPetEgg, 1, pet.getEggDmg());
					NBTTagCompound nbt = new NBTTagCompound("enderPetEgg");
					switch(pet.getEggDmg()) {
						case 0: {
							EntityEnderMiss miss = ((EntityEnderMiss)entity);
		                	nbt.setByte("petID", (byte)0);
		                	nbt.setFloat("missHealth", miss.getHealth());
		                	nbt.setInteger("missColor", miss.getColor());
		                	nbt.setInteger("missCoatColor", miss.getCoat() & 31);
		                	nbt.setInteger("missCoatBase", miss.getCoat() >> 5);
		                	nbt.setBoolean("missNoFallDmg", miss.canGetFallDmg());
		                	nbt.setBoolean("missSpecial", miss.isSpecial());
						} break;
						case 1: {
							EntityEnderAvis avis = ((EntityEnderAvis)entity);
		                	nbt.setByte("petID", (byte)1);
		                	nbt.setFloat("avisHealth", avis.getHealth());
		                	nbt.setFloat("avisCondition", avis.currFlightCondition);
		                	nbt.setInteger("avisColor", avis.getColor());
		                	nbt.setInteger("avisCoatColor", avis.getCoat() & 31);
		                	nbt.setInteger("avisCoatBase", avis.getCoat() >> 5);
		                	nbt.setBoolean("avisSaddle", avis.isImmuneToWater());
						} break;
					}
	                is.setTagCompound(nbt);
                	if( !((EntityPlayerMP)player).inventory.addItemStackToInventory(is) )
                		entity.entityDropItem(is, 0.0F);
                	if( !((EntityPlayerMP)player).capabilities.isCreativeMode )
                		((EntityPlayerMP)player).inventory.consumeInventoryItem(Item.egg.itemID);
                	((EntityPlayer)player).inventoryContainer.detectAndSendChanges();
                	entity.setDead();
				} break;
			}
		}
		
		dis.close();
		bis.close();
	}
}
