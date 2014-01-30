package sanandreasp.mods.EnderStuffPlus.client.render;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import sanandreasp.mods.EnderStuffPlus.client.model.ModelBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

@SideOnly(Side.CLIENT)
public class RenderTileEntityBiomeChanger extends TileEntitySpecialRenderer implements Textures {
	
	ModelBiomeChanger modelBlock = new ModelBiomeChanger();
	
	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
		TileEntityBiomeChanger te = ((TileEntityBiomeChanger)var1);
		
		te.renderCurrAngle += te.renderCurrHeight / 16F;
		if( te.renderCurrHeight < 16F && te.isActive() )
			te.renderCurrHeight +=  0.05F;
		else if( te.renderCurrHeight > 0F && !te.isActive() )
			te.renderCurrHeight -=  0.05F;
			
		if( te.renderCurrAngle > 360F ) te.renderCurrAngle = 0F;
		if( te.renderCurrHeight < 0F ) te.renderCurrHeight = 0F;
		
		float currAngle = ((TileEntityBiomeChanger)var1).renderCurrAngle;
		
		this.bindTexture(BIOMECHANGER_TEXTURE);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2 + 0.5F, (float)var4, (float)var6 + 0.5F);
        this.modelBlock.Shape1.rotateAngleY = (float) (-currAngle / 180F * Math.PI);
        this.modelBlock.Shape2.rotateAngleY = (float) (-currAngle / 180F * Math.PI);
        
        this.modelBlock.Shape1.rotationPointY = (float) (13D + Math.sin(te.renderCurrAngle / 180 * Math.PI) * 0.5D);
        this.modelBlock.Shape2.rotationPointY = (float) (13D - Math.sin(te.renderCurrAngle / 180 * Math.PI) * 0.5D);
        
        this.modelBlock.renderBlock();
        
        Tessellator var10 = Tessellator.instance;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
		char var5 = 0x000F0;
		int var9 = var5 % 65536;
		int var7 = var5 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
				var9 / 1.0F, var7 / 1.0F);
        renderRay(0F + currAngle, te.renderCurrHeight, var10);
        renderRay(180F + currAngle, te.renderCurrHeight, var10);
        renderRay(90F + currAngle, te.renderCurrHeight, var10);
        renderRay(270F + currAngle, te.renderCurrHeight, var10);
        
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
	}

	private void renderRay(float angle, float height, Tessellator tessellator) {
		double beamXPos = Math.cos(angle / 180 * Math.PI) * 0.24F;
		double beamZPos = Math.sin(angle / 180 * Math.PI) * 0.24F;
        
		for( float f = 1.0F; f > 0.1F; f -= 0.2F ) {
			tessellator.startDrawing(5);
	        tessellator.setColorRGBA_F(0.2F, 0F, 1F, 0.2F);
	        tessellator.addVertex(-beamXPos*f, 0.85F, -beamZPos*f);
	        tessellator.addVertex(beamXPos*f, 0.85F, beamZPos*f);
	        tessellator.addVertex(0F, 0.85F+height*f, 0F);
	        tessellator.draw();
		}
		
		float factor = height / 16F;

		tessellator.startDrawing(5);
        tessellator.setColorRGBA_F(0.2F, 0F, 1F, 0.2F);
        tessellator.addVertex(-beamXPos, 0.85F, -beamZPos);
        tessellator.addVertex(0, 0.85F, 0);
        tessellator.addVertex(beamXPos * 16F * factor, 4F * factor + 0.85F, beamZPos * 16F * factor);
        tessellator.draw();
        
        tessellator.startDrawing(5);
        tessellator.setColorRGBA_F(0.2F, 0F, 1F, 0.2F);
        tessellator.addVertex(0, 0.85F, 0);
        tessellator.addVertex(beamXPos, 0.85F, beamZPos);
        tessellator.addVertex(-beamXPos * 16F * factor, 4F * factor + 0.85F, -beamZPos * 16F * factor);
        tessellator.draw();
	}
}
