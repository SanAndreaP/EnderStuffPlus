/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import cpw.mods.fml.common.registry.EntityRegistry;
import de.sanandrew.mods.enderstuffp.entity.item.*;
import de.sanandrew.mods.enderstuffp.entity.living.EntityEnderAvisPet;
import de.sanandrew.mods.enderstuffp.entity.living.EntityEnderMiss;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderAvisMother;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderAvisWild;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderIgnis;
import de.sanandrew.mods.enderstuffp.entity.living.monster.EntityEnderNivis;
import de.sanandrew.mods.enderstuffp.entity.projectile.EntityAvisArrow;
import de.sanandrew.mods.enderstuffp.util.manager.ReflectionManager;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;

public class EspEntities
{
    public static void registerEntities() {
        registerEntityClasses();
        registerCreativeEggs();
        registerSpawnings();
    }

    private static void registerEntityClasses() {
        int entityId = 0;
        EntityRegistry.registerModEntity(EntityAvisArrow.class, "EnderAvisArrow", entityId++, EnderStuffPlus.instance, 64, 20, true);
        EntityRegistry.registerModEntity(EntityEnderNivis.class, "EnderNivis", entityId++, EnderStuffPlus.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityEnderIgnis.class, "EnderIgnis", entityId++, EnderStuffPlus.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityEnderMiss.class, "EnderMiss", entityId++, EnderStuffPlus.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityEnderAvisPet.class, "EnderAvisPet", entityId++, EnderStuffPlus.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityEnderAvisMother.class, "EnderAvisMother", entityId++, EnderStuffPlus.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityEnderAvisWild.class, "EnderAvisWild", entityId++, EnderStuffPlus.instance, 80, 1, true);
        EntityRegistry.registerModEntity(EntityPearlNivis.class, "EnderNivisPearl", entityId++, EnderStuffPlus.instance, 64, 10, true);
        EntityRegistry.registerModEntity(EntityPearlIgnis.class, "EnderIgnisPearl", entityId++, EnderStuffPlus.instance, 64, 10, true);
        EntityRegistry.registerModEntity(EntityPearlMiss.class, "EnderMissPearl", entityId++, EnderStuffPlus.instance, 64, 10, true);
        EntityRegistry.registerModEntity(EntityBait.class, "EnderMissBait", entityId++, EnderStuffPlus.instance, 64, 4, false);
        EntityRegistry.registerModEntity(EntityItemFireproof.class, "ItemFireproof", entityId++, EnderStuffPlus.instance, 64, 20, true);
    }

    private static void registerCreativeEggs() {
        ItemEspMonsterPlacer.registerEntityForEgg(EntityEnderNivis.class, 0x6060FF, 0xFFFFFF);
        ItemEspMonsterPlacer.registerEntityForEgg(EntityEnderIgnis.class, 0xFFE000, 0xE80000);
        ItemEspMonsterPlacer.registerEntityForEgg(EntityEnderMiss.class, 0x000000, 0xFF8080);
        ItemEspMonsterPlacer.registerEntityForEgg(EntityEnderAvisWild.class, 0x000000, 0x800080);
    }

    private static void registerSpawnings() {
        EntityRegistry.addSpawn(EntityEnderNivis.class, 10, 1, 4, EnumCreatureType.monster, getColdBiomes());
        EntityRegistry.addSpawn(EntityEnderIgnis.class, 10, 1, 4, EnumCreatureType.monster, getHotBiomes());
        EntityRegistry.addSpawn(EntityEnderAvisWild.class, 10, 3, 6, EnumCreatureType.monster, getHighBiomes());
    }

    private static BiomeGenBase[] getColdBiomes() {
        Collection<BiomeGenBase> biomes = Collections2.filter(Arrays.asList(BiomeGenBase.getBiomeGenArray()), new PredicateSnowBiomes());
        return biomes.toArray(new BiomeGenBase[biomes.size()]);
    }

    private static BiomeGenBase[] getHotBiomes() {
        Collection<BiomeGenBase> biomes = Collections2.filter(Arrays.asList(BiomeGenBase.getBiomeGenArray()), new PredicateHotBiomes());
        return biomes.toArray(new BiomeGenBase[biomes.size()]);
    }

    private static BiomeGenBase[] getHighBiomes() {
        Collection<BiomeGenBase> biomes = Collections2.filter(Arrays.asList(BiomeGenBase.getBiomeGenArray()), new PredicateHighBiomes());
        return biomes.toArray(new BiomeGenBase[biomes.size()]);
    }

    private static class PredicateSnowBiomes implements Predicate<BiomeGenBase>
    {
        @Override
        public boolean apply(@Nullable BiomeGenBase input) {
            return input != null && input.getEnableSnow() && input.temperature < 0.1F;
        }
    }

    private static class PredicateHotBiomes implements Predicate<BiomeGenBase>
    {
        @Override
        public boolean apply(@Nullable BiomeGenBase input) {
            return input != null && !ReflectionManager.getEnableRain(input) && input.temperature > 0.99F;
        }
    }

    private static class PredicateHighBiomes implements Predicate<BiomeGenBase>
    {
        @Override
        public boolean apply(@Nullable BiomeGenBase input) {
            return input != null && input.rootHeight > 0.99F;
        }
    }
}
