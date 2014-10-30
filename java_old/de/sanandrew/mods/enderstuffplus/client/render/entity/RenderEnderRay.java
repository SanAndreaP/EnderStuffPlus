package de.sanandrew.mods.enderstuffplus.client.render.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.client.model.ModelEnderRay;
import de.sanandrew.mods.enderstuffplus.entity.living.monster.EntityEnderRay;
import de.sanandrew.mods.enderstuffplus.registry.Textures;

@SideOnly(Side.CLIENT)
public class RenderEnderRay
    extends RenderLiving
{
    public RenderEnderRay() {
        super(new ModelEnderRay(), 1F);
        this.setRenderPassModel(super.mainModel);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return ((EntityEnderRay) entity).hasSpecialTexture() ? Textures.ENDERRAY_TEXTURE_SPEC.getResource()
                                                             : Textures.ENDERRAY_TEXTURE.getResource();
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
        float scale = 7F;
        GL11.glScalef(scale, scale, scale);
    }

    private int renderGlowStuff(EntityEnderRay ray, int pass, float partTicks) {
        if( pass == 0 ) {
            if( ray.hasSpecialTexture() ) {
                this.bindTexture(Textures.ENDERRAY_GLOW_TEXTURE_SPEC.getResource());
            } else {
                this.bindTexture(Textures.ENDERRAY_GLOW_TEXTURE.getResource());
            }

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);

            char bright = 0xF0;
            int brightX = bright % 65536;
            int brightY = bright / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDepthMask(false);

            return 1;
        } else if( pass == 1 ) {
            GL11.glDepthMask(true);
        }

        return 0;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase livingBase, int pass, float partTicks) {
        return this.renderGlowStuff((EntityEnderRay) livingBase, pass, partTicks);
    }
}
