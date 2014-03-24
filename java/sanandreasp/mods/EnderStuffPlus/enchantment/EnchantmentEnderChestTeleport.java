package sanandreasp.mods.EnderStuffPlus.enchantment;

import sanandreasp.mods.EnderStuffPlus.registry.ItemRegistry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantmentEnderChestTeleport extends Enchantment
{
    public EnchantmentEnderChestTeleport(int effectId, int weight) {
        super(effectId, weight, EnumEnchantmentType.all);
        this.setName("enderChestTel");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return super.getMinEnchantability(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench);
    }

    @Override
    public boolean canApply(ItemStack stack) {
    	Item itm = stack.getItem();
        return super.canApply(stack)
        	   && (itm == ItemRegistry.niobPick
        	       || itm == ItemRegistry.niobAxe
        	       || itm == ItemRegistry.niobShovel
        	       || itm == ItemRegistry.niobHoe
        	       || itm == ItemRegistry.niobSword
        	       || itm == ItemRegistry.niobShears
        		  );
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
    	return this.canApply(stack);
    }
}
