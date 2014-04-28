package de.sanandrew.mods.enderstuffplus.inventory;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityBiomeChanger;

public class ContainerBiomeChanger
    extends Container
{
    private TileEntityBiomeChanger teBiomeChanger;
    @SuppressWarnings("rawtypes")
    private List copiedInvItems = new ArrayList();
    @SuppressWarnings("rawtypes")
    private List copiedInvSlots = new ArrayList();

    public ContainerBiomeChanger(InventoryPlayer invPlayer, TileEntityBiomeChanger biomeChanger) {
        this.teBiomeChanger = biomeChanger;

        for( int col = 0; col < this.teBiomeChanger.getSizeInventory(); col++ ) {
            this.addSlotToContainer(new Slot_BiomeChanger(this.teBiomeChanger, col, 8 + col * 18, 108));
        }

        for( int row = 0; row < 3; row++ ) {
            for( int col = 0; col < 9; col++ ) {
                this.addSlotToContainer(new Slot(invPlayer, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
            }
        }

        for( int col = 0; col < 9; col++ ) {
            this.addSlotToContainer(new Slot(invPlayer, col, 8 + col * 18, 198));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.teBiomeChanger.isUseableByPlayer(player);
    }

    @Override
    public boolean func_94530_a(ItemStack stack, Slot slot) {
        return true;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void removeSlots() {
        if( this.inventorySlots.isEmpty() ) {
            return;
        }

        this.copiedInvSlots = new ArrayList(this.inventorySlots);
        this.copiedInvItems = new ArrayList(this.inventoryItemStacks);

        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void restoreSlots() {
        this.inventorySlots = new ArrayList(this.copiedInvSlots);
        this.inventoryItemStacks = new ArrayList(this.copiedInvItems);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
        if( this.inventorySlots.isEmpty() ) {
            return null;
        } else {
            ItemStack copySlot = null;
            Slot slot = (Slot) this.inventorySlots.get(slotId);

            if( slot != null && slot.getHasStack() ) {
                ItemStack slotStack = slot.getStack();
                copySlot = slotStack.copy();

                if( slotId < 9 ) {
                    if( !this.mergeItemStack(slotStack, 9, this.inventorySlots.size(), true) ) {
                        return null;
                    }
                } else if( this.teBiomeChanger.isFuelValid(slotStack) ) {
                    if( !this.mergeItemStack(slotStack, 0, 9, false) ) {
                        return null;
                    }
                } else {
                    return null;
                }

                if( slotStack.stackSize == 0 ) {
                    slot.putStack((ItemStack) null);
                } else {
                    slot.onSlotChanged();
                }
            }

            return copySlot;
        }
    }

    public TileEntityBiomeChanger getBiomeChanger() {
        return this.teBiomeChanger;
    }
}
