package sanandreasp.mods.EnderStuffPlus.client.particle;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.src.*;
import net.minecraft.world.World;

public class ParticleFX_Rayball extends EntityReddustFX {

	public ParticleFX_Rayball(World par1World, double par2, double par4,
			double par6, float par8, float par9, float par10) {
		super(par1World, par2, par4, par6, par8, par9, par10);
	}

	public ParticleFX_Rayball(World par1World, double par2, double par4,
			double par6, float par8, float par9, float par10, float par11) {
		super(par1World, par2, par4, par6, par8, par9, par10, par11);
	}

	@Override
	public int getBrightnessForRender(float par1) {
		return 0x0f00f0;
	}
}
