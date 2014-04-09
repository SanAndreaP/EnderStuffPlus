package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.ArrayList;
import java.util.List;

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
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityItemTantal;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlMiss;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlNivis;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;

import net.minecraftforge.common.BiomeDictionary;

import cpw.mods.fml.common.registry.EntityRegistry;

public final class ModEntityRegistry
{
    public static final void initiate() {
        registerEntities();
        registerSpawnings();
    }

    private static final void registerEntities() {
        short entityID = 0;

        ESPModRegistry.proxy.registerEntity       (EntityAvisArrow.class, "EnderAvisArrow", entityID++, ESPModRegistry.instance, 64, 20, true);
        ESPModRegistry.proxy.registerEntityWithEgg(EntityEnderNivis.class, "EnderNivis", entityID++, ESPModRegistry.instance, 80, 3, true, 0xFFFFFF, 0x66FFFF);
        ESPModRegistry.proxy.registerEntityWithEgg(EntityEnderIgnis.class, "EnderIgnis", entityID++, ESPModRegistry.instance, 80, 3, true, 0xFF0000, 0xFFFF00);
        ESPModRegistry.proxy.registerEntityWithEgg(EntityEnderRay.class, "EnderRay", entityID++, ESPModRegistry.instance, 80, 3, true, 0x222222, 0x8800AA);
        ESPModRegistry.proxy.registerEntityWithEgg(EntityEnderMiss.class, "EnderMiss", entityID++, ESPModRegistry.instance, 80, 3, true, 0xffbbdd, 0x303030);
        ESPModRegistry.proxy.registerEntityWithEgg(EntityEnderAvis.class, "EnderAvis", entityID++, ESPModRegistry.instance, 80, 3, true, 0x606060, 0xFF00FF);
        ESPModRegistry.proxy.registerEntity       (EntityRayball.class, "EnderRayBall", entityID++, ESPModRegistry.instance, 64, 10, false);
        ESPModRegistry.proxy.registerEntity       (EntityWeatherAltarFirework.class, "WAltarFirework", entityID++, ESPModRegistry.instance, 64, 10, true);
        ESPModRegistry.proxy.registerEntityWithEgg(EntityEnderNemesis.class, "EnderNemesis", entityID++, ESPModRegistry.instance, 80, 3, true, 0x606060, 0x3A3AAE);
        ESPModRegistry.proxy.registerEntity       (EntityPearlNivis.class, "EnderNivisPearl", entityID++, ESPModRegistry.instance, 64, 10, true);
        ESPModRegistry.proxy.registerEntity       (EntityPearlIgnis.class, "EnderIgnisPearl", entityID++, ESPModRegistry.instance, 64, 10, true);
        ESPModRegistry.proxy.registerEntity       (EntityPearlMiss.class, "EnderMissPearl", entityID++, ESPModRegistry.instance, 64, 10, true);
        ESPModRegistry.proxy.registerEntity       (EntityBait.class, "EnderMissBait", entityID++, ESPModRegistry.instance, 64, 4, false);
        ESPModRegistry.proxy.registerEntity       (EntityItemTantal.class, "ItemTantal", entityID++, ESPModRegistry.instance, 64, 20, true);
    }

    private static final void registerSpawnings() {
        EntityRegistry.addSpawn(EntityEnderNivis.class, ConfigRegistry.spawnConditions.get("EnderNivis")[0].intValue(),
                                ConfigRegistry.spawnConditions.get("EnderNivis")[1].intValue(),
                                ConfigRegistry.spawnConditions.get("EnderNivis")[2].intValue(),
                                EnumCreatureType.monster,
                                getEnderNivisBiomes());
        EntityRegistry.addSpawn(EntityEnderIgnis.class, ConfigRegistry.spawnConditions.get("EnderIgnis")[0].intValue(),
                                ConfigRegistry.spawnConditions.get("EnderIgnis")[1].intValue(),
                                ConfigRegistry.spawnConditions.get("EnderIgnis")[2].intValue(),
                                EnumCreatureType.monster,
                                new BiomeGenBase[] { BiomeGenBase.sky, BiomeGenBase.desert, BiomeGenBase.hell, BiomeGenBase.desertHills });
        EntityRegistry.addSpawn(EntityEnderRay.class, ConfigRegistry.spawnConditions.get("EnderRay")[0].intValue(),
                                ConfigRegistry.spawnConditions.get("EnderRay")[1].intValue(),
                                ConfigRegistry.spawnConditions.get("EnderRay")[2].intValue(),
                                EnumCreatureType.monster,
                                new BiomeGenBase[] { BiomeGenBase.sky });
        EntityRegistry.addSpawn(EntityEnderMiss.class, ConfigRegistry.spawnConditions.get("EnderMiss")[0].intValue(),
                                ConfigRegistry.spawnConditions.get("EnderMiss")[1].intValue(),
                                ConfigRegistry.spawnConditions.get("EnderMiss")[2].intValue(),
                                EnumCreatureType.monster,
                                getEnderManBiomes());
        EntityRegistry.addSpawn(EntityEnderAvis.class, ConfigRegistry.spawnConditions.get("EnderAvis")[0].intValue(),
                                ConfigRegistry.spawnConditions.get("EnderAvis")[1].intValue(),
                                ConfigRegistry.spawnConditions.get("EnderAvis")[2].intValue(),
                                EnumCreatureType.monster,
                                new BiomeGenBase[] { BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.sky,
                                                     BiomeGenBase.desertHills, BiomeGenBase.forestHills, BiomeGenBase.iceMountains,
                                                     BiomeGenBase.taigaHills, BiomeGenBase.jungleHills });
    }

    @SuppressWarnings("unchecked")
    private static final BiomeGenBase[] getEnderManBiomes() {
        List<BiomeGenBase> biomes = new ArrayList<BiomeGenBase>();

        BiomeGenBase[] biomeList = Iterators.toArray(Iterators.filter(Iterators.forArray(BiomeGenBase.biomeList), Predicates.notNull()), BiomeGenBase.class);
        for( BiomeGenBase currBiome : biomeList ) {
            List<SpawnListEntry> monsterList = currBiome.getSpawnableList(EnumCreatureType.monster);
            for( SpawnListEntry entry : monsterList ) {
                if( entry.entityClass.equals(EntityEnderman.class) ) {
                    biomes.add(currBiome);
                    break;
                }
            }
        }

        return biomes.toArray(new BiomeGenBase[0]);
    }

    private static final BiomeGenBase[] getEnderNivisBiomes() {
        List<BiomeGenBase> biomes = new ArrayList<BiomeGenBase>();

        biomes.add(BiomeGenBase.sky);

        BiomeGenBase[] biomeList = Iterators.toArray(Iterators.filter(Iterators.forArray(BiomeGenBase.biomeList), Predicates.notNull()), BiomeGenBase.class);
        for( BiomeGenBase currBiome : biomeList ) {
            if( currBiome.getEnableSnow() && currBiome.temperature < 0.1F ) {
                biomes.add(currBiome);
            }

            if( BiomeDictionary.isBiomeOfType(currBiome, BiomeDictionary.Type.FROZEN) ) {
                biomes.add(currBiome);
            }
        }

        return biomes.toArray(new BiomeGenBase[0]);
    }
}
