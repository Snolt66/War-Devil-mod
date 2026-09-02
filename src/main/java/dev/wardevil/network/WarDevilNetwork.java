package dev.wardevil.network;

import dev.wardevil.WarDevil;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = WarDevil.MOD_ID)
public final class WarDevilNetwork {
    private static final String NETWORK_VERSION = "1";

    private WarDevilNetwork() {}

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar(NETWORK_VERSION)
                .playToClient(S2CWarDevilStatePayload.TYPE, S2CWarDevilStatePayload.STREAM_CODEC);
    }
}
