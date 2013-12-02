package sanandreasp.mods.EnderStuffPlus.block;

import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;

public class BlockEnderWood extends Block {

	public BlockEnderWood(int par1) {
		super(par1, Material.wood);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("enderstuffp:enderWood" + (!ConfigRegistry.useAnimations ? "_NA" : ""));
	}
	
	@Override
	public boolean canDragonDestroy(World world, int x, int y, int z) {
		return false;
	}
}
