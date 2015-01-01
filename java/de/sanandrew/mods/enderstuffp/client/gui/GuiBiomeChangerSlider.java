/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.network.packet.PacketBiomeChangerActions;
import de.sanandrew.mods.enderstuffp.network.packet.PacketBiomeChangerActions.EnumAction;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeChanger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiBiomeChangerSlider
        extends GuiButton
{
    private boolean isDragging = false;
    private float maxSliderVal = 1.0F;
    private float sliderVal = 1.0F;
    private TileEntityBiomeChanger biomeChanger;
    private String title = "";

    public GuiBiomeChangerSlider(int id, int x, int y, TileEntityBiomeChanger tileBiomeChanger, String label) {
        super(id, x, y, 148, 20, label + ": " + tileBiomeChanger.getMaxRange());

        this.title = label;
        this.biomeChanger = tileBiomeChanger;
        this.maxSliderVal = 128.0F;
        this.sliderVal = tileBiomeChanger.getMaxRange() / 128.0F;
    }

    @Override
    public int getHoverState(boolean par1) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY) {
        if( this.visible ) {
            if( this.isDragging && this.enabled ) {
                this.sliderVal = Math.min(1.0F, Math.max((mouseX - (this.xPosition + 4.0F)) / (this.width - 8.0F), 1.0F / this.maxSliderVal));
                this.displayString = this.title + ": " + MathHelper.floor_float(this.sliderVal * this.maxSliderVal);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderVal * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderVal * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if( super.mousePressed(minecraft, mouseX, mouseY) && this.enabled ) {
            this.sliderVal = Math.min(1.0F, Math.max((mouseX - (this.xPosition + 4.0F)) / (this.width - 8.0F), 1.0F / this.maxSliderVal));

            this.displayString = this.title + ": " + MathHelper.floor_float(this.sliderVal * this.maxSliderVal);
            this.isDragging = true;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.biomeChanger.setMaxRange(MathHelper.floor_float(this.sliderVal * 128.0F));
        PacketBiomeChangerActions.sendPacketServer(this.biomeChanger, EnumAction.CHNG_MAX_RANGE);
        this.isDragging = false;
    }
}
