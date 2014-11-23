package de.sanandrew.mods.enderstuffp.tileentity;

import de.sanandrew.mods.enderstuffp.entity.living.EntityEnderAvisPet;
import de.sanandrew.mods.enderstuffp.item.ItemAvisCompass;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumParticleFx;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

public class TileEntityAvisEgg
    extends TileEntity
{
    private UUID player = null;
    private int ticksUntilHatch;

    private boolean hasPlayer;

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean("belongsToPlayer", this.player != null);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
    }

    @Override
    public void invalidate() {
        if( this.worldObj.isRemote ) {
            ItemAvisCompass.removeEgg(this);
        }

        super.invalidate();
    }

    private boolean isValidDarkness() {
        return this.worldObj.getBlockLightValue(this.xCoord, this.yCoord, this.zCoord) <= 1;
    }

    @Override
    public void onChunkUnload() {
        if( this.worldObj.isRemote ) {
            ItemAvisCompass.removeEgg(this);
        }
        super.onChunkUnload();
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.hasPlayer = pkt.func_148857_g().getBoolean("belongsToPlayer");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.ticksUntilHatch = nbt.getInteger("ticksHatch");
        if( nbt.hasKey("player") ) {
            this.player = UUID.fromString(nbt.getString("player"));
        }
    }

    @Override
    public void updateEntity() {
        if( this.worldObj.isRemote ) {
            if( !this.hasPlayer ) {
                ItemAvisCompass.addEgg(this);
            } else {
                ItemAvisCompass.removeEgg(this);
            }
        } else {
            if( this.isValidDarkness() && this.player != null ) {
                EnderStuffPlus.proxy.spawnParticle(EnumParticleFx.FX_NIOBTOOL, this.xCoord + 0.5F, this.yCoord, this.zCoord + 0.5F, this.worldObj.provider.dimensionId,
                                                   null);
                if( ++this.ticksUntilHatch >= 100 ) {
                    if( !this.worldObj.isRemote ) {
                        EntityEnderAvisPet avis = new EntityEnderAvisPet(this.worldObj);
                        avis.setTamed(true);
                        avis.setOwner(this.player);
                        avis.setPosition(this.xCoord + 0.5D, this.yCoord, this.zCoord + 0.5D);
                        this.worldObj.spawnEntityInWorld(avis);
                        avis.playLivingSound();
                    }
                    this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
                    this.worldObj.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeToNBT(par1nbtTagCompound);

        par1nbtTagCompound.setInteger("ticksHatch", this.ticksUntilHatch);
        if( this.player != null ) {
            par1nbtTagCompound.setString("player", this.player.toString());
        }
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player.getGameProfile().getId();
    }
}
