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
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvEnderName;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvEnderPetGUIAction;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnderPet extends GuiScreen {
	
	private IEnderPet pet;
	private EntityPlayer player;
	private int yPos;
	
	private GuiTextField nameTxt;

	public GuiEnderPet(IEnderPet par1Entity, EntityPlayer par2Player) {
		pet = par1Entity;
		player = par2Player;
        allowUserInput = true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		
        yPos = (this.height - 156) / 2;
		
		nameTxt = new GuiTextField(this.fontRenderer, (this.width - 198) / 2, yPos + 30, 198, 15);
		String name = pet.getName();
		nameTxt.setText(name.isEmpty() ? translate("rename") : name);
		
		int color = 0xFFFFFF;
        if( pet.getEntity() instanceof EntityEnderMiss ) {
        	color = 0xFF9090;
        } else if( pet.getEntity() instanceof EntityEnderAvis ) {
        	color = 0xFF00FF;
        }
		
    	GuiButton btn1 = new GuiButtonPetGUI(0, (this.width - 200) / 2, yPos + 55, 200, 15, translate("mount"), color).setBGAlpha(128);
    	boolean canMount = true;
    	if( pet.isSitting() )
    		canMount = false;
    	else if( pet.getEntity() instanceof EntityEnderAvis && !((EntityEnderAvis)pet.getEntity()).isSaddled() ) {
    		canMount = false;
    	}
    	btn1.enabled = canMount;
    	this.buttonList.add(btn1);
    	GuiButton btn2 = new GuiButtonPetGUI(1, (this.width - 200) / 2, yPos + 71, 200, 15, pet.isSitting() ? translate("standUp") : translate("sit"), color).setBGAlpha(128);
    	this.buttonList.add(btn2);
    	GuiButton btn3 = new GuiButtonPetGUI(2, (this.width - 200) / 2, yPos + 87, 200, 15, pet.isFollowing() ? translate("stay") : translate("follow"), color).setBGAlpha(128);
    	btn3.enabled = !pet.isSitting();
    	this.buttonList.add(btn3);
    	GuiButton btn4 = new GuiButtonPetGUI(3, (this.width - 200) / 2, yPos + 103, 200, 15, translate("putIntoEgg"), color).setBGAlpha(128);
    	btn4.enabled = this.player.inventory.hasItemStack(new ItemStack(Item.egg)) || this.player.capabilities.isCreativeMode;
    	this.buttonList.add(btn4);
    	GuiButton btn5 = new GuiButtonPetGUI(4, (this.width - 200) / 2, yPos + 139, 200, 15, translate("close"), 0xA0A0A0).setBGAlpha(128);
    	this.buttonList.add(btn5);
	}
	
	private String translate(String key) {
		return SAPLanguageManager.getTranslated("enderstuffplus.guipet."+key);
	}
	
	private String translateFormat(String key, Object... obj) {
		return SAPLanguageManager.getTranslatedFormat("enderstuffplus.guipet."+key, obj);
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        String title = "";
        int color = 0xFFFFFF;
        
        String petName = pet.getName().isEmpty() ? EnumChatFormatting.OBFUSCATED + "RANDOM" + EnumChatFormatting.RESET : pet.getName();
        if( pet.getEntity() instanceof EntityEnderMiss ) {
        	title = translateFormat("title.miss", petName);
        	color = 0xFF9090;
        } else if( pet.getEntity() instanceof EntityEnderAvis ) {
        	title = translateFormat("title.avis", petName);
        	color = 0xFF00FF;
        }
        
        this.fontRenderer.drawString(title, (this.width - this.getRealStringWidth(title)) / 2, yPos + 10, color);
        	
    	this.drawGradientRect((this.width - 205) / 2 - 5, 0, (this.width - 205) / 2 - 4, this.height / 2, 0x00000000+color, 0xFF000000+color);
    	this.drawGradientRect((this.width - 205) / 2 - 5, this.height / 2, (this.width - 205) / 2 - 4, this.height, 0xFF000000+color, 0x00000000+color);
    	this.drawGradientRect((this.width + 205) / 2 + 4, 0, (this.width + 205) / 2 + 5, this.height / 2, 0x00000000+color, 0xFF000000+color);
    	this.drawGradientRect((this.width + 205) / 2 + 4, this.height / 2, (this.width + 205) / 2 + 5, this.height, 0xFF000000+color, 0x00000000+color);
    	GL11.glColor4f(1f, 1f, 1f, 0f);
    	
    	this.fontRenderer.drawString(translate("name"), (this.width - 200) / 2, 20 + this.yPos, 0xFFFFFF);
        
        this.nameTxt.drawTextBox();
                
        super.drawScreen(par1, par2, par3);
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if( par1GuiButton.id == 4 ) {
    		this.writeEntityName();
    		this.mc.thePlayer.closeScreen();
    		return;
		}
		
		PacketRecvEnderPetGUIAction.send(((Entity)this.pet).entityId, (byte)par1GuiButton.id);
		mc.thePlayer.closeScreen();
	}
	
    @Override
	public void updateScreen()
    {
    	super.updateScreen();
    	this.nameTxt.updateCursorCounter();
    	if( this.nameTxt.isFocused() && this.nameTxt.getText().equals(translate("rename")) )
    		this.nameTxt.setText("");
    }
    
    @Override
	protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        this.nameTxt.mouseClicked(par1, par2, par3);
    }
    
    @Override
	protected void keyTyped(char par1, int par2)
    {
    	this.nameTxt.textboxKeyTyped(par1, par2);

    	if( (par2 == 28 || par2 == 1) && this.nameTxt.isFocused() ) {
    		this.nameTxt.setFocused(false);
    		writeEntityName();
    		if( this.nameTxt.getText().isEmpty() ) {
    			this.nameTxt.setText(translate("rename"));
    		}
    	}
    	else if( (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.keyCode) && !this.nameTxt.isFocused() ) {
    		this.writeEntityName();
    		this.mc.thePlayer.closeScreen();
    	}
    }
    
    private void writeEntityName() {
    	if( this.nameTxt.getText().isEmpty() || this.nameTxt.getText().equals(translate("rename")) )
    		return;
    	
    	PacketRecvEnderName.send(((Entity)pet).entityId, this.nameTxt.getText());
    }
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
    
    private int getRealStringWidth(String par1String) {
    	String var1 = par1String.replaceAll("\247.", "");
    	return this.fontRenderer.getStringWidth(var1);
    }
}
