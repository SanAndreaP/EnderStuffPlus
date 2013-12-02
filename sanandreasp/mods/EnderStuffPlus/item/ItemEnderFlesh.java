package sanandreasp.mods.EnderStuffPlus.item;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.src.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//import net.minecraft.src.forge.ITextureProvider;

public class ItemEnderFlesh extends ItemFood {

	public ItemEnderFlesh(int par1) {
		super(par1, 2, 0.6F, false);
		this.setAlwaysEdible();
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		int par1 = par1ItemStack.getItemDamage();
		return par1 == 2 ? 0xFF6666 : 0xFFFFFF;
	}

	@Override
	public void onFoodEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		for( int i = 0; i < 3; i++ )
			par3EntityPlayer.addPotionEffect(this.getRandomPotionEffect(
					par1ItemStack, par2World.rand));
	}

	private PotionEffect getRandomPotionEffect(ItemStack is, Random rand) {
		List<PotionEffect> potionEffects = new ArrayList<PotionEffect>();

		int duration = 600 * (is.getItemDamage() == 2 ? 4 : 1);

		boolean isOnlyPositive = is.getItemDamage() > 0;

		if( !isOnlyPositive ) {
			potionEffects.add(new PotionEffect(Potion.digSlowdown.id, duration,
					0));
			potionEffects
					.add(new PotionEffect(Potion.confusion.id, duration, 0));
			potionEffects.add(new PotionEffect(Potion.harm.id, 1, 0));
			potionEffects.add(new PotionEffect(Potion.hunger.id, duration, 0));
			potionEffects.add(new PotionEffect(Potion.moveSlowdown.id,
					duration, 0));
			potionEffects.add(new PotionEffect(Potion.poison.id, duration, 0));
			potionEffects
					.add(new PotionEffect(Potion.weakness.id, duration, 0));
		}

		potionEffects.add(new PotionEffect(Potion.digSpeed.id, duration, 0));
		potionEffects.add(new PotionEffect(Potion.damageBoost.id, duration, 0));
		potionEffects.add(new PotionEffect(Potion.fireResistance.id, duration,
				0));
		potionEffects.add(new PotionEffect(Potion.heal.id, 1, 0));
		potionEffects.add(new PotionEffect(Potion.jump.id, duration, 0));
		potionEffects.add(new PotionEffect(Potion.moveSpeed.id, duration, 0));
		potionEffects
				.add(new PotionEffect(Potion.regeneration.id, duration, 0));
		potionEffects.add(new PotionEffect(Potion.resistance.id, duration, 0));
		potionEffects.add(new PotionEffect(Potion.waterBreathing.id, duration,
				0));

		return potionEffects.get(rand.nextInt(potionEffects.size()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("enderstuffp:enderFlesh");
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() > 0;
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() == 2 ? EnumRarity.rare
				: (par1ItemStack.getItemDamage() == 1 ? EnumRarity.uncommon
						: EnumRarity.common);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(this, 1, 0));
		par3List.add(new ItemStack(this, 1, 1));
		par3List.add(new ItemStack(this, 1, 2));
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List par2List, boolean b) {
		if( par1ItemStack.getItemDamage() > 0 )
			par2List.add("positive effects only");
		if( par1ItemStack.getItemDamage() == 2 )
			par2List.add("4x duration");
	}
}
