/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
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
    FX_AVIS_EGG,
    FX_WEATHER_ALTAR,
    FX_BIOME_DATA,
    FX_ORE_GRIND,
    FX_NIVIS_BODY,
    FX_IGNIS_BODY,
    FX_BIOMECHG_PROGRESS,
    FX_BIOMECHG_PERIMETER;

    public static final EnumParticleFx[] VALUES = values();

    public final byte ordinalByte() {
        return (byte) ordinal();
    }
}
