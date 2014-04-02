package sanandreasp.mods.EnderStuffPlus.item;

import sanandreasp.core.manpack.helpers.ItemHorseArmor;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.ItemStack;

public class ItemEndHorseArmor
    extends ItemHorseArmor
{
    public ItemEndHorseArmor(int id) {
        super(id);
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
