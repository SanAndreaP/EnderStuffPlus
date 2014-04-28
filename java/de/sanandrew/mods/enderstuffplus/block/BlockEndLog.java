package de.sanandrew.mods.enderstuffplus.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.registry.ConfigRegistry;
import de.sanandrew.mods.enderstuffplus.registry.ModBlockRegistry;

public class BlockEndLog
    extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon topIcon;

    public BlockEndLog() {
        super(Material.wood);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldMeta) {
        byte range = 4;
        int checkRange = range + 1;

        if( world.checkChunksExist(x - checkRange, y - checkRange, z - checkRange, x + checkRange, y + checkRange,
                                   z + checkRange) ) {
            for( int offsetX = -range; offsetX <= range; ++offsetX ) {
                for( int offsetY = -range; offsetY <= range; ++offsetY ) {
                    for( int offsetZ = -range; offsetZ <= range; ++offsetZ ) {
                        Block block = world.getBlock(x + offsetX, y + offsetY, z + offsetZ);

                        if( block != null ) {
                            block.beginLeavesDecay(world, x + offsetX, y + offsetY, z + offsetZ);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess blockAccess, int x, int y, int z, Entity entity) {
        return !(entity instanceof EntityDragon);
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess blockAccess, int x, int y, int z) {
        return true;
    }

    @Override
    protected ItemStack createStackedBlock(int meta) {
        return new ItemStack(this, 1, meta & 3);
    }

    @Override
    public int damageDropped(int meta) {
        return meta & 3;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        int stripMeta = meta & 12;

        if( stripMeta == 0 && (side == 1 || side == 0) ) {
            return this.topIcon;
        } else if( stripMeta == 4 && (side == 5 || side == 4) ) {
            return this.topIcon;
        } else if( stripMeta == 8 && (side == 2 || side == 3) ) {
            return this.topIcon;
        }

        return this.blockIcon;
    }

    @Override
    public int getRenderType() {
        return 31;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubBlocks(Item item, CreativeTabs tab, List stacks) {
        stacks.add(new ItemStack(item, 1, 0));
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortuneLevel) {
        return Item.getItemFromBlock(ModBlockRegistry.enderLog);
    }

    @Override
    public boolean isWood(IBlockAccess blockAccess, int x, int y, int z) {
        return true;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset,
                             int meta) {
        int stripMeta = meta & 3;
        byte rotation = 0;

        switch( side ) {
            case 0 :
                // fallthrough;
            case 1 :
                rotation = 0;
                break;
            case 2 :
                // fallthrough;
            case 3 :
                rotation = 8;
                break;
            case 4 :
                // fallthrough;
            case 5 :
                rotation = 4;
                break;
        }

        return stripMeta | rotation;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        String nA = (!ConfigRegistry.useAnimations ? "_NA" : "");
        this.topIcon = iconRegister.registerIcon("enderstuffp:enderLog_top" + nA);
        this.blockIcon = iconRegister.registerIcon("enderstuffp:enderLog_side" + nA);
    }
}
