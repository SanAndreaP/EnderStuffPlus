package de.sanandrew.mods.enderstuffp.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityBiomeDataFX
        extends EntityPortalFX
{
    public EntityBiomeDataFX(World world, double x, double y, double z, double motX, double motY, double motZ, float red, float green, float blue) {
        super(world, x, y, z, motX, motY, motZ);

        float rndColor = this.rand.nextFloat() * 0.5F - 0.25F;

        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;

        this.particleRed += rndColor;
        this.particleGreen += rndColor;
        this.particleBlue += rndColor;

        this.setParticleTextureIndex((int)(Math.random() * 26.0D + 1.0D + 224.0D));
    }
}
