/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCrocoite
        extends TileEntity
{
    private long ticks = 0;
    private long prevWorldTicks = 0;

    @Override
    public void updateEntity() {
        if( this.prevWorldTicks != this.worldObj.getTotalWorldTime() ) {
            this.prevWorldTicks = this.worldObj.getTotalWorldTime();

            if( this.ticks % 2 == 0 ) {
                for( ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS ) {
                    int tileX = this.xCoord + dir.offsetX;
                    int tileY = this.yCoord + dir.offsetY;
                    int tileZ = this.zCoord + dir.offsetZ;

                    TileEntity te = this.worldObj.getTileEntity(tileX, tileY, tileZ);

                    if( te != null && te.canUpdate() ) {
                        te.updateEntity();
                    }
                }
            }

            this.ticks++;
        }
    }
}
