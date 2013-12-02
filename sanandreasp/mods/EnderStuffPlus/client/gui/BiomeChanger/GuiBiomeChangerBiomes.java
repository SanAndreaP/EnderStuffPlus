package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.biome.BiomeGenBase;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Maps;

import sanandreasp.mods.EnderStuffPlus.client.registry.ClientProxy;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.inventory.Container_BiomeChanger;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvBCGUIAction;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvChangeBCGUI;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

public class GuiBiomeChangerBiomes extends GuiBiomeChangerBase implements Textures {
	private HashMap<Integer, BiomeGenBase> biomeList = Maps.newHashMap();
	
	private int entryPos = 0;
	private boolean isScrolling = false;
	private float currScrollPos = 0F;
	
	public GuiBiomeChangerBiomes(TileEntityBiomeChanger par1TileEntity) {
		super(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer entityplayer) {
				return true;
			}
		});
		this.bcte = par1TileEntity;
		
		List<Integer> disabledBiomes = RegistryBiomeChanger.getDisabledBiomes();		
		for( int i = 0, j = 0; i < BiomeGenBase.biomeList.length; i++ ) {
			BiomeGenBase biome = BiomeGenBase.biomeList[i];
			if( biome != null && !disabledBiomes.contains(i) ) {
				biomeList.put(j, biome);
				j++;
			}
		}
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.tabBiomes.enabled = false;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int par1, int par2) {
		this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_II);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    int l = guiLeft;
	    int i1 = guiTop;
	    drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
	    
	    if( needsScrollBars() ) {
	        int scrollX = 163;
	        int scrollY = 19 + (int)(178F * currScrollPos);
	        drawTexturedModalRect(scrollX + l, scrollY + i1, 176, 0, 6, 6);
	    } else {
	        drawTexturedModalRect(l + 163, i1 + 19, 176, 6, 6, 6);
	    }
	    
	    GL11.glPushMatrix();
	    GL11.glTranslated(this.guiLeft, this.guiTop, 0F);
		this.fontRenderer.drawString(ESPModRegistry.manHelper.getLangMan().getTranslated("tile.enderstuffp:biomeChanger.name"), 8, 8, 0x404040);
		this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_II);
		for( int i = entryPos; i < 14 + entryPos; i++ ) {
	        int x = 8, y = 21 + 13*(i-this.entryPos);
			if( i + entryPos < this.biomeList.size() ) {
				String s = biomeList.get(i).biomeName;
				this.fontRenderer.drawString(s, x + 15, y + 2, 0xFFFFFF);
				
				if( biomeList.get(i).biomeID == bcte.getBiomeID() ) {
			        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_II);
			        drawTexturedModalRect(x, y, 176, 25, 11, 11);
				} else if( par1 < this.guiLeft + x + 11 && par1 >= this.guiLeft + x && par2 < this.guiTop + y + 11 && par2 >= this.guiTop + y && !this.bcte.isActive() ) {
			        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_II);
			        drawTexturedModalRect(x, y, 176, 14, 11, 11);
				}
			} else {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_II);
		        drawTexturedModalRect(x, y, 148, 192, 11, 11);
			}
		}
		
		if( this.bcte.isActive() ) {
			this.drawRect(6, 19, 159, 203, 0xAA808080);
		}
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
        boolean var4 = Mouse.isButtonDown(0) && !this.bcte.isActive();
        
        int scrollMinX = guiLeft + 163;
        int scrollMaxX = scrollMinX + 6;
        int scrollMinY = guiTop + 19;
        int scrollMaxY = scrollMinY + 184;
        
        if( !isScrolling && var4 && this.needsScrollBars() && par1 > scrollMinX && par1 < scrollMaxX && par2 > scrollMinY && par2 < scrollMaxY ) {
        	isScrolling = true;
        } else if( !var4 ) {
        	isScrolling = false;
        }
        
        for( int i = 0; i < 14 && var4; i++ ) {
        	int x = this.guiLeft + 8, y = this.guiTop + 21 + 13*i;
        	if( par1 < x + 11 && par1 >= x && par2 < y + 11 && par2 >= y ) {
        		this.bcte.setBiomeID(this.biomeList.get(entryPos + i).biomeID);
        		PacketRecvBCGUIAction.send(this.bcte, (byte)1, this.biomeList.get(entryPos + i).biomeID);
				break;
        	}
        }
        
        if( isScrolling ) {
        	int sY = (int) (178F / (float)(biomeList.size() - 14));
	        for( int y = 0; y < biomeList.size() - 13; y++ ) {
	        	if( par2 > sY * y + guiTop || par1 < sY * y + guiTop ) {
	        		entryPos = y;
	        	}
	        }
	        currScrollPos = ((float)(par2 - scrollMinY - 3) / 178F);
        }
        
        if( currScrollPos < 0.0F )
        	currScrollPos = 0.0F;
        if( currScrollPos > 1.0F )
        	currScrollPos = 1.0F;
        
		super.drawScreen(par1, par2, par3);
	}
	
	private boolean needsScrollBars() {
		return this.biomeList.size() > 14;
	}
	
	public void handleMouseInput()
    {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();

        if( var1 != 0 && this.needsScrollBars() )
        {
            if( var1 < 0 )
            {
                this.entryPos = Math.min(this.entryPos + 1, this.biomeList.size() - 14);
    	        currScrollPos = (float)entryPos / ((float)(biomeList.size() - 14));
            }

            if( var1 > 0 )
            {
            	this.entryPos = Math.max(this.entryPos - 1, 0);
    	        currScrollPos = (float)entryPos / ((float)(biomeList.size() - 14));
            }
        }
    }
}
