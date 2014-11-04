package de.sanandrew.mods.enderstuffplus.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
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

import de.sanandrew.core.manpack.util.client.IGlowBlockOverlay;
import de.sanandrew.core.manpack.util.client.RenderBlockGlowOverlay;

public class BlockEndStorage
    extends BlockCompressed
    implements IGlowBlockOverlay
{
    @SideOnly(Side.CLIENT)
    private IIcon[] baseIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] baseTopIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] glowIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] glowTopIcons;

    public BlockEndStorage() {
        super(MapColor.purpleColor);
    }

    @Override
    public MapColor getMapColor(int meta) {
        return meta == 1 ? MapColor.pinkColor : MapColor.blueColor;
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess blockAccess, int x, int y, int z, Entity entity) {
        return !(entity instanceof EntityDragon);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side < 2) ? this.baseTopIcons[Math.min(this.baseTopIcons.length, meta)]
                          : this.baseIcons[Math.min(this.baseIcons.length, meta)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getOverlayInvTexture(int side, int meta) {
        return (side < 2) ? this.glowTopIcons[Math.min(this.glowTopIcons.length, meta)]
                          : this.glowIcons[Math.min(this.glowIcons.length, meta)];
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
        stacks.add(new ItemStack(this, 1, 0));
        stacks.add(new ItemStack(this, 1, 1));
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
        this.baseIcons = new IIcon[2];
        this.baseTopIcons = new IIcon[2];
        this.glowIcons = new IIcon[2];
        this.glowTopIcons = new IIcon[2];

        this.baseIcons[0] = iconRegister.registerIcon("enderstuffp:niobBlock");
        this.baseIcons[1] = iconRegister.registerIcon("enderstuffp:tantalBlock");
        this.glowIcons[0] = iconRegister.registerIcon("enderstuffp:niobBlock_glow");
        this.glowIcons[1] = iconRegister.registerIcon("enderstuffp:tantalBlock_glow");
        this.baseTopIcons[0] = iconRegister.registerIcon("enderstuffp:niobBlockTop");
        this.baseTopIcons[1] = iconRegister.registerIcon("enderstuffp:tantalBlockTop");
        this.glowTopIcons[0] = iconRegister.registerIcon("enderstuffp:niobBlockTop_glow");
        this.glowTopIcons[1] = iconRegister.registerIcon("enderstuffp:tantalBlockTop_glow");

        this.blockIcon = this.baseIcons[0];
    }
}
