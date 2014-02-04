package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

@SideOnly(Side.CLIENT)
public class GuiBiomeChangerConfig extends GuiBiomeChangerBase implements Textures
{
	private GuiButton btn_circular, btn_square, btn_rhombic, sld_range, btn_chngBlocks;
	
	public GuiBiomeChangerConfig(TileEntityBiomeChanger tile) {
		super(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer player) {
				return true;
			}
		});
		this.bcte = tile;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		
		this.tabCofig.enabled = false;
        
		this.buttonList.add(this.sld_range = new GuiBiomeChangerSlider(4, this.guiLeft + 13, this.guiTop + 30, this.bcte, this.translate("enderstuffplus.biomeChanger.gui1.range"), this.bcte.getMaxRange(), 128F));
		
		this.buttonList.add(this.btn_circular = new GuiButton(this.buttonList.size(), this.guiLeft + 13, this.guiTop + 70, 50, 20, this.translate("enderstuffplus.biomeChanger.gui3.form1")));
		this.buttonList.add(this.btn_square = new GuiButton(this.buttonList.size(), this.guiLeft + 13 + 50, this.guiTop + 70, 50, 20, this.translate("enderstuffplus.biomeChanger.gui3.form2")));
		this.buttonList.add(this.btn_rhombic = new GuiButton(this.buttonList.size(), this.guiLeft + 13 + 100, this.guiTop + 70, 50, 20, this.translate("enderstuffplus.biomeChanger.gui3.form3")));
		this.buttonList.add(this.btn_chngBlocks = new GuiButton(this.buttonList.size(), this.guiLeft + 13, this.guiTop + 100, 150, 20, this.translate("enderstuffplus.biomeChanger.gui3.blocks")));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partTicks, int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_III);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        
		int middle = (int) (((float)this.bcte.getCurrRange() / (float)this.bcte.getMaxRange()) * 161F);

		GL11.glPushMatrix();
		GL11.glTranslatef(this.guiLeft, this.guiTop, 0F);
		this.drawTexturedModalRect(7, 140, 0, 230, 161, 5);
		this.drawTexturedModalRect(7, 140, 0, 235, middle, 5);
		this.drawTexturedModalRect(7, 156, 161, 230, 12, 5);
		this.drawTexturedModalRect(7, 166, 161, 235, 12, 5);
		GL11.glPopMatrix();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		RenderHelper.disableStandardItemLighting();

		this.fontRenderer.drawString(this.translate("tile.enderstuffp:biomeChanger.name"), 8, 8, 0x404040);
		this.fontRenderer.drawString(this.translate("enderstuffplus.biomeChanger.gui3.periform"), 8, 61, 0x404040);
		this.fontRenderer.drawString(this.translate("enderstuffplus.biomeChanger.gui3.stats"), 8, 130, 0x404040);
		
		this.fontRenderer.drawString(String.format(this.translate("enderstuffplus.biomeChanger.gui3.remain"), this.bcte.getMaxRange() - this.bcte.getCurrRange()), 20, 155, 0x404040);
		this.fontRenderer.drawString(String.format(this.translate("enderstuffplus.biomeChanger.gui3.processed"), this.bcte.getCurrRange()), 20, 165, 0x404040);
		RenderHelper.enableGUIStandardItemLighting();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partTicks) {
		this.btn_circular.enabled = this.bcte.getRadForm() != 0 && !this.bcte.isActive();
		this.btn_square.enabled = this.bcte.getRadForm() != 1 && !this.bcte.isActive();
		this.btn_rhombic.enabled = this.bcte.getRadForm() != 2 && !this.bcte.isActive();
		
		this.sld_range.enabled = this.btn_chngBlocks.enabled = !this.bcte.isActive();
		
		this.btn_chngBlocks.displayString = 
				translate("enderstuffplus.biomeChanger.gui3.blocks")
				+": "
				+translate(this.bcte.isReplacingBlocks ? "options.on" : "options.off");
		super.drawScreen(mouseX, mouseY, partTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		int id = button.id;
		if( id == this.btn_circular.id ) {
			ESPModRegistry.sendPacketSrv("bcGuiAction", this.bcte, (byte) 3, 0);
		} else if( id == this.btn_rhombic.id ) {
			ESPModRegistry.sendPacketSrv("bcGuiAction", this.bcte, (byte) 3, 1);
		} else if( id == this.btn_square.id ) {
			ESPModRegistry.sendPacketSrv("bcGuiAction", this.bcte, (byte) 3, 2);
		} else if( id == this.btn_chngBlocks.id ) {
			ESPModRegistry.sendPacketSrv("bcGuiAction", this.bcte, (byte) 4, 0);
		} else {
			super.actionPerformed(button);
		}
	}
	
	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int which) {
		super.mouseMovedOrUp(mouseX, mouseY, which);
		
		if( CommonUsedStuff.getSelectedBtn(this) != null && which == 0 ) {
			CommonUsedStuff.getSelectedBtn(this).mouseReleased(mouseX, mouseY);
            CommonUsedStuff.setSelectedBtn(this, null);
        }
	}

	@Override
    protected void keyTyped(char key, int keyCode)
    {
        if( keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.keyCode ) {
            this.mc.thePlayer.closeScreen();
        }
        super.keyTyped(key, keyCode);
    }
}
