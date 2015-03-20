/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.render.tileentity;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import de.sanandrew.core.manpack.util.client.helpers.ModelBoxBuilder;
import de.sanandrew.mods.enderstuffp.client.util.EnumTextures;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityOreCrocoite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class RenderTileEntityOreCrocoite
        extends TileEntitySpecialRenderer
{
    private ModelBase baseModel = new ModelBase() {};

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partTicks) {
        this.renderCrocoite((TileEntityOreCrocoite) tileEntity, x, y, z, partTicks);
    }

    private void renderCrocoite(TileEntityOreCrocoite tileEntity, double x, double y, double z, float partTicks) {
        if( tileEntity.parts == null ) {
            Random rng = new Random(tileEntity.xCoord + tileEntity.yCoord + tileEntity.zCoord);
            int partCnt = rng.nextInt(15) + 4 + 35;
            Vec3[] stemVecs = new Vec3[4];

            tileEntity.parts = new ModelRenderer[partCnt];

            generateStem(tileEntity.parts, stemVecs, 0, rng, 0.0F, 0.0F, 16, 10.0F, 10.0F, 10.0F, 0.0F, 0.0F, 0.0F);
            generateStem(tileEntity.parts, stemVecs, 1, rng, 2.0F, 2.0F, 10, 10.0F, 10.0F, 10.0F, 25.0F, 25.0F, 0.0F);
            generateStem(tileEntity.parts, stemVecs, 2, rng, -2.0F, 2.0F, 10, 10.0F, 10.0F, 10.0F, 25.0F, -25.0F, 0.0F);
            generateStem(tileEntity.parts, stemVecs, 3, rng, 0.0F, -2.0F, 10, 10.0F, 45.0F, 10.0F, -25.0F, 0.0F, 0.0F);

            for( int i = 4; i < partCnt; i++ ) {
                int stemInd = rng.nextInt(4);
                float partX = stemInd == 1 ? 2.0F : stemInd == 2 ? -2.0F : 0.0F;
                float partZ = stemInd == 0 ? 0.0F : stemInd == 3 ? -2.0F : 2.0F;
                generatePart(tileEntity.parts, stemVecs[stemInd], i, rng, partX, rng.nextFloat() * 6.0F + (stemInd == 0 ? 9.0F : 3.0F), partZ, 5,
                             rng.nextFloat() * -0.6F + 0.3F, 180.0F, 0.0F, 180.0F, 0.0F, 0.0F, 0.0F);
            }

            Collection<ModelRenderer> filteredParts = Collections2.filter(Arrays.asList(tileEntity.parts), new Predicate<ModelRenderer>() {
                                                                              @Override public boolean apply(@Nullable ModelRenderer input) { return input != null; }
                                                                          });

            tileEntity.parts = filteredParts.toArray(new ModelRenderer[filteredParts.size()]);
        }

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_SRC_COLOR, GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glEnable(GL11.GL_CULL_FACE);

        float lastBrightX = OpenGlHelper.lastBrightnessX;
        float lastBrightY = OpenGlHelper.lastBrightnessY;
        float brightX = 0xF0;
        float brightY = Minecraft.getMinecraft().theWorld.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord) * 0xF0;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);

        this.bindTexture(EnumTextures.CROCOITE_CRYSTALS.getResource());
        for( int i = tileEntity.parts.length - 1; i >= 0; i-- ) {
            tileEntity.parts[i].render(0.0625F);
        }

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightX, lastBrightY);

        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void generateStem(ModelRenderer[] partArr, Vec3[] stemVecs, int index, Random rng, float x, float z, int height, float rngX, float rngY, float rngZ,
                              float minAngX, float minAngY, float minAngZ) {
        rngX = ((rng.nextFloat() * 2.0F - 1.0F) * rngX + minAngX) / 180.0F * (float) Math.PI;
        rngY = ((rng.nextFloat() * 2.0F - 1.0F) * rngY + minAngY) / 180.0F * (float) Math.PI;
        rngZ = ((rng.nextFloat() * 2.0F - 1.0F) * rngZ + minAngZ) / 180.0F * (float) Math.PI;
        partArr[index] = ModelBoxBuilder.newBuilder(baseModel).setLocation(x, 0.0F, z).setRotation(rngX, rngY, rngZ).getBox(-1.0F, 0.0F, -1.0F, 2, height, 2, 0.0F);
        stemVecs[index] = Vec3.createVectorHelper(0.0F, 1.0F, 0.0F);
        stemVecs[index].rotateAroundX(rngX);
        stemVecs[index].rotateAroundY(rngY);
        stemVecs[index].rotateAroundZ(rngZ);
    }

    private void generatePart(ModelRenderer[] partArr, Vec3 stemVec, int index, Random rng, float x, float y, float z, int height, float scale, float rngX, float rngY,
                              float rngZ, float minAngX, float minAngY, float minAngZ) {
        rngX = ((rng.nextFloat() * 2.0F - 1.0F) * rngX + minAngX) / 180.0F * (float) Math.PI;
        rngY = ((rng.nextFloat() * 2.0F - 1.0F) * rngY + minAngY) / 180.0F * (float) Math.PI;
        rngZ = ((rng.nextFloat() * 2.0F - 1.0F) * rngZ + minAngZ) / 180.0F * (float) Math.PI;

        x -= stemVec.xCoord * y;
        y *= stemVec.yCoord;
        z -= stemVec.zCoord * y;

        partArr[index] = ModelBoxBuilder.newBuilder(baseModel).setLocation(x, y, z).setRotation(rngX, rngY, rngZ).getBox(-1.0F, 0.0F, -1.0F, 1, height, 1, scale);
    }
}
