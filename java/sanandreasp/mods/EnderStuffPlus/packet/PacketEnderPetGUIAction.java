package sanandreasp.mods.EnderStuffPlus.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import sanandreasp.core.manpack.mod.packet.ISAPPacketHandler;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import net.minecraft.entity.EntityLiving;
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
    public void getDataForPacket(DataOutputStream doStream, Object... data) throws Exception {
        doStream.writeInt((Integer) data[0]); // entityId
        doStream.writeByte((Byte) data[1]);   // buttonId
    }

    @Override
    public void processData(INetworkManager manager, Player player, DataInputStream diStream) throws Exception {
        EntityPlayerMP mpPlayer = ((EntityPlayerMP) player);
        WorldServer serverWorld = (WorldServer) mpPlayer.worldObj;
        EntityLiving entity = (EntityLiving) serverWorld.getEntityByID(diStream.readInt());
        if( entity instanceof IEnderPet ) {
            IEnderPet pet = ((IEnderPet) entity);
            switch( diStream.readByte() ){
                case 0 :
                    mpPlayer.mountEntity(entity);
                    break;
                case 1 :
                    pet.setSitting(!pet.isSitting());
                    break;
                case 2 :
                    pet.setFollowing(!pet.isFollowing());
                    break;
                case 3 :
                    ItemStack is = new ItemStack(ModItemRegistry.enderPetEgg, 1, pet.getEggDmg());
                    NBTTagCompound nbt = new NBTTagCompound("enderPetEgg");

                    pet.writePetToNBT(nbt);
                    is.setTagCompound(nbt);

                    if( !mpPlayer.inventory.addItemStackToInventory(is) ) {
                        entity.entityDropItem(is, 0.0F);
                    }

                    if( !mpPlayer.capabilities.isCreativeMode ) {
                        mpPlayer.inventory.consumeInventoryItem(Item.egg.itemID);
                    }

                    mpPlayer.inventoryContainer.detectAndSendChanges();
                    entity.setDead();

                    break;
            }
        }
    }
}
