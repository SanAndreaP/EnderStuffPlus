package de.sanandrew.mods.enderstuffp.client.model.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelWeatherAltar
    extends ModelBase
{
    private ModelRenderer altarBase1;
    private ModelRenderer altarBase2;
    private ModelRenderer altarBase3;
    private ModelRenderer throat;
    private ModelRenderer girder;
    private ModelRenderer stickM;
    private ModelRenderer stickR;
    private ModelRenderer stickL;
    private ModelRenderer plateM;
    private ModelRenderer plateR;
    private ModelRenderer plateL;

    public ModelWeatherAltar() {
        this.textureWidth = 128;
        this.textureHeight = 64;

        this.altarBase1 = new ModelRenderer(this, 0, 0);
        this.altarBase1.addBox(-8F, 0F, -8F, 16, 1, 16);
        this.altarBase1.setRotationPoint(0F, 23F, 0F);
        this.altarBase1.setTextureSize(128, 64);

        this.altarBase2 = new ModelRenderer(this, 0, 17);
        this.altarBase2.addBox(-6F, 0F, -6F, 12, 1, 12);
        this.altarBase2.setRotationPoint(0F, 22F, 0F);
        this.altarBase2.setTextureSize(128, 64);

        this.altarBase3 = new ModelRenderer(this, 0, 30);
        this.altarBase3.addBox(-4F, 0F, -4F, 8, 1, 8);
        this.altarBase3.setRotationPoint(0F, 21F, 0F);
        this.altarBase3.setTextureSize(128, 64);

        this.throat = new ModelRenderer(this, 0, 4);
        this.throat.addBox(-1F, -2F, -1F, 2, 3, 2);
        this.throat.setRotationPoint(0F, 20F, 0F);
        this.throat.setTextureSize(128, 64);

        this.girder = new ModelRenderer(this, 48, 0);
        this.girder.addBox(-6F, -1F, -1F, 12, 2, 2);
        this.girder.setRotationPoint(0F, 17F, 0F);
        this.girder.setTextureSize(128, 64);

        this.stickM = new ModelRenderer(this, 12, 0);
        this.stickM.addBox(-0.5F, -2F, -0.5F, 1, 3, 1);
        this.stickM.setRotationPoint(0F, 15F, 0F);
        this.stickM.setTextureSize(128, 64);

        this.stickR = new ModelRenderer(this, 12, 0);
        this.stickR.addBox(-0.5F, -2F, -0.5F, 1, 3, 1);
        this.stickR.setRotationPoint(-5F, 15F, 0F);
        this.stickR.setTextureSize(128, 64);

        this.stickL = new ModelRenderer(this, 12, 0);
        this.stickL.addBox(-0.5F, -2F, -0.4666667F, 1, 3, 1);
        this.stickL.setRotationPoint(5F, 15F, 0F);
        this.stickL.setTextureSize(128, 64);

        this.plateM = new ModelRenderer(this, 0, 0);
        this.plateM.addBox(-1.5F, 0F, -1.5F, 3, 1, 3);
        this.plateM.setRotationPoint(0F, 12F, 0F);
        this.plateM.setTextureSize(128, 64);

        this.plateR = new ModelRenderer(this, 0, 0);
        this.plateR.addBox(-1.5F, 0F, -1.5F, 3, 1, 3);
        this.plateR.setRotationPoint(-5F, 12F, 0F);
        this.plateR.setTextureSize(128, 64);

        this.plateL = new ModelRenderer(this, 0, 0);
        this.plateL.addBox(-1.5F, 0F, -1.5F, 3, 1, 3);
        this.plateL.setRotationPoint(5F, 12F, 0F);
        this.plateL.setTextureSize(128, 64);
    }

    public void renderBlock() {
        this.altarBase1.render(0.0625F);
        this.altarBase2.render(0.0625F);
        this.altarBase3.render(0.0625F);
        this.throat.render(0.0625F);
        this.girder.render(0.0625F);
        this.stickM.render(0.0625F);
        this.stickR.render(0.0625F);
        this.stickL.render(0.0625F);
        this.plateM.render(0.0625F);
        this.plateR.render(0.0625F);
        this.plateL.render(0.0625F);
    }
}
