package de.sanandrew.mods.enderstuffp.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.client.util.TextureAvisCompass;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;

@SideOnly(Side.CLIENT)
public class TextureStitchHandler
{
    public static TextureAtlasSprite compass = new TextureAvisCompass();
    public static IIcon sunIcon;
    public static IIcon rainIcon;
    public static IIcon thunderIcon;
    public static IIcon guiActiveOn;
    public static IIcon guiActiveOff;
    public static IIcon guiSpanner;
    public static IIcon enderball;

    @SubscribeEvent
    public void onTexStitchPost(TextureStitchEvent.Post event) {
//        ModBlockRegistry.endFluid.setIcons(ModBlockRegistry.endFluidBlock.getBlockTextureFromSide(0),
//                                        ModBlockRegistry.endFluidBlock.getBlockTextureFromSide(1));
    }

    @SubscribeEvent
    public void onTexStitchPre(TextureStitchEvent.Pre event) {
        if( event.map.getTextureType() == 1 ) {
            sunIcon = event.map.registerIcon("enderstuffp:sun");
            rainIcon = event.map.registerIcon("enderstuffp:rain");
            thunderIcon = event.map.registerIcon("enderstuffp:thunder");
            enderball = event.map.registerIcon("enderstuffp:enderball");
            guiSpanner = event.map.registerIcon("enderstuffp:spanner");
            guiActiveOn = event.map.registerIcon("enderstuffp:activeOn");
            guiActiveOff = event.map.registerIcon("enderstuffp:activeOff");

            event.map.setTextureEntry("enderstuffp:compass", compass);
        }
    }
}
