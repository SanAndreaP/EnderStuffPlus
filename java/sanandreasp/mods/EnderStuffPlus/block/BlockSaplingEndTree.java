package sanandreasp.mods.EnderStuffPlus.block;

import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.world.WorldGenEndTree;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BlockSaplingEndTree extends BlockFlower
{
	public BlockSaplingEndTree(int id) {
		super(id);
		float size = 0.4F;
		this.setBlockBounds(0.5F - size, 0.0F, 0.5F - size, 0.5F + size, size * 2.0F, 0.5F + size);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return this.blockIcon;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if( !world.isRemote ) {
			super.updateTick(world, x, y, z, rand);

			if( world.getBlockLightValue(x, y + 1, z) >= 3 && rand.nextInt(7) == 0 ) {
				this.markOrGrowMarked(world, x, y, z, rand);
			}
		}
	}

	public void markOrGrowMarked(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);

		if( (meta & 8) == 0 ) {
			world.setBlockMetadataWithNotify(x, y, z, meta | 8, 4);
		} else {
			this.growTree(world, x, y, z, rand);
		}
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return world.getBlockId(x, y-1, z) == Block.whiteStone.blockID;
	}
	
	public void growTree(World world, int x, int y, int z, Random rand)
	{
		if( !TerrainGen.saplingGrowTree(world, rand, x, y, z) ) return;

		int meta = world.getBlockMetadata(x, y, z) & 3;
		world.setBlock(x, y, z, 0, 0, 4);

		if( !(new WorldGenEndTree(true)).generate(world, rand, x, y, z) ) {
			world.setBlock(x, y, z, this.blockID, meta, 4);
		}
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta & 3;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister regIcon) {
		this.blockIcon = regIcon.registerIcon("enderstuffp:sapling_end");
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int id) {
		return id == Block.whiteStone.blockID;
	}
}
