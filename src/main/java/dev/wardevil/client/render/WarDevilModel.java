package dev.wardevil.client.render;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public final class WarDevilModel extends EntityModel<AvatarRenderState> {
    public WarDevilModel(ModelPart root) {
        super(root, RenderTypes::entityCutout);
    }

    @Override
    public void setupAnim(AvatarRenderState state) {
        super.setupAnim(state);
    }
}
