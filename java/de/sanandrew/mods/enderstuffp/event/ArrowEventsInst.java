package de.sanandrew.mods.enderstuffp.event;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;

import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffplus.entity.EntityAvisArrow;
import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class ArrowEventsInst
{
    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event) {
        if( event.bow != null && event.bow.getItem() == ModItemRegistry.niobBow ) {
            event.charge *= 2;
        }

        if( event.entityPlayer.inventory.hasItem(ModItemRegistry.avisArrow) ) {
            boolean hasInfinity = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, event.bow) > 0;

            int charge = event.charge;
            float chargeRatio = charge / 20.0F;
            chargeRatio = (chargeRatio * chargeRatio + chargeRatio * 2.0F) / 3.0F;

            if( chargeRatio < 0.1D ) {
                return;
            }

            if( chargeRatio > 1.0F ) {
                chargeRatio = 1.0F;
            }

            EntityAvisArrow arrow = new EntityAvisArrow(event.entityPlayer.worldObj, event.entityPlayer, chargeRatio * 2.0F);

            if( chargeRatio == 1.0F ) {
                arrow.setIsCritical(true);
            }

            int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, event.bow);

            if( powerLevel > 0 ) {
                arrow.setDamage(arrow.getDamage() + powerLevel * 0.5D + 0.5D);
            }

            int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, event.bow);

            if( var10 > 0 ) {
                arrow.setKnockbackStrength(var10);
            }

            if( EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, event.bow) > 0 ) {
                arrow.setFire(100);
            }

            event.bow.damageItem(1, event.entityPlayer);
            event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.bow", 1.0F,
                                                        1.0F / (SAPUtils.RANDOM.nextFloat() * 0.4F + 1.2F) + chargeRatio * 0.5F);
            event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.orb", 0.05F,
                                                        1.0F / (SAPUtils.RANDOM.nextFloat() * 0.4F + 1.2F) + chargeRatio * 0.5F);

            if( !hasInfinity && !event.entityPlayer.capabilities.isCreativeMode ) {
                event.entityPlayer.inventory.consumeInventoryItem(ModItemRegistry.avisArrow);
            } else {
                arrow.canBePickedUp = 0;
            }

            if( !event.entityPlayer.worldObj.isRemote ) {
                event.entityPlayer.worldObj.spawnEntityInWorld(arrow);
            }

            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onArrowNock(ArrowNockEvent event) {
        if( event.entityPlayer.inventory.hasItem(ModItemRegistry.avisArrow) && !event.entityPlayer.capabilities.isCreativeMode ) {
            event.entityPlayer.setItemInUse(event.result, Items.bow.getMaxItemUseDuration(event.result));
        }
    }
}
