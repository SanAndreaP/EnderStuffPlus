package sanandreasp.mods.EnderStuffPlus.client.registry;

import sanandreasp.core.manpack.mod.packet.PacketRegistry;
import sanandreasp.mods.EnderStuffPlus.client.packet.PacketChngBiome;
import sanandreasp.mods.EnderStuffPlus.client.packet.PacketShowPetGUI;
import sanandreasp.mods.EnderStuffPlus.client.packet.particle.PacketFXSpawnPortalFX;
import sanandreasp.mods.EnderStuffPlus.client.packet.particle.PacketFXSpawnTameAccept;
import sanandreasp.mods.EnderStuffPlus.client.render.ItemRendererGlowTools;
import sanandreasp.mods.EnderStuffPlus.client.render.ItemRendererNiobBow;
import sanandreasp.mods.EnderStuffPlus.client.render.ItemRendererWeatherAltar;
import sanandreasp.mods.EnderStuffPlus.client.render.ItemRendererBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.client.render.RenderHUDEvent;
import sanandreasp.mods.EnderStuffPlus.client.render.RenderTileEntityBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.client.render.RenderTileEntityWeatherAltar;
import sanandreasp.mods.EnderStuffPlus.client.render.RenderWeatherAltarFirework;
import sanandreasp.mods.EnderStuffPlus.client.render.entity.RenderAvisArrow;
import sanandreasp.mods.EnderStuffPlus.client.render.entity.RenderEnderAvis;
import sanandreasp.mods.EnderStuffPlus.client.render.entity.RenderEnderIgnis;
import sanandreasp.mods.EnderStuffPlus.client.render.entity.RenderEnderMiss;
import sanandreasp.mods.EnderStuffPlus.client.render.entity.RenderEnderNemesis;
import sanandreasp.mods.EnderStuffPlus.client.render.entity.RenderEnderNivis;
import sanandreasp.mods.EnderStuffPlus.client.render.entity.RenderEnderRay;
import sanandreasp.mods.EnderStuffPlus.client.render.entity.projectile.RenderRayball;
import sanandreasp.mods.EnderStuffPlus.entity.EntityAvisArrow;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNemesis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNivis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderRay;
import sanandreasp.mods.EnderStuffPlus.entity.EntityRayball;
import sanandreasp.mods.EnderStuffPlus.entity.EntityWeatherAltarFirework;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityBait;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlMiss;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlNivis;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.CommonProxy;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	@Override
	public void registerClientStuff() {
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderNivis.class, new RenderEnderNivis());
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderIgnis.class, new RenderEnderIgnis());
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderMiss.class, new RenderEnderMiss());
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderRay.class, new RenderEnderRay());
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderAvis.class, new RenderEnderAvis());
		RenderingRegistry.registerEntityRenderingHandler(EntityRayball.class, new RenderRayball(1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAvisArrow.class, new RenderAvisArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityWeatherAltarFirework.class, new RenderWeatherAltarFirework());
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderNemesis.class, new RenderEnderNemesis());
        RenderingRegistry.registerEntityRenderingHandler(EntityPearlNivis.class, new RenderSnowball(ESPModRegistry.espPearls, 0));
        RenderingRegistry.registerEntityRenderingHandler(EntityPearlIgnis.class, new RenderSnowball(ESPModRegistry.espPearls, 1));
        RenderingRegistry.registerEntityRenderingHandler(EntityPearlMiss.class, new RenderSnowball(ESPModRegistry.espPearls, 2));
        RenderingRegistry.registerEntityRenderingHandler(EntityBait.class, new RenderSnowball(ESPModRegistry.espPearls, 2));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBiomeChanger.class, new RenderTileEntityBiomeChanger());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWeatherAltar.class, new RenderTileEntityWeatherAltar());
        
        MinecraftForgeClient.registerItemRenderer(ESPModRegistry.biomeChanger.blockID, new ItemRendererBiomeChanger());
        MinecraftForgeClient.registerItemRenderer(ESPModRegistry.weatherAltar.blockID, new ItemRendererWeatherAltar());
        
        MinecraftForgeClient.registerItemRenderer(ESPModRegistry.niobBow.itemID, new ItemRendererNiobBow());
        MinecraftForgeClient.registerItemRenderer(ESPModRegistry.niobSword.itemID, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(ESPModRegistry.niobPick.itemID, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(ESPModRegistry.niobAxe.itemID, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(ESPModRegistry.niobHoe.itemID, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(ESPModRegistry.niobShovel.itemID, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(ESPModRegistry.niobShears.itemID, new ItemRendererGlowTools());
	}
	
	@Override
	public void registerHandlers() {
		MinecraftForge.EVENT_BUS.register(new SoundRegistry());
		MinecraftForge.EVENT_BUS.register(new RenderHUDEvent());
		MinecraftForge.EVENT_BUS.register(new FOVManipulator());
		MinecraftForge.EVENT_BUS.register(new IconRegistry());
		
		TickRegistry.registerTickHandler(new TickHandlerPlayerClt(), Side.CLIENT);
        TickRegistry.registerTickHandler(new TickHandlerClient(), Side.CLIENT);
        
		super.registerHandlers();
	}
	
	@Override
	public void registerPackets() {
		super.registerPackets();
		
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "changeBiome", new PacketChngBiome());
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "showPetGui", new PacketShowPetGUI());
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "fxPortal", new PacketFXSpawnPortalFX());
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "fxTameAcc", new PacketFXSpawnTameAccept());
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "fxTameRef", new PacketFXSpawnTameAccept());
		PacketRegistry.registerPacketHandler(ESPModRegistry.modID, "fxRayball", new PacketFXSpawnTameAccept());
	}
	
	@Override
	public int addArmor(String string) {
		return RenderingRegistry.addNewArmourRendererPrefix(string);
	}
	
	@Override
	public void setJumping(boolean jump, EntityLiving entity) {
		if( jump ) {
			PacketRegistry.sendPacketToServer(ESPModRegistry.modID, "riddenJump", entity.entityId);
		}
	}
}
