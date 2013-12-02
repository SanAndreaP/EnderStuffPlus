package sanandreasp.mods.EnderStuffPlus.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;

public interface IEnderPet {
	public void setSitting(boolean b);
	public boolean isSitting();
	public void setFollowing(boolean b);
	public boolean isFollowing();
	public void setName(String s);
	public String getName();
	public EntityCreature getEntity();
	public int getEggDmg();
	public float getPetHealth();
	public float getPetMaxHealth();
	@SideOnly(Side.CLIENT)
	public void processRiding(EntityPlayerSP player);
	public void setPetMoveForward(float f);
	public void setPetMoveStrafe(float f);
	public int getCoatBaseColor();
}
