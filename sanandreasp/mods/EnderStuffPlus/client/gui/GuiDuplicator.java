package sanandreasp.mods.EnderStuffPlus.client.gui;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.inventory.Container_BiomeChanger;
import sanandreasp.mods.EnderStuffPlus.inventory.Container_Duplicator;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvDupeInsertLevels;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryDuplicator;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public class GuiDuplicator extends GuiContainer implements Textures {
	private int xCoord, yCoord, zCoord;
	
	private GuiButton insertXP;
	
	public GuiDuplicator(Container par1Container) {
		super(par1Container);
		allowUserInput = true;
		TileEntityDuplicator dupete = ((Container_Duplicator)par1Container).duplicator;
		this.xCoord = dupete.xCoord;
		this.yCoord = dupete.yCoord;
		this.zCoord = dupete.zCoord;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		this.insertXP = new GuiItemTab( 0, this.guiLeft + this.xSize - 3, this.guiTop + 49, Item.blazeRod.getIconFromDamage(0), translate("enderstuffplus.duplicator.insertXP"), true, true);
		this.buttonList.add(this.insertXP);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		this.mc.getTextureManager().bindTexture(GUI_DUPLICATOR);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int l = guiLeft;
        int i1 = guiTop;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        RenderHelper.disableStandardItemLighting();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		RenderHelper.disableStandardItemLighting();
		TileEntityDuplicator dupete = (TileEntityDuplicator) Minecraft.getMinecraft().theWorld.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
		this.fontRenderer.drawString(translate("tile.enderstuffp:duplicator.name"), this.ySize - fontRenderer.getStringWidth(translate("tile.duplicator.name")) - 8, 8, 0x404040);
        this.fontRenderer.drawString(translate("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
        
        String s = Integer.toString(dupete.getStoredLvl());
        this.drawStringWithFrame(this.fontRenderer, s, this.xSize - 8 - this.fontRenderer.getStringWidth(s), 50, 0x80ff20, 0x000000);
        s = Integer.toString(RegistryDuplicator.getNeededExp(dupete.getStackInSlot(0)));
        this.drawStringWithFrame(this.fontRenderer, s, this.xSize - 8 - this.fontRenderer.getStringWidth(s), 65, 0x80ff20, 0x000000);
        
		this.mc.getTextureManager().bindTexture(GUI_DUPLICATOR);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        int height = 12 - MathHelper.ceiling_float_int((12F * ((float)dupete.getBurnTime() / (float)dupete.maxBurnTime)));
        drawTexturedModalRect(60, 40 + height, 176, 14+height, 20, 12-height);
        
        int length = (int) (19 * ((float)dupete.getProcTime()) / 18F);
        drawTexturedModalRect(61, 22, 176, 0, length, 14);

        if( par1 > this.guiLeft + 124 && par1 <= this.guiLeft + xSize - 7 && par2 > this.guiTop + 48 && par2 <= this.guiTop + 59 ) {
            drawRect(124, 48, this.xSize-7, 59, 0x40FFFFFF);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedModalRect(125, 49, 176, 26, 9, 9);
        	drawCreativeTabHoveringText(translate("enderstuffplus.duplicator.storedXP"), par1 - this.guiLeft, par2 - this.guiTop);
    		RenderHelper.disableStandardItemLighting();
        } else {
            drawRect(124, 48, this.xSize-7, 59, 0x40000000);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedModalRect(125, 49, 176, 26, 9, 9);
        }
		this.mc.getTextureManager().bindTexture(GUI_DUPLICATOR);
        if( par1 > this.guiLeft + 124 && par1 <= this.guiLeft + xSize - 7 && par2 > this.guiTop + 61 && par2 <= this.guiTop + 75 ) {
            drawRect(124, 61, this.xSize-7, 75, 0x40FFFFFF);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedModalRect(125, 62, 185, 26, 11, 12);
        	drawCreativeTabHoveringText(translate("enderstuffplus.duplicator.neededXP"), par1 - this.guiLeft, par2 - this.guiTop);
    		RenderHelper.disableStandardItemLighting();
        } else {
            drawRect(124, 61, this.xSize-7, 75, 0x40000000);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedModalRect(125, 62, 185, 26, 11, 12);
        }

		this.mc.getTextureManager().bindTexture(GUI_DUPLICATOR);
        
		RenderHelper.enableGUIStandardItemLighting();
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if( par1GuiButton.id == this.insertXP.id ) {
			PacketRecvDupeInsertLevels.send((TileEntityDuplicator) Minecraft.getMinecraft().theWorld.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord));
		} else super.actionPerformed(par1GuiButton);
	}
	
	private void drawStringWithFrame(FontRenderer par0FontRenderer, String par1String, int par2X, int par3Y, int par4FgColor, int par5BgColor) {
		par0FontRenderer.drawString(par1String, par2X-1, par3Y, par5BgColor);
		par0FontRenderer.drawString(par1String, par2X+1, par3Y, par5BgColor);
		par0FontRenderer.drawString(par1String, par2X, par3Y-1, par5BgColor);
		par0FontRenderer.drawString(par1String, par2X, par3Y+1, par5BgColor);
		par0FontRenderer.drawString(par1String, par2X, par3Y, par4FgColor);
	}
	
	protected String translate(String s) {
		return ESPModRegistry.manHelper.getLangMan().getTranslated(s);
	}
}
