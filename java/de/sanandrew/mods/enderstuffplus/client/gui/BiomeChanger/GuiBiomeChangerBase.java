package de.sanandrew.mods.enderstuffplus.client.gui.BiomeChanger;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.core.manpack.util.client.GuiItemTab;
import de.sanandrew.mods.enderstuffplus.client.event.IconRegistry;
import de.sanandrew.mods.enderstuffplus.packet.PacketBCGUIAction;
import de.sanandrew.mods.enderstuffplus.packet.PacketChangeBCGUI;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;
import de.sanandrew.mods.enderstuffplus.registry.Textures;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityBiomeChanger;

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
            IPacket packet = new PacketBCGUIAction(this.teBiomeChanger, 0, 0);
            ESPModRegistry.channelHandler.sendToServer(packet);
            this.mc.thePlayer.closeScreen();
        } else if( button.id == this.tabFuel.id ) {
            this.mc.thePlayer.openGui(ESPModRegistry.instance, 1, this.mc.theWorld, this.teBiomeChanger.xCoord, this.teBiomeChanger.yCoord, this.teBiomeChanger.zCoord);
            IPacket packet = new PacketChangeBCGUI(1, this.teBiomeChanger.xCoord, this.teBiomeChanger.yCoord, this.teBiomeChanger.zCoord);
            ESPModRegistry.channelHandler.sendToServer(packet);
        } else if( button.id == this.tabBiomes.id ) {
            this.mc.thePlayer.openGui(ESPModRegistry.instance, 2, this.mc.theWorld, this.teBiomeChanger.xCoord, this.teBiomeChanger.yCoord, this.teBiomeChanger.zCoord);
            IPacket packet = new PacketChangeBCGUI(2, this.teBiomeChanger.xCoord, this.teBiomeChanger.yCoord, this.teBiomeChanger.zCoord);
            ESPModRegistry.channelHandler.sendToServer(packet);
        } else if( button.id == this.tabCofig.id ) {
            this.mc.thePlayer.openGui(ESPModRegistry.instance, 3, this.mc.theWorld, this.teBiomeChanger.xCoord, this.teBiomeChanger.yCoord, this.teBiomeChanger.zCoord);
            IPacket packet = new PacketChangeBCGUI(3, this.teBiomeChanger.xCoord, this.teBiomeChanger.yCoord, this.teBiomeChanger.zCoord);
            ESPModRegistry.channelHandler.sendToServer(packet);
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
        this.tabActivate = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + this.ySize - 35, "Activate",
                                          IconRegistry.guiActiveOn, true, false, Textures.GUI_BUTTONS.getResource());
        this.buttonList.add(this.tabActivate);

        this.tabFuel = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 10, "Fuel",
                                      Items.blaze_powder.getIconFromDamage(0), true, false, Textures.GUI_BUTTONS.getResource());
        this.buttonList.add(this.tabFuel);

        this.tabBiomes = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 36, "Biome",
                                        Blocks.sapling.getIcon(0, 0), true, false, Textures.GUI_BUTTONS.getResource());
        this.buttonList.add(this.tabBiomes);

        this.tabCofig = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 62, "Settings",
                                       IconRegistry.guiSpanner, true, false, Textures.GUI_BUTTONS.getResource());
        this.buttonList.add(this.tabCofig);

        this.tabActivate.textureBaseX = 52;
        this.tabBiomes.baseTexture = TextureMap.locationBlocksTexture;
    }
}
