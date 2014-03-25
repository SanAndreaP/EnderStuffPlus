package sanandreasp.mods.EnderStuffPlus.client.render.entity;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEnderAvis extends RenderLiving implements Textures
{
	private ModelEnderAvis avisModel, coatModel;

	public RenderEnderAvis() {
		super(new ModelEnderAvis(), 0.5F);
		this.avisModel = (ModelEnderAvis) super.mainModel;
		this.coatModel = new ModelEnderAvis();
		this.setRenderPassModel(this.avisModel);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partTicks) {
		EntityEnderAvis avis = (EntityEnderAvis)entity;

		this.applyStatsToModel(avis);
		this.doRenderLiving(avis, x, y, z, yaw, partTicks);
	}

	public void applyStatsToModel(EntityEnderAvis avis) {
		this.coatModel.setFlying(!avis.onGround);
		this.avisModel.setFlying(!avis.onGround);
		
		this.coatModel.setSitting(avis.isSitting());
		this.avisModel.setSitting(avis.isSitting());
		
		this.avisModel.setTicksFlying(avis.ticksFlying);
		this.avisModel.setTamed(avis.isTamed());
		this.avisModel.setCollarColor(avis.getCollarColor(avis.getColor()));
	}

	@Override
	protected void preRenderCallback(EntityLivingBase livingBase, float partTicks) {
		if( livingBase.isChild()) {
			GL11.glScalef(0.5F, 0.5F, 0.5F );
		}
		super.preRenderCallback(livingBase, partTicks);
	}

	protected float getWingRotation(EntityEnderAvis avis, float partTicks) {
		float var3 = avis.field_756_e + (avis.field_752_b - avis.field_756_e) * partTicks;
		float var4 = avis.field_757_d + (avis.destPos - avis.field_757_d) * partTicks;
		return (MathHelper.sin(var3) + 1.0F) * var4;
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase livingBase, float partTicks) {
		return this.getWingRotation((EntityEnderAvis) livingBase, partTicks);
	}

	protected int renderPassSpecial(EntityEnderAvis avis, int pass, float partTicks) {
		if( pass == 0 ) {
			this.setRenderPassModel(this.avisModel);
			this.bindTexture(ENDERAVIS_GLOW_TEXTURE);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);

			int bright = 0xF0;
			int brightX = bright % 65536;
			int brightY = bright / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDepthMask(false);

			return 1;
		} else if( pass == 1 ) {
			GL11.glDepthMask(true);

			int bright = avis.getBrightnessForRender(partTicks);
			int brightX = bright % 65536;
			int brightY = bright / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			this.setRenderPassModel(this.coatModel);

			if( avis.isImmuneToWater() ) {
		        if( (avis.getCoat() & 31) == 18 ) {
			        GL11.glColor4f(1F, 1F, 1F, 0.5F);
		        	GL11.glEnable(GL11.GL_BLEND);
		        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		        }

				this.bindTexture(ENDERAVIS_CAPES_CLR[avis.getCoatColor()]);

				return 1;
			}
		} else if( pass == 2 && avis.isImmuneToWater() ) {
	        if( (avis.getCoat() & 31) == 18 ) {
	        	GL11.glDisable(GL11.GL_BLEND);
	        }

			this.bindTexture(ENDERAVIS_CAPES_STR[avis.getCoatBaseColor()]);

			return 1;
		} else if( pass == 3 && avis.isSaddled() ) {
			this.bindTexture(ENDERAVIS_TEXTURE_SADDLE);

			return 1;
		}

		return 0;
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase livingBase, int pass, float partTicks) {
		return this.renderPassSpecial((EntityEnderAvis) livingBase, pass, partTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ENDERAVIS_TEXTURE;
	}
}
