package de.sanandrew.mods.enderstuffplus.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.client.helpers.SAPClientUtils;
import de.sanandrew.mods.enderstuffplus.registry.raincoat.RegistryRaincoats;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemRaincoat
    extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon iconBase;
    @SideOnly(Side.CLIENT)
    private IIcon iconOver;

    public ItemRaincoat() {
        super();
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
                infos.add("\247o" + SAPClientUtils.translate(entry.name));
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
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        return pass == 0 ? this.iconBase : this.iconOver;
    }

    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        if( par1ItemStack.getTagCompound() != null ) {
            String clr = par1ItemStack.getTagCompound().getString("color");
            if( RegistryRaincoats.COLOR_LIST.containsKey(clr) ) {
                return String.format(super.getItemStackDisplayName(par1ItemStack), SAPClientUtils.translate(RegistryRaincoats.COLOR_LIST.get(clr).name));
            }
        }
        return String.format(super.getItemStackDisplayName(par1ItemStack), "[UNKNOWN]");
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List par3List) {
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
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = this.iconBase = par1IconRegister.registerIcon("enderstuffp:rainCoatBase");
        this.iconOver = par1IconRegister.registerIcon("enderstuffp:rainCoatStripes");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
