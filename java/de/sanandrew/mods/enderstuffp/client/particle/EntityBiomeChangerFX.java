/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.particle;

import codechicken.lib.math.MathHelper;
import de.sanandrew.core.manpack.mod.client.particle.EntityParticle;
import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffp.client.util.ClientProxy;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityBiomeChangerFX
        extends EntityParticle
{
    private int prevParticleAge;
    private final boolean showAsPerimeter;
    private final int biomeId;
    private static final Quartet<Double, Double, Double, Double> PARTICLE_ICON_POS = Quartet.with(0.0D, 0.0D, 0.0625D, 0.0625D);

    public EntityBiomeChangerFX(World world, double x, double y, double z, int biomeId, boolean showPerimeter) {
        super(world, x, y, z);
        Triplet<Integer, Integer, Integer> myBlockPos = Triplet.with(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));

        this.particleMaxAge = 20;
        this.noClip = true;
        this.showAsPerimeter = showPerimeter;
        this.biomeId = biomeId;
        if( showPerimeter ) {
            this.particleMaxAge = 1;
        } else {
            this.particleMaxAge = 20;
            this.motionY = 0.2F;
        }
    }

    @Override
    public int getFXLayer() {
        return ClientProxy.particleFxLayer;
    }

    @Override
    public void onUpdate() {
        prevParticleAge = particleAge;
        super.onUpdate();
    }

    @Override
    public void renderParticle(Tessellator tessellator, float partTicks, float x, float y, float z, float x1, float z1) {
        double scale = 1.0D;

        double boxPosX = this.prevPosX + (this.posX - this.prevPosX) * partTicks - interpPosX;
        double boxPosY = this.prevPosY + (this.posY - this.prevPosY) * partTicks - interpPosY;
        double boxPosZ = this.prevPosZ + (this.posZ - this.prevPosZ) * partTicks - interpPosZ;

        tessellator.setBrightness(255);

        if( this.showAsPerimeter ) {
            scale = 0.5D;
        } else {
            scale -= (this.prevParticleAge + (this.particleAge - this.prevParticleAge) * partTicks) / (double) this.particleMaxAge;
        }

        tessellator.setColorOpaque(255, 255, 255);

        boxPosX += 0.5 - scale / 2;
        boxPosZ += 0.5 - scale / 2;

        drawYNeg(tessellator, boxPosX, boxPosZ, boxPosX + scale, boxPosZ + scale, boxPosY);
        drawYPos(tessellator, boxPosX, boxPosZ, boxPosX + scale, boxPosZ + scale, boxPosY + scale);
        drawZNeg(tessellator, boxPosX, boxPosY, boxPosX + scale, boxPosY + scale, boxPosZ);
        drawZPos(tessellator, boxPosX, boxPosY, boxPosX + scale, boxPosY + scale, boxPosZ + scale);
        drawXNeg(tessellator, boxPosZ, boxPosY, boxPosZ + scale, boxPosY + scale, boxPosX);
        drawXPos(tessellator, boxPosZ, boxPosY, boxPosZ + scale, boxPosY + scale, boxPosX + scale);
    }

    private static void drawZPos(Tessellator tessellator, double cornerBeginX, double cornerBeginY, double cornerEndX, double cornerEndY, double z) {
        double minU = PARTICLE_ICON_POS.getValue0();
        double maxU = PARTICLE_ICON_POS.getValue2();
        double minV = PARTICLE_ICON_POS.getValue1();
        double maxV = PARTICLE_ICON_POS.getValue3();

        tessellator.addVertexWithUV(cornerEndX,   cornerBeginY, z, maxU, maxV);
        tessellator.addVertexWithUV(cornerEndX,   cornerEndY,   z, maxU, minV);
        tessellator.addVertexWithUV(cornerBeginX, cornerEndY,   z, minU, minV);
        tessellator.addVertexWithUV(cornerBeginX, cornerBeginY, z, minU, maxV);
    }

    private static void drawZNeg(Tessellator tessellator, double cornerBeginX, double cornerBeginY, double cornerEndX, double cornerEndY, double z) {
        double minU = PARTICLE_ICON_POS.getValue0();
        double maxU = PARTICLE_ICON_POS.getValue2();
        double minV = PARTICLE_ICON_POS.getValue1();
        double maxV = PARTICLE_ICON_POS.getValue3();

        tessellator.addVertexWithUV(cornerBeginX, cornerBeginY, z, maxU, maxV);
        tessellator.addVertexWithUV(cornerBeginX, cornerEndY,   z, maxU, minV);
        tessellator.addVertexWithUV(cornerEndX,   cornerEndY,   z, minU, minV);
        tessellator.addVertexWithUV(cornerEndX,   cornerBeginY, z, minU, maxV);
    }

    private static void drawXPos(Tessellator tessellator, double cornerBeginZ, double cornerBeginY, double cornerEndZ, double cornerEndY, double x) {
        double minU = PARTICLE_ICON_POS.getValue0();
        double maxU = PARTICLE_ICON_POS.getValue2();
        double minV = PARTICLE_ICON_POS.getValue1();
        double maxV = PARTICLE_ICON_POS.getValue3();

        tessellator.addVertexWithUV(x, cornerBeginY, cornerBeginZ, maxU, maxV);
        tessellator.addVertexWithUV(x, cornerEndY,   cornerBeginZ, maxU, minV);
        tessellator.addVertexWithUV(x,   cornerEndY,   cornerEndZ, minU, minV);
        tessellator.addVertexWithUV(x,   cornerBeginY, cornerEndZ, minU, maxV);
    }

    private static void drawXNeg(Tessellator tessellator, double cornerBeginZ, double cornerBeginY, double cornerEndZ, double cornerEndY, double x) {
        double minU = PARTICLE_ICON_POS.getValue0();
        double maxU = PARTICLE_ICON_POS.getValue2();
        double minV = PARTICLE_ICON_POS.getValue1();
        double maxV = PARTICLE_ICON_POS.getValue3();

        tessellator.addVertexWithUV(x,   cornerBeginY, cornerEndZ, maxU, maxV);
        tessellator.addVertexWithUV(x,   cornerEndY,   cornerEndZ, maxU, minV);
        tessellator.addVertexWithUV(x, cornerEndY,   cornerBeginZ, minU, minV);
        tessellator.addVertexWithUV(x, cornerBeginY, cornerBeginZ, minU, maxV);
    }

    private static void drawYPos(Tessellator tessellator, double cornerBeginX, double cornerBeginZ, double cornerEndX, double cornerEndZ, double y) {
        double minU = PARTICLE_ICON_POS.getValue0();
        double maxU = PARTICLE_ICON_POS.getValue2();
        double minV = PARTICLE_ICON_POS.getValue1();
        double maxV = PARTICLE_ICON_POS.getValue3();

        tessellator.addVertexWithUV(cornerBeginX, y, cornerEndZ, minU, maxV);
        tessellator.addVertexWithUV(cornerEndX, y,   cornerEndZ, maxU, maxV);
        tessellator.addVertexWithUV(cornerEndX,   y,   cornerBeginZ, maxU, minV);
        tessellator.addVertexWithUV(cornerBeginX,   y, cornerBeginZ, minU, minV);
    }

    private static void drawYNeg(Tessellator tessellator, double cornerBeginX, double cornerBeginZ, double cornerEndX, double cornerEndZ, double y) {
        double minU = PARTICLE_ICON_POS.getValue0();
        double maxU = PARTICLE_ICON_POS.getValue2();
        double minV = PARTICLE_ICON_POS.getValue1();
        double maxV = PARTICLE_ICON_POS.getValue3();

        tessellator.addVertexWithUV(cornerBeginX, y, cornerBeginZ, minU, minV);
        tessellator.addVertexWithUV(cornerEndX, y,   cornerBeginZ, maxU, minV);
        tessellator.addVertexWithUV(cornerEndX,   y,   cornerEndZ, maxU, maxV);
        tessellator.addVertexWithUV(cornerBeginX,   y, cornerEndZ, minU, maxV);
    }
}