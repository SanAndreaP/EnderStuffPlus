package de.sanandrew.mods.enderstuffp.client.render.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.client.model.tileentity.ModelBiomeChanger;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeChanger;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTileEntityBiomeChanger
    extends TileEntitySpecialRenderer
{
    ModelBiomeChanger modelBlock = new ModelBiomeChanger();

    private void renderRay(float angle, float height, Tessellator tess) {
        double beamXPos = Math.cos(1.0F / 180 * Math.PI) * 0.24F;
        double beamZPos = Math.sin(1.0F / 180 * Math.PI) * 0.24F;

        GL11.glPushMatrix();
        GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);

        GL11.glShadeModel(GL11.GL_SMOOTH);

        tess.startDrawing(5);
        tess.setColorRGBA_F(0.2F, 0.0F, 1.0F, 0.2F);
        tess.addVertex(-beamXPos, 0.85F, -beamZPos);
        tess.setColorRGBA_F(0.4F, 0.0F, 0.8F, 1.0F);
        tess.addVertex(0, 0.85F, 0);
        tess.setColorRGBA_F(0.2F, 0.0F, 1.0F, 0.2F);
        tess.addVertex(0.0F, 0.85F + height, 0.0F);
        tess.draw();
        tess.startDrawing(5);
        tess.setColorRGBA_F(0.4F, 0.0F, 0.8F, 1.0F);
        tess.addVertex(0, 0.85F, 0);
        tess.setColorRGBA_F(0.2F, 0.0F, 1.0F, 0.2F);
        tess.addVertex(beamXPos, 0.85F, beamZPos);
        tess.setColorRGBA_F(0.2F, 0.0F, 1.0F, 0.2F);
        tess.addVertex(0.0F, 0.85F + height, 0.0F);
        tess.draw();


        float factor = height / 16.0F;

        for( int spikeAngle = 0; spikeAngle < 4; spikeAngle++ ) {
            GL11.glPushMatrix();
            GL11.glRotatef(180.0F * (spikeAngle / 2.0F) + (45.0F * (spikeAngle % 2)), 0.0F, 1.0F, 0.0F);

            tess.startDrawing(5);
            tess.setColorRGBA_F(0.2F, 0.0F, 1.0F, 0.2F);
            tess.addVertex(-beamXPos + 0.05F, 0.85F, beamZPos - 0.05F);
            tess.setColorRGBA_F(0.4F, 0.0F, 0.8F, 0.8F);
            tess.addVertex(0, 0.85F, beamZPos - 0.05F);
            tess.setColorRGBA_F(0.2F, 0.0F, 1.0F, 0.2F);
            tess.addVertex(beamXPos * 16.0F * factor, 4.0F * factor + 0.85F, beamZPos * 16.0F * factor);
            tess.draw();

            tess.startDrawing(5);
            tess.setColorRGBA_F(0.4F, 0.0F, 0.8F, 0.8F);
            tess.addVertex(0, 0.85F, beamZPos - 0.05F);
            tess.setColorRGBA_F(0.2F, 0.0F, 1.0F, 0.2F);
            tess.addVertex(beamXPos - 0.05F, 0.85F, beamZPos - 0.05F);
            tess.setColorRGBA_F(0.2F, 0.0F, 1.0F, 0.2F);
            tess.addVertex(beamXPos * 16.0F * factor, 4.0F * factor + 0.85F, beamZPos * 16.0F * factor);
            tess.draw();

            GL11.glPopMatrix();
        }

        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partTicks) {
        TileEntityBiomeChanger te = ((TileEntityBiomeChanger) tile);
        Tessellator tess = Tessellator.instance;

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);

        if( te.getRenderPass() == 0 ) {
            this.bindTexture(EnumTextures.BIOMECHANGER.getResource());

            this.modelBlock.setBoxRotations(te.getRenderBeamAngle());

            this.modelBlock.renderBlock();
        } else if( te.getRenderPass() == 1 ) {
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.2F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_CULL_FACE);
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

            int bright = 0xF0;
            int brightX = bright % 65536;
            int brightY = bright / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
            GL11.glDepthMask(false);

            this.renderRay(0.0F - te.getRenderBeamAngle(), te.getRenderBeamHeight(), tess);
//            this.renderRay(180F + te.getRenderBeamAngle(), te.getRenderBeamHeight(), tess);
            this.renderRay(90.0F - te.getRenderBeamAngle(), te.getRenderBeamHeight(), tess);
//            this.renderRay(270F + te.getRenderBeamAngle(), te.getRenderBeamHeight(), tess);

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.5F);
        }

        GL11.glPopMatrix();
    }
}
