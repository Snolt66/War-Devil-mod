package dev.wardevil.client;

import dev.wardevil.WarDevil;
import dev.wardevil.network.S2CWarDevilStatePayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@EventBusSubscriber(modid = WarDevil.MOD_ID, value = Dist.CLIENT)
public final class WarDevilClientNetwork {
    private WarDevilClientNetwork() {}

    @SubscribeEvent
    public static void registerClientPayloadHandlers(RegisterClientPayloadHandlersEvent event) {
        event.register(S2CWarDevilStatePayload.TYPE, WarDevilClientNetwork::handleState);
    }

    private static void handleState(S2CWarDevilStatePayload payload, IPayloadContext context) {
        float nowTicks = context.player().tickCount;
        WarDevilClientStateCache.update(
                payload.playerId(), payload.sequence(), payload.transformed(),
                payload.locomotion(), payload.action(), payload.grabAction(),
                payload.locomotionElapsedTicks(), payload.actionElapsedTicks(),
                payload.grabElapsedTicks(), nowTicks);
    }
}
