package sanandreasp.mods.EnderStuffPlus.block;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityAvisEgg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAvisEgg extends BlockDragonEgg implements ITileEntityProvider
{
	public BlockAvisEgg(int par1) {
		super(par1);
        this.isBlockContainer = true;
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		return false;
	}

	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		;
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack is) {		
		if( par5EntityLiving instanceof EntityPlayer ) {
			TileEntityAvisEgg teavis = (TileEntityAvisEgg)par1World.getBlockTileEntity(par2, par3, par4);
			if( teavis != null ) {
				teavis.playerName = ((EntityPlayer)par5EntityLiving).username;
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("enderstuffp:eggAvis");
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion) {
        par1World.removeBlockTileEntity(par2, par3, par4);
		super.onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
        par1World.removeBlockTileEntity(par2, par3, par4);
		super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
	}
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeBlockTileEntity(par2, par3, par4);
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityAvisEgg();
	}
	
	@Override
    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
    }
	
	@Override
	public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return this.blockID;
    }
}
