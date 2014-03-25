package sanandreasp.mods.EnderStuffPlus.client.particle;

import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityRayballFX
    extends EntityReddustFX
{
    public EntityRayballFX(World world, double x, double y, double z, float red, float green, float blue) {
        super(world, x, y, z, red, green, blue);
    }

    public EntityRayballFX(World par1World, double x, double y, double z, float scale, float red, float green, float blue) {
        super(par1World, x, y, z, scale, red, green, blue);
    }

    @Override
    public int getBrightnessForRender(float par1) {
        return 0xF00F0;
    }
}
