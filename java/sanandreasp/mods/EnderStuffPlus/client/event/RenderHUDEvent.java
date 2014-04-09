package sanandreasp.mods.EnderStuffPlus.client.event;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.ForgeSubscribe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHUDEvent
    extends Gui
{
    private Minecraft mc;

    public RenderHUDEvent() {
        this.mc = Minecraft.getMinecraft();
    }

    @ForgeSubscribe
    public void onRenderHUD(RenderGameOverlayEvent.Pre event) {
        if( event.type == RenderGameOverlayEvent.ElementType.HEALTHMOUNT ) {
            if( this.mc.thePlayer != null && this.mc.thePlayer.ridingEntity != null
                && this.mc.thePlayer.ridingEntity instanceof IEnderPet )
            {
                ScaledResolution scaledRes = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                int scaledWdt = scaledRes.getScaledWidth();
                int scaledHgt = scaledRes.getScaledHeight();
                IEnderPet pet = ((IEnderPet) this.mc.thePlayer.ridingEntity);

                GL11.glColor4f(1F, 1F, 1F, 1F);

                this.mc.getTextureManager().bindTexture(Textures.GUI_INGAMEICONS.getResource());
                this.renderEnderStats(pet.getPetHealth(), pet.getPetMaxHealth(), scaledWdt / 2 + 91, scaledHgt - 39, 9);

                if( this.mc.thePlayer.ridingEntity instanceof EntityEnderAvis ) {
                    this.renderEnderStats(((EntityEnderAvis) pet).getCurrFlightCondInt(), 20, scaledWdt / 2 + 91, scaledHgt - 49, 0);
                }
                event.setCanceled(true);
            }
        }
    }

    private void renderEnderStats(float curCond, float maxCond, int posX, int posY, int texYOff) {
        for( int i = 0; i < 10; i++ ) {
            int shiftedPosX = posX - i * 8 - 9;

            this.drawTexturedModalRect(shiftedPosX, posY, 0, texYOff, 9, 9);
        }

        int percCond = (int) (Math.round((curCond / maxCond) * 20.0F) + 0.5F);

        boolean addAHalf = false;

        if( percCond % 2 != 0 && curCond >= 0 ) {
            addAHalf = true;
        }

        for( int j = 0; j < percCond / 2; j++ ) {
            int var6 = posX - j * 8 - 9;
            this.drawTexturedModalRect(var6, posY, 9, texYOff, 9, 9);
        }

        if( addAHalf ) {
            int var6 = posX - (percCond / 2) * 8 - 9;
            this.drawTexturedModalRect(var6, posY, 18, texYOff, 9, 9);
        }
    }
}
