/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.entity.living.EntityEnderMiss;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public enum EnumEnderPetEggInfo
{
    ENDERMISS_INFO(0xffbbdd, 0x303030),
    ENDERAVIS_INFO(0x606060, 0xFF00FF),
    NULL_INFO(0x0, 0x0);

    public final int backColorw;
    public final int foreColor;

    private EnumEnderPetEggInfo(int bgColor, int fgColor) {
        this.backColorw = bgColor;
        this.foreColor = fgColor;
    }

    public static final String NBT_ID = "petId";
    public static final String NBT_HEALTH = "health";
    public static final String NBT_MAX_HEALTH = "maxHealth";
    public static final String NBT_COAT = "coat";

    public static final String NBT_MISS_SPECIAL = "special";
    public static final String NBT_MISS_AVISFEATHER = "hasAvisFeather";

    public static final String NBT_AVIS_STAMINA = "stamina";
    public static final String NBT_AVIS_SADDLED = "saddled";

    public static final EnumEnderPetEggInfo[] VALUES = values();

    public static Class<? extends EntityCreature> getEntityClass(EnumEnderPetEggInfo type) {
        switch( type ) {
            case ENDERMISS_INFO: return EntityEnderMiss.class;
            case ENDERAVIS_INFO: return EntityEnderMiss.class; //TODO: correct class when avis is implemented!
        }

        return EntityEnderman.class;
    }

    public static void addInformation(EnumEnderPetEggInfo type, ItemStack stack, List<String> infos, boolean advancedInfo) {
        if( stack.hasTagCompound() ) {
            NBTTagCompound nbt = stack.getTagCompound();

            String entityName = "";
            switch( type ) {
                case ENDERMISS_INFO: entityName = (String) EntityList.classToStringMapping.get(EntityEnderMiss.class); break;
                case ENDERAVIS_INFO: entityName = (String) EntityList.classToStringMapping.get(EntityEnderMiss.class); break; //TODO: correct class when avis is implemented!
            }
            infos.add(SAPUtils.translatePreFormat("entity.%s.name", entityName));

            if( advancedInfo ) {
                infos.add(String.format("%s: %s%.1f HP / %.1f HP", EnumChatFormatting.DARK_AQUA + SAPUtils.translate(EnderStuffPlus.MOD_ID + ".petegg.health"),
                                        EnumChatFormatting.AQUA, nbt.getFloat(NBT_HEALTH), nbt.getFloat(NBT_MAX_HEALTH))
                );

                if( nbt.hasKey(NBT_COAT) ) {
                    infos.add(EnumChatFormatting.DARK_AQUA + SAPUtils.translate(EnderStuffPlus.MOD_ID + ".petegg.hasCoat"));
                }

                switch( type ) {
                    case ENDERMISS_INFO:
                        if( nbt.getBoolean(NBT_MISS_SPECIAL) ) {
                            infos.add(EnumChatFormatting.DARK_AQUA + SAPUtils.translate(EnderStuffPlus.MOD_ID + ".petegg.miss.isSpecial"));
                        }
                        if( nbt.getBoolean(NBT_MISS_AVISFEATHER) ) {
                            infos.add(EnumChatFormatting.DARK_AQUA + SAPUtils.translate(EnderStuffPlus.MOD_ID + ".petegg.miss.noFallDmg"));
                        }
                        break;
                    case ENDERAVIS_INFO:
                        infos.add(String.format("%s: %s%.1f SP", EnumChatFormatting.DARK_AQUA + SAPUtils.translate(EnderStuffPlus.MOD_ID + ".petegg.avis.stamina"),
                                  EnumChatFormatting.AQUA, nbt.getFloat(NBT_AVIS_STAMINA)));
                        if( nbt.getBoolean(NBT_AVIS_SADDLED) ) {
                            infos.add(EnumChatFormatting.DARK_AQUA + SAPUtils.translate(EnderStuffPlus.MOD_ID + ".petegg.avis.saddled"));
                        }
                        break;
                }
            }
        }
    }

    public static EnumEnderPetEggInfo getInfo(int ordinal) {
        return SAPUtils.isIndexInRange(VALUES, ordinal) ? VALUES[ordinal] : NULL_INFO;
    }
}
