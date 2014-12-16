/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util;

import de.sanandrew.mods.enderstuffp.item.ItemBlockBiomeDataCrystal;
import de.sanandrew.mods.enderstuffp.util.raincoat.RegistryRaincoats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;

public final class CreativeTabsEnderStuff
{
    public static final CreativeTabs ESP_TAB =
            new CreativeTabs(EnderStuffPlus.MOD_ID + ":creativeTab") {
                @Override
                public Item getTabIconItem() {
                    return EspItems.niobPick;
                }
            };

    public static final CreativeTabs ESP_TAB_COATS =
            new CreativeTabs(EnderStuffPlus.MOD_ID + ":creativeTabCoats") {
                private ItemStack stack;

                @Override
                public Item getTabIconItem() {
                    return null;
                }

                @Override
                public ItemStack getIconItemStack() {
                    if( this.stack == null ) {
                        this.stack = new ItemStack(EspItems.rainCoat);
                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setString("base", RegistryRaincoats.baseGold.getUUID());
                        nbt.setString("color", RegistryRaincoats.getColor(EnderStuffPlus.MOD_ID, "purple").getUUID());
                        this.stack.setTagCompound(nbt);
                    }

                    return this.stack;
                }
            };

    public static final CreativeTabs ESP_TAB_BIOMEDC =
            new CreativeTabs(EnderStuffPlus.MOD_ID + ":creativeTabBiomeDC") {
                private ItemStack stack;

                @Override
                public Item getTabIconItem() {
                    return null;
                }

                @Override
                public ItemStack getIconItemStack() {
                    if( this.stack == null ) {
                        this.stack = new ItemStack(EspBlocks.biomeDataCrystal);
                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setShort(ItemBlockBiomeDataCrystal.NBT_BIOME, (short) BiomeGenBase.mushroomIsland.biomeID);
                        nbt.setInteger(ItemBlockBiomeDataCrystal.NBT_DATAPROG, 10);
                        this.stack.setTagCompound(nbt);
                    }

                    return this.stack;
                }
            };
}
