package dev.wardevil.state;

public enum WarDevilFlightMode {
    NONE, HOVER, FORWARD, FAST;

    public WarDevilLocomotion locomotion() {
        return switch (this) {
            case NONE -> WarDevilLocomotion.IDLE;
            case HOVER -> WarDevilLocomotion.FLIGHT_HOVER;
            case FORWARD -> WarDevilLocomotion.FLIGHT_FORWARD;
            case FAST -> WarDevilLocomotion.FLIGHT_FAST;
        };
    }
}
