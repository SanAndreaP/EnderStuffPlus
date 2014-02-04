package sanandreasp.mods.EnderStuffPlus.block;

import java.util.Random;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sanandreasp.mods.EnderStuffPlus.client.particle.ParticleFXFuncCollection;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDuplicator extends BlockDirectional implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	private Icon top, side, frontOn, frontOff;

	public BlockDuplicator(int id) {
		super(id, Material.rock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess block, int x, int y, int z, int side) {
		int meta = block.getBlockMetadata(x, y, z);
		return this.getIcon(side, meta);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int oldID, int oldMeta) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if( tile != null && tile instanceof TileEntityDuplicator && !world.isRemote ) {
			TileEntityDuplicator tileDupe = (TileEntityDuplicator)tile;
			for( int i = 0; i < tileDupe.getSizeInventory(); i++ ) {
				ItemStack stack = tileDupe.getStackInSlot(i);
				if( stack != null && stack.stackSize > 0 ) {
					world.spawnEntityInWorld(new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, stack));
				}
			}
			tile.invalidate();
		}
		super.breakBlock(world, x, y, z, oldID, oldMeta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		int stripMeta = meta & 3;
		boolean state = (meta & 4) == 4;
		switch(side) {
			case 0: //FALL-THROUGH
			case 1:
				return this.top;
			case 2:
				return stripMeta == 2 ? state ? this.frontOn : this.frontOff : this.side;
			case 3:
				return stripMeta == 0 ? state ? this.frontOn : this.frontOff : this.side;
			case 4:
				return stripMeta == 1 ? state ? this.frontOn : this.frontOff : this.side;
			case 5:
				return stripMeta == 3 ? state ? this.frontOn : this.frontOff : this.side;
			default:
				return this.frontOff;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister regIcon) {
		this.top = regIcon.registerIcon("enderstuffp:dupe_top");
		this.side = regIcon.registerIcon("enderstuffp:dupe_side");
		this.frontOn = regIcon.registerIcon("enderstuffp:dupe_frontOn");
		this.frontOff = regIcon.registerIcon("enderstuffp:dupe_frontOff");
	}

	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int meta = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        int newBlockID = world.getBlockId(x, y, z);
        world.setBlock(x, y, z, newBlockID, meta, 3);
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
		player.openGui(ESPModRegistry.instance, 4, world, x, y, z);
		return true;
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		boolean state = (world.getBlockMetadata(x, y, z) & 4) == 4;
		return state ? 15 : super.getLightValue(world, x, y, z);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		ParticleFXFuncCollection.spawnDupeFX(world, x, y, z, rand);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityDuplicator();
	}
}
