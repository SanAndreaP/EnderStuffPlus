package de.sanandrew.mods.enderstuffplus.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class BlockEnderDoor
    extends Block
{
    private static final String[] doorIconNames = new String[] { "doorNiob_lower", "doorNiob_middle", "doorNiob_upper" };
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockEnderDoor(Material material) {
        super(material);
    }

    @Override
    public BlockEnderDoor disableStats() {
        return (BlockEnderDoor) super.disableStats();
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        if( y >= 254 ) {
            return false;
        } else {
            return super.canPlaceBlockAt(world, x, y, z) && super.canPlaceBlockAt(world, x, y + 1, z)
                   && super.canPlaceBlockAt(world, x, y + 2, z);
        }
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.collisionRayTrace(world, x, y, z, startVec, endVec);
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess blockAccess, int x, int y, int z) {
        int fullMeta = this.getFullMetadata(blockAccess, x, y, z);

        return (fullMeta & 4) != 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if( (side != 1) && (side != 0) ) {
            int fullMeta = this.getFullMetadata(blockAccess, x, y, z);
            int stripMeta = fullMeta & 3;
            boolean isOpen = (fullMeta & 4) != 0;
            boolean isTextureFlipped = false;
            boolean isTopHalf = (fullMeta & 32) != 0;
            boolean isBottomHalf = (fullMeta & 8) == 0;

            if( isOpen ) {
                if( (stripMeta == 0) && (side == 2) ) {
                    isTextureFlipped = !isTextureFlipped;
                } else if( (stripMeta == 1) && (side == 5) ) {
                    isTextureFlipped = !isTextureFlipped;
                } else if( (stripMeta == 2) && (side == 3) ) {
                    isTextureFlipped = !isTextureFlipped;
                } else if( (stripMeta == 3) && (side == 4) ) {
                    isTextureFlipped = !isTextureFlipped;
                }
            } else {
                if( (stripMeta == 0) && (side == 5) ) {
                    isTextureFlipped = !isTextureFlipped;
                } else if( (stripMeta == 1) && (side == 3) ) {
                    isTextureFlipped = !isTextureFlipped;
                } else if( (stripMeta == 2) && (side == 4) ) {
                    isTextureFlipped = !isTextureFlipped;
                } else if( (stripMeta == 3) && (side == 2) ) {
                    isTextureFlipped = !isTextureFlipped;
                }

                if( (fullMeta & 16) != 0 ) {
                    isTextureFlipped = !isTextureFlipped;
                }
            }

            return this.icons[(isTextureFlipped ? doorIconNames.length : 0) + (isBottomHalf ? 0 : isTopHalf ? 2 : 1)];
        } else {
            return this.icons[0];
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    public int getDoorOrientation(IBlockAccess world, int x, int y, int z) {
        return this.getFullMetadata(world, x, y, z) & 3;
    }

    public int getFullMetadata(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        int bottomHalf;
        int topHalf;
        boolean isTop = world.getBlock(x, y + 1, z) != this;
        boolean isBottom = world.getBlock(x, y - 1, z) != this;

        if( isTop ) {
            bottomHalf = world.getBlockMetadata(x, y - 2, z);
            topHalf = meta;
        } else if( isBottom ) {
            bottomHalf = meta;
            topHalf = world.getBlockMetadata(x, y + 2, z);
        } else {
            bottomHalf = world.getBlockMetadata(x, y - 1, z);
            topHalf = world.getBlockMetadata(x, y + 1, z);
        }

        boolean isShifted = (topHalf & 1) != 0;

        return (bottomHalf & 7) | (!isBottom ? 8 : 0) | (isShifted ? 16 : 0) | (isTop ? 32 : 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.icons[0];
    }

    @Override
    public int getMobilityFlag() {
        return 1;
    }

    @Override
    public int getRenderType() {
        return 7;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortuneLevel) {
        return ((meta & 8) != 0) ? Item.getItemById(0) : ModItemRegistry.itemNiobDoor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z) {
        return ModItemRegistry.itemNiobDoor;
    }

    public boolean isDoorOpen(IBlockAccess blockAccess, int x, int y, int z) {
        return (this.getFullMetadata(blockAccess, x, y, z) & 4) != 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset,
                                    float yOffset, float zOffset) {
        return false;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {}

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
        if( player.capabilities.isCreativeMode && (meta & 8) != 0 && world.getBlock(x, y - 1, z) == this ) {
            if( (this.getFullMetadata(world, x, y, z) & 32) != 0 ) {
                world.setBlockToAir(x, y - 2, z);
            } else {
                world.setBlockToAir(x, y - 1, z);
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        int meta = world.getBlockMetadata(x, y, z);

        if( (meta & 8) == 0 ) {
            boolean shouldDestroy = false;

            if( world.getBlock(x, y + 1, z) != this || world.getBlock(x, y + 2, z) != this ) {
                world.setBlockToAir(x, y, z);
                shouldDestroy = true;
            }

            if( shouldDestroy ) {
                if( !world.isRemote ) {
                    this.dropBlockAsItem(world, x, y, z, meta, 0);
                }
            } else {
                boolean isPowered = world.isBlockIndirectlyGettingPowered(x, y, z)
                                    || world.isBlockIndirectlyGettingPowered(x, y + 1, z)
                                    || world.isBlockIndirectlyGettingPowered(x, y + 2, z);

                if( (isPowered || block != null && block.canProvidePower()) && block != this ) {
                    this.onPoweredBlockChange(world, x, y, z, isPowered);
                }
            }
        } else {
            if( world.getBlock(x, y - 1, z) != this ) {
                world.setBlockToAir(x, y, z);
            }

            if( world.getBlock(x, y - 2, z) != this && world.getBlock(x, y + 1, z) != this ) {
                world.setBlockToAir(x, y, z);
            }

            if( block != null && block != this ) {
                if( world.getBlock(x, y + 1, z) != this ) {
                    this.onNeighborBlockChange(world, x, y - 2, z, block);
                } else {
                    this.onNeighborBlockChange(world, x, y - 1, z, block);
                }
            }
        }
    }

    public void onPoweredBlockChange(World world, int x, int y, int z, boolean isPowered) {
        int fullMeta = this.getFullMetadata(world, x, y, z);
        boolean isOpen = (fullMeta & 4) != 0;

        if( isOpen != isPowered ) {
            int stripMeta = fullMeta & 7;
            stripMeta ^= 4;

            if( (fullMeta & 8) == 0 ) {
                world.setBlockMetadataWithNotify(x, y, z, stripMeta, 2);
                world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            } else if( (fullMeta & 32) == 0 ) {
                world.setBlockMetadataWithNotify(x, y - 2, z, stripMeta, 2);
                world.markBlockRangeForRenderUpdate(x, y - 2, z, x, y, z);
            } else {
                world.setBlockMetadataWithNotify(x, y - 1, z, stripMeta, 2);
                world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
            }

            world.playAuxSFXAtEntity((EntityPlayer) null, 1003, x, y, z, 0);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister regIcon) {
        this.icons = new IIcon[doorIconNames.length * 2];

        for( int i = 0; i < doorIconNames.length; ++i ) {
            this.icons[i] = regIcon.registerIcon("enderstuffp:" + doorIconNames[i]);
            this.icons[i + doorIconNames.length] = new IconFlipped(this.icons[i], true, false);
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        this.setDoorRotation(this.getFullMetadata(blockAccess, x, y, z));
    }

    private void setDoorRotation(int meta) {
        int stripMeta = meta & 3;
        float thickness = 0.1875F;
        boolean isOpen = (meta & 4) != 0;
        boolean isShifted = (meta & 16) != 0;

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        if( stripMeta == 0 ) {
            if( isOpen ) {
                if( !isShifted ) {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, thickness);
                } else {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - thickness, 1.0F, 1.0F, 1.0F);
                }
            } else {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, thickness, 1.0F, 1.0F);
            }
        } else if( stripMeta == 1 ) {
            if( isOpen ) {
                if( !isShifted ) {
                    this.setBlockBounds(1.0F - thickness, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                } else {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, thickness, 1.0F, 1.0F);
                }
            } else {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, thickness);
            }
        } else if( stripMeta == 2 ) {
            if( isOpen ) {
                if( !isShifted ) {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - thickness, 1.0F, 1.0F, 1.0F);
                } else {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, thickness);
                }
            } else {
                this.setBlockBounds(1.0F - thickness, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        } else if( stripMeta == 3 ) {
            if( isOpen ) {
                if( !isShifted ) {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, thickness, 1.0F, 1.0F);
                } else {
                    this.setBlockBounds(1.0F - thickness, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
            } else {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - thickness, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
