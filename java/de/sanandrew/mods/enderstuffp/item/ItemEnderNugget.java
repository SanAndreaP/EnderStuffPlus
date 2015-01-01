/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EnumEnderOres;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemEnderNugget
        extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemEnderNugget() {
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":enderNugget");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return EnumEnderOres.getType(stack.getItemDamage()).nuggetName;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.icons = new IIcon[EnumEnderOres.COUNT];
        for( int i = 0; i < this.icons.length; i++ ) {
            this.icons[i] = iconRegister.registerIcon(EnumEnderOres.getType(i).nuggetTexture);
        }
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return this.icons[damage];
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubItems(Item item, CreativeTabs creativeTab, List stacks) {
        for( int i = 0; i < EnumEnderOres.COUNT; i++ ) {
            stacks.add(new ItemStack(this, 1, i));
        }
    }
}
