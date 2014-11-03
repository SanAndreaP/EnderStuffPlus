package de.sanandrew.mods.enderstuffp.util;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.enderstuffp.network.PacketProcessor;
import de.sanandrew.mods.enderstuffp.network.ServerPacketHandler;

public class CommonProxy
{
    public void init(FMLInitializationEvent event) {
        EnderStuffPlus.channel.register(new ServerPacketHandler());
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

    public void preInit(FMLPreInitializationEvent event) { }
}
