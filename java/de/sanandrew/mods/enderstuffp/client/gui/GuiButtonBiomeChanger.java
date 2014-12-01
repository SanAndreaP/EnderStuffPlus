/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.gui;

import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public class GuiButtonBiomeChanger
        extends GuiButton
{
    public GuiButtonBiomeChanger(int id, int x, int y, int width, String text) {
        super(id, x, y, width, 14, text);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if( this.visible ) {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(EnumTextures.GUI_BUTTONS.getResource());
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width
                                  && mouseY < this.yPosition + this.height;
            int hoverState = this.getHoverState(this.field_146123_n);

            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            this.drawTexturedModalRect(this.xPosition                 , this.yPosition, 0                   , 78 + hoverState * 14, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 78 + hoverState * 14, this.width / 2, this.height);

            this.mouseDragged(mc, mouseX, mouseY);
            int l = 14737632;

            if( packedFGColour != 0 ) {
                l = packedFGColour;
            } else if( !this.enabled ) {
                l = 10526880;
            } else if( this.field_146123_n ) {
                l = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
        }
    }
}
