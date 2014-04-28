package de.sanandrew.mods.enderstuffplus.client.render;

import org.lwjgl.opengl.GL11;

import de.sanandrew.core.manpack.util.javatuples.Pair;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.client.event.IconRegistry;
import de.sanandrew.mods.enderstuffplus.client.model.ModelWeatherAltar;
import de.sanandrew.mods.enderstuffplus.registry.Textures;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityWeatherAltar;

@SideOnly(Side.CLIENT)
public class RenderTileEntityWeatherAltar
    extends TileEntitySpecialRenderer
{
    ModelWeatherAltar modelBlock = new ModelWeatherAltar();

    private void renderIcon(IIcon ico, float red, float green, float blue) {
        Tessellator tess = Tessellator.instance;

        if( ico == null ) {
            ico = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture))
                                    .getTextureExtry("missingno");
        }

        float icoMinU = ico.getMinU();
        float icoMaxU = ico.getMaxU();
        float icoMinV = ico.getMinV();
        float icoMaxV = ico.getMaxV();

        GL11.glPushMatrix();
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.25F, -0.0421875F);
        GL11.glTranslatef(0f, 0f, 0.084375F);

        this.bindTexture(TextureMap.locationItemsTexture);

        GL11.glColor4f(red, green, blue, 1.0F);

        ItemRenderer.renderItemIn2D(tess, icoMaxU, icoMinV, icoMinU, icoMaxV, ico.getIconWidth(), ico.getIconHeight(), 0.0625F);

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partTicks) {
        TileEntityWeatherAltar te = ((TileEntityWeatherAltar) tile);
        Pair<float[], float[]> itmFloat = te.getItemFloat();

        this.bindTexture(Textures.WEATHERALTAR_TEXTURE.getResource());

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180F, 1F, 0F, 0F);
        GL11.glRotatef(te.blockMetadata * 90F, 0F, 1F, 0F);

        this.modelBlock.renderBlock();

        GL11.glRotatef(-180F, 1F, 0F, 0F);
        GL11.glScalef(0.25F, 0.25F, 0.25F);
        GL11.glTranslatef(0.0F, -2.5F, 0.0F);
        GL11.glTranslatef(1.25F, 0F, 0F);

        float sunFloat = 0.5F - (itmFloat.getValue0()[0] + (itmFloat.getValue1()[0] - itmFloat.getValue0()[0]) * partTicks) * 0.5F;
        float rainFloat = 0.5F - (itmFloat.getValue0()[1] + (itmFloat.getValue1()[1] - itmFloat.getValue0()[1]) * partTicks) * 0.5F;
        float stormFloat = 0.5F - (itmFloat.getValue0()[2] + (itmFloat.getValue1()[2] - itmFloat.getValue0()[2]) * partTicks) * 0.5F;

        RenderHelper.disableStandardItemLighting();

        GL11.glTranslatef(0F, -sunFloat, 0F);
        this.renderIcon(IconRegistry.sun, 1F - sunFloat, 1F - sunFloat, 1F - sunFloat);
        GL11.glTranslatef(0F, sunFloat, 0F);

        GL11.glTranslatef(-1.25F, -rainFloat, 0F);
        this.renderIcon(IconRegistry.rain, 1F - rainFloat, 1F - rainFloat, 1F - rainFloat);
        GL11.glTranslatef(0F, rainFloat, 0F);

        GL11.glTranslatef(-1.25F, -stormFloat, 0F);
        this.renderIcon(IconRegistry.thunder, 1F - stormFloat, 1F - stormFloat, 1F - stormFloat);
        GL11.glTranslatef(0F, stormFloat, 0F);

        RenderHelper.enableStandardItemLighting();

        GL11.glPopMatrix();
    }
}
