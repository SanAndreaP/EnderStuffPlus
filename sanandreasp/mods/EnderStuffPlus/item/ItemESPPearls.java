package sanandreasp.mods.EnderStuffPlus.item;
import java.util.List;

import sanandreasp.mods.EnderStuffPlus.entity.item.EntityBait;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlIgnis;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlMiss;
import sanandreasp.mods.EnderStuffPlus.entity.item.EntityPearlNivis;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemESPPearls extends Item {
	@SideOnly(Side.CLIENT)
	protected Icon icns[];
	
	public ItemESPPearls(int i) {
		super(i);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setMaxStackSize(16);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if( !par3EntityPlayer.capabilities.isCreativeMode )
            --par1ItemStack.stackSize;

//    	for( int i = -128; i < 128; i++ ) {
//        	for( int j = -128; j < 128; j++ ) {
//            	for( int k = -128; i < 128; k++ ) {
//            		par2World.markBlockForRenderUpdate((int)(par3EntityPlayer.posX+0.5F) + i, (int)(par3EntityPlayer.posY+0.5F) + j, (int)(par3EntityPlayer.posZ+0.5F) + k);
//            	}
//        	}
//    	}
        if( !par2World.isRemote ) {
	        switch( par1ItemStack.getItemDamage() ) {
		        case 0:
		        	par2World.spawnEntityInWorld(new EntityPearlNivis(par2World, par3EntityPlayer));
		        	break;
		        case 1:
		        	par2World.spawnEntityInWorld(new EntityPearlIgnis(par2World, par3EntityPlayer));
		        	break;
		        case 2:
		        	if( par3EntityPlayer.isSneaking() ) {
		    	    	AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
		    	    			par3EntityPlayer.posX - 32, par3EntityPlayer.posY - 32, par3EntityPlayer.posZ - 32,
		    	    			par3EntityPlayer.posX + 32, par3EntityPlayer.posY + 32, par3EntityPlayer.posZ + 32
		    	    	);
		    	    	List<EntityBait> myBaits = par2World.getEntitiesWithinAABB(EntityBait.class, aabb);
		    	    	for( EntityBait bait : myBaits ) {
		    	    		if( bait != null && bait.isEntityAlive() ) {
		    	    			bait.setDead();
		    	    		}
		    	    	}
		    	    	par2World.playSoundAtEntity(par3EntityPlayer, "fire.ignite", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		    	    	return par1ItemStack;
		        	} else {
		        		par2World.spawnEntityInWorld(new EntityPearlMiss(par2World, par3EntityPlayer));
		        	}
		        	break;
	        	default:
		        	par2World.spawnEntityInWorld(new EntityEnderPearl(par2World, par3EntityPlayer));
	        }
        }

    	par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        return par1ItemStack;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return super.getUnlocalizedName() + par1ItemStack.getItemDamage();
	}
	
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(this.itemID, 1, 0));
		par3List.add(new ItemStack(this.itemID, 1, 1));
		par3List.add(new ItemStack(this.itemID, 1, 2));
	}
	
	@Override
	public Icon getIconFromDamage(int par1) {
		return icns[par1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.icns = new Icon[3];
		this.itemIcon = this.icns[0] = par1IconRegister.registerIcon("enderstuffp:pearlNivis");
		this.icns[1] = par1IconRegister.registerIcon("enderstuffp:pearlIgnis");
		this.icns[2] = par1IconRegister.registerIcon("enderstuffp:pearlEndermiss");
	}
}
