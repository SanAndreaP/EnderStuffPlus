package sanandreasp.mods.EnderStuffPlus.client.registry;

import sanandreasp.mods.EnderStuffPlus.client.texture.TextureAvisCompass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.util.Icon;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

public class IconRegistry {
	
	public static Icon sun, rain, thunder, activeOn, activeOff, spanner, enderball;
	public static TextureAtlasSprite compass;
	
	@ForgeSubscribe
	public void onItemIconRegister(TextureStitchEvent.Pre evt) {
		if( evt.map.textureType == 1 ) {
			this.sun = evt.map.registerIcon("enderstuffp:sun");
			this.rain = evt.map.registerIcon("enderstuffp:rain");
			this.thunder = evt.map.registerIcon("enderstuffp:thunder");
			this.activeOn = evt.map.registerIcon("enderstuffp:activeOn");
			this.activeOff = evt.map.registerIcon("enderstuffp:activeOff");
			this.spanner = evt.map.registerIcon("enderstuffp:spanner");
			this.enderball = evt.map.registerIcon("enderstuffp:enderball");
			
			evt.map.setTextureEntry("enderstuffp:compass", this.compass = new TextureAvisCompass());
		}
	}
}
