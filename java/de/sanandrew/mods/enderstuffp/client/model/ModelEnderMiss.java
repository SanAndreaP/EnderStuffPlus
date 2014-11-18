package de.sanandrew.mods.enderstuffp.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.client.SAPClientUtils;
import de.sanandrew.mods.enderstuffp.entity.living.EntityEnderMiss;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelEnderMiss
        extends ModelBase
{
    private boolean isCaped = false;
    private boolean isRidden = false;
    private boolean isSitting = false;

    private ModelRenderer head;
    private ModelRenderer jar;
    private ModelRenderer body;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;

    private ModelRenderer coatHead;
    private ModelRenderer coatBody;
    private ModelRenderer coatRightArm;
    private ModelRenderer coatLeftArm;
    private ModelRenderer coatRightLeg;
    private ModelRenderer coatLeftLeg;

    private double maxSkirtStrech = 0.0F;

    public ModelEnderMiss(boolean hasCape) {
        this.isCaped = hasCape;

        this.textureWidth = 128;
        this.textureHeight = 64;

        this.head = SAPClientUtils.createNewBox(this, 0, 0, false, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.jar = SAPClientUtils.createNewBox(this, 0, 16, false, -4.0F, -8.0F, -4.0F, 8, 8, 8, -0.5F, 0.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.body = SAPClientUtils.createNewBox(this, 32, 16, false, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm = SAPClientUtils.createNewBox(this, 64, 0, true, -1.0F, -2.0F, -1.0F, 2, 30, 2, -3.0F, -12.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm = SAPClientUtils.createNewBox(this, 64, 0, false, -1.0F, -2.0F, -1.0F, 2, 30, 2, 5.0F, -12.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg = SAPClientUtils.createNewBox(this, 56, 0, true, -1.0F, 0.0F, -1.0F, 2, 30, 2, -2.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = SAPClientUtils.createNewBox(this, 56, 0, false, -1.0F, 0.0F, -1.0F, 2, 30, 2, 2.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        float scale = 0.1F;
        this.coatHead = SAPClientUtils.createNewBox(this, 0, 0, false, -4.0F, -8.0F, -4.0F, 8, 8, 8, scale, 0.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.coatBody = SAPClientUtils.createNewBox(this, 32, 16, false, -4.0F, 0.0F, -2.0F, 8, 12, 4, scale, 0.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.coatRightArm = SAPClientUtils.createNewBox(this, 64, 0, true, -1.0F, -2.0F, -1.0F, 2, 30, 2, scale, -5.0F, -12.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.coatLeftArm = SAPClientUtils.createNewBox(this, 64, 0, false, -1.0F, -2.0F, -1.0F, 2, 30, 2, scale, 5.0F, -12.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.coatRightLeg = SAPClientUtils.createNewBox(this, 56, 0, true, -1.0F, 0.0F, -1.0F, 2, 30, 2, scale, -2.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.coatLeftLeg = SAPClientUtils.createNewBox(this, 56, 0, false, -1.0F, 0.0F, -1.0F, 2, 30, 2, scale, 2.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    private void drawSkirt() {
        Tessellator tessellator = Tessellator.instance;

        if( !this.isSitting ) {
            GL11.glPushMatrix();
            GL11.glRotatef(this.body.rotateAngleY * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);
            double skirtStretch = Math.max(this.rightLeg.rotateAngleX, this.leftLeg.rotateAngleX) * 1.6D + 0.3D;
            this.maxSkirtStrech = skirtStretch = Math.max(this.maxSkirtStrech, skirtStretch);

            tessellator.startDrawingQuads();            // left side
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(-0.3D, 0.5D, 0.45 * skirtStretch, 64.0 / 256.0, 80.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(-0.3D, 0.5D, -0.45 * skirtStretch, 72.0 / 256.0, 80.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(-0.25D, -0.125D, -0.125, 72.0 / 256.0, 64.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(-0.25D, -0.125D, 0.125, 64.0 / 256.0, 64.0 / 128.0); // minU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // front side
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            tessellator.addVertexWithUV(-0.3D, 0.5D, -0.45D * skirtStretch, 72.0D / 256.0D, 80.0D / 128.0D); // maxU, maxV
            tessellator.addVertexWithUV(0.3D, 0.5D, -0.45D * skirtStretch, 88.0D / 256.0D, 80.0D / 128.0D); // minU, maxV
            tessellator.addVertexWithUV(0.25D, -0.125D, -0.125D, 88.0D / 256.0D, 64.0D / 128.0D); // minU, minV
            tessellator.addVertexWithUV(-0.25D, -0.125D, -0.125D, 72.0D / 256.0D, 64.0D / 128.0D); // maxU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // right side
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(0.3D, 0.5D, -0.45 * skirtStretch, 64.0 / 256.0, 80.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(0.3D, 0.5D, 0.45 * skirtStretch, 72.0 / 256.0, 80.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(0.25D, -0.125D, 0.125, 72.0 / 256.0, 64.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(0.25D, -0.125D, -0.125, 64.0 / 256.0, 64.0 / 128.0); // minU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // back side
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            tessellator.addVertexWithUV(0.3D, 0.5D, 0.45 * skirtStretch, 96.0 / 256.0, 80.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(-0.3D, 0.5D, 0.45 * skirtStretch, 112.0 / 256.0, 80.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(-0.25D, -0.125D, 0.125, 112.0 / 256.0, 64.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(0.25D, -0.125D, 0.125, 96.0 / 256.0, 64.0 / 128.0); // minU, minV
            tessellator.draw();
            GL11.glPopMatrix();
        } else {
            double yShift = -1.57D;
            tessellator.startDrawingQuads();            // left side
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(-0.251D, 1.45D + yShift, -0.4, 64.0 / 256.0, 80.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(-0.251D, 1.25D + yShift, -0.7, 72.0 / 256.0, 80.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(-0.251D, 1.25D + yShift, 0.125, 72.0 / 256.0, 64.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(-0.251D, 1.45D + yShift, 0.125, 64.0 / 256.0, 64.0 / 128.0); // minU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // top side
            tessellator.setNormal(0.0F, -1.0F, 0.0F);
            tessellator.addVertexWithUV(-0.251D, 1.25D + yShift, -0.7D, 72.0D / 256.0D, 80.0D / 128.0D); // maxU, maxV
            tessellator.addVertexWithUV(0.251D, 1.25D + yShift, -0.7D, 88.0D / 256.0D, 80.0D / 128.0D); // minU, maxV
            tessellator.addVertexWithUV(0.251D, 1.25D + yShift, -0.125D, 88.0D / 256.0D, 64.0D / 128.0D); // minU, minV
            tessellator.addVertexWithUV(-0.251D, 1.25D + yShift, -0.125D, 72.0D / 256.0D, 64.0D / 128.0D); // maxU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // right side
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(0.251D, 1.45D + yShift, -0.4, 64.0 / 256.0, 80.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(0.251D, 1.25D + yShift, -0.7, 72.0 / 256.0, 80.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(0.251D, 1.25D + yShift, 0.125, 72.0 / 256.0, 64.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(0.251D, 1.45D + yShift, 0.125, 64.0 / 256.0, 64.0 / 128.0); // minU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // back side
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            tessellator.addVertexWithUV(0.251D, 1.45D + yShift, 0.125D, 96.0D / 256.0D, 66.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(-0.251D, 1.45D + yShift, 0.125D, 112.0D / 256.0D, 66.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(-0.251D, 1.375D + yShift, 0.125D, 112.0D / 256.0D, 64.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(0.251D, 1.375D + yShift, 0.125D, 96.0D / 256.0D, 64.0 / 128.0); // minU, minV
            tessellator.draw();

            tessellator.startDrawingQuads();            // bottom side
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            tessellator.addVertexWithUV(0.251D, 1.45D + yShift, -0.4D, 96.0 / 256.0, 80.0 / 128.0); // minU, maxV
            tessellator.addVertexWithUV(-0.251D, 1.45D + yShift, -0.4D, 112.0 / 256.0, 80.0 / 128.0); // maxU, maxV
            tessellator.addVertexWithUV(-0.251D, 1.45D + yShift, 0.125D, 112.0 / 256.0, 66.0 / 128.0); // maxU, minV
            tessellator.addVertexWithUV(0.251D, 1.45D + yShift, 0.125D, 96.0 / 256.0, 66.0 / 128.0); // minU, minV
            tessellator.draw();
        }

        this.maxSkirtStrech *= 0.97F;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);
        GL11.glPushMatrix();

        if( this.isSitting ) {
//            GL11.glTranslatef(0.0F, 23.5F, 0.0F);
        }

        if( this.isCaped ) {
            this.coatHead.render(partTicks);
            this.coatBody.render(partTicks);
            this.coatRightArm.render(partTicks);
            this.coatLeftArm.render(partTicks);
            this.coatRightLeg.render(partTicks);
            this.coatLeftLeg.render(partTicks);
            this.drawSkirt();
        } else {
            this.head.render(partTicks);
            this.jar.render(partTicks);
            this.body.render(partTicks);
            this.leftArm.render(partTicks);
            this.rightArm.render(partTicks);
            this.leftLeg.render(partTicks);
            this.rightLeg.render(partTicks);
        }

        GL11.glPopMatrix();
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks, Entity entity) {
        this.head.rotateAngleY = rotYaw / (180.0F / (float)Math.PI);
        this.head.rotateAngleX = rotPitch / (180.0F / (float)Math.PI);

        this.jar.rotateAngleY = this.head.rotateAngleY;
        this.jar.rotateAngleX = this.head.rotateAngleX;

        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.rightArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleZ = 0.0F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.0F * limbSwingAmount;
        this.rightLeg.rotateAngleY = 0.0F;
        this.leftLeg.rotateAngleY = 0.0F;

        this.rightArm.rotateAngleY = 0.0F;
        this.leftArm.rotateAngleY = 0.0F;

        if( this.onGround > -9990.0F ) {
            this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(this.onGround) * (float)Math.PI * 2.0F) * 0.2F;

            this.rightArm.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.rightArm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.leftArm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.leftArm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;

            this.rightArm.rotateAngleY += this.body.rotateAngleY;
            this.leftArm.rotateAngleY += this.body.rotateAngleY;

            float groundVal = 1.0F - this.onGround;
            groundVal *= groundVal;
            groundVal *= groundVal;
            groundVal = 1.0F - groundVal;
            float f7 = MathHelper.sin(groundVal * (float)Math.PI);
            float f8 = MathHelper.sin(this.onGround * (float)Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
            this.rightArm.rotateAngleX -= f7 * 1.2F + f8;
            this.rightArm.rotateAngleY += this.body.rotateAngleY * 2.0F;
            this.rightArm.rotateAngleZ = MathHelper.sin(this.onGround * (float)Math.PI) * -0.4F;
        }

        this.rightArm.rotateAngleZ += MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
        this.leftArm.rotateAngleZ -= MathHelper.cos(rotFloat * 0.09F) * 0.05F + 0.05F;
        this.rightArm.rotateAngleX += MathHelper.sin(rotFloat * 0.067F) * 0.05F;
        this.leftArm.rotateAngleX -= MathHelper.sin(rotFloat * 0.067F) * 0.05F;

        this.rightArm.rotateAngleX *= 0.5F;
        this.leftArm.rotateAngleX *= 0.5F;
        this.rightLeg.rotateAngleX *= 0.5F;
        this.leftLeg.rotateAngleX *= 0.5F;

        if( ((EntityEnderMiss) entity).hasCoat() ) {
            this.leftArm.rotateAngleZ -= 0.08F;
            this.rightArm.rotateAngleZ += 0.08F;
        }

        if( this.isSitting || this.isRiding ) {
            this.rightArm.rotateAngleX = -(float) Math.PI / 3 - 0.1F;
            this.leftArm.rotateAngleX = -(float) Math.PI / 3 - 0.1F;
            this.rightLeg.rotateAngleX = -(float) Math.PI / 2;
            this.leftLeg.rotateAngleX = -(float) Math.PI / 2;
            this.rightArm.rotateAngleZ = -0.1F;
            this.leftArm.rotateAngleZ = 0.1F;
        }

        if( this.isRidden ) {
            this.rightArm.rotateAngleX = -3.1415927F;
            this.leftArm.rotateAngleX = -3.1415927F;
            this.rightArm.rotateAngleZ = -0.1F;
            this.leftArm.rotateAngleZ = 0.1F;
        }

        syncRotation(this.coatHead, this.head);
        syncRotation(this.coatBody, this.body);
        syncRotation(this.coatLeftArm, this.leftArm);
        syncRotation(this.coatRightArm, this.rightArm);
        syncRotation(this.coatLeftLeg, this.leftLeg);
        syncRotation(this.coatRightLeg, this.rightLeg);
    }

    public void setRidden(boolean isRidden) {
        this.isRidden = isRidden;
    }

    public void setSitting(boolean isSitting) {
        this.isSitting = isSitting;
    }

    public void rightArmPostRender() {
        this.rightArm.postRender(0.0625F);
    }

    public void leftArmPostRender() {
        this.leftArm.postRender(0.0625F);
    }

    private static void syncRotation(ModelRenderer boxDst, ModelRenderer boxSrc) {
        boxDst.rotateAngleX = boxSrc.rotateAngleX;
        boxDst.rotateAngleY = boxSrc.rotateAngleY;
        boxDst.rotateAngleZ = boxSrc.rotateAngleZ;
    }
}
