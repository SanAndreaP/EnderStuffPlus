package de.sanandrew.mods.enderstuffp.item;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumEnderOres;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemNiobArmor
    extends ItemArmor
{
    public static final int RENDER_ID = EnderStuffPlus.proxy.addArmor("armorNiobium");

    public ItemNiobArmor(String name, String texture, ArmorMaterial material, int slotId) {
        super(material, RENDER_ID, slotId);
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ':' + name);
        this.setTextureName(EnderStuffPlus.MOD_ID + ':' + texture);
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if( stack.getItem() == EspItems.niobHelmet || stack.getItem() == EspItems.niobPlate || stack.getItem() == EspItems.niobBoots ) {
            return EnumTextures.TEX_ARMOR_NIOBIUM_1.getTexture();
        } else if( stack.getItem() == EspItems.niobLegs ) {
            return EnumTextures.TEX_ARMOR_NIOBIUM_2.getTexture();
        }
        return super.getArmorTexture(stack, entity, slot, type);
    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return SAPUtils.areStacksEqual(repairItem, EnumEnderOres.REPAIR_ITEM_NIOBIUM, false) || super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        if( !world.isRemote && player != null && !player.getActivePotionEffects().isEmpty() ) {
            List<PotionEffect> badPotions = new ArrayList<>();
            for( PotionEffect pEffect : (Collection<PotionEffect>) player.getActivePotionEffects() ) {
                if( Potion.potionTypes[pEffect.getPotionID()].isBadEffect() ) {
                    badPotions.add(pEffect);
                }
            }

            if( !badPotions.isEmpty() ) {
                boolean shouldRemovePotion = true;
                for( int i = 0; i < 4; i++ ) {
                    if( player.getCurrentArmor(i) == null ) {
                        shouldRemovePotion = false;
                        break;
                    } else if( player.getCurrentArmor(i).getItem() != EnderStuffPlus.niobSet.get(i).getItem() ) {
                        shouldRemovePotion = false;
                        break;
                    }
                }

                if( shouldRemovePotion ) {
                    for( PotionEffect pEffect : badPotions ) {
                        player.removePotionEffect(pEffect.getPotionID());
                        player.inventory.damageArmor(2 + 2 * pEffect.getAmplifier());
                    }
                }
            }
        }
    }
}
