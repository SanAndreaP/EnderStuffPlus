// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package sanandreasp.mods.EnderStuffPlus.entity;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.Textures;


public class EntityEnderNivis extends EntityEndermanESP implements IEnderCreature, Textures {

	public EntityEnderNivis(World world) {
		super(world);
		this.setSize(0.6F, 2.9F);
		this.stepHeight = 1.0F;
		this.experienceValue = 8;
		this.isImmuneToWater = true;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(10.0D);
	}

	@Override
	protected String getLivingSound() {
		return "enderstuffp:endernivis.idle";
	}

	@Override
	protected String getHurtSound() {
		return "enderstuffp:endernivis.scream";
	}

	@Override
	protected String getDeathSound() {
		return "enderstuffp:endernivis.death";
	}

	@Override
	protected int getDropItemId() {
		return ESPModRegistry.espPearls.itemID;
	}
	
	@Override
	protected int getDamageDropped() {
		return 0;
	}

	@Override
	public void onLivingUpdate() {
		if( this.ticksExisted % 5 == 0 && !worldObj.isRemote ) {
			this.setBlockIce();
		}

		super.onLivingUpdate();
	}
	
	@Override
	public void spawnParticle(String type, double X, double Y, double Z, float dataI, float dataII, float dataIII) {
//    	if( id == 0 ) {
    		dataI = 0.2F;
    		dataII = 0.5F;
    		dataIII = 1F;
//    	}
		super.spawnParticle(type, X, Y, Z, dataI, dataII, dataIII);
	}

	@Override
	public ItemStack getHeldItem() {
		return new ItemStack(Item.swordIron);
	}

	private void setBlockIce() {
		int blockPosX = (int) Math.floor(this.posX);
		int blockPosY = (int) Math.floor(this.posY) - 1;
		int blockPosZ = (int) Math.floor(this.posZ);

		for( int x = -2; x <= 2; x++ ) {
			for( int z = -2; z <= 2; z++ ) {
				if( Math.sqrt((x * x) + (z * z)) <= 2.3D ) {
					if (this.worldObj.getBlockId(blockPosX + x, blockPosY,
							blockPosZ + z) == Block.waterStill.blockID)
						this.worldObj.setBlock(blockPosX + x,
								blockPosY, blockPosZ + z, Block.ice.blockID);
				}
			}
		}
	}
	
	@Override
	public float getAIMoveSpeed() {
		return this.entityToAttack != null ? 0.15F : 0.1F;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if( par1DamageSource.isFireDamage() )
			par2 *= 5;
		
		return super.attackEntityFrom(par1DamageSource, par2);
	}
}
