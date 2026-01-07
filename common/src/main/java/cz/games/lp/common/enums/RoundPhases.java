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
}
