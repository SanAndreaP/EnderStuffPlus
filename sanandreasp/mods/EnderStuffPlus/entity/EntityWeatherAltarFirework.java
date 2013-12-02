package sanandreasp.mods.EnderStuffPlus.entity;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class EntityWeatherAltarFirework extends EntityFireworkRocket {

	public EntityWeatherAltarFirework(World par1World) {
		super(par1World);
	}

	public EntityWeatherAltarFirework(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6, getFWItem());
	}

	private static ItemStack getFWItem() {
		ItemStack fireworks = new ItemStack(Item.firework, 1);
		NBTTagCompound nbt = new NBTTagCompound("Fireworks");
		
		nbt.setByte("Flight", (byte)0);
		
		NBTTagCompound nbt1 = new NBTTagCompound("Explosion");
		nbt1.setByte("Type", (byte)2);
		nbt1.setIntArray("Colors", new int[] {0x6666cc});
		nbt1.setBoolean("Trail", true);
		
		NBTTagList list = new NBTTagList("Explosions");
		list.appendTag(nbt1);
		
		nbt.setTag("Explosions", list);
		
		NBTTagCompound nbt2 = new NBTTagCompound();
		nbt2.setTag("Fireworks", nbt);
		fireworks.setTagCompound(nbt2);
		
		return fireworks;
	}
}
