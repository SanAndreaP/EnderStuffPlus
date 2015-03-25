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
import de.sanandrew.mods.enderstuffp.network.PacketManager;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeChanger;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeChanger.EnumPerimForm;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.network.INetHandler;
import net.minecraft.world.World;

import java.io.IOException;

public class PacketBiomeChangerActions
        implements IPacket
{
    @Override
    public void process(ByteBufInputStream stream, ByteBuf rawData, INetHandler handler) throws IOException {
        EnumAction action = EnumAction.VALUES[stream.readByte()];
        World world = EnderStuffPlus.proxy.getWorld(handler);
        TileEntityBiomeChanger biomeChanger = (TileEntityBiomeChanger) world.getTileEntity(stream.readInt(), stream.readInt(), stream.readInt());

        if( biomeChanger == null ) {
            return;
        }

        switch( action ) {
            case ACTIVATE:
                biomeChanger.activate();
                break;
            case DEACTIVATE:
                biomeChanger.deactivate();
                break;
            case CHANGE_BIOME:
                biomeChanger.setCurrRange(stream.readByte());
                biomeChanger.changeBiomeOrShowPerim(false);
                biomeChanger.setCurrRange(biomeChanger.getCurrRange() + 1);
                break;
            case REPLACE_BLOCKS:
                biomeChanger.replaceBlocks(stream.readBoolean());
                break;
            case CHNG_MAX_RANGE:
                biomeChanger.setMaxRange(stream.readShort());
                break;
            case CHNG_PERIM_FORM:
                biomeChanger.perimForm = EnumPerimForm.VALUES[stream.readByte()];
                break;
        }
    }

    @Override
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
        EnumAction action = (EnumAction) dataTuple.getValue(0);
        TileEntityBiomeChanger biomeChangerTile = (TileEntityBiomeChanger) dataTuple.getValue(1);

        stream.writeByte(action.ordinal());
        stream.writeInt(biomeChangerTile.xCoord);
        stream.writeInt(biomeChangerTile.yCoord);
        stream.writeInt(biomeChangerTile.zCoord);

        switch( action ) {
            case CHANGE_BIOME:
                stream.writeByte(biomeChangerTile.getCurrRange());
                break;
            case REPLACE_BLOCKS:
                stream.writeBoolean(biomeChangerTile.isReplacingBlocks());
                break;
            case CHNG_MAX_RANGE:
                stream.writeShort(biomeChangerTile.getMaxRange());
                break;
            case CHNG_PERIM_FORM:
                stream.writeByte(biomeChangerTile.perimForm.ordinal());
        }
    }

    public static void sendPacketClient(TileEntityBiomeChanger tile, EnumAction action) {
        Tuple data = Tuple.from(Pair.with(action, tile).toArray());

//        if( additionalData != null ) {
//            data = Tuple.from(ArrayUtils.addAll(data.toArray(), additionalData));
//        }

        PacketManager.sendToAllAround(PacketManager.BIOME_CHANGER_ACTIONS, tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord, 256.0D, data);
    }

    public static void sendPacketServer(TileEntityBiomeChanger tile, EnumAction action) {
        Tuple data = Tuple.from(Pair.with(action, tile).toArray());

//        if( additionalData != null ) {
//            data = Tuple.from(ArrayUtils.addAll(data.toArray(), additionalData));
//        }

        PacketManager.sendToServer(PacketManager.BIOME_CHANGER_ACTIONS, data);
    }

    public static enum EnumAction {
        ACTIVATE,
        DEACTIVATE,
        CHANGE_BIOME,
        REPLACE_BLOCKS,
        CHNG_MAX_RANGE,
        CHNG_PERIM_FORM;

        public static final EnumAction[] VALUES = values();
    }
}
