package de.sanandrew.mods.enderstuffp.client.render.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.client.helpers.SAPClientUtils;
import de.sanandrew.mods.enderstuffp.client.model.tileentity.ModelOreGenerator;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityOreGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTileEntityOreGenerator
    extends TileEntitySpecialRenderer
{
    ModelOreGenerator modelBlock = new ModelOreGenerator();

//    private void renderIcon(IIcon ico, float red, float green, float blue) {
//        Tessellator tess = Tessellator.instance;
//
//        if( ico == null ) {
//            ico = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getTextureExtry("missingno");
//        }
//
//        float icoMinU = ico.getMinU();
//        float icoMaxU = ico.getMaxU();
//        float icoMinV = ico.getMinV();
//        float icoMaxV = ico.getMaxV();
//
//        GL11.glPushMatrix();
//        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
//        GL11.glTranslatef(-0.5F, -0.25F, -0.0421875F);
//        GL11.glTranslatef(0f, 0f, 0.084375F);
//
//        this.bindTexture(TextureMap.locationItemsTexture);
//
//        GL11.glColor4f(red, green, blue, 1.0F);
//
//        ItemRenderer.renderItemIn2D(tess, icoMaxU, icoMinV, icoMinU, icoMaxV, ico.getIconWidth(), ico.getIconHeight(), 0.0625F);
//
//        GL11.glPopMatrix();
//    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partTicks) {
        TileEntityOreGenerator te = ((TileEntityOreGenerator) tile);
//        Pair<float[], float[]> itmFloat = te.getItemFloat();

        final double displayX = -2.5D;
        final double displayY = -0.775D;
        final double displayHeight = 0.0775D;

        this.bindTexture(EnumTextures.ORE_GENERATOR.getResource());

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180F, 1F, 0F, 0F);
        GL11.glRotatef(180F, 0F, 1F, 0F);
        GL11.glRotatef(te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord, te.zCoord) * 90F, 0F, 1F, 0F);

        this.modelBlock.renderBlock();

        GL11.glRotatef(-180F, 1F, 0F, 0F);
        GL11.glScalef(0.25F, 0.25F, 0.25F);
        GL11.glTranslatef(0.0F, -2.5F, 0.0F);
        GL11.glTranslatef(1.25F, 0F, 0F);

        if( !Minecraft.getMinecraft().isGamePaused() ) {
            te.drawCycles++;
            if( te.drawCycles >= 3200 ) {
                te.drawCycles = 0;
            }
        }

        for( int i = 0; i < 40; i++ ) {
            double yShift = Math.sin((te.drawCycles * (i / 80.0D + 0.625D)) / 20.0D * Math.PI) * 6.0D * displayHeight;
            GL11.glColor4f(0.2F, 0.2F, 1.0F, 1.0F);
            SAPClientUtils.drawSquareZPos(displayX + i*0.025D, displayY + yShift, displayX + 0.025D + i * 0.025D, displayY + 0.05 + yShift, 2.02);
        }

        GL11.glPopMatrix();
    }
}
