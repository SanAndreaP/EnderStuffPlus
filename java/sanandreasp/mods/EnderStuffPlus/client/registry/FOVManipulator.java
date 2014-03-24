package sanandreasp.mods.EnderStuffPlus.client.registry;

import sanandreasp.mods.EnderStuffPlus.registry.ItemRegistry;

import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.ForgeSubscribe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FOVManipulator
{
	@ForgeSubscribe
	public void onUpdateFOV(FOVUpdateEvent event) {
		float fov = event.fov;

		if( event.entity.isUsingItem() && event.entity.getItemInUse().getItem() == ItemRegistry.niobBow ) {
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
