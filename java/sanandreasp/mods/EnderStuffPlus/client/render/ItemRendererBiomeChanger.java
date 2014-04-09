package sanandreasp.mods.EnderStuffPlus.client.render;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.EnderStuffPlus.client.model.ModelBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;

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
        Minecraft.getMinecraft().getTextureManager().bindTexture(Textures.BIOMECHANGER_TEXTURE.getResource());

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
                this.renderBiomeChanger(0F, 0.4F, 0.5F);
                break;
            case INVENTORY :
                this.renderBiomeChanger(1F, 0.26F, 1F);
                break;
            default :
                this.renderBiomeChanger(0F, 0.4F, 0.5F);
        }

    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }
}
