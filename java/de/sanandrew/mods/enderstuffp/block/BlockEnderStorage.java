/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.client.IGlowBlockOverlay;
import de.sanandrew.core.manpack.util.client.RenderBlockGlowOverlay;
import de.sanandrew.mods.enderstuffp.util.EnumEnderOres;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
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

import java.util.List;

public class BlockEnderStorage
        extends Block
        implements IGlowBlockOverlay
{
    @SideOnly(Side.CLIENT)
    private IIcon[] sideIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] topIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] sideGlowIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] topGlowIcons;

    public BlockEnderStorage() {
        super(Material.iron);
    }

    @Override
    public MapColor getMapColor(int meta) {
        return EnumEnderOres.getType(meta).blockMapColor;
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess blockAccess, int x, int y, int z, Entity entity) {
        return !(entity instanceof EntityDragon);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side < 2) ? this.topIcons[meta] : this.sideIcons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getOverlayInvTexture(int side, int meta) {
        return (side < 2) ? this.topGlowIcons[meta] : this.sideGlowIcons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getOverlayTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return this.getOverlayInvTexture(side, blockAccess.getBlockMetadata(x, y, z));
    }

    @Override
    public int getRenderType() {
        return RenderBlockGlowOverlay.renderID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(Item item, CreativeTabs tab, List stacks) {
        for( int i = 0; i < EnumEnderOres.COUNT; i++ ) {
            stacks.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public boolean isBeaconBase(IBlockAccess blockAccess, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if( world.getBlockMetadata(x, y, z) != 0 ) {
            return;
        }

        if( world.getIndirectPowerOutput(x, y, z + 1, 3) || (world.getBlock(x, y, z + 1) == Blocks.redstone_wire
            && world.getBlockMetadata(x, y, z + 1) > 0) )
        {
            if( world.isAirBlock(x, y, z - 1) ) {
                world.setBlock(x, y, z, Blocks.air, 0, 2);
                world.setBlock(x, y, z - 1, this, 0, 2);
            }
        } else if( world.getIndirectPowerOutput(x, y, z - 1, 2) || (world.getBlock(x, y, z - 1) == Blocks.redstone_wire
                   && world.getBlockMetadata(x, y, z - 1) > 0) )
        {
            if( world.isAirBlock(x, y, z + 1) ) {
                world.setBlock(x, y, z, Blocks.air, 0, 2);
                world.setBlock(x, y, z + 1, this, 0, 2);
            }
        } else if( world.getIndirectPowerOutput(x + 1, y, z, 5) || (world.getBlock(x + 1, y, z) == Blocks.redstone_wire
                   && world.getBlockMetadata(x + 1, y, z) > 0) )
        {
            if( world.isAirBlock(x - 1, y, z) ) {
                world.setBlock(x, y, z, Blocks.air, 0, 2);
                world.setBlock(x - 1, y, z, this, 0, 2);
            }
        } else if( world.getIndirectPowerOutput(x - 1, y, z, 4) || (world.getBlock(x - 1, y, z) == Blocks.redstone_wire
                   && world.getBlockMetadata(x - 1, y, z) > 0) )
        {
            if( world.isAirBlock(x + 1, y, z) ) {
                world.setBlock(x, y, z, Blocks.air, 0, 2);
                world.setBlock(x + 1, y, z, this, 0, 2);
            }
        } else if( world.getIndirectPowerOutput(x, y - 1, z, 0) ) {
            if( world.isAirBlock(x, y + 1, z) ) {
                world.setBlock(x, y, z, Blocks.air, 0, 2);
                world.setBlock(x, y + 1, z, this, 0, 2);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.sideIcons = new IIcon[EnumEnderOres.COUNT];
        this.topIcons = new IIcon[this.sideIcons.length];
        this.sideGlowIcons = new IIcon[this.sideIcons.length];
        this.topGlowIcons = new IIcon[this.sideIcons.length];

        for( int i = 0; i < this.sideIcons.length; i++ ) {
            EnumEnderOres oreType = EnumEnderOres.getType(i);
            this.sideIcons[i] = iconRegister.registerIcon(oreType.blockTextureSide);
            this.sideGlowIcons[i] = iconRegister.registerIcon(oreType.blockTextureSideGlow);
            this.topIcons[i] = iconRegister.registerIcon(oreType.blockTextureTop);
            this.topGlowIcons[i] = iconRegister.registerIcon(oreType.blockTextureTopGlow);
        }

        this.blockIcon = this.sideIcons[0];
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }
}
