package de.sanandrew.mods.enderstuffp.entity.living;

import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager.CoatBaseEntry;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager.CoatColorEntry;
import net.minecraft.entity.EntityCreature;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public interface IEnderPet<E extends EntityCreature>
{
    public CoatBaseEntry getCoatBase();

    public CoatColorEntry getCoatColor();

    public E getEntity();

    public String getName();

//    public float getPetHealth();
//
//    public float getPetMaxHealth();

    public boolean isFollowing();

    public boolean isSitting();

    public void setFollowing(boolean b);

    public void setName(String name);

    public void setSitting(boolean b);

    public void readPetFromNBT(NBTTagCompound nbt);

    public void writePetToNBT(NBTTagCompound nbt);

    public void setTamed(boolean tame);

    public void setOwner(UUID owner);

    public String getGuiTitle();

    public int getGuiColor();

    public boolean canMount();
}
