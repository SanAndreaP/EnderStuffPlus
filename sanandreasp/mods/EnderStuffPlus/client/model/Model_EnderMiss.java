package sanandreasp.mods.EnderStuffPlus.client.model;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

public class Model_EnderMiss extends ModelBiped {
	/** Is the enderman carrying a block? */
	public boolean isCarrying = false;

	/** Is the enderman attacking an entity? */
	public boolean isAttacking = false;
	public boolean isCaped = false;
	public boolean hasAvisFeather = false;
	public boolean isRidden = false;
	public boolean isSitting = false;
	public float bowClr[] = new float[3];
	
	public ModelRenderer bipedBodyCoat;

	public Model_EnderMiss(boolean cape) {
		this(0F, cape);
	}

	public Model_EnderMiss(float f, boolean cape) {
		super(0.0F, -14.0F, 64, 32);
		
		this.isCaped = cape;
		
		float var1 = -14.0F;
		float var2 = 0.0F + f;

		this.textureWidth = 128;
		this.textureHeight = 64;

		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, var2);
		this.bipedHead.setRotationPoint(0.0F, 0.0F + var1, 0.0F);
		this.bipedHeadwear = new ModelRenderer(this, 0, 16);
		this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, var2 - 0.5F);
		this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + var1, 0.0F);
		this.bipedBody = new ModelRenderer(this, 32, 16);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, var2);
		this.bipedBody.setRotationPoint(0.0F, 0.0F + var1, 0.0F);
		this.bipedBodyCoat = new ModelRenderer(this, 32, 16);
		this.bipedBodyCoat.addBox(-4.0F, 0.0F, -2.0F, 8, 16, 4, var2);
		this.bipedBodyCoat.setRotationPoint(0.0F, 0.0F + var1, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 64, 0);
		this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, var2);
		this.bipedRightArm.setRotationPoint(-3.0F, 2.0F + var1, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 64, 0);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, var2);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + var1, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 56, 0);
		this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, var2);
		this.bipedRightLeg.setRotationPoint(-2.0F, 10.0F + var1, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, var2);
		this.bipedLeftLeg.setRotationPoint(2.0F, 10.0F + var1, 0.0F);
		this.bow = new ModelRenderer(this, 35, 0);
		this.bow.addBox(-5.0F, -13.0F, 0.0F, 10, 5, 0, var2);
		this.bow.setRotationPoint(0.0F, 0.0F + var1, 0.0F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

		if( this.isCaped ) {
			GL11.glPushMatrix();
			if( !this.isSitting )
				GL11.glTranslatef(0F, 0.045F, 0F);
			else
				GL11.glTranslatef(0F, -0.025F, 0F);
			GL11.glScalef(1.05F, 1.05F, 1.05F);
			this.bipedHead.render(par7);
			GL11.glPopMatrix();
		} else {
			this.bipedHead.render(par7);
		}
		
		if( this.isCaped ) {
			GL11.glPushMatrix();
			GL11.glTranslatef(this.bipedRightArm.rotationPointX * par7, this.bipedRightArm.rotationPointY * par7, this.bipedRightArm.rotationPointZ * par7);
			GL11.glRotatef(this.bipedRightArm.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(this.bipedRightArm.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(this.bipedRightArm.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
			this.bipedRightArm.rotateAngleZ = 
        		  this.bipedRightArm.rotateAngleY = 
        		  this.bipedRightArm.rotateAngleX = 0.0F;
			GL11.glTranslatef(0.031F, !this.isSitting ? 0F : -0.015F, 0.0F);
			GL11.glTranslatef(-this.bipedRightArm.rotationPointX * par7, -this.bipedRightArm.rotationPointY * par7, -this.bipedRightArm.rotationPointZ * par7);
    		GL11.glScalef(1.1F, 1.01F, 1.1F);
			this.bipedRightArm.render(par7);
			GL11.glPopMatrix();
		} else {
			this.bipedRightArm.render(par7);
		}
		
		if( this.isCaped ) {
			GL11.glPushMatrix();
			GL11.glTranslatef(this.bipedLeftArm.rotationPointX * par7, this.bipedLeftArm.rotationPointY * par7, this.bipedLeftArm.rotationPointZ * par7);
			GL11.glRotatef(this.bipedLeftArm.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(this.bipedLeftArm.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(this.bipedLeftArm.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
			this.bipedLeftArm.rotateAngleZ = 
        		  this.bipedLeftArm.rotateAngleY = 
        		  this.bipedLeftArm.rotateAngleX = 0.0F;
			GL11.glTranslatef(-0.031F, !this.isSitting ? 0F : -0.015F, 0.0F);
			GL11.glTranslatef(-this.bipedLeftArm.rotationPointX * par7, -this.bipedLeftArm.rotationPointY * par7, -this.bipedLeftArm.rotationPointZ * par7);
    		GL11.glScalef(1.1F, 1.01F, 1.1F);
			this.bipedLeftArm.render(par7);
			GL11.glPopMatrix();
		} else {
			this.bipedLeftArm.render(par7);
		}
		
		if( this.isCaped ) {
			GL11.glPushMatrix();
			GL11.glTranslatef(this.bipedRightLeg.rotationPointX * par7, this.bipedRightLeg.rotationPointY * par7, this.bipedRightLeg.rotationPointZ * par7);
			GL11.glRotatef(this.bipedRightLeg.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(this.bipedRightLeg.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(this.bipedRightLeg.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
			this.bipedRightLeg.rotateAngleZ = 
        		  this.bipedRightLeg.rotateAngleY = 
        		  this.bipedRightLeg.rotateAngleX = 0.0F;
			GL11.glTranslatef(0.015F, !this.isSitting ? 0F : -0.015F, 0.0F);
			GL11.glTranslatef(-this.bipedRightLeg.rotationPointX * par7, -this.bipedRightLeg.rotationPointY * par7, -this.bipedRightLeg.rotationPointZ * par7);
    		GL11.glScalef(1.1F, 1.01F, 1.1F);
			this.bipedRightLeg.render(par7);
			GL11.glPopMatrix();
		} else {
			this.bipedRightLeg.render(par7);
		}
		
		if( this.isCaped ) {
			GL11.glPushMatrix();
			GL11.glTranslatef(this.bipedLeftLeg.rotationPointX * par7, this.bipedLeftLeg.rotationPointY * par7, this.bipedLeftLeg.rotationPointZ * par7);
			GL11.glRotatef(this.bipedLeftLeg.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(this.bipedLeftLeg.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(this.bipedLeftLeg.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
			this.bipedLeftLeg.rotateAngleZ = 
        		  this.bipedLeftLeg.rotateAngleY = 
        		  this.bipedLeftLeg.rotateAngleX = 0.0F;
			GL11.glTranslatef(-0.015F, !this.isSitting ? 0F : -0.015F, 0.0F);
			GL11.glTranslatef(-this.bipedLeftLeg.rotationPointX * par7, -this.bipedLeftLeg.rotationPointY * par7, -this.bipedLeftLeg.rotationPointZ * par7);
    		GL11.glScalef(1.1F, 1.01F, 1.1F);
			this.bipedLeftLeg.render(par7);
			GL11.glPopMatrix();
		} else {
			this.bipedLeftLeg.render(par7);
		}
		
        this.bipedHeadwear.render(par7);
        
        if( this.isCaped && !this.isSitting ) {
        	GL11.glPushMatrix();
        	GL11.glTranslatef(0F, 0.0F, 0F);
    		GL11.glScalef(1.05F, 1.01F, 1.05F);
            this.bipedBodyCoat.render(par7);
            GL11.glPopMatrix();
        } else if( this.isCaped ) {
        	GL11.glPushMatrix();
        	GL11.glTranslatef(0F, -0.009F, 0F);
    		GL11.glScalef(1.05F, 1.01F, 1.05F);
            this.bipedBody.render(par7);
            GL11.glPopMatrix();
        } else {
            this.bipedBody.render(par7);
        }
        	
        
		GL11.glPushMatrix();
		if (par1Entity instanceof EntityLiving
				&& ((EntityLiving) par1Entity).hurtTime <= 0)
			GL11.glColor3f(this.bowClr[0], this.bowClr[1], this.bowClr[2]);
		this.bow.render(par7);
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glPopMatrix();
	}

	/**
	 * Sets the models various rotation angles.
	 */
	@Override
	public void setRotationAngles(float par1, float par2, float par3,
			float par4, float par5, float par6, Entity e) {
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, e);
		this.bipedHead.showModel = true;
		float var7 = -14.0F;
		this.bipedBody.rotateAngleX = this.bipedBodyCoat.rotateAngleX = 0.0F;
		this.bipedBody.rotationPointY = this.bipedBodyCoat.rotationPointY = var7;
		this.bipedBody.rotationPointZ = this.bipedBodyCoat.rotationPointZ = 0.0F;
		this.bipedRightArm.rotateAngleX = (float) (this.bipedRightArm.rotateAngleX * 0.5D);
		this.bipedLeftArm.rotateAngleX = (float) (this.bipedLeftArm.rotateAngleX * 0.5D);
		this.bipedRightLeg.rotateAngleX = (float) (this.bipedRightLeg.rotateAngleX * 0.5D);
		this.bipedLeftLeg.rotateAngleX = (float) (this.bipedLeftLeg.rotateAngleX * 0.5D);
		float var8 = 0.4F;

		if( this.bipedRightArm.rotateAngleX > var8 ) {
			this.bipedRightArm.rotateAngleX = var8;
		}

		if( this.bipedLeftArm.rotateAngleX > var8 ) {
			this.bipedLeftArm.rotateAngleX = var8;
		}

		if( this.bipedRightArm.rotateAngleX < -var8 ) {
			this.bipedRightArm.rotateAngleX = -var8;
		}

		if( this.bipedLeftArm.rotateAngleX < -var8 ) {
			this.bipedLeftArm.rotateAngleX = -var8;
		}

		if( this.bipedRightLeg.rotateAngleX > var8 ) {
			this.bipedRightLeg.rotateAngleX = var8;
		}

		if( this.bipedLeftLeg.rotateAngleX > var8 ) {
			this.bipedLeftLeg.rotateAngleX = var8;
		}

		if( this.bipedRightLeg.rotateAngleX < -var8 ) {
			this.bipedRightLeg.rotateAngleX = -var8;
		}

		if( this.bipedLeftLeg.rotateAngleX < -var8 ) {
			this.bipedLeftLeg.rotateAngleX = -var8;
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
		this.bipedRightLeg.rotationPointY = 9.0F + var7;
		this.bipedLeftLeg.rotationPointY = 9.0F + var7;
		this.bipedHead.rotationPointZ = -0.0F;
		this.bipedHead.rotationPointY = var7 + 1.0F;
		this.bow.rotationPointX = this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
		this.bow.rotationPointY = this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
		this.bow.rotationPointZ = this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
		this.bow.rotateAngleX = this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
		this.bow.rotateAngleY = this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
		this.bow.rotateAngleZ = this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;

		if( this.isAttacking ) {
			float var9 = 1.0F;
			this.bow.rotationPointY = this.bipedHead.rotationPointY -= var9 * 5.0F;
		}

		if( this.isSitting ) {
			float var1 = 10.0F;

			this.bipedHead.rotationPointY = var1;
			this.bipedHeadwear.rotationPointY = var1;
			this.bipedBody.rotationPointY = var1;
			this.bipedRightArm.rotationPointY = 2.0F + var1;
			this.bipedLeftArm.rotationPointY = 2.0F + var1;
			this.bipedRightLeg.rotationPointY = 12.0F + var1;
			this.bipedLeftLeg.rotationPointY = 12.0F + var1;
			this.bow.rotationPointY = var1;

			this.bipedRightArm.rotateAngleX = -(float) Math.PI / 3 - 0.1F;
			this.bipedLeftArm.rotateAngleX = -(float) Math.PI / 3 - 0.1F;
			this.bipedRightLeg.rotateAngleX = -(float) Math.PI / 2;
			this.bipedLeftLeg.rotateAngleX = -(float) Math.PI / 2;
			this.bipedRightArm.rotateAngleZ = -0.1F;
			this.bipedLeftArm.rotateAngleZ = 0.1F;

		} else {
			float var1 = -14.0F;

			this.bipedHead.rotationPointY = var1;
			this.bipedHeadwear.rotationPointY = var1;
			this.bipedBody.rotationPointY = var1;
			this.bipedRightArm.rotationPointY = 2.0F + var1;
			this.bipedLeftArm.rotationPointY = 2.0F + var1;
			this.bipedRightLeg.rotationPointY = 10.0F + var1;
			this.bipedLeftLeg.rotationPointY = 10.0F + var1;
			this.bow.rotationPointY = var1;
		}
	}

	public ModelRenderer bow;
}
