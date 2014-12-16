/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.tileentity;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Unit;
import de.sanandrew.mods.enderstuffp.network.EnumPacket;
import de.sanandrew.mods.enderstuffp.network.PacketProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityOreGenerator
        extends TileEntity
        implements IEnergyProvider, IInventory
{
    public int fluxAmount = 25_000;
    private int prevFluxAmount = -1;
    private static final int MAX_EXTRACTABLE_FLUX = 200;
    private static final int MAX_STORABLE_FLUX = 50_000;
    public long drawCycles = 0;

    private ItemStack fuelStack = null;
    private int ticksGenRemain = 0;
    private int fluxGenerated = 0;

    @Override
    public void updateEntity() {
        if( !this.worldObj.isRemote ) {
            //FIXME make it use of different ores = diff. time / diff. flux/tick
            if( this.fuelStack != null && this.ticksGenRemain == 0 && this.fluxAmount < MAX_STORABLE_FLUX ) {
                this.ticksGenRemain = 100;
                this.fluxGenerated = 80;
                this.fuelStack = SAPUtils.decrStackSize(this.fuelStack);
            }

            if( this.ticksGenRemain > 0 ) {
                this.ticksGenRemain--;

                this.fluxAmount = Math.min(this.fluxAmount + this.fluxGenerated, MAX_STORABLE_FLUX);

                if( this.ticksGenRemain == 0 ) {
                    this.fluxGenerated = 0;
                }
            }

            if( this.fluxAmount > 0 ) {
                int maxExtractable = MAX_EXTRACTABLE_FLUX;
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
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(this.fluxAmount, Math.min(MAX_EXTRACTABLE_FLUX, maxExtract));

        if( !simulate ) {
            this.fluxAmount -= energyExtracted;
        }

        return energyExtracted;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return this.fluxAmount;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return MAX_STORABLE_FLUX;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot == 0 ? this.fuelStack : null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return slot == 0 ? SAPUtils.decrInvStackSize(this.fuelStack, amount) : null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if( slot == 0 ) {
            this.fuelStack = stack;
        }
    }

    @Override
    public String getInventoryName() {
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return slot == 0 && (this.fuelStack == null || (ItemStack.areItemStacksEqual(this.fuelStack, stack)
                                                        && this.fuelStack.stackSize < this.fuelStack.getMaxStackSize()));
    }
}
