package de.sanandrew.mods.enderstuffplus.registry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

import de.sanandrew.mods.enderstuffplus.client.gui.GuiDuplicator;
import de.sanandrew.mods.enderstuffplus.client.gui.GuiEnderPet;
import de.sanandrew.mods.enderstuffplus.client.gui.GuiWeatherAltar;
import de.sanandrew.mods.enderstuffplus.client.gui.BiomeChanger.GuiBiomeChangerBiomes;
import de.sanandrew.mods.enderstuffplus.client.gui.BiomeChanger.GuiBiomeChangerConfig;
import de.sanandrew.mods.enderstuffplus.client.gui.BiomeChanger.GuiBiomeChangerFuel;
import de.sanandrew.mods.enderstuffplus.entity.living.IEnderPet;
import de.sanandrew.mods.enderstuffplus.inventory.ContainerBiomeChanger;
import de.sanandrew.mods.enderstuffplus.inventory.ContainerDuplicator;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityBiomeChanger;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityDuplicator;
import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityWeatherAltar;

public class GuiHandler
    implements IGuiHandler
{
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch( ID ){
            case 0 :
                EntityLivingBase entityClt = (EntityLivingBase) world.getEntityByID(x);

                if( entityClt instanceof IEnderPet ) {
                    return new GuiEnderPet((IEnderPet) entityClt, player);
                }

                return null;
            case 1 :
                return new GuiBiomeChangerFuel((ContainerBiomeChanger) this.getServerGuiElement(ID, player, world, x, y, z));
            case 2 :
                return new GuiBiomeChangerBiomes((TileEntityBiomeChanger) world.getTileEntity(x, y, z));
            case 3 :
                return new GuiBiomeChangerConfig((TileEntityBiomeChanger) world.getTileEntity(x, y, z));
            case 4 :
                return new GuiDuplicator((ContainerDuplicator) this.getServerGuiElement(ID, player, world, x, y, z));
            case 5 :
                return new GuiWeatherAltar((TileEntityWeatherAltar) world.getTileEntity(x, y, z));
            default:
                return null;
        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch( ID ){
            case 1 :
                return new ContainerBiomeChanger(player.inventory, (TileEntityBiomeChanger) world.getTileEntity(x, y, z));
            case 4 :
                return new ContainerDuplicator(player.inventory, (TileEntityDuplicator) world.getTileEntity(x, y, z));
            default:
                return null;
        }
    }
}
