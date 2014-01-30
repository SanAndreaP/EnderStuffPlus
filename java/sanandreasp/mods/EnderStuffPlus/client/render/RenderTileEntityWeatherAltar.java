package sanandreasp.mods.EnderStuffPlus.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import sanandreasp.mods.EnderStuffPlus.client.model.ModelWeatherAltar;
import sanandreasp.mods.EnderStuffPlus.client.registry.IconRegistry;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;

@SideOnly(Side.CLIENT)
public class RenderTileEntityWeatherAltar extends TileEntitySpecialRenderer implements Textures {
	
	ModelWeatherAltar modelBlock = new ModelWeatherAltar();
	
	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
		TileEntityWeatherAltar te = ((TileEntityWeatherAltar)var1);
		
		this.bindTexture(WEATHERALTAR_TEXTURE);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2 + 0.5F, (float)var4+1.5F, (float)var6 + 0.5F);
        GL11.glRotatef(180F, 1F, 0F, 0F);
        GL11.glRotatef((float)te.blockMetadata * 90F, 0F, 1F, 0F);
        
        this.modelBlock.renderBlock();
        
        GL11.glRotatef(-180F, 1F, 0F, 0F);
        
        GL11.glScalef(0.25F, 0.25F, 0.25F);
        GL11.glTranslatef(0.0F, -2.5F, 0.0F);

        GL11.glTranslatef(1.25F, 0F, 0F);
        

        float sunFloat = 0.5F - (te.prevItemFloat[0] + (te.itemFloat[0] - te.prevItemFloat[0]) * var8) * 0.5F;
        float rainFloat = 0.5F - (te.prevItemFloat[1] + (te.itemFloat[1] - te.prevItemFloat[1]) * var8) * 0.5F;
        float stormFloat = 0.5F - (te.prevItemFloat[2] + (te.itemFloat[2] - te.prevItemFloat[2]) * var8) * 0.5F;
        
        RenderHelper.disableStandardItemLighting();
    	GL11.glTranslatef(0F, -sunFloat, 0F);
    	this.renderIcon(IconRegistry.sun, 1F - sunFloat, 1F - sunFloat, 1F - sunFloat);
    	GL11.glTranslatef(0F, sunFloat, 0F);
    	GL11.glTranslatef(-1.25F, -rainFloat, 0F);
    	this.renderIcon(IconRegistry.rain, 1F - rainFloat, 1F - rainFloat, 1F - rainFloat);
    	GL11.glTranslatef(0F, rainFloat, 0F);
    	GL11.glTranslatef(-1.25F, -stormFloat, 0F);
    	this.renderIcon(IconRegistry.thunder, 1F - stormFloat, 1F - stormFloat, 1F - stormFloat);
    	GL11.glTranslatef(0F, stormFloat, 0F);
        RenderHelper.enableStandardItemLighting();
            
        GL11.glPopMatrix();
	}
	
	private void renderIcon(Icon par2Icon, float par5, float par6, float par7)
    {
        Tessellator tessellator = Tessellator.instance;

        if( par2Icon == null )
        {
            par2Icon = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getTextureExtry("missingno");
        }

        float f4 = par2Icon.getMinU();
        float f5 = par2Icon.getMaxU();
        float f6 = par2Icon.getMinV();
        float f7 = par2Icon.getMaxV();
        float f9 = 0.5F;
        float f10 = 0.25F;
        float f11;

        GL11.glPushMatrix();

        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);

        float f12 = 0.0625F;
        f11 = 0.021875F;

        GL11.glTranslatef(-f9, -f10, -((f12 + f11) * 0.5F));

        GL11.glTranslatef(0f, 0f, f12 + f11);
        this.bindTexture(TextureMap.locationItemsTexture);

        GL11.glColor4f(par5, par6, par7, 1.0F);
        ItemRenderer.renderItemIn2D(tessellator, f5, f6, f4, f7, par2Icon.getIconWidth(), par2Icon.getIconHeight(), f12);

        GL11.glPopMatrix();
    }
}
