package sanandreasp.mods.EnderStuffPlus.client.render.entity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.Random;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderNivis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNivis;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

@SideOnly(Side.CLIENT)
public class RenderEnderNivis extends RenderLiving implements Textures
{
	private ModelEnderNivis nivisModel;
	private Random rnd;

	public RenderEnderNivis() {
		super(new ModelEnderNivis(), 0.7F);
		this.rnd = new Random();
		this.nivisModel = (ModelEnderNivis) super.mainModel;
		this.setRenderPassModel(this.nivisModel);
	}

	public void applyStats(EntityEnderNivis nivis, double x, double y, double z, float yaw, float partTicks) {
		this.nivisModel.setCarrying(nivis.getCarried() > 0);
		this.nivisModel.setAttacking(nivis.isScreaming());
	}

	protected void renderCarrying(EntityEnderNivis nivis, float partTicks) {
		super.renderEquippedItems(nivis, partTicks);

		if( nivis.getCarried() > 0 ) {
			float scale = 0.5F;
			
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 0.6875F, -0.75F);
			GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(scale, -scale, scale);
			
			int bright = nivis.getBrightnessForRender(partTicks);
			int brightX = bright % 65536;
			int brightY = bright / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			this.bindTexture(TextureMap.locationBlocksTexture);
			this.renderBlocks.renderBlockAsItem(Block.blocksList[nivis.getCarried()], nivis.getCarryingData(), 1.0F);
			
			GL11.glPopMatrix();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
	}

	protected int renderEyes(EntityEnderNivis nivis, int pass, float partTicks) {
		if( pass == 0 ) {
			this.bindTexture(ENDERNIVIS_GLOW_TEXTURE);
			
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
		return this.renderEyes((EntityEnderNivis) livingBase, pass, partTicks);
	}


	protected void renderEquipped(EntityEnderNivis nivis, float partTicks) {
		super.renderEquippedItems(nivis, partTicks);
		
		ItemStack heldStack = nivis.getHeldItem();
		
		if( heldStack != null ) {
			float scale = 0.575F;
			
			GL11.glPushMatrix();
			
			this.nivisModel.rightArmPostRender();
			
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			GL11.glTranslatef(0.05F, 0.5875F, -0.08F);
			GL11.glScalef(scale, scale, scale);
			GL11.glRotatef(130F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-20F, 0.0F, 0.0F, 1.0F);

			int bright = nivis.getBrightnessForRender(partTicks);
			int brightX = bright % 65536;
			int brightY = bright / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);
			
			this.renderManager.itemRenderer.renderItem(nivis, heldStack, 0);
			
			if( heldStack.getItem() == Item.potion ) {
				this.renderManager.itemRenderer.renderItem(nivis, heldStack, 1);
			}
			
			GL11.glPopMatrix();
		}
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase livingBase, float partTicks) {
		this.renderCarrying((EntityEnderNivis) livingBase, partTicks);
		this.renderEquipped((EntityEnderNivis) livingBase, partTicks);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partTicks) {
		EntityEnderNivis nivis = (EntityEnderNivis)entity;
		
		if( nivis.isScreaming() ) {
			double multi = 0.02D;
			
			x += this.rnd.nextGaussian() * multi;
			z += this.rnd.nextGaussian() * multi;
		}
		
		this.applyStats(nivis, x, y, z, yaw, partTicks);
		this.doRenderLiving(nivis, x, y, z, yaw, partTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ENDERNIVIS_TEXTURE;
	}
}
