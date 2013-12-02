package sanandreasp.mods.EnderStuffPlus.enchantment;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class EnchantmentEnderChestTeleport extends Enchantment {

    public EnchantmentEnderChestTeleport(int par1, int par2) {
        super(par1, par2, EnumEnchantmentType.all);
        this.setName("enderChestTel");
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApplyTogether(Enchantment par1Enchantment) {
        return super.canApplyTogether(par1Enchantment);
    }

    @Override
    public boolean canApply(ItemStack par1ItemStack) {
    	int id = par1ItemStack.itemID;
        return super.canApply(par1ItemStack)
        		&& (
        				id == ESPModRegistry.niobPick.itemID ||
        				id == ESPModRegistry.niobAxe.itemID ||
        				id == ESPModRegistry.niobShovel.itemID ||
        				id == ESPModRegistry.niobHoe.itemID ||
        				id == ESPModRegistry.niobSword.itemID ||
        				id == ESPModRegistry.niobShears.itemID
        		   );
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
    	return this.canApply(stack);
    }

}
