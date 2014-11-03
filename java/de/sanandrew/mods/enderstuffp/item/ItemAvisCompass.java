package de.sanandrew.mods.enderstuffp.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.client.event.TextureStitchHandler;
import de.sanandrew.mods.enderstuffp.util.CreativeTabsEnderStuff;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class ItemAvisCompass
    extends Item
{
    private static final List<TileEntity> AVIS_EGGS = new ArrayList<>();

    public ItemAvisCompass() {
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":avisCompass");
        this.setTextureName(EnderStuffPlus.MOD_ID + ":compass");
        this.setCreativeTab(CreativeTabsEnderStuff.ESP_TAB);
    }

    @SideOnly(Side.CLIENT)
    public static void addEgg(TileEntity egg) {
        if( !AVIS_EGGS.contains(egg) ) {
            AVIS_EGGS.add(egg);
        }
    }

    @SideOnly(Side.CLIENT)
    public static TileEntity getNearestEgg(int x, int z) {
        int minX = -1;
        int minZ = -1;
        TileEntity nearestEgg = null;
        int vecX;
        int vecZ;

        for( TileEntity te : AVIS_EGGS ) {
            vecX = Math.abs(te.xCoord - x);
            vecZ = Math.abs(te.zCoord - z);

            if( (Math.sqrt(vecX * vecX + vecZ * vecZ) < Math.sqrt(minX * minX + minZ * minZ)) || (minX + minZ < 0) ) {
                minX = vecX;
                minZ = vecZ;
                nearestEgg = te;
            }
        }

        return nearestEgg;
    }

    @SideOnly(Side.CLIENT)
    public static void removeEgg(TileEntity egg) {
        if( AVIS_EGGS.contains(egg) ) {
            AVIS_EGGS.remove(egg);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = TextureStitchHandler.compass;
    }
}
