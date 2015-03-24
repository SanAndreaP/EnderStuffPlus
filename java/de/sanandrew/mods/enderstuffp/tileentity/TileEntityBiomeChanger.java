package de.sanandrew.mods.enderstuffp.tileentity;

import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.mod.client.particle.SAPEffectRenderer;
import de.sanandrew.core.manpack.util.javatuples.Unit;
import de.sanandrew.mods.enderstuffp.client.particle.EntityBiomeChangerFX;
import de.sanandrew.mods.enderstuffp.network.PacketManager;
import de.sanandrew.mods.enderstuffp.network.packet.PacketBiomeChangerActions;
import de.sanandrew.mods.enderstuffp.network.packet.PacketBiomeChangerActions.EnumAction;
import de.sanandrew.mods.enderstuffp.network.packet.PacketTileDataSync.ITileSync;
import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import de.sanandrew.mods.enderstuffp.world.BiomeChangerChunkLoader;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
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

import java.io.IOException;

public class TileEntityBiomeChanger
    extends TileEntity
    implements IEnergyHandler, ITileSync
{
    private short currRange = 0;
    private boolean isActive = false;
    private boolean isReplacingBlocks = false;
    private short maxRange = 16;
    private boolean prevIsActive = false;
    private float renderBeamAngle = 360.0F;
    private float renderBeamHeight = 0.0F;
    private int currentRenderPass = 0;
    private int prevUsedFlux = 0;
    private int prevFluxAmount = -1;

    public int usedFlux = 0;
    public int fluxAmount = 0;
    public EnumPerimForm perimForm = EnumPerimForm.CIRCLE;
    public String customName = null;

    public TileEntityBiomeChanger() {}

    @Override
    public boolean canUpdate() {
        return true;
    }

    public void changeBiome() {
        if( this.hasBiome() ) {
            short currBiome = this.getBiomeId();

            switch( this.perimForm ) {
                case SQUARE:
                    this.changeBiomeSquare((byte) currBiome);
                    break;
                case RHOMBUS:
                    this.changeBiomeRhombus((byte) currBiome);
                    break;
                default:
                    this.changeBiomeCircle((byte) currBiome);
            }
        }
    }

    private void changeBiomeCircle(byte currBiome) {
        for( int x = -this.currRange; x <= this.currRange; x++ ) {
            for( int z = -this.currRange; z <= this.currRange; z++ ) {
                double radius = Math.sqrt(x * x + z * z);
                if( radius < this.currRange + 0.5F && radius > this.currRange - 0.5F ) {
                    this.changeBiomeBlock(x, z, currBiome);
                }
            }
        }
    }

    private void changeBiomeRhombus(byte currBiome) {
        for( int x = -this.currRange; x <= this.currRange; x++ ) {
            for( int z = -this.currRange; z <= this.currRange; z++ ) {
                if( MathHelper.abs_int(x) + MathHelper.abs_int(z) == this.currRange ) {
                    this.changeBiomeBlock(x, z, currBiome);
                }
            }
        }
    }

    private void changeBiomeSquare(byte currBiome) {
        for( int x = -this.currRange; x <= this.currRange; x++ ) {
            for( int z = -this.currRange; z <= this.currRange; z++ ) {
                if( MathHelper.abs_int(x) == this.currRange || MathHelper.abs_int(z) == this.currRange ) {
                    this.changeBiomeBlock(x, z, currBiome);
                }
            }
        }
    }

    private void changeBiomeBlock(int x, int z, byte currBiome) {
        int x1 = x + this.xCoord;
        int z1 = z + this.zCoord;
        int y = this.worldObj.getTopSolidOrLiquidBlock(x1, z1) - 1;

        Chunk chunk = this.worldObj.getChunkFromBlockCoords(x1, z1);
        byte[] biomeArray = chunk.getBiomeArray();

        if( this.isReplacingBlocks && !this.worldObj.isRemote ) {
            BiomeGenBase prevBiome = BiomeGenBase.getBiome(biomeArray[(z1 & 0xF) << 4 | (x1 & 0xF)] & 255);

            if( this.worldObj.getBlock(x1, y, z1) == prevBiome.topBlock && this.worldObj.canBlockSeeTheSky(x1, y + 1, z1) ) {
                BiomeGenBase biome = BiomeGenBase.getBiome(currBiome);
                this.worldObj.setBlock(x1, y, z1, biome.topBlock, biome.field_150604_aj, 2);
                for( int i = 0; i < 5 && y - i >= 0; i++ ) {
                    if( this.worldObj.getBlock(x1, y - i, z1) == prevBiome.fillerBlock ) {
                        this.worldObj.setBlock(x1, y - i, z1, biome.fillerBlock, 0, 2);
                    }
                }
            }
        }

        biomeArray[(z1 & 0xF) << 4 | (x1 & 0xF)] = currBiome;

        chunk.setBiomeArray(biomeArray);
        chunk.setChunkModified();
        this.worldObj.markBlockForUpdate(x1, y, z1);

//        PacketManager.sendToAllAround(PacketManager.BIOME_CHANGER_MODIFY, this.worldObj.provider.dimensionId, x1, y, z1, 64.0D, Triplet.with(x1, z1, currBiome));
    }

    public short getCurrRange() {
        return (short) (this.currRange & 255);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setShort("currRange", this.getCurrRange());
        nbt.setShort("maxRange", this.getMaxRange());
        nbt.setByte("radForm", (byte) this.perimForm.ordinal());
        nbt.setBoolean("isActive", this.isActive);
        nbt.setBoolean("isReplacingBlocks", this.isReplacingBlocks);
        nbt.setInteger("fluxAmount", this.fluxAmount);
        nbt.setInteger("usedFlux", this.usedFlux);
        if( this.customName != null ) {
            nbt.setString("customName", this.customName);
        }

        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.func_148857_g();

        this.setCurrRange(nbt.getShort("currRange"));
        this.setMaxRange(nbt.getShort("maxRange"));
        this.perimForm = EnumPerimForm.VALUES[nbt.getByte("radForm")];
        this.isActive = nbt.getBoolean("isActive");
        this.isReplacingBlocks = nbt.getBoolean("isReplacingBlocks");
        this.fluxAmount = nbt.getInteger("fluxAmount");
        this.usedFlux = nbt.getInteger("usedFlux");
        if( nbt.hasKey("customName") ) {
            this.customName = nbt.getString("customName");
        }
    }

    public String getInventoryName() {
        return this.customName != null ? this.customName : EspBlocks.biomeChanger.getUnlocalizedName() + ".name";
    }

    public short getMaxRange() {
        return (short) (this.maxRange & 255);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 16384.0D;
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
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.setMaxRange(nbt.getShort("maxRange"));
        this.setCurrRange(nbt.getShort("currRange"));
        this.perimForm = EnumPerimForm.VALUES[nbt.getByte("radiusForm")];
        this.isActive = nbt.getBoolean("isActive");
        this.prevIsActive = nbt.getBoolean("prevActive");
        this.isReplacingBlocks = nbt.getBoolean("isReplacingBlocks");
        this.fluxAmount = nbt.getInteger("fluxAmount");
        this.usedFlux = nbt.getInteger("usedFlux");
        if( nbt.hasKey("customName") ) {
            this.customName = nbt.getString("customName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setShort("currRange", this.getCurrRange());
        nbt.setShort("maxRange", this.getMaxRange());
        nbt.setByte("radiusForm", (byte) this.perimForm.ordinal());
        nbt.setBoolean("isActive", this.isActive);
        nbt.setBoolean("prevActive", this.prevIsActive);
        nbt.setBoolean("isReplacingBlocks", this.isReplacingBlocks);
        nbt.setInteger("fluxAmount", this.fluxAmount);
        nbt.setInteger("usedFlux", this.usedFlux);
        if( this.customName != null ) {
            nbt.setString("customName", this.customName);
        }
    }

    public void activate() {
        if( !this.worldObj.isRemote ) {
            if( this.hasBiome() && this.fluxAmount > 0 && this.hasEnoughData() ) {
                this.isActive = true;
                this.drainBiomeData();
                PacketBiomeChangerActions.sendPacketClient(this, EnumAction.ACTIVATE);
                BiomeChangerChunkLoader.forceBiomeChangerChunk(this);
            }
        } else {
            this.isActive = true;
        }
    }

    public void deactivate() {
        this.isActive = false;
        this.currRange = 0;
        this.usedFlux = 0;
        if( !this.worldObj.isRemote ) {
            PacketBiomeChangerActions.sendPacketClient(this, EnumAction.DEACTIVATE);
            BiomeChangerChunkLoader.unforceBiomeChangerChunk(this);
        }
    }

    private void drainBiomeData() {
        TileEntity te = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        if( te instanceof TileEntityBiomeDataCrystal ) {
            int amount = MathHelper.ceiling_double_int(this.maxRange / 128.0D * 10.0D);
            ((TileEntityBiomeDataCrystal) te).drainData(amount);
        }
    }

    @Override
    public void updateEntity() {
        if( this.worldObj.isRemote ) {
            SAPEffectRenderer.INSTANCE.addEffect(new EntityBiomeChangerFX(this.worldObj, this.xCoord, this.yCoord + 2, this.zCoord));
        }

        if( this.isActive ) {
            if( !this.worldObj.isRemote ) {
                int fluxUsage = this.getFluxUsage();

                if( this.currRange >= this.maxRange || !this.hasBiome() ) {
                    this.deactivate();
                } else if( fluxUsage > 0 && this.fluxAmount > 0 ) {
                    if( this.usedFlux >= fluxUsage * 20 ) {
                        this.changeBiome();
                        PacketBiomeChangerActions.sendPacketClient(this, EnumAction.CHANGE_BIOME);
                        this.currRange++;
                        this.usedFlux -= fluxUsage * 20;
                    }

                    int subtract = Math.min(fluxUsage, this.fluxAmount);
                    this.usedFlux += subtract;
                    this.fluxAmount -= subtract;
                }
            } else {
                this.renderBeamAngle += this.fluxAmount == 0 ? 0.5F : 2.0F;
                if( this.renderBeamAngle > 360.0F ) {
                    this.renderBeamAngle -= 360.0F;
                }

                if( this.renderBeamHeight < 10.0F ) {
                    this.renderBeamHeight += 0.5F;
                }
            }
        } else {
            if( this.worldObj.isRemote ) {
                if( this.renderBeamAngle < 360.0F ) {
                    this.renderBeamAngle += 2.0F;
                }

                if( this.renderBeamHeight > 0.0F ) {
                    this.renderBeamHeight -= 0.5F;
                }
            }
        }

        if( !worldObj.isRemote && (this.prevFluxAmount != this.fluxAmount || this.prevUsedFlux != this.usedFlux ) ) {
            this.prevFluxAmount = this.fluxAmount;
            this.prevUsedFlux = this.usedFlux;
            PacketManager.sendToAllAround(PacketManager.TILE_DATA_SYNC, this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 64.0F,
                                          Unit.with(this)
            );
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderInPass(int pass) {
        this.currentRenderPass = pass;
        return pass <= 1;
    }

    public void setCurrRange(int range) {
        this.currRange = (short) Math.max(0, Math.min(256, range));
    }

    public void setMaxRange(int range) {
        this.maxRange = (short) Math.max(0, Math.min(256, range));
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

    public int getRenderPass() {
        return this.currentRenderPass;
    }

    public void replaceBlocks(boolean isReplacingBlocks) {
        this.isReplacingBlocks = isReplacingBlocks;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        int energyReceived = Math.min(this.getMaxEnergyStored(from) - this.fluxAmount, Math.min(500, maxReceive));

        if( !simulate ) {
            this.fluxAmount += energyReceived;
        }

        return energyReceived;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return this.fluxAmount;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return 75_000;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return from != ForgeDirection.UP && from != ForgeDirection.DOWN;
    }

    public int getFluxUsage() {
        return this.isActive ? 50 * (this.isReplacingBlocks ? 4 : 1) : 0;
    }

    public short getBiomeId() {
        TileEntity te = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        if( te instanceof TileEntityBiomeDataCrystal ) {
            TileEntityBiomeDataCrystal dataCrystal = (TileEntityBiomeDataCrystal) te;
            return dataCrystal.getBiomeId();
        }

        return 0;
    }

    public boolean hasBiome() {
        TileEntity te = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        return te instanceof TileEntityBiomeDataCrystal && ((TileEntityBiomeDataCrystal) te).hasBiome();
    }

    public boolean hasEnoughData() {
        TileEntity te = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        if( te instanceof TileEntityBiomeDataCrystal ) {
            int amount = MathHelper.ceiling_double_int(this.maxRange / 128.0D * 10.0D);
            return ((TileEntityBiomeDataCrystal) te).getDataProgress() >= amount;
        }

        return false;
    }

    @Override
    public void writeToStream(ByteBufOutputStream stream) throws IOException {
        stream.writeInt(this.fluxAmount);
        stream.writeInt(this.usedFlux);
    }

    @Override
    public void readFromStream(ByteBufInputStream stream) throws IOException {
        this.fluxAmount = stream.readInt();
        this.usedFlux = stream.readInt();
    }

    public static enum EnumPerimForm
    {
        CIRCLE, RHOMBUS, SQUARE;

        public static final EnumPerimForm[] VALUES = values();
    }
}
