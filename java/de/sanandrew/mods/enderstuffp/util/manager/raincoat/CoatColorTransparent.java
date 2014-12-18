package de.sanandrew.mods.enderstuffp.util.manager.raincoat;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager.CoatColorEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CoatColorTransparent
    extends CoatColorEntry
{
    public CoatColorTransparent(String colorName, int itemColor, ResourceLocation colorMissTexture, ResourceLocation colorAvisTexture, ItemStack craftingIngredient) {
        super(colorName, itemColor, colorMissTexture, colorAvisTexture, craftingIngredient);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void preRender() {
        super.preRender();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void postRender() {
        super.postRender();
        GL11.glDisable(GL11.GL_BLEND);
    }
}
