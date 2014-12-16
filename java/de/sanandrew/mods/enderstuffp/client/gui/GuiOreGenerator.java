/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.gui;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.inventory.ContainerOreGenerator;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityOreGenerator;
import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class GuiOreGenerator
        extends GuiContainer
{
    private TileEntityOreGenerator oreGenerator;

    public GuiOreGenerator(InventoryPlayer invPlayer, TileEntityOreGenerator generator) {
        super(new ContainerOreGenerator(invPlayer, generator));

        this.oreGenerator = generator;

        this.xSize = 176;
        this.ySize = 240;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partTicks, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glTranslatef(this.guiLeft, this.guiTop, 0.0F);
        this.mc.renderEngine.bindTexture(EnumTextures.GUI_ORE_GENERATOR.getResource());

        this.drawTexturedModalRect(0, 0, 0, 0, this.xSize, this.ySize);
        GL11.glPopMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.mc.renderEngine.bindTexture(EnumTextures.GUI_ORE_GENERATOR.getResource());
        int currFlux = this.oreGenerator.getEnergyStored(ForgeDirection.UNKNOWN);
        int maxFlux = this.oreGenerator.getMaxEnergyStored(ForgeDirection.UNKNOWN);

        int fluxScale = 40 - (int) (currFlux / (float) maxFlux * 40.0F);

        this.drawTexturedModalRect(10, 20, 176, 0, 14, 42);
        this.drawTexturedModalRect(14, 21 + fluxScale, 194, 1 + fluxScale, 9, 40 - fluxScale);

        String unlocName = EspBlocks.biomeChanger.getUnlocalizedName() + ".gui.";

        this.mc.fontRenderer.drawString(SAPUtils.translate(unlocName + "flux.usage"), 28, 21, 0x707070);
//        this.mc.fontRenderer.drawString(String.format("%d RF/t", fluxUsage), 33, 31, 0x000000);
        this.mc.fontRenderer.drawString(SAPUtils.translate(unlocName + "flux.stored"), 28, 43, 0x707070);
        this.mc.fontRenderer.drawString(String.format("%d / %d RF", currFlux, maxFlux), 33, 53, 0x000000);
    }
}
