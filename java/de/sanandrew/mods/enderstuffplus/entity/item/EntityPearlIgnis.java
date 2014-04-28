package de.sanandrew.mods.enderstuffplus.entity.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import de.sanandrew.mods.enderstuffplus.registry.ESPModRegistry;

public class EntityPearlIgnis
    extends EntityThrowable
{
    public EntityPearlIgnis(World world) {
        super(world);
    }

    public EntityPearlIgnis(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityPearlIgnis(World world, EntityLivingBase livingBase) {
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

        ESPModRegistry.sendPacketAllRng("fxPortal", this.posX, this.posY, this.posZ, 128D, this.dimension,
                                        this.posX, this.posY, this.posZ, 1.0F, 0.3F, 0.0F, this.width, this.height, 8);

        if( !this.worldObj.isRemote ) {
            if( movingObjPos.typeOfHit == MovingObjectType.BLOCK && this.getThrower() instanceof EntityPlayer ) {
                int movX = movingObjPos.blockX;
                int movY = movingObjPos.blockY;
                int movZ = movingObjPos.blockZ;

                if( !this.worldObj.canMineBlock((EntityPlayer) this.getThrower(), movX, movY, movZ) ) {
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

                            Block block = this.worldObj.getBlock(movX + i, movY + j, movZ + k);

                            if( block == Blocks.ice ) {
                                this.worldObj.setBlock(movX + i, movY + j, movZ + k, Blocks.water, 0, 3);
                                this.worldObj.notifyBlockOfNeighborChange(movX + i, movY + j, movZ + k,
                                                                          this.worldObj.getBlock(movX + i, movY + j + 1, movZ + k));
                            } else if( block == Blocks.obsidian ) {
                                this.worldObj.setBlock(movX + i, movY + j, movZ + k, Blocks.lava);
                                this.worldObj.notifyBlockOfNeighborChange(movX + i, movY + j, movZ + k,
                                                                          this.worldObj.getBlock(movX + i, movY + j + 1, movZ + k));
                            } else if( block == Blocks.snow_layer ) {
                                this.worldObj.setBlock(movX + i, movY + j, movZ + k, Blocks.flowing_water, 7, 3);
                                this.worldObj.notifyBlockOfNeighborChange(movX + i, movY + j, movZ + k,
                                                                          this.worldObj.getBlock(movX + i, movY + j + 1, movZ + k));
                            } else if( this.worldObj.isAirBlock(movX + i, movY + j, movZ + k) ) {
                                Block bID1 = this.worldObj.getBlock(movX + i, movY + j + 1, movZ + k);
                                Block bID2 = this.worldObj.getBlock(movX + i, movY + j - 1, movZ + k);
                                Block bID3 = this.worldObj.getBlock(movX + i + 1, movY + j, movZ + k);
                                Block bID4 = this.worldObj.getBlock(movX + i - 1, movY + j, movZ + k);
                                Block bID5 = this.worldObj.getBlock(movX + i, movY + j, movZ + k + 1);
                                Block bID6 = this.worldObj.getBlock(movX + i, movY + j, movZ + k - 1);

                                if( bID1 != null
                                    && bID1.isFlammable(this.worldObj, movX + i, movY + j + 1, movZ + k, ForgeDirection.DOWN) )
                                {
                                    this.worldObj.setBlock(movX + i, movY + j, movZ + k, Blocks.fire);
                                    playSound = true;
                                } else if( bID2 != null
                                           && bID2.isFlammable(this.worldObj, movX + i, movY + j - 1, movZ + k, ForgeDirection.UP) )
                                {
                                    this.worldObj.setBlock(movX + i, movY + j, movZ + k, Blocks.fire);
                                    playSound = true;
                                } else if( bID3 != null
                                           && bID3.isFlammable(this.worldObj, movX + i + 1, movY + j, movZ + k, ForgeDirection.WEST) )
                                {
                                    this.worldObj.setBlock(movX + i, movY + j, movZ + k, Blocks.fire);
                                    playSound = true;
                                } else if( bID4 != null
                                           && bID4.isFlammable(this.worldObj, movX + i - 1, movY + j, movZ + k, ForgeDirection.EAST) )
                                {
                                    this.worldObj.setBlock(movX + i, movY + j, movZ + k, Blocks.fire);
                                    playSound = true;
                                } else if( bID5 != null
                                           && bID5.isFlammable(this.worldObj, movX + i, movY + j, movZ + k + 1, ForgeDirection.NORTH) )
                                {
                                    this.worldObj.setBlock(movX + i, movY + j, movZ + k, Blocks.fire);
                                    playSound = true;
                                } else if( bID6 != null
                                           && bID6.isFlammable(this.worldObj, movX + i, movY + j, movZ + k - 1, ForgeDirection.SOUTH) )
                                {
                                    this.worldObj.setBlock(movX + i, movY + j, movZ + k, Blocks.fire);
                                    playSound = true;
                                }
                            }
                        }
                    }
                }

                if( playSound ) {
                    this.worldObj.playSoundEffect(movX, movY, movZ, "fire.ignite", 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                }
            } else if( movingObjPos.typeOfHit == MovingObjectType.ENTITY && movingObjPos.entityHit != null
                       && movingObjPos.entityHit instanceof EntityLivingBase )
            {
                ((EntityLivingBase) movingObjPos.entityHit).setFire(5);
            }

            this.setDead();
        }
    }

}
