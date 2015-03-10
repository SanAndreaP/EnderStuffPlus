/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.world;

import de.sanandrew.core.manpack.util.EnumNbtTypes;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeChanger;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.OrderedLoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

import java.util.List;

public class BiomeChangerChunkLoader
        implements OrderedLoadingCallback
{
    private static Ticket existingTicket;

    public static void requestTicket(World world) {
        if( existingTicket == null ) {
            existingTicket = ForgeChunkManager.requestTicket(EnderStuffPlus.instance, world, Type.NORMAL);
        }
    }

    public static void forceBiomeChangerChunk(TileEntityBiomeChanger tileBiomeChg) {
        if( existingTicket == null ) {
            requestTicket(tileBiomeChg.getWorldObj());
        }

        if( existingTicket != null ) {
            NBTTagCompound modData = existingTicket.getModData();
            NBTTagList coords;

            if( modData.hasKey("BiomeChngCoords") ) {
                coords = modData.getTagList("BiomeChngCoords", EnumNbtTypes.NBT_COMPOUND.ordinal());
            } else {
                coords = new NBTTagList();
            }

            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("tileX", tileBiomeChg.xCoord);
            tag.setInteger("tileY", tileBiomeChg.yCoord);
            tag.setInteger("tileZ", tileBiomeChg.zCoord);

            coords.appendTag(tag);

            modData.setTag("BiomeChngCoords", coords);

            forceChunk(tileBiomeChg.xCoord >> 4, tileBiomeChg.zCoord >> 4);
        }
    }

    public static void unforceBiomeChangerChunk(TileEntityBiomeChanger tileBiomeChg) {
        if( existingTicket != null ) {
            NBTTagCompound modData = existingTicket.getModData();
            NBTTagList coords;

            if( modData.hasKey("BiomeChngCoords") ) {
                coords = modData.getTagList("BiomeChngCoords", EnumNbtTypes.NBT_COMPOUND.ordinal());

                for( int i = 0; i < coords.tagCount(); i++ ) {
                    NBTTagCompound nbt = coords.getCompoundTagAt(i);
                    if( nbt.getInteger("tileX") == tileBiomeChg.xCoord && nbt.getInteger("tileY") == tileBiomeChg.yCoord
                        && nbt.getInteger("tileZ") == tileBiomeChg.zCoord )
                    {
                        coords.removeTag(i);
                        break;
                    }
                }

                unforceChunk(tileBiomeChg.xCoord >> 4, tileBiomeChg.zCoord >> 4);

                if( coords.tagCount() == 0 ) {
                    releaseTicket();
                    existingTicket = null;
                }
            }
        }
    }

    public static void forceChunk(int chunkX, int chunkZ) {
        ForgeChunkManager.forceChunk(existingTicket, new ChunkCoordIntPair(chunkX, chunkZ));
    }

    public static void unforceChunk(int chunkX, int chunkZ) {
        ChunkCoordIntPair chunkCoords = new ChunkCoordIntPair(chunkX, chunkZ);
        if( existingTicket != null && existingTicket.getChunkList().contains(chunkCoords) ) {
            ForgeChunkManager.unforceChunk(existingTicket, chunkCoords);
        }
    }

    public static void releaseTicket() {
        if( existingTicket != null ) {
            ForgeChunkManager.releaseTicket(existingTicket);
        }
    }

    @Override
    public void ticketsLoaded(List<Ticket> tickets, World world) {
        for( Ticket ticket : tickets ) {
            if( ticket.getModId().equals(EnderStuffPlus.MOD_ID) ) {
                existingTicket = ticket;
                if( existingTicket.getModData().hasKey("BiomeChngCoords") ) {
                    NBTTagList coords = existingTicket.getModData().getTagList("BiomeChngCoords", EnumNbtTypes.NBT_COMPOUND.ordinal());

                    for( int i = 0; i < coords.tagCount(); i++ ) {
                        NBTTagCompound nbt = coords.getCompoundTagAt(i);
                        forceChunk(nbt.getInteger("tileX") >> 4, nbt.getInteger("tileZ") >> 4);
                    }
                }
            }
        }
    }

    @Override
    public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world, int maxTicketCount) {
        return tickets;
    }
}
