package sanandreasp.mods.EnderStuffPlus.item;

import sanandreasp.mods.EnderStuffPlus.registry.ModBlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemNiobDoor
    extends Item
{
    public ItemNiobDoor(int id) {
        super(id);
        this.maxStackSize = 1;
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
        int i1 = (world.isBlockNormalCube(x - xShift, y, z - zShift) ? 1 : 0)
                 + (world.isBlockNormalCube(x - xShift, y + 1, z - zShift) ? 1 : 0);
        int j1 = (world.isBlockNormalCube(x + xShift, y, z + zShift) ? 1 : 0)
                 + (world.isBlockNormalCube(x + xShift, y + 1, z + zShift) ? 1 : 0);
        boolean flag = world.getBlockId(x - xShift, y, z - zShift) == block.blockID
                       || world.getBlockId(x - xShift, y + 1, z - zShift) == block.blockID;
        boolean flag1 = world.getBlockId(x + xShift, y, z + zShift) == block.blockID
                        || world.getBlockId(x + xShift, y + 1, z + zShift) == block.blockID;
        boolean flag2 = false;

        if( flag && !flag1 ) {
            flag2 = true;
        } else if( j1 > i1 ) {
            flag2 = true;
        }

        world.setBlock(x, y, z, block.blockID, side, 2);
        world.setBlock(x, y + 1, z, block.blockID, 8, 2);
        world.setBlock(x, y + 2, z, block.blockID, 8 | (flag2 ? 1 : 0), 2);
        world.notifyBlocksOfNeighborChange(x, y, z, block.blockID);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, block.blockID);
        world.notifyBlocksOfNeighborChange(x, y + 2, z, block.blockID);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xOffset,
                             float yOffset, float zOffset) {
        if( side != 1 ) {
            return false;
        } else {
            ++y;

            Block block = ModBlockRegistry.blockEndDoor;

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
