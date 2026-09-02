package dev.wardevil.client.render;

import dev.wardevil.WarDevil;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public final class WarDevilRenderIds {
    private WarDevilRenderIds() {}

    public static final Identifier BASE_TEXTURE = Identifier.fromNamespaceAndPath(WarDevil.MOD_ID, "textures/entity/war_form.png");
    public static final Identifier EMISSIVE_TEXTURE = Identifier.fromNamespaceAndPath(WarDevil.MOD_ID, "textures/entity/war_form_emissive.png");
    public static final ModelLayerLocation WAR_FORM_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(WarDevil.MOD_ID, "war_form"), "main");
}
