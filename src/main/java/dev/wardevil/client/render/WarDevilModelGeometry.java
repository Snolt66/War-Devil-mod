package dev.wardevil.client.render;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/** Temporary hierarchy-only geometry. Final Blockbench cubes are intentionally not copied from the older donor model. */
public final class WarDevilModelGeometry {
    private WarDevilModelGeometry() {}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("war_root", CubeListBuilder.create(), PartPose.ZERO);
        return LayerDefinition.create(mesh, 512, 512);
    }
}
