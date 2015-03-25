/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.network.packet;

import de.sanandrew.core.manpack.network.IPacket;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumParticleFx;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.network.INetHandler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.io.IOException;

public class PacketBiomeChangerModify
        implements IPacket
{
    @Override
    public void process(ByteBufInputStream stream, ByteBuf rawData, INetHandler handler) throws IOException {
        World world = EnderStuffPlus.proxy.getWorld(handler);
        if( world.isRemote ) {
            int x = stream.readInt();
            int z = stream.readInt();
            int y = world.getTopSolidOrLiquidBlock(x, z);
            byte biomeId = stream.readByte();
            Chunk chunk = world.getChunkFromBlockCoords(x, z);
            byte[] biomeArray = chunk.getBiomeArray();

            biomeArray[(z & 0xF) << 4 | (x & 0xF)] = biomeId;
            chunk.setBiomeArray(biomeArray);

            EnderStuffPlus.proxy.handleParticle(EnumParticleFx.FX_BIOMECHG_PARTICLE, x, y - 1, z, Pair.with((short) biomeId, false));
            world.markBlockForUpdate(x, y - 1, z);
        }
    }

    @Override
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
        stream.writeInt((int) dataTuple.getValue(0)); // x
        stream.writeInt((int) dataTuple.getValue(1)); // z

        stream.writeByte((byte) dataTuple.getValue(2)); // biomeId
    }
}
