package sanandreasp.mods.EnderStuffPlus.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryDuplicator;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;

public class Container_Duplicator extends Container {
	public TileEntityDuplicator duplicator;

	public Container_Duplicator(InventoryPlayer par1InventoryPlayer, TileEntityDuplicator par2Duplicator) {
		this.duplicator = par2Duplicator;
		
//        for( int col = 0; col < biomeChanger.getSizeInventory(); col++ ) {
//        	addSlotToContainer(new Slot_BiomeChanger(biomeChanger, col, 8 + col * 18, 108));
//        }
		addSlotToContainer(new Slot(par2Duplicator, 0, 26, 11));
		addSlotToContainer(new Slot(par2Duplicator, 1, 26, 31));
		addSlotToContainer(new Slot(par2Duplicator, 2, 62, 54));
		addSlotToContainer(new Slot(par2Duplicator, 3, 97, 21));
		addSlotToContainer(new Slot(par2Duplicator, 4, 115, 21));
		addSlotToContainer(new Slot(par2Duplicator, 5, 133, 21));
		
		for( int row = 0; row < 3; row++ ) {
            for( int col = 0; col < 9; col++ ) {
            	addSlotToContainer(new Slot(par1InventoryPlayer, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for( int col = 0; col < 9; col++ ) {
        	addSlotToContainer(new Slot(par1InventoryPlayer, col, 8 + col * 18, 142));
        }
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if( var4 != null && var4.getHasStack() )
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if( par2 == 3 || par2 == 4 || par2 == 5 )
            {
                if( !this.mergeItemStack(var5, 6, 42, true) )
                {
                    return null;
                }

                var4.onSlotChange(var5, var3);
            }
            else if( par2 != 1 && par2 != 0 && par2 != 2 )
            {
                if( RegistryDuplicator.isItemDupable(var5) )
                {
                    if( !this.mergeItemStack(var5, 0, 2, false) )
                    {
                        return null;
                    }
                }
                else if( RegistryDuplicator.getBurnTime(var5) > 0 )
                {
                    if( !this.mergeItemStack(var5, 2, 3, false) )
                    {
                        return null;
                    }
                }
                else if( par2 >= 6 && par2 < 33 )
                {
                    if( !this.mergeItemStack(var5, 33, 42, false) )
                    {
                        return null;
                    }
                }
                else if( par2 >= 33 && par2 < 42 && !this.mergeItemStack(var5, 6, 33, false) )
                {
                    return null;
                }
            }
            else if( !this.mergeItemStack(var5, 6, 42, false) )
            {
                return null;
            }

            if( var5.stackSize == 0 )
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if( var5.stackSize == var3.stackSize )
            {
                return null;
            }

            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }

        return var3;
    }

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return this.duplicator.isUseableByPlayer(var1);
	}

}
