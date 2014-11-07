package de.sanandrew.mods.enderstuffp.client.model;

import de.sanandrew.core.manpack.util.client.SAPClientUtils;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEnderNivis
    extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer jar;
    private ModelRenderer body;
    private ModelRenderer rightArmLow;
    private ModelRenderer leftArmLow;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;
    private ModelRenderer rightArmHigh;
    private ModelRenderer leftArmHigh;
    private int heldItemRight;
    private boolean isAttacking;
    private boolean isCarrying;

    public ModelEnderNivis() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.isCarrying = false;
        this.isAttacking = false;
        this.heldItemRight = 0;

        this.head = SAPClientUtils.createNewBox(this, 0, 0, false, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.jar = SAPClientUtils.createNewBox(this, 0, 16, false, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.body = SAPClientUtils.createNewBox(this, 32, 16, false, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.rightArmLow = SAPClientUtils.createNewBox(this, 56, 0, true, -1.0F, -2.0F, -1.0F, 2, 20, 2, -4.0F, -7.0F, 0.0F, 0.0F, 0.0F, 0.2230717F);
        this.rightArmHigh = SAPClientUtils.createNewBox(this, 56, 0, true, -1.0F, -2.0F, -1.0F, 2, 20, 2, -5.0F, -12.0F, 0.0F, 0.0F, 0.0F, 0.3490659F);
        this.leftArmLow = SAPClientUtils.createNewBox(this, 56, 0, false, -1.0F, -2.0F, -1.0F, 2, 20, 2, 4.0F, -7.0F, 0.0F, 0.0F, 0.0F, -0.2230717F);
        this.leftArmHigh = SAPClientUtils.createNewBox(this, 56, 0, false, -1.0F, -2.0F, -1.0F, 2, 20, 2, 5.0F, -12.0F, 0.0F, 0.0F, 0.0F, -0.3490659F);
        this.rightLeg = SAPClientUtils.createNewBox(this, 56, 0, true, -1.0F, 0.0F, -1.0F, 2, 7, 2, -2.0F, -2.0F, 0.0F, -0.5235988F, 0.0F, 0.0F);
        this.leftLeg = SAPClientUtils.createNewBox(this, 56, 0, false, -1.0F, 0.0F, -1.0F, 2, 7, 2, 2.0F, -2.0F, 0.0F, -0.5235988F, 0.0F, 0.0F);


        this.rightLeg.addChild(SAPClientUtils.createNewBox(this, 56, 0, true, -1.0F, -7.0F, 6.0F,     2, 10, 2,   0.0F, 0.0F, 0.0F,   -1.570796F, 0.0F, 0.0F));
        this.rightLeg.addChild(SAPClientUtils.createNewBox(this, 56, 0, true, -1.0F, -2.0F, 5.0F,     2, 30, 2,   0.0F, 0.0F, 0.0F,   0.0F, 0.0F, 0.0F));

        this.leftLeg.addChild(SAPClientUtils.createNewBox(this, 56, 0, false, -1.0F, -7.0F, 6.0F,     2, 10, 2,   0.0F, 0.0F, 0.0F,   -1.570796F, 0.0F, 0.0F));
        this.leftLeg.addChild(SAPClientUtils.createNewBox(this, 56, 0, false, -1.0F, -2.0F, 5.0F,     2, 30, 2,   0.0F, 0.0F, 0.0F,   0.0F, 0.0F, 0.0F));

        this.head.addChild(SAPClientUtils.createNewBox(this, 40, 16, false, -1.0F, 4.0F, 5.0F, 2, 8, 2, 0.0F, 0.0F, 0.0F, 1.882684F, 0.0F, 0.0F));

        this.body.addChild(SAPClientUtils.createNewBox(this, 40, 16, false, -1.0F, 0.0F, -4.0F, 2, 5, 2, 0.0F, 0.0F, 0.0F, 1.919862F, 0.0F, 0.0F));
        this.body.addChild(SAPClientUtils.createNewBox(this, 40, 16, false, -1.0F, -2.0F, -9.0F, 2, 5, 2, 0.0F, 0.0F, 0.0F, 1.919862F, 0.0F, 0.0F));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        super.render(entity, limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);
        this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        this.body.render(partTicks);
        this.jar.render(partTicks);
        this.head.render(partTicks);
        this.rightArmLow.render(partTicks);
        this.leftArmLow.render(partTicks);
        this.rightLeg.render(partTicks);
        this.leftLeg.render(partTicks);
        this.rightArmHigh.render(partTicks);
        this.leftArmHigh.render(partTicks);
    }

    private void setLivingAnimation(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        this.head.rotateAngleY = this.jar.rotateAngleY = rotYaw / 57.29578F;
        this.head.rotateAngleX = this.jar.rotateAngleX = rotPitch / 57.29578F;

        this.rightArmLow.rotateAngleX = this.rightArmHigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 2.0F * limbSwingAmount * 0.5F;
        this.leftArmLow.rotateAngleX = this.leftArmHigh.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.rightArmLow.rotateAngleZ = 0.2230717F;
        this.leftArmLow.rotateAngleZ = -0.2230717F;
        this.rightArmHigh.rotateAngleZ = 0.3490659F;
        this.leftArmHigh.rotateAngleZ = -0.3490659F;

        this.rightLeg.rotateAngleX = -0.5235988F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.rotateAngleX = -0.5235988F + MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        this.rightLeg.rotateAngleY = 0.0F;
        this.leftLeg.rotateAngleY = 0.0F;

        if( this.isRiding ) {
            this.rightArmLow.rotateAngleX -= 0.6283185F;
            this.leftArmLow.rotateAngleX -= 0.6283185F;
            this.rightArmHigh.rotateAngleX -= 0.6283185F;
            this.leftArmHigh.rotateAngleX -= 0.6283185F;

            this.rightLeg.rotateAngleX = -0.5235988F - 1.256637F;
            this.leftLeg.rotateAngleX = -0.5235988F - 1.256637F;
            this.rightLeg.rotateAngleY = 0.3141593F;
            this.leftLeg.rotateAngleY = -0.3141593F;
        }

        if( this.heldItemRight != 0 ) {
            this.rightArmHigh.rotateAngleX = this.rightArmHigh.rotateAngleX * 0.5F - 0.3141593F * this.heldItemRight;
        }

        this.rightArmLow.rotateAngleY = this.leftArmLow.rotateAngleY = this.rightArmHigh.rotateAngleY = this.leftArmHigh.rotateAngleY = 0.0F;

        if( this.onGround > -9990.0F ) {
            float aFloat1 = this.onGround;

            this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(aFloat1) * 3.141593F * 2.0F) * 0.2F;

            this.rightArmLow.rotationPointZ = 0.2230717F + MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.rightArmLow.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.leftArmLow.rotationPointZ = -0.2230717F - MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.leftArmLow.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;

            this.rightArmLow.rotateAngleY += this.body.rotateAngleY;
            this.leftArmLow.rotateAngleY += this.body.rotateAngleY;
            this.rightArmHigh.rotationPointZ = 0.3490659F + MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.rightArmHigh.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.leftArmHigh.rotationPointZ = -0.3490659F - MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.leftArmHigh.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.rightArmHigh.rotateAngleY += this.body.rotateAngleY;
            this.leftArmHigh.rotateAngleY += this.body.rotateAngleY;

            aFloat1 = 1.0F - this.onGround;
            aFloat1 *= aFloat1;
            aFloat1 *= aFloat1;
            aFloat1 = 1.0F - aFloat1;

            float f8 = MathHelper.sin(aFloat1 * 3.141593F);
            float f10 = MathHelper.sin(this.onGround * 3.141593F) * -(this.head.rotateAngleX - 0.7F) * 0.75F;

            this.rightArmLow.rotateAngleX -= f8 * 1.2D + f10;
            this.rightArmLow.rotateAngleY += this.body.rotateAngleY * 2.0F;
            this.rightArmLow.rotateAngleZ = 0.2230717F + MathHelper.sin(this.onGround * 3.141593F) * -0.4F;
            this.rightArmHigh.rotateAngleX -= f8 * 1.2D + f10;
            this.rightArmHigh.rotateAngleY += this.body.rotateAngleY * 2.0F;
            this.rightArmHigh.rotateAngleZ = 0.3490659F + MathHelper.sin(this.onGround * 3.141593F) * -0.4F;
        }

        this.rightArmLow.rotateAngleZ += MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
        this.leftArmLow.rotateAngleZ -= MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
        this.rightArmLow.rotateAngleX += MathHelper.sin(rotFloat * 0.067F) * 0.05F;
        this.leftArmLow.rotateAngleX -= MathHelper.sin(rotFloat * 0.067F) * 0.05F;
        this.rightArmHigh.rotateAngleZ += MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
        this.leftArmHigh.rotateAngleZ -= MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
        this.rightArmHigh.rotateAngleX += MathHelper.sin(rotFloat * 0.067F) * 0.05F;
        this.leftArmHigh.rotateAngleX -= MathHelper.sin(rotFloat * 0.067F) * 0.05F;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        this.setLivingAnimation(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);

        this.rightArmLow.rotateAngleX *= 0.5D;
        this.leftArmLow.rotateAngleX *= 0.5D;
        this.rightLeg.rotateAngleX *= 0.5D;
        this.leftLeg.rotateAngleX *= 0.5D;

        if( this.isCarrying ) {
            this.rightArmLow.rotateAngleX = -0.7F;
            this.leftArmLow.rotateAngleX = -0.7F;
            this.rightArmLow.rotateAngleZ = 0.05F;
            this.leftArmLow.rotateAngleZ = -0.05F;
        }

        this.head.rotationPointY = -14.0F;

        if( this.isAttacking ) {
            this.head.rotationPointY = -19.0F;
        }
    }

    public void setCarrying(boolean isCarrying) {
        this.isCarrying = isCarrying;
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public void rightArmPostRender() {
        this.rightArmHigh.postRender(0.0625F);
    }
}
