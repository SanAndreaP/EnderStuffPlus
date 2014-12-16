package de.sanandrew.mods.enderstuffp.client.util;

import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.util.ResourceLocation;

public enum EnumTextures
{
    ARROW_AVIS                  ("textures/entity/avisArrow.png"),
    BIOMECHANGER                ("textures/blocks/biomeChanger.png"),
    WEATHERALTAR                ("textures/blocks/weatherAltar.png"),
    ORE_GENERATOR               ("textures/entity/tile/ore_generator.png"),
    BIOMECRYSTAL_CORE           ("textures/entity/tile/biome_crystal_core.png"),
    ENDERAVIS_GLOW              ("textures/entity/enderAvisGlow.png"),
    ENDERAVIS_TEXTURE           ("textures/entity/enderAvis.png"),
    ENDERAVIS_TEXTURE_SADDLE    ("textures/entity/enderAvisSaddle.png"),
    ENDERIGNIS_GLOW_TEXTURE     ("textures/entity/enderIgnisGlow.png"),
    ENDERIGNIS_TEXTURE          ("textures/entity/enderIgnis.png"),
    ENDERMISS_GLOW_TEXTURE      ("textures/entity/enderMissGlow.png"),
    ENDERMISS_GLOW_TEXTURE_SPEC ("textures/entity/enderMissSpecGlow.png"),
    ENDERMISS_TEXTURE           ("textures/entity/enderMiss.png"),
    ENDERMISS_TEXTURE_SPEC      ("textures/entity/enderMissSpec.png"),
    ENDERNEMESIS_GLOW_TEXTURE   ("textures/entity/enderNemesisGlow.png"),
    ENDERNEMESIS_TEXTURE        ("textures/entity/enderNemesis.png"),
    ENDERNIVIS_GLOW_TEXTURE     ("textures/entity/enderNivisGlow.png"),
    ENDERNIVIS_TEXTURE          ("textures/entity/enderNivis.png"),
    ENDERRAY_GLOW_TEXTURE       ("textures/entity/enderRayGlow.png"),
    ENDERRAY_GLOW_TEXTURE_SPEC  ("textures/entity/enderRayScarsGlow.png"),
    ENDERRAY_TEXTURE            ("textures/entity/enderRay.png"),
    ENDERRAY_TEXTURE_SPEC       ("textures/entity/enderRayScars.png"),
//    GUI_BIOMECHANGER_I          ("textures/gui/BiomeChangerGUI/biomeChanger_fuel.png"),
//    GUI_BIOMECHANGER_II         ("textures/gui/BiomeChangerGUI/biomeChanger_biomes.png"),
//    GUI_BIOMECHANGER_III        ("textures/gui/BiomeChangerGUI/biomeChanger_config.png"),
//    GUI_BIOMECHANGER_TABS       ("textures/gui/BiomeChangerGUI/biomeChanger_tabs.png"),
    GUI_BIOMECHANGER            ("textures/gui/biome_changer.png"),
    GUI_ORE_GENERATOR            ("textures/gui/ore_generator.png"),
    GUI_SCALES                  ("textures/gui/scales.png"),
    GUI_BUTTONS                 ("textures/gui/buttons.png"),
    GUI_DUPLICATOR              ("textures/gui/duplicatorGui.png"),
    GUI_INGAMEICONS             ("textures/gui/hud_icons.png"),
    GUI_WEATHERALTAR            ("textures/gui/weather_altar.png"),
    TEX_ARMOR_NIOBIUM_1         ("textures/models/armor/niob_1.png"),
    TEX_ARMOR_NIOBIUM_2         ("textures/models/armor/niob_2.png");

    public static final ResourceLocation GLASS_TEXTURE = new ResourceLocation("textures/blocks/glass.png");
    public static final ResourceLocation BIOMECRYSTAL_BLOCKTEXTURE = new ResourceLocation(EnderStuffPlus.MOD_ID, "textures/blocks/biome_crystal.png");

    private final ResourceLocation tex;

    private EnumTextures(String texture) {
        this.tex = new ResourceLocation(EnderStuffPlus.MOD_ID, texture);
    }

    public ResourceLocation getResource() {
        return this.tex;
    }

    public String getTexture() {
        return this.tex.toString();
    }
}
