package sanandreasp.mods.EnderStuffPlus.client.render.entity;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderAvis;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;

public class RenderEnderAvis extends RenderLiving implements Textures {
	private ModelEnderAvis avisModel, coatModel;
	public RenderEnderAvis() {
		super(new ModelEnderAvis(false), 0.5F);
		this.avisModel = (ModelEnderAvis) super.mainModel;
		this.setRenderPassModel(this.avisModel);
		this.coatModel = new ModelEnderAvis(true);
	}

	@Override
	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
		EntityEnderAvis avis = (EntityEnderAvis)var1;
		
		this.doRenderAvis(avis, var2, var4, var6, var8, var9);
		this.doRenderLiving(avis, var2, var4, var6, var8, var9);
	}
	
	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
	    EntityEnderAvis avis = (EntityEnderAvis)par1EntityLiving;
		super.doRenderLiving(par1EntityLiving, par2, par4, par6, par8, par9);
		for( int cnt = 0; cnt < 3; cnt++ ) {
			if( !avis.isImmuneToWater() && !(cnt == 2 && avis.isSaddled()) ) continue;
			GL11.glPushMatrix();
		    GL11.glDisable(GL11.GL_CULL_FACE);
		    this.coatModel.onGround = this.mainModel.onGround;
		    this.coatModel.isRiding = this.mainModel.isRiding;
		    this.coatModel.isChild = this.mainModel.isChild;
		
		    try
		    {
		        float f2 = this.interpolateRotation(par1EntityLiving.prevRenderYawOffset, par1EntityLiving.renderYawOffset, par9);
		        float f3 = this.interpolateRotation(par1EntityLiving.prevRotationYawHead, par1EntityLiving.rotationYawHead, par9);
		        float f4 = par1EntityLiving.prevRotationPitch + (par1EntityLiving.rotationPitch - par1EntityLiving.prevRotationPitch) * par9;
		        this.renderLivingAt(par1EntityLiving, par2, par4, par6);
		        float f5 = this.handleRotationFloat(par1EntityLiving, par9);
		        this.rotateCorpse(par1EntityLiving, f5, f2, par9);
		        float f6 = 0.0625F;
		        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		        GL11.glScalef(-1.0F, -1.0F, 1.0F);
		        this.preRenderCallback(par1EntityLiving, par9);
		        GL11.glTranslatef(0.0F, -24.0F * f6 - 0.0078125F, 0.0F);
		        float f7 = par1EntityLiving.prevLimbSwingAmount + (par1EntityLiving.limbSwingAmount - par1EntityLiving.prevLimbSwingAmount) * par9;
		        float f8 = par1EntityLiving.limbSwing - par1EntityLiving.limbSwingAmount * (1.0F - par9);
		
		        if( par1EntityLiving.isChild() )
		        {
		            f8 *= 3.0F;
		        }
		
		        if( f7 > 1.0F )
		        {
		            f7 = 1.0F;
		        }
		
		        GL11.glEnable(GL11.GL_ALPHA_TEST);
		        if( (avis.getCoat() & 31) == 18 && cnt == 0 ) {
			        GL11.glColor4f(1F, 1F, 1F, 0.5F);
		        	GL11.glEnable(GL11.GL_BLEND);
		        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		        }
		        
		        this.bindTexture(cnt == 2 && avis.isSaddled() ? ENDERAVIS_TEXTURE_SADDLE : (cnt == 0 ? ENDERAVIS_CAPES_CLR[avis.getCoatColor()] : ENDERAVIS_CAPES_STR[avis.getCoatBaseColor()]));
		        this.coatModel.setLivingAnimations(par1EntityLiving, f8, f7, par9);
		
		        if( !par1EntityLiving.isInvisible() )
		        {
		            this.coatModel.render(par1EntityLiving, f8, f7, f5, f3 - f2, f4, f6);
		        }
		        else
		        {
		            this.coatModel.setRotationAngles(f8, f7, f5, f3 - f2, f4, f6, par1EntityLiving);
		        }
		        if( avis.getCoat() == 18 && cnt == 0 ) {
		        	GL11.glDisable(GL11.GL_BLEND);
		        }
		
		        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		    }
		    catch (Exception exception)
		    {
		        exception.printStackTrace();
		    }
		
		    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		    GL11.glEnable(GL11.GL_TEXTURE_2D);
		    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		    GL11.glEnable(GL11.GL_CULL_FACE);
		    GL11.glPopMatrix();
		}
	}

	public void doRenderAvis(EntityEnderAvis var1, double var2,
			double var4, double var6, float var8, float var9) {
		this.coatModel.isFlying = this.avisModel.isFlying = !var1.onGround;
		this.coatModel.isSitting = this.avisModel.isSitting = var1.isSitting();
		this.avisModel.motionY = var1.motionY;
		this.avisModel.ticksFlying = var1.ticksFlying;
		this.avisModel.isTamed = var1.isTamed();
		this.avisModel.collarClr = var1.getCollarColor(var1.getColor());
	}

	@Override
	protected void preRenderCallback(EntityLivingBase var1, float par2) {
		if( var1.isChild()) GL11.glScalef(0.5F, 0.5F, 0.5F );
		super.preRenderCallback(var1, par2);
	}

	protected float getWingRotation(EntityEnderAvis par1EntityAvis,
			float par2) {
		float var3 = par1EntityAvis.field_756_e
				+ (par1EntityAvis.field_752_b - par1EntityAvis.field_756_e)
				* par2;
		float var4 = par1EntityAvis.field_757_d
				+ (par1EntityAvis.destPos - par1EntityAvis.field_757_d) * par2;
		return (MathHelper.sin(var3) + 1.0F) * var4;
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	@Override
	protected float handleRotationFloat(EntityLivingBase var1, float par2) {
		return this.getWingRotation(
				(EntityEnderAvis) var1, par2);
	}

	/**
	 * Render the endermans eyes
	 */
	protected int renderEyes(EntityEnderAvis par1EntityEnderman,
			int par2, float par3) {
		if( par2 != 0 ) {
			GL11.glDepthMask(true);
			int var5 = par1EntityEnderman.getBrightnessForRender(0x0000F0);
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
					var6 / 1.0F, var7 / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			return -1;
		} else {
			this.bindTexture(ENDERAVIS_GLOW_TEXTURE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			char var5 = 0x000F0;
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
					var6 / 1.0F, var7 / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDepthMask(false);
			return 1;
		}
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3) {
		return this.renderEyes((EntityEnderAvis) par1EntityLiving, par2, par3);
	}

    private float interpolateRotation(float par1, float par2, float par3)
    {
        float f3;

        for( f3 = par2 - par1; f3 < -180.0F; f3 += 360.0F )
        {
            ;
        }

        while (f3 >= 180.0F)
        {
            f3 -= 360.0F;
        }

        return par1 + par3 * f3;
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ENDERAVIS_TEXTURE;
	}
}
