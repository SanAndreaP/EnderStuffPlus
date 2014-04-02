package sanandreasp.mods.EnderStuffPlus.item.tool;

import java.util.ArrayList;
import java.util.List;

import sanandreasp.core.manpack.helpers.CommonUsedStuff;
import sanandreasp.mods.EnderStuffPlus.registry.ConfigRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ESPModRegistry;
import sanandreasp.mods.EnderStuffPlus.registry.ModItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import net.minecraftforge.common.IPlantable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNiobHoe
    extends ItemHoe
{
    private List<Block> effectiveBlocks = new ArrayList<Block>();
    @SideOnly(Side.CLIENT)
    private Icon glowMap;

    public ItemNiobHoe(int id, EnumToolMaterial toolMaterial) {
        super(id, toolMaterial);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(ItemStack stack, int pass) {
        return pass == 1 ? this.glowMap : super.getIcon(stack, pass);
    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return repairItem.itemID == ModItemRegistry.endIngot.itemID ? true : super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    public int getItemEnchantability() {
        return this.theToolMaterial.getEnchantability();
    }

    private Block[] getPlants() {
        if( this.effectiveBlocks.size() > 0 ) {
            return this.effectiveBlocks.toArray(CommonUsedStuff.getArrayFromList(this.effectiveBlocks, Block.class));
        }
        List<Block> blocks = new ArrayList<Block>();
        for( Block block : Block.blocksList ) {
            if( block instanceof IPlantable ) {
                blocks.add(block);
            }
        }
        this.effectiveBlocks = new ArrayList<Block>(blocks);
        return blocks.toArray(CommonUsedStuff.getArrayFromList(blocks, Block.class));
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        return NiobToolHelper.onBlockStartBreak(stack, x, y, z, player, this.getPlants(), false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        super.registerIcons(iconRegister);
        this.glowMap = iconRegister.registerIcon(ESPModRegistry.MOD_ID + ":niobHoeGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
