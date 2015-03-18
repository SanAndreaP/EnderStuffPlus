/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.block;

import de.sanandrew.mods.enderstuffp.client.render.BlockRendererOreCrocoite;
import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import de.sanandrew.mods.enderstuffp.util.EspCreativeTabs;
import net.minecraft.block.BlockOre;

public class BlockOreCrocoite
        extends BlockOre
{
    public static int currRenderPass;

    public BlockOreCrocoite() {
        super();
        this.setBlockName(EnderStuffPlus.MOD_ID + ":oreCrocoite");
        this.setCreativeTab(EspCreativeTabs.ESP_TAB);
    }

    @Override
    public int getRenderType() {
        return BlockRendererOreCrocoite.renderId;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getLightOpacity() {
        return super.getLightOpacity();
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean canRenderInPass(int pass) {
        currRenderPass = pass;
        return pass <= 1;
    }
}
