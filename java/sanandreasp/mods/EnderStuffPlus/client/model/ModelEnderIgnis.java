package sanandreasp.mods.EnderStuffPlus.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEnderIgnis
    extends ModelBase
{
    private ModelRenderer backHorn;
    private ModelRenderer bipedBody;
    private ModelRenderer bipedHead;
    private ModelRenderer bipedHeadwear;
    private ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightArm;
    private ModelRenderer headHorn1;
    private ModelRenderer headHorn2;
    public int heldItemRight;
    public boolean isAttacking;

    public ModelEnderIgnis() {
        this.isAttacking = false;
        this.heldItemRight = 0;

        this.textureWidth = 64;
        this.textureHeight = 32;

        this.bipedHeadwear = new ModelRenderer(this, 0, 16);
        this.bipedHeadwear.addBox(-4F, -8F, -4F, 8, 8, 8);
        this.bipedHeadwear.setRotationPoint(0F, -14F, 0F);
        this.bipedHeadwear.setTextureSize(64, 32);
        this.setRotation(this.bipedHeadwear, 0F, 0F, 0F);

        this.bipedBody = new ModelRenderer(this, 32, 0);
        this.bipedBody.addBox(-4F, 0F, -2F, 8, 28, 4);
        this.bipedBody.setRotationPoint(0F, -14F, 0F);
        this.bipedBody.setTextureSize(64, 32);
        this.setRotation(this.bipedBody, 0F, 0F, 0F);

        this.bipedRightArm = new ModelRenderer(this, 56, 0);
        this.bipedRightArm.addBox(-1F, -2F, -1F, 2, 20, 2);
        this.bipedRightArm.setRotationPoint(-5F, -12F, 0F);
        this.bipedRightArm.setTextureSize(64, 32);
        this.bipedRightArm.mirror = true;
        this.setRotation(this.bipedRightArm, 0F, 0F, 0.3490659F);

        this.bipedLeftArm = new ModelRenderer(this, 56, 0);
        this.bipedLeftArm.addBox(-1F, -2F, -1F, 2, 20, 2);
        this.bipedLeftArm.setRotationPoint(5F, -12F, 0F);
        this.bipedLeftArm.setTextureSize(64, 32);
        this.setRotation(this.bipedLeftArm, 0F, 0F, -0.3490659F);

        this.bipedLeftArm.mirror = false;
        this.headHorn2 = new ModelRenderer(this, 56, 16);
        this.headHorn2.addBox(-3F, 2F, 6F, 2, 8, 2);
        this.headHorn2.setRotationPoint(0F, -14F, 0F);
        this.headHorn2.setTextureSize(64, 32);
        this.headHorn2.mirror = true;
        this.setRotation(this.headHorn2, 2.181662F, 0F, 0F);

        this.backHorn = new ModelRenderer(this, 56, 16);
        this.backHorn.addBox(-1F, 0F, -6F, 2, 5, 2);
        this.backHorn.setRotationPoint(0F, -14F, 0F);
        this.backHorn.setTextureSize(64, 32);
        this.setRotation(this.backHorn, 1.919862F, 0F, 0F);

        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8);
        this.bipedHead.setRotationPoint(0F, -14F, 0F);
        this.bipedHead.setTextureSize(64, 32);
        this.setRotation(this.bipedHead, 0F, 0F, 0F);

        this.headHorn1 = new ModelRenderer(this, 56, 16);
        this.headHorn1.addBox(1F, 2F, 6F, 2, 8, 2);
        this.headHorn1.setRotationPoint(0F, -14F, 0F);
        this.headHorn1.setTextureSize(64, 32);
        this.setRotation(this.headHorn1, 2.181662F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw,
                       float rotPitch, float partTicks) {
        super.render(entity, limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);
        this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        this.bipedHeadwear.render(partTicks);
        this.bipedBody.render(partTicks);
        this.bipedRightArm.render(partTicks);
        this.bipedLeftArm.render(partTicks);
        this.headHorn2.render(partTicks);
        this.backHorn.render(partTicks);
        this.bipedHead.render(partTicks);
        this.headHorn1.render(partTicks);
    }

    private void setLivingAnimation(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw,
                                    float rotPitch, float partTicks) {

        this.bipedHead.rotateAngleY = rotYaw / 57.29578F;
        this.bipedHead.rotateAngleX = rotPitch / 57.29578F;

        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;

        this.headHorn1.rotateAngleY = this.bipedHead.rotateAngleY;
        this.headHorn2.rotateAngleY = this.bipedHead.rotateAngleY;

        this.headHorn1.rotateAngleX = 2.181662F + this.bipedHead.rotateAngleX;
        this.headHorn2.rotateAngleX = 2.181662F + this.bipedHead.rotateAngleX;

        this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 2.0F * limbSwingAmount
                                          * 0.5F;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.bipedRightArm.rotateAngleZ = 0.3490659F;
        this.bipedLeftArm.rotateAngleZ = -0.3490659F;

        if( this.isRiding ) {
            this.bipedRightArm.rotateAngleX += -0.6283185F;
            this.bipedLeftArm.rotateAngleX += -0.6283185F;
        }

        if( this.heldItemRight != 0 ) {
            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.3141593F * this.heldItemRight;
        }

        this.bipedRightArm.rotateAngleY = 0.0F;
        this.bipedLeftArm.rotateAngleY = 0.0F;

        if( this.onGround > -9990F ) {
            float onGroundVal = this.onGround;

            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(onGroundVal) * 3.141593F * 2.0F) * 0.2F;
            this.bipedRightArm.rotationPointZ = 0.3490659F + MathHelper.sin(this.bipedBody.rotateAngleY) * 5F;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5F;
            this.bipedLeftArm.rotationPointZ = -0.3490659F - MathHelper.sin(this.bipedBody.rotateAngleY) * 5F;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5F;
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;

            onGroundVal = 1.0F - this.onGround;
            onGroundVal *= onGroundVal;
            onGroundVal *= onGroundVal;
            onGroundVal = 1.0F - onGroundVal;

            float rightArmAngleX1 = MathHelper.sin(onGroundVal * 3.141593F);
            float rightArmAngleX2 = MathHelper.sin(this.onGround * 3.141593F) * -(this.bipedHead.rotateAngleX - 0.7F)
                                    * 0.75F;

            this.bipedRightArm.rotateAngleX -= rightArmAngleX1 * 1.2D + rightArmAngleX2;
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
            this.bipedRightArm.rotateAngleZ = 0.3490659F + MathHelper.sin(this.onGround * 3.141593F) * -0.4F;
        }

        this.bipedBody.rotateAngleX = 0.0F;
        this.bipedHead.rotationPointY = 0.0F;
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(rotFloat * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(rotFloat * 0.067F) * 0.05F;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch,
                                  float partTicks, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        this.setLivingAnimation(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);

        this.bipedHead.rotationPointZ = -0F;
        this.bipedHead.rotationPointY = -14F;
        this.headHorn1.rotationPointZ = this.headHorn2.rotationPointZ = -0F;
        this.headHorn1.rotationPointY = this.headHorn2.rotationPointY = -14F;
        this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
        this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
        this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;

        if( this.isAttacking ) {
            this.bipedHead.rotationPointY -= 5F;
            this.headHorn2.rotationPointY -= 5F;
            this.headHorn1.rotationPointY -= 5F;
        }
    }
}
