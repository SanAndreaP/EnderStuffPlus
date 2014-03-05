package sanandreasp.mods.EnderStuffPlus.entity;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

public class EntityEndermanESP extends EntityMob {
    private static final AttributeModifier attackingSpeedBoostModifier = (new AttributeModifier(UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0"), "Attacking speed boost", 6.199999809265137D, 0)).setSaved(false);
    public static boolean[] carriableBlocks = new boolean[Block.blocksList.length];

	public boolean isImmuneToWater = false;
	public boolean canSpawn = false;
    /**
     * Counter to delay the teleportation of an enderman towards the currently attacked target
     */
    protected int teleportDelay;
    protected int jarOpeningCounter;
    protected Entity prevTarget;
    protected boolean hasTarget;
    
    public boolean calmInDaylight = true;
    
    public static final int TP_ATTACK = 0;
    public static final int TP_LIQUID = 1;
    public static final int TP_DAYTIME = 2;
    public static final int TP_PROJECTILE = 3;

    public EntityEndermanESP(World par1World) {
        super(par1World);
        this.setSize(0.6F, 2.9F);
        this.stepHeight = 1.0F;
		
		{
			EntityEndermanESP.carriableBlocks[Block.grass.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.dirt.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.sand.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.gravel.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.plantYellow.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.plantRed.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.mushroomBrown.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.mushroomRed.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.tnt.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.cactus.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.blockClay.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.pumpkin.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.melon.blockID] = ConfigRegistry.griefing;
			EntityEndermanESP.carriableBlocks[Block.mycelium.blockID] = ConfigRegistry.griefing;
		}
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.30000001192092896D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(7.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
        this.dataWatcher.addObject(17, new Byte((byte)0));
        this.dataWatcher.addObject(18, new Byte((byte)0));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("carried", (short)this.getCarried());
        par1NBTTagCompound.setShort("carriedData", (short)this.getCarryingData());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setCarried(par1NBTTagCompound.getShort("carried"));
        this.setCarryingData(par1NBTTagCompound.getShort("carriedData"));
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 64.0D);

        if( entityplayer != null ) {
            if( this.shouldAttackPlayer(entityplayer) ) {
                this.hasTarget = true;

                if( this.jarOpeningCounter == 0 ) {
                    this.worldObj.playSoundAtEntity(entityplayer, getScreamSound(), 1.0F, 1.0F);
                }

                if( this.jarOpeningCounter++ == 5 ) {
                    this.jarOpeningCounter = 0;
                    this.setScreaming(true);
                    return entityplayer;
                }
            } else {
                this.jarOpeningCounter = 0;
            }
        }

        return null;
    }

    protected boolean shouldAttackPlayer(EntityPlayer par1EntityPlayer) {
    	if( ESPModRegistry.hasPlayerFullNiob(par1EntityPlayer) )
    		return false;
    	
        ItemStack itemstack = par1EntityPlayer.inventory.armorInventory[3];

        if( itemstack != null && itemstack.itemID == Block.pumpkin.blockID ) {
            return false;
        } else {
            Vec3 vec3 = par1EntityPlayer.getLook(1.0F).normalize();
            Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - par1EntityPlayer.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - (par1EntityPlayer.posY + (double)par1EntityPlayer.getEyeHeight()), this.posZ - par1EntityPlayer.posZ);
            double d0 = vec31.lengthVector();
            vec31 = vec31.normalize();
            double d1 = vec3.dotProduct(vec31);
            return d1 > 1.0D - 0.025D / d0 ? par1EntityPlayer.canEntityBeSeen(this) : false;
        }
    }
    
    public void spawnParticle(String type, double X, double Y, double Z, float dataI, float dataII, float dataIII) {
		ESPModRegistry.sendPacketAllRng(
				"fxPortal", this.posX, this.posY, this.posZ, 128.0D, this.dimension, this.posX, this.posY,
				this.posZ, dataI, dataII, dataIII, this.width, this.height);
    }

    @Override
    public void onLivingUpdate() {
        if( this.isWet() && !this.isImmuneToWater ) {
            this.attackEntityFrom(DamageSource.drown, 1.0F);
        }

        if( this.prevTarget != this.entityToAttack ) {
            AttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            attributeinstance.removeModifier(attackingSpeedBoostModifier);

            if( this.entityToAttack != null ) {
                attributeinstance.applyModifier(attackingSpeedBoostModifier);
            }
        }

        this.prevTarget = this.entityToAttack;
        int i;

        if( !this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") ) {
            int j;
            int k;
            int l;

            if( this.getCarried() == 0 ) {
                if( this.rand.nextInt(20) == 0 ) {
                    i = MathHelper.floor_double(this.posX - 2.0D + this.rand.nextDouble() * 4.0D);
                    j = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 3.0D);
                    k = MathHelper.floor_double(this.posZ - 2.0D + this.rand.nextDouble() * 4.0D);
                    l = this.worldObj.getBlockId(i, j, k);

                    if( carriableBlocks[l] ) {
                        this.setCarried(this.worldObj.getBlockId(i, j, k));
                        this.setCarryingData(this.worldObj.getBlockMetadata(i, j, k));
                        this.worldObj.setBlock(i, j, k, 0);
                    }
                }
            } else if( this.rand.nextInt(2000) == 0 ) {
                i = MathHelper.floor_double(this.posX - 1.0D + this.rand.nextDouble() * 2.0D);
                j = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 2.0D);
                k = MathHelper.floor_double(this.posZ - 1.0D + this.rand.nextDouble() * 2.0D);
                l = this.worldObj.getBlockId(i, j, k);
                int i1 = this.worldObj.getBlockId(i, j - 1, k);

                if( l == 0 && i1 > 0 && Block.blocksList[i1].renderAsNormalBlock() ) {
                    this.worldObj.setBlock(i, j, k, this.getCarried(), this.getCarryingData(), 3);
                    this.setCarried(0);
                }
            }
        }

        this.spawnParticle("livingUpd", this.posX, this.posY, this.posZ, 0.0F, 0.0F, 0.0F);

        if( this.worldObj.isDaytime() && !this.worldObj.isRemote && this.calmInDaylight ) {
            float f = this.getBrightness(1.0F);

            if( f > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F ) {
                this.entityToAttack = null;
                this.setScreaming(false);
                this.hasTarget = false;
                this.teleportRandomly(EntityEndermanESP.TP_DAYTIME);
            }
        }

        if( (this.isWet() && !this.isImmuneToWater) || (this.isBurning() && !this.isImmuneToFire) ) {
            this.entityToAttack = null;
            this.setScreaming(false);
            this.hasTarget = false;
            this.teleportRandomly(EntityEndermanESP.TP_LIQUID);
        }

        if( this.isScreaming() && !this.hasTarget && this.rand.nextInt(100) == 0 ) {
            this.setScreaming(false);
        }

        this.isJumping = false;

        if( this.entityToAttack != null ) {
            this.faceEntity(this.entityToAttack, 100.0F, 100.0F);
        }

        if( !this.worldObj.isRemote && this.isEntityAlive() ) {
            if( this.entityToAttack != null ) {
                if( this.entityToAttack instanceof EntityPlayer && this.shouldAttackPlayer((EntityPlayer)this.entityToAttack) ) {
                    if( this.entityToAttack.getDistanceSqToEntity(this) < 16.0D ) {
                        this.teleportRandomly(EntityEndermanESP.TP_ATTACK);
                    }

                    this.teleportDelay = 0;
                } else if( this.entityToAttack.getDistanceSqToEntity(this) > 256.0D && this.teleportDelay++ >= 30 && this.teleportToEntity(EntityEndermanESP.TP_ATTACK, this.entityToAttack) ) {
                    this.teleportDelay = 0;
                }
            } else {
                this.setScreaming(false);
                this.teleportDelay = 0;
            }
        }

        super.onLivingUpdate();
    }

    protected boolean teleportRandomly(int cause) {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.teleportTo(cause, d0, d1, d2);
    }

    protected boolean teleportToEntity(int cause, Entity par1Entity) {
        Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - par1Entity.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - par1Entity.posY + (double)par1Entity.getEyeHeight(), this.posZ - par1Entity.posZ);
        vec3 = vec3.normalize();
        double d0 = 16.0D;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
        return this.teleportTo(cause, d1, d2, d3);
    }

    protected boolean teleportTo(int cause, double par1, double par3, double par5) {
        EnderTeleportEvent event = new EnderTeleportEvent(this, par1, par3, par5, 0);
        if( MinecraftForge.EVENT_BUS.post(event) ) {
            return false;
        }

        double d3 = this.posX;
        double d4 = this.posY;
        double d5 = this.posZ;
        this.posX = event.targetX;
        this.posY = event.targetY;
        this.posZ = event.targetZ;
        boolean flag = false;
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posY);
        int k = MathHelper.floor_double(this.posZ);
        int l;

        if( this.worldObj.blockExists(i, j, k)) {
            boolean flag1 = false;

            while( !flag1 && j > 0 ) {
                l = this.worldObj.getBlockId(i, j - 1, k);

                if( l != 0 && Block.blocksList[l].blockMaterial.blocksMovement() ) {
                    flag1 = true;
                } else {
                    --this.posY;
                    --j;
                }
            }

            if( flag1 ) {
                this.setPosition(this.posX, this.posY, this.posZ);

                if( this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && (!this.worldObj.isAnyLiquid(this.boundingBox) || this.isImmuneToWater) ) {
                    flag = true;
                }
            }
        }

        if( !flag ) {
            this.setPosition(d3, d4, d5);
            return false;
        } else {
            short short1 = 128;

            for( l = 0; l < short1; ++l ) {
                double d6 = (double)l / ((double)short1 - 1.0D);
                double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * (double)this.height;
                double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                this.spawnParticle("teleport", d7, d8, d9, 0.0F, 0.0F, 0.0F);
            }

            this.worldObj.playSoundEffect(d3, d4, d5, this.getPortalSound(), 1.0F, 1.0F);
            this.playSound(this.getPortalSound(), 1.0F, 1.0F);
            return true;
        }
    }
    
    protected String getPortalSound() {
    	return "mob.endermen.portal";
    }
    
    protected String getScreamSound() {
    	return "mob.endermen.stare";
    }

    @Override
    protected String getLivingSound() {
        return this.isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
    }

    @Override
    protected String getHurtSound() {
        return "mob.endermen.hit";
    }

    @Override
    protected String getDeathSound() {
        return "mob.endermen.death";
    }

    @Override
    protected int getDropItemId() {
        return Item.enderPearl.itemID;
    }
    
    protected int getDamageDropped() {
    	return 0;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int j = this.getDropItemId();

        if( j > 0 ) {
            int k = this.rand.nextInt(2 + par2);

            for( int l = 0; l < k; ++l ) {
            	this.entityDropItem(new ItemStack(j, 1, this.getDamageDropped()), 0.0F);
            }
        }
    }

    public void setCarried(int par1) {
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)(par1 & 255)));
    }

    public int getCarried() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    public void setCarryingData(int par1) {
        this.dataWatcher.updateObject(17, Byte.valueOf((byte)(par1 & 255)));
    }

    public int getCarryingData() {
        return this.dataWatcher.getWatchableObjectByte(17);
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    	if( par1DamageSource.equals(DamageSource.drown) && this.isImmuneToWater )
    		return false;
    	
        if( this.isEntityInvulnerable() ) {
            return false;
        } else {
            this.setScreaming(true);

            if( par1DamageSource instanceof EntityDamageSource && par1DamageSource.getEntity() instanceof EntityPlayer ) {
                this.hasTarget = true;
            }

            if( par1DamageSource instanceof EntityDamageSourceIndirect ) {
                this.hasTarget = false;

                for( int i = 0; i < 64; ++i ) {
                    if( this.teleportRandomly(TP_PROJECTILE) ) {
                        return true;
                    }
                }

                return super.attackEntityFrom(par1DamageSource, par2);
            } else {
                return super.attackEntityFrom(par1DamageSource, par2);
            }
        }
    }

    public boolean isScreaming() {
        return this.dataWatcher.getWatchableObjectByte(18) > 0;
    }

    public void setScreaming(boolean par1) {
        this.dataWatcher.updateObject(18, Byte.valueOf((byte)(par1 ? 1 : 0)));
    }
    
    @Override
    public boolean getCanSpawnHere() {
    	return super.getCanSpawnHere() || this.canSpawn;
    }
}
