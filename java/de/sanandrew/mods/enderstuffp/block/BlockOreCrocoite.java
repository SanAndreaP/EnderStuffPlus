/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.block;

import de.sanandrew.mods.enderstuffp.client.render.BlockRendererOreCrocoite;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityOreCrocoite;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOreCrocoite
        extends BlockOre
{
    private Random rand = new Random();

    public BlockOreCrocoite() {
        super();
        this.setBlockName(EnderStuffPlus.MOD_ID + ":oreCrocoite");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
        this.setHardness(50.0F);
        this.setResistance(2000.0F);
        this.setHarvestLevel("pickaxe", 3);
    }

    @Override
    public int getRenderType() {
        return BlockRendererOreCrocoite.renderId;
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
    public int getLightOpacity() {
        return super.getLightOpacity();
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityOreCrocoite();
    }

    @Override
    public Item getItemDropped(int meta, Random rng, int fortune) {
        return EspItems.crocoiteCrystal;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random) {
        return 1 + random.nextInt(1 + fortune);
    }

    @Override
    public int getExpDrop(IBlockAccess blockAccess, int meta, int fortune) {
        return MathHelper.getRandomIntegerInRange(this.rand, 7, 12);
    }
}
