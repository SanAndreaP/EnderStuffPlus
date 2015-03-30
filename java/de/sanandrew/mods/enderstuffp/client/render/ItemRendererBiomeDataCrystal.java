/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.render;

import de.sanandrew.core.manpack.util.client.helpers.SAPClientUtils;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.client.render.tileentity.RenderTileEntityBiomeDataCrystal;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.item.block.ItemBlockBiomeDataCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRendererBiomeDataCrystal
        implements IItemRenderer
{
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type != ItemRenderType.INVENTORY; // don't pre-transform when in inventory, due to my progress bar renderer!
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        int biomeId = item.hasTagCompound() ? item.getTagCompound().getShort(ItemBlockBiomeDataCrystal.NBT_BIOME) : -1;
        int dataProgress = item.hasTagCompound() ? item.getTagCompound().getShort(ItemBlockBiomeDataCrystal.NBT_DATAPROG) : 0;

        GL11.glPushMatrix();
        if( type == ItemRenderType.INVENTORY ) {        // GL-transform the model itself, like the "render helper" would, only for inventories!
            GL11.glEnable(GL11.GL_LIGHTING);            // also enable lighting
            GL11.glTranslatef(-2.0F, 3.0F, 0.0F);
            GL11.glScalef(10.0F, 10.0F, 10.0F);
            GL11.glTranslatef(1.0F, 0.5F, 1.0F);
            GL11.glScalef(1.0F, 1.0F, -1.0F);
            GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        }

        switch( type ){
            case ENTITY:
                GL11.glTranslatef(0.0F, -1.0F, 0.0F);
                break;
            case INVENTORY:
                GL11.glTranslatef(1.0F, -0.2F, 1.0F);
                break;
            case EQUIPPED: //FALL-THROUGH
            default:
                GL11.glTranslatef(0.5F, -0.5F, 0.5F);
        }

        // render my model - opaque parts
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(EnumTextures.TILE_BIOMECRYSTAL.getResource());
        RenderTileEntityBiomeDataCrystal.MODEL.render(0.0625F, 0);


        if( dataProgress > 0 ) {        // render data core with progress
            float prevBrightX = OpenGlHelper.lastBrightnessX;
            float prevBrightY = OpenGlHelper.lastBrightnessY;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0xF0, 0x0);

            float[] color = SAPUtils.getRgbaFromColorInt(BiomeGenBase.getBiome(biomeId).color).getColorFloatArray();
            GL11.glColor3f(color[0], color[1], color[2]);

            double minCore = 0.5D / 16.0D;
            double maxCore = 6.5D / 16.0D;
            double yMax = (1.0D - dataProgress / 10.0D) * (6.0D / 16.0D);
            double tessVMax = (1.0D - dataProgress / 10.0D) * 14.0D;
            GL11.glTranslated(-0.5D, 0.5D, -0.5D);
            GL11.glTranslated(4.5D / 16.0D, 4.5D / 16.0D, 4.5D / 16.0D);
            SAPClientUtils.drawTexturedSquareYPos(minCore, minCore, maxCore, maxCore, maxCore - yMax,
                                                  78.0D / 128.0D, 28.0D / 64.0D, 92.0D / 128.0D, 42.0D / 64.0D
            );
            SAPClientUtils.drawTexturedSquareYNeg(minCore, minCore, maxCore, maxCore, minCore,
                                                  92.0D / 128.0D, 28.0D / 64.0D, 106.0D / 128.0D, 42.0D / 64.0D
            );
            SAPClientUtils.drawTexturedSquareZNeg(minCore, minCore, maxCore, maxCore - yMax, minCore,
                                                  64.0D / 128.0D, (42.0D + tessVMax) / 64.0D, 78.0D / 128.0D, 56.0D / 64.0D
            );
            SAPClientUtils.drawTexturedSquareXPos(minCore, minCore, maxCore - yMax, maxCore, maxCore,
                                                  78.0D / 128.0D, (42.0D + tessVMax) / 64.0D, 92.0D / 128.0D, 56.0D / 64.0D
            );
            SAPClientUtils.drawTexturedSquareXNeg(minCore, minCore, maxCore - yMax, maxCore, minCore,
                                                  92.0D / 128.0D, (42.0D + tessVMax) / 64.0D, 106.0D / 128.0D, 56.0D / 64.0D
            );
            SAPClientUtils.drawTexturedSquareZPos(minCore, minCore, maxCore, maxCore - yMax, maxCore,
                                                  106.0D / 128.0D, (42.0D + tessVMax) / 64.0D, 120.0D / 128.0D, 56.0D / 64.0D
            );
            GL11.glColor3f(1.0F, 1.0F, 1.0F);

            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, prevBrightX, prevBrightY);
        }
        GL11.glPopMatrix();

        // render my model - transparent parts
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        RenderTileEntityBiomeDataCrystal.MODEL.render(0.0625F, 1);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glPopMatrix();

        // render progress bar
        if( type == ItemRenderType.INVENTORY && dataProgress > 0 ) {
            renderProgressBar(dataProgress, biomeId);
        }
    }

    private static void renderProgressBar(int dataProgress, int biomeId) {
        float[] color = SAPUtils.getRgbaFromColorInt(BiomeGenBase.getBiome(biomeId).color).getColorFloatArray();
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);

        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.setColorOpaque_I(0x0);
        tess.addVertex(1.0, 13.0, 0.0);
        tess.addVertex(1.0, 15.0, 0.0);
        tess.addVertex(15.0, 15.0, 0.0);
        tess.addVertex(15.0, 13.0, 0.0);
        tess.setColorOpaque_F(color[0] * 0.2F, color[1] * 0.2F, color[2] * 0.2F);
        tess.addVertex(1.0, 13.0, 0.0);
        tess.addVertex(1.0, 14.0, 0.0);
        tess.addVertex(15.0, 14.0, 0.0);
        tess.addVertex(15.0, 13.0, 0.0);
        tess.setColorOpaque_F(color[0] / 0.75F, color[1] / 0.75F, color[2] / 0.75F);
        tess.addVertex(1.0, 13.0, 0.0);
        tess.addVertex(1.0, 14.0, 0.0);
        tess.addVertex(1.0 + dataProgress / 10.0F * 14.0F, 14.0, 0.0);
        tess.addVertex(1.0 + dataProgress / 10.0F * 14.0F, 13.0, 0.0);
        tess.draw();

        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }
}
