package de.sanandrew.mods.enderstuffplus.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import de.sanandrew.mods.enderstuffplus.tileentity.TileEntityAvisEgg;

public class BlockAvisEgg
    extends BlockDragonEgg
    implements ITileEntityProvider
{
    public BlockAvisEgg() {
        super();
        this.isBlockContainer = true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldMeta) {
        super.breakBlock(world, x, y, z, oldBlock, oldMeta);
        world.removeTileEntity(x, y, z);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityAvisEgg();
    }

    @Override
    public Item getItem(World world, int x, int y, int z) {
        return super.getItem(world, x, y, z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset,
                                    float yOffset, float zOffset) {
        return false;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {}

    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
        world.removeTileEntity(x, y, z);
        super.onBlockDestroyedByExplosion(world, x, y, z, explosion);
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
        world.removeTileEntity(x, y, z);
        super.onBlockDestroyedByPlayer(world, x, y, z, meta);
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int evtId, int evtParam) {
        super.onBlockEventReceived(world, x, y, z, evtId, evtParam);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return (tileentity != null) ? tileentity.receiveClientEvent(evtId, evtParam) : false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
        if( entity instanceof EntityPlayer ) {
            TileEntityAvisEgg teAvisEgg = (TileEntityAvisEgg) world.getTileEntity(x, y, z);

            if( teAvisEgg != null ) {
                teAvisEgg.setPlayerName(((EntityPlayer) entity).getCommandSenderName());
            }
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {}
}
