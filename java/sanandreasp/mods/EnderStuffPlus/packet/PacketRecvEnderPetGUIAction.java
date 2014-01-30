package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.WorldServer;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;

public class PacketRecvEnderPetGUIAction extends PacketBase {
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		WorldServer serverWorld = (WorldServer) ((EntityPlayerMP)player).worldObj;
		EntityLiving entity = (EntityLiving) serverWorld.getEntityByID(iStream.readInt());
		if( entity != null && (entity instanceof IEnderPet) ) {
			IEnderPet pet = ((IEnderPet)entity);
			switch(iStream.readByte()) {
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
//		                	nbt.setBoolean("missWaterImmune", miss.isImmuneToWater());
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
//		                	nbt.setBoolean("avisWaterImmune", avis.isImmuneToWater());
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
                	player.inventoryContainer.detectAndSendChanges();
                	entity.setDead();
				} break;
			}
		}
	}
	
	public static void send(int entityID, byte subID) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x002);
			dos.writeInt(entityID);
			dos.writeByte(subID);
			
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload(ESPModRegistry.channelID, bos.toByteArray()));
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(ESPModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvJump to server!");
		}
	}

}
