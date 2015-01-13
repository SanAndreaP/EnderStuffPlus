/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.entity.living.EntityEnderAvisPet;
import de.sanandrew.mods.enderstuffp.entity.living.IEnderPet;
import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderGameOverlayHandler
        extends Gui
{
    private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");

    private Minecraft mc;

    public RenderGameOverlayHandler() {
        this.mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void onRenderHUD(RenderGameOverlayEvent.Pre event) {
        if( event.type == RenderGameOverlayEvent.ElementType.HEALTHMOUNT ) {
            if( this.mc.thePlayer != null && this.mc.thePlayer.ridingEntity != null && this.mc.thePlayer.ridingEntity instanceof IEnderPet ) {
                ScaledResolution scaledRes = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                int scaledWdt = scaledRes.getScaledWidth();
                int scaledHgt = scaledRes.getScaledHeight();
                IEnderPet pet = ((IEnderPet) this.mc.thePlayer.ridingEntity);

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                this.mc.getTextureManager().bindTexture(EnumTextures.GUI_INGAMEICONS.getResource());
                this.renderEnderStats(pet.getEntity().getHealth(), pet.getEntity().getMaxHealth(), scaledWdt / 2 + 91, scaledHgt - 39, 9);

                if( this.mc.thePlayer.ridingEntity instanceof EntityEnderAvisPet ) {
                    this.renderEnderStats(((EntityEnderAvisPet) pet).getFlightCondition(), 20.0F, scaledWdt / 2 + 91, scaledHgt - 54, 0);
                }

                event.setCanceled(true);
            }
        }
    }

    private void renderEnderStats(float currStat, float maxStat, int posX, int posY, int texYOff) {
        int currStatHalf = (int) (Math.floor(currStat) / 2.0F + 0.1F);
        int maxStatHalf = (int) (Math.floor(maxStat) / 2.0F + 0.1F);

        boolean addHalfCurrStat = Math.floor(currStat) % 2 != 0;
        boolean addHalfMaxStat = Math.floor(maxStat) % 2 != 0;

        for( int i = maxStatHalf - 1; i >= 0; i-- ) {
            int shiftedPosX = posX - i * 8 - 9 + 80 * (i / 10);
            this.drawTexturedModalRect(shiftedPosX, posY - 3 * (i / 10), 0, texYOff, 9, 9);
            if( i < currStatHalf ) {
                this.drawTexturedModalRect(shiftedPosX, posY - 3 * (i / 10), 9, texYOff, 9, 9);
            } else if( addHalfCurrStat && i == currStatHalf ) {
                this.drawTexturedModalRect(posX - (i) * 8 - 9 + 80 * ((i) / 10), posY - 3 * ((i) / 10), 18, texYOff, 9, 9);
            }
        }

        if( addHalfMaxStat ) {
            this.drawTexturedModalRect(posX - maxStatHalf * 8 - 9 + 80 * (maxStatHalf / 10), posY - 3 * (maxStatHalf / 10), 0, texYOff, 4, 9);
        }
    }

    @SubscribeEvent
    public void renderFogColor(FogColors event) {
        if( event.block == EspBlocks.endFluidBlock ) {
            event.red = 0.6F;
            event.green = 0.0F;
            event.blue = 0.8F;
        }
    }

    @SubscribeEvent
    public void renderFogDensity(FogDensity event) {
        if( event.block == EspBlocks.endFluidBlock ) {
            event.density = 0.7F;
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void renderBlockOverlay(RenderBlockOverlayEvent event) {
        if( event.player.worldObj.getBlock(event.blockX, event.blockY, event.blockZ) == EspBlocks.endFluidBlock ) {
            this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
            Tessellator tessellator = Tessellator.instance;
            float f1 = this.mc.thePlayer.getBrightness(event.renderPartialTicks);
            GL11.glColor4f(f1 * 0.8F, f1 * 0.0F, f1 * 0.6F, 1.0F);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glPushMatrix();
            float f2 = 4.0F;
            float f3 = -1.0F;
            float f4 = 1.0F;
            float f5 = -1.0F;
            float f6 = 1.0F;
            float f7 = -0.5F;
            float f8 = -this.mc.thePlayer.rotationYaw / 64.0F;
            float f9 = this.mc.thePlayer.rotationPitch / 64.0F;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(f3, f5, f7, (f2 + f8), (f2 + f9));
            tessellator.addVertexWithUV(f4, f5, f7, (0.0F + f8), (f2 + f9));
            tessellator.addVertexWithUV(f4, f6, f7, (0.0F + f8), (0.0F + f9));
            tessellator.addVertexWithUV(f3, f6, f7, (f2 + f8), (0.0F + f9));
            tessellator.draw();
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            event.setCanceled(true);
        }
    }
}
