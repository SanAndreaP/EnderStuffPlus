package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.core.manpack.managers.SAPLanguageManager;
import sanandreasp.mods.EnderStuffPlus.inventory.Container_BiomeChanger;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

@SideOnly(Side.CLIENT)
public class GuiBiomeChangerFuel extends GuiBiomeChangerBase implements Textures
{
	private HashMap<Integer, Entry<ItemStack, Integer>> fuelEntries;
	private int entryPos = 0;
	private boolean isScrolling = false;
	private float currScrollPos = 0F;
	
	public GuiBiomeChangerFuel(Container cont) {
		super(cont);
		this.bcte = ((Container_BiomeChanger)cont).biomeChanger;
				
		this.fuelEntries = RegistryBiomeChanger.getFuelList();
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.tabFuel.enabled = false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partTicks, int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_I);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
        
        if( needsScrollBars() ) {
	        int scrollX = 163;
	        int scrollY = 19 + (int)(66F * currScrollPos);
	        drawTexturedModalRect(scrollX + this.guiLeft, scrollY + this.guiTop, 176, 0, 6, 6);
        } else {
	        drawTexturedModalRect(this.guiLeft + 163, this.guiTop + 19, 176, 6, 6, 6);
        }
        
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		RenderHelper.disableStandardItemLighting();
		this.fontRenderer.drawString(SAPLanguageManager.getTranslated("tile.enderstuffp:biomeChanger.name"), 8, 8, 0x404040);
		String s = SAPLanguageManager.getTranslated("enderstuffplus.biomeChanger.gui1.range") + " " + ((Container_BiomeChanger)this.inventorySlots).biomeChanger.getMaxRange();
		this.fontRenderer.drawString(s, this.xSize - 8 - this.fontRenderer.getStringWidth(s), 96, 0x808080);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
		
        for( int i = this.entryPos; i < 4 + this.entryPos && i < this.fuelEntries.size(); i++ ) {
    		RenderHelper.enableGUIStandardItemLighting();
        	ItemStack is = this.fuelEntries.get(i).getKey();
        	int multi = this.fuelEntries.get(i).getValue() * (((Container_BiomeChanger)this.inventorySlots).biomeChanger.isReplacingBlocks ? 4 : 1);
            int needed = ((Container_BiomeChanger)this.inventorySlots).biomeChanger.getMaxRange() * multi;
            
        	boolean inavailable = needed > 9*64;
        	
        	int x = 7, y = 20 + 18*(i-this.entryPos);

            itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, is, x, y);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, is, x, y);
            
        	if( inavailable ) {
                Gui.drawRect(x-1, y-1, x+152, y+17, 0x60000000);
        	}
    		RenderHelper.disableStandardItemLighting();
            this.fontRenderer.drawString(String.format("items needed: %s", needed), 28, y + 4, inavailable ? 0x404040 : 0x5500BB);
        }
		RenderHelper.enableGUIStandardItemLighting();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partTicks) {
        boolean leftMBDown = Mouse.isButtonDown(0);
        
        int scrollMinX = guiLeft + 163;
        int scrollMaxX = scrollMinX + 6;
        int scrollMinY = guiTop + 19;
        int scrollMaxY = scrollMinY + 72;
        
        if( !this.isScrolling && leftMBDown && this.needsScrollBars() && mouseX > scrollMinX && mouseX < scrollMaxX && mouseY > scrollMinY && mouseY < scrollMaxY ) {
        	this.isScrolling = true;
        } else if( !leftMBDown ) {
        	this.isScrolling = false;
        }
        
        if( this.isScrolling ) {
        	int sY = (int) (66F / (float)(this.fuelEntries.size() - 4));
	        for( int y = 0; y < this.fuelEntries.size() - 3; y++ ) {
	        	if( mouseY > sY * y + this.guiTop || mouseX < sY * y + this.guiTop ) {
	        		this.entryPos = y;
	        	}
	        }
	        this.currScrollPos = ((float)(mouseY - scrollMinY - 3) / 66F);
        }
        
        if( this.currScrollPos < 0.0F ) {
        	this.currScrollPos = 0.0F;
        }
        if( this.currScrollPos > 1.0F ) {
        	this.currScrollPos = 1.0F;
        }
		
		super.drawScreen(mouseX, mouseY, partTicks);
	}
	
	private boolean needsScrollBars() {
		return this.fuelEntries.size() > 4;
	}
	
	@Override
	public void handleMouseInput() {
        super.handleMouseInput();
        int dWheel = Mouse.getEventDWheel();

        if( dWheel != 0 && this.needsScrollBars() ) {
            if( dWheel < 0 ) {
                this.entryPos = Math.min(this.entryPos + 1, this.fuelEntries.size() - 4);
                this.currScrollPos = (float)this.entryPos / ((float)(this.fuelEntries.size() - 4));
            }

            if( dWheel > 0 ) {
            	this.entryPos = Math.max(this.entryPos - 1, 0);
            	this.currScrollPos = (float)this.entryPos / ((float)(this.fuelEntries.size() - 4));
            }
        }
    }
}
