package sanandreasp.mods.EnderStuffPlus.client.render.entity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import java.util.Random;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderMiss;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

public class RenderEnderMiss extends RenderLiving implements Textures
{
	private ModelEnderMiss endermissModel, coatModel;
	private Random rnd = new Random();
	
	public RenderEnderMiss() {
		super(new ModelEnderMiss(false), 0.5F);
		this.endermissModel = (ModelEnderMiss) super.mainModel;
		this.coatModel = new ModelEnderMiss(true);
		this.setRenderPassModel(this.endermissModel);
	}

	public void applyStats(EntityEnderMiss enderMiss) {
		this.coatModel.isCarrying = this.endermissModel.isCarrying = enderMiss.getCarried() > 0;
		this.endermissModel.isCaped = false;
		this.endermissModel.hasAvisFeather = !enderMiss.canGetFallDmg();
		this.coatModel.isRidden = this.endermissModel.isRidden = enderMiss.isRidden();
		this.coatModel.isSitting = this.endermissModel.isSitting = enderMiss.isSitting();
		this.endermissModel.bowClr = enderMiss.getBowColor();
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partTicks) {
		EntityEnderMiss miss = (EntityEnderMiss)entity;
		
		if( miss.needFood() ) {
			double multi = 0.02D;
			x += this.rnd.nextGaussian() * multi;
			z += this.rnd.nextGaussian() * multi;
		}
		
		this.applyStats(miss);
		this.doRenderLiving(miss, x, y, z, yaw, partTicks);
	}

	protected void renderCarrying(EntityEnderMiss enderMiss, float partTicks) {
		super.renderEquippedItems(enderMiss, partTicks);

		if( enderMiss.getCarried() > 0 ) {
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glPushMatrix();
			float var3 = 0.5F;
			GL11.glTranslatef(0.0F, 0.6875F, -0.75F);
			var3 *= 1.0F;
			GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(var3, -var3, var3);
			int var4 = enderMiss.getBrightnessForRender(partTicks);
			int var5 = var4 % 65536;
			int var6 = var4 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0F, var6 / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.bindTexture(TextureMap.locationBlocksTexture);
			this.renderBlocks.renderBlockAsItem(Block.blocksList[enderMiss.getCarried()], enderMiss.getCarryingData(), 1.0F);
			GL11.glPopMatrix();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
	}

	protected int renderPassSpecial(EntityEnderMiss miss, int pass, float partTicks) {
		if( pass == 0 ) {
			setRenderPassModel(this.endermissModel);
			this.bindTexture(miss.isSpecial() ? ENDERMISS_GLOW_TEXTURE_SPEC : ENDERMISS_GLOW_TEXTURE);
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
			int var5 = miss.getBrightnessForRender(partTicks);
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			if( miss.isImmuneToWater() ) {
		        if( (miss.getCoat() & 31) == 18 ) {
			        GL11.glColor4f(1F, 1F, 1F, 0.5F);
		        	GL11.glEnable(GL11.GL_BLEND);
		        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		        }
				this.bindTexture(ENDERMISS_CAPES_CLR[miss.getCoatColor()]);
				setRenderPassModel(this.coatModel);
				
				return 1;
			} else {
				return 0;
			}
		} else if( pass == 2 && miss.isImmuneToWater() ) {
	        if( (miss.getCoat() & 31) == 18 ) {
	        	GL11.glDisable(GL11.GL_BLEND);
	        }
			this.bindTexture(ENDERMISS_CAPES_STR[miss.getCoatBaseColor()]);
			return 1;
		}
		return 0;
	}
	
	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3) {
		return this.renderPassSpecial((EntityEnderMiss) par1EntityLiving, par2, par3);
	}

	protected void renderFlower(EntityEnderMiss par1EntityLiving, float par2) {
		ItemStack var3 = par1EntityLiving.getHeldItem();

		if( var3 != null ) {
			GL11.glPushMatrix();
			this.endermissModel.bipedRightArm.postRender(0.0625F);
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
			this.endermissModel.bipedLeftArm.postRender(0.0625F);
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
		this.renderFlower((EntityEnderMiss) par1EntityLiving, par2);
		this.renderCarrying((EntityEnderMiss) par1EntityLiving, par2);
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
