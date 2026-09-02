package dev.wardevil.client.render;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/**
 * Bone-only placeholder hierarchy. No donor cubes are copied here, so the user's
 * locally corrected Blockbench geometry is not overwritten. Replace only this
 * LayerDefinition with the final Blockbench Java Entity export later.
 */
public final class WarDevilModelGeometry {
    private WarDevilModelGeometry() {}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition root = meshRoot.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition pelvis = root.addOrReplaceChild("pelvis", CubeListBuilder.create(), PartPose.offset(0F,-17F,0F));
        PartDefinition abdomen = pelvis.addOrReplaceChild("abdomen", CubeListBuilder.create(), PartPose.offset(0F,-4.5F,-0.4F));
        PartDefinition chest = abdomen.addOrReplaceChild("chest", CubeListBuilder.create(), PartPose.offset(0F,-6.5F,-0.8F));
        PartDefinition neck = chest.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0F,-4.7F,-0.8F));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0F,-2.6F,-0.6F));
        head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0F,2.2F,-2.4F));
        chest.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0F,0F,-4.8F));

        PartDefinition sl = chest.addOrReplaceChild("shoulder_left", CubeListBuilder.create(), PartPose.offset(7.7F,-1.7F,0F));
        PartDefinition sr = chest.addOrReplaceChild("shoulder_right", CubeListBuilder.create(), PartPose.offset(-7.7F,-1.7F,0F));
        PartDefinition ual = sl.addOrReplaceChild("upper_arm_left", CubeListBuilder.create(), PartPose.offset(2F,3.1F,-0.6F));
        PartDefinition uar = sr.addOrReplaceChild("upper_arm_right", CubeListBuilder.create(), PartPose.offset(-2F,3.1F,-0.6F));
        PartDefinition fal = ual.addOrReplaceChild("forearm_left", CubeListBuilder.create(), PartPose.offset(1.3F,6.6F,-0.6F));
        PartDefinition far = uar.addOrReplaceChild("forearm_right", CubeListBuilder.create(), PartPose.offset(-1.3F,6.6F,-0.6F));
        PartDefinition hl = fal.addOrReplaceChild("hand_left", CubeListBuilder.create(), PartPose.offset(0.8F,7.2F,-0.8F));
        PartDefinition hr = far.addOrReplaceChild("hand_right", CubeListBuilder.create(), PartPose.offset(-0.8F,7.2F,-0.8F));
        hl.addOrReplaceChild("thumb_left", CubeListBuilder.create(), PartPose.ZERO);
        hl.addOrReplaceChild("finger_left_1", CubeListBuilder.create(), PartPose.ZERO);
        hl.addOrReplaceChild("finger_left_2", CubeListBuilder.create(), PartPose.ZERO);
        hl.addOrReplaceChild("finger_left_3", CubeListBuilder.create(), PartPose.ZERO);
        hl.addOrReplaceChild("finger_left_4", CubeListBuilder.create(), PartPose.ZERO);
        hr.addOrReplaceChild("thumb_right", CubeListBuilder.create(), PartPose.ZERO);
        hr.addOrReplaceChild("finger_right_1", CubeListBuilder.create(), PartPose.ZERO);
        hr.addOrReplaceChild("finger_right_2", CubeListBuilder.create(), PartPose.ZERO);
        hr.addOrReplaceChild("finger_right_3", CubeListBuilder.create(), PartPose.ZERO);
        hr.addOrReplaceChild("finger_right_4", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition tl = pelvis.addOrReplaceChild("thigh_left", CubeListBuilder.create(), PartPose.offset(3.5F,0F,0F));
        PartDefinition tr = pelvis.addOrReplaceChild("thigh_right", CubeListBuilder.create(), PartPose.offset(-3.5F,0F,0F));
        PartDefinition shl = tl.addOrReplaceChild("shin_left", CubeListBuilder.create(), PartPose.offset(0.5F,6.7F,-0.8F));
        PartDefinition shr = tr.addOrReplaceChild("shin_right", CubeListBuilder.create(), PartPose.offset(-0.5F,6.7F,-0.8F));
        PartDefinition hol = shl.addOrReplaceChild("hock_left", CubeListBuilder.create(), PartPose.offset(0.1F,5.2F,2.7F));
        PartDefinition hor = shr.addOrReplaceChild("hock_right", CubeListBuilder.create(), PartPose.offset(-0.1F,5.2F,2.7F));
        hol.addOrReplaceChild("foot_left", CubeListBuilder.create(), PartPose.offset(0F,3.4F,-3.4F));
        hor.addOrReplaceChild("foot_right", CubeListBuilder.create(), PartPose.offset(0F,3.4F,-3.4F));

        PartDefinition back = chest.addOrReplaceChild("back_root", CubeListBuilder.create(), PartPose.offset(0F,0F,3.9F));
        back.addOrReplaceChild("spine_left_1", CubeListBuilder.create(), PartPose.ZERO);
        back.addOrReplaceChild("spine_left_2", CubeListBuilder.create(), PartPose.ZERO);
        back.addOrReplaceChild("spine_left_3", CubeListBuilder.create(), PartPose.ZERO);
        back.addOrReplaceChild("spine_left_4", CubeListBuilder.create(), PartPose.ZERO);
        back.addOrReplaceChild("spine_left_5", CubeListBuilder.create(), PartPose.ZERO);
        back.addOrReplaceChild("spine_right_1", CubeListBuilder.create(), PartPose.ZERO);
        back.addOrReplaceChild("spine_right_2", CubeListBuilder.create(), PartPose.ZERO);
        back.addOrReplaceChild("spine_right_3", CubeListBuilder.create(), PartPose.ZERO);
        back.addOrReplaceChild("spine_right_4", CubeListBuilder.create(), PartPose.ZERO);
        back.addOrReplaceChild("spine_right_5", CubeListBuilder.create(), PartPose.ZERO);
        back.addOrReplaceChild("spine_center_upper", CubeListBuilder.create(), PartPose.ZERO);
        back.addOrReplaceChild("spine_center_lower", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("horn_main_left", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("horn_main_right", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("horn_front_left", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("horn_front_right", CubeListBuilder.create(), PartPose.ZERO);
        return LayerDefinition.create(mesh, 512, 512);
    }
}
