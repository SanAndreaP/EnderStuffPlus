package sanandreasp.mods.EnderStuffPlus.client.particle;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.world.World;

public class ParticleFX_EnderMob extends EntityPortalFX {

	public ParticleFX_EnderMob(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float red, float green, float blue) {
		super(par1World, par2, par4, par6, par8, par10, par12);
		
		this.particleRed = red;
		this.particleGreen = green;
		this.particleBlue = blue;
		
		float rndColor = this.rand.nextFloat() * 0.5F - 0.25F;
		
		this.particleRed += rndColor;
		this.particleGreen += rndColor;
		this.particleBlue += rndColor;
	}

}
