package sanandreasp.mods.EnderStuffPlus.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.core.manpack.helpers.client.GuiItemTab;
import sanandreasp.core.manpack.managers.SAPLanguageManager;
import sanandreasp.mods.EnderStuffPlus.inventory.Container_Duplicator;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryDuplicator;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class GuiDuplicator extends GuiContainer implements Textures
{
	private int xCoord, yCoord, zCoord;
	
	private GuiButton insertXP;
	
	public GuiDuplicator(Container cont) {
		super(cont);
		allowUserInput = true;
		TileEntityDuplicator dupete = ((Container_Duplicator)cont).duplicator;
		this.xCoord = dupete.xCoord;
		this.yCoord = dupete.yCoord;
		this.zCoord = dupete.zCoord;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		
		this.insertXP = new GuiItemTab( 0, this.guiLeft + this.xSize - 3, this.guiTop + 49, this.translate("enderstuffplus.duplicator.insertXP"), Item.blazeRod.getIconFromDamage(0), true, true, GUI_BUTTONS);
		this.buttonList.add(this.insertXP);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partTicks, int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(GUI_DUPLICATOR);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        RenderHelper.disableStandardItemLighting();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		RenderHelper.disableStandardItemLighting();
		TileEntityDuplicator dupeTile = (TileEntityDuplicator) Minecraft.getMinecraft().theWorld.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
		this.fontRenderer.drawString(this.translate("tile.enderstuffp:duplicator.name"), this.ySize - this.fontRenderer.getStringWidth(translate("tile.duplicator.name")) - 8, 8, 0x404040);
        this.fontRenderer.drawString(this.translate("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
        
        String s = Integer.toString(dupeTile.getStoredLvl());
        this.drawStringWithFrame(this.fontRenderer, s, this.xSize - 8 - this.fontRenderer.getStringWidth(s), 50, 0x80ff20, 0x000000);
        s = Integer.toString(RegistryDuplicator.getNeededExp(dupeTile.getStackInSlot(0)));
        this.drawStringWithFrame(this.fontRenderer, s, this.xSize - 8 - this.fontRenderer.getStringWidth(s), 65, 0x80ff20, 0x000000);
        
		this.mc.getTextureManager().bindTexture(GUI_DUPLICATOR);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        int height = 12 - MathHelper.ceiling_float_int((12F * ((float)dupeTile.getBurnTime() / (float)dupeTile.maxBurnTime)));
        drawTexturedModalRect(60, 40 + height, 176, 14+height, 20, 12-height);
        
        int length = (int) (19 * ((float)dupeTile.getProcTime()) / 18F);
        drawTexturedModalRect(61, 22, 176, 0, length, 14);

        if( mouseX > this.guiLeft + 124
        	&& mouseX <= this.guiLeft + xSize - 7
        	&& mouseY > this.guiTop + 48
        	&& mouseY <= this.guiTop + 59 )
        {
            drawRect(124, 48, this.xSize-7, 59, 0x40FFFFFF);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedModalRect(125, 49, 176, 26, 9, 9);
        	drawCreativeTabHoveringText(translate("enderstuffplus.duplicator.storedXP"), mouseX - this.guiLeft, mouseY - this.guiTop);
    		RenderHelper.disableStandardItemLighting();
        } else {
            drawRect(124, 48, this.xSize-7, 59, 0x40000000);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedModalRect(125, 49, 176, 26, 9, 9);
        }
		this.mc.getTextureManager().bindTexture(GUI_DUPLICATOR);
        if( mouseX > this.guiLeft + 124
        	&& mouseX <= this.guiLeft + xSize - 7 
        	&& mouseY > this.guiTop + 61 
        	&& mouseY <= this.guiTop + 75 )
        {
            drawRect(124, 61, this.xSize-7, 75, 0x40FFFFFF);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedModalRect(125, 62, 185, 26, 11, 12);
        	drawCreativeTabHoveringText(translate("enderstuffplus.duplicator.neededXP"), mouseX - this.guiLeft, mouseY - this.guiTop);
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
			ESPModRegistry.sendPacketSrv("dupeInsLevels", (TileEntityDuplicator) Minecraft.getMinecraft().theWorld.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord));
		} else super.actionPerformed(par1GuiButton);
	}
	
	private void drawStringWithFrame(FontRenderer fontRenderer, String text, int x, int y, int fgColor, int bgColor) {
		fontRenderer.drawString(text, x-1, y, bgColor);
		fontRenderer.drawString(text, x+1, y, bgColor);
		fontRenderer.drawString(text, x, y-1, bgColor);
		fontRenderer.drawString(text, x, y+1, bgColor);
		fontRenderer.drawString(text, x, y, fgColor);
	}
	
	protected String translate(String s) {
		return SAPLanguageManager.getTranslated(s);
	}
}
