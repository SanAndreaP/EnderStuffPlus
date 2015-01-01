package de.sanandrew.mods.enderstuffp.item.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.enderstuffp.util.*;
import de.sanandrew.mods.enderstuffp.util.manager.NiobiumToolManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;

public class ItemNiobiumHoe
        extends ItemHoe
{
    private List<Block> effectiveBlocks;
    @SideOnly(Side.CLIENT)
    private IIcon glowMap;

    public ItemNiobiumHoe(ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":hoeNiobium");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 1 ? this.glowMap : super.getIcon(stack, pass);
    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return SAPUtils.areStacksEqual(repairItem, EnumEnderOres.REPAIR_ITEM_NIOBIUM, false) || super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    public int getItemEnchantability() {
        return this.theToolMaterial.getEnchantability();
    }

    private Block[] getPlants() {
        if( this.effectiveBlocks != null && this.effectiveBlocks.size() > 0 ) {
            return SAPUtils.getArrayFromCollection(this.effectiveBlocks, Block.class);
        }

        List<Block> blocks = new ArrayList<>(256);
        for( Object blockKey : Block.blockRegistry.getKeys() ) {
            Block block = (Block) Block.blockRegistry.getObject(blockKey);
            if( block instanceof IPlantable ) {
                blocks.add(block);
            }
        }

        this.effectiveBlocks = new ArrayList<>(blocks);
        return SAPUtils.getArrayFromCollection(blocks, Block.class);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        return NiobiumToolManager.onBlockStartBreak(stack, x, y, z, player, this.getPlants(), false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":hoe_niobium");
        this.glowMap = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":hoe_niobium_glow" + (EspConfiguration.useNiobHDGlow ? "_hd" : ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
