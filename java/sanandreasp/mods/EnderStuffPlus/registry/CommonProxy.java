package sanandreasp.mods.EnderStuffPlus.registry;

import java.io.DataInputStream;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import sanandreasp.mods.EnderStuffPlus.world.EnderStuffWorldGenerator;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void registerClientStuff() {
		;
	}
	
	public void registerEntity(Class<? extends Entity> entity, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(entity, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
	
	public void registerEntityWithEgg(Class<? extends Entity> entity, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int fstClr, int sndClr) {
		EntityRegistry.registerGlobalEntityID(entity, entityName, EntityRegistry.findGlobalUniqueEntityId(), fstClr, sndClr);
		EntityRegistry.registerModEntity(entity, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
	
	public void setJumping(boolean b, EntityLiving entity) {
		;
	}
	
	public void spawnDupeFX(World par1World, int par2X, int par3Y, int par4Z, Random par5Random) {
		;
	}
	
	public void spawnWeatherAltarFX(World par1World, int par2X, int par3Y, int par4Z, Random par5Random) {
		;
	}
	
	public void spawnParticleFromDIS(DataInputStream dis) {
		;
	}

	public int addArmor(String string) {
		return 0;
	}

	public void registerHandlers() {
		MinecraftForge.EVENT_BUS.register(new ServerEvents());
		GameRegistry.registerWorldGenerator(new EnderStuffWorldGenerator());
	}
    
}