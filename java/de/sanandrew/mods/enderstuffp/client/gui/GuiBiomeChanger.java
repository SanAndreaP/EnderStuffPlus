/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.gui;

import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.network.packet.PacketBiomeChangerActions;
import de.sanandrew.mods.enderstuffp.network.packet.PacketBiomeChangerActions.EnumAction;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeChanger;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiBiomeChanger
        extends GuiScreen
{
    private TileEntityBiomeChanger biomeChanger;

    private static final int WIDTH = 176;
    private static final int HEIGHT = 240;

    private int posX;
    private int posY;

    private GuiButton btnActivate;
    private GuiButton btnDeactivate;

    private GuiButton btnEnableBlockReplace;
    private GuiButton btnDisableBlockReplace;

    private GuiButton sldRange;

    private static FontRenderer numberFont;

    public GuiBiomeChanger(TileEntityBiomeChanger bChanger) {
        this.biomeChanger = bChanger;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initGui() {
        this.posX = (this.width - WIDTH) / 2;
        this.posY = (this.height - HEIGHT) / 2;

        if( numberFont == null ) {
            numberFont = new FontRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, false);
            ((IReloadableResourceManager) this.mc.getResourceManager()).registerReloadListener(numberFont);
        }

        this.buttonList.add(this.btnActivate = new GuiButtonBiomeChanger(0, this.posX + 10, this.posY + 220, 156, "activate"));
        this.buttonList.add(this.btnDeactivate = new GuiButtonBiomeChanger(1, this.posX + 10, this.posY + 220, 156, "deactivate"));

        this.buttonList.add(this.btnEnableBlockReplace = new GuiButtonBiomeChanger(2, this.posX + 10, this.posY + 200, 78, "enable"));
        this.buttonList.add(this.btnDisableBlockReplace = new GuiButtonBiomeChanger(3, this.posX + 88, this.posY + 200, 78, "disable"));

        this.buttonList.add(this.sldRange = new GuiBiomeChangerSlider(4, this.posX + 10, this.posY + 175, this.biomeChanger, "Range"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partTicks) {
        int currFlux = this.biomeChanger.getEnergyStored(ForgeDirection.UNKNOWN);
        int maxFlux = this.biomeChanger.getMaxEnergyStored(ForgeDirection.UNKNOWN);
        int fluxScale = 40 - (int) (currFlux / (float) maxFlux * 40.0F);

        this.drawDefaultBackground();

        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        GL11.glPushMatrix();
        GL11.glTranslatef(this.posX, this.posY, 0.0F);

        this.mc.renderEngine.bindTexture(EnumTextures.GUI_BIOMECHANGER.getResource());

        this.drawTexturedModalRect(0, 0, 0, 0, WIDTH, HEIGHT);

        this.mc.renderEngine.bindTexture(EnumTextures.GUI_SCALES.getResource());
        this.drawTexturedModalRect(10, 20, 0, 10, 14, 42);
        this.drawTexturedModalRect(10, 21 + fluxScale, 14, 11 + fluxScale, 14, 40 - fluxScale);
        this.drawTexturedModalRect(7, 70, 0, 0, 161, 5);
        this.drawTexturedModalRect(7, 70, 0, 5, Math.round(161.0F * (this.biomeChanger.getCurrRange() / (float) this.biomeChanger.getMaxRange())), 5);

        String s = String.format("%d / %d", this.biomeChanger.getCurrRange(), this.biomeChanger.getMaxRange());
        drawOutlinedString(s, 7 + (161 - numberFont.getStringWidth(s)) / 2, 69, 0xFFA080F2, 0xFF000000);

        this.mc.fontRenderer.drawString("Power Usage:", 28, 21, 0xFF555555);
        this.mc.fontRenderer.drawString(String.format("%d RF/t", this.biomeChanger.getFluxUsage()), 38, 31, 0xFF000000);
        this.mc.fontRenderer.drawString("Stored Energy:", 28, 43, 0xFF555555);
        this.mc.fontRenderer.drawString(String.format("%d / %d RF", currFlux, maxFlux), 38, 53, 0xFF000000);

        GL11.glPopMatrix();

        this.btnDeactivate.visible = this.biomeChanger.isActive();
        this.btnActivate.visible = !this.biomeChanger.isActive();
        this.btnDisableBlockReplace.enabled = this.biomeChanger.isReplacingBlocks() && !this.biomeChanger.isActive();
        this.btnEnableBlockReplace.enabled = !this.biomeChanger.isReplacingBlocks() && !this.biomeChanger.isActive();
        this.sldRange.enabled = !this.biomeChanger.isActive();

        super.drawScreen(mouseX, mouseY, partTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if( button == this.btnActivate ) {
            PacketBiomeChangerActions.sendPacketServer(this.biomeChanger, EnumAction.ACTIVATE, null);
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        } else if( button == this.btnDeactivate ) {
            PacketBiomeChangerActions.sendPacketServer(this.biomeChanger, EnumAction.DEACTIVATE, null);
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        } else if( button == this.btnEnableBlockReplace || button == this.btnDisableBlockReplace ) {
            this.biomeChanger.replaceBlocks(button == this.btnEnableBlockReplace);
            PacketBiomeChangerActions.sendPacketServer(this.biomeChanger, EnumAction.REPLACE_BLOCKS, null);
        }

        super.actionPerformed(button);
    }

    @Override
    protected void keyTyped(char keyChar, int keyCode) {
        if( keyCode == Keyboard.KEY_ESCAPE || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode() ) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private static void drawOutlinedString(String s, int x, int y, int foreColor, int frameColor) {
        numberFont.drawString(s, x - 1, y, frameColor);
        numberFont.drawString(s, x + 1, y, frameColor);
        numberFont.drawString(s, x, y - 1, frameColor);
        numberFont.drawString(s, x, y + 1, frameColor);
        numberFont.drawString(s, x, y, foreColor);
    }
}
