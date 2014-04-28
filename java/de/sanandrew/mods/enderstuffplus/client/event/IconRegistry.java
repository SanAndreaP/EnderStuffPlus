package de.sanandrew.mods.enderstuffplus.client.event;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.IIcon;

import net.minecraftforge.client.event.TextureStitchEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.client.texture.TextureAvisCompass;
import de.sanandrew.mods.enderstuffplus.registry.ModBlockRegistry;

@SideOnly(Side.CLIENT)
public class IconRegistry
{
    public static TextureAtlasSprite compass;
    public static IIcon sun;
    public static IIcon rain;
    public static IIcon thunder;
    public static IIcon guiActiveOn;
    public static IIcon guiActiveOff;
    public static IIcon guiSpanner;
    public static IIcon enderball;

    @SubscribeEvent
    public void onTexStitchPost(TextureStitchEvent.Post event) {
        ModBlockRegistry.endFluid.setIcons(ModBlockRegistry.endFluidBlock.getBlockTextureFromSide(0),
                                        ModBlockRegistry.endFluidBlock.getBlockTextureFromSide(1));
    }

    @SubscribeEvent
    public void onTexStitchPre(TextureStitchEvent.Pre event) {
        if( event.map.getTextureType() == 1 ) {
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
