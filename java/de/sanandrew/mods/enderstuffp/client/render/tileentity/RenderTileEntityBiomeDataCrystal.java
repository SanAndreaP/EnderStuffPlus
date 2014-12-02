/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.render.tileentity;

import de.sanandrew.core.manpack.util.client.SAPClientUtils;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeDataCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;
import org.lwjgl.opengl.GL11;

public class RenderTileEntityBiomeDataCrystal
        extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partTicks) {
        TileEntityBiomeDataCrystal crystalTile = (TileEntityBiomeDataCrystal) tile;
        double minCore = 3.0D / 16.0D;
        double maxCore = 13.0D / 16.0D;

        if( crystalTile.dataProgress > 0 ) {
            double yMax = (10.0D - crystalTile.dataProgress) / 16.0F;
            double tessVMax = crystalTile.dataProgress / 10.0F * 0.5F;
            float[] color = SAPUtils.getRgbaFromColorInt(BiomeGenBase.getBiome(crystalTile.getBiomeID()).color).getColorFloatArray();
            float prevBrightX = OpenGlHelper.lastBrightnessX;
            float prevBrightY = OpenGlHelper.lastBrightnessY;
            int bright = 0xF0;
            int brightX = bright % 65536;
            int brightY = bright / 65536;


            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);
            GL11.glColor3f(color[0], color[1], color[2]);
            Minecraft.getMinecraft().renderEngine.bindTexture(EnumTextures.BIOMECRYSTAL_CORE.getResource());
            SAPClientUtils.drawTexturedSquareYPos(x + minCore, z + minCore, x + maxCore, z + maxCore, y + maxCore - yMax, 0.25D, 0.0D, 0.5D, 0.5D);
            SAPClientUtils.drawTexturedSquareYNeg(x + minCore, z + minCore, x + maxCore, z + maxCore, y + minCore, 0.5D, 0.0D, 0.75D, 0.5D);
            SAPClientUtils.drawTexturedSquareXPos(y + minCore, z + minCore, y + maxCore - yMax, z + maxCore, x + maxCore, 0.25D, 1.0D - tessVMax, 0.5D, 1.0D);
            SAPClientUtils.drawTexturedSquareXNeg(y + minCore, z + minCore, y + maxCore - yMax, z + maxCore, x + minCore, 0.75D, 1.0D - tessVMax, 1.0D, 1.0D);
            SAPClientUtils.drawTexturedSquareZPos(x + minCore, y + minCore, x + maxCore, y + maxCore - yMax, z + maxCore, 0.0D, 1.0D - tessVMax, 0.25D, 1.0D);
            SAPClientUtils.drawTexturedSquareZNeg(x + minCore, y + minCore, x + maxCore, y + maxCore - yMax, z + minCore, 0.5D, 1.0D - tessVMax, 0.75D, 1.0D);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, prevBrightX, prevBrightY);
        }

        minCore -= 0.0001D;
        maxCore += 0.0001D;
        Minecraft.getMinecraft().renderEngine.bindTexture(EnumTextures.GLASS_TEXTURE);
        SAPClientUtils.drawTexturedSquareYPos(x + minCore, z + minCore, x + maxCore, z + maxCore, y + maxCore, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareYNeg(x + minCore, z + minCore, x + maxCore, z + maxCore, y + minCore, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareXPos(y + minCore, z + minCore, y + maxCore, z + maxCore, x + maxCore, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareXNeg(y + minCore, z + minCore, y + maxCore, z + maxCore, x + minCore, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareZPos(x + minCore, y + minCore, x + maxCore, y + maxCore, z + maxCore, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareZNeg(x + minCore, y + minCore, x + maxCore, y + maxCore, z + minCore, 0.0D, 0.0D, 1.0D, 1.0D);
    }
}
