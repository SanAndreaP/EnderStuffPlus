package de.sanandrew.mods.enderstuffp.entity.living;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.util.raincoat.RegistryRaincoats.CoatBaseEntry;
import de.sanandrew.mods.enderstuffp.util.raincoat.RegistryRaincoats.CoatColorEntry;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityCreature;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public interface IEnderPet<E extends EntityCreature>
{
    public CoatBaseEntry getCoatBase();

    public CoatColorEntry getCoatColor();

    public int getEggDmg();

    public E getEntity();

    public String getName();

    public float getPetHealth();

    public float getPetMaxHealth();

    public boolean isFollowing();

    public boolean isSitting();

    @SideOnly(Side.CLIENT)
    public void processRiding(EntityPlayerSP player);

    public void setFollowing(boolean b);

    public void setName(String name);

    public void setPetMoveForward(float f);

    public void setPetMoveStrafe(float f);

    public void setSitting(boolean b);

    public void readPetFromNBT(NBTTagCompound nbt);

    public void writePetToNBT(NBTTagCompound nbt);

    public void setTamed(boolean tame);

    public void setOwner(UUID owner);

    public String getGuiTitle();

    public int getGuiColor();

    public boolean canMount();
}
