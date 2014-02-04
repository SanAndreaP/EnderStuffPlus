package sanandreasp.mods.EnderStuffPlus.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

public class RenderHUDEvent extends Gui implements Textures {
	private Minecraft mc;

	public RenderHUDEvent() {
		this.mc = Minecraft.getMinecraft();
	}

	@ForgeSubscribe
	public void onRenderHUD(RenderGameOverlayEvent.Pre evt) {
		if( evt.type.equals(RenderGameOverlayEvent.ElementType.HOTBAR) ) {
			if (this.mc.thePlayer != null
					&& this.mc.thePlayer.ridingEntity != null
					&& this.mc.thePlayer.ridingEntity instanceof IEnderPet) {
				ScaledResolution scaledRes = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
				int scaledWdt = scaledRes.getScaledWidth();
				int scaledHgt = scaledRes.getScaledHeight();
				IEnderPet pet = ((IEnderPet) this.mc.thePlayer.ridingEntity);

				GL11.glColor4f(1F, 1F, 1F, 1F);

				this.mc.getTextureManager().bindTexture(GUI_INGAMEICONS);

				renderEnderStats(pet.getPetHealth(), pet.getPetMaxHealth(), scaledWdt / 2 + 91, scaledHgt - 49, 9);
				if( this.mc.thePlayer.ridingEntity instanceof EntityEnderAvis ) {
					renderEnderStats(((EntityEnderAvis) pet).getCurrFlightCondInt(), 20, scaledWdt / 2 + 91, scaledHgt - 59, 0);
				}

//				this.mc.renderEngine.func_110550_d();
			}
		}
	}

	private void renderEnderStats(float curCond, float maxCond, int posX, int posY, int texYOff) {
		for( int i = 0; i < 10; i++ ) {
			int var6 = posX - i * 8 - 9;
			this.drawTexturedModalRect(var6, posY, 0, texYOff, 9, 9);
		}
		
		int percCond = (int)(Math.round((curCond / maxCond) * 20.0F) + 0.5F);

		boolean addAHalf = false;

		if( percCond % 2 != 0 && curCond >= 0 )
			addAHalf = true;

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
