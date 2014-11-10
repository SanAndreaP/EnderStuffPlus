package de.sanandrew.mods.enderstuffp.util;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.enderstuffp.event.EntityJoinWorldHandler;
import de.sanandrew.mods.enderstuffp.entity.item.*;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderIgnis;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderNivis;
import de.sanandrew.mods.enderstuffp.network.PacketProcessor;
import de.sanandrew.mods.enderstuffp.network.ServerPacketHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event) { }

    public void init(FMLInitializationEvent event) {
        EnderStuffPlus.channel.register(new ServerPacketHandler());

        MinecraftForge.EVENT_BUS.register(new EntityJoinWorldHandler());

        int entityId = 0;
        EntityRegistry.registerModEntity(EntityEnderNivis.class, "EnderNivis", entityId++, EnderStuffPlus.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityEnderIgnis.class, "EnderIgnis", entityId++, EnderStuffPlus.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityPearlNivis.class, "EnderNivisPearl", entityId++, EnderStuffPlus.instance, 64, 10, true);
        EntityRegistry.registerModEntity(EntityPearlIgnis.class, "EnderIgnisPearl", entityId++, EnderStuffPlus.instance, 64, 10, true);
        EntityRegistry.registerModEntity(EntityPearlMiss.class, "EnderMissPearl", entityId++, EnderStuffPlus.instance, 64, 10, true);
        EntityRegistry.registerModEntity(EntityBait.class, "EnderMissBait", entityId++, EnderStuffPlus.instance, 64, 4, false);
        EntityRegistry.registerModEntity(EntityItemTantal.class, "ItemTantal", entityId++, EnderStuffPlus.instance, 64, 20, true);
    }

    public int addArmor(String armorId) {
        return 0;
    }

//    public void registerHandlers() {
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
//    }

    public void handleParticle(EnumParticleFx particleType, double x, double y, double z, Tuple data) { }

    public void spawnParticle(EnumParticleFx particleType, double x, double y, double z, int dimensionId, Tuple data) {
        PacketProcessor.sendToAllAround(PacketProcessor.PKG_PARTICLES, dimensionId, x, y, z, 64.0D, Quintet.with(particleType.ordinalByte(), x, y, z, data));
    }
}
