package dev.wardevil.client;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import dev.wardevil.client.render.WarDevilRenderData;
import dev.wardevil.state.WarDevilAction;
import dev.wardevil.state.WarDevilGrabAction;
import dev.wardevil.state.WarDevilLocomotion;

/**
 * Client-side interpolation/render cache.
 * Gameplay authority does NOT belong here.
 */
public final class WarDevilClientStateCache {
    private WarDevilClientStateCache() {}

    private static final Map<UUID, Entry> STATES = new ConcurrentHashMap<>();

    public static void update(UUID playerId, long sequence, boolean transformed,
            WarDevilLocomotion locomotion, WarDevilAction action,
            WarDevilGrabAction grabAction, int locomotionElapsedTicks,
            int actionElapsedTicks, int grabElapsedTicks, float nowTicks) {

        STATES.compute(playerId, (id, old) -> {
            if (old != null && sequence < old.sequence) return old;
            return new Entry(sequence, transformed, locomotion, action, grabAction,
                    nowTicks - Math.max(0, locomotionElapsedTicks),
                    nowTicks - Math.max(0, actionElapsedTicks),
                    nowTicks - Math.max(0, grabElapsedTicks));
        });
    }

    public static WarDevilRenderData snapshot(UUID playerId, float nowTicks) {
        Entry entry = STATES.get(playerId);
        if (entry == null) return WarDevilRenderData.INACTIVE;
        return new WarDevilRenderData(entry.transformed, entry.locomotion,
                entry.action, entry.grabAction,
                Math.max(0.0F, nowTicks - entry.locomotionStartTicks),
                Math.max(0.0F, nowTicks - entry.actionStartTicks),
                Math.max(0.0F, nowTicks - entry.grabStartTicks));
    }

    public static void clear(UUID playerId) { STATES.remove(playerId); }
    public static void clearAll() { STATES.clear(); }

    private record Entry(long sequence, boolean transformed,
            WarDevilLocomotion locomotion, WarDevilAction action,
            WarDevilGrabAction grabAction, float locomotionStartTicks,
            float actionStartTicks, float grabStartTicks) {}
}
