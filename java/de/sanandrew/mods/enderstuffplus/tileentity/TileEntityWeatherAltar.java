package de.sanandrew.mods.enderstuffplus.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.storage.WorldInfo;

import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.enderstuffplus.entity.EntityWeatherAltarFirework;

public class TileEntityWeatherAltar
    extends TileEntity
{
    private float[] itemFloat = new float[] { 0F, 0F, 0F };
    private float[] prevItemFloat = new float[] { 0F, 0F, 0F };

    @Override
    public boolean canUpdate() {
        return true;
    }

    public List<Integer[]> getSurroundingPillars() {
        List<Integer[]> blockCoords = new ArrayList<Integer[]>();
        for( int x = -1; x < 2; x++ ) {
            for( int z = -1; z < 2; z++ ) {
                for( int y = 0; y < 3; y++ ) {
                    int posX = this.xCoord + x * 2;
                    int posY = this.yCoord - y;
                    int posZ = this.zCoord + z * 2;
                    Block block = this.worldObj.getBlock(posX, posY, posZ);
                    if( block != null
                        && block.isBeaconBase(this.worldObj, posX, posY, posZ, this.xCoord, this.yCoord, this.zCoord) ) {
                        blockCoords.add(new Integer[] { posX, posY, posZ });
                    }
                }
            }
        }
        return blockCoords;
    }

    public int[] getWeather() {
        int[] ret = new int[2];

        if( this.worldObj.getWeightedThunderStrength(0F) > 0.5F ) {
            ret[0] = 2;
        } else if( this.worldObj.getRainStrength(0F) > 0.5F ) {
            ret[0] = 1;
        } else {
            ret[0] = 0;
        }

        return ret;
    }

    public boolean isValidDuration(int duration) {
        int x = this.getSurroundingPillars().size();
        return duration > 0
               && duration <= Math.round(60F + ((1000000F - 60F) / 2600F) * x * ((100F / Math.pow(26F, 2F)) * Math.pow(x, 2)));
    }

    public void setWeather(int id, int duration) {
        WorldInfo wInfo = this.worldObj.getWorldInfo();
        wInfo.setRainTime(duration * 20);
        wInfo.setThunderTime(duration * 20);

        switch( id ){
            case 0 :
                wInfo.setRaining(false);
                wInfo.setThundering(false);
                break;
            case 1 :
                wInfo.setRaining(true);
                wInfo.setThundering(false);
                break;
            case 2 :
                wInfo.setRaining(true);
                wInfo.setThundering(true);
                break;
        }

        EntityWeatherAltarFirework fwexplosion = new EntityWeatherAltarFirework(this.worldObj, this.xCoord + 0.5D, this.yCoord + 1D,
                                                                                this.zCoord + 0.5D);
        this.worldObj.spawnEntityInWorld(fwexplosion);
    }

    @Override
    public void updateEntity() {
        this.itemFloat = this.prevItemFloat;
        for( int i = 0; i < 3; i++ ) {
            if( this.itemFloat[i] > 0F && this.getWeather()[0] != i ) {
                this.itemFloat[i] -= 0.1F;
            } else if( this.itemFloat[i] < 1F && this.getWeather()[0] == i ) {
                this.itemFloat[i] += 0.1F;
            }
        }
    }

    public Pair<float[], float[]> getItemFloat() {
        return Pair.with(this.prevItemFloat, this.itemFloat);
    }
}
