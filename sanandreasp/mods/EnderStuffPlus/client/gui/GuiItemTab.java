package sanandreasp.mods.EnderStuffPlus.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.google.common.base.Optional;

import sanandreasp.core.manpack.helpers.ItemRenderHelper;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class GuiItemTab extends GuiButton implements Textures {
	
	protected Icon renderedIcon;
	
    protected boolean isRight;
    
    protected boolean hasEffect;
    
    public ResourceLocation baseTexture = new ResourceLocation("textures/atlas/items.png");
    
    public int textureBaseX = 0;
    public int textureBaseY = 0;

	public GuiItemTab(int id, int xPos, int yPos, Icon icon, String name, boolean right, boolean hasEff) {
		super(id, xPos, yPos, name);
		this.width = 26;
		this.height = 26;
		this.renderedIcon = icon;
		this.isRight = right;
		this.hasEffect = hasEff;
	}
	
	public void setIcon(Icon ico) {
		this.renderedIcon = ico;
	}
	
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if( this.drawButton )
        {
        	FontRenderer var4 = par1Minecraft.fontRenderer;
        	par1Minecraft.getTextureManager().bindTexture(GUI_BUTTONS);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int var5 = this.getHoverState(this.field_82253_i);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, this.textureBaseX+26*(isRight?0:1), this.textureBaseY+var5*26, this.width, this.height);
            this.mouseDragged(par1Minecraft, par2, par3);
            int var6 = 14737632;

            if( !this.enabled )
            {
                var6 = -6250336;
            }
            else if( this.field_82253_i )
            {
                var6 = 16777120;
            }

            this.zLevel = 300.0F;
            this.drawIcon(this.renderedIcon, this.xPosition + 5, this.yPosition + 5, par1Minecraft, null);
            this.zLevel = 0.0F;

            if( this.field_82253_i )
            	this.drawTabHoveringText(this.displayString, this.xPosition - (this.isRight ? var4.getStringWidth(this.displayString) + 5 : - 5), this.yPosition + 21, var4);

            RenderHelper.disableStandardItemLighting();
        }
    }
    
    protected void drawTabHoveringText(String par1Str, int par2, int par3, FontRenderer fontRenderer)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        int var4 = fontRenderer.getStringWidth(par1Str);
        int var5 = par2 + 12;
        int var6 = par3 - 12;
        byte var8 = 8;
        this.zLevel = 300.0F;
        int var9 = -267386864;
        this.drawGradientRect(var5 - 3, var6 - 4, var5 + var4 + 3, var6 - 3, var9, var9);
        this.drawGradientRect(var5 - 3, var6 + var8 + 3, var5 + var4 + 3, var6 + var8 + 4, var9, var9);
        this.drawGradientRect(var5 - 3, var6 - 3, var5 + var4 + 3, var6 + var8 + 3, var9, var9);
        this.drawGradientRect(var5 - 4, var6 - 3, var5 - 3, var6 + var8 + 3, var9, var9);
        this.drawGradientRect(var5 + var4 + 3, var6 - 3, var5 + var4 + 4, var6 + var8 + 3, var9, var9);
        int var10 = 1347420415;
        int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
        this.drawGradientRect(var5 - 3, var6 - 3 + 1, var5 - 3 + 1, var6 + var8 + 3 - 1, var10, var11);
        this.drawGradientRect(var5 + var4 + 2, var6 - 3 + 1, var5 + var4 + 3, var6 + var8 + 3 - 1, var10, var11);
        this.drawGradientRect(var5 - 3, var6 - 3, var5 + var4 + 3, var6 - 3 + 1, var10, var10);
        this.drawGradientRect(var5 - 3, var6 + var8 + 2, var5 + var4 + 3, var6 + var8 + 3, var11, var11);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        fontRenderer.drawStringWithShadow(par1Str, var5, var6, -1);
        this.zLevel = 0.0F;
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }

    private void drawIcon(Icon par1Icon, int par2, int par3, Minecraft mc, Integer color) {
        Icon icon = par1Icon;
        float f;
        float f1;
        float f2;

        int j1;

        GL11.glDisable(GL11.GL_LIGHTING);

        Minecraft.getMinecraft().getTextureManager().bindTexture(this.baseTexture);

        if( icon == null )
        {
            icon = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationItemsTexture)).getTextureExtry("missingno");
        }

        if( color != null )
        {
            j1 = color.intValue();
            float f4 = (float)(j1 >> 16 & 255) / 255.0F;
            f2 = (float)(j1 >> 8 & 255) / 255.0F;
            f = (float)(j1 & 255) / 255.0F;
            GL11.glColor4f(f4, f2, f, 1.0F);
        }

        this.renderIcon(par2, par3, icon, 16, 16);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        
//        if( this.hasEffect ) {
//            GL11.glDepthFunc(GL11.GL_GREATER);
//            GL11.glDisable(GL11.GL_LIGHTING);
//            GL11.glDepthMask(false);
//            Minecraft.getMinecraft().getTextureManager().bindTexture("%blur%/misc/glint.png");
//            this.zLevel -= 50.0F;
//            GL11.glEnable(GL11.GL_BLEND);
//            GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
//            GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
//            ItemRenderHelper.renderGlint(par2 * 431278612 + par3 * 32178161, par2 - 2, par3 - 2, 20, 20, this.zLevel);
//            GL11.glDisable(GL11.GL_BLEND);
//            GL11.glDepthMask(true);
//            this.zLevel += 50.0F;
//            GL11.glEnable(GL11.GL_LIGHTING);
//            GL11.glDepthFunc(GL11.GL_LEQUAL);
//        }
    }

    private void renderIcon(int par1, int par2, Icon par3Icon, int par4, int par5)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par5), (double)this.zLevel, (double)par3Icon.getMinU(), (double)par3Icon.getMaxV());
        tessellator.addVertexWithUV((double)(par1 + par4), (double)(par2 + par5), (double)this.zLevel, (double)par3Icon.getMaxU(), (double)par3Icon.getMaxV());
        tessellator.addVertexWithUV((double)(par1 + par4), (double)(par2 + 0), (double)this.zLevel, (double)par3Icon.getMaxU(), (double)par3Icon.getMinV());
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)par3Icon.getMinU(), (double)par3Icon.getMinV());
        tessellator.draw();
    }

}
