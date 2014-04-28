package de.sanandrew.mods.enderstuffplus.client.render.entity;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.registry.Textures;

@SideOnly(Side.CLIENT)
public class RenderAvisArrow
    extends RenderArrow
{
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return Textures.ARROW_AVIS.getResource();
    }
}