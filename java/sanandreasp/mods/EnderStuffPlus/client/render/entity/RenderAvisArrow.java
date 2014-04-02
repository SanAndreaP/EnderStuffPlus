package sanandreasp.mods.EnderStuffPlus.client.render.entity;

import sanandreasp.mods.EnderStuffPlus.registry.Textures;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAvisArrow
    extends RenderArrow
{
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return Textures.ARROW_AVIS;
    }
}