/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.tileentity;

import de.sanandrew.core.manpack.util.javatuples.Unit;
import de.sanandrew.mods.enderstuffp.network.EnumPacket;
import de.sanandrew.mods.enderstuffp.network.PacketProcessor;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumParticleFx;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileEntityBiomeDataCrystal
        extends TileEntity
{
    public int dataProgress = 0;
    public short biomeID = -1;
    private int prevDataProgress = 0;
    private Random rand = new Random();

    @Override
    public void updateEntity() {
        if( !this.worldObj.isRemote ) {
            int light = this.worldObj.getFullBlockLightValue(this.xCoord, this.yCoord, this.zCoord);
            if( this.biomeID < 0 ) {
                this.dataProgress = 0;
                this.prevDataProgress = 0;
                this.biomeID = (short) this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord).biomeID;
                this.markDirty();
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            } else if( light > 0 && this.dataProgress < 10 && this.biomeID == this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord).biomeID ) {
                if( this.rand.nextInt(3) == 0 ) {
                    EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_BIOME_DATA, this.xCoord + 0.5F, this.yCoord, this.zCoord + 0.5D,
                                                       this.worldObj.provider.dimensionId, Unit.with(this.biomeID));
                }

                if( this.rand.nextInt(1600 - light * 100) == 0 ) {
                    this.dataProgress++;
                }
            }

            if( this.prevDataProgress != this.dataProgress ) {
                this.prevDataProgress = this.dataProgress;
                PacketProcessor.sendToAllAround(EnumPacket.TILE_ENERGY_SYNC, this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 64.0F,
                                                Unit.with(this));
            }
        }
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.biomeID = pkt.func_148857_g().getShort("biomeId");
        this.dataProgress = pkt.func_148857_g().getInteger("progress");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setShort("biomeId", this.biomeID);
        nbt.setInteger("progress", this.dataProgress);

        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setShort("biomeId", this.biomeID);
        nbt.setInteger("progress", this.dataProgress);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.biomeID = nbt.getShort("biomeId");
        this.dataProgress = nbt.getInteger("progress");
    }

    public int getBiomeID() {
        return this.biomeID < 0 ? 0 : this.biomeID;
    }
}
