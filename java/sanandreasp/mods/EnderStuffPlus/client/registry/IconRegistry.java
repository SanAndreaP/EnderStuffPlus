package sanandreasp.mods.EnderStuffPlus.client.registry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.mods.EnderStuffPlus.client.texture.TextureAvisCompass;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Icon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

@SideOnly(Side.CLIENT)
public class IconRegistry
{
	public static Icon sun, rain, thunder, activeOn, activeOff, spanner, enderball;
	public static TextureAtlasSprite compass;
	
	@ForgeSubscribe
	public void onTexStitchPre(TextureStitchEvent.Pre evt) {
		if( evt.map.textureType == 1 ) {
			IconRegistry.sun = evt.map.registerIcon("enderstuffp:sun");
			IconRegistry.rain = evt.map.registerIcon("enderstuffp:rain");
			IconRegistry.thunder = evt.map.registerIcon("enderstuffp:thunder");
			IconRegistry.activeOn = evt.map.registerIcon("enderstuffp:activeOn");
			IconRegistry.activeOff = evt.map.registerIcon("enderstuffp:activeOff");
			IconRegistry.spanner = evt.map.registerIcon("enderstuffp:spanner");
			IconRegistry.enderball = evt.map.registerIcon("enderstuffp:enderball");
			
			evt.map.setTextureEntry("enderstuffp:compass", IconRegistry.compass = new TextureAvisCompass());
		}
	}
	
	@ForgeSubscribe
	public void onTexStitchPost(TextureStitchEvent.Post evt) {
		ESPModRegistry.endFluid.setIcons(ESPModRegistry.endFluidBlock.getBlockTextureFromSide(0), ESPModRegistry.endFluidBlock.getBlockTextureFromSide(1));
	}
}
