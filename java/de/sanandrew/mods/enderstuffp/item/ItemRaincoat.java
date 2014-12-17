package de.sanandrew.mods.enderstuffp.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.util.CreativeTabsEnderStuff;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager.CoatBaseEntry;
import de.sanandrew.mods.enderstuffp.util.manager.raincoat.RaincoatManager.CoatColorEntry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemRaincoat
    extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon iconOver;

    public ItemRaincoat() {
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":rainCoat");
        this.setCreativeTab(CreativeTabsEnderStuff.ESP_TAB_COATS);
        this.setMaxDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addInformation(ItemStack stack, EntityPlayer player, List infos, boolean isAdvancedInfo) {
        if( stack.hasTagCompound() ) {
            CoatColorEntry clr = RaincoatManager.getColor(stack.getTagCompound().getString("color"));
            infos.add(EnumChatFormatting.BOLD + SAPUtils.translate(clr.getUnlocalizedName()));


            CoatBaseEntry entry = RaincoatManager.getBase(stack.getTagCompound().getString("base"));
            if( entry != RaincoatManager.NULL_BASE ) {
                infos.add(EnumChatFormatting.ITALIC + SAPUtils.translate(entry.getUnlocalizedName()));
                String[] split = SAPUtils.translate(entry.desc).split("<BREAK>");
                for( String effect : split ) {
                    infos.add(EnumChatFormatting.DARK_AQUA + effect);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if( stack.hasTagCompound() ) {
            CoatBaseEntry base = RaincoatManager.getBase(stack.getTagCompound().getString("base"));
            CoatColorEntry color = RaincoatManager.getColor(stack.getTagCompound().getString("color"));
            if( pass == 0 ) {
                return color.color;
            } else if( pass > 0 ) {
                return base.color;
            }
        }

        return super.getColorFromItemStack(stack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        return pass == 0 ? this.itemIcon : this.iconOver;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List par3List) {
        for( String base : RaincoatManager.getBaseList() ) {
            for( String color : RaincoatManager.getColorList() ) {
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
        this.itemIcon = par1IconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":rainCoatBase");
        this.iconOver = par1IconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":rainCoatStripes");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
