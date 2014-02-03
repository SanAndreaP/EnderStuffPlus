package sanandreasp.mods.EnderStuffPlus.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelEnderNivis extends ModelBase
{
	public ModelRenderer bipedHeadwear, bipedBody, bipedRightArm1, bipedLeftArm1, bipedRightLeg, bipedLeftLeg, bipedRightArm2;
	public ModelRenderer bipedLeftArm2, Shape1, Shape2, Shape3, bipedRightLeg1, bipedLeftLeg1, bipedRightLeg2, bipedLeftLeg2;
	public ModelRenderer bipedHead;
	public boolean isCarrying;
	public boolean isAttacking;
	public int heldItemRight;

	public ModelEnderNivis() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.isCarrying = false;
		this.isAttacking = false;
		this.heldItemRight = 0;

		this.bipedHeadwear = new ModelRenderer(this, 0, 16);
		this.bipedHeadwear.addBox(-4F, -8F, -4F, 8, 8, 8);
		this.bipedHeadwear.setRotationPoint(0F, -14F, 0F);
		this.bipedHeadwear.setTextureSize(64, 32);
		this.setRotation(this.bipedHeadwear, 0F, 0F, 0F);
		
		this.bipedBody = new ModelRenderer(this, 32, 16);
		this.bipedBody.addBox(-4F, 0F, -2F, 8, 12, 4);
		this.bipedBody.setRotationPoint(0F, -14F, 0F);
		this.bipedBody.setTextureSize(64, 32);
		this.setRotation(this.bipedBody, 0F, 0F, 0F);
		
		this.bipedRightArm1 = new ModelRenderer(this, 56, 0);
		this.bipedRightArm1.addBox(-1F, -2F, -1F, 2, 20, 2);
		this.bipedRightArm1.setRotationPoint(-4F, -7F, 0F);
		this.bipedRightArm1.setTextureSize(64, 32);
		this.bipedRightArm1.mirror = true;
		this.setRotation(this.bipedRightArm1, 0F, 0F, 0.2230717F);
		
		this.bipedLeftArm1 = new ModelRenderer(this, 56, 0);
		this.bipedLeftArm1.addBox(-1F, -2F, -1F, 2, 20, 2);
		this.bipedLeftArm1.setRotationPoint(4F, -7F, 0F);
		this.bipedLeftArm1.setTextureSize(64, 32);
		this.setRotation(this.bipedLeftArm1, 0F, 0F, -0.2230717F);
		
		this.bipedRightLeg = new ModelRenderer(this, 56, 0);
		this.bipedRightLeg.addBox(-1F, 0F, -1F, 2, 7, 2);
		this.bipedRightLeg.setRotationPoint(-2F, -2F, 0F);
		this.bipedRightLeg.setTextureSize(64, 32);
		this.bipedRightLeg.mirror = true;
		this.setRotation(this.bipedRightLeg, -0.5235988F, 0F, 0F);
		
		this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
		this.bipedLeftLeg.addBox(-1F, 0F, -1F, 2, 7, 2);
		this.bipedLeftLeg.setRotationPoint(2F, -2F, 0F);
		this.bipedLeftLeg.setTextureSize(64, 32);
		this.setRotation(this.bipedLeftLeg, -0.5235988F, 0F, 0F);
		
		this.bipedRightArm2 = new ModelRenderer(this, 56, 0);
		this.bipedRightArm2.addBox(-1F, -2F, -1F, 2, 20, 2);
		this.bipedRightArm2.setRotationPoint(-5F, -12F, 0F);
		this.bipedRightArm2.setTextureSize(64, 32);
		this.bipedRightArm2.mirror = true;
		this.setRotation(this.bipedRightArm2, 0F, 0F, 0.3490659F);
		
		this.bipedLeftArm2 = new ModelRenderer(this, 56, 0);
		this.bipedLeftArm2.addBox(-1F, -2F, -1F, 2, 20, 2);
		this.bipedLeftArm2.setRotationPoint(5F, -12F, 0F);
		this.bipedLeftArm2.setTextureSize(64, 32);
		this.setRotation(this.bipedLeftArm2, 0F, 0F, -0.3490659F);
		
		this.Shape1 = new ModelRenderer(this, 40, 16);
		this.Shape1.addBox(-1F, 0F, -4F, 2, 5, 2);
		this.Shape1.setRotationPoint(0F, -14F, 0F);
		this.Shape1.setTextureSize(64, 32);
		this.Shape1.mirror = true;
		this.setRotation(this.Shape1, 1.919862F, 0F, 0F);
		
		this.Shape2 = new ModelRenderer(this, 40, 16);
		this.Shape2.addBox(-1F, 4F, 5F, 2, 8, 2);
		this.Shape2.setRotationPoint(0F, -14F, 0F);
		this.Shape2.setTextureSize(64, 32);
		this.Shape2.mirror = true;
		this.setRotation(this.Shape2, 1.882684F, 0F, 0F);
		
		this.Shape3 = new ModelRenderer(this, 40, 16);
		this.Shape3.addBox(-1F, -2F, -9F, 2, 5, 2);
		this.Shape3.setRotationPoint(0F, -14F, 0F);
		this.Shape3.setTextureSize(64, 32);
		this.Shape3.mirror = true;
		this.setRotation(this.Shape3, 1.919862F, 0F, 0F);
		
		this.bipedRightLeg1 = new ModelRenderer(this, 56, 0);
		this.bipedRightLeg1.addBox(-1F, -7F, 6F, 2, 10, 2);
		this.bipedRightLeg1.setRotationPoint(-2F, -2F, 0F);
		this.bipedRightLeg1.setTextureSize(64, 32);
		this.bipedRightLeg1.mirror = true;
		this.setRotation(this.bipedRightLeg1, -1.832596F, 0F, 0F);
		
		this.bipedLeftLeg1 = new ModelRenderer(this, 56, 0);
		this.bipedLeftLeg1.addBox(-1F, -7F, 6F, 2, 10, 2);
		this.bipedLeftLeg1.setRotationPoint(2F, -2F, 0F);
		this.bipedLeftLeg1.setTextureSize(64, 32);
		this.setRotation(this.bipedLeftLeg1, -1.832596F, 0F, 0F);
		
		this.bipedRightLeg2 = new ModelRenderer(this, 56, 0);
		this.bipedRightLeg2.addBox(-1F, -2F, 5F, 2, 30, 2);
		this.bipedRightLeg2.setRotationPoint(-2F, -2F, 0F);
		this.bipedRightLeg2.setTextureSize(64, 32);
		this.bipedRightLeg2.mirror = true;
		this.setRotation(this.bipedRightLeg2, -0.2617994F, 0F, 0F);
		
		this.bipedLeftLeg2 = new ModelRenderer(this, 56, 0);
		this.bipedLeftLeg2.addBox(-1F, -2F, 5F, 2, 30, 2);
		this.bipedLeftLeg2.setRotationPoint(2F, -2F, 0F);
		this.bipedLeftLeg2.setTextureSize(64, 32);
		this.setRotation(this.bipedLeftLeg2, -0.2617994F, 0F, 0F);
		
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8);
		this.bipedHead.setRotationPoint(0F, -14F, 0F);
		this.bipedHead.setTextureSize(64, 32);
		this.setRotation(this.bipedHead, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
		super.render(entity, limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);
		this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
		this.bipedHeadwear.render(partTicks);
		this.bipedBody.render(partTicks);
		this.bipedRightArm1.render(partTicks);
		this.bipedLeftArm1.render(partTicks);
		this.bipedRightLeg.render(partTicks);
		this.bipedLeftLeg.render(partTicks);
		this.bipedRightArm2.render(partTicks);
		this.bipedLeftArm2.render(partTicks);
		this.Shape1.render(partTicks);
		this.Shape2.render(partTicks);
		this.Shape3.render(partTicks);
		this.bipedRightLeg1.render(partTicks);
		this.bipedLeftLeg1.render(partTicks);
		this.bipedRightLeg2.render(partTicks);
		this.bipedLeftLeg2.render(partTicks);
		this.bipedHead.render(partTicks);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	private void setLivingAnimation(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
		this.bipedHead.rotateAngleY = rotYaw / 57.29578F;
		this.bipedHead.rotateAngleX = rotPitch / 57.29578F;
		this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
		this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
		this.Shape2.rotateAngleY = this.bipedHead.rotateAngleY;
		this.Shape2.rotateAngleX = 1.882684F + this.bipedHead.rotateAngleX;

		this.bipedRightArm1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 2.0F * limbSwingAmount * 0.5F;
		this.bipedLeftArm1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.bipedRightArm1.rotateAngleZ = 0.2230717F;
		this.bipedLeftArm1.rotateAngleZ = -0.2230717F;
		this.bipedRightArm2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 2.0F * limbSwingAmount * 0.5F;
		this.bipedLeftArm2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.bipedRightArm2.rotateAngleZ = 0.3490659F;
		this.bipedLeftArm2.rotateAngleZ = -0.3490659F;

		this.bipedRightLeg.rotateAngleX = -0.5235988F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.bipedLeftLeg.rotateAngleX = -0.5235988F + MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
		this.bipedRightLeg.rotateAngleY = 0.0F;
		this.bipedLeftLeg.rotateAngleY = 0.0F;
		this.bipedRightLeg1.rotateAngleX = -1.832596F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.bipedLeftLeg1.rotateAngleX = -1.832596F + MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
		this.bipedRightLeg1.rotateAngleY = 0.0F;
		this.bipedLeftLeg1.rotateAngleY = 0.0F;
		this.bipedRightLeg2.rotateAngleX = -0.2617994F + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.bipedLeftLeg2.rotateAngleX = -0.2617994F + MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
		this.bipedRightLeg2.rotateAngleY = 0.0F;
		this.bipedLeftLeg2.rotateAngleY = 0.0F;

		if( this.isRiding ) {
			this.bipedRightArm1.rotateAngleX += -0.6283185F;
			this.bipedLeftArm1.rotateAngleX += -0.6283185F;
			this.bipedRightArm2.rotateAngleX += -0.6283185F;
			this.bipedLeftArm2.rotateAngleX += -0.6283185F;

			this.bipedRightLeg.rotateAngleX = -0.5235988F - 1.256637F;
			this.bipedLeftLeg.rotateAngleX = -0.5235988F - 1.256637F;
			this.bipedRightLeg.rotateAngleY = 0.3141593F;
			this.bipedLeftLeg.rotateAngleY = -0.3141593F;
			this.bipedRightLeg1.rotateAngleX = -1.832596F - 1.256637F;
			this.bipedLeftLeg1.rotateAngleX = -1.832596F - 1.256637F;
			this.bipedRightLeg1.rotateAngleY = 0.3141593F;
			this.bipedLeftLeg1.rotateAngleY = -0.3141593F;
			this.bipedRightLeg2.rotateAngleX = -0.2617994F - 1.256637F;
			this.bipedLeftLeg2.rotateAngleX = -0.2617994F - 1.256637F;
			this.bipedRightLeg2.rotateAngleY = 0.3141593F;
			this.bipedLeftLeg2.rotateAngleY = -0.3141593F;
		}

		if( this.heldItemRight != 0 ) {
			this.bipedRightArm2.rotateAngleX = this.bipedRightArm2.rotateAngleX * 0.5F - 0.3141593F * this.heldItemRight;
		}

		this.bipedRightArm1.rotateAngleY = 0.0F;
		this.bipedLeftArm1.rotateAngleY = 0.0F;
		this.bipedRightArm2.rotateAngleY = 0.0F;
		this.bipedLeftArm2.rotateAngleY = 0.0F;

		if( this.onGround > -9990F ) {
			float aFloat1 = this.onGround;
			
			this.bipedBody.rotateAngleY = MathHelper .sin(MathHelper.sqrt_float(aFloat1) * 3.141593F * 2.0F) * 0.2F;

			this.bipedRightArm1.rotationPointZ = 0.2230717F + MathHelper.sin(this.bipedBody.rotateAngleY) * 5F;
			this.bipedRightArm1.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5F;
			this.bipedLeftArm1.rotationPointZ = -0.2230717F - MathHelper.sin(this.bipedBody.rotateAngleY) * 5F;
			this.bipedLeftArm1.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5F;
			this.bipedRightArm1.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm1.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm1.rotateAngleX += this.bipedBody.rotateAngleY;
			this.bipedRightArm2.rotationPointZ = 0.3490659F + MathHelper.sin(this.bipedBody.rotateAngleY) * 5F;
			this.bipedRightArm2.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5F;
			this.bipedLeftArm2.rotationPointZ = -0.3490659F - MathHelper.sin(this.bipedBody.rotateAngleY) * 5F;
			this.bipedLeftArm2.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5F;
			this.bipedRightArm2.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm2.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm2.rotateAngleX += this.bipedBody.rotateAngleY;

			aFloat1 = 1.0F - this.onGround;
			aFloat1 *= aFloat1;
			aFloat1 *= aFloat1;
			aFloat1 = 1.0F - aFloat1;

			float f8 = MathHelper.sin(aFloat1 * 3.141593F);
			float f10 = MathHelper.sin(this.onGround * 3.141593F) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;

			this.bipedRightArm1.rotateAngleX -= f8 * 1.2D + f10;
			this.bipedRightArm1.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
			this.bipedRightArm1.rotateAngleZ = 0.2230717F + MathHelper.sin(this.onGround * 3.141593F) * -0.4F;
			this.bipedRightArm2.rotateAngleX -= f8 * 1.2D + f10;
			this.bipedRightArm2.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
			this.bipedRightArm2.rotateAngleZ = 0.3490659F + MathHelper.sin(this.onGround * 3.141593F) * -0.4F;
		}
		this.bipedBody.rotateAngleX = 0.0F;
		this.bipedHead.rotationPointY = 0.0F;
		this.bipedRightArm1.rotateAngleZ += MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm1.rotateAngleZ -= MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm1.rotateAngleX += MathHelper.sin(rotFloat * 0.067F) * 0.05F;
		this.bipedLeftArm1.rotateAngleX -= MathHelper.sin(rotFloat * 0.067F) * 0.05F;
		this.bipedRightArm2.rotateAngleZ += MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm2.rotateAngleZ -= MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm2.rotateAngleX += MathHelper.sin(rotFloat * 0.067F) * 0.05F;
		this.bipedLeftArm2.rotateAngleX -= MathHelper.sin(rotFloat * 0.067F) * 0.05F;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
		this.setLivingAnimation(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);

		this.bipedRightArm1.rotateAngleX *= 0.5D;
		this.bipedLeftArm1.rotateAngleX *= 0.5D;

		this.bipedRightLeg.rotateAngleX -= 0.0F;
		this.bipedLeftLeg.rotateAngleX -= 0.0F;
		this.bipedRightLeg.rotateAngleX *= 0.5D;
		this.bipedLeftLeg.rotateAngleX *= 0.5D;

		float maxSwing = 0.4F;
		if( this.bipedRightArm1.rotateAngleX > maxSwing ) {
			this.bipedRightArm1.rotateAngleX = maxSwing;
		}
		if( this.bipedLeftArm1.rotateAngleX > maxSwing ) {
			this.bipedLeftArm1.rotateAngleX = maxSwing;
		}
		if( this.bipedRightArm1.rotateAngleX < -maxSwing ) {
			this.bipedRightArm1.rotateAngleX = -maxSwing;
		}
		if( this.bipedLeftArm1.rotateAngleX < -maxSwing ) {
			this.bipedLeftArm1.rotateAngleX = -maxSwing;
		}

		if( this.bipedRightLeg.rotateAngleX > -0.5235988F + maxSwing ) {
			this.bipedRightLeg.rotateAngleX = -0.5235988F + maxSwing;
		}
		if( this.bipedLeftLeg.rotateAngleX > -0.5235988F + maxSwing ) {
			this.bipedLeftLeg.rotateAngleX = -0.5235988F + maxSwing;
		}
		if( this.bipedRightLeg.rotateAngleX < -0.5235988F - maxSwing ) {
			this.bipedRightLeg.rotateAngleX = -0.5235988F - maxSwing;
		}
		if( this.bipedLeftLeg.rotateAngleX < -0.5235988F - maxSwing ) {
			this.bipedLeftLeg.rotateAngleX = -0.5235988F - maxSwing;
		}

		if( this.bipedRightLeg1.rotateAngleX > -1.832596F + maxSwing ) {
			this.bipedRightLeg1.rotateAngleX = -1.832596F + maxSwing;
		}
		if( this.bipedLeftLeg1.rotateAngleX > -1.832596F + maxSwing ) {
			this.bipedLeftLeg1.rotateAngleX = -1.832596F + maxSwing;
		}
		if( this.bipedRightLeg1.rotateAngleX < -1.832596F - maxSwing ) {
			this.bipedRightLeg1.rotateAngleX = -1.832596F - maxSwing;
		}
		if( this.bipedLeftLeg1.rotateAngleX < -1.832596F - maxSwing ) {
			this.bipedLeftLeg1.rotateAngleX = -1.832596F - maxSwing;
		}

		if( this.bipedRightLeg2.rotateAngleX > -0.2617994F + maxSwing ) {
			this.bipedRightLeg2.rotateAngleX = -0.2617994F + maxSwing;
		}
		if( this.bipedLeftLeg2.rotateAngleX > -0.2617994F + maxSwing ) {
			this.bipedLeftLeg2.rotateAngleX = -0.2617994F + maxSwing;
		}
		if( this.bipedRightLeg2.rotateAngleX < -0.2617994F - maxSwing ) {
			this.bipedRightLeg2.rotateAngleX = -0.2617994F - maxSwing;
		}
		if( this.bipedLeftLeg2.rotateAngleX < -0.2617994F - maxSwing ) {
			this.bipedLeftLeg2.rotateAngleX = -0.2617994F - maxSwing;
		}

		if( this.isCarrying ) {
			this.bipedRightArm1.rotateAngleX = -0.7F;
			this.bipedLeftArm1.rotateAngleX = -0.7F;
			this.bipedRightArm1.rotateAngleZ = 0.05F;
			this.bipedLeftArm1.rotateAngleZ = -0.05F;
		}

		this.bipedHead.rotationPointZ = -0.0F;
		this.bipedHead.rotationPointY = -14.0F;
		this.Shape2.rotationPointZ = -0.0F;
		this.Shape2.rotationPointY = -14.0F;
		this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
		this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
		this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
		this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
		this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
		this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;

		if( this.isAttacking ) {
			this.bipedHead.rotationPointY -= 5F;
			this.Shape2.rotationPointY -= 5F;
		}
	}
}
