package sanandreasp.mods.EnderStuffPlus.inventory;

import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Slot_BiomeChanger extends Slot {
	private TileEntityBiomeChanger bcte;
	
	public Slot_BiomeChanger(TileEntityBiomeChanger par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		this.bcte = par1iInventory;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return this.bcte.isFuelValid(par1ItemStack) && !this.bcte.isActive();
	}
}
