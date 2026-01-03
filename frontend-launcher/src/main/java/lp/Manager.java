package lp;

import cz.games.lp.frontend.GameApp;

public class Manager {

    private Manager() {
    }

    public static void main(String[] args) {
        new Manager().run();
    }

    private void run() {
        GameApp.startApplication();
    }

}
