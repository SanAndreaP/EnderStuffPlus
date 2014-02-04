package sanandreasp.mods.EnderStuffPlus.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.core.manpack.managers.SAPLanguageManager;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(Side.CLIENT)
public class GuiWeatherAltar extends GuiScreen implements Textures
{
	private TileEntityWeatherAltar altar;
	
    protected int xSize = 226;
    protected int ySize = 107;
    protected int guiLeft;
    protected int guiTop;
    
    private GuiButton btnSun, btnRain, btnStorm;
    private GuiTextField txtDuration;
    
    public GuiWeatherAltar(TileEntityWeatherAltar tileAltar) {
    	this.altar = tileAltar;
    	this.allowUserInput = true;
	}
    
    @SuppressWarnings({ "unchecked" })
	@Override
    public void initGui() {
    	super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        
        this.buttonList.add(this.btnSun = new GuiButton(this.buttonList.size(), this.guiLeft + 10, this.guiTop + 75, 66, 20, SAPLanguageManager.getTranslated("enderstuffplus.weatherAltar.sun")));
        this.buttonList.add(this.btnRain = new GuiButton(this.buttonList.size(), this.guiLeft + 10+67, this.guiTop + 75, 66, 20, SAPLanguageManager.getTranslated("enderstuffplus.weatherAltar.rain")));
        this.buttonList.add(this.btnStorm = new GuiButton(this.buttonList.size(), this.guiLeft + 10+134, this.guiTop + 75, 66, 20, SAPLanguageManager.getTranslated("enderstuffplus.weatherAltar.thunder")));
        
        this.txtDuration = new GuiTextField(this.fontRenderer, this.guiLeft + 10, this.guiTop + 45, 200, 15);
        this.txtDuration.setText("1");
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partTicks) {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_WEATHERALTAR);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		this.fontRenderer.drawString(SAPLanguageManager.getTranslated("tile.enderstuffp:weatherAltar.name"), this.guiLeft + 6, this.guiTop + 6, 0x808080);
        this.fontRenderer.drawString(SAPLanguageManager.getTranslated("enderstuffplus.weatherAltar.duration"), this.guiLeft + 12, this.guiTop + 35, 0x808080);
		
		this.txtDuration.drawTextBox();
		
		if( this.getDurationInt() <= 0 ) {
			this.txtDuration.setTextColor(0xFF0000);
		} else {
			this.txtDuration.setTextColor(0xFFFFFF);
		}
		
		super.drawScreen(mouseX, mouseY, partTicks);
	}
	
	private int getDurationInt() {
		try {
			int dur = Integer.parseInt(this.txtDuration.getText());
			return this.altar.isValidDuration(dur) ? dur : 0;
		} catch(NumberFormatException e) {
			return 0;
		}
	}
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }
	
    @Override
	public void updateScreen()
    {
    	super.updateScreen();
    	this.txtDuration.updateCursorCounter();
    }
    
    @Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseBtn)
    {
        super.mouseClicked(mouseX, mouseY, mouseBtn);
        this.txtDuration.mouseClicked(mouseX, mouseY, mouseBtn);
    }
	
    @Override
	protected void keyTyped(char key, int keyCode) {
    	this.txtDuration.textboxKeyTyped(key, keyCode);

    	if( (keyCode == 28 || keyCode == 1) && this.txtDuration.isFocused() ) {
    		this.txtDuration.setFocused(false);
    	} else if( (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.keyCode)
    			   && !this.txtDuration.isFocused() )
    	{
    		this.mc.thePlayer.closeScreen();
    	}
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
    	int dur = getDurationInt();
    	if( dur > 0 ) {
	    	if( button.id == this.btnSun.id ) {
	    		ESPModRegistry.sendPacketSrv("setWeather", 0, dur, this.altar.xCoord, this.altar.yCoord, this.altar.zCoord, this.mc.theWorld);
	    		this.mc.thePlayer.closeScreen();
	    	} else if( button.id == this.btnRain.id ) {
	    		ESPModRegistry.sendPacketSrv("setWeather", 1, dur, this.altar.xCoord, this.altar.yCoord, this.altar.zCoord, this.mc.theWorld);
	    		this.mc.thePlayer.closeScreen();
	    	} else if( button.id == this.btnStorm.id ) {
	    		ESPModRegistry.sendPacketSrv("setWeather", 2, dur, this.altar.xCoord, this.altar.yCoord, this.altar.zCoord, this.mc.theWorld);
	    		this.mc.thePlayer.closeScreen();
	    	}
    	}
    }
}
