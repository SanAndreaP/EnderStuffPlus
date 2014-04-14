package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sanandreasp.core.manpack.helpers.SAPUtils;
import sanandreasp.mods.EnderStuffPlus.inventory.ContainerBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBiomeChangerFuel
    extends GuiBiomeChangerBase
{
    private int entryPosition = 0;
    private float currentScrollPosition = 0F;
    private boolean isScrolling = false;
    private HashMap<Integer, Entry<ItemStack, Integer>> fuelEntries;

    public GuiBiomeChangerFuel(ContainerBiomeChanger container) {
        super(container);

        this.teBiomeChanger = container.getBiomeChanger();
        this.fuelEntries = RegistryBiomeChanger.getFuelList();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(Textures.GUI_BIOMECHANGER_I.getResource());
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        if( this.needsScrollBars() ) {
            int scrollX = 163;
            int scrollY = 19 + (int) (66F * this.currentScrollPosition);

            this.drawTexturedModalRect(scrollX + this.guiLeft, scrollY + this.guiTop, 176, 0, 6, 6);
        } else {
            this.drawTexturedModalRect(this.guiLeft + 163, this.guiTop + 19, 176, 6, 6, 6);
        }

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        RenderHelper.disableStandardItemLighting();
        this.fontRenderer.drawString(SAPUtils.getTranslated("tile.enderstuffp:biomeChanger.name"), 8, 8, 0x404040);
        String rangeText = SAPUtils.getTranslated("enderstuffplus.biomeChanger.gui1.range") + " "
                           + ((ContainerBiomeChanger) this.inventorySlots).getBiomeChanger().getMaxRange();
        this.fontRenderer.drawString(rangeText, this.xSize - 8 - this.fontRenderer.getStringWidth(rangeText), 96, 0x808080);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);

        for( int i = this.entryPosition; i < 4 + this.entryPosition && i < this.fuelEntries.size(); i++ ) {
            int multi = this.fuelEntries.get(i).getValue()
                                * (((ContainerBiomeChanger) this.inventorySlots).getBiomeChanger().isReplacingBlocks() ? 4 : 1);
            int needed = ((ContainerBiomeChanger) this.inventorySlots).getBiomeChanger().getMaxRange() * multi;
            boolean isAvailable = needed <= 9 * 64;
            ItemStack stack = this.fuelEntries.get(i).getKey();

            int x = 7, y = 20 + 18 * (i - this.entryPosition);

            RenderHelper.enableGUIStandardItemLighting();

            itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, stack, x, y);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, stack, x, y);

            if( !isAvailable ) {
                Gui.drawRect(x - 1, y - 1, x + 152, y + 17, 0x60000000);
            }

            RenderHelper.disableStandardItemLighting();
            this.fontRenderer.drawString(String.format("items needed: %s", needed), 28, y + 4, !isAvailable ? 0x404040 : 0x5500BB);
        }

        RenderHelper.enableGUIStandardItemLighting();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        boolean leftMouseButtonDown = Mouse.isButtonDown(0);

        int scrollMinX = this.guiLeft + 163;
        int scrollMaxX = scrollMinX + 6;
        int scrollMinY = this.guiTop + 19;
        int scrollMaxY = scrollMinY + 72;

        if( !this.isScrolling && leftMouseButtonDown && this.needsScrollBars() && (mouseX > scrollMinX) && (mouseX < scrollMaxX)
                && (mouseY > scrollMinY) && (mouseY < scrollMaxY) ) {
            this.isScrolling = true;
        } else if( !leftMouseButtonDown ) {
            this.isScrolling = false;
        }

        if( this.isScrolling ) {
            int sY = (int) (66F / (this.fuelEntries.size() - 4));

            for( int y = 0; y < this.fuelEntries.size() - 3; y++ ) {
                if( mouseY > sY * y + this.guiTop || mouseX < sY * y + this.guiTop ) {
                    this.entryPosition = y;
                }
            }

            this.currentScrollPosition = ((mouseY - scrollMinY - 3) / 66F);
        }

        if( this.currentScrollPosition < 0.0F ) {
            this.currentScrollPosition = 0.0F;
        }

        if( this.currentScrollPosition > 1.0F ) {
            this.currentScrollPosition = 1.0F;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void handleMouseInput() {
        int dWheel = Mouse.getEventDWheel();

        super.handleMouseInput();

        if( (dWheel != 0) && this.needsScrollBars() ) {
            if( dWheel < 0 ) {
                this.entryPosition = Math.min(this.entryPosition + 1, this.fuelEntries.size() - 4);
                this.currentScrollPosition = (float) this.entryPosition / ((float) (this.fuelEntries.size() - 4));
            }

            if( dWheel > 0 ) {
                this.entryPosition = Math.max(this.entryPosition - 1, 0);
                this.currentScrollPosition = (float) this.entryPosition / ((float) (this.fuelEntries.size() - 4));
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();

        this.tabFuel.enabled = false;
    }

    private boolean needsScrollBars() {
        return this.fuelEntries.size() > 4;
    }
}
