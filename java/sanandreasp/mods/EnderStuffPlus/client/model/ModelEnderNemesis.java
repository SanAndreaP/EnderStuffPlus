package sanandreasp.mods.EnderStuffPlus.client.model;

import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEnderNemesis
    extends ModelEnderman
{
    ModelRenderer rightWing, leftWing, heart;

    public ModelEnderNemesis() {
        super();
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.rightWing = new ModelRenderer(this, 40, -2);
        this.rightWing.addBox(0F, 0F, 0F, 0, 12, 2);
        this.rightWing.setRotationPoint(-2F, -14F, 1F);
        this.rightWing.setTextureSize(64, 32);
        this.rightWing.mirror = true;
        this.setRotation(this.rightWing, -0.0523599F, 1.099557F, 0.2443461F);

        this.leftWing = new ModelRenderer(this, 32, -4);
        this.leftWing.addBox(0F, 0F, 0F, 0, 16, 4);
        this.leftWing.setRotationPoint(4F, -14F, 0F);
        this.leftWing.setTextureSize(64, 32);
        this.setRotation(this.leftWing, 0.1396263F, -0.7504916F, -0.0872665F);

        this.heart = new ModelRenderer(this, 26, 0);
        this.heart.addBox(-1F, -1F, 0F, 2, 2, 1);
        this.heart.setRotationPoint(2F, -11F, -2.5F);
        this.heart.setTextureSize(64, 32);
        this.setRotation(this.heart, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotation(this.rightWing, 0.1523599F, 0.699557F, 0.2443461F);
        this.rightWing.render(f5);
        this.leftWing.render(f5);
        this.heart.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
