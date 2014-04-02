package sanandreasp.mods.EnderStuffPlus.client.gui;

import org.lwjgl.opengl.GL11;

import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.core.manpack.helpers.client.GuiItemTab;
import sanandreasp.mods.EnderStuffPlus.inventory.ContainerDuplicator;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryDuplicator;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDuplicator
    extends GuiContainer
{
    private GuiButton insertXP;
    private TileEntityDuplicator teDuplicator;

    public GuiDuplicator(ContainerDuplicator container) {
        super(container);

        this.allowUserInput = true;
        this.teDuplicator = container.getDuplicator();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if( button.id == this.insertXP.id ) {
            ESPModRegistry.sendPacketSrv("dupeInsLevels", this.teDuplicator);
        } else {
            super.actionPerformed(button);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(Textures.GUI_DUPLICATOR);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        RenderHelper.disableStandardItemLighting();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int height = 12 - MathHelper.ceiling_float_int((12F * ((float) this.teDuplicator.getBurnTime() / (float) this.teDuplicator.maxBurnTime)));
        int length = (int) (19 * ((float) this.teDuplicator.getProcTime()) / 18F);
        String s = Integer.toString(this.teDuplicator.getStoredLvl());

        RenderHelper.disableStandardItemLighting();
        this.fontRenderer.drawString(CommonUsedStuff.getTranslated("tile.enderstuffp:duplicator.name"), this.ySize
                                     - this.fontRenderer.getStringWidth(CommonUsedStuff.getTranslated("tile.duplicator.name"))
                                     - 8, 8, 0x404040);
        this.fontRenderer.drawString(CommonUsedStuff.getTranslated("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);

        this.drawStringWithFrame(this.fontRenderer, s, this.xSize - 8 - this.fontRenderer.getStringWidth(s), 50, 0x80ff20, 0x000000);

        s = Integer.toString(RegistryDuplicator.getNeededExp(this.teDuplicator.getStackInSlot(0)));
        this.drawStringWithFrame(this.fontRenderer, s, this.xSize - 8 - this.fontRenderer.getStringWidth(s), 65, 0x80ff20, 0x000000);

        this.mc.getTextureManager().bindTexture(Textures.GUI_DUPLICATOR);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawTexturedModalRect(60, 40 + height, 176, 14 + height, 20, 12 - height);
        this.drawTexturedModalRect(61, 22, 176, 0, length, 14);

        if( mouseX > this.guiLeft + 124 && mouseX <= this.guiLeft + this.xSize - 7 && mouseY > this.guiTop + 48
                && mouseY <= this.guiTop + 59 ) {
            drawRect(124, 48, this.xSize - 7, 59, 0x40FFFFFF);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(125, 49, 176, 26, 9, 9);
            this.drawCreativeTabHoveringText(CommonUsedStuff.getTranslated("enderstuffplus.duplicator.storedXP"),
                                             mouseX - this.guiLeft, mouseY - this.guiTop);
            RenderHelper.disableStandardItemLighting();
        } else {
            drawRect(124, 48, this.xSize - 7, 59, 0x40000000);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(125, 49, 176, 26, 9, 9);
        }

        this.mc.getTextureManager().bindTexture(Textures.GUI_DUPLICATOR);

        if( mouseX > this.guiLeft + 124 && mouseX <= this.guiLeft + this.xSize - 7 && mouseY > this.guiTop + 61
                && mouseY <= this.guiTop + 75 ) {
            drawRect(124, 61, this.xSize - 7, 75, 0x40FFFFFF);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(125, 62, 185, 26, 11, 12);
            this.drawCreativeTabHoveringText(CommonUsedStuff.getTranslated("enderstuffplus.duplicator.neededXP"),
                                             mouseX - this.guiLeft, mouseY - this.guiTop);
            RenderHelper.disableStandardItemLighting();
        } else {
            drawRect(124, 61, this.xSize - 7, 75, 0x40000000);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(125, 62, 185, 26, 11, 12);
        }

        this.mc.getTextureManager().bindTexture(Textures.GUI_DUPLICATOR);

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
                                       CommonUsedStuff.getTranslated("enderstuffplus.duplicator.insertXP"),
                                       Item.blazeRod.getIconFromDamage(0), true, true, Textures.GUI_BUTTONS);
        this.buttonList.add(this.insertXP);
    }
}
