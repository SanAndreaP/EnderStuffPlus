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

import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderNemesis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNemesis;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

public class RenderEnderNemesis extends RenderLiving implements Textures {

	private ModelEnderNemesis endermanModel;
	private Random rnd;

	public RenderEnderNemesis(ModelEnderNemesis model, float f) {
		super(model, f);
		this.rnd = new Random();
		this.endermanModel = (ModelEnderNemesis) super.mainModel;
		this.setRenderPassModel(this.endermanModel);
	}

	public void renderEnderman(EntityEnderNemesis entityenderman, double d,
			double d1, double d2, float f, float f1) {
		this.endermanModel.isCarrying = entityenderman.getCarried() > 0;
		this.endermanModel.isAttacking = entityenderman.isScreaming();
		if( entityenderman.isScreaming() ) {
			double d3 = 0.02D;
			d += this.rnd.nextGaussian() * d3;
			d2 += this.rnd.nextGaussian() * d3;
		}
		super.doRenderLiving(entityenderman, d, d1, d2, f, f1);
	}

	protected void renderCarrying(EntityEnderNemesis entityenderman, float f) {
		super.renderEquippedItems(entityenderman, f);
		if( entityenderman.getCarried() > 0 ) {
			GL11.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
			GL11.glPushMatrix();
			float f1 = 0.5F;
			GL11.glTranslatef(0.0F, 0.3875F, -0.75F);
			f1 *= 1.0F;
			GL11.glRotatef(20F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(f1, -f1, f1);
			int i = entityenderman.getBrightnessForRender(f);
			int j = i % 0x10000;
			int k = i / 0x10000;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
					j / 1.0F, k / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.bindTexture(TextureMap.locationBlocksTexture);
			this.renderBlocks.renderBlockAsItem(
					Block.blocksList[entityenderman.getCarried()],
					entityenderman.getCarryingData(), 1.0F);
			GL11.glPopMatrix();
			GL11.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
		}
	}

	protected int renderEyes(EntityEnderNemesis entityenderman, int i,
			float f) {
		if( i != 0 ) {
			GL11.glDepthMask(true);
			return -1;
		} else {
			this.bindTexture(ENDERNEMESIS_GLOW_TEXTURE);
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

	@Override
	protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f) {
		return this.renderEyes((EntityEnderNemesis) entityliving, i, f);
	}

	protected void renderEquipped(EntityEnderNemesis entityliving, float f) {
		super.renderEquippedItems(entityliving, f);
		ItemStack itemstack = entityliving.getHeldItem();
		if( itemstack != null ) {
			GL11.glPushMatrix();
			this.endermanModel.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 1.0375F, 0.0625F);

			float f4 = 0.575F;
			GL11.glTranslatef(0.05F, 0.5875F, -0.08F);
			GL11.glScalef(f4, f4, f4);
			GL11.glRotatef(130F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-20F, 0.0F, 0.0F, 1.0F);

			int var5 = entityliving.getBrightnessForRender(f);
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);

            if( itemstack.getItem().requiresMultipleRenderPasses() )
            {
                for( int x = 1; x < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); x++ )
                {
                    this.renderManager.itemRenderer.renderItem(entityliving, itemstack, x);
                }
            }
			GL11.glPopMatrix();
		}
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase entityliving, float f) {
		this.renderCarrying((EntityEnderNemesis) entityliving, f);
		this.renderEquipped((EntityEnderNemesis) entityliving, f);
	}

	@Override
	public void doRenderLiving(EntityLiving entityliving, double d, double d1,
			double d2, float f, float f1) {
		this.renderEnderman((EntityEnderNemesis) entityliving, d, d1, d2, f, f1);
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {
		this.renderEnderman((EntityEnderNemesis) entity, d, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ENDERNEMESIS_TEXTURE;
	}
}
