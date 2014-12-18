package de.sanandrew.mods.enderstuffp.event;

import net.minecraftforge.event.entity.player.BonemealEvent;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import de.sanandrew.mods.enderstuffplus.registry.ModBlockRegistry;

public class BonemealEventInst
{
    @SubscribeEvent
    public void onBonemeal(BonemealEvent event) {
        if( event.block == ModBlockRegistry.sapEndTree ) {
            if( !event.world.isRemote && event.world.rand.nextFloat() < 0.45D ) {
                ModBlockRegistry.sapEndTree.markOrGrowMarked(event.world, event.x, event.y, event.z, event.world.rand);
            }
            event.setResult(Result.ALLOW);
        }
    }
}
