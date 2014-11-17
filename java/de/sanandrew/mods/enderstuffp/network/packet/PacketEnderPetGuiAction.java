/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.network.packet;

import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.enderstuffp.entity.living.IEnderPet;
import de.sanandrew.mods.enderstuffp.network.IPacket;
import de.sanandrew.mods.enderstuffp.util.RegistryItems;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.ChatComponentText;

import java.io.IOException;

public class PacketEnderPetGuiAction
        implements IPacket
{
    @Override
    public void process(ByteBufInputStream stream, ByteBuf rawData, INetHandler handler) throws IOException {
        if( handler instanceof NetHandlerPlayServer ) {
            EntityPlayerMP playerMP = ((NetHandlerPlayServer) handler).playerEntity;
            int entityId = stream.readInt();
            IEnderPet pet = (IEnderPet) playerMP.worldObj.getEntityByID(entityId);

            switch( stream.readByte() ) {
                case 0:
                    playerMP.mountEntity(pet.getEntity());
                    break;
                case 1:
                    pet.setSitting(!pet.isSitting());
                    break;
                case 2:
                    pet.setFollowing(!pet.isFollowing());
                    break;
                case 3:
                    {
                        if( playerMP.inventory.hasItem(Items.egg) || playerMP.capabilities.isCreativeMode ) {
                            ItemStack stack = new ItemStack(RegistryItems.enderPetEgg, 1);
                            NBTTagCompound nbt = new NBTTagCompound();
                            pet.writePetToNBT(nbt);
                            stack.setTagCompound(nbt);
                            if( playerMP.inventory.addItemStackToInventory(stack) ) {
                                playerMP.inventory.consumeInventoryItem(Items.egg);
                                playerMP.inventoryContainer.detectAndSendChanges();
                                pet.getEntity().setDead();
                            } else {
                                //TODO: use translation string
                                playerMP.addChatMessage(new ChatComponentText("You don't have enough inventory space to do this!"));
                            }
                        } else {
                            //TODO: use translation string
                            playerMP.addChatMessage(new ChatComponentText("You don't have the required egg!"));
                        }
                    }
                    break;
                case 4:
                    pet.setName(stream.readUTF());
                    break;
            }
        }
    }

    @Override
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
        stream.writeInt((int) dataTuple.getValue(0));
        stream.writeByte((byte) dataTuple.getValue(1));

        if( dataTuple.getSize() == 3 ) {
            stream.writeUTF((String) dataTuple.getValue(2));
        }
    }
}
