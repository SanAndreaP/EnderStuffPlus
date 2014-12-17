/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util;

public enum EnumParticleFx
{
    FX_NIOBTOOL,
    FX_PEARL_BAIT,
    FX_PEARL_NIVIS,
    FX_PEARL_IGNIS,
    FX_TAME,
    FX_REJECT,
    FX_MISS_BODY,
    FX_MISS_TELEPORT,
    FX_AVIS_EGG,
    FX_WEATHER_ALTAR,
    FX_BIOME_DATA,
    FX_ORE_GRIND;

    public static final EnumParticleFx[] VALUES = values();

    public final byte ordinalByte() {
        return (byte) ordinal();
    }
}
