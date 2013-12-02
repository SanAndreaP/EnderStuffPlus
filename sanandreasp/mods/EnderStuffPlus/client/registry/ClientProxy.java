package sanandreasp.mods.EnderStuffPlus.client.registry;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Random;

import sanandreasp.core.manpack.helpers.RenderBlockGlowOverlay;
import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderNemesis;
import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderIgnis;
import sanandreasp.mods.EnderStuffPlus.client.model.ModelEnderNivis;
import sanandreasp.mods.EnderStuffPlus.client.packet.PacketRecvSpawnParticle;
import sanandreasp.mods.EnderStuffPlus.client.particle.EntityWeatherAltarParticleFX;
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
import sanandreasp.mods.EnderStuffPlus.client.render.entity.RenderRayball;
import sanandreasp.mods.EnderStuffPlus.entity.EntityAvisArrow;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNemesis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNivis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderRay;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEndermanESP;
import sanandreasp.mods.EnderStuffPlus.entity.EntityRayball;
import sanandreasp.mods.EnderStuffPlus.entity.EntityWeatherAltarFirework;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityBait;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlMiss;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlNivis;
import sanandreasp.mods.EnderStuffPlus.packet.PacketBase;
import sanandreasp.mods.EnderStuffPlus.packet.PacketRecvJump;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.CommonProxy;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;
import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.EntityEnchantmentTableParticleFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	
	private PacketBase spawnPartPacket;
	
	@Override
	public void registerClientStuff() {
		this.spawnPartPacket = new PacketRecvSpawnParticle();
		
//		RenderingRegistry.registerEntityRenderingHandler(EntityEndermanESP.class, new RenderEnderman());
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderNivis.class, new RenderEnderNivis(new ModelEnderNivis(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderIgnis.class, new RenderEnderIgnis(new ModelEnderIgnis(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderMiss.class, new RenderEnderMiss());
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderRay.class, new RenderEnderRay());
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderAvis.class, new RenderEnderAvis());
		RenderingRegistry.registerEntityRenderingHandler(EntityRayball.class, new RenderRayball(1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAvisArrow.class, new RenderAvisArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityWeatherAltarFirework.class, new RenderWeatherAltarFirework());
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderNemesis.class, new RenderEnderNemesis(new ModelEnderNemesis(), 0.7F));
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
	public int addArmor(String string) {
		return RenderingRegistry.addNewArmourRendererPrefix(string);
	}
	
	@Override
	public void setJumping(boolean b, EntityLiving entity) {
		if( b ) {
			PacketRecvJump.send(entity.entityId);
		}
	}
	
	@Override
	public void spawnParticleFromDIS(DataInputStream dis) {
		try {
			dis.readInt();
			this.spawnPartPacket.handle(dis, Minecraft.getMinecraft().thePlayer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    @Override
    public void spawnDupeFX(World par1World, int par2x, int par3y, int par4z, Random par5Random) {
        int var6 = par1World.getBlockMetadata(par2x, par3y, par4z);
        float var7 = (float)par2x + 0.50F;
        float var8 = (float)par3y + 0.3F + par5Random.nextFloat() * 6.0F / 16.0F;
        float var9 = (float)par4z + 0.50F;
        float var10 = 0.70F;
        float var11 = par5Random.nextFloat() * 0.4F - 0.2F;
        
        double partX = 0F, partY = (double)var8, partZ = 0F;

        if( var6 == 5 )
        {
        	partX = (double)(var7 - var10);
        	partZ = (double)(var9 + var11);
        }
        else if( var6 == 7 )
        {
        	partX = (double)(var7 + var10);
        	partZ = (double)(var9 + var11);
        }
        else if( var6 == 6 )
        {
        	partX = (double)(var7 + var11);
        	partZ = (double)(var9 - var10);
        }
        else if( var6 == 4 )
        {
        	partX = (double)(var7 + var11);
        	partZ = (double)(var9 + var10);
        }
        
        EntityFX particle = new EntityAuraFX(par1World, partX, partY, partZ, 0.0D, 0.0D, 0.0D);;
        particle.setParticleTextureIndex(66);
        particle.setRBGColorF(0.25F + par5Random.nextFloat() * 0.25F, 0.0F, 1.0F);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
        particle.setPosition(partX, partY, partZ);
    }
    
    @Override
    public void spawnWeatherAltarFX(World par1World, int par2x, int par3y, int par4z, Random par5Random) {
		TileEntityWeatherAltar altar = (TileEntityWeatherAltar) par1World.getBlockTileEntity(par2x, par3y, par4z);
		List<Integer[]> blockCoords = altar.getSurroundingPillars();
		for( Integer[] coords : blockCoords ) {
			if( par5Random.nextInt(8) == 0 ) {
				EntityFX particle = new EntityWeatherAltarParticleFX(par1World, (double)par2x + 0.5D, (double)par3y + 2.0D, (double)par4z + 0.5D, (double)((float)(coords[0] - par2x) + par5Random.nextFloat()) - 0.5D, (double)((float)(coords[1] - par3y) - par5Random.nextFloat() - 1.0F), (double)((float)(coords[2] - par4z) + par5Random.nextFloat()) - 0.5D);
				particle.setParticleTextureIndex(66);
		        particle.setRBGColorF(0.25F + par5Random.nextFloat() * 0.25F, 0.0F, 1.0F);
		        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
			}
		}
    }
}
