package sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.client.registry.ClientProxy;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvBCGUIAction;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;

public class GuiBiomeChangerSlider extends GuiButton {

	/** The value of this slider control. */
    public float sliderValue = 1.0F;
    public float maxValue = 1.0F;

    /** Is this slider control being dragged. */
    public boolean dragging = false;

    private TileEntityBiomeChanger bcte = null;
    
    private String title = "";

    public GuiBiomeChangerSlider(int par1, int par2, int par3, TileEntityBiomeChanger par4BiomeChanger, String par5Str, float par6CurrValue, float par7MaxValue)
    {
        super(par1, par2, par3, 150, 20, par5Str + ": " + MathHelper.floor_float(par6CurrValue));
        this.title = par5Str;
        this.bcte = par4BiomeChanger;
        this.maxValue = Math.max(Math.min(par7MaxValue, 128.00F), 1);
        this.sliderValue = par6CurrValue / par7MaxValue;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean par1)
    {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3)
    {
        if( this.drawButton )
        {
            if( this.dragging && this.enabled )
            {
                this.sliderValue = (float)(par2 - (this.xPosition + 4)) / (float)(this.width - 8);

                if( this.sliderValue < 1.0F / maxValue )
                {
                    this.sliderValue = 1.0F / maxValue;
                }

                if( this.sliderValue > 1.0F )
                {
                    this.sliderValue = 1.0F;
                }

                this.displayString = this.title + ": " + MathHelper.floor_float(this.sliderValue * this.maxValue);
//				Proxy_Client.sendBCTEData(0, bcte.xCoord, bcte.yCoord, bcte.zCoord, new byte[] {(byte)2, (byte)(MathHelper.floor_float(this.sliderValue * this.maxValue))});
				PacketRecvBCGUIAction.send(this.bcte, (byte) 2, MathHelper.floor_float(this.sliderValue * this.maxValue));
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }
    
    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        if( super.mousePressed(par1Minecraft, par2, par3) && this.enabled )
        {
            this.sliderValue = (float)(par2 - (this.xPosition + 4)) / (float)(this.width - 8);

            if( this.sliderValue < 1.0F / maxValue )
            {
                this.sliderValue = 1.0F / maxValue;
            }

            if( this.sliderValue > 1.0F )
            {
                this.sliderValue = 1.0F;
            }

            this.displayString = this.title + ": " + MathHelper.floor_float(this.sliderValue * this.maxValue);
//			Proxy_Client.sendBCTEData(0, bcte.xCoord, bcte.yCoord, bcte.zCoord, new byte[] {(byte)2, (byte)(MathHelper.floor_float(this.sliderValue * this.maxValue))});
			PacketRecvBCGUIAction.send(this.bcte, (byte) 2, MathHelper.floor_float(this.sliderValue * this.maxValue));
            this.dragging = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int par1, int par2)
    {
        this.dragging = false;
    }
}
