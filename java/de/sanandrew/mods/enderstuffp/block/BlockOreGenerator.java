/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.block;

import de.sanandrew.mods.enderstuffp.tileentity.TileEntityOreGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockOreGenerator
        extends Block
        implements ITileEntityProvider
{
    public BlockOreGenerator() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityOreGenerator();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
        TileEntityOreGenerator tileEntity = (TileEntityOreGenerator) world.getTileEntity(x, y, z);

        player.addChatMessage(new ChatComponentText(Integer.toString(tileEntity.getEnergyStored(ForgeDirection.UP))));

        return true;
    }
}
