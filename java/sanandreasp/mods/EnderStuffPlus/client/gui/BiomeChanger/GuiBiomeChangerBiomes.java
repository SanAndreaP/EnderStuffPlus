package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import java.util.HashMap;
import java.util.List;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.biome.BiomeGenBase;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.core.manpack.managers.SAPLanguageManager;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

@SideOnly(Side.CLIENT)
public class GuiBiomeChangerBiomes extends GuiBiomeChangerBase implements Textures 
{
	private HashMap<Integer, BiomeGenBase> biomeList = Maps.newHashMap();
	
	private int entryPos = 0;
	private boolean isScrolling = false;
	private float currScrollPos = 0F;
	
	public GuiBiomeChangerBiomes(TileEntityBiomeChanger tile) {
		super(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer player) {
				return true;
			}
		});
		this.bcte = tile;
		
		List<Integer> disabledBiomes = RegistryBiomeChanger.getDisabledBiomes();		
		for( int i = 0, j = 0; i < BiomeGenBase.biomeList.length; i++ ) {
			BiomeGenBase biome = BiomeGenBase.biomeList[i];
			if( biome != null && !disabledBiomes.contains(i) ) {
				this.biomeList.put(j, biome);
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
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	    
	    if( needsScrollBars() ) {
	        int scrollX = 163;
	        int scrollY = 19 + (int)(178F * this.currScrollPos);
	        drawTexturedModalRect(scrollX + this.guiLeft, scrollY + this.guiTop, 176, 0, 6, 6);
	    } else {
	        drawTexturedModalRect(this.guiLeft + 163, this.guiTop + 19, 176, 6, 6, 6);
	    }
	    
	    GL11.glPushMatrix();
	    GL11.glTranslated(this.guiLeft, this.guiTop, 0F);
		this.fontRenderer.drawString(SAPLanguageManager.getTranslated("tile.enderstuffp:biomeChanger.name"), 8, 8, 0x404040);
		this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_II);
		for( int i = this.entryPos; i < 14 + this.entryPos; i++ ) {
	        int x = 8, y = 21 + 13*(i-this.entryPos);
			if( i + this.entryPos < this.biomeList.size() ) {
				String s = this.biomeList.get(i).biomeName;
				this.fontRenderer.drawString(s, x + 15, y + 2, 0xFFFFFF);
				
				if( this.biomeList.get(i).biomeID == this.bcte.getBiomeID() ) {
			        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_II);
			        drawTexturedModalRect(x, y, 176, 25, 11, 11);
				} else if( par1 < this.guiLeft + x + 11 
						   && par1 >= this.guiLeft + x 
						   && par2 < this.guiTop + y + 11 
						   && par2 >= this.guiTop + y 
						   && !this.bcte.isActive() )
				{
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
			Gui.drawRect(6, 19, 159, 203, 0xAA808080);
		}
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partTicks) {
        boolean var4 = Mouse.isButtonDown(0) && !this.bcte.isActive();
        
        int scrollMinX = this.guiLeft + 163;
        int scrollMaxX = scrollMinX + 6;
        int scrollMinY = this.guiTop + 19;
        int scrollMaxY = scrollMinY + 184;
        
        if( !this.isScrolling && var4 
        	&& this.needsScrollBars() 
        	&& mouseX > scrollMinX 
        	&& mouseX < scrollMaxX 
        	&& mouseY > scrollMinY 
        	&& mouseY < scrollMaxY )
        {
        	this.isScrolling = true;
        } else if( !var4 ) {
        	this.isScrolling = false;
        }
        
        for( int i = 0; i < 14 && var4; i++ ) {
        	int x = this.guiLeft + 8, y = this.guiTop + 21 + 13*i;
        	if( mouseX < x + 11 && mouseX >= x && mouseY < y + 11 && mouseY >= y ) {
        		this.bcte.setBiomeID(this.biomeList.get(this.entryPos + i).biomeID);
    			ESPModRegistry.sendPacketSrv("bcGuiAction", this.bcte, (byte)1, this.biomeList.get(this.entryPos + i).biomeID);
				break;
        	}
        }
        
        if( this.isScrolling ) {
        	int sY = (int) (178F / (float)(this.biomeList.size() - 14));
	        for( int y = 0; y < this.biomeList.size() - 13; y++ ) {
	        	if( mouseY > sY * y + this.guiTop || mouseX < sY * y + this.guiTop ) {
	        		this.entryPos = y;
	        	}
	        }
	        this.currScrollPos = ((float)(mouseY - scrollMinY - 3) / 178F);
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
		return this.biomeList.size() > 14;
	}
	
	@Override
	public void handleMouseInput() {
        super.handleMouseInput();
        int mouseDWheel = Mouse.getEventDWheel();

        if( mouseDWheel != 0 && this.needsScrollBars() ) {
            if( mouseDWheel < 0 ) {
                this.entryPos = Math.min(this.entryPos + 1, this.biomeList.size() - 14);
                this.currScrollPos = (float)this.entryPos / ((float)(this.biomeList.size() - 14));
            }

            if( mouseDWheel > 0 ) {
            	this.entryPos = Math.max(this.entryPos - 1, 0);
            	this.currScrollPos = (float)this.entryPos / ((float)(this.biomeList.size() - 14));
            }
        }
    }
}
