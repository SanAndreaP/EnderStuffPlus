package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.world.WorldServer;

import cpw.mods.fml.common.network.Player;

public class PacketEnderPetGUIAction
    implements ISAPPacketHandler
{
    @Override
    public byte[] getDataForPacket(Object... data) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        dos.writeInt((Integer) data[0]); // entityId
        dos.writeByte((Byte) data[1]);   // buttonId

        byte[] bytes = bos.toByteArray();

        dos.close();
        bos.close();

        return bytes;
    }

    @Override
    public void processData(INetworkManager manager, Player player, byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);

        WorldServer serverWorld = (WorldServer) ((EntityPlayerMP) player).worldObj;
        EntityLiving entity = (EntityLiving) serverWorld.getEntityByID(dis.readInt());
        if( entity != null && (entity instanceof IEnderPet) ) {
            IEnderPet pet = ((IEnderPet) entity);
            switch( dis.readByte() ){
                case 0 : {
                    ((EntityPlayerMP) player).mountEntity(entity);
                }
                    break;
                case 1 : {
                    pet.setSitting(!pet.isSitting());
                }
                    break;
                case 2 : {
                    pet.setFollowing(!pet.isFollowing());
                }
                    break;
                case 3 : {
                    ItemStack is = new ItemStack(ModItemRegistry.enderPetEgg, 1, pet.getEggDmg());
                    NBTTagCompound nbt = new NBTTagCompound("enderPetEgg");
                    pet.writePetToNBT(nbt);
                    is.setTagCompound(nbt);
                    if( !((EntityPlayerMP) player).inventory.addItemStackToInventory(is) ) {
                        entity.entityDropItem(is, 0.0F);
                    }
                    if( !((EntityPlayerMP) player).capabilities.isCreativeMode ) {
                        ((EntityPlayerMP) player).inventory.consumeInventoryItem(Item.egg.itemID);
                    }
                    ((EntityPlayer) player).inventoryContainer.detectAndSendChanges();
                    entity.setDead();
                }
                    break;
            }
        }

        dis.close();
        bis.close();
    }
}
