package sanandreasp.mods.EnderStuffPlus.block;

import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityAvisEgg;

import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAvisEgg
    extends BlockDragonEgg
    implements ITileEntityProvider
{
    public BlockAvisEgg(int id) {
        super(id);
        this.isBlockContainer = true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int oldId, int oldMeta) {
        super.breakBlock(world, x, y, z, oldId, oldMeta);
        world.removeBlockTileEntity(x, y, z);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityAvisEgg();
    }

    @Override
    public int idPicked(World world, int x, int y, int z) {
        return this.blockID;
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
        world.removeBlockTileEntity(x, y, z);
        super.onBlockDestroyedByExplosion(world, x, y, z, explosion);
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
        world.removeBlockTileEntity(x, y, z);
        super.onBlockDestroyedByPlayer(world, x, y, z, meta);
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int evtId, int evtParam) {
        super.onBlockEventReceived(world, x, y, z, evtId, evtParam);
        TileEntity tileentity = world.getBlockTileEntity(x, y, z);
        return (tileentity != null) ? tileentity.receiveClientEvent(evtId, evtParam) : false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
        if( entity instanceof EntityPlayer ) {
            TileEntityAvisEgg teAvisEgg = (TileEntityAvisEgg) world.getBlockTileEntity(x, y, z);

            if( teAvisEgg != null ) {
                teAvisEgg.playerName = ((EntityPlayer) entity).username;
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("enderstuffp:eggAvis");
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {}
}
