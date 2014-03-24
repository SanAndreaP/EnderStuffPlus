package sanandreasp.mods.EnderStuffPlus.client.registry;

import sanandreasp.mods.EnderStuffPlus.client.texture.TextureAvisCompass;
import sanandreasp.mods.EnderStuffPlus.registry.BlockRegistry;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Icon;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		BlockRegistry.endFluid.setIcons(BlockRegistry.endFluidBlock.getBlockTextureFromSide(0), BlockRegistry.endFluidBlock.getBlockTextureFromSide(1));
	}
}
