package de.sanandrew.mods.enderstuffp.client.model.tileentity;

import de.sanandrew.core.manpack.util.client.helpers.ModelBoxBuilder;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

/**
 * BlockBiomeDataCrystal - SanAndreasP
 * Created using Tabula 4.1.1
 */
public class ModelBiomeDataCrystal
        extends ModelBase
{
    public ModelRenderer storage;

    public ModelRenderer stripeT1;
    public ModelRenderer stripeT2;
    public ModelRenderer stripeT3;
    public ModelRenderer stripeT4;
    public ModelRenderer stripeS1;
    public ModelRenderer stripeS2;
    public ModelRenderer stripeS3;
    public ModelRenderer stripeS4;
    public ModelRenderer stripeB1;
    public ModelRenderer stripeB2;
    public ModelRenderer stripeB3;
    public ModelRenderer stripeB4;

    public ModelRenderer paneO1;
    public ModelRenderer paneO2;
    public ModelRenderer paneO3;
    public ModelRenderer paneO4;
    public ModelRenderer paneO5;
    public ModelRenderer paneO6;

    public ModelRenderer catalyst01;
    public ModelRenderer catalyst02;
    public ModelRenderer catalyst03;
    public ModelRenderer catalyst04;
    public ModelRenderer catalyst05;
    public ModelRenderer catalyst06;
    public ModelRenderer catalyst07;
    public ModelRenderer catalyst08;
    public ModelRenderer catalyst09;
    public ModelRenderer catalyst10;
    public ModelRenderer catalyst11;
    public ModelRenderer catalyst12;
    public ModelRenderer catalyst13;
    public ModelRenderer catalyst14;
    public ModelRenderer catalyst15;
    public ModelRenderer catalyst16;
    public ModelRenderer catalyst17;
    public ModelRenderer catalyst18;
    public ModelRenderer catalyst19;
    public ModelRenderer catalyst20;
    public ModelRenderer catalyst21;
    public ModelRenderer catalyst22;
    public ModelRenderer catalyst23;
    public ModelRenderer catalyst24;
    public ModelRenderer catalyst25;
    public ModelRenderer catalyst26;
    public ModelRenderer catalyst27;
    public ModelRenderer catalyst28;
    public ModelRenderer catalyst29;
    public ModelRenderer catalyst30;
    public ModelRenderer catalyst31;
    public ModelRenderer catalyst32;
    public ModelRenderer catalyst33;
    public ModelRenderer catalyst34;
    public ModelRenderer catalyst35;
    public ModelRenderer catalyst36;
    public ModelRenderer catalyst37;
    public ModelRenderer catalyst38;
    public ModelRenderer catalyst39;
    public ModelRenderer catalyst40;
    public ModelRenderer catalyst41;
    public ModelRenderer catalyst42;
    public ModelRenderer catalyst43;
    public ModelRenderer catalyst44;
    public ModelRenderer catalyst45;
    public ModelRenderer catalyst46;
    public ModelRenderer catalyst47;
    public ModelRenderer catalyst48;

    public ModelBiomeDataCrystal() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.stripeT1 = ModelBoxBuilder.newBuilder(this).setTexture(4, 0, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-7.0F, -8.0F, -8.0F, 14, 1, 1, 0.0F);
        this.stripeT2 = ModelBoxBuilder.newBuilder(this).setTexture(4, 0, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(-7.0F, -8.0F, -8.0F, 14, 1, 1, 0.0F);
        this.stripeT3 = ModelBoxBuilder.newBuilder(this).setTexture(4, 0, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(-7.0F, -8.0F, -8.0F, 14, 1, 1, 0.0F);
        this.stripeT4 = ModelBoxBuilder.newBuilder(this).setTexture(4, 0, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(-7.0F, -8.0F, -8.0F, 14, 1, 1, 0.0F);
        this.stripeS1 = ModelBoxBuilder.newBuilder(this).setTexture(0, 0, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-8.0F, -8.0F, -8.0F, 1, 16, 1, 0.0F);
        this.stripeS2 = ModelBoxBuilder.newBuilder(this).setTexture(0, 0, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(-8.0F, -8.0F, -8.0F, 1, 16, 1, 0.0F);
        this.stripeS3 = ModelBoxBuilder.newBuilder(this).setTexture(0, 0, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(-8.0F, -8.0F, -8.0F, 1, 16, 1, 0.0F);
        this.stripeS4 = ModelBoxBuilder.newBuilder(this).setTexture(0, 0, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(-8.0F, -8.0F, -8.0F, 1, 16, 1, 0.0F);
        this.stripeB1 = ModelBoxBuilder.newBuilder(this).setTexture(4, 2, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-7.0F, 7.0F, -8.0F, 14, 1, 1, 0.0F);
        this.stripeB2 = ModelBoxBuilder.newBuilder(this).setTexture(4, 2, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(-7.0F, 7.0F, -8.0F, 14, 1, 1, 0.0F);
        this.stripeB3 = ModelBoxBuilder.newBuilder(this).setTexture(4, 2, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(-7.0F, 7.0F, -8.0F, 14, 1, 1, 0.0F);
        this.stripeB4 = ModelBoxBuilder.newBuilder(this).setTexture(4, 2, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(-7.0F, 7.0F, -8.0F, 14, 1, 1, 0.0F);

        this.paneO1 = ModelBoxBuilder.newBuilder(this).setTexture(4, 4, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-7.0F, -7.0F, -7.6F, 14, 14, 0, 0.0F);
        this.paneO2 = ModelBoxBuilder.newBuilder(this).setTexture(4, 4, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(-7.0F, -7.0F, -7.6F, 14, 14, 0, 0.0F);
        this.paneO3 = ModelBoxBuilder.newBuilder(this).setTexture(4, 4, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(-7.0F, -7.0F, -7.6F, 14, 14, 0, 0.0F);
        this.paneO4 = ModelBoxBuilder.newBuilder(this).setTexture(4, 4, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(-7.0F, -7.0F, -7.6F, 14, 14, 0, 0.0F);
        this.paneO5 = ModelBoxBuilder.newBuilder(this).setTexture(4, 4, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(-1.5707963267948966F, 0.0F, 0.0F).getBox(-7.0F, -7.0F, -7.6F, 14, 14, 0, 0.0F);
        this.paneO6 = ModelBoxBuilder.newBuilder(this).setTexture(4, 4, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(1.5707963267948966F, 0.0F, 0.0F).getBox(-7.0F, -7.0F, -7.6F, 14, 14, 0, 0.0F);

        this.storage = ModelBoxBuilder.newBuilder(this).setTexture(32, 0, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-3.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F);

        this.catalyst01 = ModelBoxBuilder.newBuilder(this).setTexture(0, 17, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(-5.0F, -7.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst02 = ModelBoxBuilder.newBuilder(this).setTexture(0, 17, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-5.0F, -7.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst03 = ModelBoxBuilder.newBuilder(this).setTexture(0, 17, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(-5.0F, -7.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst04 = ModelBoxBuilder.newBuilder(this).setTexture(0, 17, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(-5.0F, -7.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst05 = ModelBoxBuilder.newBuilder(this).setTexture(0, 17, false).setLocation(0.0F, 16.0F, 0.0F).getBox(4.0F, -7.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst06 = ModelBoxBuilder.newBuilder(this).setTexture(0, 17, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(4.0F, -7.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst07 = ModelBoxBuilder.newBuilder(this).setTexture(0, 17, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(4.0F, -7.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst08 = ModelBoxBuilder.newBuilder(this).setTexture(0, 17, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(4.0F, -7.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst09 = ModelBoxBuilder.newBuilder(this).setTexture(12, 23, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(-5.0F, 5.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst10 = ModelBoxBuilder.newBuilder(this).setTexture(12, 23, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-5.0F, 5.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst11 = ModelBoxBuilder.newBuilder(this).setTexture(12, 23, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(-5.0F, 5.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst12 = ModelBoxBuilder.newBuilder(this).setTexture(12, 23, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(-5.0F, 5.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst13 = ModelBoxBuilder.newBuilder(this).setTexture(12, 23, false).setLocation(0.0F, 16.0F, 0.0F).getBox(4.0F, 5.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst14 = ModelBoxBuilder.newBuilder(this).setTexture(12, 23, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(4.0F, 5.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst15 = ModelBoxBuilder.newBuilder(this).setTexture(12, 23, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(4.0F, 5.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst16 = ModelBoxBuilder.newBuilder(this).setTexture(12, 23, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(4.0F, 5.5F, -7.5F, 1, 2, 2, 0.0F);
        this.catalyst17 = ModelBoxBuilder.newBuilder(this).setTexture(8, 18, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-7.5F, -5.0F, -7.5F, 2, 1, 2, 0.0F);
        this.catalyst18 = ModelBoxBuilder.newBuilder(this).setTexture(8, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(-7.5F, -5.0F, -7.5F, 2, 1, 2, 0.0F);
        this.catalyst19 = ModelBoxBuilder.newBuilder(this).setTexture(8, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(-7.5F, -5.0F, -7.5F, 2, 1, 2, 0.0F);
        this.catalyst20 = ModelBoxBuilder.newBuilder(this).setTexture(8, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(-7.5F, -5.0F, -7.5F, 2, 1, 2, 0.0F);
        this.catalyst21 = ModelBoxBuilder.newBuilder(this).setTexture(8, 18, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-7.5F, 4.0F, -7.5F, 2, 1, 2, 0.0F);
        this.catalyst22 = ModelBoxBuilder.newBuilder(this).setTexture(8, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(-7.5F, 4.0F, -7.5F, 2, 1, 2, 0.0F);
        this.catalyst23 = ModelBoxBuilder.newBuilder(this).setTexture(8, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(-7.5F, 4.0F, -7.5F, 2, 1, 2, 0.0F);
        this.catalyst24 = ModelBoxBuilder.newBuilder(this).setTexture(8, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(-7.5F, 4.0F, -7.5F, 2, 1, 2, 0.0F);
        this.catalyst25 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(-3.0F, -7.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst26 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-3.0F, -7.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst27 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(-3.0F, -7.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst28 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(-3.0F, -7.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst29 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).getBox(2.0F, -7.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst30 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(2.0F, -7.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst31 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(2.0F, -7.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst32 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(2.0F, -7.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst33 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(-3.0F, 3.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst34 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-3.0F, 3.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst35 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(-3.0F, 3.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst36 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(-3.0F, 3.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst37 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).getBox(2.0F, 3.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst38 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(2.0F, 3.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst39 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(2.0F, 3.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst40 = ModelBoxBuilder.newBuilder(this).setTexture(2, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(2.0F, 3.5F, -7.5F, 1, 4, 4, 0.0F);
        this.catalyst41 = ModelBoxBuilder.newBuilder(this).setTexture(12, 18, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-7.5F, -3.0F, -7.5F, 4, 1, 4, 0.0F);
        this.catalyst42 = ModelBoxBuilder.newBuilder(this).setTexture(12, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(-7.5F, -3.0F, -7.5F, 4, 1, 4, 0.0F);
        this.catalyst43 = ModelBoxBuilder.newBuilder(this).setTexture(12, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(-7.5F, -3.0F, -7.5F, 4, 1, 4, 0.0F);
        this.catalyst44 = ModelBoxBuilder.newBuilder(this).setTexture(12, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(-7.5F, -3.0F, -7.5F, 4, 1, 4, 0.0F);
        this.catalyst45 = ModelBoxBuilder.newBuilder(this).setTexture(12, 18, false).setLocation(0.0F, 16.0F, 0.0F).getBox(-7.5F, 2.0F, -7.5F, 4, 1, 4, 0.0F);
        this.catalyst46 = ModelBoxBuilder.newBuilder(this).setTexture(12, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 1.5707963267948966F, 0.0F).getBox(-7.5F, 2.0F, -7.5F, 4, 1, 4, 0.0F);
        this.catalyst47 = ModelBoxBuilder.newBuilder(this).setTexture(12, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, 3.141592653589793F, 0.0F).getBox(-7.5F, 2.0F, -7.5F, 4, 1, 4, 0.0F);
        this.catalyst48 = ModelBoxBuilder.newBuilder(this).setTexture(12, 18, false).setLocation(0.0F, 16.0F, 0.0F).setRotation(0.0F, -1.5707963267948966F, 0.0F).getBox(-7.5F, 2.0F, -7.5F, 4, 1, 4, 0.0F);
    }

    public void render(float partTicks, int pass) {
        if( pass == 0 ) {
            this.stripeT1.render(partTicks);
            this.stripeT2.render(partTicks);
            this.stripeT3.render(partTicks);
            this.stripeT4.render(partTicks);
            this.stripeS1.render(partTicks);
            this.stripeS2.render(partTicks);
            this.stripeS3.render(partTicks);
            this.stripeS4.render(partTicks);
            this.stripeB1.render(partTicks);
            this.stripeB2.render(partTicks);
            this.stripeB3.render(partTicks);
            this.stripeB4.render(partTicks);
//            this.catalyst01.render(partTicks);
//            this.catalyst02.render(partTicks);
//            this.catalyst03.render(partTicks);
            GL11.glPushMatrix();
            this.catalyst04.render(partTicks);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            this.catalyst04.render(partTicks);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            this.catalyst04.render(partTicks);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            this.catalyst04.render(partTicks);
            GL11.glPopMatrix();

            this.catalyst05.render(partTicks);
            this.catalyst06.render(partTicks);
            this.catalyst07.render(partTicks);
            this.catalyst08.render(partTicks);
            this.catalyst09.render(partTicks);
            this.catalyst10.render(partTicks);
            this.catalyst11.render(partTicks);
            this.catalyst12.render(partTicks);
            this.catalyst13.render(partTicks);
            this.catalyst14.render(partTicks);
            this.catalyst15.render(partTicks);
            this.catalyst16.render(partTicks);
            this.catalyst17.render(partTicks);
            this.catalyst18.render(partTicks);
            this.catalyst19.render(partTicks);
            this.catalyst20.render(partTicks);
            this.catalyst21.render(partTicks);
            this.catalyst22.render(partTicks);
            this.catalyst23.render(partTicks);
            this.catalyst24.render(partTicks);
            this.catalyst25.render(partTicks);
            this.catalyst26.render(partTicks);
            this.catalyst27.render(partTicks);
            this.catalyst28.render(partTicks);
            this.catalyst29.render(partTicks);
            this.catalyst30.render(partTicks);
            this.catalyst31.render(partTicks);
            this.catalyst32.render(partTicks);
            this.catalyst33.render(partTicks);
            this.catalyst34.render(partTicks);
            this.catalyst35.render(partTicks);
            this.catalyst36.render(partTicks);
            this.catalyst37.render(partTicks);
            this.catalyst38.render(partTicks);
            this.catalyst39.render(partTicks);
            this.catalyst40.render(partTicks);
            this.catalyst41.render(partTicks);
            this.catalyst42.render(partTicks);
            this.catalyst43.render(partTicks);
            this.catalyst44.render(partTicks);
            this.catalyst45.render(partTicks);
            this.catalyst46.render(partTicks);
            this.catalyst47.render(partTicks);
            this.catalyst48.render(partTicks);
            this.storage.render(partTicks);
        } else {
            this.paneO2.render(partTicks);
            this.paneO3.render(partTicks);
            this.paneO5.render(partTicks);
            this.paneO1.render(partTicks);
            this.paneO4.render(partTicks);
            this.paneO6.render(partTicks);
        }
    }
}
