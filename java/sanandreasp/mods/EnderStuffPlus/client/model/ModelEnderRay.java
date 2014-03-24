package sanandreasp.mods.EnderStuffPlus.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEnderRay
    extends ModelBase
{
    ModelRenderer body, rightarm, leftarm, rightleg, leftleg, body2, righttail, lefttail;

    public ModelEnderRay() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.body = new ModelRenderer(this, 0, 18);
        this.body.addBox(-6F, -1F, -6F, 12, 2, 12);
        this.body.setRotationPoint(0F, 24.0F, 0F);
        this.body.setTextureSize(64, 32);
        this.body.mirror = false;
        this.setRotation(this.body, 0F, 0.7853982F, 0F);
        this.rightarm = new ModelRenderer(this, 33, 0);
        this.rightarm.addBox(-4F, -1F, -2F, 4, 1, 11);
        this.rightarm.setRotationPoint(-3F, 24.5F, -5F);
        this.rightarm.setTextureSize(64, 32);
        this.rightarm.mirror = false;
        this.setRotation(this.rightarm, 0F, -0.7853982F, 0F);
        this.leftarm = new ModelRenderer(this, 33, 0);
        this.leftarm.addBox(0F, -1F, -2F, 4, 1, 11);
        this.leftarm.setRotationPoint(3F, 24.5F, -5F);
        this.leftarm.setTextureSize(64, 32);
        this.leftarm.mirror = true;
        this.setRotation(this.leftarm, 0F, 0.7853982F, 0F);
        this.rightleg = new ModelRenderer(this, 37, 12);
        this.rightleg.addBox(-2F, -1F, 0F, 2, 1, 11);
        this.rightleg.setRotationPoint(-1F, 24.5F, 5F);
        this.rightleg.setTextureSize(64, 32);
        this.setRotation(this.rightleg, 0F, 0.0523599F, 0F);
        this.rightleg.mirror = false;
        this.leftleg = new ModelRenderer(this, 33, 0);
        this.leftleg.addBox(0F, -1F, -2F, 4, 1, 11);
        this.leftleg.setRotationPoint(6F, 24.5F, 2F);
        this.leftleg.setTextureSize(64, 32);
        this.leftleg.mirror = true;
        this.setRotation(this.leftleg, 0F, -0.3316126F, 0F);
        this.body2 = new ModelRenderer(this, 0, 0);
        this.body2.addBox(-5F, -2F, -4F, 9, 2, 9);
        this.body2.setRotationPoint(0F, 24.0F, 0F);
        this.body2.setTextureSize(64, 32);
        this.body2.mirror = false;
        this.setRotation(this.body2, 0F, 0.7853982F, 0F);
        this.righttail = new ModelRenderer(this, 33, 0);
        this.righttail.addBox(-4F, -1F, -2F, 4, 1, 11);
        this.righttail.setRotationPoint(-6F, 24.5F, 2F);
        this.righttail.setTextureSize(64, 32);
        this.setRotation(this.righttail, 0F, 0.3316126F, 0F);
        this.righttail.mirror = false;
        this.lefttail = new ModelRenderer(this, 37, 12);
        this.lefttail.addBox(0F, -1F, 0F, 2, 1, 11);
        this.lefttail.setRotationPoint(1F, 24.5F, 5F);
        this.lefttail.setTextureSize(64, 32);
        this.lefttail.mirror = true;
        this.setRotation(this.lefttail, 0F, -0.0523599F, 0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw,
                       float rotPitch, float partTicks) {
        super.render(entity, limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);
        this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        this.body.render(partTicks);
        this.rightarm.render(partTicks);
        this.leftarm.render(partTicks);
        this.rightleg.render(partTicks);
        this.leftleg.render(partTicks);
        this.body2.render(partTicks);
        this.righttail.render(partTicks);
        this.lefttail.render(partTicks);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch,
                                  float partTicks, Entity entity) {
        this.lefttail.rotateAngleX = 0.1F * MathHelper.sin(rotFloat * 0.1F) + 0.0F;
        this.rightleg.rotateAngleX = -0.1F * MathHelper.sin(rotFloat * 0.1F + 0.7F) + 0.0F;
        this.righttail.rotateAngleY = 0.3F * MathHelper.sin(rotFloat * 0.1F) + 0.0F;
        this.leftleg.rotateAngleY = -0.3F * MathHelper.sin(rotFloat * 0.1F + 0.7F) + 0.0F;
    }
}
