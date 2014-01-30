package sanandreasp.mods.EnderStuffPlus.item.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.mods.EnderStuffPlus.packet.PacketsSendToClient;
import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class ItemNiobShears extends ItemShears {
	
	private List<Block> effectiveBlocks = new ArrayList<Block>();
	private Icon glowMap;

	public ItemNiobShears(int par1) {
		super(par1);
        this.setMaxDamage(476);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLiving) {
		return par3 != Block.deadBush.blockID ? super.onBlockDestroyed(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving) : true;
	}
	
    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity)
    {
    	return false;
    }
    
    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) 
    {
        if( player.worldObj.isRemote || player.capabilities.isCreativeMode )
            return false;

        int id = player.worldObj.getBlockId(x, y, z);
    	if( getShearable(itemstack, player.worldObj, x, y, z).contains(Block.blocksList[id]) || id == Block.deadBush.blockID ) {
    		ArrayList<ItemStack> drops;
    		if( id == Block.deadBush.blockID ) {
    			drops = new ArrayList<ItemStack>();
    			drops.add(new ItemStack(Block.deadBush, 1, itemstack.getItemDamage()));
    		} else {
    			IShearable target = (IShearable)Block.blocksList[id];
	            drops = target.onSheared(itemstack, player.worldObj, x, y, z,
	                    EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
    		}
            Random rand = new Random();
			boolean transportSucceed = false;
            for( ItemStack item : drops )
            {
            	ItemStack newStack = EnchantmentHelper.getEnchantmentLevel(ESPModRegistry.enderChestTel.effectId, itemstack) > 0
		    			? CommonUsedStuff.addItemStackToInventory(item.copy(), player.getInventoryEnderChest())
		    			: item.copy();
		    	if( newStack != null ) {
		    		newStack = CommonUsedStuff.addItemStackToInventory(newStack.copy(), player.inventory);
		    		if( newStack == null ) {
		    			transportSucceed = true;
		    		} else {
	                    float f = 0.7F;
	                    double d  = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	                    double d1 = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	                    double d2 = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	                    EntityItem entityitem = new EntityItem(player.worldObj, (double)x + d, (double)y + d1, (double)z + d2, item);
	                    entityitem.delayBeforeCanPickup = 10;
	                    player.worldObj.spawnEntityInWorld(entityitem);
	                }
            	} else {
        			transportSucceed = true;
        		}
            }
            itemstack.damageItem(1, player);
            player.addStat(StatList.mineBlockStatArray[id], 1);
            if( transportSucceed ) {
            	PacketsSendToClient.sendParticle(player.worldObj, x, y, z, (byte)5);
            	player.inventoryContainer.detectAndSendChanges();
            	if( player.openContainer != null )
            		player.inventoryContainer.detectAndSendChanges();
            }
        }
        return false;
    }
	
	@Override
    public int getItemEnchantability()
    {
        return ESPModRegistry.TOOL_NIOBIUM.getEnchantability();
    }

	private List<Block> getShearable(ItemStack stack, World worldObj, int x, int y, int z) {
		if( effectiveBlocks.size() > 0 )
			return new ArrayList<Block>(this.effectiveBlocks);
		List<Block> blocks = new ArrayList<Block>();
		for( int i = 0; i < Block.blocksList.length; i++ ) {
			if( Block.blocksList[i] != null && Block.blocksList[i] instanceof IShearable )
				if( ((IShearable)Block.blocksList[i]).isShearable(stack, worldObj, x, y, z) )
					blocks.add(Block.blocksList[i]);
		}
		this.effectiveBlocks = new ArrayList<Block>(blocks);
		return new ArrayList<Block>(blocks);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		this.glowMap = par1IconRegister.registerIcon("enderstuffp:niobShearsGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return pass == 1 ? glowMap : super.getIcon(stack, pass);
	}
}
