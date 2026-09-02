package dev.wardevil.client.render;

import dev.wardevil.WarDevil;
import dev.wardevil.client.WarDevilClientStateCache;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.renderstate.AvatarRenderStateModifier;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

public final class WarDevilRenderStateHooks {
    private WarDevilRenderStateHooks() {}

    public static final ContextKey<WarDevilRenderData> WAR_DEVIL_DATA = new ContextKey<>(Identifier.fromNamespaceAndPath(WarDevil.MOD_ID, "render/war_devil_data"));

    public static void register(RegisterRenderStateModifiersEvent event) {
        event.registerAvatarEntityModifier(new AvatarRenderStateModifier() {
            @Override
            public <T extends Avatar & ClientAvatarEntity> void accept(T avatar, AvatarRenderState state) {
                WarDevilRenderData data = WarDevilClientStateCache.snapshot(avatar.getUUID(), state.ageInTicks);
                state.setRenderData(WAR_DEVIL_DATA, data);
                if (!data.rendersWarForm()) return;
                state.showHat = false;
                state.showJacket = false;
                state.showLeftPants = false;
                state.showRightPants = false;
                state.showLeftSleeve = false;
                state.showRightSleeve = false;
                state.showCape = false;
                state.showExtraEars = false;
                state.headEquipment = ItemStack.EMPTY;
                state.chestEquipment = ItemStack.EMPTY;
                state.legsEquipment = ItemStack.EMPTY;
                state.feetEquipment = ItemStack.EMPTY;
                if (data.hidesRightHeldItem()) {
                    state.rightHandItemStack = ItemStack.EMPTY;
                    state.rightHandItemState.clear();
                }
            }
        });
    }
}
