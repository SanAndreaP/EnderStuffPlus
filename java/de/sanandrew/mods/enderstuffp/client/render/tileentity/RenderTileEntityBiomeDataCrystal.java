/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.render.tileentity;

import de.sanandrew.core.manpack.util.client.helpers.SAPClientUtils;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.client.model.tileentity.ModelBiomeDataCrystal;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeDataCrystal;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;
import org.lwjgl.opengl.GL11;

public class RenderTileEntityBiomeDataCrystal
        extends TileEntitySpecialRenderer
{
    public final ModelBiomeDataCrystal model = new ModelBiomeDataCrystal();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partTicks) {
        TileEntityBiomeDataCrystal crystalTile = (TileEntityBiomeDataCrystal) tile;
//

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y - 0.5D, z + 0.5D);
        this.bindTexture(EnumTextures.BIOMECRYSTAL_BLOCKTEXTURE);
        if( crystalTile.currRenderPass == 0 ) {
            model.render(0.0625F, 0);
            if( crystalTile.getDataProgress() > 0 ) {
                double minCore = 0.5D / 16.0D;
                double maxCore = 6.5D / 16.0D;
                double yMax = (1.0D - crystalTile.getDataProgress() / 10.0D) * (6.0D / 16.0D);
                double tessVMax = (1.0D - crystalTile.getDataProgress() / 10.0D) * 14.0D;
//                double tessVMax = crystalTile.getDataProgress() / 10.0F * 0.5F;
                float[] color = SAPUtils.getRgbaFromColorInt(BiomeGenBase.getBiome(crystalTile.getBiomeId()).color).getColorFloatArray();
//                float prevBrightX = OpenGlHelper.lastBrightnessX;
//                float prevBrightY = OpenGlHelper.lastBrightnessY;
//                int bright = 0xF0;
//                int brightX = bright % 65536;
//                int brightY = bright / 65536;
////
////                GL11.glTranslated(-0.48D, +0.52D, -0.48D);
//                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);
//                GL11.glPushMatrix();
//                GL11.glTranslated(0.0D, -(1.0D - yMax) * 0.2, 0.0D);
//                GL11.glTranslatef(this.model.data.offsetX, this.model.data.offsetY, this.model.data.offsetZ);
//                GL11.glTranslatef(this.model.data.rotationPointX * 0.0625F, this.model.data.rotationPointY * 0.0625F, this.model.data.rotationPointZ * 0.0625F);
//                GL11.glScaled(0.9D, 0.9D, 0.9D);
//                GL11.glScaled(1.0D, yMax, 1.0D);
//                GL11.glTranslatef(-this.model.data.offsetX, -this.model.data.offsetY, -this.model.data.offsetZ);
//                GL11.glTranslatef(-this.model.data.rotationPointX * 0.0625F, -this.model.data.rotationPointY * 0.0625F, -this.model.data.rotationPointZ * 0.0625F);
                GL11.glColor3f(color[0], color[1], color[2]);
//                this.model.data.render(0.0625F);
//                GL11.glPopMatrix();
////                Minecraft.getMinecraft().renderEngine.bindTexture(EnumTextures.BIOMECRYSTAL_CORE.getResource());
                GL11.glTranslated(-0.5D, +0.5D, -0.5D);
                GL11.glTranslated(4.5D / 16.0D, 4.5D / 16.0D, 4.5D / 16.0D);
                SAPClientUtils.drawTexturedSquareYPos(minCore, minCore, maxCore, maxCore, maxCore - yMax,
                                                      78.0D / 128.0D, 28.0D / 64.0D, 92.0D / 128.0D, 42.0D / 64.0D
                );
                SAPClientUtils.drawTexturedSquareYNeg(minCore, minCore, maxCore, maxCore, minCore,
                                                      92.0D / 128.0D, 28.0D / 64.0D, 106.0D / 128.0D, 42.0D / 64.0D
                );
                SAPClientUtils.drawTexturedSquareZNeg(minCore, minCore, maxCore, maxCore - yMax, minCore,
                                                      64.0D / 128.0D,  (42.0D + tessVMax) / 64.0D, 78.0D / 128.0D,  56.0D / 64.0D);
                SAPClientUtils.drawTexturedSquareXPos(minCore, minCore, maxCore - yMax, maxCore, maxCore,
                                                      78.0D / 128.0D,  (42.0D + tessVMax) / 64.0D, 92.0D / 128.0D,  56.0D / 64.0D);
                SAPClientUtils.drawTexturedSquareXNeg(minCore, minCore, maxCore - yMax, maxCore, minCore,
                                                      92.0D / 128.0D,  (42.0D + tessVMax) / 64.0D, 106.0D / 128.0D, 56.0D / 64.0D);
                SAPClientUtils.drawTexturedSquareZPos(minCore, minCore, maxCore, maxCore - yMax, maxCore,
                                                      106.0D / 128.0D, (42.0D + tessVMax) / 64.0D, 120.0D / 128.0D, 56.0D / 64.0D);
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
//                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, prevBrightX, prevBrightY);
            }
        } else {
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
            model.render(0.0625F, 1);
            GL11.glDisable(GL11.GL_BLEND);
        }
        GL11.glPopMatrix();

//        minCore -= 0.01D;
//        maxCore += 0.01D;
//        Minecraft.getMinecraft().renderEngine.bindTexture(EnumTextures.GLASS_TEXTURE);
//        SAPClientUtils.drawTexturedSquareYPos(x + minCore, z + minCore, x + maxCore, z + maxCore, y + maxCore, 0.0D, 0.0D, 1.0D, 1.0D);
//        SAPClientUtils.drawTexturedSquareYNeg(x + minCore, z + minCore, x + maxCore, z + maxCore, y + minCore, 0.0D, 0.0D, 1.0D, 1.0D);
//        SAPClientUtils.drawTexturedSquareXPos(y + minCore, z + minCore, y + maxCore, z + maxCore, x + maxCore, 0.0D, 0.0D, 1.0D, 1.0D);
//        SAPClientUtils.drawTexturedSquareXNeg(y + minCore, z + minCore, y + maxCore, z + maxCore, x + minCore, 0.0D, 0.0D, 1.0D, 1.0D);
//        SAPClientUtils.drawTexturedSquareZPos(x + minCore, y + minCore, x + maxCore, y + maxCore, z + maxCore, 0.0D, 0.0D, 1.0D, 1.0D);
//        SAPClientUtils.drawTexturedSquareZNeg(x + minCore, y + minCore, x + maxCore, y + maxCore, z + minCore, 0.0D, 0.0D, 1.0D, 1.0D);
    }
}
