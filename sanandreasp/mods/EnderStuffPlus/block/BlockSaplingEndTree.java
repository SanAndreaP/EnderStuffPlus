package sanandreasp.mods.EnderStuffPlus.block;

import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.world.WorldGenEndTree;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BlockSaplingEndTree extends BlockFlower {

	public BlockSaplingEndTree(int par1) {
		super(par1);
		float f = 0.4F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return this.blockIcon;
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if( !par1World.isRemote ) {
			super.updateTick(par1World, par2, par3, par4, par5Random);

			if( par1World.getBlockLightValue(par2, par3 + 1, par4) >= 3 && par5Random.nextInt(7) == 0 ) {
				this.markOrGrowMarked(par1World, par2, par3, par4, par5Random);
			}
		}
	}

	public void markOrGrowMarked(World par1World, int par2, int par3, int par4, Random par5Random) {
		int l = par1World.getBlockMetadata(par2, par3, par4);

		if( (l & 8) == 0 ) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, l | 8, 4);
		} else {
			this.growTree(par1World, par2, par3, par4, par5Random);
		}
	}
	
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
		return par1World.getBlockId(par2, par3-1, par4) == Block.whiteStone.blockID;
	}
	
	public void growTree(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if( !TerrainGen.saplingGrowTree(par1World, par5Random, par2, par3, par4) ) return;

		int l = par1World.getBlockMetadata(par2, par3, par4) & 3;
		par1World.setBlock(par2, par3, par4, 0, 0, 4);

		if( !(new WorldGenEndTree(true)).generate(par1World, par5Random, par2, par3, par4) ) {
			par1World.setBlock(par2, par3, par4, this.blockID, l, 4);
		}
	}
	
	@Override
	public int damageDropped(int par1) {
		return par1 & 3;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("enderstuffp:sapling_end");
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int par1) {
		return par1 == Block.whiteStone.blockID;
	}
}
