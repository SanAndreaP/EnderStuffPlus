package sanandreasp.mods.EnderStuffPlus.client.render.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

@SideOnly(Side.CLIENT)
public class RenderAvisArrow extends RenderArrow implements Textures
{
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ARROW_AVIS;
	}
}