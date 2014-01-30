package sanandreasp.mods.EnderStuffPlus.entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

public class EntityAvisArrow extends EntityArrow {
	
	protected boolean inGround = false;

	public EntityAvisArrow(World par1World) {
		super(par1World);
	}

	public EntityAvisArrow(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

	public EntityAvisArrow(World par1World, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving, float par4, float par5) {
		super(par1World, par2EntityLiving, par3EntityLiving, par4, par5);
	}

	public EntityAvisArrow(World par1World, EntityLivingBase par2EntityLiving, float par3) {
		super(par1World, par2EntityLiving, par3);
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
	

    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
        if( !this.worldObj.isRemote )
        {
            if( this.inGround && this.arrowShake <= 0 && (
            		(this.canBePickedUp == 1 && par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(ESPModRegistry.avisArrow, 1)))
            		||
            		par1EntityPlayer.capabilities.isCreativeMode
            ) )
            {
                this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                par1EntityPlayer.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }
}
