/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.util;

import de.sanandrew.mods.enderstuffp.client.gui.GuiEnderPet;
import de.sanandrew.mods.enderstuffp.client.gui.GuiWeatherAltar;
import de.sanandrew.mods.enderstuffp.entity.living.IEnderPet;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityWeatherAltar;
import de.sanandrew.mods.enderstuffp.util.EnumGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClientGuiHandler
{
    public static Object getGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch( EnumGui.VALUES[id] ) {
            case ENDERPET:
                return new GuiEnderPet((IEnderPet) world.getEntityByID(x), player);
            case WEATHERALTAR:
                return new GuiWeatherAltar((TileEntityWeatherAltar) world.getTileEntity(x, y, z));
        }
        return null;
    }
}
