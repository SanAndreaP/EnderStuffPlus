package sanandreasp.mods.EnderStuffPlus.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemGeneral extends Item {
	
	private String fileName = "";

	public ItemGeneral(int par1) {
		super(par1);
	}
	
	public ItemGeneral setFileName(String file) {
		this.fileName = file;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		if( fileName.length() > 0 )
			this.itemIcon = par1IconRegister.registerIcon(this.fileName);
	}

}
