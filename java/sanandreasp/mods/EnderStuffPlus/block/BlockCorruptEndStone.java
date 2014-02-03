package sanandreasp.mods.EnderStuffPlus.block;

import sanandreasp.core.manpack.helpers.client.IconFlippedFixed;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCorruptEndStone extends Block
{
	@SideOnly(Side.CLIENT)
	private Icon icons[] = new Icon[4];
	
	public BlockCorruptEndStone(int par1) {
		super(par1, Block.whiteStone.blockMaterial);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.setBlockMetadataWithNotify(x, y, z, world.rand.nextInt(4), 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister regIcon) {
		this.blockIcon = regIcon.registerIcon("enderstuffp:corrupt_end_stone");
		this.icons[0] = this.blockIcon;
		this.icons[1] = new IconFlippedFixed(this.blockIcon, true, false);
		this.icons[2] = new IconFlippedFixed(this.blockIcon, false, true);
		this.icons[3] = new IconFlippedFixed(this.blockIcon, true, true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return this.icons[Math.max(0, Math.min(3, blockAccess.getBlockMetadata(x, y, z)))];
	}
}
