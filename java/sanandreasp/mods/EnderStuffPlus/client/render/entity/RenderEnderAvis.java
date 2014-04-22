package sanandreasp.mods.EnderStuffPlus.client.render.entity;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.registry.raincoat.RegistryRaincoats.CoatBaseEntry;
import sanandreasp.mods.EnderStuffPlus.registry.raincoat.RegistryRaincoats.CoatColorEntry;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEnderAvis
    extends RenderLiving
{
    private ModelEnderAvis avisModel;
    private ModelEnderAvis coatModel;

    public RenderEnderAvis() {
        super(new ModelEnderAvis(), 0.5F);
        this.avisModel = (ModelEnderAvis) super.mainModel;
        this.coatModel = new ModelEnderAvis();
        this.setRenderPassModel(this.avisModel);
    }

    private void applyStats(EntityEnderAvis avis) {
        this.coatModel.setFlying(!avis.onGround);
        this.avisModel.setFlying(!avis.onGround);

        this.coatModel.setSitting(avis.isSitting());
        this.avisModel.setSitting(avis.isSitting());

        this.avisModel.setTicksFlying(avis.getTicksFlying());
        this.avisModel.setTamed(avis.isTamed());
        this.avisModel.setCollarColor(avis.getCollarColorArr());
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partTicks) {
        EntityEnderAvis avis = (EntityEnderAvis) entity;

        this.applyStats(avis);
        this.doRenderLiving(avis, x, y, z, yaw, partTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return Textures.ENDERAVIS_TEXTURE.getResource();
    }

    private float getWingRotation(EntityEnderAvis avis, float partTicks) {
        float wingRot = avis.getPrevWingRot() + (avis.getWingRot() - avis.getPrevWingRot()) * partTicks;
        float destPos = avis.getPrevDestPos() + (avis.getDestPos() - avis.getPrevDestPos()) * partTicks;
        return (MathHelper.sin(wingRot) + 1.0F) * destPos;
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase livingBase, float partTicks) {
        return this.getWingRotation((EntityEnderAvis) livingBase, partTicks);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase livingBase, float partTicks) {
        if( livingBase.isChild() ) {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
        }
        super.preRenderCallback(livingBase, partTicks);
    }

    private int renderPassSpecial(EntityEnderAvis avis, int pass, float partTicks) {
        if( pass == 0 ) {
            this.setRenderPassModel(this.avisModel);
            this.bindTexture(Textures.ENDERAVIS_GLOW_TEXTURE.getResource());

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
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);

            int bright = avis.getBrightnessForRender(partTicks);
            int brightX = bright % 65536;
            int brightY = bright / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            this.setRenderPassModel(this.coatModel);

            if( avis.hasCoat() ) {
                CoatColorEntry coatClr = avis.getCoatColor();
                if( coatClr != null ) {
                    coatClr.preRender();
                    this.bindTexture(coatClr.avisTexture);
                }

                return 1;
            }
        } else if( pass == 2 && avis.hasCoat() ) {
            CoatColorEntry coatClr = avis.getCoatColor();
            if( coatClr != null ) {
                coatClr.postRender();
            }

            CoatBaseEntry coatBse = avis.getCoatBase();
            if( coatBse != null ) {
                coatBse.preRender();
                this.bindTexture(coatBse.avisTexture);
            }

            return 1;
        } else if( pass == 3 && avis.isSaddled() ) {
            CoatBaseEntry coatBse = avis.getCoatBase();
            if( coatBse != null ) {
                coatBse.postRender();
            }

            this.bindTexture(Textures.ENDERAVIS_TEXTURE_SADDLE.getResource());

            return 1;
        }

        return 0;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase livingBase, int pass, float partTicks) {
        return this.renderPassSpecial((EntityEnderAvis) livingBase, pass, partTicks);
    }
}
