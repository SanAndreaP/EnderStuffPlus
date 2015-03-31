/**
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.enderstuffp.client.render;

import de.sanandrew.mods.enderstuffp.client.render.tileentity.RenderTileEntityOreCrocoite;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class ItemRendererCrocoiteOre
        implements IItemRenderer
{
    private static ModelRenderer[] parts;

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
        GL11.glPushMatrix();

        switch( type ){
            case ENTITY:
                GL11.glTranslatef(0.0F, -0.5F, 0.0F);
                break;
            case INVENTORY:
                GL11.glTranslatef(1.0F, 0.3F, 1.0F);
                break;
            case EQUIPPED: //FALL-THROUGH
            default:
                GL11.glTranslatef(0.5F, 0.4F, 0.5F);
        }

        GL11.glPushMatrix();
        Tessellator tessellator = Tessellator.instance;
        RenderBlocks renderer = RenderBlocks.getInstance();

        renderer.renderMinX = 0.25F;
        renderer.renderMinZ = 0.25F;
        renderer.renderMaxX = 0.75F;
        renderer.renderMaxZ = 0.75F;
        renderer.renderMaxY = 0.125F;

        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.2F, -0.5F);

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(Blocks.end_stone, 0.0D, 0.0D, 0.0D, Blocks.end_stone.getIcon(0, 0));
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(Blocks.end_stone, 0.0D, 0.0D, 0.0D, Blocks.end_stone.getIcon(0, 0));
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(Blocks.end_stone, 0.0D, 0.0D, 0.0D, Blocks.end_stone.getIcon(0, 0));
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(Blocks.end_stone, 0.0D, 0.0D, 0.0D, Blocks.end_stone.getIcon(0, 0));
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(Blocks.end_stone, 0.0D, 0.0D, 0.0D, Blocks.end_stone.getIcon(0, 0));
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(Blocks.end_stone, 0.0D, 0.0D, 0.0D, Blocks.end_stone.getIcon(0, 0));
        tessellator.draw();
        GL11.glPopMatrix();

        parts = RenderTileEntityOreCrocoite.renderCrystal(parts, new Random(0), 0xF0);
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();
    }
}
