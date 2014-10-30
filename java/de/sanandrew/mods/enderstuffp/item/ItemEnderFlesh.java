package de.sanandrew.mods.enderstuffp.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemEnderFlesh
    extends ItemFood
{
    private static final Multimap<Boolean, Potion> POTION_EFFECTS = ArrayListMultimap.create();

    static {
        POTION_EFFECTS.put(true, Potion.digSlowdown);
        POTION_EFFECTS.put(true, Potion.confusion);
        POTION_EFFECTS.put(true, Potion.harm);
        POTION_EFFECTS.put(true, Potion.hunger);
        POTION_EFFECTS.put(true, Potion.moveSlowdown);
        POTION_EFFECTS.put(true, Potion.poison);
        POTION_EFFECTS.put(true, Potion.weakness);

        POTION_EFFECTS.put(false, Potion.digSpeed);
        POTION_EFFECTS.put(false, Potion.damageBoost);
        POTION_EFFECTS.put(false, Potion.fireResistance);
        POTION_EFFECTS.put(false, Potion.heal);
        POTION_EFFECTS.put(false, Potion.jump);
        POTION_EFFECTS.put(false, Potion.moveSpeed);
        POTION_EFFECTS.put(false, Potion.regeneration);
        POTION_EFFECTS.put(false, Potion.resistance);
        POTION_EFFECTS.put(false, Potion.waterBreathing);
    }

    public ItemEnderFlesh() {
        super(2, 0.6F, false);
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":enderFlesh");
        this.setTextureName(EnderStuffPlus.MOD_ID + ":enderFlesh");
        this.setCreativeTab(EnderStuffPlus.ESP_TAB);
        this.setAlwaysEdible();
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addInformation(ItemStack stack, EntityPlayer player, List infos, boolean isAdvancedInfo) {
        if( stack.getItemDamage() > 0 ) {
            infos.add("positive effects only");
        }
        if( stack.getItemDamage() == 2 ) {
            infos.add("4x duration");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        int damage = stack.getItemDamage();
        return damage == 2 ? 0xFF6666 : 0xFFFFFF;
    }

    private static PotionEffect getRandomPotionEffect(ItemStack stack, Random random) {
        int duration = 600 * (stack.getItemDamage() == 2 ? 4 : 1);
        boolean isPositive = stack.getItemDamage() > 0;
        List<Potion> potions = new ArrayList<Potion>(isPositive ? POTION_EFFECTS.get(false) : POTION_EFFECTS.values());

        return new PotionEffect(potions.get(random.nextInt(potions.size())).id, duration, 0);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return stack.getItemDamage() == 2 ? EnumRarity.rare
                                          : (stack.getItemDamage() == 1 ? EnumRarity.uncommon : EnumRarity.common);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubItems(Item item, CreativeTabs tab, List stacks) {
        stacks.add(new ItemStack(this, 1, 0));
        stacks.add(new ItemStack(this, 1, 1));
        stacks.add(new ItemStack(this, 1, 2));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass) {
        return stack.getItemDamage() > 0;
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        for( int i = 0; i < 3; i++ ) {
            player.addPotionEffect(getRandomPotionEffect(stack, world.rand));
        }
    }
}
