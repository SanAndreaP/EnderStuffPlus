package de.sanandrew.mods.enderstuffplus.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.client.GuiItemTab;
import de.sanandrew.mods.enderstuffplus.inventory.ContainerDuplicator;
import de.sanandrew.mods.enderstuffplus.packet.PacketDupeInsertLevels;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;
import de.sanandrew.mods.enderstuffplus.registry.RegistryDuplicator;
import de.sanandrew.mods.enderstuffplus.registry.Textures;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityDuplicator;

@SideOnly(Side.CLIENT)
public class GuiDuplicator
    extends GuiContainer
{
    private GuiButton insertXP;
    private final TileEntityDuplicator teDuplicator;

    public GuiDuplicator(ContainerDuplicator container) {
        super(container);

        this.allowUserInput = true;
        this.teDuplicator = container.getDuplicator();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if( button.id == this.insertXP.id ) {
            IPacket packet = new PacketDupeInsertLevels(this.teDuplicator);
            ESPModRegistry.channelHandler.sendToServer(packet);
//            ESPModRegistry.sendPacketSrv("dupeInsLevels", this.teDuplicator);
        } else {
            super.actionPerformed(button);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(Textures.GUI_DUPLICATOR.getResource());
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        RenderHelper.disableStandardItemLighting();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        float burnTimeRatio = this.teDuplicator.getBurnTime() / (float) this.teDuplicator.getMaxBurnTime();
        int height = 12 - MathHelper.ceiling_float_int((12F * burnTimeRatio));
        int length = (int) (19 * ((float) this.teDuplicator.getProcTime()) / 18F);
        String s = Integer.toString(this.teDuplicator.getStoredLvl());

        RenderHelper.disableStandardItemLighting();
        this.fontRendererObj.drawString(SAPUtils.getTranslated("tile.enderstuffp:duplicator.name"), this.ySize
                                        - this.fontRendererObj.getStringWidth(SAPUtils.getTranslated("tile.duplicator.name"))
                                        - 8, 8, 0x404040);
        this.fontRendererObj.drawString(SAPUtils.getTranslated("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);

        this.drawStringWithFrame(this.fontRendererObj, s, this.xSize - 8 - this.fontRendererObj.getStringWidth(s), 50, 0x80ff20, 0x000000);

        s = Integer.toString(RegistryDuplicator.getNeededExp(this.teDuplicator.getStackInSlot(0)));
        this.drawStringWithFrame(this.fontRendererObj, s, this.xSize - 8 - this.fontRendererObj.getStringWidth(s), 65, 0x80ff20, 0x000000);

        this.mc.getTextureManager().bindTexture(Textures.GUI_DUPLICATOR.getResource());

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawTexturedModalRect(60, 40 + height, 176, 14 + height, 20, 12 - height);
        this.drawTexturedModalRect(61, 22, 176, 0, length, 14);

        if( mouseX > this.guiLeft + 124 && mouseX <= this.guiLeft + this.xSize - 7 && mouseY > this.guiTop + 48
                && mouseY <= this.guiTop + 59 ) {
            drawRect(124, 48, this.xSize - 7, 59, 0x40FFFFFF);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(125, 49, 176, 26, 9, 9);
            this.drawCreativeTabHoveringText(SAPUtils.getTranslated("enderstuffplus.duplicator.storedXP"),
                                             mouseX - this.guiLeft, mouseY - this.guiTop);
            RenderHelper.disableStandardItemLighting();
        } else {
            drawRect(124, 48, this.xSize - 7, 59, 0x40000000);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(125, 49, 176, 26, 9, 9);
        }

        this.mc.getTextureManager().bindTexture(Textures.GUI_DUPLICATOR.getResource());

        if( mouseX > this.guiLeft + 124 && mouseX <= this.guiLeft + this.xSize - 7 && mouseY > this.guiTop + 61
                && mouseY <= this.guiTop + 75 ) {
            drawRect(124, 61, this.xSize - 7, 75, 0x40FFFFFF);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(125, 62, 185, 26, 11, 12);
            this.drawCreativeTabHoveringText(SAPUtils.getTranslated("enderstuffplus.duplicator.neededXP"),
                                             mouseX - this.guiLeft, mouseY - this.guiTop);
            RenderHelper.disableStandardItemLighting();
        } else {
            drawRect(124, 61, this.xSize - 7, 75, 0x40000000);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(125, 62, 185, 26, 11, 12);
        }

        this.mc.getTextureManager().bindTexture(Textures.GUI_DUPLICATOR.getResource());

        RenderHelper.enableGUIStandardItemLighting();
    }

    private void drawStringWithFrame(FontRenderer fontRenderer, String text, int x, int y, int fgColor, int bgColor) {
        fontRenderer.drawString(text, x - 1, y, bgColor);
        fontRenderer.drawString(text, x + 1, y, bgColor);
        fontRenderer.drawString(text, x, y - 1, bgColor);
        fontRenderer.drawString(text, x, y + 1, bgColor);
        fontRenderer.drawString(text, x, y, fgColor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initGui() {
        super.initGui();

        this.insertXP = new GuiItemTab(0, this.guiLeft + this.xSize - 3, this.guiTop + 49,
                                       SAPUtils.getTranslated("enderstuffplus.duplicator.insertXP"),
                                       Items.blaze_rod.getIconFromDamage(0), true, true, Textures.GUI_BUTTONS.getResource());
        this.buttonList.add(this.insertXP);
    }
}
