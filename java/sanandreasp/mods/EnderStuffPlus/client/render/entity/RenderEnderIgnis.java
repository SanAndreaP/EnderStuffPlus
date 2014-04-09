package sanandreasp.mods.EnderStuffPlus.client.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderIgnis;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEnderIgnis
    extends RenderLiving
{
    private ModelEnderIgnis ignisModel;
    private Random rnd;

    public RenderEnderIgnis() {
        super(new ModelEnderIgnis(), 0.7F);
        this.rnd = new Random();
        this.ignisModel = (ModelEnderIgnis) super.mainModel;
        this.setRenderPassModel(this.ignisModel);
    }

    private void applyStats(EntityEnderIgnis ignis) {
        this.ignisModel.setAttacking(ignis.isScreaming());
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partTicks) {
        EntityEnderIgnis ignis = (EntityEnderIgnis) entity;

        if( ignis.isScreaming() ) {
            double multi = 0.02D;

            x += this.rnd.nextGaussian() * multi;
            z += this.rnd.nextGaussian() * multi;
        }

        this.applyStats(ignis);
        this.doRenderLiving(ignis, x, y, z, yaw, partTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return Textures.ENDERIGNIS_TEXTURE.getResource();
    }

    private void renderEquipped(EntityEnderIgnis ignis, float partTicks) {
        super.renderEquippedItems(ignis, partTicks);

        ItemStack heldStack = ignis.getHeldItem();

        if( heldStack != null ) {
            float scale = 0.575F;

            GL11.glPushMatrix();

            this.ignisModel.rightArmPostRender();

            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
            GL11.glTranslatef(0.05F, 0.5875F, -0.08F);
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(130F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-20F, 0.0F, 0.0F, 1.0F);

            int bright = ignis.getBrightnessForRender(partTicks);
            int brightX = bright % 65536;
            int brightY = bright / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);

            this.renderManager.itemRenderer.renderItem(ignis, heldStack, 0);

            if( heldStack.getItem() == Item.potion ) {
                this.renderManager.itemRenderer.renderItem(ignis, heldStack, 1);
            }

            GL11.glPopMatrix();
        }
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase livingBase, float partTicks) {
        this.renderEquipped((EntityEnderIgnis) livingBase, partTicks);
    }

    private int renderEyes(EntityEnderIgnis ignis, int pass, float partTicks) {
        if( pass == 0 ) {
            this.bindTexture(Textures.ENDERIGNIS_GLOW_TEXTURE.getResource());

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
        return this.renderEyes((EntityEnderIgnis) livingBase, pass, partTicks);
    }
}
