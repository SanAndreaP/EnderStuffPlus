package sanandreasp.mods.EnderStuffPlus.block;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityAvisEgg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAvisEgg extends BlockDragonEgg implements ITileEntityProvider
{
	public BlockAvisEgg(int id) {
		super(id);
        this.isBlockContainer = true;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
		return false;
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {		
		if( entity instanceof EntityPlayer ) {
			TileEntityAvisEgg teavis = (TileEntityAvisEgg)world.getBlockTileEntity(x, y, z);
			if( teavis != null ) {
				teavis.playerName = ((EntityPlayer)entity).username;
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister regIcon) {
		this.blockIcon = regIcon.registerIcon("enderstuffp:eggAvis");
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
        world.removeBlockTileEntity(x, y, z);
		super.onBlockDestroyedByExplosion(world, x, y, z, explosion);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
        world.removeBlockTileEntity(x, y, z);
		super.onBlockDestroyedByPlayer(world, x, y, z, meta);
	}
	
	@Override
    public void breakBlock(World world, int x, int y, int z, int oldID, int oldMeta) {
        super.breakBlock(world, x, y, z, oldID, oldMeta);
        world.removeBlockTileEntity(x, y, z);
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityAvisEgg();
	}
	
	@Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int evtID, int evtParam) {
        super.onBlockEventReceived(world, x, y, z, evtID, evtParam);
        TileEntity tileentity = world.getBlockTileEntity(x, y, z);
        return tileentity != null ? tileentity.receiveClientEvent(evtID, evtParam) : false;
    }
	
	@Override
	public int idPicked(World world, int x, int y, int z) {
        return this.blockID;
    }
}
