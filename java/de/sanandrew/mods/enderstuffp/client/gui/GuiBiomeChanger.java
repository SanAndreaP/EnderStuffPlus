/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.gui;

import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeChanger;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class GuiBiomeChanger
        extends GuiScreen
{
    private TileEntityBiomeChanger biomeChanger;

    private static final int WIDTH = 176;
    private static final int HEIGHT = 256;

    private int posX;
    private int posY;

    public GuiBiomeChanger(TileEntityBiomeChanger bChanger) {
        this.biomeChanger = bChanger;
    }

    @Override
    public void initGui() {
        this.posX = (this.width - WIDTH) / 2;
        this.posY = (this.height - HEIGHT) / 2;

        this.buttonList.add(new GuiButtonBiomeChanger(0, this.posX + 10, this.posY + 73, 155, "activate"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partTicks) {
        int currFlux = this.biomeChanger.getEnergyStored(ForgeDirection.UNKNOWN);
        int maxFlux = this.biomeChanger.getMaxEnergyStored(ForgeDirection.UNKNOWN);
        int fluxScale = 40 - (int) (currFlux / (float) maxFlux * 40.0F);

        this.drawDefaultBackground();

        GL11.glPushMatrix();
        GL11.glTranslatef(this.posX, this.posY, 0.0F);

        this.mc.renderEngine.bindTexture(EnumTextures.GUI_BIOMECHANGER.getResource());

        this.drawTexturedModalRect(0, 0, 0, 0, WIDTH, HEIGHT);

        this.mc.renderEngine.bindTexture(EnumTextures.GUI_SCALES.getResource());
        this.drawTexturedModalRect(10, 20, 0, 10, 14, 42);
        this.drawTexturedModalRect(10, 21 + fluxScale, 14, 11 + fluxScale, 14, 40 - fluxScale);

        this.mc.fontRenderer.drawString("Power Usage:", 28, 21, 0xFF555555);
        this.mc.fontRenderer.drawString("0 RF/t", 38, 31, 0xFF000000);
        this.mc.fontRenderer.drawString("Stored Energy:", 28, 43, 0xFF555555);
        this.mc.fontRenderer.drawString(String.format("%d / %d RF", currFlux, maxFlux), 38, 53, 0xFF000000);
//        this.mc.fontRenderer.drawString(Integer.toString(biomeChanger.getEnergyStored(ForgeDirection.UNKNOWN)), 0, 0, 0xFFFFFFFF);

        GL11.glPopMatrix();

        super.drawScreen(mouseX, mouseY, partTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
