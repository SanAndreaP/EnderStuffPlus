package de.sanandrew.mods.enderstuffplus.item;

import de.sanandrew.core.manpack.item.AItemHorseArmor;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.ItemStack;

public class ItemEndHorseArmor
    extends AItemHorseArmor
{
    public ItemEndHorseArmor() {
        super();
    }

    @Override
    public String getArmorTexture(EntityHorse horse, ItemStack stack) {
        if( stack.getItemDamage() == 1 ) {
            return "enderstuffp:textures/entity/horse/armor/horse_armor_niobium.png";
        } else {
            return "enderstuffp:textures/entity/horse/armor/horse_armor_niobium.png";
        }
    }

    @Override
    public int getArmorValue(EntityHorse horse, ItemStack stack) {
        return 9;
    }

}
