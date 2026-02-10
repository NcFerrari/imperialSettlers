package cz.games.lp.frontend.enums;

import lombok.Getter;

public enum Actions {
    BUILD_LOCATION("build_location"),
    MAKE_A_DEAL("make_a_deal"),
    RAZE("raze"),
    ACTIVATE_AN_ACTION_LOCATION("activate_an_action_location"),
    SPEND_TWO_SETTLERS("spend_two_settlers"),
    DEFENSE_TOKEN("defense_token");

    @Getter
    private final String imagePath;

    Actions(String imagePath) {
        this.imagePath = imagePath;
    }
}
