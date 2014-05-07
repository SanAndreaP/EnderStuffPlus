package de.sanandrew.mods.enderstuffplus.entity.living.monster;

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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.enderstuffplus.entity.EntityRayball;
import de.sanandrew.mods.enderstuffplus.entity.living.IEnderCreature;
import de.sanandrew.mods.enderstuffplus.registry.ModItemRegistry;

public class EntityEnderRay
    extends EntityFlying
    implements IMob, IEnderCreature
{

    private int aggroCooldown = 0;
    private int attackCounter = 0;
    private Entity attackedEntity = null;
    private int courseChangeCooldown = 0;
    private boolean hasSpecialTexture = false;
    private Entity targetedEntity = null;
    private double waypointX;
    private double waypointY;
    private double waypointZ;

    private static final List<Triplet<? extends Item, Integer, Integer>> RARE_DROPS = new ArrayList<>();

    static {
        RARE_DROPS.add(Triplet.with(Items.diamond_sword, 1, 0));
        RARE_DROPS.add(Triplet.with(Items.diamond_helmet, 1, 0));
        RARE_DROPS.add(Triplet.with(Items.diamond, 3, 0));
        RARE_DROPS.add(Triplet.with(Items.diamond_shovel, 1, 0));
        RARE_DROPS.add(Triplet.with(Items.diamond_pickaxe, 1, 0));
        RARE_DROPS.add(Triplet.with(Items.diamond_boots, 1, 0));
        RARE_DROPS.add(Triplet.with(ModItemRegistry.enderFlesh, 2, 3));
    }

    public EntityEnderRay(World world) {
        super(world);
        this.setSize(6.0F, 1.0F);
        this.hasSpecialTexture = this.rand.nextInt(2) == 0;
        this.experienceValue = 10;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }

    @Override
    public boolean attackEntityFrom(DamageSource dmgSource, float attackPts) {
        if( dmgSource.getSourceOfDamage() instanceof EntityPlayer ) {
            this.attackedEntity = dmgSource.getSourceOfDamage();
        }

        if( dmgSource.getSourceOfDamage() instanceof EntityArrow ) {
            this.attackedEntity = ((EntityArrow) dmgSource.getSourceOfDamage()).shootingEntity;
        }

        return super.attackEntityFrom(dmgSource, attackPts);
    }

    @SuppressWarnings("unchecked")
    private boolean checkIfEntitiesExist(Class<? extends Entity> entityClass, int maxCount) {
        int cnt = 0;
        Iterator<? extends Entity> entities = this.worldObj.loadedEntityList.iterator();

        while( entities.hasNext() ) {
            Entity entity = entities.next();
            if( entity.getClass().isAssignableFrom(entityClass) ) {
                cnt++;
                if( cnt > maxCount ) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void dropFewItems(boolean recentlyHit, int fortuneLevel) {
        int dropCount = this.rand.nextInt(3);

        if( fortuneLevel > 0 ) {
            dropCount += this.rand.nextInt(fortuneLevel + 1);
        }

        for( int i = 0; i < dropCount; i++ ) {
            this.entityDropItem(new ItemStack(ModItemRegistry.enderFlesh, 1, this.rand.nextInt(5) == 0 ? 1 : 0), 0.0F);
        }
    }

    @Override
    protected void dropRareDrop(int shouldDefDrop) {
        Triplet<? extends Item, Integer, Integer> randItem = RARE_DROPS.get(this.rand.nextInt(RARE_DROPS.size()));
        this.entityDropItem(new ItemStack(randItem.getValue0(), this.rand.nextInt(randItem.getValue1()), this.rand.nextInt(randItem.getValue2() + 1)), 0.0F);
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.rand.nextInt(10) == 0 && super.getCanSpawnHere() && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL
               && !this.checkIfEntitiesExist(this.getClass(), 2);
    }

    @Override
    protected String getDeathSound() {
        return "enderstuffp:enderray.death";
    }

    @Override
    protected String getHurtSound() {
        return "enderstuffp:enderray.scream";
    }

    @Override
    protected String getLivingSound() {
        return "enderstuffp:enderray.moan";
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    protected float getSoundVolume() {
        return 10.0F;
    }

    private boolean isCourseTraversable(double waypointX, double waypointY, double waypointZ, double posDeltaVec) {
        double wayptDeltaX = (waypointX - this.posX) / posDeltaVec;
        double wayptDeltaY = (waypointY - this.posY) / posDeltaVec;
        double wayptDeltaZ = (waypointZ - this.posZ) / posDeltaVec;
        AxisAlignedBB myBoundingBox = this.boundingBox.copy();

        for( int var16 = 1; var16 < posDeltaVec; ++var16 ) {
            myBoundingBox.offset(wayptDeltaX, wayptDeltaY, wayptDeltaZ);

            if( this.worldObj.getCollidingBoundingBoxes(this, myBoundingBox).size() > 0 ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void onUpdate() {
        if( this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && !this.worldObj.isRemote ) {
            this.setDead();
        }

        super.onUpdate();
    }

    @Override
    protected void updateEntityActionState() {
        EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 256);

        if( player != null && this.getDistanceToEntity(player) > 64 ) {
            this.courseChangeCooldown = 0;
            this.waypointX = player.posX;
            this.waypointY = player.posY + 7F;
            this.waypointZ = player.posZ;
        }

        double posDeltaX = this.waypointX - this.posX;
        double posDeltaY = this.waypointY - this.posY;
        double posDeltaZ = this.waypointZ - this.posZ;
        double posDeltaVec = MathHelper.sqrt_double(posDeltaX * posDeltaX + posDeltaY * posDeltaY + posDeltaZ * posDeltaZ);

        if( posDeltaVec < 1.0D || posDeltaVec > 60.0D ) {
            this.waypointX = this.posX + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
            this.waypointY = this.posY + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
            this.waypointZ = this.posZ + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
        }

        if( this.courseChangeCooldown-- <= 0 ) {
            this.courseChangeCooldown += this.rand.nextInt(5) + 2;

            if( this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, posDeltaVec) ) {
                this.motionX += posDeltaX / posDeltaVec * 0.1D;
                this.motionY += posDeltaY / posDeltaVec * 0.1D;
                this.motionZ += posDeltaZ / posDeltaVec * 0.1D;
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

        if( this.targetedEntity != null && this.targetedEntity.getDistanceToEntity(this) < 64.0D ) {
            posDeltaX = this.targetedEntity.posX - this.posX;
            posDeltaY = this.targetedEntity.boundingBox.minY + this.targetedEntity.height / 2.0F - (this.posY + this.height / 2.0F);
            posDeltaZ = this.targetedEntity.posZ - this.posZ;
            this.rotationYaw = -((float) Math.atan2(posDeltaX, posDeltaZ)) * 180.0F / (float) Math.PI;
            this.renderYawOffset = this.rotationYaw;

            if( this.canEntityBeSeen(this.targetedEntity) ) {
                if( this.attackCounter == 10 ) {
                    this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "enderstuffp:enderray.charge",
                                                  10.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1F);
                }

                this.attackCounter++;

                if( this.attackCounter == 20 ) {
                    this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1008, (int) this.posX, (int) this.posY, (int) this.posZ, 0);

                    EntityFireball projectile = new EntityRayball(this.worldObj, this, posDeltaX, posDeltaY, posDeltaZ);
                    double vectorMulti = 4.0D;
                    Vec3 lookVector = this.getLook(1.0F);
                    projectile.posX = this.posX + lookVector.xCoord * vectorMulti;
                    projectile.posY = this.posY + this.height / 2.0F + 0.5D;
                    projectile.posZ = this.posZ + lookVector.zCoord * vectorMulti;
                    this.worldObj.spawnEntityInWorld(projectile);
                    this.attackCounter = -40;
                }
            } else if( this.attackCounter > 0 ) {
                --this.attackCounter;
            }
        } else {
            this.rotationYaw = -((float) Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float) Math.PI;
            this.renderYawOffset = this.rotationYaw;

            if( this.attackCounter > 0 ) {
                --this.attackCounter;
            }
        }
    }

    public boolean hasSpecialTexture() {
        return this.hasSpecialTexture;
    }
}
