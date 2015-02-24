package de.sanandrew.mods.enderstuffp.block;

import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.world.IBlockAccess;

public class BlockEnderWood
    extends Block
{
    public BlockEnderWood() {
        super(Material.wood);
        this.setBlockName(EnderStuffPlus.MOD_ID + ":enderWood");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setStepSound(Block.soundTypeWood);
        this.setBlockTextureName(EnderStuffPlus.MOD_ID + ":enderWood");
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess blockAccess, int x, int y, int z, Entity entity) {
        return !(entity instanceof EntityDragon);
    }
}
