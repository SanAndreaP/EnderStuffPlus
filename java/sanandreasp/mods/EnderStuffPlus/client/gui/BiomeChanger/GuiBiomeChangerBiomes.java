package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

import com.google.common.collect.Maps;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.biome.BiomeGenBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBiomeChangerBiomes
    extends GuiBiomeChangerBase
    implements Textures
{
    private HashMap<Integer, BiomeGenBase> biomeList = Maps.newHashMap();
    private int entryPosition = 0;
    private boolean isScrolling = false;
    private float scrollPosition = 0F;

    public GuiBiomeChangerBiomes(TileEntityBiomeChanger tileBiomeChanger) {
        super(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer player) {
                return true;
            }
        });

        this.teBiomeChanger = tileBiomeChanger;

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
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_II);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        if( this.needsScrollBars() ) {
            int scrollX = 163;
            int scrollY = 19 + (int) (178F * this.scrollPosition);

            this.drawTexturedModalRect(scrollX + this.guiLeft, scrollY + this.guiTop, 176, 0, 6, 6);
        } else {
            this.drawTexturedModalRect(this.guiLeft + 163, this.guiTop + 19, 176, 6, 6, 6);
        }

        GL11.glPushMatrix();

          GL11.glTranslated(this.guiLeft, this.guiTop, 0F);
          this.fontRenderer.drawString(CommonUsedStuff.getTranslated("tile.enderstuffp:biomeChanger.name"), 8, 8, 0x404040);
          this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_II);

          for( int i = this.entryPosition; i < 14 + this.entryPosition; i++ ) {
              int x = 8;
              int y = 21 + 13 * (i - this.entryPosition);

              if( i + this.entryPosition < this.biomeList.size() ) {
                  String s = this.biomeList.get(i).biomeName;
                  this.fontRenderer.drawString(s, x + 15, y + 2, 0xFFFFFF);

                  if( this.biomeList.get(i).biomeID == this.teBiomeChanger.getBiomeID() ) {
                      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                      this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_II);
                      this.drawTexturedModalRect(x, y, 176, 25, 11, 11);
                  } else if( mouseX < this.guiLeft + x + 11 && mouseX >= this.guiLeft + x
                             && mouseY < this.guiTop + y + 11 && mouseY >= this.guiTop + y
                             && !this.teBiomeChanger.isActive() ) {
                      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                      this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_II);
                      this.drawTexturedModalRect(x, y, 176, 14, 11, 11);
                  }
              } else {
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  this.mc.getTextureManager().bindTexture(GUI_BIOMECHANGER_II);
                  this.drawTexturedModalRect(x, y, 148, 192, 11, 11);
              }
          }

          if( this.teBiomeChanger.isActive() ) {
              Gui.drawRect(6, 19, 159, 203, 0xAA808080);
          }

        GL11.glPopMatrix();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int scrollMinX = this.guiLeft + 163;
        int scrollMaxX = scrollMinX + 6;
        int scrollMinY = this.guiTop + 19;
        int scrollMaxY = scrollMinY + 184;
        boolean shouldScroll = Mouse.isButtonDown(0) && !this.teBiomeChanger.isActive();

        if( !this.isScrolling && shouldScroll && this.needsScrollBars() && (mouseX > scrollMinX) && (mouseX < scrollMaxX)
                && (mouseY > scrollMinY) && (mouseY < scrollMaxY) ) {
            this.isScrolling = true;
        } else if( !shouldScroll ) {
            this.isScrolling = false;
        }

        for( int i = 0; i < 14 && shouldScroll; i++ ) {
            int x = this.guiLeft + 8;
            int y = this.guiTop + 21 + 13 * i;

            if( (mouseX < x + 11) && (mouseX >= x) && (mouseY < y + 11) && (mouseY >= y) ) {
                this.teBiomeChanger.setBiomeID(this.biomeList.get(this.entryPosition + i).biomeID);
                ESPModRegistry.sendPacketSrv("bcGuiAction", this.teBiomeChanger, (byte) 1,
                                             this.biomeList.get(this.entryPosition + i).biomeID);
                break;
            }
        }

        if( this.isScrolling ) {
            int scrollY = (int) (178F / (this.biomeList.size() - 14));

            for( int y = 0; y < this.biomeList.size() - 13; y++ ) {
                if( (mouseY > scrollY * y + this.guiTop) || (mouseX < scrollY * y + this.guiTop) ) {
                    this.entryPosition = y;
                }
            }

            this.scrollPosition = ((mouseY - scrollMinY - 3) / 178F);
        }

        if( this.scrollPosition < 0.0F ) {
            this.scrollPosition = 0.0F;
        }

        if( this.scrollPosition > 1.0F ) {
            this.scrollPosition = 1.0F;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void handleMouseInput() {
        int mouseDWheel = Mouse.getEventDWheel();

        super.handleMouseInput();

        if( (mouseDWheel != 0) && this.needsScrollBars() ) {
            if( mouseDWheel < 0 ) {
                this.entryPosition = Math.min(this.entryPosition + 1, this.biomeList.size() - 14);
                this.scrollPosition = (float) this.entryPosition / ((float) (this.biomeList.size() - 14));
            }

            if( mouseDWheel > 0 ) {
                this.entryPosition = Math.max(this.entryPosition - 1, 0);
                this.scrollPosition = (float) this.entryPosition / ((float) (this.biomeList.size() - 14));
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();

        this.tabBiomes.enabled = false;
    }

    private boolean needsScrollBars() {
        return this.biomeList.size() > 14;
    }
}
