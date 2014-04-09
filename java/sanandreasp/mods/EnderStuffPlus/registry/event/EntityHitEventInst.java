package sanandreasp.mods.EnderStuffPlus.registry.event;

import sanandreasp.mods.EnderStuffPlus.entity.EntityAvisArrow;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EntityHitEventInst
{
    @ForgeSubscribe
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
