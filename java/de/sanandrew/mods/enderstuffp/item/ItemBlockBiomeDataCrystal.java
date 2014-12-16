/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.item;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeDataCrystal;
import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.List;

public class ItemBlockBiomeDataCrystal
        extends ItemBlock
{
    public static final String NBT_BIOME = "biomeId";
    public static final String NBT_DATAPROG = "dataProgress";

    public ItemBlockBiomeDataCrystal(Block block) {
        super(block);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List itemList) {
        super.getSubItems(item, tab, itemList);

        for( BiomeGenBase biome : BiomeGenBase.getBiomeGenArray() ) {
            if( biome != null /*&& !RegistryBiomeChanger.isBiomeDisabled(biome)*/ ) {
                ItemStack stack = new ItemStack(EspBlocks.biomeDataCrystal, 1, 0);
                NBTTagCompound nbt = new NBTTagCompound();

                nbt.setShort(NBT_BIOME, (short) biome.biomeID);
                nbt.setInteger(NBT_DATAPROG, 10);

                stack.setTagCompound(nbt);
                //noinspection unchecked
                itemList.add(stack);
            }
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if( world.getBlock(x, y, z) == Blocks.cauldron && world.getBlockMetadata(x, y, z) > 0 && stack.hasTagCompound() ) {
            stack.setTagCompound(null);
            world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) - 1, 3);
            return true;
        }

        return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        if( super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata) ) {
            TileEntityBiomeDataCrystal crystal = (TileEntityBiomeDataCrystal) world.getTileEntity(x, y, z);

            crystal.biomeID = stack.hasTagCompound() ? stack.getTagCompound().getShort(NBT_BIOME) : -1;
            crystal.dataProgress = stack.hasTagCompound() ? stack.getTagCompound().getInteger(NBT_DATAPROG) : 0;

            return true;
        } else {
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List infos, boolean advancedInfo) {
        int biomeID = stack.hasTagCompound() ? stack.getTagCompound().getShort(NBT_BIOME) : -1;
        int dataProgress = stack.hasTagCompound() ? stack.getTagCompound().getInteger(NBT_DATAPROG) : 0;

        infos.add(String.format("%s", biomeID < 0 ? SAPUtils.translate(this.getUnlocalizedName() + ".empty") : BiomeGenBase.getBiome(biomeID).biomeName));
        if( biomeID >= 0 ) {
            infos.add(SAPUtils.translatePostFormat(this.getUnlocalizedName() + ".percentage", dataProgress * 10));
        }

        super.addInformation(stack, player, infos, advancedInfo);
    }
}
