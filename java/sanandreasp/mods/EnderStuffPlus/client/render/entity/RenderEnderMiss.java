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
	private ModelEnderMiss missModel, coatModel;
	private Random rnd = new Random();
	
	public RenderEnderMiss() {
		super(new ModelEnderMiss(false), 0.5F);
		this.missModel = (ModelEnderMiss) super.mainModel;
		this.coatModel = new ModelEnderMiss(true);
		this.setRenderPassModel(this.missModel);
	}

	public void applyStats(EntityEnderMiss miss) {
		this.coatModel.isCarrying = this.missModel.isCarrying = miss.getCarried() > 0;
		this.missModel.isCaped = false;
		this.missModel.hasAvisFeather = !miss.canGetFallDmg();
		this.coatModel.isRidden = this.missModel.isRidden = miss.isRidden();
		this.coatModel.isSitting = this.missModel.isSitting = miss.isSitting();
		this.missModel.bowClr = miss.getBowColor();
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

	protected void renderCarrying(EntityEnderMiss miss, float partTicks) {
		super.renderEquippedItems(miss, partTicks);

		if( miss.getCarried() > 0 ) {
			float scale = 0.5F;
			
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 0.6875F, -0.75F);
			GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(scale, -scale, scale);
			
			int bright = miss.getBrightnessForRender(partTicks);
			int brightX = bright % 65536;
			int brightY = bright / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			this.bindTexture(TextureMap.locationBlocksTexture);
			this.renderBlocks.renderBlockAsItem(Block.blocksList[miss.getCarried()], miss.getCarryingData(), 1.0F);
			
			GL11.glPopMatrix();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
	}

	protected int renderPassSpecial(EntityEnderMiss miss, int pass, float partTicks) {
		if( pass == 0 ) {
			this.setRenderPassModel(this.missModel);
			this.bindTexture(miss.isSpecial() ? ENDERMISS_GLOW_TEXTURE_SPEC : ENDERMISS_GLOW_TEXTURE);
			
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
			
			int bright = miss.getBrightnessForRender(partTicks);
			int brightX = bright % 65536;
			int brightY = bright / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			if( miss.isImmuneToWater() ) {
		        if( (miss.getCoat() & 31) == 18 ) {
			        GL11.glColor4f(1F, 1F, 1F, 0.5F);
		        	GL11.glEnable(GL11.GL_BLEND);
		        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		        }
		        
				this.bindTexture(ENDERMISS_CAPES_CLR[miss.getCoatColor()]);
				this.setRenderPassModel(this.coatModel);
				
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
	protected int shouldRenderPass(EntityLivingBase livingBase, int pass, float partTicks) {
		return this.renderPassSpecial((EntityEnderMiss) livingBase, pass, partTicks);
	}

	protected void renderFlower(EntityEnderMiss miss, float partTicks) {
		ItemStack heldStack = miss.getHeldItem();

		if( heldStack != null ) {
			float scale = 0.375F;
			
			GL11.glPushMatrix();
			
			this.missModel.bipedRightArm.postRender(0.0625F);
			
			GL11.glTranslatef(-0.0625F, 1.4375F, 0.0625F);
			GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
			GL11.glScalef(scale, scale, scale);
			GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);

			float lastBrightX = OpenGlHelper.lastBrightnessX;
			float lastBrightY = OpenGlHelper.lastBrightnessY;
			
			int bright = miss.getBrightnessForRender(partTicks);
			int brightX = bright % 65536;
			int brightY = bright / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);
			
			this.renderManager.itemRenderer.renderItem(miss, heldStack, 0);

			if( heldStack.getItem().isFull3D() ) {
				this.renderManager.itemRenderer.renderItem(miss, heldStack, 1);
			}
			
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightX, lastBrightY);

			GL11.glPopMatrix();
		}
		
		if( !miss.canGetFallDmg() ) {
			float scale = 0.15F;
			
			GL11.glPushMatrix();
			
			this.missModel.bipedLeftArm.postRender(partTicks);
			
			GL11.glTranslatef(-0.0625F, 1.4375F, 0.0625F);
			GL11.glTranslatef(0.10F, 0.1875F, -0.1075F);
			GL11.glScalef(scale, scale, scale);
			GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-110.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);

			float lastBrightX = OpenGlHelper.lastBrightnessX;
			float lastBrightY = OpenGlHelper.lastBrightnessY;
			
			int bright = miss.getBrightnessForRender(partTicks);
			int brightX = bright % 65536;
			int brightY = bright / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);
			
			this.renderManager.itemRenderer.renderItem(miss, new ItemStack(ESPModRegistry.avisFeather), 0);

			if( heldStack.getItem().isFull3D() ) {
				this.renderManager.itemRenderer.renderItem(miss, new ItemStack(ESPModRegistry.avisFeather), 1);
			}
			
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightX, lastBrightY);

			GL11.glPopMatrix();
		}
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase livingBase, float partTicks) {
		this.renderFlower((EntityEnderMiss) livingBase, partTicks);
		this.renderCarrying((EntityEnderMiss) livingBase, partTicks);
	}

    @Override
    protected void renderLivingLabel(EntityLivingBase livingBase, String text, double x, double y, double z, int maxRange) {
    	if( livingBase instanceof EntityEnderMiss && ((EntityEnderMiss)livingBase).isSitting() ) {
    		super.renderLivingLabel(livingBase, text, x, y + 0.5F, z, maxRange);
    	} else {
    		super.renderLivingLabel(livingBase, text, x, y, z, maxRange);
    	}
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ((EntityEnderMiss)entity).isSpecial() ? ENDERMISS_TEXTURE_SPEC : ENDERMISS_TEXTURE;
	}
}
