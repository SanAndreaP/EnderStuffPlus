package sanandreasp.mods.EnderStuffPlus.entity;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityRayball extends EntityFireball {
	public EntityRayball(World par1World) {
		super(par1World);
	}

	public EntityRayball(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, par8, par10, par12);
	}

	public EntityRayball(World par1World,
			EntityLiving par2EntityLiving, double par3, double par5, double par7) {
		super(par1World, par2EntityLiving, par3, par5, par7);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		this.extinguish();
		ESPModRegistry.sendPacketAllRng("fxRayball", this.posX, this.posY, this.posZ, 128.0D, this.dimension, this.posX,
				this.posY, this.posZ
		);
	}

	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		if( !this.worldObj.isRemote ) {
			if( par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 4) ) {
				if( (par1MovingObjectPosition.entityHit instanceof EntityLivingBase) && !(par1MovingObjectPosition.entityHit instanceof EntityEnderRay) ) {
					((EntityLivingBase) par1MovingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 0));
					((EntityLivingBase) par1MovingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.poison.id, 100, 0));
				}
			}

			this.worldObj.createExplosion((Entity) null, this.posX, this.posY, this.posZ, 1.0F, false);
			this.setDead();
		}
	}
}
