package sanandreasp.mods.EnderStuffPlus.registry;

import net.minecraft.util.ResourceLocation;

public enum Textures {
    ARROW_AVIS                  ("textures/entity/avisArrow.png"),
    BIOMECHANGER_TEXTURE        ("textures/blocks/biomeChanger.png"),
    ENDERAVIS_GLOW_TEXTURE      ("textures/entity/enderAvisGlow.png"),
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
    GUI_BIOMECHANGER_I          ("textures/guis/BiomeChangerGUI/biomeChanger_fuel.png"),
    GUI_BIOMECHANGER_II         ("textures/guis/BiomeChangerGUI/biomeChanger_biomes.png"),
    GUI_BIOMECHANGER_III        ("textures/guis/BiomeChangerGUI/biomeChanger_config.png"),
    GUI_BIOMECHANGER_TABS       ("textures/guis/BiomeChangerGUI/biomeChanger_tabs.png"),
    GUI_BUTTONS                 ("textures/guis/buttons.png"),
    GUI_DUPLICATOR              ("textures/guis/duplicatorGui.png"),
    GUI_INGAMEICONS             ("textures/guis/hudIcons.png"),
    GUI_WEATHERALTAR            ("textures/guis/weatherAltar.png"),
    TEX_ARMOR_NIOBIUM_1         ("textures/models/armor/niob_1.png"),
    TEX_ARMOR_NIOBIUM_2         ("textures/models/armor/niob_2.png"),
    WEATHERALTAR_TEXTURE        ("textures/blocks/weatherAltar.png");

    private final ResourceLocation tex;

    private Textures(String texture) {
        this.tex = new ResourceLocation("enderstuffp", texture);
    }

    public ResourceLocation getResource() {
        return this.tex;
    }

    public String getTexture() {
        return this.tex.toString();
    }
}
