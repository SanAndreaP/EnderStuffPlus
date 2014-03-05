package sanandreasp.mods.EnderStuffPlus.client.render.entity;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderRay;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderRay;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

@SideOnly(Side.CLIENT)
public class RenderEnderRay extends RenderLiving implements Textures
{
	public RenderEnderRay() {
		super(new ModelEnderRay(), 1F);
		this.setRenderPassModel(super.mainModel);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
		float scale = 7F;
		GL11.glScalef(scale, scale, scale);
	}

	protected int renderGlowStuff(EntityEnderRay ray, int pass, float partTicks) {
		if( pass == 0 ) {
			if( ray.hasSpecialTexture ) {
				this.bindTexture(ENDERRAY_GLOW_TEXTURE_SPEC);
			} else {
				this.bindTexture(ENDERRAY_GLOW_TEXTURE);
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

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ((EntityEnderRay)entity).hasSpecialTexture ? ENDERRAY_TEXTURE_SPEC : ENDERRAY_TEXTURE;
	}
}