package sanandreasp.mods.EnderStuffPlus.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderNivis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderRay;

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
		registerSpawnings();
	}

	private static final void registerSpawnings() {
		EntityRegistry.addSpawn(EntityEnderNivis.class,
				ConfigRegistry.spawnConditions.get("EnderNivis")[0].intValue(),
				ConfigRegistry.spawnConditions.get("EnderNivis")[1].intValue(),
				ConfigRegistry.spawnConditions.get("EnderNivis")[2].intValue(),
				EnumCreatureType.monster, getEnderNivisBiomes()
		);
		EntityRegistry.addSpawn(EntityEnderIgnis.class,
				ConfigRegistry.spawnConditions.get("EnderIgnis")[0].intValue(),
				ConfigRegistry.spawnConditions.get("EnderIgnis")[1].intValue(),
				ConfigRegistry.spawnConditions.get("EnderIgnis")[2].intValue(),
				EnumCreatureType.monster,
				new BiomeGenBase[] {
						BiomeGenBase.sky, BiomeGenBase.desert,
						BiomeGenBase.hell, BiomeGenBase.desertHills
				}
		);
		EntityRegistry.addSpawn(EntityEnderRay.class,
				ConfigRegistry.spawnConditions.get("EnderRay")[0].intValue(),
				ConfigRegistry.spawnConditions.get("EnderRay")[1].intValue(),
				ConfigRegistry.spawnConditions.get("EnderRay")[2].intValue(),
				EnumCreatureType.monster,
				new BiomeGenBase[] {
						BiomeGenBase.sky
				}
		);
		EntityRegistry.addSpawn(EntityEnderMiss.class,
				ConfigRegistry.spawnConditions.get("EnderMiss")[0].intValue(),
				ConfigRegistry.spawnConditions.get("EnderMiss")[1].intValue(),
				ConfigRegistry.spawnConditions.get("EnderMiss")[2].intValue(),
				EnumCreatureType.monster, getEnderManBiomes());
		EntityRegistry.addSpawn(EntityEnderAvis.class,
				ConfigRegistry.spawnConditions.get("EnderAvis")[0].intValue(),
				ConfigRegistry.spawnConditions.get("EnderAvis")[1].intValue(),
				ConfigRegistry.spawnConditions.get("EnderAvis")[2].intValue(),
				EnumCreatureType.monster,
				new BiomeGenBase[] {
						BiomeGenBase.extremeHills,
						BiomeGenBase.extremeHillsEdge, BiomeGenBase.sky,
						BiomeGenBase.desertHills, BiomeGenBase.forestHills,
						BiomeGenBase.iceMountains, BiomeGenBase.taigaHills,
						BiomeGenBase.jungleHills
				}
		);
	}

	private static final BiomeGenBase[] getEnderManBiomes() {
		List<BiomeGenBase> biomes = new ArrayList<BiomeGenBase>();
		BiomeGenBase[] biomeList = Iterators.toArray(Iterators.filter(Iterators.forArray(BiomeGenBase.biomeList), Predicates.notNull()), BiomeGenBase.class);

		for( BiomeGenBase currBiome : biomeList ) {
			List<SpawnListEntry> monsterList = Arrays.asList(
					Iterators.toArray(
							Iterators.filter(
									Iterators.forArray(
											currBiome.getSpawnableList(EnumCreatureType.monster).toArray()
									), SpawnListEntry.class
							), SpawnListEntry.class
					)
			);
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
			} if( BiomeDictionary.isBiomeOfType(currBiome, BiomeDictionary.Type.FROZEN) ) {
				biomes.add(currBiome);
			}
		}

		return biomes.toArray(new BiomeGenBase[0]);
	}
}
