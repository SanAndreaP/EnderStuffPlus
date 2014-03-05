package sanandreasp.mods.EnderStuffPlus.client.render;

import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.mods.EnderStuffPlus.client.model.ModelWeatherAltar;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public class ItemRendererWeatherAltar implements IItemRenderer, Textures
{
	private ModelWeatherAltar modelWA = new ModelWeatherAltar();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}


	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case ENTITY:
				this.renderWeatherAltar(0.0F, -0.45F, 0.0F);
				break;
			case INVENTORY:
				this.renderWeatherAltar(1F, 0.26F, 1F);
				break;
			case EQUIPPED: // FALL-THROUGH
			default:
				this.renderWeatherAltar(0.5F, 0.4F, 0.5F);
		}
	
	}
	
	private void renderWeatherAltar(float x, float y, float z){
		Minecraft.getMinecraft().getTextureManager().bindTexture(WEATHERALTAR_TEXTURE);
		
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y+1.57F, z);
		GL11.glRotatef(180F, 1F, 0F, 0F);
		GL11.glRotatef(90F, 0F, 1F, 0F);
		
		this.modelWA.renderBlock();
		
		GL11.glPopMatrix();
	}
}