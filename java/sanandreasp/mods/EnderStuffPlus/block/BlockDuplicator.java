package sanandreasp.mods.EnderStuffPlus.block;

import java.util.Random;

import sanandreasp.mods.EnderStuffPlus.client.particle.ParticleFXFuncCollection;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.tileentity.TileEntityDuplicator;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDuplicator
    extends BlockDirectional
    implements ITileEntityProvider
{
    @SideOnly(Side.CLIENT)
    private Icon topIcon;
    @SideOnly(Side.CLIENT)
    private Icon sideIcon;
    @SideOnly(Side.CLIENT)
    private Icon frontOnIcon;
    @SideOnly(Side.CLIENT)
    private Icon frontOffIcon;

    public BlockDuplicator(int id) {
        super(id, Material.rock);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int oldId, int oldMeta) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if( (tileEntity instanceof TileEntityDuplicator) && !world.isRemote ) {
            TileEntityDuplicator teDuplicator = (TileEntityDuplicator) tileEntity;

            for( int i = 0; i < teDuplicator.getSizeInventory(); i++ ) {
                ItemStack stack = teDuplicator.getStackInSlot(i);

                if( (stack != null) && (stack.stackSize > 0) ) {
                    world.spawnEntityInWorld(new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, stack));
                }
            }

            tileEntity.invalidate();
        }

        super.breakBlock(world, x, y, z, oldId, oldMeta);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityDuplicator();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int meta = blockAccess.getBlockMetadata(x, y, z);

        return this.getIcon(side, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        int stripMeta = meta & 3;
        boolean isActive = (meta & 4) == 4;

        switch( side ) {
            case 0 :
                // fallthrough;
            case 1 :
                return this.topIcon;
            case 2 :
                return (stripMeta == 2) ? isActive ? this.frontOnIcon : this.frontOffIcon : this.sideIcon;
            case 3 :
                return (stripMeta == 0) ? isActive ? this.frontOnIcon : this.frontOffIcon : this.sideIcon;
            case 4 :
                return (stripMeta == 1) ? isActive ? this.frontOnIcon : this.frontOffIcon : this.sideIcon;
            case 5 :
                return (stripMeta == 3) ? isActive ? this.frontOnIcon : this.frontOffIcon : this.sideIcon;
            default :
                return this.frontOffIcon;
        }
    }

    @Override
    public int getLightValue(IBlockAccess blockAccess, int x, int y, int z) {
        boolean isActive = (blockAccess.getBlockMetadata(x, y, z) & 4) == 4;

        return isActive ? 15 : super.getLightValue(blockAccess, x, y, z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset,
                                    float yOffset, float zOffset) {
        player.openGui(ESPModRegistry.instance, 4, world, x, y, z);
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int meta = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
        int newBlockId = world.getBlockId(x, y, z);

        world.setBlock(x, y, z, newBlockId, meta, 3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        ParticleFXFuncCollection.spawnDuplicatorFX(world, x, y, z, random);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.topIcon = iconRegister.registerIcon("enderstuffp:dupe_top");
        this.sideIcon = iconRegister.registerIcon("enderstuffp:dupe_side");
        this.frontOnIcon = iconRegister.registerIcon("enderstuffp:dupe_frontOn");
        this.frontOffIcon = iconRegister.registerIcon("enderstuffp:dupe_frontOff");
    }
}
