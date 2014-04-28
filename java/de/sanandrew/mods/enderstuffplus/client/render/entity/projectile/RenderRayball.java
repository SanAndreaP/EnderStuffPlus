package de.sanandrew.mods.enderstuffplus.client.render.entity.projectile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.client.event.IconRegistry;

@SideOnly(Side.CLIENT)
public class RenderRayball
    extends Render
{
    private float renderSize;

    public RenderRayball(float size) {
        this.renderSize = size;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partTicks) {
        Tessellator tessellator = Tessellator.instance;
        IIcon icon = IconRegistry.enderball;
        double icoMinU = icon.getMinU();
        double icoMaxU = icon.getMaxU();
        double icoMinV = icon.getMinV();
        double icoMaxV = icon.getMaxV();

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(this.renderSize, this.renderSize, this.renderSize);

        this.bindTexture(TextureMap.locationItemsTexture);

        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(-0.5D, -0.25D, 0.0D, icoMinU, icoMaxV);
        tessellator.addVertexWithUV(0.5D, -0.25D, 0.0D, icoMaxU, icoMaxV);
        tessellator.addVertexWithUV(0.5D, 0.75D, 0.0D, icoMaxU, icoMinV);
        tessellator.addVertexWithUV(-0.5D, 0.75D, 0.0D, icoMinU, icoMinV);
        tessellator.draw();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
