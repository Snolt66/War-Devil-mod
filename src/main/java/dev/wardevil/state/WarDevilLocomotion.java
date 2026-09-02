package dev.wardevil.state;

public enum WarDevilLocomotion {
    IDLE, WALK, RUN, JUMP_START, AIRBORNE, LAND,
    FLIGHT_HOVER, FLIGHT_FORWARD, FLIGHT_FAST;

    private static final WarDevilLocomotion[] VALUES = values();

    public static WarDevilLocomotion fromNetwork(int id) {
        return id >= 0 && id < VALUES.length ? VALUES[id] : IDLE;
    }

    public boolean isFlight() {
        return this == FLIGHT_HOVER || this == FLIGHT_FORWARD || this == FLIGHT_FAST;
    }
}
