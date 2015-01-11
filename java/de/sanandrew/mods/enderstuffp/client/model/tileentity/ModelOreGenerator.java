/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.enderstuffp.client.model.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.client.helpers.SAPClientUtils;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

@SideOnly(Side.CLIENT)
public class ModelOreGenerator
        extends ModelBase
{
    private ModelRenderer stanceBackLeft;
    private ModelRenderer stanceBackRight;
    private ModelRenderer stanceFrontLeft;
    private ModelRenderer stanceFrontRight;
    private ModelRenderer grinderAxis1;
    private ModelRenderer grinderAxis2;
    private ModelRenderer sideLeft;
    private ModelRenderer sideRight;
    private ModelRenderer ramp;
    private ModelRenderer storage;
    private ModelRenderer sieveBottom;
    private ModelRenderer sieveTop;
    private ModelRenderer reinfBarBottomBackT;
    private ModelRenderer reinfBarBottomBackB;
    private ModelRenderer reinfBarBottomFrontT;
    private ModelRenderer reinfBarBottomFrontB;
    private ModelRenderer reinfBarTopBack;
    private ModelRenderer reinfBarBottomRightB;
    private ModelRenderer reinfBarBottomRightT;
    private ModelRenderer reinfBarBottomLeftT;
    private ModelRenderer reinfBarBottomLeftB;

    public ModelOreGenerator() {
        final float degree45 = (float) (Math.PI / 4.0D);

        this.textureWidth = 64;
        this.textureHeight = 64;

        this.stanceBackLeft = SAPClientUtils.createNewBox(this, 0, 4, false, 7.0F, 9.0F, 7.0F, 1, 15, 1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.stanceBackRight = SAPClientUtils.createNewBox(this, 0, 4, true, -8.0F, 9.0F, 7.0F, 1, 15, 1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.stanceFrontLeft = SAPClientUtils.createNewBox(this, 0, 4, false, 7.0F, 10.0F, -8.0F, 1, 14, 1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.stanceFrontRight = SAPClientUtils.createNewBox(this, 0, 4, true, -8.0F, 10.0F, -8.0F, 1, 14, 1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        this.grinderAxis1 = SAPClientUtils.createNewBox(this, 0, 22, false, -7.0F, -0.5F, -0.5F, 14, 1, 1, 0.0F, 14.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.grinderAxis1.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, -5.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.grinderAxis1.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, -4.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, degree45, 0.0F, 0.0F));
        this.grinderAxis1.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, -3.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.grinderAxis1.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, -2.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, degree45, 0.0F, 0.0F));
        this.grinderAxis1.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, -1.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.grinderAxis1.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, 0.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, degree45, 0.0F, 0.0F));
        this.grinderAxis1.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, 1.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.grinderAxis1.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, 2.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, degree45, 0.0F, 0.0F));
        this.grinderAxis1.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, 3.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.grinderAxis1.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, 4.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, degree45, 0.0F, 0.0F));

        this.grinderAxis2 = SAPClientUtils.createNewBox(this, 0, 22, false, -7.0F, -0.5F, -0.5F, 14, 1, 1, 0.0F, 15.75F, 0.25F, degree45, 0.0F, 0.0F);
        this.grinderAxis2.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, -5.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.grinderAxis2.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, -4.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, degree45, 0.0F, 0.0F));
        this.grinderAxis2.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, -3.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.grinderAxis2.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, -2.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, degree45, 0.0F, 0.0F));
        this.grinderAxis2.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, -1.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.grinderAxis2.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, 0.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, degree45, 0.0F, 0.0F));
        this.grinderAxis2.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, 1.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.grinderAxis2.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, 2.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, degree45, 0.0F, 0.0F));
        this.grinderAxis2.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, 3.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        this.grinderAxis2.addChild(SAPClientUtils.createNewBox(this, 0, 0, false, 4.0F, -1.0F, -1.0F, 1, 2, 2, 0.0F, 0.0F, 0.0F, degree45, 0.0F, 0.0F));

        this.sideLeft = SAPClientUtils.createNewBox(this, 4, 0, false, 5.5F, 10.5F, -7.5F, 2, 7, 15, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.sideRight = SAPClientUtils.createNewBox(this, 4, 0, true, -7.5F, 10.5F, -7.5F, 2, 7, 15, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.ramp = SAPClientUtils.createNewBox(this, 30, 22, false, -7.0F, 11.45F, 4.25F, 14, 1, 4, 0.0F, 0.0F, 0.0F, (float) -(0.19444444D * Math.PI), 0.0F, 0.0F);
        this.storage = SAPClientUtils.createNewBox(this, 0, 41, false, -7.0F, 9.0F, -8.0F, 14, 8, 5, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.sieveBottom = SAPClientUtils.createNewBox(this, 15, 26, false, -7.5F, 21.0F, -7.5F, 15, 0, 15, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.sieveTop = SAPClientUtils.createNewBox(this, -15, 26, false, -7.5F, 19.0F, -7.5F, 15, 0, 15, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.reinfBarBottomBackT = SAPClientUtils.createNewBox(this, 0, 24, true, -7.0F, 18.5F, 7.0F, 14, 1, 1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.reinfBarBottomBackB = SAPClientUtils.createNewBox(this, 0, 24, true, -7.0F, 20.5F, 7.0F, 14, 1, 1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.reinfBarBottomFrontT = SAPClientUtils.createNewBox(this, 0, 24, true, -7.0F, 18.5F, -8.0F, 14, 1, 1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.reinfBarBottomFrontB = SAPClientUtils.createNewBox(this, 0, 24, true, -7.0F, 20.5F, -8.0F, 14, 1, 1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.reinfBarTopBack = SAPClientUtils.createNewBox(this, 0, 24, true, -7.0F, 8.5F, 7.0F, 14, 1, 1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.reinfBarBottomRightB = SAPClientUtils.createNewBox(this, 0, 24, true, -7.0F, 20.5F, -8.0F, 14, 1, 1, 0.0F, 0.0F, 0.0F, 0.0F, degree45 * 2.0F, 0.0F);
        this.reinfBarBottomRightT = SAPClientUtils.createNewBox(this, 0, 24, true, -7.0F, 18.5F, -8.0F, 14, 1, 1, 0.0F, 0.0F, 0.0F, 0.0F, degree45 * 2.0F, 0.0F);
        this.reinfBarBottomLeftT = SAPClientUtils.createNewBox(this, 0, 24, true, -7.0F, 18.5F, -8.0F, 14, 1, 1, 0.0F, 0.0F, 0.0F, 0.0F, -degree45 * 2.0F, 0.0F);
        this.reinfBarBottomLeftB = SAPClientUtils.createNewBox(this, 0, 24, true, -7.0F, 20.5F, -8.0F, 14, 1, 1, 0.0F, 0.0F, 0.0F, 0.0F, -degree45 * 2.0F, 0.0F);
    }

    public void renderBlock() {
        stanceBackLeft.render(0.0625F);
        stanceBackRight.render(0.0625F);
        stanceFrontLeft.render(0.0625F);
        stanceFrontRight.render(0.0625F);
        grinderAxis1.render(0.0625F);
        grinderAxis2.render(0.0625F);
        sideLeft.render(0.0625F);
        sideRight.render(0.0625F);
        ramp.render(0.0625F);
        storage.render(0.0625F);
        sieveBottom.render(0.0625F);
        sieveTop.render(0.0625F);
        reinfBarBottomBackT.render(0.0625F);
        reinfBarBottomBackB.render(0.0625F);
        reinfBarBottomFrontT.render(0.0625F);
        reinfBarBottomFrontB.render(0.0625F);
        reinfBarTopBack.render(0.0625F);
        reinfBarBottomRightB.render(0.0625F);
        reinfBarBottomRightT.render(0.0625F);
        reinfBarBottomLeftT.render(0.0625F);
        reinfBarBottomLeftB.render(0.0625F);
    }

    public void addGrinderRotation(float rotation) {
        this.grinderAxis1.rotateAngleX = rotation;
        this.grinderAxis2.rotateAngleX = -rotation;
    }
}
