package cz.games.lp.common.enums;

public enum RoundPhases {

    LOOKOUT,
    PRODUCTION,
    ACTION,
    PASS,
    CLEANUP;

    @Override
    public String toString() {
        return name();
    }

    public RoundPhases nextPhase() {
        return switch (this) {
            case LOOKOUT -> PRODUCTION;
            case PRODUCTION -> ACTION;
            case ACTION -> PASS;
            case PASS -> CLEANUP;
            case CLEANUP -> LOOKOUT;
        };
    }
}

