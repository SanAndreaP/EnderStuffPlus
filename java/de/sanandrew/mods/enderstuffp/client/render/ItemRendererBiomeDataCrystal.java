/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.render;

import de.sanandrew.core.manpack.util.client.helpers.SAPClientUtils;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.item.ItemBlockBiomeDataCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
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
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        int biomeId = item.hasTagCompound() ? item.getTagCompound().getShort(ItemBlockBiomeDataCrystal.NBT_BIOME) : -1;
        int dataProgress = item.hasTagCompound() ? item.getTagCompound().getShort(ItemBlockBiomeDataCrystal.NBT_DATAPROG) : 0;
        double minCore = 3.0D / 16.0D;
        double maxCore = 13.0D / 16.0D;

        switch( type ){
            case ENTITY :
                GL11.glTranslatef(-0.5F, -0.508F, -0.5F);
                break;
            case INVENTORY :
                GL11.glTranslatef(0.0F, -0.1F, 0.0F);
                break;
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glDepthMask(false);

        if( dataProgress > 0 && biomeId >= 0 ) {
            double yMax = (10.0D - dataProgress) / 16.0F;
            double tessVMax = dataProgress / 10.0F * 0.5F;
            float[] color = SAPUtils.getRgbaFromColorInt(BiomeGenBase.getBiome(biomeId).color).getColorFloatArray();

            GL11.glColor3f(color[0], color[1], color[2]);
            Minecraft.getMinecraft().renderEngine.bindTexture(EnumTextures.BIOMECRYSTAL_CORE.getResource());
            SAPClientUtils.drawTexturedSquareYPos(minCore, minCore, maxCore, maxCore, maxCore - yMax, 0.25D, 0.0D, 0.5D, 0.5D);
            SAPClientUtils.drawTexturedSquareYNeg(minCore, minCore, maxCore, maxCore, minCore, 0.5D, 0.0D, 0.75D, 0.5D);
            SAPClientUtils.drawTexturedSquareXPos(minCore, minCore, maxCore - yMax, maxCore, maxCore, 0.25D, 1.0D - tessVMax, 0.5D, 1.0D);
            SAPClientUtils.drawTexturedSquareXNeg(minCore, minCore, maxCore - yMax, maxCore, minCore, 0.75D, 1.0D - tessVMax, 1.0D, 1.0D);
            SAPClientUtils.drawTexturedSquareZPos(minCore, minCore, maxCore, maxCore - yMax, maxCore, 0.0D, 1.0D - tessVMax, 0.25D, 1.0D);
            SAPClientUtils.drawTexturedSquareZNeg(minCore, minCore, maxCore, maxCore - yMax, minCore, 0.5D, 1.0D - tessVMax, 0.75D, 1.0D);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }

        minCore -= 0.0001D;
        maxCore += 0.0001D;
        Minecraft.getMinecraft().renderEngine.bindTexture(EnumTextures.GLASS_TEXTURE);
        SAPClientUtils.drawTexturedSquareYPos(minCore, minCore, maxCore, maxCore, maxCore, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareYNeg(minCore, minCore, maxCore, maxCore, minCore, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareXPos(minCore, minCore, maxCore, maxCore, maxCore, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareXNeg(minCore, minCore, maxCore, maxCore, minCore, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareZPos(minCore, minCore, maxCore, maxCore, maxCore, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareZNeg(minCore, minCore, maxCore, maxCore, minCore, 0.0D, 0.0D, 1.0D, 1.0D);

        Minecraft.getMinecraft().renderEngine.bindTexture(EnumTextures.BIOMECRYSTAL_BLOCKTEXTURE);
        SAPClientUtils.drawTexturedSquareYPos(0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareYNeg(0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareXPos(0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareXNeg(0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareZPos(0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 0.0D, 0.0D, 1.0D, 1.0D);
        SAPClientUtils.drawTexturedSquareZNeg(0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D);

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
