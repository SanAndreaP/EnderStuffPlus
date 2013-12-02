package sanandreasp.mods.EnderStuffPlus.item;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemRaincoat extends Item {
	
	@SideOnly(Side.CLIENT)
	private Icon iconBase;
	@SideOnly(Side.CLIENT)
	private Icon iconOver;
	
	private static Map<Integer, String> clrNames = Maps.newHashMap();
	private static Map<Integer, String> strNames = Maps.newHashMap();
	
	public static final int dyeColors[] = new int[] {
			0x1C1C22, // black d
			0xC62127, // red d
			0x4A6B18, // green d
			0x663311, // brown d
			0x142EAF, // blue d
			0x8C39BC, // purple d
			0x2D7C9D, // cyan d
			0xA9A9A9, // light gray d
			0x848484, // gray d
			0xEDA7CB, // pink d
			0x83D41C, // lemon d
			0xE7E72A, // yellow d
			0x8FB9F4, // light blue d
			0xCB69C5, // magenta d
			0xFF8000, // orange d
			0xFFFFFF, // white d
			0xFFC545, // gold
			0x3C408B, // niobium
			0xCDCDCD  // transparent
	};
	
	public static final int dyeStripes[] = new int[] {
			0xFFC545, // gold
			0x3C408B, // niobium
			0xA9A9A9, // iron
			0xC62127, // redstone
			0x1C1C22  // obsidian
	};

	public ItemRaincoat(int par1) {
		super(par1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		clrNames.put(0, "Black");
		clrNames.put(1, "Red");
		clrNames.put(2, "Green");
		clrNames.put(3, "Brown");
		clrNames.put(4, "Blue");
		clrNames.put(5, "Purple");
		clrNames.put(6, "Cyan");
		clrNames.put(7, "Light Gray");
		clrNames.put(8, "Gray");
		clrNames.put(9, "Pink");
		clrNames.put(10, "Lemon Green");
		clrNames.put(11, "Yellow");
		clrNames.put(12, "Light Blue");
		clrNames.put(13, "Magenta");
		clrNames.put(14, "Orange");
		clrNames.put(15, "White");
		clrNames.put(16, "Gold");
		clrNames.put(17, "Niobium");
		clrNames.put(18, "Transparent");
		
		strNames.put(0, "Gold");
		strNames.put(1, "Niobium");
		strNames.put(2, "Iron");
		strNames.put(3, "Redstone");
		strNames.put(4, "Obsidian");
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
		int i = par1ItemStack.getItemDamage();
		if( (i & 31) >= 0 && (i & 31) < dyeColors.length && par2 == 0 )
			return dyeColors[(i & 31)];
		if( par2 > 0 )
			return dyeStripes[(i >> 5)];
		
		return super.getColorFromItemStack(par1ItemStack, par2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if( clrNames.containsKey(par1ItemStack.getItemDamage() & 31) )
			par3List.add("\247oColor: " + clrNames.get(par1ItemStack.getItemDamage() & 31));
		if( strNames.containsKey(par1ItemStack.getItemDamage() >> 5) )
			par3List.add("\247oBase: " + strNames.get(par1ItemStack.getItemDamage() >> 5));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = this.iconBase = par1IconRegister.registerIcon("enderstuffp:rainCoatBase");
		this.iconOver = par1IconRegister.registerIcon("enderstuffp:rainCoatStripes");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for( int dmg1 : strNames.keySet() ) {
			for( int dmg : clrNames.keySet() ) {
				par3List.add(new ItemStack(this, 1, dmg | (dmg1 << 5)));
			}
		}
	}
}
