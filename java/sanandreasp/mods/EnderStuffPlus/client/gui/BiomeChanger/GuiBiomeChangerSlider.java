package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBiomeChangerSlider
    extends GuiButton
{
    private boolean isDragging = false;
    private float maxSliderValue = 1.0F;
    private float sliderValue = 1.0F;
    private TileEntityBiomeChanger teBiomeChanger = null;
    private String title = "";

    public GuiBiomeChangerSlider(int id, int x, int y, TileEntityBiomeChanger tileBiomeChanger, String label,
                                 float currValue, float maxValue) {
        super(id, x, y, 150, 20, label + ": " + MathHelper.floor_float(currValue));

        this.title = label;
        this.teBiomeChanger = tileBiomeChanger;
        this.maxSliderValue = Math.max(Math.min(maxValue, 128.00F), 1);
        this.sliderValue = currValue / maxValue;
    }

    @Override
    protected int getHoverState(boolean par1) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY) {
        if( this.drawButton ) {
            if( this.isDragging && this.enabled ) {
                this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);

                if( this.sliderValue < 1.0F / this.maxSliderValue ) {
                    this.sliderValue = 1.0F / this.maxSliderValue;
                }

                if( this.sliderValue > 1.0F ) {
                    this.sliderValue = 1.0F;
                }

                this.displayString = this.title + ": " + MathHelper.floor_float(this.sliderValue * this.maxSliderValue);

                ESPModRegistry.sendPacketSrv("bcGuiAction", this.teBiomeChanger, (byte) 2,
                                             MathHelper.floor_float(this.sliderValue * this.maxSliderValue));
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if( super.mousePressed(minecraft, mouseX, mouseY) && this.enabled ) {
            this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);

            if( this.sliderValue < 1.0F / this.maxSliderValue ) {
                this.sliderValue = 1.0F / this.maxSliderValue;
            }

            if( this.sliderValue > 1.0F ) {
                this.sliderValue = 1.0F;
            }

            this.displayString = this.title + ": " + MathHelper.floor_float(this.sliderValue * this.maxSliderValue);
            ESPModRegistry.sendPacketSrv("bcGuiAction", this.teBiomeChanger, (byte) 2,
                                         MathHelper.floor_float(this.sliderValue * this.maxSliderValue));
            this.isDragging = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.isDragging = false;
    }
}
