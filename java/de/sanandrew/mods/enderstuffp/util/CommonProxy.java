package de.sanandrew.mods.enderstuffp.util;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.enderstuffp.entity.living.EntityEnderMiss;
import de.sanandrew.mods.enderstuffp.event.EntityJoinWorldHandler;
import de.sanandrew.mods.enderstuffp.entity.item.*;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderIgnis;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderNivis;
import de.sanandrew.mods.enderstuffp.network.EnumPacket;
import de.sanandrew.mods.enderstuffp.network.PacketProcessor;
import de.sanandrew.mods.enderstuffp.network.ServerPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
        implements IGuiHandler
{
    public void preInit(FMLPreInitializationEvent event) { }

    public void init(FMLInitializationEvent event) {
        EnderStuffPlus.channel.register(new ServerPacketHandler());

        MinecraftForge.EVENT_BUS.register(new EntityJoinWorldHandler());

        int entityId = 0;
        EntityRegistry.registerModEntity(EntityEnderNivis.class, "EnderNivis", entityId++, EnderStuffPlus.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityEnderIgnis.class, "EnderIgnis", entityId++, EnderStuffPlus.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityEnderMiss.class, "EnderMiss", entityId++, EnderStuffPlus.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityPearlNivis.class, "EnderNivisPearl", entityId++, EnderStuffPlus.instance, 64, 10, true);
        EntityRegistry.registerModEntity(EntityPearlIgnis.class, "EnderIgnisPearl", entityId++, EnderStuffPlus.instance, 64, 10, true);
        EntityRegistry.registerModEntity(EntityPearlMiss.class, "EnderMissPearl", entityId++, EnderStuffPlus.instance, 64, 10, true);
        EntityRegistry.registerModEntity(EntityBait.class, "EnderMissBait", entityId++, EnderStuffPlus.instance, 64, 4, false);
        EntityRegistry.registerModEntity(EntityItemTantal.class, "ItemTantal", entityId++, EnderStuffPlus.instance, 64, 20, true);
    }

    public int addArmor(String armorId) {
        return 0;
    }

//    public void registerHandlers() {
        // TODO: reimplement event handlers
//        MinecraftForge.EVENT_BUS.register(new ArrowEventsInst());
//        MinecraftForge.EVENT_BUS.register(new BonemealEventInst());
//        MinecraftForge.EVENT_BUS.register(new EnderEvents());
//        MinecraftForge.EVENT_BUS.register(new EntityDropEventInst());
//        MinecraftForge.EVENT_BUS.register(new EntityHitEventInst());
//        MinecraftForge.EVENT_BUS.register(new EntityInteractEventInst());
//        MinecraftForge.EVENT_BUS.register(new EntityJoinWorldEventInst());
//        MinecraftForge.EVENT_BUS.register(new EnderStuffWorldGenerator());
//
//        GameRegistry.registerWorldGenerator(new EnderStuffWorldGenerator(), 10);
//    }

    public void handleParticle(EnumParticleFx particleType, double x, double y, double z, Tuple data) { }

    public void spawnParticle(EnumParticleFx particleType, double x, double y, double z, int dimensionId, Tuple data) {
        PacketProcessor.sendToAllAround(EnumPacket.PKG_PARTICLES, dimensionId, x, y, z, 64.0D, Quintet.with(particleType.ordinalByte(), x, y, z, data));
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return CommonGuiHandler.getGuiElement(id, player, world, x, y, z);
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public void openGui(EntityPlayer player, EnumGui id, int x, int y, int z) {
        int guiId = id.ordinal();
        if( player instanceof EntityPlayerMP && getServerGuiElement(guiId, player, player.worldObj, x, y, z) == null ) {
            PacketProcessor.sendToPlayer(EnumPacket.PKG_OPEN_CLIENT_GUI, (EntityPlayerMP) player, Quartet.with((byte) guiId, x, y, z));
        } else {
            FMLNetworkHandler.openGui(player, EnderStuffPlus.instance, guiId, player.worldObj, x, y, z);
        }
    }
}
