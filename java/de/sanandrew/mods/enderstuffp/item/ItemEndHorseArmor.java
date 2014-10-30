package de.sanandrew.mods.enderstuffp.item;

import de.sanandrew.core.manpack.item.AItemHorseArmor;
import de.sanandrew.mods.enderstuffp.util.CreativeTabsEnderStuff;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.ItemStack;

public class ItemEndHorseArmor
        extends AItemHorseArmor
{
    public ItemEndHorseArmor() {
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":enderHorseArmor");
        this.setTextureName(EnderStuffPlus.MOD_ID + ":enderStick");
        this.setCreativeTab(CreativeTabsEnderStuff.ESP_TAB);
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
