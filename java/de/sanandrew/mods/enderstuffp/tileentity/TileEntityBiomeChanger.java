package de.sanandrew.mods.enderstuffp.tileentity;

import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

@Optional.Interface(modid="CoFHCore", iface="cofh.api.energy.IEnergyReceiver")
public class TileEntityBiomeChanger
    extends TileEntity
    implements IEnergyReceiver
{
    private byte biomeID = (byte) BiomeGenBase.plains.biomeID;
    private byte currRange = 0;
    private EnumPerimForm form = EnumPerimForm.CIRCLE;
    private boolean isActive = false;
    private boolean isReplacingBlocks = false;
    private byte maxRange = 16;
    private boolean prevIsActive = false;
    private int fluxAmount = 25_000;
    private Random rand = new Random();
    private long ticksExisted = 0;

    private float renderBeamAngle = 360.0F;
    private float renderBeamHeight = 0.0F;
    private int currentRenderPass = 0;

    public TileEntityBiomeChanger() {}

    @Override
    public boolean canUpdate() {
        return true;
    }

    public void changeBiome() {
        switch( this.form ){
            case SQUARE :
                this.changeBiomeSquare();
                break;
            case RHOMBUS :
                this.changeBiomeRhombus();
                break;
            default :
                this.changeBiomeCircle();
        }
    }

    private void changeBiomeCircle() {
        for( int x = -this.currRange; x <= this.currRange; x++ ) {
            for( int z = -this.currRange; z <= this.currRange; z++ ) {
                if( Math.sqrt(x * x + z * z) + 0.5F < this.currRange && Math.sqrt(x * x + z * z) + 1.5D > this.currRange ) {
                    this.changeBiomeBlock(x, z);
                }
            }
        }
    }

    private void changeBiomeRhombus() {
        for( int x = -this.currRange; x <= this.currRange; x++ ) {
            for( int z = -this.currRange; z <= this.currRange; z++ ) {
                if( MathHelper.abs_int(x) + MathHelper.abs_int(z) == this.currRange ) {
                    this.changeBiomeBlock(x, z);
                }
            }
        }
    }

    private void changeBiomeSquare() {
        for( int x = -this.currRange; x <= this.currRange; x++ ) {
            for( int z = -this.currRange; z <= this.currRange; z++ ) {
                if( MathHelper.abs_int(x) == this.currRange || MathHelper.abs_int(z) == this.currRange ) {
                    this.changeBiomeBlock(x, z);
                }
            }
        }
    }

    private void changeBiomeBlock(int x, int z) {
        int x1 = x + this.xCoord;
        int z1 = z + this.zCoord;
        int y = this.worldObj.getTopSolidOrLiquidBlock(x1, z1);

        Chunk chunk = this.worldObj.getChunkFromBlockCoords(x1, z1);
        byte[] biomeArray = chunk.getBiomeArray();

        if( this.isReplacingBlocks && !this.worldObj.isRemote ) {
            byte prevBiomeID = biomeArray[(z1 & 0xF) << 4 | (x1 & 0xF)];

            if( this.worldObj.getBlock(x1, y - 1, z1) == BiomeGenBase.getBiome(prevBiomeID).topBlock && this.worldObj.canBlockSeeTheSky(x1, y, z1) ) {
                this.worldObj.setBlock(x1, y - 1, z1, BiomeGenBase.getBiome(this.biomeID).topBlock, 0, 3);
                for( int i = 0; i < 5 && y - 1 - i >= 0; i++ ) {
                    if( this.worldObj.getBlock(x1, y - 1 - i, z1) == BiomeGenBase.getBiome(prevBiomeID).fillerBlock ) {
                        this.worldObj.setBlock(x1, y - 1 - i, z1, BiomeGenBase.getBiome(this.biomeID).fillerBlock, 0, 3);
                    }
                }
            }
        }

        biomeArray[(z1 & 0xF) << 4 | (x1 & 0xF)] = this.biomeID;
//        for( int i = 0; i < 8; i++ ) {
//            float partX = x + this.xCoord + this.rand.nextFloat();
//            float partZ = z + this.zCoord + this.rand.nextFloat();
//            float partY = y + 0.2F + this.rand.nextFloat() * 0.5F;
//            this.worldObj.spawnParticle("reddust", partX, partY, partZ, -1.0F, 0.0F, 0.0F);
//        }

        chunk.setBiomeArray(biomeArray);
        chunk.setChunkModified();
        this.worldObj.markBlockForUpdate(x1, y, z1);
    }

    public short getBiomeID() {
        return (short) (this.biomeID & 255);
    }

    public short getCurrRange() {
        return (short) (this.currRange & 255);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setShort("CurrRange", this.getCurrRange());
        nbt.setShort("MaxRange", this.getMaxRange());
        nbt.setByte("BiomeID", this.biomeID);
        nbt.setByte("RadForm", this.getRadForm());
        nbt.setBoolean("IsActive", this.isActive);
        nbt.setBoolean("PrevActiveState", this.prevIsActive);

        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g();

        this.setCurrRange(nbt.getShort("CurrRange"));
        this.setMaxRange(nbt.getShort("MaxRange"));
        this.biomeID = nbt.getByte("BiomeID");
        this.form = EnumPerimForm.VALUES[nbt.getByte("RadForm")];
        this.isActive = nbt.getBoolean("IsActive");
        this.prevIsActive = nbt.getBoolean("PrevActiveState");
    }

    public String getName() {
        return "tile.biomeChanger.name";
    }

    public short getMaxRange() {
        return (short) (this.maxRange & 255);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 16384.0D;
    }

    public int getNeededIngotFuel() {
        return (this.maxRange & 255) * 4;
    }

    public byte getRadForm() {
        return (byte) this.form.ordinal();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);

        this.setMaxRange(par1nbtTagCompound.getShort("maxRange"));
        this.biomeID = par1nbtTagCompound.getByte("biomeID");
        this.setCurrRange(par1nbtTagCompound.getShort("currRange"));
        this.form = EnumPerimForm.VALUES[par1nbtTagCompound.getByte("radiusForm")];
        this.isActive = par1nbtTagCompound.getBoolean("isActive");
        this.prevIsActive = par1nbtTagCompound.getBoolean("prevActive");
        this.isReplacingBlocks = par1nbtTagCompound.getBoolean("IsReplacingBlocks");
    }

    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeToNBT(par1nbtTagCompound);

        par1nbtTagCompound.setShort("currRange", this.getCurrRange());
        par1nbtTagCompound.setShort("maxRange", this.getMaxRange());
        par1nbtTagCompound.setByte("biomeID", this.biomeID);
        par1nbtTagCompound.setByte("radiusForm", this.getRadForm());
        par1nbtTagCompound.setBoolean("isActive", this.isActive);
        par1nbtTagCompound.setBoolean("prevActive", this.prevIsActive);
        par1nbtTagCompound.setBoolean("IsReplacingBlocks", this.isReplacingBlocks);
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void setBiomeID(int par1BiomeID) {
        if( par1BiomeID > 255 || par1BiomeID < 0 ) {
            return;
        }

        this.biomeID = (byte) (par1BiomeID & 255);
    }

    public void setCurrRange(int par1CurrRange) {
        if( par1CurrRange > 255 || par1CurrRange < 0 ) {
            return;
        }

        this.currRange = (byte) (par1CurrRange & 255);
    }

    public void setMaxRange(int par1MaxRange) {
        if( par1MaxRange > 255 || par1MaxRange < 0 ) {
            return;
        }

        this.maxRange = (byte) (par1MaxRange & 255);
    }

    public void setRadForm(EnumPerimForm form) {
        this.form = form;
    }

    @Override
    public void updateEntity() {
        this.ticksExisted++;

        if( this.isActive ) {
            if( !this.worldObj.isRemote ) {
                this.changeBiome();
                this.currRange++;

                if( this.currRange >= this.maxRange ) {
                    this.isActive = false;
                }
            } else {
                this.renderBeamAngle += 10.0F;
                if( this.renderBeamAngle > 360.0F ) {
                    this.renderBeamAngle -= 360.0F;
                }

                if( this.renderBeamHeight < 10.0F ) {
                    this.renderBeamHeight += 1.0F;
                }
            }
        } else {
            if( this.worldObj.isRemote ) {
                if( this.renderBeamAngle <= 360.0F ) {
                    this.renderBeamAngle += 10.0F;
                }

                if( this.renderBeamHeight > 0 ) {
                    this.renderBeamHeight -= 1.0F;
                }
            }
        }
    }

    public float getRenderBeamHeight() {
        return this.renderBeamHeight;
    }

    public float getRenderBeamAngle() {
        return this.renderBeamAngle;
    }

    public boolean isReplacingBlocks() {
        return this.isReplacingBlocks;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderInPass(int pass) {
        this.currentRenderPass = pass;
        return pass <= 1;
    }

    public void replaceBlocks(boolean isReplacingBlocks) {
        this.isReplacingBlocks = isReplacingBlocks;
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int maxReceive, boolean checkSize) {
        int energyReceived = Math.min(this.getMaxEnergyStored(forgeDirection) - this.fluxAmount, Math.min(10, maxReceive));

        if (!checkSize) {
            this.fluxAmount += energyReceived;
        }

        return energyReceived;
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return this.fluxAmount;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return 50_000;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return forgeDirection != ForgeDirection.UP;
    }

    public static enum EnumPerimForm
    {
        CIRCLE, RHOMBUS, SQUARE;

        private static EnumPerimForm[] VALUES = values();
    }
}
