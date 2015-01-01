/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.ItemStack;

public enum EnumEnderOres
{
    NIOBIUM(MapColor.blueColor),
    TANTALUM(MapColor.pinkColor);

    public static final ItemStack REPAIR_ITEM_NIOBIUM = new ItemStack(EspItems.enderIngot, 1, EnumEnderOres.NIOBIUM.ordinal());
    public static final ItemStack REPAIR_ITEM_TANTALUM = new ItemStack(EspItems.enderIngot, 1, EnumEnderOres.TANTALUM.ordinal());

    public final String oreName;
    public final String oreTexture;
    public final String oreTextureGlow;

    public final String ingotName;
    public final String ingotTexture;

    public final String nuggetName;
    public final String nuggetTexture;

    public final String blockName;
    public final String blockTextureSide;
    public final String blockTextureSideGlow;
    public final String blockTextureTop;
    public final String blockTextureTopGlow;
    public final MapColor blockMapColor;

    private static final EnumEnderOres[] VALUES = values();

    public static final int COUNT = VALUES.length;

    private EnumEnderOres(MapColor mapColor) {
        String name = this.name();
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        this.oreName = "tile." + EnderStuffPlus.MOD_ID + ":ore" + name;
        this.oreTexture = EnderStuffPlus.MOD_ID + ":ore_" + name.toLowerCase();
        this.oreTextureGlow = EnderStuffPlus.MOD_ID + ":ore_" + name.toLowerCase() + "_glow";

        this.ingotName = "item." + EnderStuffPlus.MOD_ID + ":ingot" + name;
        this.ingotTexture = EnderStuffPlus.MOD_ID + ":ingot_" + name.toLowerCase();

        this.nuggetName = "item." + EnderStuffPlus.MOD_ID + ":nugget" + name;
        this.nuggetTexture = EnderStuffPlus.MOD_ID + ":nugget_" + name.toLowerCase();

        this.blockName = "tile." + EnderStuffPlus.MOD_ID + ":block" + name;
        this.blockTextureSide = EnderStuffPlus.MOD_ID + ":block_" + name.toLowerCase() + "_side";
        this.blockTextureSideGlow = EnderStuffPlus.MOD_ID + ":block_" + name.toLowerCase() + "_side_glow";
        this.blockTextureTop = EnderStuffPlus.MOD_ID + ":block_" + name.toLowerCase() + "_top";
        this.blockTextureTopGlow = EnderStuffPlus.MOD_ID + ":block_" + name.toLowerCase() + "_top_glow";
        this.blockMapColor = mapColor;
    }

    public static EnumEnderOres getType(int ordinal) {
        if( SAPUtils.isIndexInRange(VALUES, ordinal) ) {
            return VALUES[ordinal];
        }

        return VALUES[0];
    }
}
