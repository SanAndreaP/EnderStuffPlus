/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.entity.living;

import net.minecraft.entity.EntityLivingBase;

public interface ITeleportProvider<T extends EntityLivingBase>
{
    public void spawnParticles(double x, double y, double z);
    public T getEntity();
}
