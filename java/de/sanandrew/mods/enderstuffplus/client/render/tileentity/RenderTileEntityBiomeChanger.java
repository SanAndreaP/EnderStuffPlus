package de.sanandrew.mods.enderstuffplus.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.client.model.ModelBiomeChanger;
import de.sanandrew.mods.enderstuffplus.registry.Textures;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityBiomeChanger;

@SideOnly(Side.CLIENT)
public class RenderTileEntityBiomeChanger
    extends TileEntitySpecialRenderer
{
    ModelBiomeChanger modelBlock = new ModelBiomeChanger();

    private void renderRay(float angle, float height, Tessellator tess) {
        double beamXPos = Math.cos(angle / 180 * Math.PI) * 0.24F;
        double beamZPos = Math.sin(angle / 180 * Math.PI) * 0.24F;

//        for( float f = 1.0F; f > 0.1F; f -= 0.2F ) {
            tess.startDrawing(5);
            tess.setColorRGBA_F(0.2F, 0F, 1F, 0.5F);
            tess.addVertex(0, 0.85F, 0);
            tess.addVertex(beamXPos, 0.85F, beamZPos);
            tess.addVertex(0F, 0.85F + height, 0F);
            tess.draw();
//        }

        float factor = height / 16F;

        tess.startDrawing(5);
        tess.setColorRGBA_F(0.2F, 0F, 1F, 0.5F);
        tess.addVertex(-beamXPos - 0.001F, 0.85F, -beamZPos + 0.001F);
        tess.addVertex(0, 0.85F, 0);
        tess.addVertex(beamXPos * 16F * factor, 4F * factor + 0.85F, beamZPos * 16F * factor);
        tess.draw();

//        tess.startDrawing(5);
//        tess.setColorRGBA_F(0.2F, 0F, 1F, 0.5F);
//        tess.addVertex(0, 0.85F, 0);
//        tess.addVertex(beamXPos, 0.85F, beamZPos);
//        tess.addVertex(-beamXPos * 16F * factor, 4F * factor + 0.85F, -beamZPos * 16F * factor);
//        tess.draw();
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partTicks) {
        TileEntityBiomeChanger te = ((TileEntityBiomeChanger) tile);
        Tessellator tess = Tessellator.instance;

        te.setRenderCurrAngle(te.getRenderCurrAngle() + te.getRenderCurrHeight() / 16F);
        if( te.getRenderCurrHeight() < 16F && te.isActive() ) {
            te.setRenderCurrHeight(te.getRenderCurrHeight() + 0.05F);
        } else if( te.getRenderCurrHeight() > 0F && !te.isActive() ) {
            te.setRenderCurrHeight(te.getRenderCurrHeight() - 0.05F);
        }

        if( te.getRenderCurrAngle() > 360F ) {
            te.setRenderCurrAngle(0F);
        }
        if( te.getRenderCurrHeight() < 0F ) {
            te.setRenderCurrHeight(0F);
        }

        this.bindTexture(Textures.BIOMECHANGER_TEXTURE.getResource());

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);

        this.modelBlock.setBoxRotations(te.getRenderCurrAngle());

        this.modelBlock.renderBlock();

        GL11.glAlphaFunc(GL11.GL_GREATER, 0.2F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
//        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
//        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
//        GL11.glDisable(GL11.GL_LIGHTING);
//        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glMatrixMode(GL11.GL_PROJECTION);
//        OpenGlHelper.glBlendFunc(GL11.GL_ONE, GL11.GL_ZERO, GL11.GL_ONE, GL11.GL_ZERO);
//        GL11.glDepthMask(false);
//        GL11.glDepthFunc(GL11.GL_LEQUAL);
        
        int bright = 0xF0;
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);

//        GL11.glDisable(GL11.GL_CULL_FACE);
        this.renderRay(0F + te.getRenderCurrAngle(), te.getRenderCurrHeight(), tess);
        this.renderRay(180F + te.getRenderCurrAngle(), te.getRenderCurrHeight(), tess);
        this.renderRay(90F + te.getRenderCurrAngle(), te.getRenderCurrHeight(), tess);
        this.renderRay(270F + te.getRenderCurrAngle(), te.getRenderCurrHeight(), tess);

        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glDepthMask(true);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glPopMatrix();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.5F);
    }
}
