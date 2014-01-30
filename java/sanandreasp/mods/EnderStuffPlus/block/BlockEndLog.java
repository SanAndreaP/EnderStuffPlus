package sanandreasp.mods.EnderStuffPlus.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockEndLog extends Block
{
    @SideOnly(Side.CLIENT)
    private Icon tree_top;

	public BlockEndLog(int id) {
		super(id, Material.wood);
	}

	@Override
    public int getRenderType() {
        return 31;
    }

	@Override
    public int quantityDropped(Random rand) {
        return 1;
    }

	@Override
    public int idDropped(int meta, Random rand, int fortuneLvl) {
        return ESPModRegistry.enderLog.blockID;
    }

	@Override
    public void breakBlock(World world, int x, int y, int z, int oldID, int oldMeta) {
        byte range = 4;
        int checkRng = range + 1;

        if( world.checkChunksExist(x - checkRng, y - checkRng, z - checkRng, x + checkRng, y + checkRng, z + checkRng) ) {
            for( int offX = -range; offX <= range; ++offX ) {
                for( int offY = -range; offY <= range; ++offY ) {
                    for( int offZ = -range; offZ <= range; ++offZ ) {
                        int bID = world.getBlockId(x + offX, y + offY, z + offZ);

                        if( Block.blocksList[bID] != null ) {
                            Block.blocksList[bID].beginLeavesDecay(world, x + offX, y + offY, z + offZ);
                        }
                    }
                }
            }
        }
    }

	@Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset, int meta) {
        int stripMeta = meta & 3;
        byte rotation = 0;

        switch (side) {
            case 0: // FALL-THROUGH
            case 1:
                rotation = 0;
                break;
            case 2: // FALL-THROUGH
            case 3:
                rotation = 8;
                break;
            case 4: // FALL-THROUGH
            case 5:
                rotation = 4;
                break;
        }

        return stripMeta | rotation;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        int stripMeta = meta & 12;
        
        if( stripMeta == 0 && (side == 1 || side == 0) ) {
        	return this.tree_top;
        } else if( stripMeta == 4 && (side == 5 || side == 4) ) {
        	return this.tree_top;
        } else if( stripMeta == 8 && (side == 2 || side == 3) ) {
        	return this.tree_top;
        }
        
        return this.blockIcon;
    }

	@Override
    public int damageDropped(int meta) {
        return meta & 3;
    }

    public static int limitToValidMetadata(int meta) {
        return meta & 3;
    }

	@Override
    @SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubBlocks(int id, CreativeTabs tab, List stacks) {
        stacks.add(new ItemStack(id, 1, 0));
    }

	@Override
    protected ItemStack createStackedBlock(int meta) {
        return new ItemStack(this.blockID, 1, limitToValidMetadata(meta));
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister regIcon) {
    	String nA = (!ConfigRegistry.useAnimations ? "_NA" : "");
        this.tree_top = regIcon.registerIcon("enderstuffp:enderLog_top" + nA);
        this.blockIcon = regIcon.registerIcon("enderstuffp:enderLog_side" + nA);
    }

    @Override
    public boolean canSustainLeaves(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean isWood(World world, int x, int y, int z) {
        return true;
    }
    
    @Override
    public boolean canDragonDestroy(World world, int x, int y, int z) {
    	return false;
    }
}
