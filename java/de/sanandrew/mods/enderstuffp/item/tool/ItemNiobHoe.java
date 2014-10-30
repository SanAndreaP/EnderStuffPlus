package de.sanandrew.mods.enderstuffp.item.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.enderstuffp.util.ConfigRegistry;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.RegistryItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;

public class ItemNiobHoe
        extends ItemHoe
{
    private List<Block> effectiveBlocks = new ArrayList<>();
    @SideOnly(Side.CLIENT)
    private IIcon glowMap;

    public ItemNiobHoe(ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":niobHoe");
        this.setCreativeTab(EnderStuffPlus.ESP_TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 1 ? this.glowMap : super.getIcon(stack, pass);
    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return repairItem.getItem() == RegistryItems.endIngot || super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    public int getItemEnchantability() {
        return this.theToolMaterial.getEnchantability();
    }

    private Block[] getPlants() {
        if( this.effectiveBlocks.size() > 0 ) {
            return this.effectiveBlocks.toArray(SAPUtils.getArrayFromCollection(this.effectiveBlocks, Block.class));
        }
        List<Block> blocks = new ArrayList<>();
        for( Object blockKey : Block.blockRegistry.getKeys() ) {
            Block block = (Block) Block.blockRegistry.getObject(blockKey);
            if( block instanceof IPlantable ) {
                blocks.add(block);
            }
        }
        this.effectiveBlocks = new ArrayList<>(blocks);
        return blocks.toArray(SAPUtils.getArrayFromCollection(blocks, Block.class));
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        return NiobToolHelper.onBlockStartBreak(stack, x, y, z, player, this.getPlants(), false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":niobHoe");
        this.glowMap = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":niobHoeGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
