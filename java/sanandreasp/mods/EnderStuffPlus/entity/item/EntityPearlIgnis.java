package sanandreasp.mods.EnderStuffPlus.entity.item;

import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class EntityPearlIgnis extends EntityThrowable
{
    public EntityPearlIgnis(World world) {
        super(world);
    }
    
    public EntityPearlIgnis(World world, EntityLivingBase livingBase) {
        super(world, livingBase);
    }

    public EntityPearlIgnis(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjPos) {
        if( movingObjPos.entityHit != null ) {
            movingObjPos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }
        
        for( int i = 0; i < 8; i++ ) {
        	ESPModRegistry.sendPacketAllRng("fxPortal", this.posX, this.posY, this.posZ, 128D, this.dimension, this.posX, this.posY, this.posZ, 1.0F, 0.3F, 0.0F, this.width, this.height);
        }
		
        if( !this.worldObj.isRemote ) {
            if( movingObjPos.typeOfHit == EnumMovingObjectType.TILE && this.getThrower() instanceof EntityPlayer ) {
                int movX = movingObjPos.blockX;
                int movY = movingObjPos.blockY;
                int movZ = movingObjPos.blockZ;
        
                if( !this.worldObj.canMineBlock((EntityPlayer)this.getThrower(), movX, movY, movZ) ) {
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
                            
                            int blockID = this.worldObj.getBlockId(movX + i, movY + j, movZ + k);
                            
                            if( blockID == Block.ice.blockID ) {
                                this.worldObj.setBlock(movX + i, movY + j, movZ + k, Block.waterStill.blockID, 0, 3);
                                this.worldObj.notifyBlockOfNeighborChange(movX + i, movY + j, movZ + k, this.worldObj.getBlockId(movX + i, movY + j + 1, movZ + k));
                            } else if( blockID == Block.obsidian.blockID ) {
                                this.worldObj.setBlock(movX + i, movY + j, movZ + k, Block.lavaStill.blockID);
                                this.worldObj.notifyBlockOfNeighborChange(movX + i, movY + j, movZ + k, this.worldObj.getBlockId(movX + i, movY + j + 1, movZ + k));
                            } else if( blockID == Block.snow.blockID ) {
                                this.worldObj.setBlock(movX + i, movY + j, movZ + k, Block.waterMoving.blockID, 7, 3);
                                this.worldObj.notifyBlockOfNeighborChange(movX + i, movY + j, movZ + k, this.worldObj.getBlockId(movX + i, movY + j + 1, movZ + k));
                            } else if( this.worldObj.isAirBlock(movX + i, movY + j, movZ + k) ) {
                                int bID1 = this.worldObj.getBlockId(movX + i, movY + j+1, movZ + k);
                                int bID2 = this.worldObj.getBlockId(movX + i, movY + j-1, movZ + k);
                                int bID3 = this.worldObj.getBlockId(movX + i+1, movY + j, movZ + k);
                                int bID4 = this.worldObj.getBlockId(movX + i-1, movY + j, movZ + k);
                                int bID5 = this.worldObj.getBlockId(movX + i, movY + j, movZ + k+1);
                                int bID6 = this.worldObj.getBlockId(movX + i, movY + j, movZ + k-1);
                                
                                if( Block.blocksList[bID1] != null && Block.blocksList[bID1].isFlammable(this.worldObj, movX + i, movY + j+1, movZ + k, this.worldObj.getBlockMetadata(movX + i, movY + j+1, movZ + k), ForgeDirection.DOWN) ) {
                                    this.worldObj.setBlock(movX + i, movY + j, movZ + k, Block.fire.blockID);
                                    playSound = true;
                                } else if( Block.blocksList[bID2] != null && Block.blocksList[bID2].isFlammable(this.worldObj, movX + i, movY + j-1, movZ + k, this.worldObj.getBlockMetadata(movX + i, movY + j-1, movZ + k), ForgeDirection.UP) ) {
                                    this.worldObj.setBlock(movX + i, movY + j, movZ + k, Block.fire.blockID);
                                    playSound = true;
                                } else if( Block.blocksList[bID3] != null && Block.blocksList[bID3].isFlammable(this.worldObj, movX + i+1, movY + j, movZ + k, this.worldObj.getBlockMetadata(movX + i+1, movY + j, movZ + k), ForgeDirection.WEST) ) {
                                    this.worldObj.setBlock(movX + i, movY + j, movZ + k, Block.fire.blockID);
                                    playSound = true;
                                } else if( Block.blocksList[bID4] != null && Block.blocksList[bID4].isFlammable(this.worldObj, movX + i-1, movY + j, movZ + k, this.worldObj.getBlockMetadata(movX + i-1, movY + j, movZ + k), ForgeDirection.EAST) ) {
                                    this.worldObj.setBlock(movX + i, movY + j, movZ + k, Block.fire.blockID);
                                    playSound = true;
                                } else if( Block.blocksList[bID5] != null && Block.blocksList[bID5].isFlammable(this.worldObj, movX + i, movY + j, movZ + k+1, this.worldObj.getBlockMetadata(movX + i, movY + j, movZ + k+1), ForgeDirection.NORTH) ) {
                                    this.worldObj.setBlock(movX + i, movY + j, movZ + k, Block.fire.blockID);
                                    playSound = true;
                                } else if( Block.blocksList[bID6] != null && Block.blocksList[bID6].isFlammable(this.worldObj, movX + i, movY + j, movZ + k-1, this.worldObj.getBlockMetadata(movX + i, movY + j, movZ + k-1), ForgeDirection.SOUTH) ) {
                                    this.worldObj.setBlock(movX + i, movY + j, movZ + k, Block.fire.blockID);
                                    playSound = true;
                                }
                            }
                        }
                    }
                }
                
                if( playSound ) {
                    this.worldObj.playSoundEffect(movX, movY, movZ, "fire.ignite", 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                }
            } else if( movingObjPos.typeOfHit == EnumMovingObjectType.ENTITY
            		   && movingObjPos.entityHit != null
            		   && movingObjPos.entityHit instanceof EntityLivingBase )
            {
                ((EntityLivingBase)movingObjPos.entityHit).setFire(5);
            }
            
            this.setDead();
        }
    }
    
    public boolean _SAP_canImpactOnLiquid() {
        return true;
    }
    
}
