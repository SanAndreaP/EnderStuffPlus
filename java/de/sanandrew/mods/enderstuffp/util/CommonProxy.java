package de.sanandrew.mods.enderstuffp.util;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class CommonProxy
{
    public int addArmor(String string) {
        return 0;
    }

    public void registerClientStuff() {}

    public void registerEntity(Class<? extends Entity> entity, String entityName, int id, Object mod, int trackingRange,
                               int updateFrequency, boolean sendsVelocityUpdates) {
        EntityRegistry.registerModEntity(entity, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    }

    public void registerEntityWithEgg(Class<? extends Entity> entity, String entityName, int id, Object mod, int trackingRange,
                                      int updateFrequency, boolean sendsVelocityUpdates, int fstClr, int sndClr) {
        EntityRegistry.registerGlobalEntityID(entity, entityName, EntityRegistry.findGlobalUniqueEntityId(), fstClr, sndClr);
        EntityRegistry.registerModEntity(entity, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    }

    public void registerHandlers() {
        // TODO: reimplement event handlers
//        MinecraftForge.EVENT_BUS.register(new ArrowEventsInst());
//        MinecraftForge.EVENT_BUS.register(new BonemealEventInst());
//        MinecraftForge.EVENT_BUS.register(new EnderEvents());
//        MinecraftForge.EVENT_BUS.register(new EntityDropEventInst());
//        MinecraftForge.EVENT_BUS.register(new EntityHitEventInst());
//        MinecraftForge.EVENT_BUS.register(new EntityInteractEventInst());
//        MinecraftForge.EVENT_BUS.register(new EntityJoinWorldEventInst());
//        MinecraftForge.EVENT_BUS.register(new EnderStuffWorldGenerator());
//
//        GameRegistry.registerWorldGenerator(new EnderStuffWorldGenerator(), 10);
    }

    public void setJumping(boolean b, EntityLiving entity) {}

}
