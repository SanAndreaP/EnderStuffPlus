package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import javax.swing.text.TabableView;

import sanandreasp.mods.EnderStuffPlus.client.gui.GuiItemTab;
import sanandreasp.mods.EnderStuffPlus.client.registry.IconRegistry;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvBCGUIAction;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvChangeBCGUI;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

public abstract class GuiBiomeChangerBase extends GuiContainer {
	
	protected GuiItemTab tabActivate, tabFuel, tabBiomes, tabCofig;
	protected TileEntityBiomeChanger bcte;
	
	public GuiBiomeChangerBase(Container par1Container) {
		super(par1Container);
		this.ySize = 222;
		this.allowUserInput = true;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		buttonList.add(this.tabActivate = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + this.ySize - 35, IconRegistry.activeOn, "Activate", true, false));
		buttonList.add(this.tabFuel = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 10, Item.blazePowder.getIconFromDamage(0), "Fuel", true, false));
		buttonList.add(this.tabBiomes = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 36, Block.sapling.getIcon(0, 0), "Biome", true, false));
		buttonList.add(this.tabCofig = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 62, IconRegistry.spanner, "Settings", true, false));
	
		this.tabActivate.textureBaseX = 52;
		this.tabBiomes.baseTexture = TextureMap.locationItemsTexture;
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
				PacketRecvBCGUIAction.send(this.bcte, (byte) 0, 0);
				this.mc.displayGuiScreen(null);
			} else {
				PacketRecvBCGUIAction.send(this.bcte, (byte) 0, 0);
				this.mc.displayGuiScreen(null);
			}
		} else if( id == tabFuel.id ) {
			PacketRecvChangeBCGUI.send(1, this.bcte.xCoord, this.bcte.yCoord, this.bcte.zCoord, this.mc.thePlayer);
		} else if( id == tabBiomes.id ) {
			PacketRecvChangeBCGUI.send(2, this.bcte.xCoord, this.bcte.yCoord, this.bcte.zCoord, this.mc.thePlayer);
		} else if( id == tabCofig.id ) {
			PacketRecvChangeBCGUI.send(3, this.bcte.xCoord, this.bcte.yCoord, this.bcte.zCoord, this.mc.thePlayer);
		}
	}
	
	protected String translate(String s) {
		return ESPModRegistry.manHelper.getLangMan().getTranslated(s);
	}

}
