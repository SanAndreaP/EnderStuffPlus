package sanandreasp.mods.EnderStuffPlus.client.event;

import sanandreasp.mods.EnderStuffPlus.client.texture.TextureAvisCompass;
import sanandreasp.mods.EnderStuffPlus.registry.ModBlockRegistry;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Icon;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class IconRegistry
{
    public static TextureAtlasSprite compass;
    public static Icon sun;
    public static Icon rain;
    public static Icon thunder;
    public static Icon guiActiveOn;
    public static Icon guiActiveOff;
    public static Icon guiSpanner;
    public static Icon enderball;

    @ForgeSubscribe
    public void onTexStitchPost(TextureStitchEvent.Post event) {
        ModBlockRegistry.endFluid.setIcons(ModBlockRegistry.endFluidBlock.getBlockTextureFromSide(0),
                                        ModBlockRegistry.endFluidBlock.getBlockTextureFromSide(1));
    }

    @ForgeSubscribe
    public void onTexStitchPre(TextureStitchEvent.Pre event) {
        if( event.map.textureType == 1 ) {
            sun = event.map.registerIcon("enderstuffp:sun");
            rain = event.map.registerIcon("enderstuffp:rain");
            thunder = event.map.registerIcon("enderstuffp:thunder");
            enderball = event.map.registerIcon("enderstuffp:enderball");
            guiSpanner = event.map.registerIcon("enderstuffp:spanner");
            guiActiveOn = event.map.registerIcon("enderstuffp:activeOn");
            guiActiveOff = event.map.registerIcon("enderstuffp:activeOff");

            event.map.setTextureEntry("enderstuffp:compass", compass = new TextureAvisCompass());
        }
    }
}
