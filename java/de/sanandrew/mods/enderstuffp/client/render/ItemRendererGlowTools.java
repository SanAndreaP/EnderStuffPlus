package de.sanandrew.mods.enderstuffp.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.client.ItemRenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ItemRendererGlowTools
    implements IItemRenderer
{
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        EntityLivingBase entity = (EntityLivingBase) data[1];

        GL11.glPopMatrix();                                         // prevents Forge from pre-translating the item

        ItemRenderHelper.renderItem(item, 0, false);
        ItemRenderHelper.renderItem(item, 1, true);

        GL11.glPushMatrix();
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

}
