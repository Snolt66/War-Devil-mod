package dev.wardevil.client.render;

import java.util.EnumMap;
import java.util.Map;

import dev.wardevil.state.WarDevilAction;
import dev.wardevil.state.WarDevilGrabAction;
import dev.wardevil.state.WarDevilLocomotion;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public final class WarDevilModel extends EntityModel<AvatarRenderState> {
    private final Map<WarDevilLocomotion, KeyframeAnimation> locomotion = new EnumMap<>(WarDevilLocomotion.class);
    private final Map<WarDevilAction, KeyframeAnimation> actions = new EnumMap<>(WarDevilAction.class);
    private final Map<WarDevilGrabAction, KeyframeAnimation> grabs = new EnumMap<>(WarDevilGrabAction.class);

    public WarDevilModel(ModelPart root) {
        super(root, RenderTypes::entityCutout);

        locomotion.put(WarDevilLocomotion.IDLE, bake("war_idle", root));
        locomotion.put(WarDevilLocomotion.WALK, bake("war_walk", root));
        locomotion.put(WarDevilLocomotion.RUN, bake("war_run", root));
        locomotion.put(WarDevilLocomotion.JUMP_START, bake("war_jump_start", root));
        locomotion.put(WarDevilLocomotion.AIRBORNE, bake("war_airborne", root));
        locomotion.put(WarDevilLocomotion.LAND, bake("war_land", root));
        locomotion.put(WarDevilLocomotion.FLIGHT_HOVER, bake("war_flight_hover", root));
        locomotion.put(WarDevilLocomotion.FLIGHT_FORWARD, bake("war_flight_forward", root));
        locomotion.put(WarDevilLocomotion.FLIGHT_FAST, bake("war_flight_fast", root));

        actions.put(WarDevilAction.TRANSFORM, bake("war_transform", root));
        actions.put(WarDevilAction.ABILITY_1_START, bake("war_ability1_start", root));
        actions.put(WarDevilAction.ABILITY_1_LOOP, bake("war_ability1_loop", root));
        actions.put(WarDevilAction.ABILITY_2_HEAL, bake("war_ability2_heal", root));
        actions.put(WarDevilAction.ABILITY_3_CAST, bake("war_ability3_cast", root));
        actions.put(WarDevilAction.ABILITY_4_CAST, bake("war_ability4_cast", root));
        actions.put(WarDevilAction.MELEE_LEFT, bake("war_melee_left", root));
        actions.put(WarDevilAction.MELEE_RIGHT, bake("war_melee_right", root));

        grabs.put(WarDevilGrabAction.START, bake("war_grab_start", root));
        grabs.put(WarDevilGrabAction.HOLD, bake("war_grab_hold", root));
        grabs.put(WarDevilGrabAction.SLAM, bake("war_grab_slam", root));
        grabs.put(WarDevilGrabAction.THROW, bake("war_grab_throw", root));
        grabs.put(WarDevilGrabAction.BASH, bake("war_grab_bash", root));
    }

    private static KeyframeAnimation bake(String name, ModelPart root) {
        return getAnimation(WarDevilRenderIds.animation(name)).get().bake(root);
    }

    @Override
    public void setupAnim(AvatarRenderState state) {
        super.setupAnim(state);
        WarDevilRenderData data = state.getRenderData(WarDevilRenderStateHooks.WAR_DEVIL_DATA);
        if (data == null || !data.rendersWarForm()) return;

        if (data.action() == WarDevilAction.TRANSFORM) {
            apply(actions.get(WarDevilAction.TRANSFORM), data.actionElapsedTicks());
            return;
        }

        apply(locomotion.get(data.locomotion()), data.locomotionElapsedTicks());
        if (data.grabAction() != WarDevilGrabAction.NONE) {
            apply(grabs.get(data.grabAction()), data.grabElapsedTicks());
        }
        if (data.action() != WarDevilAction.NONE) {
            apply(actions.get(data.action()), data.actionElapsedTicks());
        }
    }

    private static void apply(KeyframeAnimation animation, float elapsedTicks) {
        if (animation != null) {
            animation.apply(Math.max(0L, (long)(elapsedTicks * 50.0F)), 1.0F);
        }
    }
}
