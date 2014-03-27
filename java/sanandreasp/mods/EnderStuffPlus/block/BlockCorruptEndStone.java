package sanandreasp.mods.EnderStuffPlus.block;

import sanandreasp.core.manpack.helpers.client.IconFlippedFixed;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCorruptEndStone
    extends Block
{
    @SideOnly(Side.CLIENT)
    private Icon[] icons = new Icon[4];

    public BlockCorruptEndStone(int id) {
        super(id, Block.whiteStone.blockMaterial);
        this.setUnlocalizedName("esp:corruptEndstone");
        this.setCreativeTab(ESPModRegistry.espTab);
        this.setHardness(Block.whiteStone.blockHardness);
        this.setResistance(Block.whiteStone.blockResistance);
        this.setStepSound(Block.soundStoneFootstep);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return this.icons[Math.min(3, blockAccess.getBlockMetadata(x, y, z))];
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, world.rand.nextInt(4), 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("enderstuffp:corrupt_end_stone");
        this.icons[0] = this.blockIcon;
        this.icons[1] = new IconFlippedFixed(this.blockIcon, true, false);
        this.icons[2] = new IconFlippedFixed(this.blockIcon, false, true);
        this.icons[3] = new IconFlippedFixed(this.blockIcon, true, true);
    }
}
