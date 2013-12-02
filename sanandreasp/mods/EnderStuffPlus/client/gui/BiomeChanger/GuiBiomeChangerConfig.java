package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import java.util.HashMap;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;

import org.lwjgl.opengl.GL11;

import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.mods.EnderStuffPlus.client.registry.ClientProxy;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.inventory.Container_BiomeChanger;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvBCGUIAction;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvChangeBCGUI;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class GuiBiomeChangerConfig extends GuiBiomeChangerBase implements Textures {
	private GuiButton btn_circular, btn_square, btn_rhombic, sld_range, btn_chngBlocks;
	
	private int entryPos = 0;
	private boolean isScrolling = false;
	private float currScrollPos = 0F;
	
	public GuiBiomeChangerConfig(TileEntityBiomeChanger par1TileEntity) {
		super(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer entityplayer) {
				return true;
			}
		});
		this.bcte = par1TileEntity;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		this.tabCofig.enabled = false;
        
		this.buttonList.add(this.sld_range = new GuiBiomeChangerSlider(4, this.guiLeft + 13, this.guiTop + 30, this.bcte, translate("enderstuffplus.biomeChanger.gui1.range"), this.bcte.getMaxRange(), 128F));
		
		buttonList.add(this.btn_circular = new GuiButton(buttonList.size(), this.guiLeft + 13, this.guiTop + 70, 50, 20, translate("enderstuffplus.biomeChanger.gui3.form1")));
		buttonList.add(this.btn_square = new GuiButton(buttonList.size(), this.guiLeft + 13 + 50, this.guiTop + 70, 50, 20, translate("enderstuffplus.biomeChanger.gui3.form2")));
		buttonList.add(this.btn_rhombic = new GuiButton(buttonList.size(), this.guiLeft + 13 + 100, this.guiTop + 70, 50, 20, translate("enderstuffplus.biomeChanger.gui3.form3")));
		buttonList.add(this.btn_chngBlocks = new GuiButton(buttonList.size(), this.guiLeft + 13, this.guiTop + 100, 150, 20, translate("enderstuffplus.biomeChanger.gui3.blocks")));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_III);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int l = guiLeft;
        int i1 = guiTop;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        
		int middle = (int) (((float)this.bcte.getCurrRange() / (float)this.bcte.getMaxRange()) * 161F);

		GL11.glPushMatrix();
		GL11.glTranslatef(l, i1, 0F);
		this.drawTexturedModalRect(7, 140, 0, 230, 161, 5);
		this.drawTexturedModalRect(7, 140, 0, 235, middle, 5);
		this.drawTexturedModalRect(7, 156, 161, 230, 12, 5);
		this.drawTexturedModalRect(7, 166, 161, 235, 12, 5);
		GL11.glPopMatrix();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		RenderHelper.disableStandardItemLighting();

		this.fontRenderer.drawString(translate("tile.enderstuffp:biomeChanger.name"), 8, 8, 0x404040);
		this.fontRenderer.drawString(translate("enderstuffplus.biomeChanger.gui3.periform"), 8, 61, 0x404040);
		this.fontRenderer.drawString(translate("enderstuffplus.biomeChanger.gui3.stats"), 8, 130, 0x404040);
		
		this.fontRenderer.drawString(String.format(translate("enderstuffplus.biomeChanger.gui3.remain"), this.bcte.getMaxRange() - this.bcte.getCurrRange()), 20, 155, 0x404040);
		this.fontRenderer.drawString(String.format(translate("enderstuffplus.biomeChanger.gui3.processed"), this.bcte.getCurrRange()), 20, 165, 0x404040);
		RenderHelper.enableGUIStandardItemLighting();
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.btn_circular.enabled = this.bcte.getRadForm() != 0 && !this.bcte.isActive();
		this.btn_square.enabled = this.bcte.getRadForm() != 1 && !this.bcte.isActive();
		this.btn_rhombic.enabled = this.bcte.getRadForm() != 2 && !this.bcte.isActive();
		
		this.sld_range.enabled = this.btn_chngBlocks.enabled = !this.bcte.isActive();
		
		this.btn_chngBlocks.displayString = 
				translate("enderstuffplus.biomeChanger.gui3.blocks")
				+": "
				+translate(this.bcte.isReplacingBlocks ? "options.on" : "options.off");
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		int id = par1GuiButton.id;
		if( id == this.btn_circular.id ) {
			PacketRecvBCGUIAction.send(this.bcte, (byte) 3, 0);
		} else if( id == this.btn_rhombic.id ) {
			PacketRecvBCGUIAction.send(this.bcte, (byte) 3, 1);
		} else if( id == this.btn_square.id ) {
			PacketRecvBCGUIAction.send(this.bcte, (byte) 3, 2);
		} else if( id == this.btn_chngBlocks.id ) {
			PacketRecvBCGUIAction.send(this.bcte, (byte) 4, 0);
		} else {
			super.actionPerformed(par1GuiButton);
		}
	}
	
	@Override
	protected void mouseMovedOrUp(int par1, int par2, int par3) {
		super.mouseMovedOrUp(par1, par2, par3);
		
		if( CommonUsedStuff.getSelectedBtn(this) != null && par3 == 0 )
        {
			CommonUsedStuff.getSelectedBtn(this).mouseReleased(par1, par2);
            CommonUsedStuff.setSelectedBtn(this, null);
        }
	}

	@Override
    protected void keyTyped(char par1, int par2)
    {
        if( par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.keyCode )
        {
            this.mc.thePlayer.closeScreen();
        }
        super.keyTyped(par1, par2);
    }
}
