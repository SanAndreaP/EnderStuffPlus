package de.sanandrew.mods.enderstuffp.client.render;

import de.sanandrew.mods.enderstuffp.client.model.tileentity.ModelBiomeChanger;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.IItemRenderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ItemRendererBiomeChanger
    implements IItemRenderer
{
    private ModelBiomeChanger modelBC = new ModelBiomeChanger();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    private void renderBiomeChanger(float x, float y, float z) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(EnumTextures.BIOMECHANGER.getResource());

        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);

        this.modelBC.renderBlock();

        GL11.glPopMatrix();
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch( type ){
            case ENTITY :
                this.renderBiomeChanger(0.0F, -0.45F, 0.0F);
                break;
            case EQUIPPED :
                this.renderBiomeChanger(0.0F, 0.4F, 0.5F);
                break;
            case INVENTORY :
                this.renderBiomeChanger(1.0F, 0.26F, 1.0F);
                break;
            default :
                this.renderBiomeChanger(0.0F, 0.4F, 0.5F);
        }

    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }
}
