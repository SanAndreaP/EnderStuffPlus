package de.sanandrew.mods.enderstuffp.client.util;

import de.sanandrew.mods.enderstuffp.util.EnderStuffPlus;
import net.minecraft.util.ResourceLocation;

public enum EnumTextures
{
    // ENTITIES
    ARROW_AVIS                  ("textures/entity/avisArrow.png"),
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

    // TILE ENTITIES
    TILE_BIOMECHANGER("textures/blocks/tile/biome_changer.png"),
    TILE_WEATHERALTAR("textures/blocks/tile/weather_altar.png"),
    TILE_ORE_GENERATOR("textures/blocks/tile/ore_generator.png"),
    TILE_BIOMECRYSTAL("textures/blocks/tile/biome_crystal.png"),
    TILE_CROCOITE_ORE("textures/blocks/tile/crocoite_crystal.png"),

    // GUIS
    GUI_BIOMECHANGER            ("textures/gui/biome_changer.png"),
    GUI_ORE_GENERATOR           ("textures/gui/ore_generator.png"),
    GUI_SCALES                  ("textures/gui/scales.png"),
    GUI_BUTTONS                 ("textures/gui/buttons.png"),
    GUI_DUPLICATOR              ("textures/gui/duplicatorGui.png"),
    GUI_INGAMEICONS             ("textures/gui/hud_icons.png"),
    GUI_WEATHERALTAR            ("textures/gui/weather_altar.png"),

    // ARMOR
    TEX_ARMOR_NIOBIUM_1         ("textures/models/armor/niob_1.png"),
    TEX_ARMOR_NIOBIUM_2         ("textures/models/armor/niob_2.png"),

    // MISC
    PARTICLES                   ("textures/particles/particles.png");

    public static final ResourceLocation GLASS_TEXTURE = new ResourceLocation("textures/blocks/glass.png");

    private final ResourceLocation tex;

    EnumTextures(String texture) {
        this.tex = new ResourceLocation(EnderStuffPlus.MOD_ID, texture);
    }

    public ResourceLocation getResource() {
        return this.tex;
    }

    public String getTexture() {
        return this.tex.toString();
    }
}
