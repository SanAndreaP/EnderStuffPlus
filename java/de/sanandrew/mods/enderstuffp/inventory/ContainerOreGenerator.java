/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.inventory;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityOreGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerOreGenerator
        extends Container
{
    private TileEntityOreGenerator oreGenerator;
    private final Slot fuelSlot;

    public ContainerOreGenerator(InventoryPlayer invPlayer, TileEntityOreGenerator generator) {
        this.oreGenerator = generator;
        this.addSlotToContainer(this.fuelSlot = new Slot(generator, 0, 62, 70));

        for( int i = 0; i < 3; ++i ) {
            for( int j = 0; j < 9; ++j ) {
                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 106 + i * 18));
            }
        }

        for( int i = 0; i < 9; ++i ) {
            this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 164));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.oreGenerator.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
        ItemStack stackCopy = null;
        Slot slot = (Slot)this.inventorySlots.get(slotId);

        if( slot != null && slot.getHasStack() ) {
            ItemStack slotStack = slot.getStack();
            stackCopy = slotStack.copy();

            if( slotId == 0 ) {
                if( !this.mergeItemStack(slotStack, 1, 37, true) ) {
                    return null;
                }

                slot.onSlotChange(slotStack, stackCopy);
            } else if( !this.fuelSlot.getHasStack() && this.fuelSlot.isItemValid(slotStack) && slotStack.stackSize > 0 ) {
                if( !this.mergeItemStack(slotStack, 0, 1, false) ) {
                    return null;
                }
            } else if( this.fuelSlot.getHasStack() && SAPUtils.areStacksEqual(slotStack, this.fuelSlot.getStack(), true)
                       && this.fuelSlot.getStack().stackSize < this.fuelSlot.getStack().getMaxStackSize() )
            {
                if( !this.mergeItemStack(slotStack, 0, 1, false) ) {
                    return null;
                }
            } else if( slotId >= 1 && slotId < 28 ) {
                if( !this.mergeItemStack(slotStack, 28, 37, false) ) {
                    return null;
                }
            } else if( slotId >= 28 && slotId < 37 ) {
                if( !this.mergeItemStack(slotStack, 1, 28, false) ) {
                    return null;
                }
            } else if( !this.mergeItemStack(slotStack, 1, 37, false) ) {
                return null;
            }

            if( slotStack.stackSize == 0 ) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if( slotStack.stackSize == stackCopy.stackSize ) {
                return null;
            }

            slot.onPickupFromSlot(player, slotStack);
        }

        return stackCopy;
    }
}
