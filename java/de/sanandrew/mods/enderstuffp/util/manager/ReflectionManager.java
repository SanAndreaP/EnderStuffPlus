/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util.manager;

import de.sanandrew.core.manpack.util.SAPReflectionHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;

public final class ReflectionManager
{
    public static boolean isArrowInGround(EntityArrow instance) {
        return SAPReflectionHelper.getCachedFieldValue(EntityArrow.class, instance, "inGround", "field_70254_i");
    }

    public static boolean isLivingJumping(EntityLivingBase instance) {
        return SAPReflectionHelper.getCachedFieldValue(EntityLivingBase.class, instance, "isJumping", "field_70703_bu");
    }

    public static int getTicksInAir(EntityArrow instance) {
        return  SAPReflectionHelper.getCachedFieldValue(EntityArrow.class, instance, "ticksInAir", "field_70257_an");
    }
}
