package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import sanandreasp.core.manpack.helpers.client.GuiItemTab;
import sanandreasp.mods.EnderStuffPlus.client.event.IconRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiBiomeChangerBase
    extends GuiContainer
{
    protected GuiItemTab tabActivate;
    protected GuiItemTab tabFuel;
    protected GuiItemTab tabBiomes;
    protected GuiItemTab tabCofig;
    protected TileEntityBiomeChanger teBiomeChanger;

    public GuiBiomeChangerBase(Container container) {
        super(container);
        this.ySize = 222;
        this.allowUserInput = true;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if( button.id == this.tabActivate.id ) {
            if( this.teBiomeChanger.isActive() ) {
                ESPModRegistry.sendPacketSrv("bcGuiAction", this.teBiomeChanger, (byte) 0, 0);
                this.mc.displayGuiScreen(null);
            } else {
                ESPModRegistry.sendPacketSrv("bcGuiAction", this.teBiomeChanger, (byte) 0, 0);
                this.mc.displayGuiScreen(null);
            }
        } else if( button.id == this.tabFuel.id ) {
            ESPModRegistry.sendPacketSrv("bcGuiChange", 1, this.teBiomeChanger.xCoord, this.teBiomeChanger.yCoord,
                                         this.teBiomeChanger.zCoord, this.mc.thePlayer);
        } else if( button.id == this.tabBiomes.id ) {
            ESPModRegistry.sendPacketSrv("bcGuiChange", 2, this.teBiomeChanger.xCoord, this.teBiomeChanger.yCoord,
                                         this.teBiomeChanger.zCoord, this.mc.thePlayer);
        } else if( button.id == this.tabCofig.id ) {
            ESPModRegistry.sendPacketSrv("bcGuiChange", 3, this.teBiomeChanger.xCoord, this.teBiomeChanger.yCoord,
                                         this.teBiomeChanger.zCoord, this.mc.thePlayer);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        RenderHelper.enableGUIStandardItemLighting();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.tabActivate.setIcon(this.teBiomeChanger.isActive() ? IconRegistry.guiActiveOff : IconRegistry.guiActiveOn);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initGui() {
        super.initGui();
        this.tabActivate = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3,
                                          this.guiTop + this.ySize - 35, "Activate", IconRegistry.guiActiveOn, true,
                                          false, Textures.GUI_BUTTONS);
        this.buttonList.add(this.tabActivate);

        this.tabFuel = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 10, "Fuel",
                                      Item.blazePowder.getIconFromDamage(0), true, false, Textures.GUI_BUTTONS);
        this.buttonList.add(this.tabFuel);

        this.tabBiomes = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 36,
                                        "Biome", Block.sapling.getIcon(0, 0), true, false, Textures.GUI_BUTTONS);
        this.buttonList.add(this.tabBiomes);

        this.tabCofig = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 62,
                                       "Settings", IconRegistry.guiSpanner, true, false, Textures.GUI_BUTTONS);
        this.buttonList.add(this.tabCofig);

        this.tabActivate.textureBaseX = 52;
        this.tabBiomes.baseTexture = TextureMap.locationBlocksTexture;
    }
}
