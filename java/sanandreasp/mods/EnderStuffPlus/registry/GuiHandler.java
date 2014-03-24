package sanandreasp.mods.EnderStuffPlus.registry;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import sanandreasp.mods.EnderStuffPlus.client.gui.GuiDuplicator;
import sanandreasp.mods.EnderStuffPlus.client.gui.GuiEnderPet;
import sanandreasp.mods.EnderStuffPlus.client.gui.GuiWeatherAltar;
import sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger.GuiBiomeChangerBiomes;
import sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger.GuiBiomeChangerConfig;
import sanandreasp.mods.EnderStuffPlus.client.gui.BiomeChanger.GuiBiomeChangerFuel;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;
import sanandreasp.mods.EnderStuffPlus.inventory.ContainerBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.inventory.ContainerDuplicator;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityBiomeChanger;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityWeatherAltar;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if( ID == 1 ) {
			return new ContainerBiomeChanger(player.inventory, (TileEntityBiomeChanger)world.getBlockTileEntity(x, y, z));
		} else if( ID == 4 ) {
			return new ContainerDuplicator(player.inventory, (TileEntityDuplicator)world.getBlockTileEntity(x, y, z));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if( ID == 0 ) {
			EntityLiving entityClt = null;
			if( world instanceof WorldClient) entityClt = (EntityLiving)world.getEntityByID(x );
			if( entityClt instanceof IEnderPet )
				return new GuiEnderPet((IEnderPet)entityClt, player);
		} else if( ID == 1 ) {
			return new GuiBiomeChangerFuel((ContainerBiomeChanger) getServerGuiElement(ID, player, world, x, y, z));
		} else if( ID == 2 ) {
			return new GuiBiomeChangerBiomes((TileEntityBiomeChanger) world.getBlockTileEntity(x, y, z));
		} else if( ID == 3 ) {
			return new GuiBiomeChangerConfig((TileEntityBiomeChanger) world.getBlockTileEntity(x, y, z));
		} else if( ID == 4 ) {
			return new GuiDuplicator((ContainerDuplicator)getServerGuiElement(ID, player, world, x, y, z));
		} else if( ID == 5 ) {
			return new GuiWeatherAltar((TileEntityWeatherAltar)world.getBlockTileEntity(x, y, z));
		}
		return null;
	}
}
