package de.sanandrew.mods.enderstuffplus.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import de.sanandrew.mods.enderstuffplus.registry.event.ArrowEventsInst;
import de.sanandrew.mods.enderstuffplus.registry.event.BonemealEventInst;
import de.sanandrew.mods.enderstuffplus.registry.event.EnderEvents;
import de.sanandrew.mods.enderstuffplus.registry.event.EntityDropEventInst;
import de.sanandrew.mods.enderstuffplus.registry.event.EntityHitEventInst;
import de.sanandrew.mods.enderstuffplus.registry.event.EntityInteractEventInst;
import de.sanandrew.mods.enderstuffplus.registry.event.EntityJoinWorldEventInst;
import de.sanandrew.mods.enderstuffplus.world.EnderStuffWorldGenerator;

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
        MinecraftForge.EVENT_BUS.register(new ArrowEventsInst());
        MinecraftForge.EVENT_BUS.register(new BonemealEventInst());
        MinecraftForge.EVENT_BUS.register(new EnderEvents());
        MinecraftForge.EVENT_BUS.register(new EntityDropEventInst());
        MinecraftForge.EVENT_BUS.register(new EntityHitEventInst());
        MinecraftForge.EVENT_BUS.register(new EntityInteractEventInst());
        MinecraftForge.EVENT_BUS.register(new EntityJoinWorldEventInst());
        MinecraftForge.EVENT_BUS.register(new EnderStuffWorldGenerator());

        GameRegistry.registerWorldGenerator(new EnderStuffWorldGenerator(), 10);
    }

    public void registerPackets() { //FIXME use new packet system!
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "bcGuiAction", new PacketBCGUIAction());
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "bcGuiChange", new PacketChangeBCGUI());
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "dupeInsLevels", new PacketDupeInsertLevels());
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "setEnderName", new PacketSetEnderName());
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "enderGuiAction", new PacketEnderPetGUIAction());
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "riddenJump", new PacketRiddenJump());
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "riddenMove", new PacketRiddenMove());
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "setWeather", new PacketSetWeather());
    }

    public void setJumping(boolean b, EntityLiving entity) {}

}