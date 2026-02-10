package cz.games.lp.common.enums;

public enum Phases {
    LOOKOUT("PRODUCTION"),
    PRODUCTION("ACTION"),
    ACTION("PASS_ACTION"),
    PASS_ACTION("CLEANUP"),
    CLEANUP("LOOKOUT");

    private final String followingPhase;

    Phases(String followingPhase) {
        this.followingPhase = followingPhase;
    }

    public Phases getFollowingPhase() {
        return Phases.valueOf(followingPhase);
    }
}
