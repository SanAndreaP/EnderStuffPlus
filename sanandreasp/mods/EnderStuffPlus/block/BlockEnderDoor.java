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
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnderDoor extends Block {
	
    private static final String[] doorIconNames = new String[] {"doorNiob_lower", "doorNiob_middle", "doorNiob_upper"};
    
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

	public BlockEnderDoor(int par1, Material par2Material) {
		super(par1, par2Material);
		this.disableStats();
	}

    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        return this.iconArray[0];
    }

    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if( par5 != 1 && par5 != 0 )
        {
            int i1 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
            int j1 = i1 & 3;
            boolean isOpen = (i1 & 4) != 0;
            boolean flipTex = false;
            boolean isTopHalf = (i1 & 32) != 0;
            boolean isBtmHalf = (i1 & 8) == 0;
            
            if( isOpen )
            {
                if( j1 == 0 && par5 == 2 )
                {
                    flipTex = !flipTex;
                }
                else if( j1 == 1 && par5 == 5 )
                {
                    flipTex = !flipTex;
                }
                else if( j1 == 2 && par5 == 3 )
                {
                    flipTex = !flipTex;
                }
                else if( j1 == 3 && par5 == 4 )
                {
                    flipTex = !flipTex;
                }
            }
            else
            {
                if( j1 == 0 && par5 == 5 )
                {
                    flipTex = !flipTex;
                }
                else if( j1 == 1 && par5 == 3 )
                {
                    flipTex = !flipTex;
                }
                else if( j1 == 2 && par5 == 4 )
                {
                    flipTex = !flipTex;
                }
                else if( j1 == 3 && par5 == 2 )
                {
                    flipTex = !flipTex;
                }
	            if( (i1 & 16) != 0 )
	            {
	                flipTex = !flipTex;
	            }
            }

            return this.iconArray[(flipTex?doorIconNames.length:0) + (isBtmHalf?0:isTopHalf?2:1)];
        }
        else
        {
            return this.iconArray[0];
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[doorIconNames.length * 2];

        for( int i = 0; i < doorIconNames.length; ++i )
        {
            this.iconArray[i] = par1IconRegister.registerIcon("enderstuffp:"+doorIconNames[i]);
            this.iconArray[i + doorIconNames.length] = new IconFlipped(this.iconArray[i], true, false);
        }
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
        return (l & 4) != 0;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return 7;
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.setDoorRotation(this.getFullMetadata(par1IBlockAccess, par2, par3, par4));
    }

    public int getDoorOrientation(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return this.getFullMetadata(par1IBlockAccess, par2, par3, par4) & 3;
    }

    public boolean isDoorOpen(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return (this.getFullMetadata(par1IBlockAccess, par2, par3, par4) & 4) != 0;
    }

    private void setDoorRotation(int par1)
    {
        float f = 0.1875F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        int j = par1 & 3;
        boolean flag = (par1 & 4) != 0;
        boolean flag1 = (par1 & 16) != 0;

        if( j == 0 )
        {
            if( flag )
            {
                if( !flag1 )
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                }
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
        }
        else if( j == 1 )
        {
            if( flag )
            {
                if( !flag1 )
                {
                    this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
                }
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }
        }
        else if( j == 2 )
        {
            if( flag )
            {
                if( !flag1 )
                {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                }
            }
            else
            {
                this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
        else if( j == 3 )
        {
            if( flag )
            {
                if( !flag1 )
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {}

    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        return false; //Allow items to interact with the door
    }

    public void onPoweredBlockChange(World par1World, int par2, int par3, int par4, boolean par5)
    {
        int l = this.getFullMetadata(par1World, par2, par3, par4);
        boolean flag1 = (l & 4) != 0;

        if( flag1 != par5 )
        {
            int i1 = l & 7;
            i1 ^= 4;

            if( (l & 8) == 0 )
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, i1, 2);
                par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
            }
            else if( (l & 32) == 0 )
            {
                par1World.setBlockMetadataWithNotify(par2, par3 - 2, par4, i1, 2);
                par1World.markBlockRangeForRenderUpdate(par2, par3 - 2, par4, par2, par3, par4);
            }
            else
            {
            	par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, i1, 2);
            	par1World.markBlockRangeForRenderUpdate(par2, par3 - 1, par4, par2, par3, par4);
            }

            par1World.playAuxSFXAtEntity((EntityPlayer)null, 1003, par2, par3, par4, 0);
        }
    }

    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        int i1 = par1World.getBlockMetadata(par2, par3, par4);

        if( (i1 & 8) == 0 )
        {
            boolean flag = false;

            if( par1World.getBlockId(par2, par3 + 1, par4) != this.blockID || par1World.getBlockId(par2, par3 + 2, par4) != this.blockID )
            {
                par1World.setBlockToAir(par2, par3, par4);
                flag = true;
            }

            if( !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) )
            {
                par1World.setBlockToAir(par2, par3, par4);
                flag = true;

                if( par1World.getBlockId(par2, par3 + 1, par4) == this.blockID )
                {
                    par1World.setBlockToAir(par2, par3 + 1, par4);
                }
                if( par1World.getBlockId(par2, par3 + 2, par4) == this.blockID )
                {
                    par1World.setBlockToAir(par2, par3 + 2, par4);
                }
            }

            if( flag )
            {
                if( !par1World.isRemote )
                {
                    this.dropBlockAsItem(par1World, par2, par3, par4, i1, 0);
                }
            }
            else
            {
                boolean flag1 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 2, par4);

                if( (flag1 || par5 > 0 && Block.blocksList[par5].canProvidePower()) && par5 != this.blockID )
                {
                    this.onPoweredBlockChange(par1World, par2, par3, par4, flag1);
                }
            }
        }
        else
        {
            if( par1World.getBlockId(par2, par3 - 1, par4) != this.blockID )
            {
                par1World.setBlockToAir(par2, par3, par4);
            }
            if( par1World.getBlockId(par2, par3 - 2, par4 ) != this.blockID
            		&& par1World.getBlockId(par2, par3 + 1, par4) != this.blockID)
            {
                par1World.setBlockToAir(par2, par3, par4);
            }

            if( par5 > 0 && par5 != this.blockID )
            {
            	if( par1World.getBlockId(par2, par3 + 1, par4) != this.blockID )
            		this.onNeighborBlockChange(par1World, par2, par3 - 2, par4, par5);
            	else
            		this.onNeighborBlockChange(par1World, par2, par3 - 1, par4, par5);
            }
        }
    }

    public int idDropped(int par1, Random par2Random, int par3)
    {
        return (par1 & 8) != 0 ? 0 : ESPModRegistry.itemNiobDoor.itemID;
    }

    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
    }

    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par3 >= 255 ? false : par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && super.canPlaceBlockAt(par1World, par2, par3, par4) && super.canPlaceBlockAt(par1World, par2, par3 + 1, par4) && super.canPlaceBlockAt(par1World, par2, par3 + 2, par4);
    }

    public int getMobilityFlag()
    {
        return 1;
    }

    public int getFullMetadata(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        boolean isTop = par1IBlockAccess.getBlockId(par2, par3+1, par4) != this.blockID;
        boolean isBottom = par1IBlockAccess.getBlockId(par2, par3-1, par4) != this.blockID;
        int btmHalf;
        int topHalf;

        if( isTop ) {
            btmHalf = par1IBlockAccess.getBlockMetadata(par2, par3 - 2, par4);
            topHalf = l;
        } else if( isBottom ) {
            btmHalf = l;
            topHalf = par1IBlockAccess.getBlockMetadata(par2, par3 + 2, par4);
        } else {
            btmHalf = par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4);
            topHalf = par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4);
        }

        boolean flag1 = (topHalf & 1) != 0;
        return btmHalf & 7 | (!isBottom ? 8 : 0) | (flag1 ? 16 : 0) | (isTop ? 32 : 0);
    }

    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return ESPModRegistry.itemNiobDoor.itemID;
    }

    public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
    {
        if( par6EntityPlayer.capabilities.isCreativeMode && (par5 & 8) != 0 && par1World.getBlockId(par2, par3 - 1, par4) == this.blockID )
        {
        	if( (this.getFullMetadata(par1World, par2, par3, par4) & 32) != 0 )
        		par1World.setBlockToAir(par2, par3 - 2, par4);
        	else
        		par1World.setBlockToAir(par2, par3 - 1, par4);
        }
    }
}
