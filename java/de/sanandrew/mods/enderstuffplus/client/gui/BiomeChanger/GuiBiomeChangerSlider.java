package de.sanandrew.mods.enderstuffplus.client.gui.BiomeChanger;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.mods.enderstuffplus.packet.PacketBCGUIAction;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityBiomeChanger;

@SideOnly(Side.CLIENT)
public class GuiBiomeChangerSlider
    extends GuiButton
{
    private boolean isDragging = false;
    private float maxSliderVal = 1.0F;
    private float sliderVal = 1.0F;
    private TileEntityBiomeChanger teBiomeChanger = null;
    private String title = "";

    public GuiBiomeChangerSlider(int id, int x, int y, TileEntityBiomeChanger tileBiomeChanger, String label,
                                 float currValue, float maxValue) {
        super(id, x, y, 150, 20, label + ": " + MathHelper.floor_float(currValue));

        this.title = label;
        this.teBiomeChanger = tileBiomeChanger;
        this.maxSliderVal = Math.max(Math.min(maxValue, 128.00F), 1);
        this.sliderVal = currValue / maxValue;
    }

    @Override
    protected int getHoverState(boolean par1) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY) {
        if( this.visible ) {
            if( this.isDragging && this.enabled ) {
                this.sliderVal = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);

                if( this.sliderVal < 1.0F / this.maxSliderVal ) {
                    this.sliderVal = 1.0F / this.maxSliderVal;
                }

                if( this.sliderVal > 1.0F ) {
                    this.sliderVal = 1.0F;
                }

                this.displayString = this.title + ": " + MathHelper.floor_float(this.sliderVal * this.maxSliderVal);

                IPacket packet = new PacketBCGUIAction(this.teBiomeChanger, 2, MathHelper.floor_float(this.sliderVal * this.maxSliderVal));
                ESPModRegistry.channelHandler.sendToServer(packet);
//                ESPModRegistry.sendPacketSrv("bcGuiAction", this.teBiomeChanger, (byte) 2,
//                                             MathHelper.floor_float(this.sliderValue * this.maxSliderValue));
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderVal * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderVal * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if( super.mousePressed(minecraft, mouseX, mouseY) && this.enabled ) {
            this.sliderVal = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);

            if( this.sliderVal < 1.0F / this.maxSliderVal ) {
                this.sliderVal = 1.0F / this.maxSliderVal;
            }

            if( this.sliderVal > 1.0F ) {
                this.sliderVal = 1.0F;
            }

            this.displayString = this.title + ": " + MathHelper.floor_float(this.sliderVal * this.maxSliderVal);

            IPacket packet = new PacketBCGUIAction(this.teBiomeChanger, 2, MathHelper.floor_float(this.sliderVal * this.maxSliderVal));
            ESPModRegistry.channelHandler.sendToServer(packet);
//            ESPModRegistry.sendPacketSrv("bcGuiAction", this.teBiomeChanger, (byte) 2,
//                                         MathHelper.floor_float(this.sliderVal * this.maxSliderVal));
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
