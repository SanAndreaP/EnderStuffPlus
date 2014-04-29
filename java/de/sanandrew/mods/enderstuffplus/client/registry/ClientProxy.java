package de.sanandrew.mods.enderstuffplus.client.registry;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.mods.enderstuffplus.client.event.FOVManipulator;
import de.sanandrew.mods.enderstuffplus.client.event.IconRegistry;
import de.sanandrew.mods.enderstuffplus.client.event.RenderHUDEvent;
import de.sanandrew.mods.enderstuffplus.client.render.ItemRendererAhrahSword;
import de.sanandrew.mods.enderstuffplus.client.render.ItemRendererBiomeChanger;
import de.sanandrew.mods.enderstuffplus.client.render.ItemRendererGlowTools;
import de.sanandrew.mods.enderstuffplus.client.render.ItemRendererNiobBow;
import de.sanandrew.mods.enderstuffplus.client.render.ItemRendererWeatherAltar;
import de.sanandrew.mods.enderstuffplus.client.render.RenderTileEntityBiomeChanger;
import de.sanandrew.mods.enderstuffplus.client.render.RenderTileEntityWeatherAltar;
import de.sanandrew.mods.enderstuffplus.client.render.RenderWeatherAltarFirework;
import de.sanandrew.mods.enderstuffplus.client.render.entity.RenderAvisArrow;
import de.sanandrew.mods.enderstuffplus.client.render.entity.RenderEnderAvis;
import de.sanandrew.mods.enderstuffplus.client.render.entity.RenderEnderIgnis;
import de.sanandrew.mods.enderstuffplus.client.render.entity.RenderEnderMiss;
import de.sanandrew.mods.enderstuffplus.client.render.entity.RenderEnderNemesis;
import de.sanandrew.mods.enderstuffplus.client.render.entity.RenderEnderNivis;
import de.sanandrew.mods.enderstuffplus.client.render.entity.RenderEnderRay;
import de.sanandrew.mods.enderstuffplus.client.render.entity.projectile.RenderRayball;
import de.sanandrew.mods.enderstuffplus.entity.EntityAvisArrow;
import de.sanandrew.mods.enderstuffplus.entity.EntityEnderAvis;
import de.sanandrew.mods.enderstuffplus.entity.EntityEnderIgnis;
import de.sanandrew.mods.enderstuffplus.entity.EntityEnderMiss;
import de.sanandrew.mods.enderstuffplus.entity.EntityEnderNemesis;
import de.sanandrew.mods.enderstuffplus.entity.EntityEnderNivis;
import de.sanandrew.mods.enderstuffplus.entity.EntityEnderRay;
import de.sanandrew.mods.enderstuffplus.entity.EntityRayball;
import de.sanandrew.mods.enderstuffplus.entity.EntityWeatherAltarFirework;
import de.sanandrew.mods.enderstuffplus.entity.item.EntityBait;
import de.sanandrew.mods.enderstuffplus.entity.item.EntityPearlIgnis;
import de.sanandrew.mods.enderstuffplus.entity.item.EntityPearlMiss;
import de.sanandrew.mods.enderstuffplus.entity.item.EntityPearlNivis;
import de.sanandrew.mods.enderstuffplus.registry.CommonProxy;
import de.sanandrew.mods.enderstuffplus.registry.ModBlockRegistry;
import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityBiomeChanger;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityWeatherAltar;

@SideOnly(Side.CLIENT)
public class ClientProxy
    extends CommonProxy
{
    @Override
    public int addArmor(String prefix) {
        return RenderingRegistry.addNewArmourRendererPrefix(prefix);
    }

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
        RenderingRegistry.registerEntityRenderingHandler(EntityPearlNivis.class, new RenderSnowball(ModItemRegistry.espPearls, 0));
        RenderingRegistry.registerEntityRenderingHandler(EntityPearlIgnis.class, new RenderSnowball(ModItemRegistry.espPearls, 1));
        RenderingRegistry.registerEntityRenderingHandler(EntityPearlMiss.class, new RenderSnowball(ModItemRegistry.espPearls, 2));
        RenderingRegistry.registerEntityRenderingHandler(EntityBait.class, new RenderSnowball(ModItemRegistry.espPearls, 2));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBiomeChanger.class, new RenderTileEntityBiomeChanger());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWeatherAltar.class, new RenderTileEntityWeatherAltar());

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlockRegistry.biomeChanger), new ItemRendererBiomeChanger());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlockRegistry.weatherAltar), new ItemRendererWeatherAltar());
        MinecraftForgeClient.registerItemRenderer(ModItemRegistry.niobBow, new ItemRendererNiobBow());
        MinecraftForgeClient.registerItemRenderer(ModItemRegistry.niobSword, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(ModItemRegistry.niobPick, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(ModItemRegistry.niobAxe, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(ModItemRegistry.niobHoe, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(ModItemRegistry.niobShovel, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(ModItemRegistry.niobShears, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(ModItemRegistry.ahrahSword, new ItemRendererAhrahSword());
    }

    @Override
    public void registerHandlers() {
//        MinecraftForge.EVENT_BUS.register(new SoundRegistry());
        MinecraftForge.EVENT_BUS.register(new RenderHUDEvent());
        MinecraftForge.EVENT_BUS.register(new FOVManipulator());
        MinecraftForge.EVENT_BUS.register(new IconRegistry());

//        TickRegistry.registerTickHandler(new TickHandlerPlayerClt(), Side.CLIENT);

        super.registerHandlers();
    }

    @Override
    public void registerPackets() {
        super.registerPackets();
        //TODO use new packet system!
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "changeBiome", new PacketChangeBiome());
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "showPetGui", new PacketShowPetGUI());
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "fxPortal", new PacketFXSpawnPortalFX());
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "fxTameAcc", new PacketFXSpawnTameAccept());
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "fxTameRef", new PacketFXSpawnTameRefuse());
//        PacketRegistry.registerPacketHandler(ESPModRegistry.MOD_ID, "fxRayball", new PacketFXSpawnRayballFX());
    }

    @Override
    public void setJumping(boolean jump, EntityLiving entity) {
        if( jump ) {
//            PacketRegistry.sendPacketToServer(ESPModRegistry.MOD_ID, "riddenJump", entity.entityId);
        }
    }
}
