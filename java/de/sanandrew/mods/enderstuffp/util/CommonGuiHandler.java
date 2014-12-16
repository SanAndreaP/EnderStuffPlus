/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util;

import de.sanandrew.mods.enderstuffp.inventory.ContainerOreGenerator;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityOreGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonGuiHandler
{
    public static Object getGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch( EnumGui.VALUES[id] ) {
            case ORE_GENERATOR:
                return new ContainerOreGenerator(player.inventory, (TileEntityOreGenerator) world.getTileEntity(x, y, z));
            default:
                return null;
        }
    }
}
