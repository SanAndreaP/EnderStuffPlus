// Date: 12.12.2014 09:46:41
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package net.minecraft.src;

public class ModelModelOreGenerator extends ModelBase
{
  //fields
    ModelRenderer stanceBackLeft;
    ModelRenderer stanceBackRight;
    ModelRenderer stanceFrontLeft;
    ModelRenderer stanceFrontRight;
    ModelRenderer grinderAxis1;
    ModelRenderer grinderP10;
    ModelRenderer grinderP11;
    ModelRenderer grinderP12;
    ModelRenderer grinderP13;
    ModelRenderer grinderP14;
    ModelRenderer grinderP15;
    ModelRenderer grinderP16;
    ModelRenderer grinderP17;
    ModelRenderer grinderP18;
    ModelRenderer grinderP19;
    ModelRenderer grinderAxis2;
    ModelRenderer grinderP20;
    ModelRenderer grinderP21;
    ModelRenderer grinderP22;
    ModelRenderer grinderP23;
    ModelRenderer grinderP24;
    ModelRenderer grinderP25;
    ModelRenderer grinderP26;
    ModelRenderer grinderP27;
    ModelRenderer grinderP28;
    ModelRenderer grinderP29;
    ModelRenderer sideLeft;
    ModelRenderer sideRight;
    ModelRenderer ramp;
    ModelRenderer storage;
    ModelRenderer sieveBottom;
    ModelRenderer sieveTop;
    ModelRenderer reinfBarBottomBackT;
    ModelRenderer reinfBarBottomBackB;
    ModelRenderer reinfBarBottomFrontT;
    ModelRenderer reinfBarBottomFrontB;
    ModelRenderer reinfBarTopBack;
    ModelRenderer reinfBarBottomRightB;
    ModelRenderer reinfBarBottomRightT;
    ModelRenderer reinfBarBottomLeftT;
    ModelRenderer reinfBarBottomLeftB;
  
  public ModelModelOreGenerator()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      stanceBackLeft = new ModelRenderer(this, 0, 4);
      stanceBackLeft.addBox(7F, 9F, 7F, 1, 15, 1);
      stanceBackLeft.setRotationPoint(0F, 0F, 0F);
      stanceBackLeft.setTextureSize(64, 64);
      stanceBackLeft.mirror = true;
      setRotation(stanceBackLeft, 0F, 0F, 0F);
      stanceBackRight = new ModelRenderer(this, 0, 4);
      stanceBackRight.addBox(-8F, 9F, 7F, 1, 15, 1);
      stanceBackRight.setRotationPoint(0F, 0F, 0F);
      stanceBackRight.setTextureSize(64, 64);
      stanceBackRight.mirror = true;
      setRotation(stanceBackRight, 0F, 0F, 0F);
      stanceFrontLeft = new ModelRenderer(this, 0, 4);
      stanceFrontLeft.addBox(7F, 10F, -8F, 1, 14, 1);
      stanceFrontLeft.setRotationPoint(0F, 0F, 0F);
      stanceFrontLeft.setTextureSize(64, 64);
      stanceFrontLeft.mirror = true;
      setRotation(stanceFrontLeft, 0F, 0F, 0F);
      stanceFrontRight = new ModelRenderer(this, 0, 4);
      stanceFrontRight.addBox(-8F, 10F, -8F, 1, 14, 1);
      stanceFrontRight.setRotationPoint(0F, 0F, 0F);
      stanceFrontRight.setTextureSize(64, 64);
      stanceFrontRight.mirror = true;
      setRotation(stanceFrontRight, 0F, 0F, 0F);
      grinderAxis1 = new ModelRenderer(this, 0, 22);
      grinderAxis1.addBox(-7F, -0.5F, -0.5F, 14, 1, 1);
      grinderAxis1.setRotationPoint(0F, 14F, 2F);
      grinderAxis1.setTextureSize(64, 64);
      grinderAxis1.mirror = true;
      setRotation(grinderAxis1, 0F, 0F, 0F);
      grinderP10 = new ModelRenderer(this, 0, 0);
      grinderP10.addBox(-5F, -1F, -1F, 1, 2, 2);
      grinderP10.setRotationPoint(0F, 14F, 2F);
      grinderP10.setTextureSize(64, 64);
      grinderP10.mirror = true;
      setRotation(grinderP10, 0F, 0F, 0F);
      grinderP11 = new ModelRenderer(this, 0, 0);
      grinderP11.addBox(-4F, -1F, -1F, 1, 2, 2);
      grinderP11.setRotationPoint(0F, 14F, 2F);
      grinderP11.setTextureSize(64, 64);
      grinderP11.mirror = true;
      setRotation(grinderP11, 0.7853982F, 0F, 0F);
      grinderP12 = new ModelRenderer(this, 0, 0);
      grinderP12.addBox(-3F, -1F, -1F, 1, 2, 2);
      grinderP12.setRotationPoint(0F, 14F, 2F);
      grinderP12.setTextureSize(64, 64);
      grinderP12.mirror = true;
      setRotation(grinderP12, 0F, 0F, 0F);
      grinderP13 = new ModelRenderer(this, 0, 0);
      grinderP13.addBox(-2F, -1F, -1F, 1, 2, 2);
      grinderP13.setRotationPoint(0F, 14F, 2F);
      grinderP13.setTextureSize(64, 64);
      grinderP13.mirror = true;
      setRotation(grinderP13, 0.7853982F, 0F, 0F);
      grinderP14 = new ModelRenderer(this, 0, 0);
      grinderP14.addBox(-1F, -1F, -1F, 1, 2, 2);
      grinderP14.setRotationPoint(0F, 14F, 2F);
      grinderP14.setTextureSize(64, 64);
      grinderP14.mirror = true;
      setRotation(grinderP14, 0F, 0F, 0F);
      grinderP15 = new ModelRenderer(this, 0, 0);
      grinderP15.addBox(0F, -1F, -1F, 1, 2, 2);
      grinderP15.setRotationPoint(0F, 14F, 2F);
      grinderP15.setTextureSize(64, 64);
      grinderP15.mirror = true;
      setRotation(grinderP15, 0.7853982F, 0F, 0F);
      grinderP16 = new ModelRenderer(this, 0, 0);
      grinderP16.addBox(1F, -1F, -1F, 1, 2, 2);
      grinderP16.setRotationPoint(0F, 14F, 2F);
      grinderP16.setTextureSize(64, 64);
      grinderP16.mirror = true;
      setRotation(grinderP16, 0F, 0F, 0F);
      grinderP17 = new ModelRenderer(this, 0, 0);
      grinderP17.addBox(2F, -1F, -1F, 1, 2, 2);
      grinderP17.setRotationPoint(0F, 14F, 2F);
      grinderP17.setTextureSize(64, 64);
      grinderP17.mirror = true;
      setRotation(grinderP17, 0.7853982F, 0F, 0F);
      grinderP18 = new ModelRenderer(this, 0, 0);
      grinderP18.addBox(3F, -1F, -1F, 1, 2, 2);
      grinderP18.setRotationPoint(0F, 14F, 2F);
      grinderP18.setTextureSize(64, 64);
      grinderP18.mirror = true;
      setRotation(grinderP18, 0F, 0F, 0F);
      grinderP19 = new ModelRenderer(this, 0, 0);
      grinderP19.addBox(4F, -1F, -1F, 1, 2, 2);
      grinderP19.setRotationPoint(0F, 14F, 2F);
      grinderP19.setTextureSize(64, 64);
      grinderP19.mirror = true;
      setRotation(grinderP19, 0.7853982F, 0F, 0F);
      grinderAxis2 = new ModelRenderer(this, 0, 22);
      grinderAxis2.addBox(-7F, -0.5F, -0.5F, 14, 1, 1);
      grinderAxis2.setRotationPoint(0F, 15.75F, 0.25F);
      grinderAxis2.setTextureSize(64, 64);
      grinderAxis2.mirror = true;
      setRotation(grinderAxis2, 0.7853982F, 0F, 0F);
      grinderP20 = new ModelRenderer(this, 0, 0);
      grinderP20.addBox(-5F, -1F, -1F, 1, 2, 2);
      grinderP20.setRotationPoint(0F, 15.75F, 0.25F);
      grinderP20.setTextureSize(64, 64);
      grinderP20.mirror = true;
      setRotation(grinderP20, 0.7853982F, 0F, 0F);
      grinderP21 = new ModelRenderer(this, 0, 0);
      grinderP21.addBox(-4F, -1F, -1F, 1, 2, 2);
      grinderP21.setRotationPoint(0F, 15.75F, 0.25F);
      grinderP21.setTextureSize(64, 64);
      grinderP21.mirror = true;
      setRotation(grinderP21, 1.570796F, 0F, 0F);
      grinderP22 = new ModelRenderer(this, 0, 0);
      grinderP22.addBox(-3F, -1F, -1F, 1, 2, 2);
      grinderP22.setRotationPoint(0F, 15.75F, 0.25F);
      grinderP22.setTextureSize(64, 64);
      grinderP22.mirror = true;
      setRotation(grinderP22, 0.7853982F, 0F, 0F);
      grinderP23 = new ModelRenderer(this, 0, 0);
      grinderP23.addBox(-2F, -1F, -1F, 1, 2, 2);
      grinderP23.setRotationPoint(0F, 15.75F, 0.25F);
      grinderP23.setTextureSize(64, 64);
      grinderP23.mirror = true;
      setRotation(grinderP23, 1.570796F, 0F, 0F);
      grinderP24 = new ModelRenderer(this, 0, 0);
      grinderP24.addBox(-1F, -1F, -1F, 1, 2, 2);
      grinderP24.setRotationPoint(0F, 15.75F, 0.25F);
      grinderP24.setTextureSize(64, 64);
      grinderP24.mirror = true;
      setRotation(grinderP24, 0.7853982F, 0F, 0F);
      grinderP25 = new ModelRenderer(this, 0, 0);
      grinderP25.addBox(0F, -1F, -1F, 1, 2, 2);
      grinderP25.setRotationPoint(0F, 15.75F, 0.25F);
      grinderP25.setTextureSize(64, 64);
      grinderP25.mirror = true;
      setRotation(grinderP25, 1.570796F, 0F, 0F);
      grinderP26 = new ModelRenderer(this, 0, 0);
      grinderP26.addBox(1F, -1F, -1F, 1, 2, 2);
      grinderP26.setRotationPoint(0F, 15.75F, 0.25F);
      grinderP26.setTextureSize(64, 64);
      grinderP26.mirror = true;
      setRotation(grinderP26, 0.7853982F, 0F, 0F);
      grinderP27 = new ModelRenderer(this, 0, 0);
      grinderP27.addBox(2F, -1F, -1F, 1, 2, 2);
      grinderP27.setRotationPoint(0F, 15.75F, 0.25F);
      grinderP27.setTextureSize(64, 64);
      grinderP27.mirror = true;
      setRotation(grinderP27, 1.570796F, 0F, 0F);
      grinderP28 = new ModelRenderer(this, 0, 0);
      grinderP28.addBox(3F, -1F, -1F, 1, 2, 2);
      grinderP28.setRotationPoint(0F, 15.75F, 0.25F);
      grinderP28.setTextureSize(64, 64);
      grinderP28.mirror = true;
      setRotation(grinderP28, 0.7853982F, 0F, 0F);
      grinderP29 = new ModelRenderer(this, 0, 0);
      grinderP29.addBox(4F, -1F, -1F, 1, 2, 2);
      grinderP29.setRotationPoint(0F, 15.75F, 0.25F);
      grinderP29.setTextureSize(64, 64);
      grinderP29.mirror = true;
      setRotation(grinderP29, 1.570796F, 0F, 0F);
      sideLeft = new ModelRenderer(this, 4, 0);
      sideLeft.addBox(5.466667F, 10.46667F, -7.466667F, 2, 7, 15);
      sideLeft.setRotationPoint(0F, 0F, 0F);
      sideLeft.setTextureSize(64, 64);
      sideLeft.mirror = true;
      setRotation(sideLeft, 0F, 0F, 0F);
      sideRight.mirror = true;
      sideRight = new ModelRenderer(this, 4, 0);
      sideRight.addBox(-7.533333F, 10.46667F, -7.466667F, 2, 7, 15);
      sideRight.setRotationPoint(0F, 0F, 0F);
      sideRight.setTextureSize(64, 64);
      sideRight.mirror = true;
      setRotation(sideRight, 0F, 0F, 0F);
      sideRight.mirror = false;
      ramp = new ModelRenderer(this, 30, 22);
      ramp.addBox(-7F, 11.46667F, 4.25F, 14, 1, 3);
      ramp.setRotationPoint(0F, 0F, 0F);
      ramp.setTextureSize(64, 64);
      ramp.mirror = true;
      setRotation(ramp, -0.6108652F, 0F, 0F);
      storage = new ModelRenderer(this, 0, 41);
      storage.addBox(-7F, 9F, -8F, 14, 8, 5);
      storage.setRotationPoint(0F, 0F, 0F);
      storage.setTextureSize(64, 64);
      storage.mirror = true;
      setRotation(storage, 0F, 0F, 0F);
      sieveBottom = new ModelRenderer(this, 15, 26);
      sieveBottom.addBox(-7.466667F, 21F, -7.466667F, 15, 0, 15);
      sieveBottom.setRotationPoint(0F, 0F, 0F);
      sieveBottom.setTextureSize(64, 64);
      sieveBottom.mirror = true;
      setRotation(sieveBottom, 0F, 0F, 0F);
      sieveTop = new ModelRenderer(this, -15, 26);
      sieveTop.addBox(-7.466667F, 19F, -7.466667F, 15, 0, 15);
      sieveTop.setRotationPoint(0F, 0F, 0F);
      sieveTop.setTextureSize(64, 64);
      sieveTop.mirror = true;
      setRotation(sieveTop, 0F, 0F, 0F);
      reinfBarBottomBackT = new ModelRenderer(this, 0, 24);
      reinfBarBottomBackT.addBox(-7F, 18.5F, 7F, 14, 1, 1);
      reinfBarBottomBackT.setRotationPoint(0F, 0F, 0F);
      reinfBarBottomBackT.setTextureSize(64, 64);
      reinfBarBottomBackT.mirror = true;
      setRotation(reinfBarBottomBackT, 0F, 0F, 0F);
      reinfBarBottomBackB = new ModelRenderer(this, 0, 24);
      reinfBarBottomBackB.addBox(-7F, 20.5F, 7F, 14, 1, 1);
      reinfBarBottomBackB.setRotationPoint(0F, 0F, 0F);
      reinfBarBottomBackB.setTextureSize(64, 64);
      reinfBarBottomBackB.mirror = true;
      setRotation(reinfBarBottomBackB, 0F, 0F, 0F);
      reinfBarBottomFrontT = new ModelRenderer(this, 0, 24);
      reinfBarBottomFrontT.addBox(-7F, 18.5F, -8F, 14, 1, 1);
      reinfBarBottomFrontT.setRotationPoint(0F, 0F, 0F);
      reinfBarBottomFrontT.setTextureSize(64, 64);
      reinfBarBottomFrontT.mirror = true;
      setRotation(reinfBarBottomFrontT, 0F, 0F, 0F);
      reinfBarBottomFrontB = new ModelRenderer(this, 0, 24);
      reinfBarBottomFrontB.addBox(-7F, 20.5F, -8F, 14, 1, 1);
      reinfBarBottomFrontB.setRotationPoint(0F, 0F, 0F);
      reinfBarBottomFrontB.setTextureSize(64, 64);
      reinfBarBottomFrontB.mirror = true;
      setRotation(reinfBarBottomFrontB, 0F, 0F, 0F);
      reinfBarTopBack = new ModelRenderer(this, 0, 24);
      reinfBarTopBack.addBox(-7F, 8.5F, 7F, 14, 1, 1);
      reinfBarTopBack.setRotationPoint(0F, 0F, 0F);
      reinfBarTopBack.setTextureSize(64, 64);
      reinfBarTopBack.mirror = true;
      setRotation(reinfBarTopBack, 0F, 0F, 0F);
      reinfBarBottomRightB = new ModelRenderer(this, 0, 24);
      reinfBarBottomRightB.addBox(-7F, 20.5F, -8F, 14, 1, 1);
      reinfBarBottomRightB.setRotationPoint(0F, 0F, 0F);
      reinfBarBottomRightB.setTextureSize(64, 64);
      reinfBarBottomRightB.mirror = true;
      setRotation(reinfBarBottomRightB, 0F, 1.570796F, 0F);
      reinfBarBottomRightT = new ModelRenderer(this, 0, 24);
      reinfBarBottomRightT.addBox(-7F, 18.5F, -8F, 14, 1, 1);
      reinfBarBottomRightT.setRotationPoint(0F, 0F, 0F);
      reinfBarBottomRightT.setTextureSize(64, 64);
      reinfBarBottomRightT.mirror = true;
      setRotation(reinfBarBottomRightT, 0F, 1.570796F, 0F);
      reinfBarBottomLeftT = new ModelRenderer(this, 0, 24);
      reinfBarBottomLeftT.addBox(-7F, 18.5F, -8F, 14, 1, 1);
      reinfBarBottomLeftT.setRotationPoint(0F, 0F, 0F);
      reinfBarBottomLeftT.setTextureSize(64, 64);
      reinfBarBottomLeftT.mirror = true;
      setRotation(reinfBarBottomLeftT, 0F, -1.570796F, 0F);
      reinfBarBottomLeftB = new ModelRenderer(this, 0, 24);
      reinfBarBottomLeftB.addBox(-7F, 20.5F, -8F, 14, 1, 1);
      reinfBarBottomLeftB.setRotationPoint(0F, 0F, 0F);
      reinfBarBottomLeftB.setTextureSize(64, 64);
      reinfBarBottomLeftB.mirror = true;
      setRotation(reinfBarBottomLeftB, 0F, -1.570796F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    stanceBackLeft.render(f5);
    stanceBackRight.render(f5);
    stanceFrontLeft.render(f5);
    stanceFrontRight.render(f5);
    grinderAxis1.render(f5);
    grinderP10.render(f5);
    grinderP11.render(f5);
    grinderP12.render(f5);
    grinderP13.render(f5);
    grinderP14.render(f5);
    grinderP15.render(f5);
    grinderP16.render(f5);
    grinderP17.render(f5);
    grinderP18.render(f5);
    grinderP19.render(f5);
    grinderAxis2.render(f5);
    grinderP20.render(f5);
    grinderP21.render(f5);
    grinderP22.render(f5);
    grinderP23.render(f5);
    grinderP24.render(f5);
    grinderP25.render(f5);
    grinderP26.render(f5);
    grinderP27.render(f5);
    grinderP28.render(f5);
    grinderP29.render(f5);
    sideLeft.render(f5);
    sideRight.render(f5);
    ramp.render(f5);
    storage.render(f5);
    sieveBottom.render(f5);
    sieveTop.render(f5);
    reinfBarBottomBackT.render(f5);
    reinfBarBottomBackB.render(f5);
    reinfBarBottomFrontT.render(f5);
    reinfBarBottomFrontB.render(f5);
    reinfBarTopBack.render(f5);
    reinfBarBottomRightB.render(f5);
    reinfBarBottomRightT.render(f5);
    reinfBarBottomLeftT.render(f5);
    reinfBarBottomLeftB.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5);
  }

}