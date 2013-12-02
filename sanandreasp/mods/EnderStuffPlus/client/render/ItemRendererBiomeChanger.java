package sanandreasp.mods.EnderStuffPlus.client.render;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.client.model.ModelBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererBiomeChanger implements IItemRenderer {
	
	private ModelBiomeChanger modelBC = new ModelBiomeChanger();

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
			case ENTITY: renderBiomeChanger(0.0F, -0.45F, 0.0F); break;
			case EQUIPPED: renderBiomeChanger(0F, 0.4F, 0.5F); break;
			case INVENTORY: renderBiomeChanger(1F, 0.26F, 1F); break;
			default: renderBiomeChanger(0F, 0.4F, 0.5F);
		}
	
	}
	
	private void renderBiomeChanger(float x, float y, float z){
		Tessellator tesselator = Tessellator.instance;
		Minecraft.getMinecraft().getTextureManager().bindTexture(Textures.BIOMECHANGER_TEXTURE);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		modelBC.renderBlock();
		GL11.glPopMatrix();
	}
}
