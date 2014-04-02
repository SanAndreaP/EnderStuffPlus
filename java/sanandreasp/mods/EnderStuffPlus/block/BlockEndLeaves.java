package sanandreasp.mods.EnderStuffPlus.block;

import java.util.List;
import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.client.particle.ParticleFXFuncCollection;
import sanandreasp.mods.EnderStuffPlus.registry.ModBlockRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEndLeaves
    extends BlockLeaves
{
    @SideOnly(Side.CLIENT)
    private Icon[] icons = new Icon[2];

    public BlockEndLeaves(int id) {
        super(id);
        this.graphicsLevel = false;
    }

    @Override
    public boolean canDragonDestroy(World world, int x, int y, int z) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        return 0xFFFFFF;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float velocity, int fortuneLevel) {
        if( !world.isRemote ) {
            int randomChance = 20;

            if( fortuneLevel > 0 ) {
                randomChance -= 2 << fortuneLevel;
                if( randomChance < 10 ) {
                    randomChance = 10;
                }
            }

            if( world.rand.nextInt(randomChance) == 0 ) {
                this.dropBlockAsItem_do(world, x, y, z, new ItemStack(ModBlockRegistry.sapEndTree.blockID, 1, 0));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return 0xFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        this.graphicsLevel = Block.leaves.graphicsLevel;

        return this.icons[this.graphicsLevel ? 0 : 1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return 0xFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubBlocks(int id, CreativeTabs creativeTab, List stacks) {
        stacks.add(new ItemStack(id, 1, 0));
    }

    @Override
    public boolean isOpaqueCube() {
        this.graphicsLevel = Block.leaves.graphicsLevel;

        return !this.graphicsLevel;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        super.randomDisplayTick(world, x, y, z, random);
        ParticleFXFuncCollection.spawnEndLeavesFX(world, x, y, z, random);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        String nA = (!ConfigRegistry.useAnimations ? "_NA" : "");
        this.icons[0] = iconRegister.registerIcon("enderstuffp:enderLeaves" + nA);
        this.icons[1] = iconRegister.registerIcon("enderstuffp:enderLeaves_opaque" + nA);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        this.graphicsLevel = Block.leaves.graphicsLevel;

        return super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }
}
