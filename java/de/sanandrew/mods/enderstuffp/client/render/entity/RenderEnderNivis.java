package de.sanandrew.mods.enderstuffp.client.render.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.client.model.ModelEnderNivis;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderNivis;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class RenderEnderNivis
    extends RenderLiving
{
    private ModelEnderNivis nivisModel;
    private Random rnd;

    public RenderEnderNivis() {
        super(new ModelEnderNivis(), 0.7F);
        this.rnd = new Random();
        this.nivisModel = (ModelEnderNivis) super.mainModel;
        this.setRenderPassModel(this.nivisModel);
    }

    private void applyStats(EntityEnderNivis nivis, double x, double y, double z, float yaw, float partTicks) {
        this.nivisModel.setCarrying(nivis.func_146080_bZ().getMaterial() != Material.air);
        this.nivisModel.setAttacking(nivis.isScreaming());
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partTicks) {
        EntityEnderNivis nivis = (EntityEnderNivis) entity;

        if( nivis.isScreaming() ) {
            double multi = 0.02D;

            x += this.rnd.nextGaussian() * multi;
            z += this.rnd.nextGaussian() * multi;
        }

        this.applyStats(nivis, x, y, z, yaw, partTicks);
        super.doRender(nivis, x, y, z, yaw, partTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return EnumTextures.ENDERNIVIS_TEXTURE.getResource();
    }

    private void renderCarrying(EntityEnderNivis nivis, float partTicks) {
        super.renderEquippedItems(nivis, partTicks);

        if( nivis.func_146080_bZ().getMaterial() != Material.air ) {
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
            this.field_147909_c.renderBlockAsItem(nivis.func_146080_bZ(), nivis.getCarryingData(), 1.0F);

            GL11.glPopMatrix();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
    }

    private void renderEquipped(EntityEnderNivis nivis, float partTicks) {
        super.renderEquippedItems(nivis, partTicks);

        ItemStack heldStack = nivis.getHeldItem();

        if( heldStack != null ) {
            float scale = 0.575F;

            GL11.glPushMatrix();

            this.nivisModel.rightArmPostRender();

            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
            GL11.glTranslatef(0.05F, 0.5875F, -0.08F);
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(130.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);

            int bright = nivis.getBrightnessForRender(partTicks);
            int brightX = bright % 65536;
            int brightY = bright / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);

            this.renderManager.itemRenderer.renderItem(nivis, heldStack, 0);

            if( heldStack.getItem() == Items.potionitem ) {
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

    private int renderEyes(EntityEnderNivis nivis, int pass, float partTicks) {
        if( pass == 0 ) {
            this.bindTexture(EnumTextures.ENDERNIVIS_GLOW_TEXTURE.getResource());

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
        }

        return 0;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase livingBase, int pass, float partTicks) {
        return this.renderEyes((EntityEnderNivis) livingBase, pass, partTicks);
    }
}
