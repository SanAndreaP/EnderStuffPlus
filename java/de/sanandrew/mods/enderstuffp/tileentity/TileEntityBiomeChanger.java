package de.sanandrew.mods.enderstuffp.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.util.RegistryBiomeChanger;
import de.sanandrew.mods.enderstuffp.util.RegistryBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class TileEntityBiomeChanger
    extends TileEntity
    implements IInventory
{
    private ItemStack invItemStacks[] = new ItemStack[9];
    private ItemStack savedInvItemStacks[] = new ItemStack[9];

    private byte biomeID = (byte) BiomeGenBase.plains.biomeID;
    private byte currRange = 0;
    private EnumPerimForm form = EnumPerimForm.CIRCLE;
    private boolean isActive = false;
    private boolean isReplacingBlocks = false;
    private byte maxRange = 16;
    private boolean prevIsActive = false;
//    private ItemStack prevFuelItem = null;
    private Random rand = new Random();
    private long ticksExisted = 0;

    private float renderBeamAngle = 0.0F;
    private float renderBeamHeight = 0.0F;
    private int currentRenderPass = 0;

    public TileEntityBiomeChanger() {}

    @Override
    public boolean canUpdate() {
        return true;
    }

    public void changeBiome(int radius) {
        switch( this.form ){
            case SQUARE :
                this.changeBiomeSquare(radius);
                break;
            case RHOMBUS :
                this.changeBiomeRhombus(radius);
                break;
            default :
                this.changeBiomeCircle(radius);
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

    private void changeBiomeCircle(int radius) {
        for( int x = -radius; x <= radius; x++ ) {
            for( int z = -radius; z <= radius; z++ ) {
                if( Math.sqrt(x * x + z * z) + 0.5F < radius && Math.sqrt(x * x + z * z) + 1.5D > radius ) {
                    this.changeBiomeBlock(x, z);
                }
            }
        }
    }

    private void changeBiomeRhombus(int radius) {
        for( int x = -radius; x <= radius; x++ ) {
            for( int z = -radius; z <= radius; z++ ) {
                if( MathHelper.abs_int(x) + MathHelper.abs_int(z) == radius ) {
                    this.changeBiomeBlock(x, z);
                }
            }
        }
    }

    private void changeBiomeSquare(int radius) {
        for( int x = -radius; x <= radius; x++ ) {
            for( int z = -radius; z <= radius; z++ ) {
                if( MathHelper.abs_int(x) == radius || MathHelper.abs_int(z) == radius ) {
                    this.changeBiomeBlock(x, z);
                }
            }
        }
    }

    @Override
    public void closeInventory() {}

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if( this.invItemStacks[par1] != null ) {
            ItemStack var3;

            if( this.invItemStacks[par1].stackSize <= par2 ) {
                var3 = this.invItemStacks[par1];
                this.invItemStacks[par1] = null;
                this.markDirty();
                return var3;
            } else {
                var3 = this.invItemStacks[par1].splitStack(par2);

                if( this.invItemStacks[par1].stackSize == 0 ) {
                    this.invItemStacks[par1] = null;
                }

                this.markDirty();
                return var3;
            }
        } else {
            return null;
        }
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
//        nbt.setBoolean("IsReplacingBlocks", this.isReplacingBlocks);

        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
    }

    public ItemStack[] getInventory() {
        ItemStack[] var1Stacks = new ItemStack[this.invItemStacks.length];
        System.arraycopy(this.invItemStacks, 0, var1Stacks, 0, this.invItemStacks.length);
        return var1Stacks;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public String getInventoryName() {
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

    public int getNeededFuel(int range) {
        if( this.isFuelValid(null) ) {
            for( ItemStack invItemStack : this.invItemStacks ) {
                if( invItemStack != null ) {
                    return range * RegistryBiomeChanger.getMultiFromStack(invItemStack) * (this.isReplacingBlocks ? 4 : 1);
                }
            }
        }
        return -1;
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

    @Override
    public int getSizeInventory() {
        return this.invItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return this.invItemStacks[var1];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        ItemStack is = this.invItemStacks[var1];
        this.setInventorySlotContents(var1, null);
        return is;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public boolean isEnoughFuel(int amount) {
        int remain = amount;

        for( int i = this.invItemStacks.length - 1; i >= 0 && remain > 0; i-- ) {
            ItemStack is = this.invItemStacks[i];
            if( is != null && is.stackSize > 0 ) {
                remain -= is.stackSize;
            }
        }

        return remain <= 0;
    }

    public boolean isFuelValid(ItemStack par1Stack) {
        ItemStack currIS = par1Stack;
        for( ItemStack is : this.invItemStacks ) {
            if( currIS == null && is != null ) {
                currIS = is;
            } else if( currIS != null && is != null && !currIS.isItemEqual(is) ) {
                return false;
            }
        }

        return RegistryBiomeChanger.getMultiFromStack(currIS) > 0;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) < 64.0D;
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
//        this.isReplacingBlocks = nbt.getBoolean("IsReplacingBlocks");
    }

    @Override
    public void openInventory() {}

    private void readdFuel(int fuelLeft) {
        for( int i = 0, remain = fuelLeft; i < this.savedInvItemStacks.length && remain > 0; i++ ) {
            ItemStack stack = this.savedInvItemStacks[i];
            if( stack == null ) {
                continue;
            }

            if( stack.stackSize > remain ) {
                stack.stackSize = remain;
                remain = 0;
            } else {
                remain -= stack.stackSize;
            }

            if( stack.stackSize > 0 ) {
                for( int j = 0; j < this.invItemStacks.length; j++ ) {
                    ItemStack invItem = this.invItemStacks[j];
                    if( invItem == null ) {
                        this.invItemStacks[j] = stack.copy();
                        stack.stackSize = 0;
                        break;
                    } else if( invItem.stackSize < invItem.getMaxStackSize() && ItemStack.areItemStacksEqual(invItem, stack) ) {
                        int diff = stack.stackSize - invItem.stackSize;
                        remain -= stack.getMaxStackSize()
                    }
                }
            }
        }
//        for( int i = 0; i < this.invItemStacks.length && remain > 0 && this.prevFuelItem != null; i++ ) {
//            ItemStack is = this.invItemStacks[i] != null ? this.invItemStacks[i].copy() : null;
//            if( is != null && is.stackSize > 0 ) {
//                if( is.stackSize + remain <= is.getMaxStackSize() ) {
//                    is.stackSize += remain;
//                    remain = 0;
//                } else {
//                    remain -= (is.getMaxStackSize() - is.stackSize);
//                    is.stackSize = is.getMaxStackSize();
//                }
//            } else {
//                is = new ItemStack(this.prevFuelItem.getItem(), Math.min(this.prevFuelItem.getMaxStackSize(), remain),
//                                   this.prevFuelItem.getItemDamage());
//                remain -= Math.min(is.getMaxStackSize(), remain);
//            }
//            this.invItemStacks[i] = is.copy();
//        }
//
//        this.prevFuelItem = null;

        if( amount > 0 ) {
            this.markDirty();
        }
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

        if( par1nbtTagCompound.hasKey("prevFuelItem") ) {
            this.prevFuelItem = ItemStack.loadItemStackFromNBT(par1nbtTagCompound.getCompoundTag("prevFuelItem"));
        }

        NBTTagList var2 = par1nbtTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        this.invItemStacks = new ItemStack[this.getSizeInventory()];

        for( int var3 = 0; var3 < var2.tagCount(); ++var3 ) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);

            byte var5 = var4.getByte("Slot");

            if( var5 >= 0 && var5 < this.invItemStacks.length ) {
                this.invItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }

    @Override
    public boolean receiveClientEvent(int par1, int par2) {
        if( par1 == 1 ) {
            this.changeBiome(par2);
            return true;
        } else if( par1 == 2 ) {
            this.setCurrRange(par2);
            return true;
        }
        return false;
    }

    private boolean removeFuel(int amount) {
        int remain = amount;

        if( !this.isEnoughFuel(amount) ) {
            return false;
        }

        if( this.prevFuelItem != null ) {
            return true;
        }

        for( int i = this.invItemStacks.length - 1; i >= 0 && remain > 0; i-- ) {
            ItemStack is = this.invItemStacks[i] != null ? this.invItemStacks[i].copy() : null;
            if( is != null && is.stackSize > 0 ) {
                this.prevFuelItem = new ItemStack(is.getItem(), 1, is.getItemDamage());
                if( is.stackSize > remain ) {
                    is.stackSize -= remain;
                    remain = 0;
                } else {
                    remain -= is.stackSize;
                    is = null;
                }
            }
            this.invItemStacks[i] = is != null ? is.copy() : null;
        }

        this.markDirty();
        return true;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
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

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
        this.invItemStacks[var1] = var2;

        if( var2 != null && var2.stackSize > this.getInventoryStackLimit() ) {
            var2.stackSize = this.getInventoryStackLimit();
        }
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

        if( !this.isActive && this.prevIsActive ) {
            if( !this.worldObj.isRemote ) {
                this.readdFuel((this.getMaxRange() - this.getCurrRange()) * RegistryBiomeChanger.getMultiFromStack(this.prevFuelItem));
            }

            this.prevIsActive = false;
        } else if( !this.prevIsActive ) {
            if( !this.prevIsActive && !this.worldObj.isRemote ) {
                if( this.getNeededFuel(this.getMaxRange() - this.getCurrRange()) < 0
                    || !this.removeFuel(this.getNeededFuel(this.getMaxRange() - this.getCurrRange())) )
                {
                    this.isActive = false;
                    this.setCurrRange(0);
                    this.markDirty();
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                    return;
                } else {
                    this.prevIsActive = true;
                }
            }

            if( this.ticksExisted % (30 * (this.isReplacingBlocks ? 2 : 1)) == 0 && !this.worldObj.isRemote ) {
                this.changeBiome(this.getCurrRange());
//                IPacket packet = new PacketChangeBiome(this, this.getCurrRange());
//                TargetPoint tPoint = new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 256);
//                ESPModRegistry.channelHandler.sendToAllAround(packet, tPoint);

                this.setCurrRange(this.getCurrRange() + 1);

                if( this.getCurrRange() >= this.getMaxRange() ) {
                    this.isActive = false;
                    this.setCurrRange(0);
                    this.prevIsActive = false;
                    this.prevFuelItem = null;
                    this.markDirty();
                    this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, RegistryBlocks.biomeChanger, 2, 0);
                    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                    return;
                }

                this.markDirty();
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
        }
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

        if( this.prevFuelItem != null ) {
            NBTTagCompound var1 = new NBTTagCompound();
            this.prevFuelItem.writeToNBT(var1);
            par1nbtTagCompound.setTag("prevFuelItem", var1);
        }

        NBTTagList var2 = new NBTTagList();
        for( int var3 = 0; var3 < this.invItemStacks.length; ++var3 ) {
            if( this.invItemStacks[var3] != null ) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.invItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1nbtTagCompound.setTag("Items", var2);
    }

    public float getRenderBeamHeight() {
        return this.renderBeamHeight;
    }

    public void setRenderBeamHeight(float renderBeamHeight) {
        this.renderBeamHeight = renderBeamHeight;
    }

    public float getRenderBeamAngle() {
        return this.renderBeamAngle;
    }

    public void setRenderBeamAngle(float renderBeamAngle) {
        this.renderBeamAngle = renderBeamAngle;
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

    public void setReplacingBlocks(boolean isReplacingBlocks) {
        this.isReplacingBlocks = isReplacingBlocks;
    }

    public ItemStack getPrevFuelItem() {
        return this.prevFuelItem;
    }

    public static enum EnumPerimForm
    {
        CIRCLE, RHOMBUS, SQUARE;

        private static EnumPerimForm[] VALUES = values();
    }
}
