package sanandreasp.mods.EnderStuffPlus.registry.event;

import sanandreasp.mods.EnderStuffPlus.registry.ModBlockRegistry;

import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class BonemealEventInst
{
    @ForgeSubscribe
    public void onBonemeal(BonemealEvent event) {
        if( event.ID == ModBlockRegistry.sapEndTree.blockID ) {
            if( !event.world.isRemote && event.world.rand.nextFloat() < 0.45D ) {
                ModBlockRegistry.sapEndTree.markOrGrowMarked(event.world, event.X, event.Y, event.Z, event.world.rand);
            }
            event.setResult(Result.ALLOW);
        }
    }
}
