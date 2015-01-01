package de.sanandrew.mods.enderstuffp.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.entity.living.IEnderPet;
import de.sanandrew.mods.enderstuffp.entity.projectile.EntityAvisArrow;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.oredict.OreDictionary;

public class EntityHitEventHandler
{
    private static final ItemStack AVIS_FEATHER = new ItemStack(EspItems.avisFeather, 1, OreDictionary.WILDCARD_VALUE);

    @SubscribeEvent
    public void onEntityHit(LivingHurtEvent event) {
        if( (event.source.getSourceOfDamage() instanceof EntityAvisArrow) /*&& !(event.entityLiving instanceof EntityEnderAvis)*/ ) {
            EntityAvisArrow arrow = ((EntityAvisArrow) event.source.getSourceOfDamage());
            event.entityLiving.addPotionEffect(new PotionEffect(Potion.poison.id, 100, arrow.getIsCritical() ? 1 : 0));
        } else if( event.source.equals(DamageSource.fall) && SAPUtils.areStacksEqual(event.entityLiving.getHeldItem(), AVIS_FEATHER, false) ) {
                event.ammount = 0.0F;
        } else if( event.source.getSourceOfDamage() instanceof EntityPlayer && event.source.getSourceOfDamage().ridingEntity instanceof IEnderPet
                   && ((IEnderPet) event.source.getSourceOfDamage().ridingEntity).getCoatBase().equals(RaincoatManager.baseIron) )
        {
            event.ammount *= 1.5F;
        }
    }
}
