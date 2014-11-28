package de.sanandrew.mods.enderstuffp.client.model.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBiomeChanger
    extends ModelBase
{
    private ModelRenderer floatyBox1;
    private ModelRenderer floatyBox2;
    private ModelRenderer base;

    public ModelBiomeChanger() {
        this.base = new ModelRenderer(this, 0, 0);
        this.base.addBox(-7.0F, 0.0F, -7.0F, 14, 9, 14);

        this.floatyBox1 = new ModelRenderer(this, 16, 23);
        this.floatyBox1.addBox(-4.0F, 0.0F, -2.0F, 8, 1, 4);

        this.floatyBox2 = new ModelRenderer(this, 0, 23);
        this.floatyBox2.addBox(-2.0F, 0.0F, -4.0F, 4, 1, 8);
    }

    public void renderBlock() {
        this.base.render(0.0625F);
        GL11.glPushMatrix();
        this.floatyBox1.render(0.0625F);
        this.floatyBox2.render(0.0625F);
        GL11.glPopMatrix();
        this.floatyBox1.rotationPointY = 13.0F;
        this.floatyBox2.rotationPointY = 13.0F;
    }

    public void setBoxRotations(float angle) {
        this.floatyBox1.rotateAngleY = (float) (-angle / 180.0F * Math.PI);
        this.floatyBox2.rotateAngleY = (float) (-angle / 180.0F * Math.PI);
        this.floatyBox1.rotationPointY = (float) (13.0D + Math.sin(angle / 180 * Math.PI) * 0.5D);
        this.floatyBox2.rotationPointY = (float) (13.0D - Math.sin(angle / 180 * Math.PI) * 0.5D) + 0.001F;
    }
}
