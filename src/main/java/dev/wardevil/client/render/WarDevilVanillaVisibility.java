package dev.wardevil.client.render;

import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.client.model.player.PlayerModel;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

public final class WarDevilVanillaVisibility {
    private WarDevilVanillaVisibility() {}
    private static final ThreadLocal<Deque<VisibilitySnapshot>> STACK = ThreadLocal.withInitial(ArrayDeque::new);

    public static void onPre(RenderPlayerEvent.Pre<?> event) {
        WarDevilRenderData data = event.getRenderState().getRenderData(WarDevilRenderStateHooks.WAR_DEVIL_DATA);
        if (data == null || !data.rendersWarForm()) return;
        PlayerModel model = event.getRenderer().getModel();
        STACK.get().push(new VisibilitySnapshot(model.head.visible, model.hat.visible, model.body.visible, model.leftArm.visible, model.rightArm.visible, model.leftLeg.visible, model.rightLeg.visible));
        model.head.visible = model.hat.visible = model.body.visible = false;
        model.leftArm.visible = model.rightArm.visible = false;
        model.leftLeg.visible = model.rightLeg.visible = false;
    }

    public static void onPost(RenderPlayerEvent.Post<?> event) {
        WarDevilRenderData data = event.getRenderState().getRenderData(WarDevilRenderStateHooks.WAR_DEVIL_DATA);
        if (data == null || !data.rendersWarForm()) return;
        Deque<VisibilitySnapshot> stack = STACK.get();
        if (stack.isEmpty()) return;
        VisibilitySnapshot s = stack.pop();
        PlayerModel model = event.getRenderer().getModel();
        model.head.visible=s.head(); model.hat.visible=s.hat(); model.body.visible=s.body();
        model.leftArm.visible=s.leftArm(); model.rightArm.visible=s.rightArm();
        model.leftLeg.visible=s.leftLeg(); model.rightLeg.visible=s.rightLeg();
    }

    private record VisibilitySnapshot(boolean head, boolean hat, boolean body, boolean leftArm, boolean rightArm, boolean leftLeg, boolean rightLeg) {}
}
