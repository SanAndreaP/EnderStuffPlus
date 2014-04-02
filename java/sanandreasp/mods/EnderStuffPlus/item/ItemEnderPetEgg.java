package sanandreasp.mods.EnderStuffPlus.item;

import java.util.HashMap;
import java.util.List;

import sanandreasp.core.manpack.helpers.CommonUsedStuff;
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
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        int par1 = par1ItemStack.getItemDamage();
        return (Integer) (PETS.containsKey(par1) ? (par2 == 0 ? PETS.get(par1)[1] : PETS.get(par1)[2]) : 0xFFFFFF);
    }

    private String getEnderPetName(ItemStack par1ItemStack) {
        int petID = par1ItemStack.getItemDamage();
        if( !PETS.containsKey(petID) ) {
            petID = 0;
        }

        return (String) PETS.get(petID)[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamageForRenderPass(int par1, int par2) {
        return Item.monsterPlacer.getIconFromDamageForRenderPass(par1, par2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemDisplayName(ItemStack par1ItemStack) {
        String var2 = ("" + CommonUsedStuff.getTranslated(this.getUnlocalizedName() + ".name")).trim();
        String var3 = this.getEnderPetName(par1ItemStack);

        if( var3 != null ) {
            var2 = var2 + " " + StatCollector.translateToLocal("entity." + var3 + ".name");
        }

        return var2;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        ItemStack pet = new ItemStack(this, 1, 0);
        NBTTagCompound nbt = new NBTTagCompound("enderPetEgg");
        nbt.setByte("petID", (byte) 0);
        nbt.setFloat("health", 40.0F);
        nbt.setBoolean("fallDmg", false);
        nbt.setBoolean("special", false);
        pet.setTagCompound(nbt);
        par3List.add(pet.copy());
        nbt.setBoolean("special", true);
        pet.setTagCompound(nbt);
        par3List.add(pet.copy());
        pet = new ItemStack(this, 1, 1);
        nbt = new NBTTagCompound("enderPetEgg");
        nbt.setByte("petID", (byte) 1);
        nbt.setFloat("health", 40.0F);
        nbt.setFloat("condition", 10.0F);
        nbt.setBoolean("saddled", false);
        pet.setTagCompound(nbt);
        par3List.add(pet.copy());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack) {
        return par1ItemStack.hasTagCompound();
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4,
                             int par5, int par6, int par7, float par8, float par9, float par10) {
        if( par3World.isRemote ) {
            return true;
        } else {
            int var11 = par3World.getBlockId(par4, par5, par6);
            par4 += Facing.offsetsXForSide[par7];
            par5 += Facing.offsetsYForSide[par7];
            par6 += Facing.offsetsZForSide[par7];
            double var12 = 0.0D;

            if( par7 == 1 && var11 == Block.fence.blockID || var11 == Block.netherFence.blockID ) {
                var12 = 0.5D;
            }

            if( spawnEnderPet(par3World, par1ItemStack, this.getEnderPetName(par1ItemStack), par4 + 0.5D, par5 + var12,
                              par6 + 0.5D, par2EntityPlayer.username) ) {
                --par1ItemStack.stackSize;
            }

            return true;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {}

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
