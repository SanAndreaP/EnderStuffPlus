package sanandreasp.mods.EnderStuffPlus.client.render.entity;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.Item;
import net.minecraft.src.*;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sanandreasp.mods.EnderStuffPlus.client.registry.IconRegistry;
import sanandreasp.mods.EnderStuffPlus.entity.EntityRayball;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

public class RenderRayball extends Render {

    private float size;

    public RenderRayball(float sz)
    {
        this.size = sz;
    }

    public void doRenderRayball(EntityRayball par1EntityFireball, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float f2 = this.size;
        GL11.glScalef(f2, f2, f2);
        this.bindTexture(TextureMap.locationItemsTexture);
        Icon icon = IconRegistry.enderball;
        Tessellator tessellator = Tessellator.instance;
        float f3 = icon.getMinU();
        float f4 = icon.getMaxU();
        float f5 = icon.getMinV();
        float f6 = icon.getMaxV();
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV((double)(0.0F - f8), (double)(0.0F - f9), 0.0D, (double)f3, (double)f6);
        tessellator.addVertexWithUV((double)(f7 - f8), (double)(0.0F - f9), 0.0D, (double)f4, (double)f6);
        tessellator.addVertexWithUV((double)(f7 - f8), (double)(1.0F - f9), 0.0D, (double)f4, (double)f5);
        tessellator.addVertexWithUV((double)(0.0F - f8), (double)(1.0F - f9), 0.0D, (double)f3, (double)f5);
        tessellator.draw();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRenderRayball((EntityRayball)par1Entity, par2, par4, par6, par8, par9);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}
}
