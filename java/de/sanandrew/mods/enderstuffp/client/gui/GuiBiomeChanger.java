/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.gui;

import com.google.common.collect.Maps;
import de.sanandrew.core.manpack.util.client.helpers.GuiUtils;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.network.packet.PacketBiomeChangerActions;
import de.sanandrew.mods.enderstuffp.network.packet.PacketBiomeChangerActions.EnumAction;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeChanger;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeChanger.EnumPerimForm;
import de.sanandrew.mods.enderstuffp.util.RegistryBlocks;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class GuiBiomeChanger
        extends GuiScreen
{
    private TileEntityBiomeChanger biomeChanger;

    private static final int WIDTH = 176;
    private static final int HEIGHT = 240;

    private int posX;
    private int posY;

    private static final int BTN_ACTIVATE = 0;
    private static final int BTN_DEACTIVATE = 1;
    private static final int BTN_BLOCKREPL_ENABLE = 2;
    private static final int BTN_BLOCKREPL_DISABLE = 3;
    private static final int BTN_SLIDER_RANGE = 4;
    private static final int BTN_PERIM_SQUARE = 5;
    private static final int BTN_PERIM_CIRCLE = 6;
    private static final int BTN_PERIM_RHOMBUS = 7;

    private Map<Integer, GuiButton> buttons = Maps.newHashMap();

    private static FontRenderer numberFont;
    private static FontRenderer unicodeFont;

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
        if( unicodeFont == null ) {
            unicodeFont = new FontRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, true);
            ((IReloadableResourceManager) this.mc.getResourceManager()).registerReloadListener(unicodeFont);
        }

        String s = RegistryBlocks.biomeChanger.getUnlocalizedName() + ".gui.";
        this.buttons.put(BTN_PERIM_SQUARE, new GuiButtonBiomeChanger(BTN_PERIM_SQUARE, this.posX + 14, this.posY + 98, 148,
                                                                     SAPUtils.translate(s + "perim.rectangle")));
        this.buttons.put(BTN_PERIM_CIRCLE, new GuiButtonBiomeChanger(BTN_PERIM_CIRCLE, this.posX + 14, this.posY + 112, 148,
                                                                     SAPUtils.translate(s + "perim.circle")));
        this.buttons.put(BTN_PERIM_RHOMBUS, new GuiButtonBiomeChanger(BTN_PERIM_RHOMBUS, this.posX + 14, this.posY + 126, 148,
                                                                      SAPUtils.translate(s + "perim.rhombus")));

        this.buttons.put(BTN_SLIDER_RANGE, new GuiBiomeChangerSlider(BTN_SLIDER_RANGE, this.posX + 14, this.posY + 143, this.biomeChanger,
                                                                     SAPUtils.translate(s + "perim.range")));

        this.buttons.put(BTN_BLOCKREPL_ENABLE, new GuiButtonBiomeChanger(BTN_BLOCKREPL_ENABLE, this.posX + 10, this.posY + 189, 78,
                                                                         SAPUtils.translate(s + "blockrepl.btn.enable")));
        this.buttons.put(BTN_BLOCKREPL_DISABLE, new GuiButtonBiomeChanger(BTN_BLOCKREPL_DISABLE, this.posX + 88, this.posY + 189, 78,
                                                                          SAPUtils.translate(s + "blockrepl.btn.disable")));

        this.buttons.put(BTN_ACTIVATE, new GuiButtonBiomeChanger(BTN_ACTIVATE, this.posX + 10, this.posY + 215, 156, SAPUtils.translate(s + "activate")));
        this.buttons.put(BTN_DEACTIVATE, new GuiButtonBiomeChanger(BTN_DEACTIVATE, this.posX + 10, this.posY + 215, 156, SAPUtils.translate(s + "deactivate")));

        this.buttonList.addAll(this.buttons.values());
    }

    private long drawCycles = 0;
    @Override
    public void drawScreen(int mouseX, int mouseY, float partTicks) {

        int currFlux = this.biomeChanger.getEnergyStored(ForgeDirection.UNKNOWN);
        int maxFlux = this.biomeChanger.getMaxEnergyStored(ForgeDirection.UNKNOWN);
        int fluxUsage = this.biomeChanger.getFluxUsage();
        int fluxScale = 40 - (int) (currFlux / (float) maxFlux * 40.0F);
        int bufferScale = fluxUsage > 0 ? 40 - (int) (this.biomeChanger.usedFlux / (fluxUsage * 20.0F) * 40.0F) : 40;

        this.drawDefaultBackground();

        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        GL11.glPushMatrix();
        GL11.glTranslatef(this.posX, this.posY, 0.0F);

        this.mc.renderEngine.bindTexture(EnumTextures.GUI_BIOMECHANGER.getResource());

        this.drawTexturedModalRect(0, 0, 0, 0, WIDTH, HEIGHT);

        this.drawTexturedModalRect(10, 20, 176, 0, 14, 42);
        this.drawTexturedModalRect(14, 21 + fluxScale, 194, 1 + fluxScale, 9, 40 - fluxScale);
        this.drawTexturedModalRect(11, 21 + bufferScale, 191, 1 + bufferScale, 3, 40 - bufferScale);
        this.drawTexturedModalRect(7, 75, 0, 240, 161, 5);
        this.drawTexturedModalRect(7, 75, 0, 245, Math.round(161.0F * (this.biomeChanger.getCurrRange() / (float) this.biomeChanger.getMaxRange())), 5);

        drawCycles++;
        for( int i = 0; i < 80; i++ ) {
            float yShift = (float) (Math.sin((drawCycles * ((i + 50) * (1.0F / 80.0F))) / 20.0D * Math.PI) * 6.0D);
            GL11.glPushMatrix();
            GL11.glTranslatef(i + 3, yShift + 9.0F, 0.0F);
            drawRect(0, 0, 1, 1, 0xFFFFFFFF);
            GL11.glPopMatrix();
        }

        this.mc.fontRenderer.drawString(SAPUtils.translate(this.biomeChanger.getName()), 8, 6, 0x404040);

        String unlocName = RegistryBlocks.biomeChanger.getUnlocalizedName() + ".gui.";

        this.mc.fontRenderer.drawString(SAPUtils.translate(unlocName + "flux.usage"), 28, 21, 0x707070);
        this.mc.fontRenderer.drawString(String.format("%d RF/t", fluxUsage), 33, 31, 0x000000);
        this.mc.fontRenderer.drawString(SAPUtils.translate(unlocName + "flux.stored"), 28, 43, 0x707070);
        this.mc.fontRenderer.drawString(String.format("%d %s(+%d)%s / %d RF", currFlux, EnumChatFormatting.DARK_GREEN, this.biomeChanger.usedFlux,
                                                      EnumChatFormatting.RESET, maxFlux), 33, 53, 0x000000);

        this.mc.fontRenderer.drawString(SAPUtils.translate(unlocName + "progress"), 10, 66, 0x404040);
        String s = String.format("%d / %d", this.biomeChanger.getCurrRange(), this.biomeChanger.getMaxRange());
        GuiUtils.drawOutlinedString(numberFont, s, 7 + (161 - numberFont.getStringWidth(s)) / 2, 74, 0xA090FF, 0x000000);

        s = SAPUtils.translate(unlocName + "perim");
        drawRect(14, 88, 17 + this.mc.fontRenderer.getStringWidth(s), 88 + this.mc.fontRenderer.FONT_HEIGHT, 0xFFC6C6C6);
        this.mc.fontRenderer.drawString(s, 16, 88, 0x404040);

        s = SAPUtils.translatePostFormat(unlocName + "blockrepl", this.biomeChanger.isReplacingBlocks()
                                                                        ? EnumChatFormatting.DARK_GREEN + SAPUtils.translate(unlocName + "blockrepl.label.enabled")
                                                                        : EnumChatFormatting.DARK_RED + SAPUtils.translate(unlocName + "blockrepl.label.disabled"));
        this.mc.fontRenderer.drawString(s, 10, 179, 0x404040);

        GL11.glPopMatrix();

        this.buttons.get(BTN_DEACTIVATE).visible = this.biomeChanger.isActive();
        this.buttons.get(BTN_ACTIVATE).visible = !this.biomeChanger.isActive();

        this.buttons.get(BTN_BLOCKREPL_DISABLE).enabled = this.biomeChanger.isReplacingBlocks() && !this.biomeChanger.isActive();
        this.buttons.get(BTN_BLOCKREPL_ENABLE).enabled = !this.biomeChanger.isReplacingBlocks() && !this.biomeChanger.isActive();

        this.buttons.get(BTN_SLIDER_RANGE).enabled = !this.biomeChanger.isActive();

        this.buttons.get(BTN_PERIM_CIRCLE).enabled = this.biomeChanger.perimForm != EnumPerimForm.CIRCLE && !this.biomeChanger.isActive();
        this.buttons.get(BTN_PERIM_RHOMBUS).enabled = this.biomeChanger.perimForm != EnumPerimForm.RHOMBUS && !this.biomeChanger.isActive();
        this.buttons.get(BTN_PERIM_SQUARE).enabled = this.biomeChanger.perimForm != EnumPerimForm.SQUARE && !this.biomeChanger.isActive();

        super.drawScreen(mouseX, mouseY, partTicks);

        if( this.biomeChanger.isActive() ) {
            drawRect(this.posX + 10, this.posY + 88, this.posX + 170, this.posY + 210, 0x80C6C6C6);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch( button.id ) {
            case BTN_ACTIVATE:      //FALL-THROUGH
            case BTN_DEACTIVATE:
                PacketBiomeChangerActions.sendPacketServer(this.biomeChanger, button.id == BTN_ACTIVATE ? EnumAction.ACTIVATE : EnumAction.DEACTIVATE);
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                return;
            case BTN_BLOCKREPL_ENABLE:      //FALL-THROUGH
            case BTN_BLOCKREPL_DISABLE:
                this.biomeChanger.replaceBlocks(button.id == BTN_BLOCKREPL_ENABLE);
                PacketBiomeChangerActions.sendPacketServer(this.biomeChanger, EnumAction.REPLACE_BLOCKS);
                return;
            case BTN_PERIM_CIRCLE:
                this.biomeChanger.perimForm = EnumPerimForm.CIRCLE;
                PacketBiomeChangerActions.sendPacketServer(this.biomeChanger, EnumAction.CHNG_PERIM_FORM);
                return;
            case BTN_PERIM_RHOMBUS:
                this.biomeChanger.perimForm = EnumPerimForm.RHOMBUS;
                PacketBiomeChangerActions.sendPacketServer(this.biomeChanger, EnumAction.CHNG_PERIM_FORM);
                return;
            case BTN_PERIM_SQUARE:
                this.biomeChanger.perimForm = EnumPerimForm.SQUARE;
                PacketBiomeChangerActions.sendPacketServer(this.biomeChanger, EnumAction.CHNG_PERIM_FORM);
                return;
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
}
