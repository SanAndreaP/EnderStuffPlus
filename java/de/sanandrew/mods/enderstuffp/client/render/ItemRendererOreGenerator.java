package de.sanandrew.mods.enderstuffp.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.client.model.tileentity.ModelOreGenerator;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ItemRendererOreGenerator
    implements IItemRenderer
{
    private ModelOreGenerator modelGenerator = new ModelOreGenerator();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();

        switch( type ){
            case ENTITY:
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                this.renderGenerator(0.0F, -0.5F, 0.0F);
                break;
            case INVENTORY:
                this.renderGenerator(1.0F, 0.26F, 1.0F);
                break;
            case EQUIPPED: //FALL-THROUGH
            default:
                this.renderGenerator(0.5F, -0.05F, 0.5F);
        }

        GL11.glPopMatrix();
    }

    private void renderGenerator(float x, float y, float z) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(EnumTextures.ORE_GENERATOR.getResource());

        GL11.glTranslatef(x, y + 1.55F, z);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);

        this.modelGenerator.renderBlock();
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }
}
