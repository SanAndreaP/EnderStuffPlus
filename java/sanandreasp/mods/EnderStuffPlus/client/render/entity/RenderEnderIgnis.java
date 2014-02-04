// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package sanandreasp.mods.EnderStuffPlus.client.render.entity;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderIgnis;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

public class RenderEnderIgnis extends RenderLiving implements Textures {

	private ModelEnderIgnis endermanModel;
	private Random rnd;

	public RenderEnderIgnis(ModelEnderIgnis model, float f) {
		super(model, f);
		this.rnd = new Random();
		this.endermanModel = (ModelEnderIgnis) super.mainModel;
		this.setRenderPassModel(this.endermanModel);
	}

	public void renderEnderman(EntityEnderIgnis entityenderman, double d,
			double d1, double d2, float f, float f1) {
		this.endermanModel.isAttacking = entityenderman.isScreaming();
		if( entityenderman.isScreaming() ) {
			double d3 = 0.02D;
			d += this.rnd.nextGaussian() * d3;
			d2 += this.rnd.nextGaussian() * d3;
		}
		super.doRenderLiving(entityenderman, d, d1, d2, f, f1);
	}

	protected int renderEyes(EntityEnderIgnis entityenderman, int i,
			float f) {
		if( i != 0 ) {
			GL11.glDepthMask(true);
			return -1;
		} else {
			this.bindTexture(ENDERIGNIS_GLOW_TEXTURE);
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

	@Override
	protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f) {
		return this.renderEyes((EntityEnderIgnis) entityliving, i, f);
	}

	protected void renderEquipped(EntityEnderIgnis entityliving, float f) {
		super.renderEquippedItems(entityliving, f);
		ItemStack itemstack = entityliving.getHeldItem();
		if( itemstack != null ) {
			GL11.glPushMatrix();
			this.endermanModel.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

			float f4 = 0.575F;
			GL11.glTranslatef(0.05F, 0.5875F, -0.08F);
			GL11.glScalef(f4, f4, f4);
			GL11.glRotatef(130F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-20F, 0.0F, 0.0F, 1.0F);

			int var5 = entityliving.getBrightnessForRender(f);
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
					var6 / 1.0F, var7 / 1.0F);
			this.renderManager.itemRenderer.renderItem(entityliving, itemstack, 0);
			if( itemstack.itemID == Item.potion.itemID ) {
				this.renderManager.itemRenderer.renderItem(entityliving, itemstack,
						1);
			}
			GL11.glPopMatrix();
		}
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase entityliving, float f) {
		this.renderEquipped((EntityEnderIgnis) entityliving, f);
	}

	@Override
	public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
		this.renderEnderman((EntityEnderIgnis) entityliving, d, d1, d2, f, f1);
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {
		this.renderEnderman((EntityEnderIgnis) entity, d, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ENDERIGNIS_TEXTURE;
	}
}
