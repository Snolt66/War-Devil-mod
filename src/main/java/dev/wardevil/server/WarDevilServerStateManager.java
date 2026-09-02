package dev.wardevil.server;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import dev.wardevil.state.WarDevilAction;
import dev.wardevil.state.WarDevilFlightMode;
import dev.wardevil.state.WarDevilGrabAction;
import dev.wardevil.state.WarDevilLocomotion;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public final class WarDevilServerStateManager {
    private WarDevilServerStateManager() {}

    private static final Map<UUID, WarDevilServerPlayerState> STATES = new ConcurrentHashMap<>();

    public static void ensure(ServerPlayer player) {
        long now = gameTime(player);
        STATES.computeIfAbsent(player.getUUID(), id -> {
            WarDevilServerPlayerState state = new WarDevilServerPlayerState();
            state.setLastOnGround(player.onGround());
            state.setLocomotion(WarDevilLocomotion.IDLE, now);
            return state;
        });
    }

    public static void remove(ServerPlayer player) { STATES.remove(player.getUUID()); }

    public static void syncSelf(ServerPlayer player) {
        WarDevilServerPlayerState state = get(player);
        PacketDistributor.sendToPlayer(player, state.snapshot(player.getUUID(), gameTime(player)));
    }

    public static void syncTo(ServerPlayer target, ServerPlayer receiver) {
        WarDevilServerPlayerState state = get(target);
        PacketDistributor.sendToPlayer(receiver, state.snapshot(target.getUUID(), gameTime(target)));
    }

    public static void syncTrackingAndSelf(ServerPlayer player) {
        WarDevilServerPlayerState state = get(player);
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(player,
                state.snapshot(player.getUUID(), gameTime(player)));
    }

    public static boolean isTransformed(ServerPlayer player) { return get(player).transformed(); }

    public static void setTransformed(ServerPlayer player, boolean transformed) {
        WarDevilServerPlayerState state = get(player);
        long now = gameTime(player);
        if (state.setTransformed(transformed, now)) {
            state.bumpSequence();
            syncTrackingAndSelf(player);
        }
    }

    public static void startTransformation(ServerPlayer player) {
        WarDevilServerPlayerState state = get(player);
        long now = gameTime(player);
        boolean changed = state.setTransformed(true, now);
        changed |= state.setAction(WarDevilAction.TRANSFORM, now);
        if (changed) {
            state.bumpSequence();
            syncTrackingAndSelf(player);
        }
    }

    public static void startAction(ServerPlayer player, WarDevilAction action) {
        if (action == WarDevilAction.TRANSFORM) { startTransformation(player); return; }
        WarDevilServerPlayerState state = get(player);
        if (!state.transformed()) return;
        long now = gameTime(player);
        if (state.setAction(action, now)) {
            state.bumpSequence();
            syncTrackingAndSelf(player);
        }
    }

    public static void startGrabAction(ServerPlayer player, WarDevilGrabAction action) {
        WarDevilServerPlayerState state = get(player);
        if (!state.transformed()) return;
        long now = gameTime(player);
        if (state.setGrabAction(action, now)) {
            state.bumpSequence();
            syncTrackingAndSelf(player);
        }
    }

    public static void setFlightMode(ServerPlayer player, WarDevilFlightMode mode) {
        WarDevilServerPlayerState state = get(player);
        if (!state.transformed() && mode != WarDevilFlightMode.NONE) return;
        long now = gameTime(player);
        boolean changed = state.setFlightMode(mode, now);
        if (mode == WarDevilFlightMode.NONE) changed |= updateGroundLocomotion(player, state, now, false);
        if (changed) {
            state.bumpSequence();
            syncTrackingAndSelf(player);
        }
    }

    public static void tick(ServerPlayer player) {
        WarDevilServerPlayerState state = get(player);
        long now = gameTime(player);
        if (!state.transformed()) {
            state.setLastOnGround(player.onGround());
            return;
        }
        boolean changed = state.tickTimedAction(now) | state.tickTimedGrab(now);
        if (state.flightMode() != WarDevilFlightMode.NONE) {
            changed |= state.setLocomotion(state.flightMode().locomotion(), now);
        } else {
            changed |= updateGroundLocomotion(player, state, now, true);
        }
        if (changed) {
            state.bumpSequence();
            syncTrackingAndSelf(player);
        }
    }

    private static boolean updateGroundLocomotion(ServerPlayer player, WarDevilServerPlayerState state,
            long now, boolean allowTransient) {
        boolean onGround = player.onGround();
        boolean wasOnGround = state.lastOnGround();
        state.setLastOnGround(onGround);
        if (allowTransient && now < state.locomotionLockedUntilTick()) return false;
        if (allowTransient && wasOnGround && !onGround && player.getDeltaMovement().y > 0.05D) {
            boolean changed = state.setLocomotion(WarDevilLocomotion.JUMP_START, now);
            state.lockLocomotionUntil(now + 5);
            return changed;
        }
        if (allowTransient && !wasOnGround && onGround) {
            boolean changed = state.setLocomotion(WarDevilLocomotion.LAND, now);
            state.lockLocomotionUntil(now + 7);
            return changed;
        }
        if (!onGround) return state.setLocomotion(WarDevilLocomotion.AIRBORNE, now);
        Vec3 motion = player.getDeltaMovement();
        double h2 = motion.x * motion.x + motion.z * motion.z;
        if (player.isSprinting() && h2 > 0.0025D) return state.setLocomotion(WarDevilLocomotion.RUN, now);
        if (h2 > 0.0004D) return state.setLocomotion(WarDevilLocomotion.WALK, now);
        return state.setLocomotion(WarDevilLocomotion.IDLE, now);
    }

    private static WarDevilServerPlayerState get(ServerPlayer player) {
        ensure(player);
        return STATES.get(player.getUUID());
    }

    private static long gameTime(ServerPlayer player) { return player.level().getGameTime(); }
}
