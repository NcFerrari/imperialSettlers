module frontend {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires static lombok;
    requires common;

    exports cz.games.lp.frontend.api;
    exports cz.games.lp.frontend.components;
    exports cz.games.lp.frontend.panes;
    exports cz.games.lp.frontend.game_actions;
    exports cz.games.lp.frontend;
    exports cz.games.lp.frontend.models;
    exports cz.games.lp.frontend.components.transition_components;
    exports cz.games.lp.frontend.enums;
}