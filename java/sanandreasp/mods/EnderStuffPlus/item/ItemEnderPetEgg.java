package sanandreasp.mods.EnderStuffPlus.item;

import java.util.HashMap;
import java.util.List;

import sanandreasp.core.manpack.helpers.CUS;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderAvis;
import sanandreasp.mods.EnderStuffPlus.entity.EntityEnderMiss;

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
    private static HashMap<Integer, Object[]> pets = Maps.newHashMap();

    public ItemEnderPetEgg(int i) {
        super(i);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    public static void addPet(int petID, String petName, int frgColor, int bkgColor) {
        pets.put(petID, new Object[] { petName, frgColor, bkgColor });
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemDisplayName(ItemStack par1ItemStack) {
        String var2 = ("" + CUS.getTranslated(this.getUnlocalizedName() + ".name")).trim();
        String var3 = this.getEnderPetName(par1ItemStack);

        if( var3 != null ) {
            var2 = var2 + " " + StatCollector.translateToLocal("entity." + var3 + ".name");
        }

        return var2;
    }

    private String getEnderPetName(ItemStack par1ItemStack) {
        int petID = par1ItemStack.getItemDamage();
        if( !pets.containsKey(petID) ) {
            petID = 0;
        }

        return (String) pets.get(petID)[0];
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

    public static boolean spawnEnderPet(World par0World, ItemStack par1ItemStack, String par2EntityName,
                                        double par3PosX, double par4PosY, double par6PosZ, String par7PlayerName) {
        if( !pets.containsKey(par1ItemStack.getItemDamage()) ) {
            return false;
        } else {
            Entity var8 = EntityList.createEntityByName(par2EntityName, par0World);
            NBTTagCompound nbt = par1ItemStack.hasTagCompound() ? par1ItemStack.getTagCompound() : null;

            if( var8 != null && nbt != null && nbt.hasKey("petID") ) {
                var8.setLocationAndAngles(par3PosX, par4PosY, par6PosZ, par0World.rand.nextFloat() * 360.0F, 0.0F);

                if( var8 instanceof EntityEnderMiss ) {
                    EntityEnderMiss var9 = (EntityEnderMiss) var8;
                    var9.setTamed(true);
                    var9.ownerName = par7PlayerName;
                    var9.setHealth(nbt.getFloat("missHealth"));
                    if( nbt.hasKey("missCoatColor") && nbt.hasKey("missCoatBase") ) {
                        var9.setCoat(nbt.getInteger("missCoatBase"), nbt.getInteger("missCoatColor"));
                    } else {
                        var9.setCoat(0, nbt.getBoolean("missWaterImmune") ? 16 : -1);
                    }
                    var9.setCanGetFallDmg(nbt.getBoolean("missNoFallDmg"));
                    if( nbt.hasKey("missColor") ) {
                        var9.setColor(nbt.getInteger("missColor"));
                    } else {
                        var9.setColor(par0World.rand.nextInt(ItemRaincoat.colorList.size() - 3));
                    }
                    var9.setSpecial(nbt.getBoolean("missSpecial"));
                    par0World.spawnEntityInWorld(var9);
                    return true;
                } else if( var8 instanceof EntityEnderAvis ) {
                    EntityEnderAvis var9 = (EntityEnderAvis) var8;
                    var9.setTamed(true);
                    var9.setOwner(par7PlayerName);
                    var9.setHealth(nbt.getFloat("avisHealth"));
                    var9.setCondition(nbt.getFloat("avisCondition"));
                    if( nbt.hasKey("avisCoatColor") && nbt.hasKey("avisCoatBase") ) {
                        var9.setCoat(nbt.getInteger("avisCoatBase"), nbt.getInteger("avisCoatColor"));
                    } else {
                        var9.setCoat(0, nbt.getBoolean("avisWaterImmune") ? 16 : -1);
                    }
                    if( nbt.hasKey("avisColor") ) {
                        var9.setColor(nbt.getInteger("avisColor"));
                    } else {
                        var9.setColor(par0World.rand.nextInt(ItemRaincoat.colorList.size() - 3));
                    }
                    var9.setSaddled(nbt.getBoolean("avisSaddle"));
                    par0World.spawnEntityInWorld(var9);
                    return true;
                }

                par0World.spawnEntityInWorld(var8);
                ((EntityLiving) var8).playLivingSound();
            }

            return var8 != null;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        ItemStack pet = new ItemStack(this, 1, 0);
        NBTTagCompound nbt = new NBTTagCompound("enderPetEgg");
        nbt.setByte("petID", (byte) 0);
        nbt.setFloat("missHealth", 40.0F);
        nbt.setBoolean("missNoFallDmg", false);
        nbt.setBoolean("missSpecial", false);
        pet.setTagCompound(nbt);
        par3List.add(pet.copy());
        nbt.setBoolean("missSpecial", true);
        pet.setTagCompound(nbt);
        par3List.add(pet.copy());
        pet = new ItemStack(this, 1, 1);
        nbt = new NBTTagCompound("enderPetEgg");
        nbt.setByte("petID", (byte) 1);
        nbt.setFloat("avisHealth", 40.0F);
        nbt.setFloat("avisCondition", 10.0F);
        nbt.setBoolean("avisSaddle", false);
        pet.setTagCompound(nbt);
        par3List.add(pet.copy());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        int par1 = par1ItemStack.getItemDamage();
        return (Integer) (pets.containsKey(par1) ? (par2 == 0 ? pets.get(par1)[1] : pets.get(par1)[2]) : 0xFFFFFF);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamageForRenderPass(int par1, int par2) {
        return Item.monsterPlacer.getIconFromDamageForRenderPass(par1, par2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack) {
        return par1ItemStack.hasTagCompound();
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List par2List, boolean b) {
        if( par1ItemStack.hasTagCompound() ) {
            NBTTagCompound nbt = par1ItemStack.getTagCompound();
            if( par1ItemStack.getItemDamage() == 0 ) {
                par2List.add(String.format("%s: \2473%s", CUS.getTranslated("enderstuffplus.petegg.health"),
                                           (int) (nbt.getFloat("missHealth") / 40F * 100F) + "%"));
                par2List.add(String.format("%s: \2473%s",
                                           CUS.getTranslated("enderstuffplus.petegg.hasSpecSkin"),
                                           nbt.getBoolean("missSpecial") ? CUS.getTranslated("enderstuffplus.petegg.true")
                                                                        : CUS.getTranslated("enderstuffplus.petegg.false")));
                par2List.add(String.format("%s: \2473%s",
                                           CUS.getTranslated("enderstuffplus.petegg.immuneToH2O"),
                                           nbt.hasKey("missCoatColor") && nbt.hasKey("missCoatBase") ? CUS.getTranslated("enderstuffplus.petegg.true")
                                                                                                    : CUS.getTranslated("enderstuffplus.petegg.false")));
                par2List.add(String.format("%s: \2473%s",
                                           CUS.getTranslated("enderstuffplus.petegg.fallDmg"),
                                           nbt.getBoolean("missNoFallDmg") ? CUS.getTranslated("enderstuffplus.petegg.true")
                                                                          : CUS.getTranslated("enderstuffplus.petegg.false")));
            } else if( par1ItemStack.getItemDamage() == 1 ) {
                par2List.add(String.format("%s: \2473%s", CUS.getTranslated("enderstuffplus.petegg.health"),
                                           (int) (nbt.getFloat("avisHealth") / 40F * 100F) + "%"));
                par2List.add(String.format("%s: \2473%s", CUS.getTranslated("enderstuffplus.petegg.condition"),
                                           (int) (nbt.getFloat("avisCondition") * 10F) + "%"));
                par2List.add(String.format("%s: \2473%s",
                                           CUS.getTranslated("enderstuffplus.petegg.saddle"),
                                           nbt.getBoolean("avisSaddle") ? CUS.getTranslated("enderstuffplus.petegg.true")
                                                                       : CUS.getTranslated("enderstuffplus.petegg.false")));
                par2List.add(String.format("%s: \2473%s",
                                           CUS.getTranslated("enderstuffplus.petegg.immuneToH2O"),
                                           nbt.hasKey("avisCoatColor") && nbt.hasKey("avisCoatBase") ? CUS.getTranslated("enderstuffplus.petegg.true")
                                                                                                    : CUS.getTranslated("enderstuffplus.petegg.false")));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {}
}
