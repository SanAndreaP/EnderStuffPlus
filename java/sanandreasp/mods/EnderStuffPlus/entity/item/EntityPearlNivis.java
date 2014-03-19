package sanandreasp.mods.EnderStuffPlus.entity.item;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPearlNivis extends EntityThrowable
{
    public EntityPearlNivis(World world) {
        super(world);
    }
    
    public EntityPearlNivis(World world, EntityLivingBase livingBase) {
        super(world, livingBase);
    }

    public EntityPearlNivis(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjPos) {
        if( movingObjPos.entityHit != null ) {
            movingObjPos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }
        
        for( int i = 0; i < 8; i++ ) {
        	ESPModRegistry.sendPacketAllRng("fxPortal", this.posX, this.posY, this.posZ, 128D, this.dimension, this.posX, this.posY, this.posZ, 0.2F, 0.5F, 1.0F, this.width, this.height);
        }

        if( !this.worldObj.isRemote ) {
            if( movingObjPos.typeOfHit == EnumMovingObjectType.TILE && this.getThrower() instanceof EntityPlayer ) {
                int var13 = movingObjPos.blockX;
                int var14 = movingObjPos.blockY;
                int var15 = movingObjPos.blockZ;
        
                if( !this.worldObj.canMineBlock((EntityPlayer)this.getThrower(), var13, var14, var15) ) {
                    this.setDead();
                    return;
                }
    
                boolean playSound = false;
    
                for( int i = -3; i <= 3; i++ ) {
                    for( int j = -3; j <= 3; j++ ) {
                        for( int k = -3; k <= 3; k++ ) {
                            if( Math.sqrt(i*i + j*j + k*k) > 3.4D ) {
                                continue;
                            }
                            
                            int blockID = this.worldObj.getBlockId(var13 + i, var14 + j, var15 + k);
                            
                            if( (blockID == Block.waterStill.blockID || blockID == Block.waterMoving.blockID) && this.worldObj.getBlockMetadata(var13 + i, var14 + j, var15 + k) == 0 ) {
                                this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Block.ice.blockID);
                            } else if( (blockID == Block.lavaStill.blockID || blockID == Block.lavaMoving.blockID) && this.worldObj.getBlockMetadata(var13 + i, var14 + j, var15 + k) == 0 ) {
                                this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Block.obsidian.blockID);
                            } else if( (blockID == Block.lavaStill.blockID || blockID == Block.lavaMoving.blockID) ) {
                                this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Block.cobblestone.blockID);
                            } else if( this.worldObj.isBlockNormalCube(var13 + i, var14 + j, var15 + k) && this.worldObj.isAirBlock(var13 + i, var14 + j + 1, var15 + k) ) {
                                this.worldObj.setBlock(var13 + i, var14 + j + 1, var15 + k, Block.snow.blockID);
                            } else if( blockID == Block.fire.blockID ) {
                                this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, 0);
                                playSound = true;
                            }
                        }
                    }
                }
                if( playSound ) {
                    this.worldObj.playSoundEffect(var13, var14, var15, "random.fizz", 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                }
            } else if( movingObjPos.typeOfHit == EnumMovingObjectType.ENTITY && movingObjPos.entityHit != null ) {
            	if( movingObjPos.entityHit instanceof EntityLivingBase ) {
            		((EntityLivingBase)movingObjPos.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 300, 0));
            	}
                movingObjPos.entityHit.extinguish();
            }
            
            this.setDead();
        }
    }
    
    public boolean _SAP_canImpactOnLiquid() {
        return true;
    }

}
