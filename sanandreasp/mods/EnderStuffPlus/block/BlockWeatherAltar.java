package sanandreasp.mods.EnderStuffPlus.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvSetWeather;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockWeatherAltar extends BlockDirectional implements ITileEntityProvider {

	public BlockWeatherAltar(int par1) {
		super(par1, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityWeatherAltar();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = Block.blockNetherQuartz.getIcon(0, 0);
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		par5EntityPlayer.openGui(ESPModRegistry.instance, 5, par1World, par2, par3, par4);
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);
		byte b0 = (byte) (MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
		par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		ESPModRegistry.proxy.spawnWeatherAltarFX(par1World, par2, par3, par4, par5Random);
	}

}
