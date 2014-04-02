package sanandreasp.mods.EnderStuffPlus.entity;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityCreature;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IEnderPet
{
    public String getCoatBase();

    public String getCoatColor();

    public int getEggDmg();

    public EntityCreature getEntity();

    public String getName();

    public float getPetHealth();

    public float getPetMaxHealth();

    public boolean isFollowing();

    public boolean isSitting();

    @SideOnly(Side.CLIENT)
    public void processRiding(EntityPlayerSP player);

    public void setFollowing(boolean b);

    public void setName(String s);

    public void setPetMoveForward(float f);

    public void setPetMoveStrafe(float f);

    public void setSitting(boolean b);

    public void readPetFromNBT(NBTTagCompound nbt);

    public void writePetToNBT(NBTTagCompound nbt);

    public void setTamed(boolean tame);

    public void setOwnerName(String ownerName);
}
