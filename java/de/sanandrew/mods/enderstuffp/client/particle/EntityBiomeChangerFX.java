/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.particle;

import de.sanandrew.core.manpack.mod.client.particle.EntityParticle;
import de.sanandrew.mods.enderstuffp.client.util.ClientProxy;
import net.minecraft.world.World;

public class EntityBiomeChangerFX
        extends EntityParticle
{
    public EntityBiomeChangerFX(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public int getFXLayer() {
        return ClientProxy.particleFxLayer;
    }
}
