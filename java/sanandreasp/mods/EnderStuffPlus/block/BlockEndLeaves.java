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
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndLeaves extends BlockLeaves
{
	@SideOnly(Side.CLIENT)
    private Icon[] iconArray;

	public BlockEndLeaves(int id) {
		super(id);
		this.graphicsLevel = false;
	}

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return 0xFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return 0xFFFFFF;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
    	return 0xFFFFFF;
    }
    
    @Override
    public boolean isOpaqueCube() {
    	this.graphicsLevel = Block.leaves.graphicsLevel;
        return !this.graphicsLevel;
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
    	this.graphicsLevel = Block.leaves.graphicsLevel;
    	return super.shouldSideBeRendered(world, x, y, z, side);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
    	//TODO: outsource this into the ClientProxy!!!
    	
    	super.randomDisplayTick(world, x, y, z, rand);
    	
    	Block blockBelow = Block.blocksList[world.getBlockId(x, y-1, z)];
    	if( (blockBelow == null || blockBelow.isAirBlock(world, x, y-1, z)) && rand.nextInt(2) == 1 ) {
            double partX = (double)((float)x + rand.nextFloat());
            double partY = (double)y - 0.05D;
            double partZ = (double)((float)z + rand.nextFloat());
            EntityReddustFX fx = new EntityReddustFX(world, partX, partY, partZ, 66F / 255F, 0.0F, 88F / 255F);
            fx.motionY = -0.2D;
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }
    
    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float velocity, int fortuneLvl) {
        if( !world.isRemote ) {
            int randChance = 20;

            if( fortuneLvl > 0 ) {
                randChance -= 2 << fortuneLvl;

                if( randChance < 10 ) {
                    randChance = 10;
                }
            }

            if( world.rand.nextInt(randChance) == 0 ) {
                this.dropBlockAsItem_do(world, x, y, z, new ItemStack(ESPModRegistry.sapEndTree.blockID, 1, 0));
            }
        }
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubBlocks(int id, CreativeTabs tab, List stacks) {
    	stacks.add(new ItemStack(id, 1, 0));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
    	this.graphicsLevel = Block.leaves.graphicsLevel;
        return this.iconArray[this.graphicsLevel ? 0 : 1];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister regIcon) {
    	iconArray = new Icon[2];
    	String nA = (!ConfigRegistry.useAnimations ? "_NA" : "");
    	this.iconArray[0] = regIcon.registerIcon("enderstuffp:enderLeaves" + nA);
    	this.iconArray[1] = regIcon.registerIcon("enderstuffp:enderLeaves_opaque" + nA);
    }
    
    @Override
    public boolean canDragonDestroy(World world, int x, int y, int z) {
    	return false;
    }
}
