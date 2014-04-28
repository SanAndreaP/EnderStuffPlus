package de.sanandrew.mods.enderstuffplus.registry.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.event.entity.living.LivingHurtEvent;

import de.sanandrew.mods.enderstuffplus.entity.EntityAvisArrow;
import de.sanandrew.mods.enderstuffplus.entity.EntityEnderAvis;
import de.sanandrew.mods.enderstuffplus.entity.IEnderPet;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;
import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class EntityHitEventInst
{
    @SubscribeEvent
    public void onEntityHit(LivingHurtEvent event) {
        if( (event.source.getSourceOfDamage() instanceof EntityAvisArrow) && !(event.entityLiving instanceof EntityEnderAvis) ) {
            EntityAvisArrow arrow = ((EntityAvisArrow) event.source.getSourceOfDamage());
            event.entityLiving.addPotionEffect(new PotionEffect(Potion.poison.id, 100, arrow.getIsCritical() ? 1 : 0));
        } else if( event.source.equals(DamageSource.fall) && event.entityLiving instanceof EntityPlayer ) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if( player.getCurrentEquippedItem() != null
                    && player.getCurrentEquippedItem().isItemEqual(new ItemStack(ModItemRegistry.avisFeather)) )
            {
                event.ammount = 0F;
            }
        } else if( event.source.getSourceOfDamage() instanceof EntityPlayer
                   && event.source.getSourceOfDamage().ridingEntity instanceof IEnderPet
                   && ((IEnderPet) event.source.getSourceOfDamage().ridingEntity).getCoatBase().equals(ESPModRegistry.MOD_ID + "_002") ) {
            event.ammount *= 1.5F;
        }
    }
}
