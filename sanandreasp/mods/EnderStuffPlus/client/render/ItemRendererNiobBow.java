package sanandreasp.mods.EnderStuffPlus.client.render;

import java.lang.reflect.Method;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sanandreasp.core.manpack.helpers.ItemRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ItemRendererNiobBow implements IItemRenderer {
	private RenderManager renderManager;
	private Minecraft mc;
	
	public ItemRendererNiobBow() {
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
		if( type == ItemRenderType.EQUIPPED_FIRST_PERSON ) {
			ItemRenderHelper.renderItem(entity, item, 0);
		} else {
			GL11.glPushMatrix();

			// contra-translate the item from it's standard translation
			// also apply some more scale or else the bow is tiny
            float f2 = 3F - (1F/3F);
            GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glScalef(f2, f2, f2);
            GL11.glTranslatef(-0.25F, -0.1875F, 0.1875F);
            
            // render the item as 'real' bow
            float f3 = 0.625F;
            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(f3, -f3, f3);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            
            ItemRenderHelper.renderItem(entity, item, 0);
			GL11.glPopMatrix();
		}
		GL11.glPushMatrix(); // prevents GL Underflow errors
	}
}
