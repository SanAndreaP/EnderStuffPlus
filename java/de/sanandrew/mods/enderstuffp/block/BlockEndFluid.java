package de.sanandrew.mods.enderstuffp.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockEndFluid
    extends BlockFluidClassic
{
    @SideOnly(Side.CLIENT)
    private IIcon flowingIcon;
    @SideOnly(Side.CLIENT)
    private IIcon stillIcon;

    public BlockEndFluid(Fluid fluid, Material material) {
        super(fluid, material);
    }

    @Override
    public boolean canDisplace(IBlockAccess blockAccess, int x, int y, int z) {
        return super.canDisplace(blockAccess, x, y, z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
        return super.displaceIfPossible(world, x, y, z);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? this.stillIcon : this.flowingIcon;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        entity.attackEntityFrom(EnderStuffPlus.endAcid, 2);
        super.onEntityCollidedWithBlock(world, x, y, z, entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.stillIcon = iconRegister.registerIcon("enderstuffp:water_still");
        this.flowingIcon = iconRegister.registerIcon("enderstuffp:water_flow");
    }

}
