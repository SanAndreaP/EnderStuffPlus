package sanandreasp.mods.EnderStuffPlus.item;

import java.util.List;

import sanandreasp.core.manpack.helpers.SAPUtils;
import sanandreasp.mods.EnderStuffPlus.registry.RegistryRaincoats;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRaincoat
    extends Item
{
    @SideOnly(Side.CLIENT)
    private Icon iconBase;
    @SideOnly(Side.CLIENT)
    private Icon iconOver;

    public ItemRaincoat(int id) {
        super(id);
        this.setMaxDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addInformation(ItemStack stack, EntityPlayer player, List infos, boolean isAdvancedInfo) {
        if( stack.hasTagCompound() ) {
            String base = stack.getTagCompound().getString("base");
            if( RegistryRaincoats.BASE_LIST.containsKey(base) ) {
                RegistryRaincoats.CoatBaseEntry entry = RegistryRaincoats.BASE_LIST.get(base);
                infos.add("\247o" + SAPUtils.getTranslated(entry.name));
                String[] split = entry.desc.split("\n");
                for( String effect : split ) {
                    infos.add("\2473" + effect);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if( stack.hasTagCompound() ) {
            String base = stack.getTagCompound().getString("base");
            String color = stack.getTagCompound().getString("color");
            if( pass == 0 && RegistryRaincoats.COLOR_LIST.containsKey(color) ) {
                return RegistryRaincoats.COLOR_LIST.get(color).color;
            } else if( pass > 0 && RegistryRaincoats.BASE_LIST.containsKey(base) ) {
                return RegistryRaincoats.BASE_LIST.get(base).color;
            }
        }

        return super.getColorFromItemStack(stack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamageForRenderPass(int damage, int pass) {
        return pass == 0 ? this.iconBase : this.iconOver;
    }

    @Override
    public String getItemDisplayName(ItemStack par1ItemStack) {
        if( par1ItemStack.getTagCompound() != null ) {
            String clr = par1ItemStack.getTagCompound().getString("color");
            if( RegistryRaincoats.COLOR_LIST.containsKey(clr) ) {
                return String.format(super.getItemDisplayName(par1ItemStack),
                                     SAPUtils.getTranslated(RegistryRaincoats.COLOR_LIST.get(clr).name));
            }
        }
        return String.format(super.getItemDisplayName(par1ItemStack), "[UNKNOWN]");
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for( String base : RegistryRaincoats.BASE_LIST.keySet() ) {
            for( String color : RegistryRaincoats.COLOR_LIST.keySet() ) {
                NBTTagCompound nbt = new NBTTagCompound();
                ItemStack is = new ItemStack(this, 1, 0);
                nbt.setString("base", base);
                nbt.setString("color", color);
                is.setTagCompound(nbt);
                par3List.add(is);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = this.iconBase = par1IconRegister.registerIcon("enderstuffp:rainCoatBase");
        this.iconOver = par1IconRegister.registerIcon("enderstuffp:rainCoatStripes");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
