package de.sanandrew.mods.enderstuffp.client.util;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.client.render.ItemRendererGlowTools;
import de.sanandrew.mods.enderstuffp.util.CommonProxy;
import de.sanandrew.mods.enderstuffp.util.RegistryItems;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.client.MinecraftForgeClient;

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
        MinecraftForgeClient.registerItemRenderer(RegistryItems.niobSword, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(RegistryItems.niobPick, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(RegistryItems.niobAxe, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(RegistryItems.niobHoe, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(RegistryItems.niobShovel, new ItemRendererGlowTools());
        MinecraftForgeClient.registerItemRenderer(RegistryItems.niobShears, new ItemRendererGlowTools());
//        MinecraftForgeClient.registerItemRenderer(ModItemRegistry.ahrahSword, new ItemRendererAhrahSword());
    }

    @Override
    public void registerHandlers() {
//        MinecraftForge.EVENT_BUS.register(new SoundRegistry());
//        MinecraftForge.EVENT_BUS.register(new RenderHUDEvent());
//        MinecraftForge.EVENT_BUS.register(new FOVManipulator());
//        MinecraftForge.EVENT_BUS.register(new IconRegistry());

//        TickRegistry.registerTickHandler(new TickHandlerPlayerClt(), Side.CLIENT);

        super.registerHandlers();
    }

    @Override
    public void setJumping(boolean jump, EntityLiving entity) {
        if( jump ) {
//            PacketRegistry.sendPacketToServer(ESPModRegistry.MOD_ID, "riddenJump", entity.entityId);
        }
    }
}
