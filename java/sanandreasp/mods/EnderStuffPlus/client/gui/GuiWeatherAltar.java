package sanandreasp.mods.EnderStuffPlus.client.gui;

import org.lwjgl.opengl.GL11;

import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWeatherAltar
    extends GuiScreen
{
    private TileEntityWeatherAltar teWeatherAltar;
    private GuiButton btnSun;
    private GuiButton btnRain;
    private GuiButton btnStorm;
    private GuiTextField txtDuration;
    private int guiLeft;
    private int guiTop;
    private int xSize = 226;
    private int ySize = 107;

    public GuiWeatherAltar(TileEntityWeatherAltar tileAltar) {
        this.teWeatherAltar = tileAltar;
        this.allowUserInput = true;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        int dur = this.getDurationInt();

        if( dur > 0 ) {
            if( button.id == this.btnSun.id ) {
                ESPModRegistry.sendPacketSrv("setWeather", 0, dur, this.teWeatherAltar.xCoord, this.teWeatherAltar.yCoord,
                                             this.teWeatherAltar.zCoord, this.mc.theWorld);
                this.mc.thePlayer.closeScreen();
            } else if( button.id == this.btnRain.id ) {
                ESPModRegistry.sendPacketSrv("setWeather", 1, dur, this.teWeatherAltar.xCoord, this.teWeatherAltar.yCoord,
                                             this.teWeatherAltar.zCoord, this.mc.theWorld);
                this.mc.thePlayer.closeScreen();
            } else if( button.id == this.btnStorm.id ) {
                ESPModRegistry.sendPacketSrv("setWeather", 2, dur, this.teWeatherAltar.xCoord, this.teWeatherAltar.yCoord,
                                             this.teWeatherAltar.zCoord, this.mc.theWorld);
                this.mc.thePlayer.closeScreen();
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partTicks) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.GUI_WEATHERALTAR);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        this.fontRenderer.drawString(CommonUsedStuff.getTranslated("tile.enderstuffp:weatherAltar.name"),
                                     this.guiLeft + 6, this.guiTop + 6, 0x808080);
        this.fontRenderer.drawString(CommonUsedStuff.getTranslated("enderstuffplus.weatherAltar.duration"),
                                     this.guiLeft + 12, this.guiTop + 35, 0x808080);

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

            return this.teWeatherAltar.isValidDuration(dur) ? dur : 0;
        } catch( NumberFormatException e ) {
            return 0;
        }
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        this.btnSun = new GuiButton(this.buttonList.size(), this.guiLeft + 10, this.guiTop + 75, 66, 20,
                                    CommonUsedStuff.getTranslated("enderstuffplus.weatherAltar.sun"));
        this.buttonList.add(this.btnSun);

        this.btnRain = new GuiButton(this.buttonList.size(), this.guiLeft + 10 + 67, this.guiTop + 75, 66, 20,
                                     CommonUsedStuff.getTranslated("enderstuffplus.weatherAltar.rain"));
        this.buttonList.add(this.btnRain);

        this.btnStorm = new GuiButton(this.buttonList.size(), this.guiLeft + 10 + 134, this.guiTop + 75, 66, 20,
                                      CommonUsedStuff.getTranslated("enderstuffplus.weatherAltar.thunder"));
        this.buttonList.add(this.btnStorm);

        this.txtDuration = new GuiTextField(this.fontRenderer, this.guiLeft + 10, this.guiTop + 45, 200, 15);
        this.txtDuration.setText("1");
    }

    @Override
    protected void keyTyped(char key, int keyCode) {
        this.txtDuration.textboxKeyTyped(key, keyCode);

        if( (keyCode == 28 || keyCode == 1) && this.txtDuration.isFocused() ) {
            this.txtDuration.setFocused(false);
        } else if( (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.keyCode)
                   && !this.txtDuration.isFocused() ) {
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
