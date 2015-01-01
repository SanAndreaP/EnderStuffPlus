package de.sanandrew.mods.enderstuffp.event;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class BonemealEventHandler
{
    @SubscribeEvent
    public void onBonemeal(BonemealEvent event) {
        if( event.block == EspBlocks.sapEndTree ) {
            if( !event.world.isRemote && event.world.rand.nextFloat() < 0.45D ) {
                EspBlocks.sapEndTree.markOrGrowMarked(event.world, event.x, event.y, event.z, event.world.rand);
            }

            event.setResult(Result.ALLOW);
        }
    }
}
