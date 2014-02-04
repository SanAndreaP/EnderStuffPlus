package sanandreasp.mods.EnderStuffPlus.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

public class ItemNiobArmor extends ItemArmor implements Textures {
	
	public static int renderID = ESPModRegistry.proxy.addArmor("niobArmor");

	public ItemNiobArmor(int par1ID, EnumArmorMaterial material, int par2SlotID) {
		super(par1ID, material, renderID, par2SlotID);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (stack.itemID == ESPModRegistry.niobHelmet.itemID
				|| stack.itemID == ESPModRegistry.niobPlate.itemID
				|| stack.itemID == ESPModRegistry.niobBoots.itemID) {
			return TEX_ARMOR_NIOBIUM_1;
		} else if( stack.itemID == ESPModRegistry.niobLegs.itemID ) {
			return TEX_ARMOR_NIOBIUM_2;
		}
		return super.getArmorTexture(stack, entity, slot, type);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack) {
		if( player != null && !player.getActivePotionEffects().isEmpty() && !world.isRemote ) {
			List<PotionEffect> badPotions = new ArrayList<PotionEffect>();
			for( PotionEffect pEffect : (Collection<PotionEffect>)player.getActivePotionEffects() ) {
				if( Potion.potionTypes[pEffect.getPotionID()].isBadEffect() )
					badPotions.add(pEffect);
			}
			if( !badPotions.isEmpty() ) {
				boolean b = true;
				for( int i = 1; i <= 4; i++ ) {
					if( player.getCurrentItemOrArmor(i) == null ) {
						b = false;
						break;
					} else if( player.getCurrentItemOrArmor(i).itemID != ESPModRegistry.niobSet.get(i-1).itemID ) {
						b = false;
						break;
					}
				}
				if( b ) {
					for( PotionEffect pEffect : badPotions ) {
						player.removePotionEffect(pEffect.getPotionID());
						player.inventory.damageArmor(2 + 2*pEffect.getAmplifier());
//						int randArmor = player.getRNG().nextInt(4)+1;
//						if (!player.capabilities.isCreativeMode
//								&& player.getCurrentItemOrArmor(randArmor).attemptDamageItem(2 + 2*pEffect.getAmplifier(), player.getRNG()))
//							player.setCurrentItemOrArmor(randArmor, null);
					}
				}
			}
		}
	}
	
	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.itemID == ESPModRegistry.endIngot.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
}
