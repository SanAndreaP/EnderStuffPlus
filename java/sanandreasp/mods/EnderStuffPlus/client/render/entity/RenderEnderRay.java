package sanandreasp.mods.EnderStuffPlus.client.render.entity;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderRay;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderRay;

public class RenderEnderRay extends RenderLiving implements Textures {

	public RenderEnderRay() {
		super(new ModelEnderRay(), 1F);
		this.setRenderPassModel(super.mainModel);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
		float scale = 7F;
		GL11.glScalef(scale, scale, scale);
	}

	/**
	 * Sets the ray's glowing stuff
	 */
	protected int setGlowstuffBrightness(EntityEnderRay par1EntityRay,
			int par2, float par3) {
		if( par2 != 0 ) {
			GL11.glDepthMask(true);
			return -1;
		} else {
			if( par1EntityRay.hasSpecialTexture )
				this.bindTexture(ENDERRAY_GLOW_TEXTURE_SPEC);
			else
				this.bindTexture(ENDERRAY_GLOW_TEXTURE);
			float var4 = 1.0F;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			char var5 = 0x000F0;
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
					var6 / 1.0F, var7 / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
			GL11.glDepthMask(false);
			return 1;
		}
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3) {
		return this.setGlowstuffBrightness((EntityEnderRay) par1EntityLiving, par2, par3);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ((EntityEnderRay)entity).hasSpecialTexture ? ENDERRAY_TEXTURE_SPEC : ENDERRAY_TEXTURE;
	}
}
