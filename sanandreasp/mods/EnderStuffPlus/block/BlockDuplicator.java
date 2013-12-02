package sanandreasp.mods.EnderStuffPlus.block;

import java.util.Random;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDuplicator extends BlockDirectional implements ITileEntityProvider {
	
	private Icon top, side, frontOn, frontOff;

	public BlockDuplicator(int par1) {
		super(par1, Material.rock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		int meta = par1iBlockAccess.getBlockMetadata(par2, par3, par4);
		return this.getIcon(par5, meta);
	}
	
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		TileEntity var1Tile = par1World.getBlockTileEntity(par2, par3, par4);
		if( var1Tile != null && var1Tile instanceof TileEntityDuplicator && !par1World.isRemote ) {
			TileEntityDuplicator var2TileBC = (TileEntityDuplicator)var1Tile;
			for( int i = 0; i < var2TileBC.getSizeInventory(); i++ ) {
				ItemStack var3Stack = var2TileBC.getStackInSlot(i);
				if( var3Stack != null && var3Stack.stackSize > 0 ) {
					par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, var3Stack));
				}
			}
			
			var1Tile.invalidate();
		}
		
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public Icon getIcon(int par1, int par2) {
		int meta = par2 & 3;
		boolean state = (par2 & 4) == 4;
		switch(par1) {
			case 0: //FALL-THROUGH
			case 1:
				return this.top;
			case 2: return meta == 2 ? state ? this.frontOn : this.frontOff : this.side;
			case 3: return meta == 0 ? state ? this.frontOn : this.frontOff : this.side;
			case 4: return meta == 1 ? state ? this.frontOn : this.frontOff : this.side;
			case 5: return meta == 3 ? state ? this.frontOn : this.frontOff : this.side;
			default: return this.frontOff;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.top = par1IconRegister.registerIcon("enderstuffp:dupe_top");
		this.side = par1IconRegister.registerIcon("enderstuffp:dupe_side");
		this.frontOn = par1IconRegister.registerIcon("enderstuffp:dupe_frontOn");
		this.frontOff = par1IconRegister.registerIcon("enderstuffp:dupe_frontOff");
	}

	@Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
        int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        int bID = par1World.getBlockId(par2, par3, par4);
        par1World.setBlock(par2, par3, par4, bID, var6, 3);
    }
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		par5EntityPlayer.openGui(ESPModRegistry.instance, 4, par1World, par2, par3, par4);
		return true;
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		boolean state = (world.getBlockMetadata(x, y, z) & 4) == 4;
		return state ? 15 : super.getLightValue(world, x, y, z);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		ESPModRegistry.proxy.spawnDupeFX(par1World, par2, par3, par4, par5Random);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityDuplicator();
	}
}
