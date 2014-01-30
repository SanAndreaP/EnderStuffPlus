package sanandreasp.mods.EnderStuffPlus.block;

import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnderDoor extends Block
{
    private static final String[] doorIconNames = new String[] {"doorNiob_lower", "doorNiob_middle", "doorNiob_upper"};
    
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

	public BlockEnderDoor(int id, Material material) {
		super(id, material);
		this.disableStats();
	}

	@Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        return this.iconArray[0];
    }

	@Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
        if( side != 1 && side != 0 ) {
            int fullMeta = this.getFullMetadata(world, x, y, z);
            int stripMeta = fullMeta & 3;
            boolean isOpen = (fullMeta & 4) != 0;
            boolean flipTex = false;
            boolean isTopHalf = (fullMeta & 32) != 0;
            boolean isBtmHalf = (fullMeta & 8) == 0;
            
            if( isOpen ) {
                if( stripMeta == 0 && side == 2 ) {
                    flipTex = !flipTex;
                } else if( stripMeta == 1 && side == 5 ) {
                    flipTex = !flipTex;
                } else if( stripMeta == 2 && side == 3 ) {
                    flipTex = !flipTex;
                } else if( stripMeta == 3 && side == 4 ) {
                    flipTex = !flipTex;
                }
            } else {
                if( stripMeta == 0 && side == 5 ) {
                    flipTex = !flipTex;
                } else if( stripMeta == 1 && side == 3 ) {
                    flipTex = !flipTex;
                } else if( stripMeta == 2 && side == 4 ) {
                    flipTex = !flipTex;
                } else if( stripMeta == 3 && side == 2 ) {
                    flipTex = !flipTex;
                }
                
                if( (fullMeta & 16) != 0 ) {
	                flipTex = !flipTex;
	            }
            }

            return this.iconArray[(flipTex ? doorIconNames.length : 0) + (isBtmHalf ? 0 : isTopHalf?2:1)];
        } else {
            return this.iconArray[0];
        }
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister regIcon) {
        this.iconArray = new Icon[doorIconNames.length * 2];

        for( int i = 0; i < doorIconNames.length; ++i ) {
            this.iconArray[i] = regIcon.registerIcon("enderstuffp:"+doorIconNames[i]);
            this.iconArray[i + doorIconNames.length] = new IconFlipped(this.iconArray[i], true, false);
        }
    }

	@Override
    public boolean isOpaqueCube() {
        return false;
    }

	@Override
    public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z) {
        int fullMeta = this.getFullMetadata(world, x, y, z);
        return (fullMeta & 4) != 0;
    }

	@Override
    public boolean renderAsNormalBlock() {
        return false;
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
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

	@Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        this.setDoorRotation(this.getFullMetadata(world, x, y, z));
    }

    public int getDoorOrientation(IBlockAccess world, int x, int y, int z) {
        return this.getFullMetadata(world, x, y, z) & 3;
    }

    public boolean isDoorOpen(IBlockAccess world, int x, int y, int z) {
        return (this.getFullMetadata(world, x, y, z) & 4) != 0;
    }

    private void setDoorRotation(int meta) {
        float thickness = 0.1875F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        int stripMeta = meta & 3;
        boolean isOpen = (meta & 4) != 0;
        boolean isShifted = (meta & 16) != 0;

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

	@Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
    	;
    }

	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
        return false;
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

            world.playAuxSFXAtEntity((EntityPlayer)null, 1003, x, y, z, 0);
        }
    }

	@Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
        int meta = world.getBlockMetadata(x, y, z);

        if( (meta & 8) == 0 ) {
            boolean destroy = false;

            if( Block.blocksList[world.getBlockId(x, y + 1, z)] != this
            	|| Block.blocksList[world.getBlockId(x, y + 2, z)] != this )
            {
                world.setBlockToAir(x, y, z);
                destroy = true;
            }

            if( !world.doesBlockHaveSolidTopSurface(x, y - 1, z) ) {
                world.setBlockToAir(x, y, z);
                destroy = true;

                if( Block.blocksList[world.getBlockId(x, y + 1, z)] == this ) {
                    world.setBlockToAir(x, y + 1, z);
                }
                if( Block.blocksList[world.getBlockId(x, y + 2, z)] == this ) {
                    world.setBlockToAir(x, y + 2, z);
                }
            }

            if( destroy ) {
                if( !world.isRemote ) {
                    this.dropBlockAsItem(world, x, y, z, meta, 0);
                }
            } else {
                boolean isPowered = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z) || world.isBlockIndirectlyGettingPowered(x, y + 2, z);

                if( (isPowered || id > 0 && Block.blocksList[id].canProvidePower()) && id != this.blockID ) {
                    this.onPoweredBlockChange(world, x, y, z, isPowered);
                }
            }
        } else {
            if( Block.blocksList[world.getBlockId(x, y - 1, z)] != this ) {
                world.setBlockToAir(x, y, z);
            }
            if( Block.blocksList[world.getBlockId(x, y - 2, z )] != this
            	&& Block.blocksList[world.getBlockId(x, y + 1, z)] != this )
            {
                world.setBlockToAir(x, y, z);
            }

            if( id > 0 && id != this.blockID ) {
            	if( Block.blocksList[world.getBlockId(x, y + 1, z)] != this ) {
            		this.onNeighborBlockChange(world, x, y - 2, z, id);
            	} else {
            		this.onNeighborBlockChange(world, x, y - 1, z, id);
            	}
            }
        }
    }

	@Override
    public int idDropped(int meta, Random rand, int fortuneLvl) {
        return (meta & 8) != 0 ? 0 : ESPModRegistry.itemNiobDoor.itemID;
    }

	@Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.collisionRayTrace(world, x, y, z, startVec, endVec);
    }

	@Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if( y >= 255 ) {
			return false;
		} else {
			return world.doesBlockHaveSolidTopSurface(x, y - 1, z) 
					&& super.canPlaceBlockAt(world, x, y, z) 
					&& super.canPlaceBlockAt(world, x, y + 1, z) 
					&& super.canPlaceBlockAt(world, x, y + 2, z);
		}
    }

	@Override
    public int getMobilityFlag() {
        return 1;
    }

    public int getFullMetadata(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        boolean isTop = Block.blocksList[world.getBlockId(x, y+1, z)] != this;
        boolean isBottom = Block.blocksList[world.getBlockId(x, y-1, z)] != this;
        int btmHalf;
        int topHalf;

        if( isTop ) {
            btmHalf = world.getBlockMetadata(x, y - 2, z);
            topHalf = meta;
        } else if( isBottom ) {
            btmHalf = meta;
            topHalf = world.getBlockMetadata(x, y + 2, z);
        } else {
            btmHalf = world.getBlockMetadata(x, y - 1, z);
            topHalf = world.getBlockMetadata(x, y + 1, z);
        }

        boolean isShifted = (topHalf & 1) != 0;
        return btmHalf & 7 | (!isBottom ? 8 : 0) | (isShifted ? 16 : 0) | (isTop ? 32 : 0);
    }

	@Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World world, int x, int y, int z) {
        return ESPModRegistry.itemNiobDoor.itemID;
    }

	@Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
        if( player.capabilities.isCreativeMode 
        	&& (meta & 8) != 0 
        	&& Block.blocksList[world.getBlockId(x, y - 1, z)] == this )
        {
        	if( (this.getFullMetadata(world, x, y, z) & 32) != 0 ) {
        		world.setBlockToAir(x, y - 2, z);
        	} else {
        		world.setBlockToAir(x, y - 1, z);
        	}
        }
    }
}
