package sanandreasp.mods.EnderStuffPlus.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemNiobArmor
    extends ItemArmor
{
    public static final int RENDER_ID = ESPModRegistry.proxy.addArmor("niobArmor");

    public ItemNiobArmor(int id, EnumArmorMaterial material, int slotId) {
        super(id, material, RENDER_ID, slotId);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if( stack.getItem() == ModItemRegistry.niobHelmet || stack.getItem() == ModItemRegistry.niobPlate
            || stack.getItem() == ModItemRegistry.niobBoots )
        {
            return Textures.TEX_ARMOR_NIOBIUM_1.getTexture();
        } else if( stack.itemID == ModItemRegistry.niobLegs.itemID ) {
            return Textures.TEX_ARMOR_NIOBIUM_2.getTexture();
        }
        return super.getArmorTexture(stack, entity, slot, type);
    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return repairItem.itemID == ModItemRegistry.endIngot.itemID ? true : super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack stack) {
        if( !world.isRemote && player != null && !player.getActivePotionEffects().isEmpty() ) {
            List<PotionEffect> badPotions = new ArrayList<PotionEffect>();
            for( PotionEffect pEffect : (Collection<PotionEffect>) player.getActivePotionEffects() ) {
                if( Potion.potionTypes[pEffect.getPotionID()].isBadEffect() ) {
                    badPotions.add(pEffect);
                }
            }
            if( !badPotions.isEmpty() ) {
                boolean shouldRemoveBadPotions = true;
                for( int i = 1; i <= 4; i++ ) {
                    if( player.getCurrentItemOrArmor(i) == null ) {
                        shouldRemoveBadPotions = false;
                        break;
                    } else if( player.getCurrentItemOrArmor(i).getItem() != ESPModRegistry.niobSet.get(i - 1).getItem() ) {
                        shouldRemoveBadPotions = false;
                        break;
                    }
                }
                if( shouldRemoveBadPotions ) {
                    for( PotionEffect pEffect : badPotions ) {
                        player.removePotionEffect(pEffect.getPotionID());
                        player.inventory.damageArmor(2 + 2 * pEffect.getAmplifier());
                    }
                }
            }
        }
    }
}
