package sanandreasp.mods.EnderStuffPlus.inventory;

import sanandreasp.mods.EnderStuffPlus.registry.RegistryDuplicator;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDuplicator
    extends Container
{
    private TileEntityDuplicator teDuplicator;

    public ContainerDuplicator(InventoryPlayer invPlayer, TileEntityDuplicator duplicator) {
        this.teDuplicator = duplicator;

        this.addSlotToContainer(new Slot(duplicator, 0, 26, 11));
        this.addSlotToContainer(new Slot(duplicator, 1, 26, 31));
        this.addSlotToContainer(new Slot(duplicator, 2, 62, 54));
        this.addSlotToContainer(new Slot(duplicator, 3, 97, 21));
        this.addSlotToContainer(new Slot(duplicator, 4, 115, 21));
        this.addSlotToContainer(new Slot(duplicator, 5, 133, 21));

        for( int row = 0; row < 3; row++ ) {
            for( int col = 0; col < 9; col++ ) {
                this.addSlotToContainer(new Slot(invPlayer, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for( int col = 0; col < 9; col++ ) {
            this.addSlotToContainer(new Slot(invPlayer, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.teDuplicator.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
        ItemStack copyStack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotId);

        if( slot != null && slot.getHasStack() ) {
            ItemStack slotStack = slot.getStack();
            copyStack = slotStack.copy();

            if( slotId == 3 || slotId == 4 || slotId == 5 ) {
                if( !this.mergeItemStack(slotStack, 6, 42, true) ) {
                    return null;
                }

                slot.onSlotChange(slotStack, copyStack);
            } else if( slotId != 1 && slotId != 0 && slotId != 2 ) {
                if( RegistryDuplicator.isItemDupable(slotStack) ) {
                    if( !this.mergeItemStack(slotStack, 0, 2, false) ) {
                        return null;
                    }
                } else if( RegistryDuplicator.getBurnTime(slotStack) > 0 ) {
                    if( !this.mergeItemStack(slotStack, 2, 3, false) ) {
                        return null;
                    }
                } else if( slotId >= 6 && slotId < 33 ) {
                    if( !this.mergeItemStack(slotStack, 33, 42, false) ) {
                        return null;
                    }
                } else if( slotId >= 33 && slotId < 42 && !this.mergeItemStack(slotStack, 6, 33, false) ) {
                    return null;
                }
            } else if( !this.mergeItemStack(slotStack, 6, 42, false) ) {
                return null;
            }

            if( slotStack.stackSize == 0 ) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if( slotStack.stackSize == copyStack.stackSize ) {
                return null;
            }

            slot.onPickupFromSlot(player, slotStack);
        }

        return copyStack;
    }

    public TileEntityDuplicator getDuplicator() {
        return this.teDuplicator;
    }
}
