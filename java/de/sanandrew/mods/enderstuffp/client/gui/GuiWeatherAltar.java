package de.sanandrew.mods.enderstuffp.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.network.EnumPacket;
import de.sanandrew.mods.enderstuffp.network.PacketProcessor;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityWeatherAltar;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiWeatherAltar
        extends GuiScreen
{
    private static final int X_SIZE = 226;
    private static final int Y_SIZE = 107;

    private TileEntityWeatherAltar weatherAltar;
    private GuiButton btnSun;
    private GuiButton btnRain;
    private GuiButton btnStorm;
    private GuiTextField txtDuration;
    private int guiLeft;
    private int guiTop;

    public GuiWeatherAltar(TileEntityWeatherAltar tileAltar) {
        this.weatherAltar = tileAltar;
        this.allowUserInput = true;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        int dur = this.getDurationInt();

        if( dur > 0 ) {
            byte weatherId = -1;
            if( button.id == this.btnSun.id ) {
                weatherId = 0;
            } else if( button.id == this.btnRain.id ) {
                weatherId = 1;
            } else if( button.id == this.btnStorm.id ) {
                weatherId = 2;
            }

            PacketProcessor.sendToServer(EnumPacket.WEATHERALTAR_SET, Quintet.with(this.weatherAltar.xCoord, this.weatherAltar.yCoord, this.weatherAltar.zCoord,
                                                                                   weatherId, dur));
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partTicks) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(EnumTextures.GUI_WEATHERALTAR.getResource());
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, X_SIZE, Y_SIZE);

        this.fontRendererObj.drawString(SAPUtils.translatePreFormat("tile.%s:weatherAltar.name", EnderStuffPlus.MOD_ID), this.guiLeft + 6, this.guiTop + 6, 0x808080);
        this.fontRendererObj.drawString(SAPUtils.translate(EnderStuffPlus.MOD_ID + ".weatherAltar.duration"), this.guiLeft + 12, this.guiTop + 35, 0x808080);

        this.txtDuration.drawTextBox();

        if( this.getDurationInt() <= 0 ) {
            this.txtDuration.setTextColor(0xFF0000);
        } else {
            this.txtDuration.setTextColor(0xFFFFFF);
        }

        super.drawScreen(mouseX, mouseY, partTicks);
    }

    private int getDurationInt() {
        try {
            int dur = Integer.parseInt(this.txtDuration.getText());

            return this.weatherAltar.isValidDuration(dur) ? dur : 0;
        } catch( NumberFormatException e ) {
            return 0;
        }
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - X_SIZE) / 2;
        this.guiTop = (this.height - Y_SIZE) / 2;

        this.btnSun = new GuiButton(this.buttonList.size(), this.guiLeft + 10, this.guiTop + 75, 66, 20,
                                    SAPUtils.translate(EnderStuffPlus.MOD_ID + ".weatherAltar.sun"));
        this.buttonList.add(this.btnSun);

        this.btnRain = new GuiButton(this.buttonList.size(), this.guiLeft + 10 + 67, this.guiTop + 75, 66, 20,
                                     SAPUtils.translate(EnderStuffPlus.MOD_ID + ".weatherAltar.rain"));
        this.buttonList.add(this.btnRain);

        this.btnStorm = new GuiButton(this.buttonList.size(), this.guiLeft + 10 + 134, this.guiTop + 75, 66, 20,
                                      SAPUtils.translate(EnderStuffPlus.MOD_ID + ".weatherAltar.thunder"));
        this.buttonList.add(this.btnStorm);

        this.txtDuration = new GuiTextField(this.fontRendererObj, this.guiLeft + 10, this.guiTop + 45, 200, 15);
        this.txtDuration.setText("1");
    }

    @Override
    protected void keyTyped(char key, int keyCode) {
        this.txtDuration.textboxKeyTyped(key, keyCode);

        if( (keyCode == 28 || keyCode == 1) && this.txtDuration.isFocused() ) {
            this.txtDuration.setFocused(false);
        } else if( (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) && !this.txtDuration.isFocused() ) {
            this.mc.thePlayer.closeScreen();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseBtn) {
        super.mouseClicked(mouseX, mouseY, mouseBtn);
        this.txtDuration.mouseClicked(mouseX, mouseY, mouseBtn);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.txtDuration.updateCursorCounter();
    }
}
