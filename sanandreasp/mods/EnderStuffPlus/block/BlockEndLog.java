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

public class BlockEndLog extends Block {
	
    @SideOnly(Side.CLIENT)
    private Icon tree_top;

	public BlockEndLog(int par1) {
		super(par1, Material.wood);
	}

	@Override
    public int getRenderType()
    {
        return 31;
    }

	@Override
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

	@Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return ESPModRegistry.enderLog.blockID;
    }

	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        byte b0 = 4;
        int j1 = b0 + 1;

        if( par1World.checkChunksExist(par2 - j1, par3 - j1, par4 - j1, par2 + j1, par3 + j1, par4 + j1) )
        {
            for( int k1 = -b0; k1 <= b0; ++k1 )
            {
                for( int l1 = -b0; l1 <= b0; ++l1 )
                {
                    for( int i2 = -b0; i2 <= b0; ++i2 )
                    {
                        int j2 = par1World.getBlockId(par2 + k1, par3 + l1, par4 + i2);

                        if( Block.blocksList[j2] != null )
                        {
                            Block.blocksList[j2].beginLeavesDecay(par1World, par2 + k1, par3 + l1, par4 + i2);
                        }
                    }
                }
            }
        }
    }

	@Override
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int j1 = par9 & 3;
        byte b0 = 0;

        switch (par5)
        {
            case 0:
            case 1:
                b0 = 0;
                break;
            case 2:
            case 3:
                b0 = 8;
                break;
            case 4:
            case 5:
                b0 = 4;
        }

        return j1 | b0;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        int k = par2 & 12;
        return k == 0 && (par1 == 1 || par1 == 0) ? this.tree_top : (k == 4 && (par1 == 5 || par1 == 4) ? this.tree_top : (k == 8 && (par1 == 2 || par1 == 3) ? this.tree_top : this.blockIcon));
    }

	@Override
    public int damageDropped(int par1)
    {
        return par1 & 3;
    }

    public static int limitToValidMetadata(int par0)
    {
        return par0 & 3;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
    }

	@Override
    protected ItemStack createStackedBlock(int par1) {
        return new ItemStack(this.blockID, 1, limitToValidMetadata(par1));
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
    	String nA = (!ConfigRegistry.useAnimations ? "_NA" : "");
        this.tree_top = par1IconRegister.registerIcon("enderstuffp:enderLog_top" + nA);
        this.blockIcon = par1IconRegister.registerIcon("enderstuffp:enderLog_side" + nA);
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
