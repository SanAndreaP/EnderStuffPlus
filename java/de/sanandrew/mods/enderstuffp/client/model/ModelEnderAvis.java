package de.sanandrew.mods.enderstuffp.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.client.SAPClientUtils;
import de.sanandrew.mods.enderstuffp.entity.living.AEntityEnderAvis;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelEnderAvis
    extends ModelBase
{
    public float[] collarColor;
    public boolean isSitting = false;
    public boolean isTamed = false;
    public boolean isSaddle = false;
    public boolean isCoat = false;

    private ModelRenderer body;
    private ModelRenderer head;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;
    private ModelRenderer rightWing;
    private ModelRenderer leftWing;

    private ModelCollar collar;

    private ModelRenderer saddleBody;

    private ModelRenderer coatBody;
    private ModelRenderer coatHead;
    private ModelRenderer coatRightLeg;
    private ModelRenderer coatLeftLeg;

    public ModelEnderAvis(boolean isSaddle, boolean isCoat) {
        this.isSaddle = isSaddle;
        this.isCoat = isCoat;

        this.textureWidth = 64;
        this.textureHeight = 32;

        this.body = SAPClientUtils.createNewBox(this, 0, 0, false, -4.0F, -3.0F, -8.0F, 8, 6, 16, 0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.head = SAPClientUtils.createNewBox(this, 32, 6, false, -2.0F, -3.0F, -3.0F, 4, 4, 6, 0.0F, -1.0F, -12.0F, 0.0F, 0.0F, 0.0F);
        this.rightWing = SAPClientUtils.createNewBox(this, 16, 24, true, -11.5F, 0.5F, 0.0F, 12, 0, 8, -4.0F, 6.0F, -5.0F, 0.0F, 1.5707963F, 0.0F);
        this.leftWing = SAPClientUtils.createNewBox(this, -8, 24, false, -0.5F, 0.5F, 0.0F, 12, 0, 8, 4.0F, 6.0F, -5.0F, 0.0F, -1.5707963F, 0.0F);
        this.rightLeg = SAPClientUtils.createNewBox(this, 2, 0, true, -0.5F, 0.0F, -0.5F, 1, 14, 1, -2.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = SAPClientUtils.createNewBox(this, 2, 0, false, -0.5F, 0.0F, -0.5F, 1, 14, 1, 2.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        this.saddleBody = SAPClientUtils.createNewBox(this, 0, 0, false, -4.0F, -3.0F, -8.0F, 8, 6, 16, 0.2F, 0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        this.coatBody = SAPClientUtils.createNewBox(this, 0, 0, false, -4.0F, -3.0F, -8.0F, 8, 6, 16, 0.1F, 0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.coatHead = SAPClientUtils.createNewBox(this, 32, 6, false, -2.0F, -3.0F, -3.0F, 4, 4, 6, 0.1F, 0.0F, -1.0F, -12.0F, 0.0F, 0.0F, 0.0F);
        this.coatRightLeg = SAPClientUtils.createNewBox(this, 2, 0, true, -0.5F, 0.0F, -0.5F, 1, 14, 1, 0.1F, -2.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.coatLeftLeg = SAPClientUtils.createNewBox(this, 2, 0, false, -0.5F, 0.0F, -0.5F, 1, 14, 1, 0.1F, 2.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        // neck
        ModelRenderer neck = SAPClientUtils.createNewBox(this, 8, 0, false, -1.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F, -2.0F, -7.0F, -2.530727F, 0.0F, 0.0F);
        this.body.addChild(neck);
        this.coatBody.addChild(SAPClientUtils.createNewBox(this, 8, 0, false, -1.0F, 0.0F, -1.0F, 2, 9, 2, 0.1F, 0.0F, -2.0F, -7.0F, -2.530727F, 0.0F, 0.0F));

        // collar
        this.collar = SAPClientUtils.createNewBox(ModelCollar.class, this, 38, 0, false, -1.0F, 3.0F, -1.8F, 2, 3, 2, 0.2F, 0.0F, 0.0F, 0.8F, 0.0F, 0.0F, 0.0F);
        neck.addChild(this.collar);

        // body tail feathers
        this.body.addChild(SAPClientUtils.createNewBox(this, -8, 0, false, 0.0F, 0.0F, 0.0F, 1, 0, 8, 0.0F, -3.0F, 8.0F, 0.5235988F, 0.5235988F, 0.3490659F));
        this.body.addChild(SAPClientUtils.createNewBox(this, -8, 0, false, -0.5F, 0.0F, 0.0F, 1, 0, 8, 0.0F, -3.0F, 8.0F, 0.5235988F, 0.0F, 0.0F));
        this.body.addChild(SAPClientUtils.createNewBox(this, -8, 0, false, -1.0F, 0.0F, 0.0F, 1, 0, 8, 0.0F, -3.0F, 8.0F, 0.5235988F, -0.5235988F, -0.3490659F));

        // head feathers
        this.head.addChild(SAPClientUtils.createNewBox(this, -4, 12, false, -0.5F, 0.0F, 4.0F, 1, 0, 4, 0.0F, 0.0F, 0.0F, 0.7853982F, 0.2617994F, 0.2617994F));
        this.head.addChild(SAPClientUtils.createNewBox(this, -4, 12, false, -0.5F, 0.0F, 4.0F, 1, 0, 4, 0.0F, 0.0F, 0.0F, 0.7853982F, 0.0F, 0.0F));
        this.head.addChild(SAPClientUtils.createNewBox(this, -4, 12, false, -0.5F, 0.0F, 4.0F, 1, 0, 4, 0.0F, 0.0F, 0.0F, 0.7853982F, -0.2617994F, -0.2617994F));

        // head beak
        this.head.addChild(SAPClientUtils.createNewBox(this, 8, 12, false, -1.0F, -2.0F, -5.0F, 2, 2, 2, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.head.addChild(SAPClientUtils.createNewBox(this, 6, 12, false, -0.5F, -1.5F, -6.0F, 1, 1, 1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        // feet
        this.rightLeg.addChild(SAPClientUtils.createNewBox(this, 27, 0, true, -1.5F, 14.0F, -3.0F, 3, 0, 5, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.leftLeg.addChild(SAPClientUtils.createNewBox(this, 27, 0, false, -1.5F, 14.0F, -3.0F, 3, 0, 5, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        // wing bones
        this.rightWing.addChild(SAPClientUtils.createNewBox(this, 0, 22, true, -12.0F, 0.0F, -1.0F, 12, 1, 1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.leftWing.addChild(SAPClientUtils.createNewBox(this, 0, 22, false, 0.0F, 0.0F, -1.0F, 12, 1, 1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
        this.setRotationAngles(limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks, entity);

        if( this.isSaddle ) {
            this.saddleBody.render(partTicks);
        } else if( this.isCoat ) {
            this.coatBody.render(partTicks);
            this.coatHead.render(partTicks);
            this.coatRightLeg.render(partTicks);
            this.coatLeftLeg.render(partTicks);
        } else {
            this.collar.isHidden = !this.isTamed;
            this.collar.color = this.collarColor;

            this.body.render(partTicks);
            this.head.render(partTicks);
            this.leftWing.render(partTicks);
            this.rightWing.render(partTicks);

            if( !this.isSitting ) {
                this.rightLeg.render(partTicks);
                this.leftLeg.render(partTicks);
            }
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks, Entity entity) {
        AEntityEnderAvis avis = (AEntityEnderAvis) entity;

        this.head.rotateAngleY = this.coatHead.rotateAngleY = (rotYaw / (180.0F / (float) Math.PI));
        this.head.rotateAngleX = this.coatHead.rotateAngleX = rotPitch / (180.0F / (float) Math.PI);

        this.rightWing.rotateAngleX = 0.0F;
        this.rightWing.rotateAngleY = avis.prevWingSpread += (avis.wingSpread - avis.prevWingSpread) * partTicks;
        this.rightWing.rotateAngleZ = (float) Math.sin(avis.prevWingRot += (avis.wingRot - avis.prevWingRot) * partTicks);
        this.leftWing.rotateAngleX = 0.0F;
        this.leftWing.rotateAngleY = -avis.prevWingSpread;
        this.leftWing.rotateAngleZ = (float) -Math.sin(avis.prevWingRot);

        if( !avis.onGround ) {
            this.rightLeg.rotateAngleX = this.coatRightLeg.rotateAngleX = 0.8F;
            this.leftLeg.rotateAngleX = this.coatLeftLeg.rotateAngleX = 0.8F;
        } else {
            this.rightLeg.rotateAngleX = this.coatRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leftLeg.rotateAngleX = this.coatLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        }
    }

    public static class ModelCollar
            extends ModelRenderer
    {
        public float[] color;

        public ModelCollar(ModelBase model, int texX, int texY) {
            super(model, texX, texY);
        }

        @Override
        public void render(float partTicks) {
            if( this.color == null ) {
                return;
            }

            GL11.glPushAttrib(GL11.GL_CURRENT_BIT);
            GL11.glColor3f(this.color[0], this.color[1], this.color[2]);
            super.render(partTicks);
            GL11.glPopAttrib();
        }
    }
}
