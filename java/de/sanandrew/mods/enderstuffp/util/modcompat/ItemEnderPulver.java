/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.util.modcompat;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.util.CreativeTabsEnderStuff;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemEnderPulver
        extends Item
{
    public static final int NIOBIUM = 0;
    public static final int TANTALUM = 1;

    private final String[][] types = new String[][] {
            new String[] {"niobium", "pulver_niobium"},
            new String[] {"tantalum", "pulver_tantalum"}
    };

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemEnderPulver() {
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":enderPulver");
        this.setCreativeTab(CreativeTabsEnderStuff.ESP_TAB);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + '_' + this.types[stack.getItemDamage()][0];
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.icons = new IIcon[this.types.length];
        for( int i = 0; i < this.types.length; i++ ) {
            this.icons[i] = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ':' + this.types[i][1]);
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
        for( int i = 0; i < this.types.length; i++ ) {
            stacks.add(new ItemStack(this, 1, i));
        }
    }
}
