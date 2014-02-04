package sanandreasp.mods.EnderStuffPlus.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import com.google.common.collect.ImmutableList;
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
			new CoatColorEntry("Black", 0x1C1C22),
			new CoatColorEntry("Red", 0xC62127),
			new CoatColorEntry("Green", 0x4A6B18),
			new CoatColorEntry("Brown", 0x663311),
			new CoatColorEntry("Blue", 0x142EAF),
			new CoatColorEntry("Purple", 0x8C39BC),
			new CoatColorEntry("Cyan", 0x2D7C9D),
			new CoatColorEntry("Light Gray", 0xA9A9A9),
			new CoatColorEntry("Gray", 0x848484),
			new CoatColorEntry("Pink", 0xFF81E8),
			new CoatColorEntry("Lemon Green", 0x83D41C),
			new CoatColorEntry("Yellow", 0xE7E72A),
			new CoatColorEntry("Light Blue", 0x8FB9F4),
			new CoatColorEntry("Magenta", 0xFF08D2),
			new CoatColorEntry("Orange", 0xFF8000),
			new CoatColorEntry("White", 0xFFFFFF),
			new CoatColorEntry("Gold", 0xFFC545),
			new CoatColorEntry("Niobium", 0x3C408B),
			new CoatColorEntry("Transparent", 0xCDCDCD)
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
		if( (dmg & 31) >= 0 && (dmg & 31) < colorList.size() && par2 == 0 )
			return colorList.get(dmg & 31).getColor();
		if( par2 > 0 )
			return baseList.get(dmg >> 5).getColor();
		
		return super.getColorFromItemStack(par1ItemStack, par2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		int clr = par1ItemStack.getItemDamage() & 31;
		int bas = par1ItemStack.getItemDamage() >> 5;
		
		if( clr < colorList.asList().size() && clr >= 0 )
			par3List.add("\247oColor: " + colorList.get(clr).getName());
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
