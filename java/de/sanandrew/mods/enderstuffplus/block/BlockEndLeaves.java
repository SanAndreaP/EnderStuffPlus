package de.sanandrew.mods.enderstuffplus.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.client.particle.ParticleFXFuncCollection;
import de.sanandrew.mods.enderstuffplus.registry.ConfigRegistry;
import de.sanandrew.mods.enderstuffplus.registry.ModBlockRegistry;

public class BlockEndLeaves
    extends BlockLeaves
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons = new IIcon[2];

    public BlockEndLeaves() {
        super();
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess blockAccess, int x, int y, int z, Entity entity) {
        return !(entity instanceof EntityDragon);
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
                this.dropBlockAsItem(world, x, y, z, new ItemStack(ModBlockRegistry.sapEndTree, 1, 0));
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
    public IIcon getIcon(int side, int meta) {
        return this.icons[!Blocks.leaves.isOpaqueCube() ? 0 : 1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return 0xFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubBlocks(Item item, CreativeTabs creativeTab, List stacks) {
        stacks.add(new ItemStack(item, 1, 0));
    }

    @Override
    public boolean isOpaqueCube() {
        return Blocks.leaves.isOpaqueCube();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        super.randomDisplayTick(world, x, y, z, random);
        ParticleFXFuncCollection.spawnEndLeavesFX(world, x, y, z, random);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        String nA = (!ConfigRegistry.useAnimations ? "_NA" : "");
        this.icons[0] = iconRegister.registerIcon("enderstuffp:enderLeaves" + nA);
        this.icons[1] = iconRegister.registerIcon("enderstuffp:enderLeaves_opaque" + nA);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        this.field_150121_P = !Blocks.leaves.isOpaqueCube();

        return super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }

    @Override
    public String[] func_150125_e() {
        return new String[] {"end"};
    }
}
