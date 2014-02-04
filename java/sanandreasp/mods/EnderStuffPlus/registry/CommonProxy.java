package sanandreasp.mods.EnderStuffPlus.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.common.MinecraftForge;
import sanandreasp.core.manpack.mod.packet.PacketRegistry;
import sanandreasp.mods.EnderStuffPlus.packet.PacketBCGUIAction;
import sanandreasp.mods.EnderStuffPlus.packet.PacketChangeBCGUI;
import sanandreasp.mods.EnderStuffPlus.packet.PacketDupeInsertLevels;
import sanandreasp.mods.EnderStuffPlus.packet.PacketEnderPetGUIAction;
import sanandreasp.mods.EnderStuffPlus.packet.PacketSetWeather;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRiddenJump;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRiddenMove;
import sanandreasp.mods.EnderStuffPlus.packet.PacketSetEnderName;
import sanandreasp.mods.EnderStuffPlus.world.EnderStuffWorldGenerator;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void registerClientStuff() { }
	
	public void registerEntity(Class<? extends Entity> entity, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(entity, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
	
	public void registerEntityWithEgg(Class<? extends Entity> entity, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int fstClr, int sndClr) {
		EntityRegistry.registerGlobalEntityID(entity, entityName, EntityRegistry.findGlobalUniqueEntityId(), fstClr, sndClr);
		EntityRegistry.registerModEntity(entity, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
	
	public void setJumping(boolean b, EntityLiving entity) { }
	
	public int addArmor(String string) {
		return 0;
	}

	public void registerHandlers() {
		MinecraftForge.EVENT_BUS.register(new ServerEvents());
		GameRegistry.registerWorldGenerator(new EnderStuffWorldGenerator());
	}

	public void registerPackets() {
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "bcGuiAction", new PacketBCGUIAction());
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "bcGuiChange", new PacketChangeBCGUI());
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "dupeInsLevels", new PacketDupeInsertLevels());
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "setEnderName", new PacketSetEnderName());
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "enderGuiAction", new PacketEnderPetGUIAction());
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "riddenJump", new PacketRiddenJump());
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "riddenMove", new PacketRiddenMove());
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "setWeather", new PacketSetWeather());
	}
    
}