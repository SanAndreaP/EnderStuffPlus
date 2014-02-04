package sanandreasp.mods.EnderStuffPlus.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityEnderMobFX extends EntityPortalFX
{
	public EntityEnderMobFX(World world, double x, double y, double z, double motX, double motY, double motZ, float red, float green, float blue) {
		super(world, x, y, z, motX, motY, motZ);
		
		this.particleRed = red;
		this.particleGreen = green;
		this.particleBlue = blue;
		
		float rndColor = this.rand.nextFloat() * 0.5F - 0.25F;
		
		this.particleRed += rndColor;
		this.particleGreen += rndColor;
		this.particleBlue += rndColor;
	}
}
