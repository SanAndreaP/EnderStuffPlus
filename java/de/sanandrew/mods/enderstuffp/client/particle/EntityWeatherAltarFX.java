package de.sanandrew.mods.enderstuffp.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityWeatherAltarFX
        extends EntityFX
{
    private double initX;
    private double initY;
    private double initZ;

    public EntityWeatherAltarFX(World world, double x, double y, double z, double motX, double motY, double motZ) {
        super(world, x + motX, y + motY, z + motZ, motX, motY, motZ);

        float colorMulti = this.rand.nextFloat() * 0.6F + 0.4F;

        this.motionX = motX;
        this.motionY = motY;
        this.motionZ = motZ;
        this.initX = x;
        this.initY = y;
        this.initZ = z;
        this.particleScale = this.rand.nextFloat() * 0.5F + 0.2F;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F * colorMulti;
        this.particleGreen *= 0.9F;
        this.particleRed *= 0.9F;
        this.particleMaxAge = (int) (Math.random() * 10.0D) + 30;
        this.noClip = true;

        this.setParticleTextureIndex((int) (Math.random() * 26.0D + 1.0D + 224.0D));
    }

    @Override
    public float getBrightness(float par1) {
        float f1 = super.getBrightness(par1);
        float f2 = (float) this.particleAge / (float) this.particleMaxAge;

        f2 *= f2;
        f2 *= f2;
        return f1 * (1.0F - f2) + f2;
    }

    @Override
    public int getBrightnessForRender(float par1) {
        int i = super.getBrightnessForRender(par1);
        float f1 = (float) this.particleAge / (float) this.particleMaxAge;
        int j = i & 255;
        int k = i >> 16 & 255;

        f1 *= f1;
        f1 *= f1;
        k += (int) (f1 * 15.0F * 16.0F);

        if( k > 240 ) {
            k = 240;
        }

        return j | k << 16;
    }

    @Override
    public void onUpdate() {
        float f = (float) this.particleAge / (float) this.particleMaxAge;

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        f = 1.0F - f;
        float f1 = 1.0F - f;

        f1 *= f1;
        f1 *= f1;
        this.posX = this.initX + this.motionX * f;
        this.posY = this.initY + this.motionY * f - f1 * 1.2F;
        this.posZ = this.initZ + this.motionZ * f;

        if( this.particleAge++ >= this.particleMaxAge ) {
            this.setDead();
        }
    }

    @Override
    public void setPosition(double par1, double par3, double par5) {
        super.setPosition(par1, par3, par5);
    }
}
