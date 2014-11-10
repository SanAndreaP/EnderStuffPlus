package de.sanandrew.mods.enderstuffp.client.util;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.enderstuffp.client.event.FovUpdateHandler;
import de.sanandrew.mods.enderstuffp.client.event.TextureStitchHandler;
import de.sanandrew.mods.enderstuffp.client.render.ItemRendererGlowTools;
import de.sanandrew.mods.enderstuffp.client.render.entity.RenderEnderIgnis;
import de.sanandrew.mods.enderstuffp.client.render.entity.RenderEnderNivis;
import de.sanandrew.mods.enderstuffp.entity.item.EntityBait;
import de.sanandrew.mods.enderstuffp.entity.item.EntityPearlIgnis;
import de.sanandrew.mods.enderstuffp.entity.item.EntityPearlMiss;
import de.sanandrew.mods.enderstuffp.entity.item.EntityPearlNivis;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderIgnis;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderNivis;
import de.sanandrew.mods.enderstuffp.network.ClientPacketHandler;
import de.sanandrew.mods.enderstuffp.util.CommonProxy;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumParticleFx;
import de.sanandrew.mods.enderstuffp.util.RegistryItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class ClientProxy
    extends CommonProxy
{
    @Override
    public int addArmor(String armorId) {
        return RenderingRegistry.addNewArmourRendererPrefix(armorId);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        EnderStuffPlus.channel.register(new ClientPacketHandler());

        RenderingRegistry.registerEntityRenderingHandler(EntityEnderNivis.class, new RenderEnderNivis());
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderIgnis.class, new RenderEnderIgnis());
        RenderingRegistry.registerEntityRenderingHandler(EntityPearlNivis.class, new RenderSnowball(RegistryItems.espPearls, 0));
        RenderingRegistry.registerEntityRenderingHandler(EntityPearlIgnis.class, new RenderSnowball(RegistryItems.espPearls, 1));
        RenderingRegistry.registerEntityRenderingHandler(EntityPearlMiss.class, new RenderSnowball(RegistryItems.espPearls, 2));
        RenderingRegistry.registerEntityRenderingHandler(EntityBait.class, new RenderSnowball(RegistryItems.espPearls, 2));
    }

    //    @Override
//    public void registerClientStuff() {

//        RenderingRegistry.registerEntityRenderingHandler(EntityEnderNivis.class, new RenderEnderNivis());
//        RenderingRegistry.registerEntityRenderingHandler(EntityEnderIgnis.class, new RenderEnderIgnis());
//        RenderingRegistry.registerEntityRenderingHandler(EntityEnderMiss.class, new RenderEnderMiss());
//        RenderingRegistry.registerEntityRenderingHandler(EntityEnderRay.class, new RenderEnderRay());
//        RenderingRegistry.registerEntityRenderingHandler(EntityEnderAvisBase.class, new RenderEnderAvis());
//        RenderingRegistry.registerEntityRenderingHandler(EntityRayball.class, new RenderRayball(1F));
//        RenderingRegistry.registerEntityRenderingHandler(EntityAvisArrow.class, new RenderAvisArrow());
//        RenderingRegistry.registerEntityRenderingHandler(EntityWeatherAltarFirework.class, new RenderWeatherAltarFirework());
//        RenderingRegistry.registerEntityRenderingHandler(EntityEnderNemesis.class, new RenderEnderNemesis());
//        RenderingRegistry.registerEntityRenderingHandler(EntityPearlNivis.class, new RenderSnowball(ModItemRegistry.espPearls, 0));
//        RenderingRegistry.registerEntityRenderingHandler(EntityPearlIgnis.class, new RenderSnowball(ModItemRegistry.espPearls, 1));
//        RenderingRegistry.registerEntityRenderingHandler(EntityPearlMiss.class, new RenderSnowball(ModItemRegistry.espPearls, 2));
//        RenderingRegistry.registerEntityRenderingHandler(EntityBait.class, new RenderSnowball(ModItemRegistry.espPearls, 2));
//
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBiomeChanger.class, new RenderTileEntityBiomeChanger());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWeatherAltar.class, new RenderTileEntityWeatherAltar());
//
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlockRegistry.biomeChanger), new ItemRendererBiomeChanger());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlockRegistry.weatherAltar), new ItemRendererWeatherAltar());
//        MinecraftForgeClient.registerItemRenderer(ModItemRegistry.niobBow, new ItemRendererNiobBow());
//        MinecraftForgeClient.registerItemRenderer(ModItemRegistry.ahrahSword, new ItemRendererAhrahSword());
//    }


    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        MinecraftForgeClient.registerItemRenderer(RegistryItems.niobSword, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(RegistryItems.niobPick, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(RegistryItems.niobAxe, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(RegistryItems.niobHoe, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(RegistryItems.niobShovel, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(RegistryItems.niobShears, new ItemRendererGlowTools());

        MinecraftForge.EVENT_BUS.register(new FovUpdateHandler());
        MinecraftForge.EVENT_BUS.register(new TextureStitchHandler());

    }

//    @Override
//    public void registerHandlers() {
////        MinecraftForge.EVENT_BUS.register(new SoundRegistry());
////        MinecraftForge.EVENT_BUS.register(new RenderHUDEvent());
////        MinecraftForge.EVENT_BUS.register(new FOVManipulator());
////        MinecraftForge.EVENT_BUS.register(new IconRegistry());
//
////        TickRegistry.registerTickHandler(new TickHandlerPlayerClt(), Side.CLIENT);
//
//        super.registerHandlers();
//    }

    @Override
    public void handleParticle(EnumParticleFx particleType, double x, double y, double z, Tuple data) {
        Random random = SAPUtils.RNG;
        switch( particleType ) {
            case FX_NIOBTOOL:
                ParticleHelper.spawnPortalFX(x, y, z, random, 20, 0.5F, 0.0F, 1.0F);
                break;
            case FX_PEARL_BAIT:
                ParticleHelper.spawnPortalFX(x, y, z, random, 20, 1.0F, 0.5F, 0.7F);
                break;
            case FX_PEARL_NIVIS:
                ParticleHelper.spawnPortalFX(x, y, z, random, 20, 0.2F, 0.5F, 1.0F);
                break;
            case FX_PEARL_IGNIS:
                ParticleHelper.spawnPortalFX(x, y, z, random, 20, 1.0F, 0.3F, 0.0F);
                break;
        }
    }

    private static WorldClient getWorld() {
        return Minecraft.getMinecraft().theWorld;
    }
}
