package sanandreasp.mods.EnderStuffPlus.item;

import java.util.ArrayList;
import java.util.List;

import sanandreasp.mods.EnderStuffPlus.client.event.IconRegistry;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAvisCompass
    extends Item
{
    private static final List<TileEntity> AVIS_EGGS = new ArrayList<TileEntity>();

    public ItemAvisCompass(int par1) {
        super(par1);
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
    public Icon getIconFromDamage(int dmg) {
        return IconRegistry.compass;
    }
}
