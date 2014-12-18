/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.gui;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.inventory.ContainerOreGenerator;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityOreGenerator;
import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import de.sanandrew.mods.enderstuffp.util.manager.OreGeneratorManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GuiOreGenerator
        extends GuiContainer
{
    private TileEntityOreGenerator oreGenerator;

    public GuiOreGenerator(InventoryPlayer invPlayer, TileEntityOreGenerator generator) {
        super(new ContainerOreGenerator(invPlayer, generator));

        this.oreGenerator = generator;

        this.xSize = 176;
        this.ySize = 188;
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
        int currFlux = this.oreGenerator.getEnergyStored(ForgeDirection.UNKNOWN);
        int maxFlux = this.oreGenerator.getMaxEnergyStored(ForgeDirection.UNKNOWN);
        int fluxScale = 40 - (int) (currFlux / (float) maxFlux * 40.0F);

        int ticksRemain = this.oreGenerator.ticksGenRemain;
        int maxTicksRemain = this.oreGenerator.maxTicksGenRemain;
        int ticksScale = Math.max(14 - (int) (ticksRemain / (float) maxTicksRemain * 14.0F), 0);


        this.mc.fontRenderer.drawString(SAPUtils.translate(this.oreGenerator.getInventoryName()), 8, 6, 0x404040);
        this.mc.fontRenderer.drawString(SAPUtils.translate("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);

        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.bindTexture(EnumTextures.GUI_ORE_GENERATOR.getResource());

        this.drawTexturedModalRect(10, 20, 176, 0, 14, 42);
        this.drawTexturedModalRect(11, 21 + fluxScale, 191, 1 + fluxScale, 12, 40 - fluxScale);

        this.drawTexturedModalRect(98, 70, 176, 42, 16, 16);
        this.drawTexturedModalRect(98, 70 + ticksScale, 192, 42 + ticksScale, 16, 16 - ticksScale);

        String unlocName = EspBlocks.oreGenerator.getUnlocalizedName() + ".gui.";

        this.mc.fontRenderer.drawString(SAPUtils.translate(unlocName + "flux.generating"), 28, 21, 0x707070);
        this.mc.fontRenderer.drawString(String.format("%d RF/t (%s)", this.oreGenerator.fluxGenerated, getTimeFromTicks(ticksRemain)), 33, 31, 0x000000);
        this.mc.fontRenderer.drawString(SAPUtils.translate(unlocName + "flux.stored"), 28, 43, 0x707070);
        this.mc.fontRenderer.drawString(String.format("%d / %d RF", currFlux, maxFlux), 33, 53, 0x000000);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void renderToolTip(ItemStack stack, int x, int y) {
        List list = stack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
        Pair<Integer, Integer> fuelValues = OreGeneratorManager.getFuelValues(stack);
        if( fuelValues != null ) {
            list.add(SAPUtils.translatePostFormat(EspBlocks.oreGenerator.getUnlocalizedName() + ".gui.fuelTooltip", fuelValues.getValue1() * fuelValues.getValue0(),
                                                  fuelValues.getValue0()));
        }

        for (int k = 0; k < list.size(); ++k) {
            if (k == 0) {
                list.set(k, stack.getRarity().rarityColor + (String)list.get(k));
            } else {
                list.set(k, EnumChatFormatting.GRAY + (String)list.get(k));
            }
        }

        FontRenderer font = stack.getItem().getFontRenderer(stack);
        drawHoveringText(list, x, y, (font == null ? fontRendererObj : font));
    }

    private static String getTimeFromTicks(int ticks) {
        double secs = ticks / 20.0D;
        String s = String.format("%.1f", secs % 60.0D) + 's';
        int t = (int)secs / 60;
        if( t == 0 ) {
            return s;
        }

        s = t % 60 + "m " + s;
        t /= 60;
        if( t == 0 ) {
            return s;
        }

        s = t % 24 + "h " + s;
        t /= 24;
        if( t == 0 ) {
            return s;
        }

        s = t + "d " + s;
        return s;
    }
}
