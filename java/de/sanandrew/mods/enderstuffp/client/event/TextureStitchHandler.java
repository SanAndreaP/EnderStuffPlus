/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.client.util.TextureAvisCompass;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
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
            sunIcon = event.map.registerIcon(EnderStuffPlus.MOD_ID + ":sun");
            rainIcon = event.map.registerIcon(EnderStuffPlus.MOD_ID + ":rain");
            thunderIcon = event.map.registerIcon(EnderStuffPlus.MOD_ID + ":thunder");
            enderball = event.map.registerIcon(EnderStuffPlus.MOD_ID + ":enderball");
            guiSpanner = event.map.registerIcon(EnderStuffPlus.MOD_ID + ":spanner");
            guiActiveOn = event.map.registerIcon(EnderStuffPlus.MOD_ID + ":activeOn");
            guiActiveOff = event.map.registerIcon(EnderStuffPlus.MOD_ID + ":activeOff");

            event.map.setTextureEntry(EnderStuffPlus.MOD_ID + ":compass", compass);
        }
    }
}
