package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import sanandreasp.core.manpack.helpers.client.GuiItemTab;
import sanandreasp.core.manpack.managers.SAPLanguageManager;
import sanandreasp.core.manpack.mod.packet.PacketRegistry;
import sanandreasp.mods.EnderStuffPlus.client.registry.IconRegistry;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;

public abstract class GuiBiomeChangerBase extends GuiContainer {
	
	protected GuiItemTab tabActivate, tabFuel, tabBiomes, tabCofig;
	protected TileEntityBiomeChanger bcte;
	
	public GuiBiomeChangerBase(Container par1Container) {
		super(par1Container);
		this.ySize = 222;
		this.allowUserInput = true;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void initGui() {
		super.initGui();
		
		buttonList.add(this.tabActivate = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + this.ySize - 35, "Activate", IconRegistry.activeOn, true, false, Textures.GUI_BUTTONS));
		buttonList.add(this.tabFuel = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 10, "Fuel", Item.blazePowder.getIconFromDamage(0), true, false, Textures.GUI_BUTTONS));
		buttonList.add(this.tabBiomes = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 36, "Biome", Block.sapling.getIcon(0, 0), true, false, Textures.GUI_BUTTONS));
		buttonList.add(this.tabCofig = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 62, "Settings", IconRegistry.spanner, true, false, Textures.GUI_BUTTONS));
	
		this.tabActivate.textureBaseX = 52;
		this.tabBiomes.baseTexture = TextureMap.locationBlocksTexture;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.tabActivate.setIcon(bcte.isActive() ? IconRegistry.activeOff : IconRegistry.activeOn);
		
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		RenderHelper.enableGUIStandardItemLighting();
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		int id = par1GuiButton.id;
		if( id == tabActivate.id ) {
			if( bcte.isActive() ) {
				PacketRegistry.sendPacketToServer(ESPModRegistry.modID, "bcGuiAction", this.bcte, (byte) 0, 0);
//				PacketBCGUIAction.send(this.bcte, (byte) 0, 0);
				this.mc.displayGuiScreen(null);
			} else {
				PacketRegistry.sendPacketToServer(ESPModRegistry.modID, "bcGuiAction", this.bcte, (byte) 0, 0);
//				PacketBCGUIAction.send(this.bcte, (byte) 0, 0);
				this.mc.displayGuiScreen(null);
			}
		} else if( id == tabFuel.id ) {
			PacketRegistry.sendPacketToServer(ESPModRegistry.modID, "bcGuiChange", 1, this.bcte.xCoord, this.bcte.yCoord, this.bcte.zCoord, this.mc.thePlayer);
//			PacketChangeBCGUI.send(1, this.bcte.xCoord, this.bcte.yCoord, this.bcte.zCoord, this.mc.thePlayer);
		} else if( id == tabBiomes.id ) {
			PacketRegistry.sendPacketToServer(ESPModRegistry.modID, "bcGuiChange", 2, this.bcte.xCoord, this.bcte.yCoord, this.bcte.zCoord, this.mc.thePlayer);
//			PacketChangeBCGUI.send(2, this.bcte.xCoord, this.bcte.yCoord, this.bcte.zCoord, this.mc.thePlayer);
		} else if( id == tabCofig.id ) {
			PacketRegistry.sendPacketToServer(ESPModRegistry.modID, "bcGuiChange", 3, this.bcte.xCoord, this.bcte.yCoord, this.bcte.zCoord, this.mc.thePlayer);
//			PacketChangeBCGUI.send(3, this.bcte.xCoord, this.bcte.yCoord, this.bcte.zCoord, this.mc.thePlayer);
		}
	}
	
	protected String translate(String s) {
		return SAPLanguageManager.getTranslated(s);
	}

}
