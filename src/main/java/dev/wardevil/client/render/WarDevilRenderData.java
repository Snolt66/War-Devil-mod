package dev.wardevil.client.render;

import dev.wardevil.state.WarDevilAction;
import dev.wardevil.state.WarDevilGrabAction;
import dev.wardevil.state.WarDevilLocomotion;

public record WarDevilRenderData(
        boolean transformed,
        WarDevilLocomotion locomotion,
        WarDevilAction action,
        WarDevilGrabAction grabAction,
        float locomotionElapsedTicks,
        float actionElapsedTicks,
        float grabElapsedTicks) {

    public static final WarDevilRenderData INACTIVE = new WarDevilRenderData(
            false, WarDevilLocomotion.IDLE, WarDevilAction.NONE,
            WarDevilGrabAction.NONE, 0.0F, 0.0F, 0.0F);

    public boolean rendersWarForm() {
        return transformed || action == WarDevilAction.TRANSFORM;
    }

    public boolean hidesRightHeldItem() {
        return grabAction != WarDevilGrabAction.NONE;
    }
}
