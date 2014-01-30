package sanandreasp.mods.EnderStuffPlus.client.registry;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class FOVManipulator {
	@ForgeSubscribe
	public void onUpdateFOV(FOVUpdateEvent event) {
		float f = event.fov;
		
		if( event.entity.isUsingItem() && event.entity.getItemInUse().itemID == ESPModRegistry.niobBow.itemID ) {
			int i = event.entity.getItemInUseDuration();
			float f1 = (float)i / 10.0F;
			
			if( f1 > 1.0F ) {
			    f1 = 1.0F;
			} else {
			    f1 *= f1;
			}
			
			f *= 1.0F - f1 * 0.3F;
        }
		
		event.newfov = f;
	}
}
