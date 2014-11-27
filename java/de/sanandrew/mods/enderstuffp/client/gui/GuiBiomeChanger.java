/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.gui;

import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeChanger;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.util.ForgeDirection;

public class GuiBiomeChanger
        extends GuiScreen
{
    private TileEntityBiomeChanger biomeChanger;

    public GuiBiomeChanger(TileEntityBiomeChanger bChanger) {
        this.biomeChanger = bChanger;
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        this.mc.fontRenderer.drawString(Integer.toString(biomeChanger.getEnergyStored(ForgeDirection.UNKNOWN)), 0, 0, 0xFFFFFFFF);

        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
