package de.sanandrew.mods.enderstuffp.item;

import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.util.CreativeTabsEnderStuff;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
//import de.sanandrew.mods.enderstuffplus.entity.living.EntityEnderAvis;

public class ItemEnderPetEgg
    extends Item
{
    private static final HashMap<Integer, Object[]> PETS = Maps.newHashMap();

    public ItemEnderPetEgg() {
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":enderPetEgg");
        this.setCreativeTab(CreativeTabsEnderStuff.ESP_TAB);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    public static void addPet(int petID, String petName, int frgColor, int bkgColor) {
        PETS.put(petID, new Object[] { petName, frgColor, bkgColor });
    }

    public static boolean spawnEnderPet(World world, ItemStack stack, String entityName, double posX, double posY, double posZ,
                                        String playerName) {
        if( !PETS.containsKey(stack.getItemDamage()) ) {
            return false;
        } else {
            Entity entity = EntityList.createEntityByName(entityName, world);
            NBTTagCompound nbt = stack.getTagCompound();

            // todo reimplement entity spawning
//            if( entity instanceof IEnderPet && nbt != null && nbt.hasKey("petID") ) {
//                IEnderPet pet = (IEnderPet) entity;
//
//                entity.setLocationAndAngles(posX, posY, posZ, world.rand.nextFloat() * 360.0F, 0.0F);
//                pet.readPetFromNBT(nbt);
//                pet.setTamed(true);
//                pet.setOwnerName(playerName);
//                world.spawnEntityInWorld(entity);
//                ((EntityLiving) pet).playLivingSound();
//
//            }

            return entity != null;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addInformation(ItemStack stack, EntityPlayer player, List infos, boolean isAdvancedInfo) {
        if( stack.getItemDamage() == 0 ) {
//            EntityEnderMiss.getEggInfo(stack, player, infos, isAdvancedInfo);
        } else if( stack.getItemDamage() == 1 ) {
//            EntityEnderAvis.getEggInfo(stack, player, infos, isAdvancedInfo);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        int par1 = stack.getItemDamage();
        return (Integer) (PETS.containsKey(par1) ? (pass == 0 ? PETS.get(par1)[1] : PETS.get(par1)[2]) : 0xFFFFFF);
    }

    private static String getEnderPetName(ItemStack stack) {
        int petID = stack.getItemDamage();
        if( !PETS.containsKey(petID) ) {
            return "";
        }

        return (String) PETS.get(petID)[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int dmg, int pass) {
        return Items.spawn_egg.getIconFromDamageForRenderPass(dmg, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        String itemName = (SAPUtils.translate(this.getUnlocalizedName() + ".name")).trim();
        String petName = getEnderPetName(stack);

        if( petName != null ) {
            itemName = itemName + ' ' + SAPUtils.translate("entity." + petName + ".name");
        }

        return itemName;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubItems(Item item, CreativeTabs tab, List stacks) {
        ItemStack pet = new ItemStack(this, 1, 0);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setByte("petID", (byte) 0);
        nbt.setFloat("health", 40.0F);
        nbt.setBoolean("fallDmg", false);
        nbt.setBoolean("special", false);
        pet.setTagCompound(nbt);
        stacks.add(pet.copy());
        nbt.setBoolean("special", true);
        pet.setTagCompound(nbt);
        stacks.add(pet.copy());
        pet = new ItemStack(this, 1, 1);
        nbt = new NBTTagCompound();
        nbt.setByte("petID", (byte) 1);
        nbt.setFloat("health", 40.0F);
        nbt.setFloat("condition", 10.0F);
        nbt.setBoolean("saddled", false);
        pet.setTagCompound(nbt);
        stacks.add(pet.copy());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass) {
        return stack.hasTagCompound();
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float offX, float offY,
                             float offZ) {
        if( world.isRemote ) {
            return true;
        } else {
            Block blockId = world.getBlock(x, y, z);
            x += Facing.offsetsXForSide[side];
            y += Facing.offsetsYForSide[side];
            z += Facing.offsetsZForSide[side];
            double yOffset = 0.0D;

            if( side == 1 && blockId == Blocks.fence || blockId == Blocks.nether_brick_fence ) {
                yOffset = 0.5D;
            }

            if( spawnEnderPet(world, stack, getEnderPetName(stack), x + 0.5D, y + yOffset, z + 0.5D, player.getCommandSenderName()) ) {
                --stack.stackSize;
            }

            return true;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) { }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
