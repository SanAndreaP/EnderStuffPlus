package sanandreasp.mods.EnderStuffPlus.entity.item;

import sanandreasp.mods.EnderStuffPlus.packet.PacketsSendToClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

public class EntityPearlNivis extends EntityThrowable {
    
    public EntityPearlNivis(World par1World) {
        super(par1World);
    }
    
    public EntityPearlNivis(World par1World, EntityLivingBase par2EntityLivingBase) {
        super(par1World, par2EntityLivingBase);
    }

    @SideOnly(Side.CLIENT)
    public EntityPearlNivis(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingobjectposition) {
        if( movingobjectposition.entityHit != null ) {
            movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }
        
        PacketsSendToClient.sendParticle(this, (byte)0, this.posX, this.posY, this.posZ, 0.2D, 0.5D, 1.0D);

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
            } else if( movingobjectposition.typeOfHit == EnumMovingObjectType.ENTITY && movingobjectposition.entityHit != null ) {
            	if( movingobjectposition.entityHit instanceof EntityLivingBase )
            		((EntityLivingBase)movingobjectposition.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 300, 0));
                movingobjectposition.entityHit.extinguish();
            }
            
            this.setDead();
        }
    }
    
    public boolean canImpactOnLiquid() {
        return true;
    }

}
