package de.sanandrew.mods.enderstuffp.item;

import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import de.sanandrew.mods.enderstuffp.util.EspBlocks;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemNiobDoor
    extends Item
{
    public ItemNiobDoor() {
        super();
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":doorNiob");
        this.setTextureName(EnderStuffPlus.MOD_ID + ":doorNiob");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
        this.setMaxStackSize(1);
    }

    public static void placeDoorBlock(World world, int x, int y, int z, int side, Block block) {
        byte xShift = 0;
        byte zShift = 0;

        switch( side ) {
            case 0 :
                zShift = 1;
                break;
            case 1 :
                xShift = -1;
                break;
            case 2 :
                zShift = -1;
                break;
            case 3 :
                xShift = 1;
                break;
        }
        //TODO: find meaningful names for those variables!
        int i1 = (world.isBlockNormalCubeDefault(x - xShift, y, z - zShift, false) ? 1 : 0)
                 + (world.isBlockNormalCubeDefault(x - xShift, y + 1, z - zShift, false) ? 1 : 0);
        int j1 = (world.isBlockNormalCubeDefault(x + xShift, y, z + zShift, false) ? 1 : 0)
                 + (world.isBlockNormalCubeDefault(x + xShift, y + 1, z + zShift, false) ? 1 : 0);
        boolean flag = world.getBlock(x - xShift, y, z - zShift) == block
                       || world.getBlock(x - xShift, y + 1, z - zShift) == block;
        boolean flag1 = world.getBlock(x + xShift, y, z + zShift) == block
                        || world.getBlock(x + xShift, y + 1, z + zShift) == block;
        boolean flag2 = false;

        if( flag && !flag1 ) {
            flag2 = true;
        } else if( j1 > i1 ) {
            flag2 = true;
        }

        world.setBlock(x, y, z, block, side, 2);
        world.setBlock(x, y + 1, z, block, 8, 2);
        world.setBlock(x, y + 2, z, block, 8 | (flag2 ? 1 : 0), 2);
        world.notifyBlocksOfNeighborChange(x, y, z, block);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, block);
        world.notifyBlocksOfNeighborChange(x, y + 2, z, block);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset) {
        if( side != 1 ) {
            return false;
        } else {
            ++y;

            Block block = EspBlocks.blockEndDoor;

            if( player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack) ) {
                if( !block.canPlaceBlockAt(world, x, y, z) ) {
                    return false;
                } else {
                    int doorSide = MathHelper.floor_double((player.rotationYaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 3;
                    placeDoorBlock(world, x, y, z, doorSide, block);
                    --stack.stackSize;
                    return true;
                }
            } else {
                return false;
            }
        }
    }
}
