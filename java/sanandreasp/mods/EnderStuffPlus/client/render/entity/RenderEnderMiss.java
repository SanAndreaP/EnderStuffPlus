package sanandreasp.mods.EnderStuffPlus.client.render.entity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sanandreasp.mods.EnderStuffPlus.client.model.Model_EnderMiss;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

public class RenderEnderMiss extends RenderLiving implements Textures {
	/** The model of the enderman */
	private Model_EnderMiss endermanModel;
	private Model_EnderMiss coatModel;
	private Random rnd = new Random();
	
	public RenderEnderMiss() {
		super(new Model_EnderMiss(false), 0.5F);
		this.endermanModel = (Model_EnderMiss) super.mainModel;
		this.coatModel = new Model_EnderMiss(true);
		this.setRenderPassModel(this.endermanModel);
	}

	/**
	 * Renders the enderman
	 */
	public void renderEnderman(EntityEnderMiss par1EntityEnderman,
			double par2, double par4, double par6, float par8, float par9) {
		this.coatModel.isCarrying = this.endermanModel.isCarrying = par1EntityEnderman.getCarried() > 0;
		this.endermanModel.isCaped = false;
		this.endermanModel.hasAvisFeather = !par1EntityEnderman.canGetFallDmg();
		this.coatModel.isRidden = this.endermanModel.isRidden = par1EntityEnderman.isRidden();
		this.coatModel.isSitting = this.endermanModel.isSitting = par1EntityEnderman.isSitting();
		this.endermanModel.bowClr = par1EntityEnderman.getBowColor();

		if( par1EntityEnderman.needFood() ) {
			double var10 = 0.02D;
			par2 += this.rnd.nextGaussian() * var10;
			par6 += this.rnd.nextGaussian() * var10;
		}
	}

	/**
	 * Render the block an enderman is carrying
	 */
	protected void renderCarrying(EntityEnderMiss par1EntityEnderman, float par2) {
		super.renderEquippedItems(par1EntityEnderman, par2);

		if( par1EntityEnderman.getCarried() > 0 ) {
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glPushMatrix();
			float var3 = 0.5F;
			GL11.glTranslatef(0.0F, 0.6875F, -0.75F);
			var3 *= 1.0F;
			GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(var3, -var3, var3);
			int var4 = par1EntityEnderman.getBrightnessForRender(par2);
			int var5 = var4 % 65536;
			int var6 = var4 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0F, var6 / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.bindTexture(TextureMap.locationBlocksTexture);
			this.renderBlocks.renderBlockAsItem(
					Block.blocksList[par1EntityEnderman.getCarried()],
					par1EntityEnderman.getCarryingData(), 1.0F);
			GL11.glPopMatrix();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
	}

	/**
	 * Render the endermans eyes
	 */
	protected int renderEyes(EntityEnderMiss par1EntityEnderman, int par2, float par3) {
		if( par2 != 0 ) {
			GL11.glDepthMask(true);
			return -1;
		} else {
			if( par1EntityEnderman.isSpecial() )
				this.bindTexture(ENDERMISS_GLOW_TEXTURE_SPEC);
			else
				this.bindTexture(ENDERMISS_GLOW_TEXTURE);
			float var4 = 1.0F;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			char var5 = 0x000F0;
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
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
//		return super.shouldRenderPass(par1EntityLiving, par2, par3);
		return this.renderEyes((EntityEnderMiss) par1EntityLiving, par2, par3);
	}

	protected void renderEquippedItems2(EntityEnderMiss par1EntityLiving, float par2) {
		ItemStack var3 = par1EntityLiving.getHeldItem();

		if( var3 != null ) {
			GL11.glPushMatrix();
			this.endermanModel.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 1.4375F, 0.0625F);
			float var4;
			var4 = 0.375F;
			GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
			GL11.glScalef(var4, var4, var4);
			GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);

			float lastBrightX = OpenGlHelper.lastBrightnessX, lastBrightY = OpenGlHelper.lastBrightnessY;
			
			int var5 = par1EntityLiving.getBrightnessForRender(par2);
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
			this.renderManager.itemRenderer.renderItem(par1EntityLiving, var3, 0);

			if( var3.getItem().isFull3D() ) {
				this.renderManager.itemRenderer.renderItem(par1EntityLiving, var3, 1);
			}
			
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightX, lastBrightY);

			GL11.glPopMatrix();
		}
		
		if( !par1EntityLiving.canGetFallDmg() ) {
			GL11.glPushMatrix();
			this.endermanModel.bipedLeftArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 1.4375F, 0.0625F);
			float var4;
			var4 = 0.15F;
			GL11.glTranslatef(0.10F, 0.1875F, -0.1075F);
			GL11.glScalef(var4, var4, var4);
			GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-110.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);

			float lastBrightX = OpenGlHelper.lastBrightnessX, lastBrightY = OpenGlHelper.lastBrightnessY;
			
			int var5 = par1EntityLiving.getBrightnessForRender(par2);
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
			this.renderManager.itemRenderer.renderItem(par1EntityLiving, new ItemStack(ESPModRegistry.avisFeather), 0);

			if( var3.getItem().isFull3D() ) {
				this.renderManager.itemRenderer.renderItem(par1EntityLiving, new ItemStack(ESPModRegistry.avisFeather), 1);
			}
			
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightX, lastBrightY);

			GL11.glPopMatrix();
		}
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase par1EntityLiving, float par2) {
		this.renderEquippedItems2((EntityEnderMiss) par1EntityLiving, par2);
		this.renderCarrying((EntityEnderMiss) par1EntityLiving, par2);
	}

	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
		EntityEnderMiss miss = ((EntityEnderMiss)par1EntityLiving);
		
		this.renderEnderman(miss, par2, par4, par6, par8, par9);
		
		if( !miss.isImmuneToWater() ) {
			super.doRenderLiving(miss, par2, par4, par6, par8, par9);
		} else {
			float lastBrightX = OpenGlHelper.lastBrightnessX, lastBrightY = OpenGlHelper.lastBrightnessY;
			
			super.doRenderLiving(miss, par2, par4, par6, par8, par9);
			
			for( int cnt = 0; cnt < 3; cnt++ ) {
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
			        if( (miss.getCoat() & 31) == 18 && cnt == 0 ) {
				        GL11.glColor4f(1F, 1F, 1F, 0.5F);
			        	GL11.glEnable(GL11.GL_BLEND);
			        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			        }
			        
			        this.bindTexture(cnt == 0 ? ENDERMISS_CAPES_CLR[miss.getCoatColor()] : ENDERMISS_CAPES_STR[miss.getCoatBaseColor()]);
			        this.coatModel.setLivingAnimations(par1EntityLiving, f8, f7, par9);
			
			        if( !par1EntityLiving.isInvisible() )
			        {
						int var5 = par1EntityLiving.getBrightnessForRender(par8);
						int var6 = var5 % 65536;
						int var7 = var5 / 65536;
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
	
						this.coatModel.render(par1EntityLiving, f8, f7, f5, f3 - f2, f4, f6);
	
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightX, lastBrightY);
			        }
			        else
			        {
			            this.coatModel.setRotationAngles(f8, f7, f5, f3 - f2, f4, f6, par1EntityLiving);
			        }
			        if( miss.getCoat() == 18 && cnt == 0 ) {
			        	GL11.glDisable(GL11.GL_BLEND);
			        }
			
			        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			    }
			    catch (Exception exception)
			    {
			        exception.printStackTrace();
			    }
			
			    GL11.glPopMatrix();
			}
		}
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
    protected void renderLivingLabel(EntityLivingBase par1EntityLivingBase, String par2Str, double par3, double par5, double par7, int par9) {
    	if( par1EntityLivingBase instanceof EntityEnderMiss && ((EntityEnderMiss)par1EntityLivingBase).isSitting() ) {
    		GL11.glPushMatrix();
    		GL11.glTranslated(0F, 0.5F, 0F);
    		super.renderLivingLabel(par1EntityLivingBase, par2Str, par3, par5, par7, par9);
    		GL11.glPopMatrix();
    	} else {
    		super.renderLivingLabel(par1EntityLivingBase, par2Str, par3, par5, par7, par9);
    	}
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ((EntityEnderMiss)entity).isSpecial() ? ENDERMISS_TEXTURE_SPEC : ENDERMISS_TEXTURE;
	}
}
