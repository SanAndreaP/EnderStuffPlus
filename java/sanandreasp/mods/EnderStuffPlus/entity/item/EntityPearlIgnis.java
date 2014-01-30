package sanandreasp.mods.EnderStuffPlus.entity.item;

import sanandreasp.mods.EnderStuffPlus.packet.PacketsSendToClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class EntityPearlIgnis extends EntityThrowable {
    
    public EntityPearlIgnis(World par1World) {
        super(par1World);
    }
    
    public EntityPearlIgnis(World par1World, EntityLivingBase par2EntityLivingBase) {
        super(par1World, par2EntityLivingBase);
    }

    @SideOnly(Side.CLIENT)
    public EntityPearlIgnis(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingobjectposition) {
        if( movingobjectposition.entityHit != null ) {
            movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }
        
        for( int i = 0; i < 8; i++ )
            PacketsSendToClient.sendParticle(this, (byte)0, this.posX, this.posY, this.posZ, 1.0D, 0.3D, 0.0D);

        if( !this.worldObj.isRemote ) {
            if( movingobjectposition.typeOfHit == EnumMovingObjectType.TILE && this.getThrower() instanceof EntityPlayer ) {
                int var13 = movingobjectposition.blockX;
                int var14 = movingobjectposition.blockY;
                int var15 = movingobjectposition.blockZ;
        
                if( !this.worldObj.canMineBlock((EntityPlayer)this.getThrower(), var13, var14, var15) ) {
                    this.setDead();
                    return;
                }
    
                boolean playSound = false;
                
                for( int i = -3; i <= 3; i++ ) {
                    for( int j = -3; j <= 3; j++ ) {
                        for( int k = -3; k <= 3; k++ ) {
                            if( Math.sqrt(i*i + j*j + k*k) > 3.4D )
                                continue;

                            
                            int blockID = this.worldObj.getBlockId(var13 + i, var14 + j, var15 + k);
                            
                            if( blockID == Block.ice.blockID ) {
                                this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Block.waterStill.blockID, 0, 3);
                                this.worldObj.notifyBlockOfNeighborChange(var13 + i, var14 + j, var15 + k, this.worldObj.getBlockId(var13 + i, var14 + j + 1, var15 + k));
                            } else if( blockID == Block.obsidian.blockID ) {
                                this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Block.lavaStill.blockID);
                                this.worldObj.notifyBlockOfNeighborChange(var13 + i, var14 + j, var15 + k, this.worldObj.getBlockId(var13 + i, var14 + j + 1, var15 + k));
                            } else if( blockID == Block.snow.blockID ) {
                                this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Block.waterMoving.blockID, 7, 3);
                                this.worldObj.notifyBlockOfNeighborChange(var13 + i, var14 + j, var15 + k, this.worldObj.getBlockId(var13 + i, var14 + j + 1, var15 + k));
                            } else if( this.worldObj.isAirBlock(var13 + i, var14 + j, var15 + k) ) {
                                int bID1 = this.worldObj.getBlockId(var13 + i, var14 + j+1, var15 + k);
                                int bID2 = this.worldObj.getBlockId(var13 + i, var14 + j-1, var15 + k);
                                int bID3 = this.worldObj.getBlockId(var13 + i+1, var14 + j, var15 + k);
                                int bID4 = this.worldObj.getBlockId(var13 + i-1, var14 + j, var15 + k);
                                int bID5 = this.worldObj.getBlockId(var13 + i, var14 + j, var15 + k+1);
                                int bID6 = this.worldObj.getBlockId(var13 + i, var14 + j, var15 + k-1);
                                
                                if( Block.blocksList[bID1] != null && Block.blocksList[bID1].isFlammable(this.worldObj, var13 + i, var14 + j+1, var15 + k, this.worldObj.getBlockMetadata(var13 + i, var14 + j+1, var15 + k), ForgeDirection.DOWN) ) {
                                    this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Block.fire.blockID);
                                    playSound = true;
                                } else if( Block.blocksList[bID2] != null && Block.blocksList[bID2].isFlammable(this.worldObj, var13 + i, var14 + j-1, var15 + k, this.worldObj.getBlockMetadata(var13 + i, var14 + j-1, var15 + k), ForgeDirection.UP) ) {
                                    this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Block.fire.blockID);
                                    playSound = true;
                                } else if( Block.blocksList[bID3] != null && Block.blocksList[bID3].isFlammable(this.worldObj, var13 + i+1, var14 + j, var15 + k, this.worldObj.getBlockMetadata(var13 + i+1, var14 + j, var15 + k), ForgeDirection.WEST) ) {
                                    this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Block.fire.blockID);
                                    playSound = true;
                                } else if( Block.blocksList[bID4] != null && Block.blocksList[bID4].isFlammable(this.worldObj, var13 + i-1, var14 + j, var15 + k, this.worldObj.getBlockMetadata(var13 + i-1, var14 + j, var15 + k), ForgeDirection.EAST) ) {
                                    this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Block.fire.blockID);
                                    playSound = true;
                                } else if( Block.blocksList[bID5] != null && Block.blocksList[bID5].isFlammable(this.worldObj, var13 + i, var14 + j, var15 + k+1, this.worldObj.getBlockMetadata(var13 + i, var14 + j, var15 + k+1), ForgeDirection.NORTH) ) {
                                    this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Block.fire.blockID);
                                    playSound = true;
                                } else if( Block.blocksList[bID6] != null && Block.blocksList[bID6].isFlammable(this.worldObj, var13 + i, var14 + j, var15 + k-1, this.worldObj.getBlockMetadata(var13 + i, var14 + j, var15 + k-1), ForgeDirection.SOUTH) ) {
                                    this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Block.fire.blockID);
                                    playSound = true;
                                }
                            }
                        }
                    }
                }
                if( playSound ) {
                    this.worldObj.playSoundEffect(var13, var14, var15, "fire.ignite", 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                }
            } else if( movingobjectposition.typeOfHit == EnumMovingObjectType.ENTITY && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityLivingBase ) {
                ((EntityLivingBase)movingobjectposition.entityHit).setFire(5);
            }
            
            this.setDead();
        }
    }
    
    public boolean canImpactOnLiquid() {
        return true;
    }

}
