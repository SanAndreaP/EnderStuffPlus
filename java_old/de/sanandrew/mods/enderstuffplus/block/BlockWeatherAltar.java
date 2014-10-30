package de.sanandrew.mods.enderstuffplus.block;

import java.util.Random;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.client.particle.ParticleFXFuncCollection;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityWeatherAltar;

public class BlockWeatherAltar
    extends BlockDirectional
    implements ITileEntityProvider
{
    public BlockWeatherAltar() {
        super(Material.rock);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityWeatherAltar();
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset,
                                    float yOffset, float zOffset) {
        player.openGui(ESPModRegistry.instance, 5, world, x, y, z);
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        byte rotation = (byte) (MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3);

        super.onBlockPlacedBy(world, x, y, z, entity, stack);
        world.setBlockMetadataWithNotify(x, y, z, rotation, 3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        ParticleFXFuncCollection.spawnWeatherAltarFX(world, x, y, z, random);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = Blocks.quartz_block.getIcon(0, 0);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}
