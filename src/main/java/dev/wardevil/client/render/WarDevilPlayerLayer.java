package dev.wardevil.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;

public final class WarDevilPlayerLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    private final WarDevilModel model;

    public WarDevilPlayerLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent, EntityModelSet entityModels) {
        super(parent);
        this.model = new WarDevilModel(entityModels.bakeLayer(WarDevilRenderIds.WAR_FORM_LAYER));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, AvatarRenderState state, float yRot, float xRot) {
        WarDevilRenderData data = state.getRenderData(WarDevilRenderStateHooks.WAR_DEVIL_DATA);
        if (data == null || !data.rendersWarForm()) return;
        this.model.setupAnim(state);
        poseStack.pushPose();
        collector.order(1).submitModel(this.model, state, poseStack, RenderTypes.entityCutout(WarDevilRenderIds.BASE_TEXTURE), lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        poseStack.popPose();
    }
}
