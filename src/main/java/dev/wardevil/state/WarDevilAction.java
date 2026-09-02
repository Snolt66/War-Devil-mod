package dev.wardevil.state;

public enum WarDevilAction {
    NONE, TRANSFORM, ABILITY_1_START, ABILITY_1_LOOP,
    ABILITY_2_HEAL, ABILITY_3_CAST, ABILITY_4_CAST,
    MELEE_LEFT, MELEE_RIGHT;

    private static final WarDevilAction[] VALUES = values();

    public static WarDevilAction fromNetwork(int id) {
        return id >= 0 && id < VALUES.length ? VALUES[id] : NONE;
    }

    public int durationTicks() {
        return switch (this) {
            case NONE, ABILITY_1_LOOP -> -1;
            case TRANSFORM -> 44;
            case ABILITY_1_START -> 15;
            case ABILITY_2_HEAL -> 16;
            case ABILITY_3_CAST -> 11;
            case ABILITY_4_CAST -> 30;
            case MELEE_LEFT, MELEE_RIGHT -> 9;
        };
    }
}
