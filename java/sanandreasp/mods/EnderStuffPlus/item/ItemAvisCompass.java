package sanandreasp.mods.EnderStuffPlus.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import sanandreasp.mods.EnderStuffPlus.client.registry.IconRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAvisCompass extends ItemGeneral {
	
	private static List<TileEntity> tiles = new ArrayList<TileEntity>();

	public ItemAvisCompass(int par1) {
		super(par1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return IconRegistry.compass;
	}
	
	@SideOnly(Side.CLIENT)
	public static void addTile(TileEntity te) {
		if( !tiles.contains(te) )
			tiles.add(te);
	}

	@SideOnly(Side.CLIENT)
	public static TileEntity getNearestTile(int x, int z) {
		int minX = -1;
		int minZ = -1;
		TileEntity minTE = null;
		int vecX, vecZ;
		
		for( TileEntity te : tiles ) {
			vecX = Math.abs(te.xCoord - x);
			vecZ = Math.abs(te.zCoord - z);
			
			if( (Math.sqrt(vecX*vecX + vecZ*vecZ) < Math.sqrt(minX*minX + minZ*minZ)) || (minX + minZ < 0) ) {
				minX = vecX;
				minZ = vecZ;
				minTE = te;
			}
		}
		
		return minTE;
	}
	
	@SideOnly(Side.CLIENT)
	public static void remTile(TileEntity te) {
		if( tiles.contains(te) ) {
			tiles.remove(te);
		}
	}
}
