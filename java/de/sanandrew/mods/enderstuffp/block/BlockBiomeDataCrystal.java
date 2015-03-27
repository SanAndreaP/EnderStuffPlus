/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.block;

import de.sanandrew.mods.enderstuffp.item.block.ItemBlockBiomeDataCrystal;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeDataCrystal;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;

public class BlockBiomeDataCrystal
        extends Block
        implements ITileEntityProvider
{
    public BlockBiomeDataCrystal() {
        super(Material.glass);
        this.setBlockName(EnderStuffPlus.MOD_ID + ":biomeDataCrystal");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB_BIOMEDC);
        this.setHardness(1.0F);
        this.setStepSound(Block.soundTypeMetal);
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
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBiomeDataCrystal();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if( !world.isRemote ) {
            TileEntityBiomeDataCrystal crystalTile = (TileEntityBiomeDataCrystal) world.getTileEntity(x, y, z);

            if( crystalTile != null && crystalTile.getBiomeId() >= 0 ) {
                player.addChatMessage(new ChatComponentText(String.format("Biome: %s", BiomeGenBase.getBiome(crystalTile.getBiomeId()).biomeName)));
            } else {
                player.addChatMessage(new ChatComponentText("Empty"));
            }
        }

        return true;
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        if( willHarvest ) {
            ItemStack stack = new ItemStack(this, 1);
            NBTTagCompound nbt = new NBTTagCompound();
            TileEntityBiomeDataCrystal crystalTile = (TileEntityBiomeDataCrystal) world.getTileEntity(x, y, z);

            nbt.setShort(ItemBlockBiomeDataCrystal.NBT_BIOME, crystalTile.hasBiome() ? crystalTile.getBiomeId() : -1);
            nbt.setInteger(ItemBlockBiomeDataCrystal.NBT_DATAPROG, crystalTile.getDataProgress());
            nbt.setBoolean(ItemBlockBiomeDataCrystal.NBT_USED, crystalTile.isUsed);
            stack.setTagCompound(nbt);

            this.dropBlockAsItem(world, x, y, z, stack);
        }

        return super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    @Override
    protected ItemStack createStackedBlock(int damage) {
        return null;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return new ArrayList<>(0);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        ItemStack stack = super.getPickBlock(target, world, x, y, z, player);
        NBTTagCompound nbt = new NBTTagCompound();
        TileEntityBiomeDataCrystal crystalTile = (TileEntityBiomeDataCrystal) world.getTileEntity(x, y, z);

        nbt.setShort(ItemBlockBiomeDataCrystal.NBT_BIOME, crystalTile.hasBiome() ? crystalTile.getBiomeId() : -1);
        nbt.setInteger(ItemBlockBiomeDataCrystal.NBT_DATAPROG, crystalTile.getDataProgress());
        nbt.setBoolean(ItemBlockBiomeDataCrystal.NBT_USED, crystalTile.isUsed);
        stack.setTagCompound(nbt);

        return stack;
    }
}
