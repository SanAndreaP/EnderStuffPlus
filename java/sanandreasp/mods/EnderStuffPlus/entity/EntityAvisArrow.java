package sanandreasp.mods.EnderStuffPlus.entity;

import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityAvisArrow
    extends EntityArrow
{
    public EntityAvisArrow(World world) {
        super(world);
    }

    public EntityAvisArrow(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityAvisArrow(World world, EntityLivingBase shooter, EntityLivingBase target, float motionMulti,
                           float precision) {
        super(world, shooter, target, motionMulti, precision);
    }

    public EntityAvisArrow(World world, EntityLivingBase shooter, float motionMulti) {
        super(world, shooter, motionMulti);
    }

    public boolean _SAP_(EntityPlayer player) {
        return true;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        if( !this.worldObj.isRemote ) {
            NBTTagCompound origNBT = new NBTTagCompound();
            this.writeEntityToNBT(origNBT);

            if( origNBT.getByte("inGround") == 1
                && this.arrowShake <= 0
                && ((this.canBePickedUp == 1
                        && player.inventory.addItemStackToInventory(new ItemStack(ModItemRegistry.avisArrow, 1)))
                    || player.capabilities.isCreativeMode) )
            {
                this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F,
                                                ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    @Override
    public void onUpdate() {
        double prevMotionX = this.motionX;
        double prevMotionY = this.motionY;
        double prevMotionZ = this.motionZ;

        super.onUpdate();

        this.motionX = prevMotionX;
        this.motionY = prevMotionY * 0.99999F;
        this.motionZ = prevMotionZ;
    }
}
