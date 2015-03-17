/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.client.IGlowBlockOverlay;
import de.sanandrew.core.manpack.util.client.RenderBlockGlowOverlay;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityCrocoite;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrocoite
        extends BlockCompressed
        implements IGlowBlockOverlay
{
    @SideOnly(Side.CLIENT)
    private IIcon sideGlowIcon;

    public BlockCrocoite() {
        super(MapColor.redColor);
        this.setBlockName(EnderStuffPlus.MOD_ID + ":blockCrocoite");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setStepSound(Block.soundTypeMetal);
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess blockAccess, int x, int y, int z, Entity entity) {
        return !(entity instanceof EntityDragon);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getOverlayInvTexture(int side, int meta) {
        return this.sideGlowIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getOverlayTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return this.getOverlayInvTexture(side, blockAccess.getBlockMetadata(x, y, z));
    }

    @Override
    public int getRenderType() {
        return RenderBlockGlowOverlay.renderID;
    }

    @Override
    public boolean isBeaconBase(IBlockAccess blockAccess, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":block_crocoite");
        this.sideGlowIcon = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":block_crocoite_glow");
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityCrocoite();
    }
}
