package sanandreasp.mods.EnderStuffPlus.registry;

import sanandreasp.core.manpack.mod.packet.PacketRegistry;
import sanandreasp.mods.EnderStuffPlus.packet.PacketBCGUIAction;
import sanandreasp.mods.EnderStuffPlus.packet.PacketChangeBCGUI;
import sanandreasp.mods.EnderStuffPlus.packet.PacketDupeInsertLevels;
import sanandreasp.mods.EnderStuffPlus.packet.PacketEnderPetGUIAction;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRiddenJump;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRiddenMove;
import sanandreasp.mods.EnderStuffPlus.packet.PacketSetEnderName;
import sanandreasp.mods.EnderStuffPlus.packet.PacketSetWeather;
import sanandreasp.mods.EnderStuffPlus.registry.event.ArrowEventsInst;
import sanandreasp.mods.EnderStuffPlus.registry.event.BonemealEventInst;
import sanandreasp.mods.EnderStuffPlus.registry.event.EnderTeleportEventInst;
import sanandreasp.mods.EnderStuffPlus.registry.event.EntityDropEventInst;
import sanandreasp.mods.EnderStuffPlus.registry.event.EntityHitEventInst;
import sanandreasp.mods.EnderStuffPlus.registry.event.EntityInteractEventInst;
import sanandreasp.mods.EnderStuffPlus.registry.event.EntityJoinWorldEventInst;
import sanandreasp.mods.EnderStuffPlus.world.EnderStuffWorldGenerator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

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
        MinecraftForge.EVENT_BUS.register(new EnderTeleportEventInst());
        MinecraftForge.EVENT_BUS.register(new EntityDropEventInst());
        MinecraftForge.EVENT_BUS.register(new EntityHitEventInst());
        MinecraftForge.EVENT_BUS.register(new EntityInteractEventInst());
        MinecraftForge.EVENT_BUS.register(new EntityJoinWorldEventInst());
        MinecraftForge.EVENT_BUS.register(new EnderStuffWorldGenerator());

        GameRegistry.registerWorldGenerator(new EnderStuffWorldGenerator());
    }

    public void registerPackets() {
        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "bcGuiAction", new PacketBCGUIAction());
        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "bcGuiChange", new PacketChangeBCGUI());
        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "dupeInsLevels", new PacketDupeInsertLevels());
        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "setEnderName", new PacketSetEnderName());
        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "enderGuiAction", new PacketEnderPetGUIAction());
        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "riddenJump", new PacketRiddenJump());
        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "riddenMove", new PacketRiddenMove());
        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "setWeather", new PacketSetWeather());
    }

    public void setJumping(boolean b, EntityLiving entity) {}

}