package sanandreasp.mods.EnderStuffPlus.client.model;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEnderMiss
    extends ModelBiped
{
    private ModelRenderer bipedBodyCoat;
    private boolean isAttacking = false;
    private boolean isCaped = false;
    private boolean isCarrying = false;
    private boolean isRidden = false;
    private boolean isSitting = false;

    public ModelEnderMiss(boolean hasCape) {
        super(0.0F, -14.0F, 64, 32);

        this.isCaped = hasCape;

        this.textureWidth = 128;
        this.textureHeight = 64;

        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.bipedHead.setRotationPoint(0.0F, -13.5F, 0.0F);

        this.bipedHeadwear = new ModelRenderer(this, 0, 16);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, -0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, -13.5F, 0.0F);

        this.bipedBody = new ModelRenderer(this, 32, 16);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.bipedBody.setRotationPoint(0.0F, -14.0F, 0.0F);

        this.bipedBodyCoat = new ModelRenderer(this, 32, 16);
        this.bipedBodyCoat.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.bipedBodyCoat.setRotationPoint(0.0F, -14.0F, 0.0F);

        this.bipedRightArm = new ModelRenderer(this, 64, 0);
        this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, 0.0F);
        this.bipedRightArm.setRotationPoint(-3.0F, -12.0F, 0.0F);

        this.bipedLeftArm = new ModelRenderer(this, 64, 0);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, 0.0F);
        this.bipedLeftArm.setRotationPoint(5.0F, -12.0F, 0.0F);

        this.bipedRightLeg = new ModelRenderer(this, 56, 0);
        this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, 0.0F);
        this.bipedRightLeg.setRotationPoint(-2.0F, -4.0F, 0.0F);

        this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, 0.0F);
        this.bipedLeftLeg.setRotationPoint(2.0F, -4.0F, 0.0F);
    }

    private void drawSkirt(float limbSwing, float limbSwingAmount, float partTicks) {
        Tessellator tessellator = Tessellator.instance;

        if( !this.isSitting ) {
            GL11.glPushMatrix();
            GL11.glRotatef(this.bipedBody.rotateAngleY * 180F / (float) Math.PI, 0F, 1F, 0F);
            double skirtStretch = Math.max(this.bipedRightLeg.rotateAngleX, this.bipedLeftLeg.rotateAngleX) * 1.6D + 0.3D;

            tessellator.startDrawingQuads();            // left side
            tessellator.setNormal(1F, 0F, 0F);
            tessellator.addVertexWithUV(-0.3D,   0.5D,    0.45 * skirtStretch, 64.0 / 256.0, 80.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(-0.3D,   0.5D,   -0.45 * skirtStretch, 72.0 / 256.0, 80.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(-0.25D, -0.125D, -0.125,               72.0 / 256.0, 64.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(-0.25D, -0.125D,  0.125,               64.0 / 256.0, 64.0 / 128.0); // minU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // front side
            tessellator.setNormal(0F, 0F, 1F);
            tessellator.addVertexWithUV(-0.3D,   0.5D,   -0.45D * skirtStretch, 72.0D / 256.0D, 80.0D / 128.0D); // maxU, maxV
            tessellator.addVertexWithUV(0.3D,    0.5D,   -0.45D * skirtStretch, 88.0D / 256.0D, 80.0D / 128.0D); // minU, maxV
            tessellator.addVertexWithUV(0.25D,  -0.125D, -0.125D,               88.0D / 256.0D, 64.0D / 128.0D); // minU, minV
            tessellator.addVertexWithUV(-0.25D, -0.125D, -0.125D,               72.0D / 256.0D, 64.0D / 128.0D); // maxU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // right side
            tessellator.setNormal(-1F, 0F, 0F);
            tessellator.addVertexWithUV(0.3D,   0.5D,   -0.45 * skirtStretch, 64.0 / 256.0, 80.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(0.3D,   0.5D,    0.45 * skirtStretch, 72.0 / 256.0, 80.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(0.25D, -0.125D,  0.125,               72.0 / 256.0, 64.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(0.25D, -0.125D, -0.125,               64.0 / 256.0, 64.0 / 128.0); // minU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // back side
            tessellator.setNormal(0F, 0F, -1F);
            tessellator.addVertexWithUV(0.3D,   0.5D,   0.45 * skirtStretch, 96.0 / 256.0,  80.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(-0.3D,    0.5D,   0.45 * skirtStretch, 112.0 / 256.0, 80.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(-0.25D,  -0.125D, 0.125,               112.0 / 256.0, 64.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(0.25D, -0.125D, 0.125,               96.0 / 256.0,  64.0 / 128.0); // minU, minV
            tessellator.draw();
            GL11.glPopMatrix();
        } else {
            tessellator.startDrawingQuads();            // left side
            tessellator.setNormal(1F, 0F, 0F);
            tessellator.addVertexWithUV(-0.251D,   1.45D,    -0.4,  64.0 / 256.0, 80.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(-0.251D,   1.25D,   -0.7,  72.0 / 256.0, 80.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(-0.251D,  1.25D, 0.125, 72.0 / 256.0, 64.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(-0.251D,  1.45D,  0.125, 64.0 / 256.0, 64.0 / 128.0); // minU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // top side
            tessellator.setNormal(0F, -1F, 0F);
            tessellator.addVertexWithUV(-0.251D,  1.25D, -0.7D,   72.0D / 256.0D, 80.0D / 128.0D); // maxU, maxV
            tessellator.addVertexWithUV(0.251D,   1.25D, -0.7D,   88.0D / 256.0D, 80.0D / 128.0D); // minU, maxV
            tessellator.addVertexWithUV(0.251D,  1.25D, -0.125D, 88.0D / 256.0D, 64.0D / 128.0D); // minU, minV
            tessellator.addVertexWithUV(-0.251D, 1.25D, -0.125D, 72.0D / 256.0D, 64.0D / 128.0D); // maxU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // right side
            tessellator.setNormal(-1F, 0F, 0F);
            tessellator.addVertexWithUV(0.251D,   1.45D,    -0.4,  64.0 / 256.0, 80.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(0.251D,   1.25D,   -0.7,  72.0 / 256.0, 80.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(0.251D, 1.25D, 0.125, 72.0 / 256.0, 64.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(0.251D, 1.45D,  0.125, 64.0 / 256.0, 64.0 / 128.0); // minU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // back side
            tessellator.setNormal(0F, 0F, -1F);
            tessellator.addVertexWithUV(0.251D,   1.45D,   0.125D,  96.0D / 256.0D,  66.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(-0.251D,    1.45D,   0.125D,  112.0D / 256.0D, 66.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(-0.251D,   1.375D, 0.125D, 112.0D / 256.0D, 64.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(0.251D,  1.375D, 0.125D, 96.0D / 256.0D,  64.0 / 128.0); // minU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // bottom side
            tessellator.setNormal(0F, 1F, 0F);
            tessellator.addVertexWithUV(0.251D,   1.45D,   -0.4D,  96.0 / 256.0,  80.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(-0.251D,    1.45D,   -0.4D,  112.0 / 256.0, 80.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(-0.251D,   1.45D, 0.125D, 112.0 / 256.0, 66.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(0.251D,  1.45D, 0.125D, 96.0 / 256.0,  66.0 / 128.0); // minU, minV
            tessellator.draw();
        }
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw,
                       float rotPitch, float partTicks) {
        this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);

        if( this.isCaped ) {
            GL11.glPushMatrix();                            // bipedHead
              if( !this.isSitting ) {
                  GL11.glTranslatef(0F, 0.045F, 0F);
              } else {
                  GL11.glTranslatef(0F, -0.025F, 0F);
              }
              GL11.glScalef(1.05F, 1.05F, 1.05F);
              this.bipedHead.render(partTicks);
            GL11.glPopMatrix();

            GL11.glPushMatrix();                            // bipedBody
              GL11.glTranslatef(0F, -0.009F, 0F);
              GL11.glScalef(1.05F, 1.01F, 1.05F);
              this.drawSkirt(limbSwing, limbSwingAmount, partTicks);
              this.bipedBody.render(partTicks);
            GL11.glPopMatrix();

            GL11.glPushMatrix();                            // bipedLeftArm
              GL11.glTranslatef(this.bipedLeftArm.rotationPointX * partTicks, this.bipedLeftArm.rotationPointY * partTicks,
                                this.bipedLeftArm.rotationPointZ * partTicks);
              GL11.glRotatef(this.bipedLeftArm.rotateAngleZ * (180.0F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
              GL11.glRotatef(this.bipedLeftArm.rotateAngleY * (180.0F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
              GL11.glRotatef(this.bipedLeftArm.rotateAngleX * (180.0F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
              this.bipedLeftArm.rotateAngleZ = this.bipedLeftArm.rotateAngleY = this.bipedLeftArm.rotateAngleX = 0.0F;
              GL11.glTranslatef(-0.031F, !this.isSitting ? 0.0F : -0.015F, 0.0F);
              GL11.glTranslatef(-this.bipedLeftArm.rotationPointX * partTicks, -this.bipedLeftArm.rotationPointY * partTicks,
                                -this.bipedLeftArm.rotationPointZ * partTicks);
              GL11.glScalef(1.1F, 1.01F, 1.1F);
              this.bipedLeftArm.render(partTicks);
            GL11.glPopMatrix();

            GL11.glPushMatrix();                            // bipedRightArm
              GL11.glTranslatef(this.bipedRightArm.rotationPointX * partTicks, this.bipedRightArm.rotationPointY * partTicks,
                                this.bipedRightArm.rotationPointZ * partTicks);
              GL11.glRotatef(this.bipedRightArm.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
              GL11.glRotatef(this.bipedRightArm.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
              GL11.glRotatef(this.bipedRightArm.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
              this.bipedRightArm.rotateAngleZ = this.bipedRightArm.rotateAngleY = this.bipedRightArm.rotateAngleX = 0.0F;
              GL11.glTranslatef(0.031F, !this.isSitting ? 0F : -0.015F, 0.0F);
              GL11.glTranslatef(-this.bipedRightArm.rotationPointX * partTicks, -this.bipedRightArm.rotationPointY * partTicks,
                                -this.bipedRightArm.rotationPointZ * partTicks);
              GL11.glScalef(1.1F, 1.01F, 1.1F);
              this.bipedRightArm.render(partTicks);
            GL11.glPopMatrix();

            GL11.glPushMatrix();                            // bipedLeftLeg
            GL11.glTranslatef(this.bipedLeftLeg.rotationPointX * partTicks, this.bipedLeftLeg.rotationPointY * partTicks,
                              this.bipedLeftLeg.rotationPointZ * partTicks);
            GL11.glRotatef(this.bipedLeftLeg.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(this.bipedLeftLeg.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.bipedLeftLeg.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
            this.bipedLeftLeg.rotateAngleZ = this.bipedLeftLeg.rotateAngleY = this.bipedLeftLeg.rotateAngleX = 0.0F;
            GL11.glTranslatef(-0.015F, !this.isSitting ? 0F : -0.015F, 0.0F);
            GL11.glTranslatef(-this.bipedLeftLeg.rotationPointX * partTicks, -this.bipedLeftLeg.rotationPointY * partTicks,
                              -this.bipedLeftLeg.rotationPointZ * partTicks);
            GL11.glScalef(1.1F, 1.01F, 1.1F);
            this.bipedLeftLeg.render(partTicks);
            GL11.glPopMatrix();

            GL11.glPushMatrix();                            // bipedRightLeg
              GL11.glTranslatef(this.bipedRightLeg.rotationPointX * partTicks, this.bipedRightLeg.rotationPointY * partTicks,
                                this.bipedRightLeg.rotationPointZ * partTicks);
              GL11.glRotatef(this.bipedRightLeg.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
              GL11.glRotatef(this.bipedRightLeg.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
              GL11.glRotatef(this.bipedRightLeg.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
              this.bipedRightLeg.rotateAngleZ = this.bipedRightLeg.rotateAngleY = this.bipedRightLeg.rotateAngleX = 0.0F;
              GL11.glTranslatef(0.015F, !this.isSitting ? 0F : -0.015F, 0.0F);
              GL11.glTranslatef(-this.bipedRightLeg.rotationPointX * partTicks, -this.bipedRightLeg.rotationPointY * partTicks,
                                -this.bipedRightLeg.rotationPointZ * partTicks);
              GL11.glScalef(1.1F, 1.01F, 1.1F);
              this.bipedRightLeg.render(partTicks);
            GL11.glPopMatrix();
        } else {
            this.bipedHead.render(partTicks);
            this.bipedBody.render(partTicks);
            this.bipedLeftArm.render(partTicks);
            this.bipedRightArm.render(partTicks);
            this.bipedLeftLeg.render(partTicks);
            this.bipedRightLeg.render(partTicks);
        }

        this.bipedHeadwear.render(partTicks);

    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch,
                                  float partTicks, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);

        this.bipedHead.showModel = true;

        this.bipedBody.rotateAngleX = this.bipedBodyCoat.rotateAngleX = 0.0F;
        this.bipedBody.rotationPointY = this.bipedBodyCoat.rotationPointY = -14.0F;
        this.bipedBody.rotationPointZ = this.bipedBodyCoat.rotationPointZ = 0.0F;

        this.bipedRightArm.rotateAngleX = (float) (this.bipedRightArm.rotateAngleX * 0.5D);
        this.bipedLeftArm.rotateAngleX = (float) (this.bipedLeftArm.rotateAngleX * 0.5D);
        this.bipedRightLeg.rotateAngleX = (float) (this.bipedRightLeg.rotateAngleX * 0.5D);
        this.bipedLeftLeg.rotateAngleX = (float) (this.bipedLeftLeg.rotateAngleX * 0.5D);

        if( ((EntityEnderMiss) entity).hasCoat() ) {
            this.bipedLeftArm.rotateAngleZ -= 0.08;
            this.bipedRightArm.rotateAngleZ += 0.08;
        }

        float maxLimbRotation = 0.4F;

        if( this.bipedRightArm.rotateAngleX > maxLimbRotation ) {
            this.bipedRightArm.rotateAngleX = maxLimbRotation;
        }

        if( this.bipedLeftArm.rotateAngleX > maxLimbRotation ) {
            this.bipedLeftArm.rotateAngleX = maxLimbRotation;
        }

        if( this.bipedRightArm.rotateAngleX < -maxLimbRotation ) {
            this.bipedRightArm.rotateAngleX = -maxLimbRotation;
        }

        if( this.bipedLeftArm.rotateAngleX < -maxLimbRotation ) {
            this.bipedLeftArm.rotateAngleX = -maxLimbRotation;
        }

        if( this.bipedRightLeg.rotateAngleX > maxLimbRotation ) {
            this.bipedRightLeg.rotateAngleX = maxLimbRotation;
        }

        if( this.bipedLeftLeg.rotateAngleX > maxLimbRotation ) {
            this.bipedLeftLeg.rotateAngleX = maxLimbRotation;
        }

        if( this.bipedRightLeg.rotateAngleX < -maxLimbRotation ) {
            this.bipedRightLeg.rotateAngleX = -maxLimbRotation;
        }

        if( this.bipedLeftLeg.rotateAngleX < -maxLimbRotation ) {
            this.bipedLeftLeg.rotateAngleX = -maxLimbRotation;
        }

        if( this.isCarrying ) {
            this.bipedRightArm.rotateAngleX = -0.5F;
            this.bipedLeftArm.rotateAngleX = -0.5F;
            this.bipedRightArm.rotateAngleZ = 0.05F;
            this.bipedLeftArm.rotateAngleZ = -0.05F;
        }

        if( this.isRidden ) {
            this.bipedRightArm.rotateAngleX = -(float) Math.PI;
            this.bipedLeftArm.rotateAngleX = -(float) Math.PI;
            this.bipedRightArm.rotateAngleZ = -0.1F;
            this.bipedLeftArm.rotateAngleZ = 0.1F;
        }

        this.bipedRightArm.rotationPointZ = 0.0F;
        this.bipedLeftArm.rotationPointZ = 0.0F;
        this.bipedRightLeg.rotationPointZ = 0.0F;
        this.bipedLeftLeg.rotationPointZ = 0.0F;

        this.bipedRightLeg.rotationPointY = -5.0F;
        this.bipedLeftLeg.rotationPointY = -5.0F;

        this.bipedHead.rotationPointZ = -0.0F;
        this.bipedHead.rotationPointY = -14.0F;

        this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
        this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
        this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;

        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;

        if( this.isAttacking ) {
            this.bipedHead.rotationPointY -= 1.0F * 5.0F;
        }

        if( this.isSitting ) {
            this.bipedHead.rotationPointY = 10.0F;
            this.bipedHeadwear.rotationPointY = 10.0F;
            this.bipedBody.rotationPointY = 10.0F;
            this.bipedRightArm.rotationPointY = 12.0F;
            this.bipedLeftArm.rotationPointY = 12.0F;
            this.bipedRightLeg.rotationPointY = 22.0F;
            this.bipedLeftLeg.rotationPointY = 22.0F;

            this.bipedRightArm.rotateAngleX = -(float) Math.PI / 3 - 0.1F;
            this.bipedLeftArm.rotateAngleX = -(float) Math.PI / 3 - 0.1F;
            this.bipedRightLeg.rotateAngleX = -(float) Math.PI / 2;
            this.bipedLeftLeg.rotateAngleX = -(float) Math.PI / 2;
            this.bipedRightArm.rotateAngleZ = -0.1F;
            this.bipedLeftArm.rotateAngleZ = 0.1F;

        } else {
            float var1 = -14.0F;

            this.bipedHead.rotationPointY = var1 + 0.5F;
            this.bipedHeadwear.rotationPointY = var1 + 0.5F;
            this.bipedBody.rotationPointY = var1;
            this.bipedRightArm.rotationPointY = 2.0F + var1;
            this.bipedLeftArm.rotationPointY = 2.0F + var1;
            this.bipedRightLeg.rotationPointY = 10.0F + var1;
            this.bipedLeftLeg.rotationPointY = 10.0F + var1;
        }
    }

    public void setCarrying(boolean isCarrying) {
        this.isCarrying = isCarrying;
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public void setRidden(boolean isRidden) {
        this.isRidden = isRidden;
    }

    public void setSitting(boolean isSitting) {
        this.isSitting = isSitting;
    }
}
