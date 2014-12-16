package de.sanandrew.mods.enderstuffp.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityWeatherAltar;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumGui;
import de.sanandrew.mods.enderstuffp.util.EnumParticleFx;
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

import java.util.Random;

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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
        if( !world.isRemote ) {
            EnderStuffPlus.proxy.openGui(player, EnumGui.WEATHER_ALTAR, x, y, z);
        }

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
        EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_WEATHER_ALTAR, x, y, z, world.provider.dimensionId, null);
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
