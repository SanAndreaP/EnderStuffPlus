package sanandreasp.mods.EnderStuffPlus.block;

import java.util.List;

import sanandreasp.core.manpack.helpers.IGlowBlockOverlay;
import sanandreasp.core.manpack.helpers.client.RenderBlockGlowOverlay;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOreStorage;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEndStorage
    extends BlockOreStorage
    implements IGlowBlockOverlay
{
    @SideOnly(Side.CLIENT)
    private Icon[] baseIcons = new Icon[2];
    @SideOnly(Side.CLIENT)
    private Icon[] baseTopIcons = new Icon[2];
    @SideOnly(Side.CLIENT)
    private Icon[] glowIcons = new Icon[2];
    @SideOnly(Side.CLIENT)
    private Icon[] glowTopIcons = new Icon[2];

    public BlockEndStorage(int id) {
        super(id);
    }

    @Override
    public boolean canDragonDestroy(World world, int x, int y, int z) {
        return false;
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return (side < 2) ? this.baseTopIcons[Math.min(this.baseTopIcons.length, meta)]
                          : this.baseIcons[Math.min(this.baseIcons.length, meta)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getOverlayInvTexture(int side, int meta) {
        return (side < 2) ? this.glowTopIcons[Math.min(this.glowTopIcons.length, meta)]
                          : this.glowIcons[Math.min(this.glowIcons.length, meta)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getOverlayTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return this.getOverlayInvTexture(side, blockAccess.getBlockMetadata(x, y, z));
    }

    @Override
    public int getRenderType() {
        return RenderBlockGlowOverlay.renderID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int id, CreativeTabs tab, List stacks) {
        stacks.add(new ItemStack(this.blockID, 1, 0));
        stacks.add(new ItemStack(this.blockID, 1, 1));
    }

    @Override
    public boolean isBeaconBase(World world, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int side) {
        if( world.getBlockMetadata(x, y, z) != 0 ) {
            return;
        }

        if( world.getIndirectPowerOutput(x, y, z + 1, 3)
                || (Block.blocksList[world.getBlockId(x, y, z + 1)] == Block.redstoneWire
                    && world.getBlockMetadata(x, y, z + 1) > 0) ) {
            if( world.isAirBlock(x, y, z - 1) ) {
                world.setBlock(x, y, z, 0, 0, 2);
                world.setBlock(x, y, z - 1, this.blockID, 0, 2);
            }
        } else if( world.getIndirectPowerOutput(x, y, z - 1, 2)
                   || (Block.blocksList[world.getBlockId(x, y, z - 1)] == Block.redstoneWire
                       && world.getBlockMetadata(x, y, z - 1) > 0) ) {
            if( world.isAirBlock(x, y, z + 1) ) {
                world.setBlock(x, y, z, 0, 0, 2);
                world.setBlock(x, y, z + 1, this.blockID, 0, 2);
            }
        } else if( world.getIndirectPowerOutput(x + 1, y, z, 5)
                   || (Block.blocksList[world.getBlockId(x + 1, y, z)] == Block.redstoneWire
                       && world.getBlockMetadata(x + 1, y, z) > 0) ) {
            if( world.isAirBlock(x - 1, y, z) ) {
                world.setBlock(x, y, z, 0, 0, 2);
                world.setBlock(x - 1, y, z, this.blockID, 0, 2);
            }
        } else if( world.getIndirectPowerOutput(x - 1, y, z, 4)
                   || (Block.blocksList[world.getBlockId(x - 1, y, z)] == Block.redstoneWire
                       && world.getBlockMetadata(x - 1, y, z) > 0) ) {
            if( world.isAirBlock(x + 1, y, z) ) {
                world.setBlock(x, y, z, 0, 0, 2);
                world.setBlock(x + 1, y, z, this.blockID, 0, 2);
            }
        } else if( world.getIndirectPowerOutput(x, y - 1, z, 0) ) {
            if( world.isAirBlock(x, y + 1, z) ) {
                world.setBlock(x, y, z, 0, 0, 2);
                world.setBlock(x, y + 1, z, this.blockID, 0, 2);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
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
