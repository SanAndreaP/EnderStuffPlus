package sanandreasp.mods.EnderStuffPlus.client.render;

import org.lwjgl.opengl.GL11;

import sanandreasp.core.manpack.helpers.client.ItemRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ItemRendererGlowTools implements IItemRenderer {
	private RenderManager renderManager;
	private Minecraft mc;
	
	public ItemRendererGlowTools() {
		this.renderManager = RenderManager.instance;
		this.mc = Minecraft.getMinecraft();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		EntityLivingBase entity = (EntityLivingBase)data[1];
		ItemRenderer irInstance = this.mc.entityRenderer.itemRenderer;

		GL11.glPopMatrix(); // prevents Forge from pre-translating the item
		
		ItemRenderHelper.renderItem(entity, item, 0, false);
		ItemRenderHelper.renderItem(entity, item, 1, true);
		
		GL11.glPushMatrix();
	}

}
