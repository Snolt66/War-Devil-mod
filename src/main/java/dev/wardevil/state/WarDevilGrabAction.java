package dev.wardevil.state;

public enum WarDevilGrabAction {
    NONE, START, HOLD, SLAM, THROW, BASH;

    private static final WarDevilGrabAction[] VALUES = values();

    public static WarDevilGrabAction fromNetwork(int id) {
        return id >= 0 && id < VALUES.length ? VALUES[id] : NONE;
    }

    public int durationTicks() {
        return switch (this) {
            case NONE, HOLD -> -1;
            case START -> 7;
            case SLAM -> 15;
            case THROW -> 13;
            case BASH -> 14;
        };
    }
}
