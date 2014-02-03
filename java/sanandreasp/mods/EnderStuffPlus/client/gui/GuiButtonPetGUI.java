package sanandreasp.mods.EnderStuffPlus.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiButtonPetGUI extends GuiButton 
{
	private int color = 0xFFFFFF;
	private int transparency = 0xFF;

	public GuiButtonPetGUI(int id, int x, int y, int width, int height, String label, int color) {
		super(id, x, y, width, height, label);
		this.color = color;
	}

    public GuiButtonPetGUI(int id, int x, int y, String label, int color) {
        this(id, x, y, 200, 20, label, color);
    }
    
    /** sets the transparency of the button (excluding the text) to the value from the parameter.
     * Valid values are  all from 0 to 255 **/
    public GuiButtonPetGUI setBGAlpha(int alpha) {
    	this.transparency = Math.min(255, Math.max(0, alpha));
    	return this;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if( this.drawButton ) {
            FontRenderer var4 = mc.fontRenderer;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean mouseHovering = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            
            int var7 = this.color;
            if( !this.enabled ) {
                var7 = 0x505050;
            } else if( mouseHovering ) {
                var7 = 0xFFFFFF;
            }
            
            int var8Color = ((transparency << 24) & 0xFF000000) + var7;
            Gui.drawRect(this.xPosition + 1, this.yPosition, this.xPosition + this.width, this.yPosition + 1, var8Color);
            Gui.drawRect(this.xPosition + this.width - 1, this.yPosition + 1, this.xPosition + this.width, this.yPosition + this.height, var8Color);
            Gui.drawRect(this.xPosition, this.yPosition + this.height - 1, this.xPosition + this.width - 1, this.yPosition + this.height, var8Color);
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + 1, this.yPosition + this.height - 1, var8Color);
            if( mouseHovering && this.enabled ) {
            	Gui.drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, ((transparency << 24) & 0xFF000000) + this.color);
            }
            Gui.drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, (transparency << 24) & 0xFF000000);
            this.mouseDragged(mc, mouseX, mouseY);

            var4.drawString(this.displayString, this.xPosition + (this.width - var4.getStringWidth(this.displayString)) / 2, this.yPosition + (this.height - 8) / 2, var7);
        }
    }
}
