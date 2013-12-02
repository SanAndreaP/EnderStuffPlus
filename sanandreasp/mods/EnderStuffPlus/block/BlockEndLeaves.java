package sanandreasp.mods.EnderStuffPlus.block;

import java.util.List;
import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndLeaves extends BlockLeaves {
	
	@SideOnly(Side.CLIENT)
    private Icon[] iconArray;

	public BlockEndLeaves(int par1) {
		super(par1);
		this.graphicsLevel = false;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return 0xFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int par1) {
        return 0xFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
    	return 0xFFFFFF;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
    	this.graphicsLevel = Block.leaves.graphicsLevel;
        return !this.graphicsLevel;
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
    	this.graphicsLevel = Block.leaves.graphicsLevel;
    	return super.shouldSideBeRendered(par1iBlockAccess, par2, par3, par4, par5);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
    	super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
    	
    	Block blockBelow = Block.blocksList[par1World.getBlockId(par2, par3-1, par4)];
    	if( (blockBelow == null || blockBelow.isAirBlock(par1World, par2, par3-1, par4)) && par5Random.nextInt(2) == 1 )
        {
            double d0 = (double)((float)par2 + par5Random.nextFloat());
            double d1 = (double)par3 - 0.05D;
            double d2 = (double)((float)par4 + par5Random.nextFloat());
            EntityReddustFX fx = new EntityReddustFX(par1World, d0, d1, d2, 66F / 255F, 0.0F, 88F / 255F);
            fx.motionY = -0.2D;
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }
    
    @Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
        if( !par1World.isRemote )
        {
            int j1 = 20;

            if( par7 > 0 )
            {
                j1 -= 2 << par7;

                if( j1 < 10 )
                {
                    j1 = 10;
                }
            }

            if( par1World.rand.nextInt(j1) == 0 )
            {
                this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(ESPModRegistry.sapEndTree.blockID, 1, 0));
            }
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
    	par3List.add(new ItemStack(par1, 1, 0));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2) {
    	this.graphicsLevel = Block.leaves.graphicsLevel;
        return this.iconArray[this.graphicsLevel ? 0 : 1];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
    	iconArray = new Icon[2];
    	String nA = (!ConfigRegistry.useAnimations ? "_NA" : "");
    	this.iconArray[0] = par1IconRegister.registerIcon("enderstuffp:enderLeaves" + nA);
    	this.iconArray[1] = par1IconRegister.registerIcon("enderstuffp:enderLeaves_opaque" + nA);
    }
    
    @Override
    public boolean canDragonDestroy(World world, int x, int y, int z) {
    	return false;
    }
}
