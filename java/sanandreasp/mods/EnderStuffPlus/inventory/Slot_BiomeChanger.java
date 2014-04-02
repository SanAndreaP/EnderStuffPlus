package sanandreasp.mods.EnderStuffPlus.inventory;

import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Slot_BiomeChanger
    extends Slot
{
    private TileEntityBiomeChanger teBiomeChanger;

    public Slot_BiomeChanger(TileEntityBiomeChanger biomeChanger, int id, int x, int y) {
        super(biomeChanger, id, x, y);
        this.teBiomeChanger = biomeChanger;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return this.teBiomeChanger.isFuelValid(stack) && !this.teBiomeChanger.isActive();
    }
}
