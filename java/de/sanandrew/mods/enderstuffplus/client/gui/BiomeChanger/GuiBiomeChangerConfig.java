package de.sanandrew.mods.enderstuffplus.client.gui.BiomeChanger;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;
import de.sanandrew.mods.enderstuffplus.registry.Textures;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityBiomeChanger;

@SideOnly(Side.CLIENT)
public class GuiBiomeChangerConfig
    extends GuiBiomeChangerBase
{
    private GuiButton circularButton;
    private GuiButton squareButton;
    private GuiButton rhombicButton;
    private GuiButton rangeSlider;
    private GuiButton changeBlocksButton;

    public GuiBiomeChangerConfig(TileEntityBiomeChanger tileBiomeChanger) {
        super(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer player) {
                return true;
            }
        });

        this.teBiomeChanger = tileBiomeChanger;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        int id = button.id;

        if( id == this.circularButton.id ) {
            ESPModRegistry.sendPacketSrv("bcGuiAction", this.teBiomeChanger, (byte) 3, 0);
        } else if( id == this.rhombicButton.id ) {
            ESPModRegistry.sendPacketSrv("bcGuiAction", this.teBiomeChanger, (byte) 3, 1);
        } else if( id == this.squareButton.id ) {
            ESPModRegistry.sendPacketSrv("bcGuiAction", this.teBiomeChanger, (byte) 3, 2);
        } else if( id == this.changeBlocksButton.id ) {
            ESPModRegistry.sendPacketSrv("bcGuiAction", this.teBiomeChanger, (byte) 4, 0);
        } else {
            super.actionPerformed(button);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(Textures.GUI_BIOMECHANGER_III.getResource());
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int middle = (int) (((float) this.teBiomeChanger.getCurrRange() / (float) this.teBiomeChanger.getMaxRange()) * 161F);

        GL11.glPushMatrix();
          GL11.glTranslatef(this.guiLeft, this.guiTop, 0F);
          this.drawTexturedModalRect(7, 140, 0, 230, 161, 5);
          this.drawTexturedModalRect(7, 140, 0, 235, middle, 5);
          this.drawTexturedModalRect(7, 156, 161, 230, 12, 5);
          this.drawTexturedModalRect(7, 166, 161, 235, 12, 5);
        GL11.glPopMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        RenderHelper.disableStandardItemLighting();

        this.fontRendererObj.drawString(SAPUtils.getTranslated("tile.enderstuffp:biomeChanger.name"), 8, 8, 0x404040);
        this.fontRendererObj.drawString(SAPUtils.getTranslated("enderstuffplus.biomeChanger.gui3.periform"), 8, 61, 0x404040);
        this.fontRendererObj.drawString(SAPUtils.getTranslated("enderstuffplus.biomeChanger.gui3.stats"), 8, 130, 0x404040);
        this.fontRendererObj.drawString(SAPUtils.getTranslated("enderstuffplus.biomeChanger.gui3.remain",
                                                                   this.teBiomeChanger.getMaxRange()
                                                                   - this.teBiomeChanger.getCurrRange()),
                                     20, 155, 0x404040);
        this.fontRendererObj.drawString(SAPUtils.getTranslated("enderstuffplus.biomeChanger.gui3.processed",
                                                                   this.teBiomeChanger.getCurrRange()),
                                     20, 165, 0x404040);

        RenderHelper.enableGUIStandardItemLighting();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partTicks) {
        this.circularButton.enabled = this.teBiomeChanger.getRadForm() != 0 && !this.teBiomeChanger.isActive();
        this.squareButton.enabled = this.teBiomeChanger.getRadForm() != 1 && !this.teBiomeChanger.isActive();
        this.rhombicButton.enabled = this.teBiomeChanger.getRadForm() != 2 && !this.teBiomeChanger.isActive();
        this.rangeSlider.enabled = this.changeBlocksButton.enabled = !this.teBiomeChanger.isActive();

        String onOffText = SAPUtils.getTranslated(this.teBiomeChanger.isReplacingBlocks() ? "options.on"
                                                                                               : "options.off");
        this.changeBlocksButton.displayString = SAPUtils.getTranslated("enderstuffplus.biomeChanger.gui3.blocks")
                                                + ": " + onOffText;

        super.drawScreen(mouseX, mouseY, partTicks);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();

        this.tabCofig.enabled = false;
        this.rangeSlider = new GuiBiomeChangerSlider(4, this.guiLeft + 13, this.guiTop + 30, this.teBiomeChanger,
                                                     SAPUtils.getTranslated("enderstuffplus.biomeChanger.gui1.range"),
                                                     this.teBiomeChanger.getMaxRange(), 128F);
        this.buttonList.add(this.rangeSlider);

        this.circularButton = new GuiButton(this.buttonList.size(), this.guiLeft + 13, this.guiTop + 70, 50, 20,
                                            SAPUtils.getTranslated("enderstuffplus.biomeChanger.gui3.form1"));
        this.buttonList.add(this.circularButton);

        this.squareButton = new GuiButton(this.buttonList.size(), this.guiLeft + 13 + 50, this.guiTop + 70, 50, 20,
                                          SAPUtils.getTranslated("enderstuffplus.biomeChanger.gui3.form2"));
        this.buttonList.add(this.squareButton);

        this.rhombicButton = new GuiButton(this.buttonList.size(), this.guiLeft + 13 + 100, this.guiTop + 70, 50, 20,
                                           SAPUtils.getTranslated("enderstuffplus.biomeChanger.gui3.form3"));
        this.buttonList.add(this.rhombicButton);

        this.changeBlocksButton = new GuiButton(this.buttonList.size(), this.guiLeft + 13, this.guiTop + 100, 150, 20,
                                                SAPUtils.getTranslated("enderstuffplus.biomeChanger.gui3.blocks"));
        this.buttonList.add(this.changeBlocksButton);
    }

    @Override
    protected void keyTyped(char key, int keyCode) {
        if( (keyCode == 1) || (keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) ) {
            this.mc.thePlayer.closeScreen();
        }

        super.keyTyped(key, keyCode);
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int buttonIndex) {
        super.mouseMovedOrUp(mouseX, mouseY, buttonIndex);

        if( (SAPUtils.getSelectedBtn(this) != null) && (buttonIndex == 0) ) {
            SAPUtils.getSelectedBtn(this).mouseReleased(mouseX, mouseY);
            SAPUtils.setSelectedBtn(this, null);
        }
    }
}
