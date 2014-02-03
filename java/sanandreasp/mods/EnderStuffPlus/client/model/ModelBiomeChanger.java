package sanandreasp.mods.EnderStuffPlus.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBiomeChanger extends ModelBase
{
	public ModelRenderer Box;
	public ModelRenderer Shape1;
	public ModelRenderer Shape2;
	
	public ModelBiomeChanger() {
		this.Box = new ModelRenderer(this, 0, 0);
		this.Box.addBox(-7F, 0F, -7F, 14, 9, 14);
		
		this.Shape1 = new ModelRenderer(this, 16, 23);
		this.Shape1.addBox(-4F, 0F, -2F, 8, 1, 4);
		
		this.Shape2 = new ModelRenderer(this, 0, 23);
		this.Shape2.addBox(-2F, 0F, -4F, 4, 1, 8);
	}
	
	public void renderBlock() {
		this.Box.render(0.0625F);
		GL11.glPushMatrix();
		this.Shape1.render(0.0625F);
		this.Shape2.render(0.0625F);
		GL11.glPopMatrix();
		this.Shape1.rotationPointY = 13F;
		this.Shape2.rotationPointY = 13F;
	}
}
