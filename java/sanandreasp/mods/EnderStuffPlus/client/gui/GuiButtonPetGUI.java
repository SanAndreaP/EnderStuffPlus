package sanandreasp.mods.EnderStuffPlus.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonPetGUI extends GuiButton {
	private int color = 0xFFFFFF;
	private int transparency = 0xFF;

	public GuiButtonPetGUI(int par1, int par2, int par3, int par4, int par5, String par6Str, int par7Color) {
		super(par1, par2, par3, par4, par5, par6Str);
		this.color = par7Color;
	}

    public GuiButtonPetGUI(int par1, int par2, int par3, String par4Str, int par5Color)
    {
        this(par1, par2, par3, 200, 20, par4Str, par5Color);
    }
    
    /** sets the transparency of the button (excluding the text) to the value from the parameter.
     * Valid values are  all from 0 to 255 **/
    public GuiButtonPetGUI setBGAlpha(int alpha) {
    	transparency = Math.min(255, Math.max(0, alpha));
    	return this;
    }

    /**
     * Draws this button to the screen.
     */
    @Override
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if( this.drawButton )
        {
            FontRenderer var4 = par1Minecraft.fontRenderer;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean var5 = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            
            int var7 = this.color;
            if( !this.enabled )
            {
                var7 = 0x505050;
            }
            else if( var5 )
            {
                var7 = 0xFFFFFF;
            }
            
            int var8Color = ((transparency << 24) & 0xFF000000) + var7;
            Gui.drawRect(this.xPosition + 1, this.yPosition, this.xPosition + this.width, this.yPosition + 1, var8Color);
            Gui.drawRect(this.xPosition + this.width - 1, this.yPosition + 1, this.xPosition + this.width, this.yPosition + this.height, var8Color);
            Gui.drawRect(this.xPosition, this.yPosition + this.height - 1, this.xPosition + this.width - 1, this.yPosition + this.height, var8Color);
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + 1, this.yPosition + this.height - 1, var8Color);
            if( var5 && this.enabled )
            	Gui.drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, ((transparency << 24) & 0xFF000000) + this.color);
            Gui.drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, (transparency << 24) & 0xFF000000);
            this.mouseDragged(par1Minecraft, par2, par3);

            var4.drawString(this.displayString, this.xPosition + (this.width - var4.getStringWidth(this.displayString)) / 2, this.yPosition + (this.height - 8) / 2, var7);
        }
    }
}
