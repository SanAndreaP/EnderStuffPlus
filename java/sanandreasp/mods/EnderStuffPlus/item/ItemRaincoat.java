package sanandreasp.mods.EnderStuffPlus.item;

import java.util.List;

import sanandreasp.core.manpack.helpers.CommonUsedStuff;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRaincoat extends Item
{
	public static class CoatBaseEntry
	{
		private final int clr;
		private final String dsc;
		private final String nm;

		public CoatBaseEntry(String name, int color, String desc) {
			this.clr = color;
			this.dsc = desc;
			this.nm = name;
		}

		public int getColor() {
			return this.clr;
		}

		public String getDesc() {
			return this.dsc;
		}

		public String getName() {
			return this.nm;
		}
	}

	public static class CoatColorEntry
	{
		private final int clr;
		private final String nm;

		public CoatColorEntry(String name, int color) {
			this.clr = color;
			this.nm = name;
		}

		public int getColor() {
			return this.clr;
		}

		public String getName() {
			return this.nm;
		}
	}

	@SideOnly(Side.CLIENT)
	private Icon iconBase;
	@SideOnly(Side.CLIENT)
	private Icon iconOver;

	public static final ImmutableList<CoatColorEntry> colorList = ImmutableList.of(
			new CoatColorEntry("item.esp:rainCoat.color.black",       0x1A1515),
			new CoatColorEntry("item.esp:rainCoat.color.red",         0xCF3B37),
			new CoatColorEntry("item.esp:rainCoat.color.green",       0x3D591B),
			new CoatColorEntry("item.esp:rainCoat.color.brown",       0x663A20),
			new CoatColorEntry("item.esp:rainCoat.color.blue",        0x3343C6),
			new CoatColorEntry("item.esp:rainCoat.color.purple",      0xB54AE7),
			new CoatColorEntry("item.esp:rainCoat.color.cyan",        0x349EC1),
			new CoatColorEntry("item.esp:rainCoat.color.silver",      0xD3D8D8),
			new CoatColorEntry("item.esp:rainCoat.color.gray",        0x4D4D4D),
			new CoatColorEntry("item.esp:rainCoat.color.pink",        0xF4BBD1),
			new CoatColorEntry("item.esp:rainCoat.color.lime",        0x50E243),
			new CoatColorEntry("item.esp:rainCoat.color.yellow",      0xE4DC2A),
			new CoatColorEntry("item.esp:rainCoat.color.lightBlue",   0x98C2F1),
			new CoatColorEntry("item.esp:rainCoat.color.magenta",     0xE66AEB),
			new CoatColorEntry("item.esp:rainCoat.color.orange",      0xF7B24C),
			new CoatColorEntry("item.esp:rainCoat.color.white",       0xF8F8F8),
			new CoatColorEntry("item.esp:rainCoat.color.gold",        0xC5B600),
			new CoatColorEntry("item.esp:rainCoat.color.niobium",     0x141E61),
			new CoatColorEntry("item.esp:rainCoat.color.transparent", 0xCDCDCD)
	);

	public static final ImmutableList<CoatBaseEntry> baseList = ImmutableList.of(
			new CoatBaseEntry("Gold", 0xFFC545, "+50% pet speed boost"),
			new CoatBaseEntry("Niobium", 0x3C408B, "protection from bad potion effects"),
			new CoatBaseEntry("Iron", 0xA9A9A9, "+50% rider damage"),
			new CoatBaseEntry("Redstone", 0xC62127, "+50% pet jump boost"),
			new CoatBaseEntry("Obsidian", 0x1C1C22, "+33% pet health"),
			new CoatBaseEntry("Tantalum", 0xFF81E8, "protection from bad potion effects,\nimmunity to fire and lava")
	);

	public ItemRaincoat(int par1) {
		super(par1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamageForRenderPass(int par1, int par2) {
		return par2 == 0 ? this.iconBase : this.iconOver;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		int dmg = par1ItemStack.getItemDamage();
		if( (dmg & 31) >= 0 && (dmg & 31) < colorList.size() && par2 == 0 ) {
            return colorList.get(dmg & 31).getColor();
        }
		if( par2 > 0 ) {
            return baseList.get(dmg >> 5).getColor();
        }

		return super.getColorFromItemStack(par1ItemStack, par2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		int clr = par1ItemStack.getItemDamage() & 31;
		int bas = par1ItemStack.getItemDamage() >> 5;

		if( clr < colorList.asList().size() && clr >= 0 ) {
            par3List.add("\247oColor: " + CommonUsedStuff.getTranslated(colorList.get(clr).getName()));
        }
		if( bas < baseList.asList().size() && bas >= 0 ) {
			par3List.add("\247oBase: " + baseList.get(bas).getName());
			String[] split = baseList.get(bas).getDesc().split("\n");
			for( String effect : split ) {
				par3List.add("\2473" + effect);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = this.iconBase = par1IconRegister.registerIcon("enderstuffp:rainCoatBase");
		this.iconOver = par1IconRegister.registerIcon("enderstuffp:rainCoatStripes");
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for( int base = 0; base < baseList.size(); base++ ) {
			for( int clr = 0; clr < colorList.size(); clr++ ) {
				par3List.add(new ItemStack(this, 1, clr | (base << 5)));
			}
		}
	}
}
