package de.sanandrew.mods.enderstuffplus.entity;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.item.Item;
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
        ItemStack fireworks = new ItemStack(Item.firework, 1);
        NBTTagCompound nbtFireworks = new NBTTagCompound();
        NBTTagCompound nbtFirework = new NBTTagCompound("Fireworks");
        NBTTagCompound nbtExplosion = new NBTTagCompound("Explosion");
        NBTTagList nbtExplosions = new NBTTagList("Explosions");

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
