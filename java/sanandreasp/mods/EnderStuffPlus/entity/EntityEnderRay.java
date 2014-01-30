package sanandreasp.mods.EnderStuffPlus.entity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import sanandreasp.mods.EnderStuffPlus.client.registry.Textures;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

public class EntityEnderRay extends EntityFlying implements IMob, IEnderCreature, Textures {

	public int courseChangeCooldown = 0;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	public boolean hasSpecialTexture = false;
	private Entity targetedEntity = null;
	private Entity attackedEntity = null;
	private int aggroCooldown = 0;
	public int prevAttackCounter = 0;
	public int attackCounter = 0;

	public EntityEnderRay(World par1World) {
		super(par1World);
		this.setSize(6.0F, 1.0F);
		this.hasSpecialTexture = this.rand.nextInt(2) == 0;
		this.experienceValue = 10;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.3D);
	}
	
	@Override
	public void onUpdate() {
		if( this.worldObj.difficultySetting == 0 && !this.worldObj.isRemote )
			this.setDead();
		
		super.onUpdate();
	}

	@Override
	protected void updateEntityActionState() {
		EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 256);
		
		if( player != null && this.getDistanceToEntity(player) > 64 ) {
			this.courseChangeCooldown = 0;
			this.waypointX = player.posX;
			this.waypointY = player.posY + 7F;
			this.waypointZ = player.posZ;
		}

		double var1 = this.waypointX - this.posX;
		double var3 = this.waypointY - this.posY;
		double var5 = this.waypointZ - this.posZ;
		double var7 = MathHelper.sqrt_double(var1 * var1 + var3 * var3
				+ var5 * var5);

		if( var7 < 1.0D || var7 > 60.0D ) {
			this.waypointX = this.posX + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
			this.waypointY = this.posY + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
			this.waypointZ = this.posZ + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
		}

		if( this.courseChangeCooldown-- <= 0 ) {
			this.courseChangeCooldown += this.rand.nextInt(5) + 2;

			if( this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, var7) ) {
				this.motionX += var1 / var7 * 0.1D;
				this.motionY += var3 / var7 * 0.1D;
				this.motionZ += var5 / var7 * 0.1D;
			} else {
				this.waypointX = this.posX;
				this.waypointY = this.posY;
				this.waypointZ = this.posZ;
			}
		}

		if( this.targetedEntity != null && this.targetedEntity.isDead ) {
			this.targetedEntity = null;
		}

		if( this.targetedEntity == null || this.aggroCooldown-- <= 0 ) {
			this.targetedEntity = this.worldObj.getClosestVulnerablePlayerToEntity(this, 100.0D);

			if( this.targetedEntity != null ) {
				this.aggroCooldown = 20;
			}
		}

		if( this.attackedEntity != null && this.attackedEntity.isDead ) {
			this.attackedEntity = null;
		}

		if( this.attackedEntity != null && !this.attackedEntity.isDead ) {
			this.targetedEntity = this.attackedEntity;

			if( this.targetedEntity != null ) {
				this.aggroCooldown = 20;
			}
		}

		double var9 = 64.0D;

		if( this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < var9 * var9 ) {
			double var11 = this.targetedEntity.posX - this.posX;
			double var13 = this.targetedEntity.boundingBox.minY + this.targetedEntity.height / 2.0F - (this.posY + this.height / 2.0F);
			double var15 = this.targetedEntity.posZ - this.posZ;
			this.renderYawOffset = this.rotationYaw = -((float) Math.atan2(var11, var15)) * 180.0F / (float) Math.PI;

			if( this.canEntityBeSeen(this.targetedEntity) ) {
				if( this.attackCounter == 10 ) {
					this.worldObj.playSoundEffect(
							this.posX + 0.5D,
							this.posY + 0.5D,
							this.posZ + 0.5D,
							"enderstuffp:enderray.charge", 10.0F,
							(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1F
					);
				}

				++this.attackCounter;

				if( this.attackCounter == 20 ) {
					this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1008, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
					EntityFireball var17 = new EntityRayball(this.worldObj, this, var11, var13, var15);
					double var18 = 4.0D;
					Vec3 var20 = this.getLook(1.0F);
					var17.posX = this.posX + var20.xCoord * var18;
					var17.posY = this.posY + this.height / 2.0F + 0.5D;
					var17.posZ = this.posZ + var20.zCoord * var18;
					this.worldObj.spawnEntityInWorld(var17);
					this.attackCounter = -40;
				}
			} else if( this.attackCounter > 0 ) {
				--this.attackCounter;
			}
		} else {
			this.renderYawOffset = this.rotationYaw = -((float) Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float) Math.PI;

			if( this.attackCounter > 0 ) {
				--this.attackCounter;
			}
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if( par1DamageSource.getSourceOfDamage() instanceof EntityPlayer ) {
			this.attackedEntity = par1DamageSource.getSourceOfDamage();
		}

		if( par1DamageSource.getSourceOfDamage() instanceof EntityArrow ) {
			this.attackedEntity = ((EntityArrow) par1DamageSource.getSourceOfDamage()).shootingEntity;
		}

		return super.attackEntityFrom(par1DamageSource, par2);
	}

	private boolean isCourseTraversable(double par1, double par3, double par5, double par7) {
		double var9 = (this.waypointX - this.posX) / par7;
		double var11 = (this.waypointY - this.posY) / par7;
		double var13 = (this.waypointZ - this.posZ) / par7;
		AxisAlignedBB var15 = this.boundingBox.copy();

		for( int var16 = 1; var16 < par7; ++var16 ) {
			var15.offset(var9, var11, var13);

			if( this.worldObj.getCollidingBoundingBoxes(this, var15).size() > 0 ) {
				return false;
			}
		}

		return true;
	}

	@Override
	protected String getLivingSound() {
		return "enderstuffp:enderray.moan";
	}

	@Override
	protected String getHurtSound() {
		return "enderstuffp:enderray.scream";
	}

	@Override
	protected String getDeathSound() {
		return "enderstuffp:enderray.death";
	}

	@Override
	protected float getSoundVolume() {
		return 10.0F;
	}

	@Override
	public boolean getCanSpawnHere() {
		return this.rand.nextInt(10) == 0 && super.getCanSpawnHere()
				&& this.worldObj.difficultySetting > 0
				&& !this.checkIfEntitiesExist(this.getClass(), 2);
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	private boolean checkIfEntitiesExist(Class<? extends Entity> entityClass, int maxCount) {
		int cnt = 0;
		Iterator<? extends Entity> entities = this.worldObj.loadedEntityList.iterator();
		
		while( entities.hasNext() ) {
			Entity entity = entities.next();
			if( entity.getClass().isAssignableFrom(entityClass) ) {
				cnt++;
				if( cnt > maxCount )
					return true;
			}
		}
		return false;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int var3 = ESPModRegistry.enderFlesh.itemID;
		int damage = this.rand.nextInt(5) == 0 ? 1 : 0;

		if( var3 > 0 ) {
			int var4 = this.rand.nextInt(3);

			if( par2 > 0 ) {
				var4 += this.rand.nextInt(par2 + 1);
			}

			for( int var5 = 0; var5 < var4; ++var5 ) {
				this.entityDropItem(new ItemStack(var3, 1, damage), 0.0F);
			}
		}
	}

	@Override
	protected void dropRareDrop(int par1) {
		List<ItemStack> rareItems = new ArrayList<ItemStack>();

		rareItems.add(new ItemStack(Item.swordDiamond, 1));
		rareItems.add(new ItemStack(Item.helmetDiamond, 1));
		rareItems.add(new ItemStack(Item.diamond, this.rand.nextInt(3) + 1));
		rareItems.add(new ItemStack(Item.shovelDiamond, 1));
		rareItems.add(new ItemStack(Item.pickaxeDiamond, 1));
		rareItems.add(new ItemStack(Item.bootsDiamond, 1));
		rareItems.add(new ItemStack(ESPModRegistry.enderFlesh, 1, 2));

		this.entityDropItem(rareItems.get(this.rand.nextInt(rareItems.size())), 0.0F);
	}
}
