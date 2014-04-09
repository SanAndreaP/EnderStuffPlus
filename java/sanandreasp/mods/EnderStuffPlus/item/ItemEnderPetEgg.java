package sanandreasp.mods.EnderStuffPlus.item;

import java.util.HashMap;
import java.util.List;

import sanandreasp.core.manpack.helpers.SAPUtils;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;
import sanandreasp.mods.EnderStuffPlus.entity.IEnderPet;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEnderPetEgg
    extends Item
{
    private static final HashMap<Integer, Object[]> PETS = Maps.newHashMap();

    public ItemEnderPetEgg(int id) {
        super(id);
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

            if( entity instanceof IEnderPet && nbt != null && nbt.hasKey("petID") ) {
                IEnderPet pet = (IEnderPet) entity;

                entity.setLocationAndAngles(posX, posY, posZ, world.rand.nextFloat() * 360.0F, 0.0F);
                pet.readPetFromNBT(nbt);
                pet.setTamed(true);
                pet.setOwnerName(playerName);
                world.spawnEntityInWorld(entity);
                ((EntityLiving) pet).playLivingSound();

            }

            return entity != null;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addInformation(ItemStack stack, EntityPlayer player, List infos, boolean isAdvancedInfo) {
        if( stack.getItemDamage() == 0 ) {
            EntityEnderMiss.getEggInfo(stack, player, infos, isAdvancedInfo);
        } else if( stack.getItemDamage() == 1 ) {
            EntityEnderAvis.getEggInfo(stack, player, infos, isAdvancedInfo);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        int par1 = stack.getItemDamage();
        return (Integer) (PETS.containsKey(par1) ? (pass == 0 ? PETS.get(par1)[1] : PETS.get(par1)[2]) : 0xFFFFFF);
    }

    private String getEnderPetName(ItemStack stack) {
        int petID = stack.getItemDamage();
        if( !PETS.containsKey(petID) ) {
            petID = 0;
        }

        return (String) PETS.get(petID)[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamageForRenderPass(int dmg, int pass) {
        return Item.monsterPlacer.getIconFromDamageForRenderPass(dmg, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemDisplayName(ItemStack stack) {
        String itemName = ("" + SAPUtils.getTranslated(this.getUnlocalizedName() + ".name")).trim();
        String petName = this.getEnderPetName(stack);

        if( petName != null ) {
            itemName = itemName + " " + StatCollector.translateToLocal("entity." + petName + ".name");
        }

        return itemName;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubItems(int itemId, CreativeTabs tab, List stacks) {
        ItemStack pet = new ItemStack(this, 1, 0);
        NBTTagCompound nbt = new NBTTagCompound("enderPetEgg");
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
        nbt = new NBTTagCompound("enderPetEgg");
        nbt.setByte("petID", (byte) 1);
        nbt.setFloat("health", 40.0F);
        nbt.setFloat("condition", 10.0F);
        nbt.setBoolean("saddled", false);
        pet.setTagCompound(nbt);
        stacks.add(pet.copy());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return stack.hasTagCompound();
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float offX, float offY,
                             float offZ) {
        if( world.isRemote ) {
            return true;
        } else {
            int blockId = world.getBlockId(x, y, z);
            x += Facing.offsetsXForSide[side];
            y += Facing.offsetsYForSide[side];
            z += Facing.offsetsZForSide[side];
            double yOffset = 0.0D;

            if( side == 1 && blockId == Block.fence.blockID || blockId == Block.netherFence.blockID ) {
                yOffset = 0.5D;
            }

            if( spawnEnderPet(world, stack, this.getEnderPetName(stack), x + 0.5D, y + yOffset, z + 0.5D, player.username) ) {
                --stack.stackSize;
            }

            return true;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {}

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
