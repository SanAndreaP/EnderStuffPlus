package sanandreasp.mods.EnderStuffPlus.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

// Date: 08.05.2013 17:53:34
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

public class ModelWeatherAltar extends ModelBase
{
  //fields
		ModelRenderer altarBase1;
		ModelRenderer altarBase2;
		ModelRenderer altarBase3;
		ModelRenderer throat;
		ModelRenderer girder;
		ModelRenderer stickM;
		ModelRenderer stickR;
		ModelRenderer stickL;
		ModelRenderer plateM;
		ModelRenderer plateR;
		ModelRenderer plateL;
  
	public ModelWeatherAltar() {
		textureWidth = 128;
		textureHeight = 64;
		
		altarBase1 = new ModelRenderer(this, 0, 0);
		altarBase1.addBox(-8F, 0F, -8F, 16, 1, 16);
		altarBase1.setRotationPoint(0F, 23F, 0F);
		altarBase1.setTextureSize(128, 64);
		altarBase2 = new ModelRenderer(this, 0, 17);
		altarBase2.addBox(-6F, 0F, -6F, 12, 1, 12);
		altarBase2.setRotationPoint(0F, 22F, 0F);
		altarBase2.setTextureSize(128, 64);
		altarBase3 = new ModelRenderer(this, 0, 30);
		altarBase3.addBox(-4F, 0F, -4F, 8, 1, 8);
		altarBase3.setRotationPoint(0F, 21F, 0F);
		altarBase3.setTextureSize(128, 64);
		throat = new ModelRenderer(this, 0, 4);
		throat.addBox(-1F, -2F, -1F, 2, 3, 2);
		throat.setRotationPoint(0F, 20F, 0F);
		throat.setTextureSize(128, 64);
		girder = new ModelRenderer(this, 48, 0);
		girder.addBox(-6F, -1F, -1F, 12, 2, 2);
		girder.setRotationPoint(0F, 17F, 0F);
		girder.setTextureSize(128, 64);
		stickM = new ModelRenderer(this, 12, 0);
		stickM.addBox(-0.5F, -2F, -0.5F, 1, 3, 1);
		stickM.setRotationPoint(0F, 15F, 0F);
		stickM.setTextureSize(128, 64);
		stickR = new ModelRenderer(this, 12, 0);
		stickR.addBox(-0.5F, -2F, -0.5F, 1, 3, 1);
		stickR.setRotationPoint(-5F, 15F, 0F);
		stickR.setTextureSize(128, 64);
		stickL = new ModelRenderer(this, 12, 0);
		stickL.addBox(-0.5F, -2F, -0.4666667F, 1, 3, 1);
		stickL.setRotationPoint(5F, 15F, 0F);
		stickL.setTextureSize(128, 64);
		plateM = new ModelRenderer(this, 0, 0);
		plateM.addBox(-1.5F, 0F, -1.5F, 3, 1, 3);
		plateM.setRotationPoint(0F, 12F, 0F);
		plateM.setTextureSize(128, 64);
		plateR = new ModelRenderer(this, 0, 0);
		plateR.addBox(-1.5F, 0F, -1.5F, 3, 1, 3);
		plateR.setRotationPoint(-5F, 12F, 0F);
		plateR.setTextureSize(128, 64);
		plateL = new ModelRenderer(this, 0, 0);
		plateL.addBox(-1.5F, 0F, -1.5F, 3, 1, 3);
		plateL.setRotationPoint(5F, 12F, 0F);
		plateL.setTextureSize(128, 64);
	}
	
	
	public void renderBlock() {
		altarBase1.render(0.0625F);
		altarBase2.render(0.0625F);
		altarBase3.render(0.0625F);
		throat.render(0.0625F);
		girder.render(0.0625F);
		stickM.render(0.0625F);
		stickR.render(0.0625F);
		stickL.render(0.0625F);
		plateM.render(0.0625F);
		plateR.render(0.0625F);
		plateL.render(0.0625F);
	}
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
  }
}
