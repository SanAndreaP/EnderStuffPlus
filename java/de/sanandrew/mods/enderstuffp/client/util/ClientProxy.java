package de.sanandrew.mods.enderstuffp.client.util;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.mod.client.particle.SAPEffectRenderer;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.enderstuffp.client.event.FovUpdateHandler;
import de.sanandrew.mods.enderstuffp.client.event.RenderGameOverlayHandler;
import de.sanandrew.mods.enderstuffp.client.event.TextureStitchHandler;
import de.sanandrew.mods.enderstuffp.client.render.*;
import de.sanandrew.mods.enderstuffp.client.render.entity.*;
import de.sanandrew.mods.enderstuffp.client.render.tileentity.*;
import de.sanandrew.mods.enderstuffp.entity.EntityWeatherAltarFirework;
import de.sanandrew.mods.enderstuffp.entity.item.EntityBait;
import de.sanandrew.mods.enderstuffp.entity.item.EntityPearlIgnis;
import de.sanandrew.mods.enderstuffp.entity.item.EntityPearlMiss;
import de.sanandrew.mods.enderstuffp.entity.item.EntityPearlNivis;
import de.sanandrew.mods.enderstuffp.entity.living.AEntityEnderAvis;
import de.sanandrew.mods.enderstuffp.entity.living.EntityEnderMiss;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderIgnis;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderNivis;
import de.sanandrew.mods.enderstuffp.entity.projectile.EntityAvisArrow;
import de.sanandrew.mods.enderstuffp.network.packet.PacketTileDataSync.ITileSync;
import de.sanandrew.mods.enderstuffp.tileentity.*;
import de.sanandrew.mods.enderstuffp.util.*;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.util.Random;

@SideOnly(Side.CLIENT)
public class ClientProxy
    extends CommonProxy
{
    public static int particleFxLayer;

    @Override
    public int addArmor(String armorId) {
        return RenderingRegistry.addNewArmourRendererPrefix(armorId);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        RenderingRegistry.registerEntityRenderingHandler(EntityEnderNivis.class, new RenderEnderNivis());
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderIgnis.class, new RenderEnderIgnis());
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderMiss.class, new RenderEnderMiss());
        RenderingRegistry.registerEntityRenderingHandler(AEntityEnderAvis.class, new RenderEnderAvis());
        RenderingRegistry.registerEntityRenderingHandler(EntityPearlNivis.class, new RenderSnowball(EspItems.espPearls, 0));
        RenderingRegistry.registerEntityRenderingHandler(EntityPearlIgnis.class, new RenderSnowball(EspItems.espPearls, 1));
        RenderingRegistry.registerEntityRenderingHandler(EntityPearlMiss.class, new RenderSnowball(EspItems.espPearls, 2));
        RenderingRegistry.registerEntityRenderingHandler(EntityBait.class, new RenderSnowball(EspItems.espPearls, 2));
        RenderingRegistry.registerEntityRenderingHandler(EntityWeatherAltarFirework.class, new RenderWeatherAltarFirework());
        RenderingRegistry.registerEntityRenderingHandler(EntityAvisArrow.class, new RenderAvisArrow());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBiomeChanger.class, new RenderTileEntityBiomeChanger());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWeatherAltar.class, new RenderTileEntityWeatherAltar());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBiomeDataCrystal.class, new RenderTileEntityBiomeDataCrystal());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOreGenerator.class, new RenderTileEntityOreGenerator());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOreCrocoite.class, new RenderTileEntityOreCrocoite());

        MinecraftForgeClient.registerItemRenderer(EspItems.niobSword, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(EspItems.niobPick, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(EspItems.niobAxe, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(EspItems.niobHoe, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(EspItems.niobShovel, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(EspItems.niobShears, new ItemRendererGlowTools());

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EspBlocks.biomeChanger), new ItemRendererBiomeChanger());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EspBlocks.weatherAltar), new ItemRendererWeatherAltar());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EspBlocks.biomeDataCrystal), new ItemRendererBiomeDataCrystal());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EspBlocks.oreGenerator), new ItemRendererOreGenerator());

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EspBlocks.oreCrocoite), new ItemRendererCrocoiteOre());

        MinecraftForgeClient.registerItemRenderer(EspItems.niobBow, new ItemRendererBow());

        BlockRendererOreCrocoite.renderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(BlockRendererOreCrocoite.renderId, new BlockRendererOreCrocoite());

        particleFxLayer = SAPEffectRenderer.INSTANCE.registerFxLayer(EnumTextures.PARTICLES.getResource(), true);
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

        MinecraftForge.EVENT_BUS.register(new FovUpdateHandler());
        MinecraftForge.EVENT_BUS.register(new TextureStitchHandler());
        MinecraftForge.EVENT_BUS.register(new RenderGameOverlayHandler());

    }

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
            case FX_TAME:
                ParticleHelper.spawnTameFX(x, y, z, random);
                break;
            case FX_REJECT:
                ParticleHelper.spawnRefuseFX(x, y, z, random);
                break;
            case FX_MISS_BODY:
                ParticleHelper.spawnEnderBodyFX(x, y, z, random, 1.0F, 0.5F, 0.7F, (Boolean) data.getValue(0));
                break;
            case FX_AVIS_EGG:
                ParticleHelper.spawnPortalFX(x, y, z, random, 5, 0.5F, 0.0F, 1.0F);
                break;
            case FX_WEATHER_ALTAR:
                ParticleHelper.spawnWeatherAltarFX(x, y, z, random, this.getWorld(null));
                break;
            case FX_BIOME_DATA:
                ParticleHelper.spawnBiomeDataFX(x, y, z, random, (Short) data.getValue(0));
                break;
            case FX_ORE_GRIND:
                ParticleHelper.spawnOreGrindFX(x, y, z, random, new ItemStack((Item) Item.itemRegistry.getObject(data.getValue(0)), 1, (int) data.getValue(1)));
                break;
            case FX_NIVIS_BODY:
                ParticleHelper.spawnEnderBodyFX(x, y, z, random, 0.4F, 0.4F, 1.0F, (Boolean) data.getValue(0));
                break;
            case FX_IGNIS_BODY:
                ParticleHelper.spawnEnderBodyFX(x, y, z, random, 1.0F, 1.0F, 0.0F, (Boolean) data.getValue(0));
                break;
            case FX_BIOMECHG_PROGRESS:
                ParticleHelper.spawnBiomeChangerProgressFX(x, y, z, (Short) data.getValue(0));
                break;
            case FX_BIOMECHG_PERIMETER:
                ParticleHelper.spawnBiomeChangerPerimeterFX(x, y, z, (Short) data.getValue(0), (Integer) data.getValue(1), (Integer) data.getValue(2));
                break;
        }
    }

    @Override
    public void syncTileData(int tileX, int tileY, int tileZ, ByteBufInputStream stream) throws IOException {
        TileEntity tile = this.getWorld(null).getTileEntity(tileX, tileY, tileZ);

        if( tile instanceof ITileSync ) {
            ((ITileSync) tile).readFromStream(stream);
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return ClientGuiHandler.getGuiElement(id, player, world, x, y, z);
    }

    @Override
    public void openGui(EntityPlayer player, EnumGui id, int x, int y, int z) {
        if( player == null ) {
            player = Minecraft.getMinecraft().thePlayer;
        }

        super.openGui(player, id, x, y, z);
    }

    @Override
    public World getWorld(INetHandler handler) {
        World commonWorld = super.getWorld(handler);
        if( commonWorld == null ) {
            return Minecraft.getMinecraft().theWorld;
        }

        return commonWorld;
    }
}
