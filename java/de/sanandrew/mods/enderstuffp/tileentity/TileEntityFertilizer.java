/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.tileentity;

import net.minecraft.tileentity.TileEntity;

public class TileEntityFertilizer
        extends TileEntity
{
    private int ticksExisting = 0;

    @Override
    public void updateEntity() {
        if( this.ticksExisting++ >= 0 ) {
            this.ticksExisting = 0;
        }

        int y = this.yCoord - 2;
        if( this.ticksExisting == 0 ) {
            for( int x = -5; x <= 5; x++ ) {
                for( int z = -5; z <= 5; z++ ) {
                    if( this.worldObj.isRemote ) {
                        this.worldObj.spawnParticle("reddust", this.xCoord + x + 0.5F, y + 0.5F, this.zCoord + z + 0.5F, 1.0F, .10F, 1.0F);
                    } else {
                        this.worldObj.getBlock(this.xCoord + x, y, this.zCoord + z).updateTick(this.worldObj, this.xCoord + x, y, this.zCoord + z, this.worldObj.rand);
                    }
                }
            }
        }
    }
}
