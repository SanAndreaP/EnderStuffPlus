package sanandreasp.mods.EnderStuffPlus.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;
import sanandreasp.core.manpack.managers.SAPLanguageManager;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnderPet extends GuiScreen
{
	private IEnderPet enderPet;
	private EntityPlayer owner;
	private int yPos;
	
	private GuiTextField nameTxt;

	public GuiEnderPet(IEnderPet pet, EntityPlayer player) {
		this.enderPet = pet;
		this.owner = player;
        allowUserInput = true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		
		this.yPos = (this.height - 156) / 2;
		
		this.nameTxt = new GuiTextField(this.fontRenderer, (this.width - 198) / 2, this.yPos + 30, 198, 15);
		String name = this.enderPet.getName();
		this.nameTxt.setText(name.isEmpty() ? translate("rename") : name);
		
		int color = 0xFFFFFF;
        if( this.enderPet.getEntity() instanceof EntityEnderMiss ) {
        	color = 0xFF9090;
        } else if( this.enderPet.getEntity() instanceof EntityEnderAvis ) {
        	color = 0xFF00FF;
        }
		
    	GuiButton btn1 = new GuiButtonPetGUI(0, (this.width - 200) / 2, yPos + 55, 200, 15, translate("mount"), color).setBGAlpha(128);
    	boolean canMount = true;
    	if( this.enderPet.isSitting() ) {
    		canMount = false;
    	} else if( this.enderPet.getEntity() instanceof EntityEnderAvis 
    			   && !((EntityEnderAvis)enderPet.getEntity()).isSaddled() )
    	{
    		canMount = false;
    	}
    	btn1.enabled = canMount;
    	this.buttonList.add(btn1);
    	GuiButton btn2 = new GuiButtonPetGUI(1, (this.width - 200) / 2, this.yPos + 71, 200, 15, this.enderPet.isSitting() ? this.translate("standUp") : translate("sit"), color).setBGAlpha(128);
    	this.buttonList.add(btn2);
    	GuiButton btn3 = new GuiButtonPetGUI(2, (this.width - 200) / 2, this.yPos + 87, 200, 15, this.enderPet.isFollowing() ? this.translate("stay") : translate("follow"), color).setBGAlpha(128);
    	btn3.enabled = !enderPet.isSitting();
    	this.buttonList.add(btn3);
    	GuiButton btn4 = new GuiButtonPetGUI(3, (this.width - 200) / 2, this.yPos + 103, 200, 15, this.translate("putIntoEgg"), color).setBGAlpha(128);
    	btn4.enabled = this.owner.inventory.hasItemStack(new ItemStack(Item.egg)) || this.owner.capabilities.isCreativeMode;
    	this.buttonList.add(btn4);
    	GuiButton btn5 = new GuiButtonPetGUI(4, (this.width - 200) / 2, this.yPos + 139, 200, 15, this.translate("close"), 0xA0A0A0).setBGAlpha(128);
    	this.buttonList.add(btn5);
	}
	
	private String translate(String key) {
		return SAPLanguageManager.getTranslated("enderstuffplus.guipet."+key);
	}
	
	private String translateFormat(String key, Object... obj) {
		return SAPLanguageManager.getTranslatedFormat("enderstuffplus.guipet."+key, obj);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partTicks) {
        this.drawDefaultBackground();
        String title = "";
        int color = 0xFFFFFF;
        
        String petName = enderPet.getName().isEmpty() 
        					? EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET
        					: enderPet.getName();
        if( enderPet.getEntity() instanceof EntityEnderMiss ) {
        	title = translateFormat("title.miss", petName);
        	color = 0xFF9090;
        } else if( enderPet.getEntity() instanceof EntityEnderAvis ) {
        	title = translateFormat("title.avis", petName);
        	color = 0xFF00FF;
        }
        
        this.fontRenderer.drawString(title, (this.width - this.getRealStringWidth(title)) / 2, this.yPos + 10, color);
        	
    	this.drawGradientRect((this.width - 205) / 2 - 5, 0, (this.width - 205) / 2 - 4, this.height / 2, 0x00000000+color, 0xFF000000+color);
    	this.drawGradientRect((this.width - 205) / 2 - 5, this.height / 2, (this.width - 205) / 2 - 4, this.height, 0xFF000000+color, 0x00000000+color);
    	this.drawGradientRect((this.width + 205) / 2 + 4, 0, (this.width + 205) / 2 + 5, this.height / 2, 0x00000000+color, 0xFF000000+color);
    	this.drawGradientRect((this.width + 205) / 2 + 4, this.height / 2, (this.width + 205) / 2 + 5, this.height, 0xFF000000+color, 0x00000000+color);
    	GL11.glColor4f(1f, 1f, 1f, 0f);
    	
    	this.fontRenderer.drawString(translate("name"), (this.width - 200) / 2, 20 + this.yPos, 0xFFFFFF);
        
        this.nameTxt.drawTextBox();
                
        super.drawScreen(mouseX, mouseY, partTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if( button.id == 4 ) {
    		this.writeEntityName();
    		this.mc.thePlayer.closeScreen();
    		return;
		}
		
		ESPModRegistry.sendPacketSrv("enderGuiAction", ((Entity)this.enderPet).entityId, (byte)button.id);
		this.mc.thePlayer.closeScreen();
	}
	
    @Override
	public void updateScreen() {
    	super.updateScreen();
    	this.nameTxt.updateCursorCounter();
    	if( this.nameTxt.isFocused() && this.nameTxt.getText().equals(this.translate("rename")) )
    		this.nameTxt.setText("");
    }
    
    @Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseBtn) {
        super.mouseClicked(mouseX, mouseY, mouseBtn);
        this.nameTxt.mouseClicked(mouseX, mouseY, mouseBtn);
    }
    
    @Override
	protected void keyTyped(char key, int keyCode) {
    	this.nameTxt.textboxKeyTyped(key, keyCode);

    	if( (keyCode == 28 || keyCode == 1) && this.nameTxt.isFocused() ) {
    		this.nameTxt.setFocused(false);
    		writeEntityName();
    		if( this.nameTxt.getText().isEmpty() ) {
    			this.nameTxt.setText(this.translate("rename"));
    		}
    	} else if( (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.keyCode)
    			   && !this.nameTxt.isFocused() )
    	{
    		this.writeEntityName();
    		this.mc.thePlayer.closeScreen();
    	}
    }
    
    private void writeEntityName() {
    	if( this.nameTxt.getText().isEmpty() || this.nameTxt.getText().equals(translate("rename")) ) {
    		return;
    	}
    	
    	ESPModRegistry.sendPacketSrv("setEnderName", ((Entity)enderPet).entityId, this.nameTxt.getText());
    }
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
    
    private int getRealStringWidth(String str) {
    	String strippedString = str.replaceAll("\247.", "");
    	return this.fontRenderer.getStringWidth(strippedString);
    }
}
