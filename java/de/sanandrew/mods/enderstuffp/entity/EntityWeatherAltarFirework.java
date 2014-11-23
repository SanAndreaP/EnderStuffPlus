package de.sanandrew.mods.enderstuffp.entity;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class EntityWeatherAltarFirework
    extends EntityFireworkRocket
{

    public EntityWeatherAltarFirework(World world) {
        super(world);
    }

    public EntityWeatherAltarFirework(World world, double x, double y, double z) {
        super(world, x, y, z, getFWItem());
    }

    private static ItemStack getFWItem() {
        ItemStack fireworks = new ItemStack(Items.fireworks, 1);
        NBTTagCompound nbtFireworks = new NBTTagCompound();
        NBTTagCompound nbtFirework = new NBTTagCompound();
        NBTTagCompound nbtExplosion = new NBTTagCompound();
        NBTTagList nbtExplosions = new NBTTagList();

        nbtFirework.setByte("Flight", (byte) 0);

        nbtExplosion.setByte("Type", (byte) 2);
        nbtExplosion.setIntArray("Colors", new int[] { 0x6666cc });
        nbtExplosion.setBoolean("Trail", true);

        nbtExplosions.appendTag(nbtExplosion);
        nbtFirework.setTag("Explosions", nbtExplosions);
        nbtFireworks.setTag("Fireworks", nbtFirework);
        fireworks.setTagCompound(nbtFireworks);

        return fireworks;
    }
}
