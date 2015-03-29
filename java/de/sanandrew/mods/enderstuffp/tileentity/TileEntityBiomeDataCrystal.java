/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.tileentity;

import de.sanandrew.core.manpack.util.javatuples.Unit;
import de.sanandrew.mods.enderstuffp.network.PacketManager;
import de.sanandrew.mods.enderstuffp.network.packet.PacketTileDataSync.ITileSync;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumParticleFx;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;

import java.io.IOException;
import java.util.Random;

public class TileEntityBiomeDataCrystal
        extends TileEntity
        implements ITileSync
{
    public static final int MAX_PROGRESS = 10;

    private int dataProgress = 0;
    private short biomeId = -1;
    private int prevDataProgress = 0;
    public boolean isUsed = false;
    private Random rand = new Random();

    public int currRenderPass = 0;

    @Override
    public void updateEntity() {
        if( !this.worldObj.isRemote ) {
            int light = this.worldObj.getFullBlockLightValue(this.xCoord, this.yCoord, this.zCoord);

            if( this.biomeId < 0 ) {
                this.dataProgress = 0;
                this.prevDataProgress = 0;
                this.biomeId = (short) this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord).biomeID;
                this.markDirty();
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            } else if( !this.isUsed && light > 0 && this.dataProgress < MAX_PROGRESS
                       && this.biomeId == this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord).biomeID )
            {
                if( this.rand.nextInt(3) == 0 ) {
                    EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_BIOME_DATA, this.xCoord + 0.5F, this.yCoord, this.zCoord + 0.5D,
                                                       this.worldObj.provider.dimensionId, Unit.with(this.biomeId));
                }

                if( this.rand.nextInt(1600 - light * 100) == 0 ) {
                    this.dataProgress++;
                }
            }

            if( this.dataProgress == MAX_PROGRESS ) {
                this.isUsed = true;
                this.markDirty();
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }

            if( this.prevDataProgress != this.dataProgress ) {
                this.prevDataProgress = this.dataProgress;
                PacketManager.sendToAllAround(PacketManager.TILE_DATA_SYNC, this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 64.0F,
                                              Unit.with(this));
            }
        }
    }

    public void drainData(int amount) {
        this.dataProgress -= amount;
        if( this.dataProgress <= 0 ) {
            this.dataProgress = 0;
            this.prevDataProgress = -1;
        }
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.biomeId = pkt.func_148857_g().getShort("biomeId");
        this.dataProgress = pkt.func_148857_g().getInteger("progress");
        this.isUsed = pkt.func_148857_g().getBoolean("used");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setShort("biomeId", this.biomeId);
        nbt.setInteger("progress", this.dataProgress);
        nbt.setBoolean("used", this.isUsed);

        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setShort("biomeId", this.biomeId);
        nbt.setInteger("progress", this.dataProgress);
        nbt.setBoolean("used", this.isUsed);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.biomeId = nbt.getShort("biomeId");
        this.dataProgress = nbt.getInteger("progress");
        this.isUsed = nbt.getBoolean("used");
    }

    public short getBiomeId() {
        return this.biomeId < 0 ? 0 : this.biomeId;
    }

    public boolean hasBiome() {
        return this.biomeId >= 0;
    }

    public void setNoBiome() {
        this.biomeId = -1;
    }

    public void setBiomeId(int biomeId) {
        if( biomeId < 0 ) {
            this.setNoBiome();
            return;
        }

        if( biomeId >= BiomeGenBase.getBiomeGenArray().length || BiomeGenBase.getBiome(biomeId) == null ) {
            return;
        }

        this.biomeId = (short) (biomeId & 255);
    }

    @Override
    public void writeToStream(ByteBufOutputStream stream) throws IOException {
        stream.writeInt(this.dataProgress);
    }

    @Override
    public void readFromStream(ByteBufInputStream stream) throws IOException {
        this.dataProgress = stream.readInt();
    }

    public int getDataProgress() {
        return this.dataProgress;
    }

    public void setDataProgress(int dataProgress) {
        this.dataProgress = Math.max(0, Math.min(MAX_PROGRESS, dataProgress));
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return (this.currRenderPass = pass) < 2;
    }
}
