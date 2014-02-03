package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class GuiBiomeChangerSlider extends GuiButton
{
    public float sliderValue = 1.0F;
    public float maxValue = 1.0F;
    public boolean dragging = false;
    private TileEntityBiomeChanger bcte = null;
    private String title = "";

    public GuiBiomeChangerSlider(int id, int x, int y, TileEntityBiomeChanger tileBC, String label, float currValue, float maxValue) {
        super(id, x, y, 150, 20, label + ": " + MathHelper.floor_float(currValue));
        this.title = label;
        this.bcte = tileBC;
        this.maxValue = Math.max(Math.min(maxValue, 128.00F), 1);
        this.sliderValue = currValue / maxValue;
    }

    @Override
    protected int getHoverState(boolean par1) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if( this.drawButton ) {
            if( this.dragging && this.enabled ) {
                this.sliderValue = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);

                if( this.sliderValue < 1.0F / maxValue ) {
                    this.sliderValue = 1.0F / maxValue;
                }

                if( this.sliderValue > 1.0F ) {
                    this.sliderValue = 1.0F;
                }

                this.displayString = this.title + ": " + MathHelper.floor_float(this.sliderValue * this.maxValue);
                ESPModRegistry.sendPacketSrv("bcGuiAction", this.bcte, (byte) 2, MathHelper.floor_float(this.sliderValue * this.maxValue));
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }
    
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if( super.mousePressed(mc, mouseX, mouseY) && this.enabled ) {
            this.sliderValue = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);

            if( this.sliderValue < 1.0F / maxValue ) {
                this.sliderValue = 1.0F / maxValue;
            }

            if( this.sliderValue > 1.0F ) {
                this.sliderValue = 1.0F;
            }

            this.displayString = this.title + ": " + MathHelper.floor_float(this.sliderValue * this.maxValue);
            ESPModRegistry.sendPacketSrv("bcGuiAction", this.bcte, (byte) 2, MathHelper.floor_float(this.sliderValue * this.maxValue));
            this.dragging = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }
}
