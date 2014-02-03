package sanandreasp.mods.EnderStuffPlus.client.particle;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.world.World;

public final class ParticleFXFuncCollection
{
	private static Random rand = new Random();
	
	public static void spawnEndLeavesFX(World world, int x, int y, int z, Random blkRand) {
    	Block blockBelow = Block.blocksList[world.getBlockId(x, y-1, z)];
    	if( (blockBelow == null || blockBelow.isAirBlock(world, x, y-1, z)) && blkRand.nextInt(2) == 1 ) {
            double partX = ((float)x + blkRand.nextFloat());
            double partY = (double)y - 0.05D;
            double partZ = ((float)z + blkRand.nextFloat());
            EntityReddustFX fx = new EntityReddustFX(world, partX, partY, partZ, 0.26F, 0.0F, 0.35F);
            fx.motionY = -0.2D;
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
	}
	
	public static void spawnPortalFX(World world, double posX, double posY, double posZ, float colorR, float colorG, float colorB, float width, float height) {
		for( int k = 0; k < 2; k++ ) {
			EntityFX part = new ParticleFX_EnderMob(
					world,
					posX + (rand.nextDouble() - 0.5D) * width,
					(posY + rand.nextDouble() * height) - 0.25D,
					posZ + (rand.nextDouble() - 0.5D) * width,
					(rand.nextDouble() - 0.5D) * 2D, -rand.nextDouble(),
					(rand.nextDouble() - 0.5D) * 2D,
					(float)colorR,
					(float)colorG,
					(float)colorB
			);
			Minecraft.getMinecraft().effectRenderer.addEffect(part);
		}
	}
	
	public static void spawnRayballFX(World world, double posX, double posY, double posZ) {
		Minecraft.getMinecraft().effectRenderer.addEffect(
				new ParticleFX_Rayball(world, posX, posY + 0.25D, posZ, 1.0F, 0.0F, 1.0F)
		);
	}
	
	public static void spawnRefuseTameFX(World world, double posX, double posY, double posZ, float width, float height) {
		for( int k = 0; k < 7; k++ ) {
			world.spawnParticle(
					"smoke",
					posX + rand.nextFloat() * width * 2.0F - width,
					posY + 0.5D + rand.nextFloat() * height,
					posZ + rand.nextFloat() * width * 2.0F - width,
					rand.nextGaussian() * 0.02D,
					rand.nextGaussian() * 0.02D,
					rand.nextGaussian() * 0.02D
			);
		}
	}
	
	public static void spawnAcceptTameFX(World world, double posX, double posY, double posZ, float width, float height) {
		for( int var3 = 0; var3 < 7; ++var3 ) {
			world.spawnParticle(
					"heart",
					posX + rand.nextFloat() * width * 2.0F - width,
					posY + 0.5D + rand.nextFloat() * height,
					posZ + rand.nextFloat() * width * 2.0F - width,
					rand.nextGaussian() * 0.02D,
					rand.nextGaussian() * 0.02D,
					rand.nextGaussian() * 0.02D
			);
		}
	}
}
