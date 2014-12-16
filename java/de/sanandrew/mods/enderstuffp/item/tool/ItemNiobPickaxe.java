package de.sanandrew.mods.enderstuffp.item.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.enderstuffp.util.ConfigRegistry;
import de.sanandrew.mods.enderstuffp.util.CreativeTabsEnderStuff;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EspItems;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemNiobPickaxe
    extends ItemPickaxe
{
    @SideOnly(Side.CLIENT)
    private IIcon glowMap;

    public ItemNiobPickaxe(ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.setUnlocalizedName(EnderStuffPlus.MOD_ID + ":niobPick");
        this.setCreativeTab(CreativeTabsEnderStuff.ESP_TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return this.getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        return pass == 1 ? this.glowMap : this.itemIcon;
    }

    @Override
    public boolean getIsRepairable(ItemStack brokenItem, ItemStack repairItem) {
        return repairItem.getItem() == EspItems.endIngot || super.getIsRepairable(brokenItem, repairItem);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        return NiobToolHelper.onBlockStartBreak(stack, x, y, z, player, true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":niobPick");
        this.glowMap = iconRegister.registerIcon(EnderStuffPlus.MOD_ID + ":niobPickGlow" + (ConfigRegistry.useNiobHDGlow ? "HD" : ""));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
}
