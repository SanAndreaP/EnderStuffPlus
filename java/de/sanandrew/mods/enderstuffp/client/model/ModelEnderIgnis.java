package de.sanandrew.mods.enderstuffp.client.model;

import de.sanandrew.core.manpack.util.client.helpers.SAPClientUtils;
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
    private ModelRenderer body;
    private ModelRenderer head;
    private ModelRenderer jar;
    private ModelRenderer leftArm;
    private ModelRenderer rightArm;
    private int heldItemRight;
    private boolean isAttacking;

    public ModelEnderIgnis() {
        this.isAttacking = false;
        this.heldItemRight = 0;

        this.textureWidth = 64;
        this.textureHeight = 32;

        this.head = SAPClientUtils.createNewBox(this, 0, 0, false, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.jar = SAPClientUtils.createNewBox(this, 0, 16, false, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.body = SAPClientUtils.createNewBox(this, 32, 0, false, -4.0F, 0.0F, -2.0F, 8, 28, 4, 0.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm = SAPClientUtils.createNewBox(this, 56, 0, true, -1.0F, -2.0F, -1.0F, 2, 20, 2, -5.0F, -12.0F, 0.0F, 0.0F, 0.0F, 0.3490659F);
        this.leftArm = SAPClientUtils.createNewBox(this, 56, 0, false, -1.0F, -2.0F, -1.0F, 2, 20, 2, 5.0F, -12.0F, 0.0F, 0.0F, 0.0F, -0.3490659F);

        this.head.addChild(SAPClientUtils.createNewBox(this, 56, 16, true, -3.0F, 2.0F, 6.0F, 2, 8, 2, 0.0F, 0.0F, 0.0F, 2.181662F, 0.0F, 0.0F));
        this.head.addChild(SAPClientUtils.createNewBox(this, 56, 16, false, 1.0F, 2.0F, 6.0F, 2, 8, 2, 0.0F, 0.0F, 0.0F, 2.181662F, 0.0F, 0.0F));
        this.body.addChild(SAPClientUtils.createNewBox(this, 56, 16, false, -1.0F, 0.0F, -6.0F, 2, 5, 2, 0.0F, 0.0F, 0.0F, 1.919862F, 0.0F, 0.0F));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        super.render(entity, limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);
        this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        this.jar.render(partTicks);
        this.body.render(partTicks);
        this.rightArm.render(partTicks);
        this.leftArm.render(partTicks);
//        this.backHorn.render(partTicks);
        this.head.render(partTicks);
    }

    private void setLivingAnimation(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        this.head.rotateAngleY = rotYaw / 57.29578F;
        this.head.rotateAngleX = rotPitch / 57.29578F;

        this.jar.rotateAngleY = this.head.rotateAngleY;
        this.jar.rotateAngleX = this.head.rotateAngleX;

        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 2.0F * limbSwingAmount * 0.5F;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.rightArm.rotateAngleZ = 0.3490659F;
        this.leftArm.rotateAngleZ = -0.3490659F;

        if( this.isRiding ) {
            this.rightArm.rotateAngleX -= 0.6283185F;
            this.leftArm.rotateAngleX -= 0.6283185F;
        }

        if( this.heldItemRight != 0 ) {
            this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5F - 0.3141593F * this.heldItemRight;
        }

        this.rightArm.rotateAngleY = 0.0F;
        this.leftArm.rotateAngleY = 0.0F;

        if( this.onGround > -9990.0F ) {
            float onGroundVal = this.onGround;

            this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(onGroundVal) * 3.141593F * 2.0F) * 0.2F;
            this.rightArm.rotationPointZ = 0.3490659F + MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.rightArm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.leftArm.rotationPointZ = -0.3490659F - MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.leftArm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.rightArm.rotateAngleY += this.body.rotateAngleY;
            this.leftArm.rotateAngleY += this.body.rotateAngleY;

            onGroundVal = 1.0F - this.onGround;
            onGroundVal *= onGroundVal;
            onGroundVal *= onGroundVal;
            onGroundVal = 1.0F - onGroundVal;

            float rightArmAngleX1 = MathHelper.sin(onGroundVal * 3.141593F);
            float rightArmAngleX2 = MathHelper.sin(this.onGround * 3.141593F) * -(this.head.rotateAngleX - 0.7F) * 0.75F;

            this.rightArm.rotateAngleX -= rightArmAngleX1 * 1.2D + rightArmAngleX2;
            this.rightArm.rotateAngleY += this.body.rotateAngleY * 2.0F;
            this.rightArm.rotateAngleZ = 0.3490659F + MathHelper.sin(this.onGround * 3.141593F) * -0.4F;
        }

        this.body.rotateAngleX = 0.0F;
        this.head.rotationPointY = 0.0F;
        this.rightArm.rotateAngleZ += MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
        this.leftArm.rotateAngleZ -= MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
        this.rightArm.rotateAngleX += MathHelper.sin(rotFloat * 0.067F) * 0.05F;
        this.leftArm.rotateAngleX -= MathHelper.sin(rotFloat * 0.067F) * 0.05F;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        this.setLivingAnimation(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);

        this.head.rotationPointZ = -0.0F;
        this.head.rotationPointY = -14.0F;
        this.jar.rotationPointX = this.head.rotationPointX;
        this.jar.rotationPointY = this.head.rotationPointY;
        this.jar.rotationPointZ = this.head.rotationPointZ;
        this.jar.rotateAngleX = this.head.rotateAngleX;
        this.jar.rotateAngleY = this.head.rotateAngleY;
        this.jar.rotateAngleZ = this.head.rotateAngleZ;

        if( this.isAttacking ) {
            this.head.rotationPointY -= 5.0F;
        }
    }

    public void setAttacking(boolean isModelAttacking) {
        this.isAttacking = isModelAttacking;
    }

    public void rightArmPostRender() {
        this.rightArm.postRender(0.0625F);
    }
}
