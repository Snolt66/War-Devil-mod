package dev.wardevil.server;

import dev.wardevil.network.S2CWarDevilStatePayload;
import dev.wardevil.state.WarDevilAction;
import dev.wardevil.state.WarDevilFlightMode;
import dev.wardevil.state.WarDevilGrabAction;
import dev.wardevil.state.WarDevilLocomotion;

final class WarDevilServerPlayerState {
    private long sequence;
    private boolean transformed;
    private WarDevilLocomotion locomotion = WarDevilLocomotion.IDLE;
    private WarDevilAction action = WarDevilAction.NONE;
    private WarDevilGrabAction grabAction = WarDevilGrabAction.NONE;
    private WarDevilFlightMode flightMode = WarDevilFlightMode.NONE;
    private long locomotionStartTick;
    private long actionStartTick;
    private long grabStartTick;
    private long locomotionLockedUntilTick;
    private boolean lastOnGround = true;

    boolean transformed() { return transformed; }
    WarDevilLocomotion locomotion() { return locomotion; }
    WarDevilAction action() { return action; }
    WarDevilGrabAction grabAction() { return grabAction; }
    WarDevilFlightMode flightMode() { return flightMode; }
    boolean lastOnGround() { return lastOnGround; }
    void setLastOnGround(boolean value) { lastOnGround = value; }
    long locomotionLockedUntilTick() { return locomotionLockedUntilTick; }
    void lockLocomotionUntil(long tick) { locomotionLockedUntilTick = Math.max(locomotionLockedUntilTick, tick); }

    boolean setTransformed(boolean value, long now) {
        if (transformed == value) return false;
        transformed = value;
        if (!value) {
            flightMode = WarDevilFlightMode.NONE;
            setLocomotionInternal(WarDevilLocomotion.IDLE, now);
            setActionInternal(WarDevilAction.NONE, now);
            setGrabInternal(WarDevilGrabAction.NONE, now);
        }
        return true;
    }

    boolean setLocomotion(WarDevilLocomotion value, long now) {
        if (locomotion == value) return false;
        setLocomotionInternal(value, now);
        return true;
    }

    boolean setAction(WarDevilAction value, long now) {
        if (action == value) return false;
        setActionInternal(value, now);
        return true;
    }

    boolean setGrabAction(WarDevilGrabAction value, long now) {
        if (grabAction == value) return false;
        setGrabInternal(value, now);
        return true;
    }

    boolean setFlightMode(WarDevilFlightMode value, long now) {
        if (flightMode == value) return false;
        flightMode = value;
        if (value != WarDevilFlightMode.NONE) setLocomotionInternal(value.locomotion(), now);
        return true;
    }

    void bumpSequence() { sequence++; }

    boolean tickTimedAction(long now) {
        int duration = action.durationTicks();
        if (duration < 0 || now - actionStartTick < duration) return false;
        return switch (action) {
            case ABILITY_1_START -> setAction(WarDevilAction.ABILITY_1_LOOP, now);
            case NONE, ABILITY_1_LOOP -> false;
            default -> setAction(WarDevilAction.NONE, now);
        };
    }

    boolean tickTimedGrab(long now) {
        int duration = grabAction.durationTicks();
        if (duration < 0 || now - grabStartTick < duration) return false;
        return switch (grabAction) {
            case START, SLAM, BASH -> setGrabAction(WarDevilGrabAction.HOLD, now);
            case THROW -> setGrabAction(WarDevilGrabAction.NONE, now);
            case NONE, HOLD -> false;
        };
    }

    S2CWarDevilStatePayload snapshot(java.util.UUID playerId, long now) {
        return new S2CWarDevilStatePayload(playerId, sequence, transformed, locomotion,
                action, grabAction, elapsed(now, locomotionStartTick),
                elapsed(now, actionStartTick), elapsed(now, grabStartTick));
    }

    private void setLocomotionInternal(WarDevilLocomotion value, long now) { locomotion = value; locomotionStartTick = now; }
    private void setActionInternal(WarDevilAction value, long now) { action = value; actionStartTick = now; }
    private void setGrabInternal(WarDevilGrabAction value, long now) { grabAction = value; grabStartTick = now; }
    private static int elapsed(long now, long start) { return (int)Math.min(Integer.MAX_VALUE, Math.max(0L, now - start)); }
}
