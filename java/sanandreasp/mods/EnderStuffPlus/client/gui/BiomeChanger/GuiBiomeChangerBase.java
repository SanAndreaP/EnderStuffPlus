package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.core.manpack.helpers.client.GuiItemTab;
import sanandreasp.core.manpack.managers.SAPLanguageManager;
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

@SideOnly(Side.CLIENT)
public abstract class GuiBiomeChangerBase extends GuiContainer
{
	protected GuiItemTab tabActivate, tabFuel, tabBiomes, tabCofig;
	protected TileEntityBiomeChanger bcte;
	
	public GuiBiomeChangerBase(Container cont) {
		super(cont);
		this.ySize = 222;
		this.allowUserInput = true;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void initGui() {
		super.initGui();
		
		this.buttonList.add(this.tabActivate = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + this.ySize - 35, "Activate", IconRegistry.activeOn, true, false, Textures.GUI_BUTTONS));
		this.buttonList.add(this.tabFuel = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 10, "Fuel", Item.blazePowder.getIconFromDamage(0), true, false, Textures.GUI_BUTTONS));
		this.buttonList.add(this.tabBiomes = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 36, "Biome", Block.sapling.getIcon(0, 0), true, false, Textures.GUI_BUTTONS));
		this.buttonList.add(this.tabCofig = new GuiItemTab(this.buttonList.size(), this.guiLeft + this.xSize - 3, this.guiTop + 62, "Settings", IconRegistry.spanner, true, false, Textures.GUI_BUTTONS));
	
		this.tabActivate.textureBaseX = 52;
		this.tabBiomes.baseTexture = TextureMap.locationBlocksTexture;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partTicks) {
		this.tabActivate.setIcon(bcte.isActive() ? IconRegistry.activeOff : IconRegistry.activeOn);
		
		super.drawScreen(mouseX, mouseY, partTicks);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		RenderHelper.enableGUIStandardItemLighting();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		int id = button.id;
		if( id == this.tabActivate.id ) {
			if(this. bcte.isActive() ) {
				ESPModRegistry.sendPacketSrv("bcGuiAction", this.bcte, (byte) 0, 0);
				this.mc.displayGuiScreen(null);
			} else {
				ESPModRegistry.sendPacketSrv("bcGuiAction", this.bcte, (byte) 0, 0);
				this.mc.displayGuiScreen(null);
			}
		} else if( id == this.tabFuel.id ) {
			ESPModRegistry.sendPacketSrv("bcGuiChange", 1, this.bcte.xCoord, this.bcte.yCoord, this.bcte.zCoord, this.mc.thePlayer);
		} else if( id == this.tabBiomes.id ) {
			ESPModRegistry.sendPacketSrv("bcGuiChange", 2, this.bcte.xCoord, this.bcte.yCoord, this.bcte.zCoord, this.mc.thePlayer);
		} else if( id == this.tabCofig.id ) {
			ESPModRegistry.sendPacketSrv("bcGuiChange", 3, this.bcte.xCoord, this.bcte.yCoord, this.bcte.zCoord, this.mc.thePlayer);
		}
	}
	
	protected String translate(String s) {
		return SAPLanguageManager.getTranslated(s);
	}
}
