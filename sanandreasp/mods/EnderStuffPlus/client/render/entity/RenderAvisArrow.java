package sanandreasp.mods.EnderStuffPlus.client.render.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.entity.EntityAvisArrow;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

@SideOnly(Side.CLIENT)
public class RenderAvisArrow extends RenderArrow implements Textures
{
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ARROW_AVIS;
	}
}