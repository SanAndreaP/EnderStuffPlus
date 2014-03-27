package sanandreasp.mods.EnderStuffPlus.block;

import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEnderWood
    extends Block
{
    public BlockEnderWood(int id) {
        super(id, Material.wood);
        this.setUnlocalizedName("esp:enderWood");
        this.setCreativeTab(ESPModRegistry.espTab);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setStepSound(Block.soundWoodFootstep);
    }

    @Override
    public boolean canDragonDestroy(World world, int x, int y, int z) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("enderstuffp:enderWood" + (!ConfigRegistry.useAnimations ? "_NA" : ""));
    }
}
