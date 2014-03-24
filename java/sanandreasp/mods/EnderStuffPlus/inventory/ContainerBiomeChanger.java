package sanandreasp.mods.EnderStuffPlus.inventory;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

public class ContainerBiomeChanger extends Container {
	public TileEntityBiomeChanger biomeChanger;
	@SuppressWarnings("rawtypes")
	private List copiedInvSlots = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List copiedInvItems = new ArrayList();

	public ContainerBiomeChanger(InventoryPlayer par1InventoryPlayer, TileEntityBiomeChanger par2BiomeChanger) {
		this.biomeChanger = par2BiomeChanger;
		
        for( int col = 0; col < biomeChanger.getSizeInventory(); col++ ) {
        	addSlotToContainer(new Slot_BiomeChanger(biomeChanger, col, 8 + col * 18, 108));
        }
		
		for( int row = 0; row < 3; row++ ) {
            for( int col = 0; col < 9; col++ ) {
            	addSlotToContainer(new Slot(par1InventoryPlayer, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
            }
        }

        for( int col = 0; col < 9; col++ ) {
        	addSlotToContainer(new Slot(par1InventoryPlayer, col, 8 + col * 18, 198));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return this.biomeChanger.isUseableByPlayer(var1);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		if( this.inventorySlots.isEmpty() )
			return null;
		else {
	        ItemStack var3 = null;
	        Slot var4 = (Slot)this.inventorySlots.get(par2);

	        if( var4 != null && var4.getHasStack() )
	        {
	            ItemStack var5 = var4.getStack();
	            var3 = var5.copy();

	            if( par2 < 9 )
	            {
	                if( !this.mergeItemStack(var5, 9, this.inventorySlots.size(), true) )
	                {
	                    return null;
	                }
	            }
	            else if( this.biomeChanger.isFuelValid(var5) ) {
	            	if( !this.mergeItemStack(var5, 0, 9, false) )
	            		return null;
	            } else {	
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
	        }

	        return var3;
		}
	}

	@Override
    public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot)
    {
        return true;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void removeSlots() {
    	if( inventorySlots.isEmpty() )
    		return;
    	
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
}
