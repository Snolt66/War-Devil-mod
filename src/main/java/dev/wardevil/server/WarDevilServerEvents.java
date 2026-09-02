package dev.wardevil.server;

import dev.wardevil.WarDevil;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = WarDevil.MOD_ID)
public final class WarDevilServerEvents {
    private WarDevilServerEvents() {}

    @SubscribeEvent
    public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            WarDevilServerStateManager.ensure(player);
            WarDevilServerStateManager.syncSelf(player);
        }
    }

    @SubscribeEvent
    public static void playerRespawned(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            WarDevilServerStateManager.ensure(player);
            WarDevilServerStateManager.syncTrackingAndSelf(player);
        }
    }

    @SubscribeEvent
    public static void playerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            WarDevilServerStateManager.syncTrackingAndSelf(player);
        }
    }

    @SubscribeEvent
    public static void startTracking(PlayerEvent.StartTracking event) {
        if (event.getEntity() instanceof ServerPlayer receiver
                && event.getTarget() instanceof ServerPlayer target) {
            WarDevilServerStateManager.syncTo(target, receiver);
        }
    }

    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            WarDevilServerStateManager.tick(player);
        }
    }

    @SubscribeEvent
    public static void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            WarDevilServerStateManager.remove(player);
        }
    }
}
