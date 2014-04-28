package de.sanandrew.mods.enderstuffplus.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEnderAvis
    extends ModelBase
{
    private float[] collarColor;
    private boolean isFlying = false;
    private boolean isSitting = false;
    private boolean isTamed = false;
    private int ticksFlying = 0;

    private ModelRenderer beak1;
    private ModelRenderer beak2;
    private ModelRenderer body;
    private ModelRenderer bodyTailL;
    private ModelRenderer bodyTailM;
    private ModelRenderer bodyTailR;
    private ModelRenderer collar;
    private ModelRenderer head;
    private ModelRenderer headTailL;
    private ModelRenderer headTailM;
    private ModelRenderer headTailR;
    private ModelRenderer leftFoot;
    private ModelRenderer leftLeg;
    private ModelRenderer leftWing;
    private ModelRenderer leftWingBone;
    private ModelRenderer neck;
    private ModelRenderer rightFoot;
    private ModelRenderer rightLeg;
    private ModelRenderer rightWing;
    private ModelRenderer rightWingBone;

    public ModelEnderAvis() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-4F, -3F, -8F, 8, 6, 16);
        this.body.setRotationPoint(0F, 8F, 0F);
        this.body.setTextureSize(64, 32);
        this.setRotation(this.body, 0F, 0F, 0F);

        this.neck = new ModelRenderer(this, 8, 0);
        this.neck.addBox(-1F, 0F, -1F, 2, 9, 2);
        this.neck.setRotationPoint(0F, 6F, -7F);
        this.neck.setTextureSize(64, 32);
        this.setRotation(this.neck, -2.530727F, 0F, 0F);

        this.bodyTailM = new ModelRenderer(this, -8, 0);
        this.bodyTailM.addBox(-0.5F, 0F, 0F, 1, 0, 8);
        this.bodyTailM.setRotationPoint(0F, 5F, 8F);
        this.bodyTailM.setTextureSize(64, 32);
        this.setRotation(this.bodyTailM, 0.5235988F, 0F, 0F);

        this.bodyTailL = new ModelRenderer(this, -8, 0);
        this.bodyTailL.addBox(0F, 0F, 0F, 1, 0, 8);
        this.bodyTailL.setRotationPoint(0F, 5F, 8F);
        this.bodyTailL.setTextureSize(64, 32);
        this.setRotation(this.bodyTailL, 0.5235988F, 0.5235988F, 0.3490659F);

        this.bodyTailR = new ModelRenderer(this, -8, 0);
        this.bodyTailR.addBox(-1F, 0F, 0F, 1, 0, 8);
        this.bodyTailR.setRotationPoint(0F, 5F, 8F);
        this.bodyTailR.setTextureSize(64, 32);
        this.setRotation(this.bodyTailR, 0.5235988F, -0.5235988F, -0.3490659F);

        this.head = new ModelRenderer(this, 32, 6);
        this.head.addBox(-2F, -3F, -3F, 4, 4, 6);
        this.head.setRotationPoint(0F, -1F, -12F);
        this.head.setTextureSize(64, 32);
        this.setRotation(this.head, 0F, 0F, 0F);

        this.headTailM = new ModelRenderer(this, -4, 12);
        this.headTailM.addBox(-0.5F, 0F, 4F, 1, 0, 4);
        this.headTailM.setRotationPoint(0F, -1F, -12F);
        this.headTailM.setTextureSize(64, 32);
        this.setRotation(this.headTailM, 0.7853982F, 0F, 0F);

        this.beak1 = new ModelRenderer(this, 8, 12);
        this.beak1.addBox(-1F, -2F, -5F, 2, 2, 2);
        this.beak1.setRotationPoint(0F, -1F, -12F);
        this.beak1.setTextureSize(64, 32);
        this.setRotation(this.beak1, 0F, 0F, 0F);

        this.beak2 = new ModelRenderer(this, 6, 12);
        this.beak2.addBox(-0.5F, -1.5F, -6F, 1, 1, 1);
        this.beak2.setRotationPoint(0F, -1F, -12F);
        this.beak2.setTextureSize(64, 32);
        this.setRotation(this.beak2, 0F, 0F, 0F);

        this.rightLeg = new ModelRenderer(this, 2, 0);
        this.rightLeg.addBox(-0.5F, 0F, -0.5F, 1, 14, 1);
        this.rightLeg.setRotationPoint(-2F, 10F, 0F);
        this.rightLeg.setTextureSize(64, 32);
        this.rightLeg.mirror = true;
        this.setRotation(this.rightLeg, 0F, 0F, 0F);

        this.leftLeg = new ModelRenderer(this, 2, 0);
        this.leftLeg.addBox(-0.5F, 0F, -0.5F, 1, 14, 1);
        this.leftLeg.setRotationPoint(2F, 10F, 0F);
        this.leftLeg.setTextureSize(64, 32);
        this.setRotation(this.leftLeg, 0F, 0F, 0F);

        this.rightFoot = new ModelRenderer(this, 27, 0);
        this.rightFoot.addBox(-1.5F, 14F, -3F, 3, 0, 5);
        this.rightFoot.setRotationPoint(-2F, 10F, 0F);
        this.rightFoot.setTextureSize(64, 32);
        this.rightFoot.mirror = true;
        this.setRotation(this.rightFoot, 0F, 0F, 0F);

        this.leftFoot = new ModelRenderer(this, 27, 0);
        this.leftFoot.addBox(-1.5F, 14F, -3F, 3, 0, 5);
        this.leftFoot.setRotationPoint(2F, 10F, 0F);
        this.leftFoot.setTextureSize(64, 32);
        this.setRotation(this.leftFoot, 0F, 0F, 0F);

        this.leftWingBone = new ModelRenderer(this, 0, 22);
        this.leftWingBone.addBox(0F, 0F, -1F, 12, 1, 1);
        this.leftWingBone.setRotationPoint(4F, 6F, -5F);
        this.leftWingBone.setTextureSize(64, 32);
        this.setRotation(this.leftWingBone, 0F, -1.5707963267949F, 0F);

        this.leftWing = new ModelRenderer(this, -8, 24);
        this.leftWing.addBox(-0.5F, 0.5F, 0F, 12, 0, 8);
        this.leftWing.setRotationPoint(4F, 6F, -5F);
        this.leftWing.setTextureSize(64, 32);
        this.leftWing.mirror = true;
        this.setRotation(this.leftWing, 0F, -1.5707963267949F, 0F);

        this.rightWingBone = new ModelRenderer(this, 0, 22);
        this.rightWingBone.addBox(-12F, 0F, -1F, 12, 1, 1);
        this.rightWingBone.setRotationPoint(-4F, 6F, -5F);
        this.rightWingBone.setTextureSize(64, 32);
        this.setRotation(this.rightWingBone, 0F, 1.5707963267949F, 0F);

        this.rightWing = new ModelRenderer(this, 16, 24);
        this.rightWing.addBox(-11.5F, 0.5F, 0F, 12, 0, 8);
        this.rightWing.setRotationPoint(-4F, 6F, -5F);
        this.rightWing.setTextureSize(64, 32);
        this.setRotation(this.rightWing, 0F, 1.5707963267949F, 0F);

        this.headTailL = new ModelRenderer(this, -4, 12);
        this.headTailL.addBox(-0.5F, 0F, 4F, 1, 0, 4);
        this.headTailL.setRotationPoint(0F, -1F, -12F);
        this.headTailL.setTextureSize(64, 32);
        this.setRotation(this.headTailL, 0.7853982F, 0.2617994F, 0.2617994F);

        this.headTailR = new ModelRenderer(this, -4, 12);
        this.headTailR.addBox(-0.5F, 0F, 4F, 1, 0, 4);
        this.headTailR.setRotationPoint(0F, -1F, -12F);
        this.headTailR.setTextureSize(64, 32);
        this.setRotation(this.headTailR, 0.7853982F, -0.2617994F, -0.2617994F);

        this.collar = new ModelRenderer(this, 38, 0);
        this.collar.addBox(-1F, 3F, -1.8F, 2, 3, 2);
        this.collar.setRotationPoint(0F, 6F, -7F);
        this.collar.setTextureSize(64, 32);
        this.setRotation(this.collar, -2.530727F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw,
                       float rotPitch, float partTicks) {
        this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);

        float yPos = this.isSitting ? 11.5F : 0F;

        this.body.setRotationPoint(0F, 8F + yPos, 0F);
        this.neck.setRotationPoint(0F, 6F + yPos, -7F);
        this.bodyTailM.setRotationPoint(0F, 5F + yPos, 8F);
        this.bodyTailL.setRotationPoint(0F, 5F + yPos, 8F);
        this.bodyTailR.setRotationPoint(0F, 5F + yPos, 8F);
        this.head.setRotationPoint(0F, -1F + yPos, -12F);
        this.headTailM.setRotationPoint(0F, -1F + yPos, -12F);
        this.beak1.setRotationPoint(0F, -1F + yPos, -12F);
        this.beak2.setRotationPoint(0F, -1F + yPos, -12F);
        this.leftWingBone.setRotationPoint(4F, 6F + yPos, -5F);
        this.leftWing.setRotationPoint(4F, 6F + yPos, -5F);
        this.rightWingBone.setRotationPoint(-4F, 6F + yPos, -5F);
        this.rightWing.setRotationPoint(-4F, 6F + yPos, -5F);
        this.headTailL.setRotationPoint(0F, -1F + yPos, -12F);
        this.headTailR.setRotationPoint(0F, -1F + yPos, -12F);
        this.collar.setRotationPoint(0F, 6F + yPos, -7F + (this.isSitting ? 0.8F : 0F));

        this.body.render(partTicks);
        this.neck.render(partTicks);
        this.bodyTailM.render(partTicks);
        this.bodyTailL.render(partTicks);
        this.bodyTailR.render(partTicks);
        this.head.render(partTicks);
        this.headTailM.render(partTicks);
        this.beak1.render(partTicks);
        this.beak2.render(partTicks);

        if( !this.isSitting ) {
            this.rightLeg.render(partTicks);
            this.leftLeg.render(partTicks);
            this.rightFoot.render(partTicks);
            this.leftFoot.render(partTicks);
        }

        this.leftWingBone.render(partTicks);
        this.leftWing.render(partTicks);
        this.rightWingBone.render(partTicks);
        this.rightWing.render(partTicks);
        this.headTailL.render(partTicks);
        this.headTailR.render(partTicks);

        if( this.isTamed ) {
            GL11.glPushMatrix();

            if( entity instanceof EntityLiving && ((EntityLiving) entity).hurtTime <= 0 ) {
                GL11.glColor3f(this.collarColor[0], this.collarColor[1], this.collarColor[2]);
            }

            GL11.glScalef(1.1F, 1.1F, 1.1F);
            this.collar.render(partTicks);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glColor3f(1F, 1F, 1F);
            GL11.glPopMatrix();
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch,
                                  float partTicks, Entity entity) {
        this.head.rotateAngleY = (rotYaw / (180F / (float) Math.PI));
        this.head.rotateAngleX = rotPitch / (180F / (float) Math.PI);

        this.beak1.rotateAngleY = this.head.rotateAngleY;
        this.beak2.rotateAngleY = this.head.rotateAngleY;

        this.beak1.rotateAngleX = this.head.rotateAngleX;
        this.beak2.rotateAngleX = this.head.rotateAngleX;

        this.headTailM.rotateAngleY = this.head.rotateAngleY;
        this.headTailM.rotateAngleX = 0.7853982F + this.head.rotateAngleX;
        this.headTailL.rotateAngleY = 0.2617994F + this.head.rotateAngleY;
        this.headTailL.rotateAngleX = 0.7853982F + this.head.rotateAngleX;
        this.headTailR.rotateAngleY = -0.2617994F + this.head.rotateAngleY;
        this.headTailR.rotateAngleX = 0.7853982F + this.head.rotateAngleX;

        this.rightWingBone.rotateAngleY = 1.5707963267949F - (1.5707963267949F * (this.ticksFlying / 5F));
        this.rightWing.rotateAngleY = 1.5707963267949F - (1.5707963267949F * (this.ticksFlying / 5F));
        this.leftWingBone.rotateAngleY = -1.5707963267949F + (1.5707963267949F * (this.ticksFlying / 5F));
        this.leftWing.rotateAngleY = -1.5707963267949F + (1.5707963267949F * (this.ticksFlying / 5F));

        if( this.isFlying ) {
            if( this.ticksFlying >= 5 ) {
                this.rightWing.rotateAngleZ = rotFloat - 0.7F;
                this.leftWing.rotateAngleZ = -rotFloat + 0.7F;
                this.rightWingBone.rotateAngleZ = rotFloat - 0.7F;
                this.leftWingBone.rotateAngleZ = -rotFloat + 0.7F;
            }

            this.rightLeg.rotateAngleX *= 0.8F;
            this.leftLeg.rotateAngleX *= 0.8F;
            this.rightFoot.rotateAngleX *= 0.8F;
            this.leftFoot.rotateAngleX *= 0.8F;
        } else {
            this.rightWing.rotateAngleZ = 0F;
            this.rightWingBone.rotateAngleZ = 0F;
            this.leftWing.rotateAngleZ = 0F;
            this.leftWingBone.rotateAngleZ = 0F;
            this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.rightFoot.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leftFoot.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        }
    }
    
    public void setFlying(boolean isModelFlying) {
        this.isFlying = isModelFlying;
    }
    
    public void setSitting(boolean isModelSitting) {
        this.isSitting = isModelSitting;
    }
    
    public void setTicksFlying(int ticks) {
        this.ticksFlying = ticks;
    }
    
    public void setTamed(boolean isModelTamed) {
        this.isTamed = isModelTamed;
    }
    
    public void setCollarColor(float[] colors) {
        this.collarColor = colors;
    }
}
