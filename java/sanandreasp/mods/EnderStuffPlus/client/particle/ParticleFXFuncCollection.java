package sanandreasp.mods.EnderStuffPlus.client.particle;

import java.util.List;
import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class ParticleFXFuncCollection
{
    private static Random rand = new Random();

    public static void spawnAcceptTameFX(World world, double x, double y, double z, float width, float height) {
        for( int i = 0; i < 7; ++i ) {
            world.spawnParticle("heart", x + rand.nextFloat() * width * 2.0F - width, y + 0.5D + rand.nextFloat() * height,
                                z + rand.nextFloat() * width * 2.0F - width, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D,
                                rand.nextGaussian() * 0.02D);
        }
    }

    public static void spawnDuplicatorFX(World world, int x, int y, int z, Random rand) {
        int meta = world.getBlockMetadata(x, y, z);
        double partX = x + 0.5D;
        double partY = y + 0.3D + rand.nextDouble() * 6.0D / 16.0D;
        double partZ = z + 0.5D;
        float shift = 0.7F;
        float randPos = rand.nextFloat() * 0.4F - 0.2F;

        if( meta == 5 ) {
            partX -= shift;
            partZ += randPos;
        } else if( meta == 7 ) {
            partX += shift;
            partZ += randPos;
        } else if( meta == 6 ) {
            partX += randPos;
            partZ -= shift;
        } else if( meta == 4 ) {
            partX += randPos;
            partZ += shift;
        }

        EntityFX particle = new EntityAuraFX(world, partX, partY, partZ, 0.0D, 0.0D, 0.0D);
        
        particle.setParticleTextureIndex(66);
        particle.setRBGColorF(0.25F + rand.nextFloat() * 0.25F, 0.0F, 1.0F);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
        particle.setPosition(partX, partY, partZ);
    }

    public static void spawnEndLeavesFX(World world, int x, int y, int z, Random blkRand) {
        Block blockBelow = Block.blocksList[world.getBlockId(x, y - 1, z)];
        if( (blockBelow == null || blockBelow.isAirBlock(world, x, y - 1, z)) && blkRand.nextInt(2) == 1 ) {
            double partX = (x + blkRand.nextFloat());
            double partY = y - 0.05D;
            double partZ = (z + blkRand.nextFloat());
            EntityReddustFX fx = new EntityReddustFX(world, partX, partY, partZ, 0.26F, 0.0F, 0.35F);
            
            fx.motionY = -0.2D;
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }

    public static void spawnPortalFX(World world, double x, double y, double z, float clrRed, float clrGreen, float clrBlue, float width, float height) {
        for( int i = 0; i < 2; i++ ) {
            EntityFX part = new EntityEnderMobFX(world, x + (rand.nextDouble() - 0.5D) * width, (y + rand.nextDouble() * height) - 0.25D,
                                                 z + (rand.nextDouble() - 0.5D) * width, (rand.nextDouble() - 0.5D) * 2D, -rand.nextDouble(),
                                                 (rand.nextDouble() - 0.5D) * 2D, clrRed, clrGreen, clrBlue);
            
            Minecraft.getMinecraft().effectRenderer.addEffect(part);
        }
    }

    public static void spawnRayballFX(World world, double x, double y, double z) {
        Minecraft.getMinecraft().effectRenderer.addEffect(new EntityRayballFX(world, x, y + 0.25D, z, 1.0F, 0.0F, 1.0F));
    }

    public static void spawnRefuseTameFX(World world, double x, double y, double z, float width, float height) {
        for( int i = 0; i < 7; i++ ) {
            world.spawnParticle("smoke", x + rand.nextFloat() * width * 2.0F - width, y + 0.5D + rand.nextFloat() * height,
                                z + rand.nextFloat() * width * 2.0F - width, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D,
                                rand.nextGaussian() * 0.02D);
        }
    }

    public static void spawnWeatherAltarFX(World world, int x, int y, int z, Random rand) {
        TileEntityWeatherAltar altar = (TileEntityWeatherAltar) world.getBlockTileEntity(x, y, z);
        List<Integer[]> blockCoords = altar.getSurroundingPillars();
        
        for( Integer[] coords : blockCoords ) {
            if( rand.nextInt(8) == 0 ) {
                EntityFX particle = new EntityWeatherAltarFX(world, x + 0.5D, y + 2.0D, z + 0.5D, coords[0] - x + rand.nextFloat() - 0.5D,
                                                             coords[1] - y - rand.nextFloat() - 1.0F, coords[2] - z + rand.nextFloat() - 0.5D);
                
                particle.setParticleTextureIndex(66);
                particle.setRBGColorF(0.25F + rand.nextFloat() * 0.25F, 0.0F, 1.0F);
                Minecraft.getMinecraft().effectRenderer.addEffect(particle);
            }
        }
    }
}
