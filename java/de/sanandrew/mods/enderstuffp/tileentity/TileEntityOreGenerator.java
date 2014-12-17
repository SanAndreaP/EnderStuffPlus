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
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.core.manpack.util.javatuples.Unit;
import de.sanandrew.mods.enderstuffp.network.EnumPacket;
import de.sanandrew.mods.enderstuffp.network.PacketProcessor;
import de.sanandrew.mods.enderstuffp.network.packet.PacketTileDataSync.ITileSync;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumParticleFx;
import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.io.IOException;

public class TileEntityOreGenerator
        extends TileEntity
        implements IEnergyProvider, IInventory, ITileSync
{
    private static final int MAX_EXTRACTABLE_FLUX = 200;
    private static final int MAX_STORABLE_FLUX = 50_000;

    private int prevFluxAmount = -1;
    private int prevTicksGenRemain = -1;
    private ItemStack fuelStack = null;

    public long displayDrawCycles = 0;
    public double displayAmplitude = 0.0D;

    public int fluxAmount = 0;
    public int ticksGenRemain = 0;
    public int maxTicksGenRemain = 0;
    public int fluxGenerated = 0;

    public String customName = null;

    private ItemStack prevFuelStack = null;

    @Override
    public void updateEntity() {
        if( !this.worldObj.isRemote ) {
            //FIXME make it use of different ores = diff. time / diff. flux/tick
            if( this.ticksGenRemain > 0 ) {
                this.ticksGenRemain--;
                this.fluxAmount = Math.min(this.fluxAmount + this.fluxGenerated, MAX_STORABLE_FLUX);

                if( this.ticksGenRemain == 0 ) {
                    this.fluxGenerated = 0;
                }
            }

            if( this.fuelStack != null && this.ticksGenRemain == 0 && this.fluxAmount < MAX_STORABLE_FLUX ) {
                this.ticksGenRemain = 100;
                this.maxTicksGenRemain = 100;
                this.fluxGenerated = 80;
                this.prevFuelStack = this.fuelStack.copy();

                this.fuelStack = SAPUtils.decrStackSize(this.fuelStack);
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
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

            if( this.prevFluxAmount != this.fluxAmount || this.prevTicksGenRemain != this.ticksGenRemain ) {
                this.prevFluxAmount = this.fluxAmount;
                this.prevTicksGenRemain = this.ticksGenRemain;
                PacketProcessor.sendToAllAround(EnumPacket.TILE_DATA_SYNC, this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 64.0F,
                                                Unit.with(this));
            }
        } else if( this.ticksGenRemain > 0 && this.prevFuelStack != null ) {
            Pair data = Pair.with(Item.itemRegistry.getNameForObject(this.prevFuelStack.getItem()), this.prevFuelStack.getItemDamage());
            EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_ORE_GRIND, this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 0.5F,
                                               this.worldObj.provider.dimensionId, data);
        }
    }

    @Override
     public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.fluxAmount = nbt.getInteger("fluxAmount");
        this.ticksGenRemain = nbt.getInteger("ticksGenRemain");
        this.maxTicksGenRemain = nbt.getInteger("maxTicksGenRemain");
        this.fluxGenerated = nbt.getInteger("fluxGenerated");

        if( nbt.hasKey("fuelItem") ) {
            this.fuelStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("fuelItem"));
        }

        if( nbt.hasKey("prevFuelItem") ) {
            this.prevFuelStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("prevFuelItem"));
        }

        if( nbt.hasKey("customName") ) {
            this.customName = nbt.getString("customName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("fluxAmount", this.fluxAmount);
        nbt.setInteger("ticksGenRemain", this.ticksGenRemain);
        nbt.setInteger("maxTicksGenRemain", this.maxTicksGenRemain);
        nbt.setInteger("fluxGenerated", this.fluxGenerated);

        if( this.fuelStack != null ) {
            nbt.setTag("fuelItem", this.fuelStack.writeToNBT(new NBTTagCompound()));
        }

        if( this.prevFuelStack != null ) {
            nbt.setTag("prevFuelItem", this.prevFuelStack.writeToNBT(new NBTTagCompound()));
        }

        if( this.customName != null ) {
            nbt.setString("customName", this.customName);
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("fluxAmount", this.fluxAmount);
        nbt.setInteger("ticksGenRemain", this.ticksGenRemain);
        nbt.setInteger("maxTicksGenRemain", this.maxTicksGenRemain);
        nbt.setInteger("fluxGenerated", this.fluxGenerated);

        if( this.fuelStack != null ) {
            nbt.setTag("fuelItem", this.fuelStack.writeToNBT(new NBTTagCompound()));
        }

        if( this.prevFuelStack != null ) {
            nbt.setTag("prevFuelItem", this.prevFuelStack.writeToNBT(new NBTTagCompound()));
        }

        if( this.customName != null ) {
            nbt.setString("customName", this.customName);
        }

        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g();

        this.fluxAmount = nbt.getInteger("fluxAmount");
        this.ticksGenRemain = nbt.getInteger("ticksGenRemain");
        this.maxTicksGenRemain = nbt.getInteger("maxTicksGenRemain");
        this.fluxGenerated = nbt.getInteger("fluxGenerated");

        if( nbt.hasKey("fuelItem") ) {
            this.fuelStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("fuelItem"));
        }

        if( nbt.hasKey("prevFuelItem") ) {
            this.prevFuelStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("prevFuelItem"));
        }

        if( nbt.hasKey("customName") ) {
            this.customName = nbt.getString("customName");
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
        return this.customName != null ? this.customName : EspBlocks.oreGenerator.getUnlocalizedName() + ".name";
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
        return slot == 0 && (this.fuelStack == null || (SAPUtils.areStacksEqual(this.fuelStack, stack, true)
                                                        && this.fuelStack.stackSize < this.fuelStack.getMaxStackSize()));
    }

    @Override
    public void writeToStream(ByteBufOutputStream stream) throws IOException {
        stream.writeInt(this.fluxAmount);
        stream.writeInt(this.fluxGenerated);
        stream.writeInt(this.ticksGenRemain);
    }

    @Override
    public void readFromStream(ByteBufInputStream stream) throws IOException {
        this.fluxAmount = stream.readInt();
        this.fluxGenerated = stream.readInt();
        this.ticksGenRemain = stream.readInt();
    }
}
