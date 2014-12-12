/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.tileentity;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.sanandrew.core.manpack.util.javatuples.Unit;
import de.sanandrew.mods.enderstuffp.network.EnumPacket;
import de.sanandrew.mods.enderstuffp.network.PacketProcessor;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityOreGenerator
        extends TileEntity
        implements IEnergyProvider
{
    public int fluxAmount = 25_000;
    private int prevFluxAmount = -1;
    private static final int MAX_EXTRACTABLE_RFLUX = 40;
    public long drawCycles = 0;

    @Override
    public void updateEntity() {
        if( !this.worldObj.isRemote ) {
            if( this.fluxAmount > 0 ) {
                int maxExtractable = MAX_EXTRACTABLE_RFLUX;
                for( ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS ) {
                    TileEntity te = this.worldObj.getTileEntity(this.xCoord + direction.offsetX, this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);

                    if( te instanceof IEnergyReceiver ) {
                        IEnergyReceiver receiver = (IEnergyReceiver) te;

                        if( !receiver.canConnectEnergy(direction.getOpposite()) ) {
                            continue;
                        }

                        int extractable = this.extractEnergy(direction.getOpposite(), maxExtractable, true);
                        int receivable = receiver.receiveEnergy(direction, extractable, false);

                        maxExtractable -= receivable;
                        this.extractEnergy(direction.getOpposite(), receivable, false);
                    }

                    if( maxExtractable == 0 ) {
                        break;
                    }
                }
            }

            if( this.prevFluxAmount != this.fluxAmount ) {
                this.prevFluxAmount = this.fluxAmount;
                PacketProcessor.sendToAllAround(EnumPacket.TILE_ENERGY_SYNC, this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 64.0F,
                                                Unit.with(this));
            }
        }
    }

    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int maxExtract, boolean checkSize) {
        int energyExtracted = Math.min(this.fluxAmount, Math.min(MAX_EXTRACTABLE_RFLUX, maxExtract));

        if (!checkSize) {
            this.fluxAmount -= energyExtracted;
        }

        return energyExtracted;
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return this.fluxAmount;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return 50_000;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return true;
    }
}
