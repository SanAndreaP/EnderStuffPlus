package de.sanandrew.mods.enderstuffplus.entity.living.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.sanandrew.core.manpack.util.SAPReflectionHelper;
import de.sanandrew.mods.enderstuffplus.entity.living.IEnderCreature;
import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class EntityEnderNemesis
    extends EntityEnderman
    implements IEnderCreature
{

    public EntityEnderNemesis(World world) {
        super(world);
        this.isImmuneToFire = true;
    }

    @Override
    protected void dropRareDrop(int par1) {
        this.dropItem(this.dataWatcher.getWatchableObjectItemStack(20).getItem(), 1);
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        ItemStack holdItm = new ItemStack(ModItemRegistry.niobSword);
        switch( this.rand.nextInt(8) ){
            case 4 :
                holdItm = new ItemStack(ModItemRegistry.niobPick);
                break;
            case 5 :
                holdItm = new ItemStack(ModItemRegistry.niobAxe);
                break;
            case 6 :
                holdItm = new ItemStack(ModItemRegistry.niobShovel);
                break;
            case 7 :
                holdItm = new ItemStack(ModItemRegistry.niobHoe);
                break;
        }
        this.dataWatcher.addObject(20, holdItm);
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.checkNoEntityCollision(this.boundingBox)
               && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()
               && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

    @Override
    protected Item getDropItem() {
        return null;
    }

    @Override
    public ItemStack getHeldItem() {
        return this.dataWatcher.getWatchableObjectItemStack(20);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getItemIcon(ItemStack par1ItemStack, int par2) {
        return par1ItemStack.getItem().getIcon(par1ItemStack, par2);
    }

    @Override
    protected void onDeathUpdate() {
        this.deathTime = 19;
        super.onDeathUpdate();
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        if( par1nbtTagCompound.hasKey("CarryingWeapon") ) {
            this.dataWatcher.updateObject(20, ItemStack.loadItemStackFromNBT((NBTTagCompound) par1nbtTagCompound.getTag("CarryingWeapon")));
        }
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 24.0D);

        if (entityplayer != null)
        {
            //FIXME find SRG name!
            SAPReflectionHelper.setCachedFieldValue(EntityEnderman.class, this, "isAggressive", "func_xxxx_xx", true);
            this.worldObj.playSoundEffect(entityplayer.posX, entityplayer.posY, entityplayer.posZ, "mob.endermen.stare", 1.0F, 1.0F);
            this.setScreaming(true);
            return entityplayer;
        }

        return null;
    }

    @Override
    protected boolean teleportTo(double par1, double par3, double par5) {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource dmgSource, float dmg) {
        if( dmgSource.equals(DamageSource.drown) && this.getAir() > -20 ) {
            return false;
        }
        return super.attackEntityFrom(dmgSource, dmg);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeEntityToNBT(par1nbtTagCompound);
        par1nbtTagCompound.setTag("CarryingWeapon", this.dataWatcher.getWatchableObjectItemStack(20).writeToNBT(new NBTTagCompound()));
    }
}
