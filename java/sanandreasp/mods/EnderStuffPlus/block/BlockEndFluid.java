package sanandreasp.mods.EnderStuffPlus.block;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEndFluid
    extends BlockFluidClassic
{
    @SideOnly(Side.CLIENT)
    private Icon flowingIcon;
    @SideOnly(Side.CLIENT)
    private Icon stillIcon;

    public BlockEndFluid(int id, Fluid fluid, Material material) {
        super(id, fluid, material);
        this.setCreativeTab(CreativeTabs.tabMisc);
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
    public Icon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? this.stillIcon : this.flowingIcon;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        entity.attackEntityFrom(ESPModRegistry.endAcid, 2);
        super.onEntityCollidedWithBlock(world, x, y, z, entity);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister iconRegister) {
        this.stillIcon = iconRegister.registerIcon("enderstuffp:water_still");
        this.flowingIcon = iconRegister.registerIcon("enderstuffp:water_flow");
    }

}
