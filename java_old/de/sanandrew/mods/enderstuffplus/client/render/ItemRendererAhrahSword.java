package de.sanandrew.mods.enderstuffplus.client.render;

import org.lwjgl.opengl.GL11;

import de.sanandrew.core.manpack.util.client.ItemRenderHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.IItemRenderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemRendererAhrahSword
    implements IItemRenderer
{
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        EntityLivingBase entity = (EntityLivingBase) data[1];

        GL11.glPopMatrix();                             // prevents Forge from pre-translating the item
        GL11.glPushMatrix();
        GL11.glRotatef(45F, 0F, 1F, 0F);
        GL11.glScalef(1.0F, 1.0F, 0.5F);
        GL11.glRotatef(45F, 0F, -1F, 0F);
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        ItemRenderHelper.renderItem(entity, item, 0, false);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

}
