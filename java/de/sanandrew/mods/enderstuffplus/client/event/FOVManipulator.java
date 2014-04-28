package de.sanandrew.mods.enderstuffplus.client.event;

import net.minecraftforge.client.event.FOVUpdateEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

@SideOnly(Side.CLIENT)
public class FOVManipulator
{
    @SubscribeEvent
    public void onUpdateFOV(FOVUpdateEvent event) {
        float fov = event.fov;

        if( event.entity.isUsingItem() && event.entity.getItemInUse().getItem() == ModItemRegistry.niobBow ) {
            int duration = event.entity.getItemInUseDuration();
            float multiplier = duration / 10.0F;

            if( multiplier > 1.0F ) {
                multiplier = 1.0F;
            } else {
                multiplier *= multiplier;
            }

            fov *= 1.0F - multiplier * 0.3F;
        }

        event.newfov = fov;
    }
}
