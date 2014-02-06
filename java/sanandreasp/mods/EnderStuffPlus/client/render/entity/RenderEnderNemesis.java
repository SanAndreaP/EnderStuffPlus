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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderNemesis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNemesis;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

@SideOnly(Side.CLIENT)
public class RenderEnderNemesis extends RenderLiving implements Textures
{
	private ModelEnderNemesis endermanModel;
	private Random rnd;

	public RenderEnderNemesis() {
		super(new ModelEnderNemesis(), 0.7F);
		this.rnd = new Random();
		this.endermanModel = (ModelEnderNemesis) super.mainModel;
		this.setRenderPassModel(this.endermanModel);
	}

	public void applyStats(EntityEnderNemesis nemesis, double x, double y, double z, float yaw, float partTicks) {
		this.endermanModel.isCarrying = nemesis.getCarried() > 0;
		this.endermanModel.isAttacking = nemesis.isScreaming();
	}

	protected void renderCarrying(EntityEnderNemesis nemesis, float partTicks) {
		super.renderEquippedItems(nemesis, partTicks);
		
		if( nemesis.getCarried() > 0 ) {
			float scale = 0.5F;
			
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 0.3875F, -0.75F);
			GL11.glRotatef(20F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(scale, -scale, scale);
			
			int bright = nemesis.getBrightnessForRender(partTicks);
			int brightX = bright % 0x10000;
			int brightY = bright / 0x10000;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			this.bindTexture(TextureMap.locationBlocksTexture);
			this.renderBlocks.renderBlockAsItem(Block.blocksList[nemesis.getCarried()], nemesis.getCarryingData(), 1.0F);
			
			GL11.glPopMatrix();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
	}

	protected int renderEyes(EntityEnderNemesis nemesis, int pass, float partTicks) {
		if( pass == 0 ) {
			this.bindTexture(ENDERNEMESIS_GLOW_TEXTURE);
			
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
		return this.renderEyes((EntityEnderNemesis) livingBase, pass, partTicks);
	}

	protected void renderEquipped(EntityEnderNemesis nemesis, float partTicks) {
		super.renderEquippedItems(nemesis, partTicks);
		
		ItemStack heldStack = nemesis.getHeldItem();
		
		if( heldStack != null ) {
			float scale = 0.575F;
			
			GL11.glPushMatrix();
			
			this.endermanModel.bipedRightArm.postRender(0.0625F);
			
			GL11.glTranslatef(-0.0625F, 1.0375F, 0.0625F);
			GL11.glTranslatef(0.05F, 0.5875F, -0.08F);
			GL11.glScalef(scale, scale, scale);
			GL11.glRotatef(130F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-20F, 0.0F, 0.0F, 1.0F);

			int bright = nemesis.getBrightnessForRender(partTicks);
			int brightX = bright % 65536;
			int brightY = bright / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);

            if( heldStack.getItem().requiresMultipleRenderPasses() ) {
            	int maxPasses = heldStack.getItem().getRenderPasses(heldStack.getItemDamage());
            	
                for( int x = 1; x < maxPasses; x++ ) {
                    this.renderManager.itemRenderer.renderItem(nemesis, heldStack, x);
                }
            }
            
			GL11.glPopMatrix();
		}
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase livingBase, float partTicks) {
		this.renderCarrying((EntityEnderNemesis) livingBase, partTicks);
		this.renderEquipped((EntityEnderNemesis) livingBase, partTicks);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partTicks) {
		EntityEnderNemesis nemesis = (EntityEnderNemesis)entity;
		
		if( nemesis.isScreaming() ) {
			double multi = 0.02D;
			
			x += this.rnd.nextGaussian() * multi;
			z += this.rnd.nextGaussian() * multi;
		}
		
		this.applyStats((EntityEnderNemesis) entity, x, y, z, yaw, partTicks);
		this.doRenderLiving(nemesis, x, y, z, yaw, partTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ENDERNEMESIS_TEXTURE;
	}
}
