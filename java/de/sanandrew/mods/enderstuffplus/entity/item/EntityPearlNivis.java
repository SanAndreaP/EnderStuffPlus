package de.sanandrew.mods.enderstuffplus.entity.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

import de.sanandrew.core.manpack.mod.packet.IPacket;
import de.sanandrew.mods.enderstuffplus.packet.PacketFXCstPortal;
import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;

public class EntityPearlNivis
    extends EntityThrowable
{
    public EntityPearlNivis(World world) {
        super(world);
    }

    public EntityPearlNivis(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityPearlNivis(World world, EntityLivingBase livingBase) {
        super(world, livingBase);
    }

    public boolean _SAP_canImpactOnLiquid() {
        return true;
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjPos) {
        if( movingObjPos.entityHit != null ) {
            movingObjPos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }

        IPacket packet = new PacketFXCstPortal(this.posX, this.posY, this.posZ, 0.2F, 0.5F, 1.0F, this.width, this.height, 8);
        ESPModRegistry.channelHandler.sendToAllAround(packet, new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64));

        if( !this.worldObj.isRemote ) {
            if( movingObjPos.typeOfHit == MovingObjectType.BLOCK && this.getThrower() instanceof EntityPlayer ) {
                int var13 = movingObjPos.blockX;
                int var14 = movingObjPos.blockY;
                int var15 = movingObjPos.blockZ;

                if( !this.worldObj.canMineBlock((EntityPlayer) this.getThrower(), var13, var14, var15) ) {
                    this.setDead();
                    return;
                }

                boolean playSound = false;

                for( int i = -3; i <= 3; i++ ) {
                    for( int j = -3; j <= 3; j++ ) {
                        for( int k = -3; k <= 3; k++ ) {
                            if( Math.sqrt(i * i + j * j + k * k) > 3.4D ) {
                                continue;
                            }

                            Block blockID = this.worldObj.getBlock(var13 + i, var14 + j, var15 + k);

                            if( (blockID == Blocks.water || blockID == Blocks.flowing_water)
                                && this.worldObj.getBlockMetadata(var13 + i, var14 + j, var15 + k) == 0 )
                            {
                                this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Blocks.ice);
                            } else if( (blockID == Blocks.lava || blockID == Blocks.flowing_lava)
                                       && this.worldObj.getBlockMetadata(var13 + i, var14 + j, var15 + k) == 0 ) {
                                this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Blocks.obsidian);
                            } else if( (blockID == Blocks.lava || blockID == Blocks.flowing_lava) ) {
                                this.worldObj.setBlock(var13 + i, var14 + j, var15 + k, Blocks.cobblestone);
                            } else if( this.worldObj.isBlockNormalCubeDefault(var13 + i, var14 + j, var15 + k, false)
                                       && this.worldObj.isAirBlock(var13 + i, var14 + j + 1, var15 + k) )
                            {
                                this.worldObj.setBlock(var13 + i, var14 + j + 1, var15 + k, Blocks.snow_layer);
                            } else if( blockID == Blocks.fire ) {
                                this.worldObj.setBlockToAir(var13 + i, var14 + j, var15 + k);
                                playSound = true;
                            }
                        }
                    }
                }
                if( playSound ) {
                    this.worldObj.playSoundEffect(var13, var14, var15, "random.fizz", 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                }
            } else if( movingObjPos.typeOfHit == MovingObjectType.ENTITY && movingObjPos.entityHit != null ) {
                if( movingObjPos.entityHit instanceof EntityLivingBase ) {
                    ((EntityLivingBase) movingObjPos.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 300, 0));
                }
                movingObjPos.entityHit.extinguish();
            }

            this.setDead();
        }
    }

}
