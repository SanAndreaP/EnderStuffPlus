/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.world;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.OrderedLoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.event.world.WorldEvent;

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

    public static void forceChunk(int chunkX, int chunkZ) {
        ForgeChunkManager.forceChunk(existingTicket, new ChunkCoordIntPair(chunkX, chunkZ));
    }

    public static void unforceChunk(int chunkX, int chunkZ) {
        ForgeChunkManager.unforceChunk(existingTicket, new ChunkCoordIntPair(chunkX, chunkZ));
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        requestTicket(event.world);
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        existingTicket = null;
    }

    public static void releaseTicket() {
        if( existingTicket != null ) {
            ForgeChunkManager.releaseTicket(existingTicket);
        }
    }

    @Override
    public void ticketsLoaded(List<Ticket> tickets, World world) {
        //TODO: probably do sth. when a ticket is loaded, so a BiomeChanger outside of loaded chunks can then load and operate.
    }

    @Override
    public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world, int maxTicketCount) {
        //TODO: probably do sth. when a ticket is loaded, so a BiomeChanger outside of loaded chunks can then load and operate.
        return tickets;
    }
}
