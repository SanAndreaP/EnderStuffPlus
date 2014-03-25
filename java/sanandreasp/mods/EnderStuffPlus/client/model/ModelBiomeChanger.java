package sanandreasp.mods.EnderStuffPlus.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBiomeChanger
    extends ModelBase
{
    public ModelRenderer floatyBox1;
    public ModelRenderer floatyBox2;
    private ModelRenderer base;

    public ModelBiomeChanger() {
        this.base = new ModelRenderer(this, 0, 0);
        this.base.addBox(-7F, 0F, -7F, 14, 9, 14);

        this.floatyBox1 = new ModelRenderer(this, 16, 23);
        this.floatyBox1.addBox(-4F, 0F, -2F, 8, 1, 4);

        this.floatyBox2 = new ModelRenderer(this, 0, 23);
        this.floatyBox2.addBox(-2F, 0F, -4F, 4, 1, 8);
    }

    public void renderBlock() {
        this.base.render(0.0625F);
        GL11.glPushMatrix();
        this.floatyBox1.render(0.0625F);
        this.floatyBox2.render(0.0625F);
        GL11.glPopMatrix();
        this.floatyBox1.rotationPointY = 13F;
        this.floatyBox2.rotationPointY = 13F;
    }
}
