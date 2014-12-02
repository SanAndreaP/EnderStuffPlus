package de.sanandrew.mods.enderstuffp.client.render.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.client.model.ModelEnderAvis;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.entity.living.AEntityEnderAvis;
import de.sanandrew.mods.enderstuffp.entity.living.EntityEnderAvisPet;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderAvisMother;
import de.sanandrew.mods.enderstuffp.util.raincoat.RegistryRaincoats.CoatBaseEntry;
import de.sanandrew.mods.enderstuffp.util.raincoat.RegistryRaincoats.CoatColorEntry;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

//import de.sanandrew.mods.enderstuffplus.entity.living.EntityEnderAvis;

@SideOnly(Side.CLIENT)
public class RenderEnderAvis
    extends RenderLiving
{
    private final ModelEnderAvis avisModel;
    private final ModelEnderAvis coatModel;
    private final ModelEnderAvis saddleModel;

    public RenderEnderAvis() {
        super(new ModelEnderAvis(false, false), 0.5F);
        this.avisModel = (ModelEnderAvis) super.mainModel;
        this.saddleModel = new ModelEnderAvis(true, false);
        this.coatModel = new ModelEnderAvis(false, true);
        this.setRenderPassModel(this.avisModel);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partTicks) {
        if( entity instanceof EntityEnderAvisPet ) {
            EntityEnderAvisPet avis = (EntityEnderAvisPet) entity;
            this.avisModel.collarColor = avis.getCollarColorArr();
            this.avisModel.isTamed = true;
            this.avisModel.isSitting = this.saddleModel.isSitting = this.coatModel.isSitting = avis.isSitting();
        } else {
            this.avisModel.isTamed = false;
            this.avisModel.isSitting = this.saddleModel.isSitting = this.coatModel.isSitting = false;
        }

        if( entity instanceof EntityEnderAvisMother ) {
            BossStatus.setBossStatus((EntityEnderAvisMother) entity, false);
        }

        super.doRender(entity, x, y, z, yaw, partTicks);
    }

    @Override
    protected void renderModel(EntityLivingBase livingBase, float limbSwing, float prevLimbSwing, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        if( livingBase instanceof EntityEnderAvisPet && ((EntityEnderAvisPet) livingBase).isSitting() ) {
            GL11.glTranslatef(0.0F, 0.82F, 0.0F);
        }

        super.renderModel(livingBase, limbSwing, prevLimbSwing, rotFloat, rotYaw, rotPitch, partTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return EnumTextures.ENDERAVIS_TEXTURE.getResource();
    }

    @Override
    protected void preRenderCallback(EntityLivingBase livingBase, float partTicks) {
        if( livingBase.isChild() ) {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
        }

        if( livingBase instanceof EntityEnderAvisMother ) {
            GL11.glScalef(1.5F, 1.5F, 1.5F);
        }

        super.preRenderCallback(livingBase, partTicks);
    }

    private int renderPassSpecial(AEntityEnderAvis avis, int pass, float partTicks) {
        if( pass == 0 ) {
            this.setRenderPassModel(this.avisModel);
            this.bindTexture(EnumTextures.ENDERAVIS_GLOW.getResource());

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
        } else if( pass == 3 && (avis.hasCoat() || (avis instanceof EntityEnderAvisPet && ((EntityEnderAvisPet) avis).isSaddled())) ) {
            if( avis.hasCoat() ) {
                CoatBaseEntry coatBse = avis.getCoatBase();
                if( coatBse != null ) {
                    coatBse.postRender();
                }
            }

            if( avis instanceof EntityEnderAvisPet && ((EntityEnderAvisPet) avis).isSaddled() ) {
                this.bindTexture(EnumTextures.ENDERAVIS_TEXTURE_SADDLE.getResource());
                this.setRenderPassModel(this.saddleModel);
            }

            return 1;
        }

        return 0;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase livingBase, int pass, float partTicks) {
        return this.renderPassSpecial((AEntityEnderAvis) livingBase, pass, partTicks);
    }
}
