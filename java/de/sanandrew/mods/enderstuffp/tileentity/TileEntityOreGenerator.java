/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.tileentity;

import cofh.api.energy.IEnergyProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityOreGenerator
        extends TileEntity
        implements IEnergyProvider
{
    private int storedFlux = 25_000;

    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int maxExtract, boolean checkSize) {
        int energyExtracted = Math.min(this.storedFlux, Math.min(10, maxExtract));

        if (!checkSize) {
            this.storedFlux -= energyExtracted;
        }

        return energyExtracted;
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return this.storedFlux;
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
