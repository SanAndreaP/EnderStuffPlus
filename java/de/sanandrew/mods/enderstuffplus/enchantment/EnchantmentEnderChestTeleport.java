package de.sanandrew.mods.enderstuffplus.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class EnchantmentEnderChestTeleport
    extends Enchantment
{
    public EnchantmentEnderChestTeleport(int effectId, int weight) {
        super(effectId, weight, EnumEnchantmentType.all);
        this.setName("enderChestTel");
    }

    @Override
    public boolean canApply(ItemStack stack) {
        Item itm = stack.getItem();
        return super.canApply(stack)
               && (itm == ModItemRegistry.niobPick || itm == ModItemRegistry.niobAxe || itm == ModItemRegistry.niobShovel
                   || itm == ModItemRegistry.niobHoe || itm == ModItemRegistry.niobSword || itm == ModItemRegistry.niobShears);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return this.canApply(stack);
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench);
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
    public int getMinEnchantability(int level) {
        return 15;
    }
}
