package de.sanandrew.mods.enderstuffp.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.tileentity.TileEntityBiomeChanger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBiomeChanger
    extends BlockContainer
{
    public BlockBiomeChanger(Material material) {
        super(material);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldMeta) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if( (tileEntity instanceof TileEntityBiomeChanger) && !world.isRemote ) {
            TileEntityBiomeChanger teBiomeChager = (TileEntityBiomeChanger) tileEntity;

//            for( int i = 0; i < teBiomeChager.getSizeInventory(); i++ ) {
//                ItemStack stack = teBiomeChager.getStackInSlot(i);
//
//                if( (stack != null) && (stack.stackSize > 0) ) {
//                    world.spawnEntityInWorld(new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, stack));
//                }
//            }

//            if( teBiomeChager.getPrevFuelItem() != null ) {
//                int itemCnt = (teBiomeChager.getMaxRange() - teBiomeChager.getCurrRange())
//                             * RegistryBiomeChanger.getMultiFromStack(teBiomeChager.getPrevFuelItem());
//
//                while( itemCnt > 0 ) {
//                    ItemStack stack = teBiomeChager.getPrevFuelItem().copy();
//
//                    if( itemCnt > teBiomeChager.getPrevFuelItem().getMaxStackSize() ) {
//                        itemCnt -= stack.stackSize = teBiomeChager.getPrevFuelItem().getMaxStackSize();
//                    } else {
//                        stack.stackSize = itemCnt;
//                        itemCnt = 0;
//                    }
//
//                    world.spawnEntityInWorld(new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, stack));
//                }
//            }

            tileEntity.invalidate();
        }

        super.breakBlock(world, x, y, z, oldBlock, oldMeta);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBiomeChanger();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x + 0.0625F, y, z + 0.0625F, x + 0.9375F, y + 0.5625F, z + 0.9375F);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x + 0.0625F, y, z + 0.0625F, x + 0.9375F, y + 0.5625F, z + 0.9375F);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
//        int id = 1;
        TileEntityBiomeChanger tileEntity = (TileEntityBiomeChanger) world.getTileEntity(x, y, z);

//        if( (tileEntity instanceof TileEntityBiomeChanger) && ((TileEntityBiomeChanger) tileEntity).isActive() ) {
//            id = 3;
//        }

//        player.openGui(EnderStuffPlus.instance, id, world, x, y, z);

        player.addChatMessage(new ChatComponentText(Integer.toString(tileEntity.getEnergyStored(ForgeDirection.UP))));

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = Blocks.obsidian.getIcon(0, 0);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.5625F, 0.9375F);
    }
}
