package cz.games.lp.backend.service.agregates;

import cz.games.lp.backend.infrstructure.console.ConsolePrinter;
import cz.games.lp.backend.infrstructure.console.ConsoleUI;

public interface ConsoleServices {

    ConsoleUI getConsoleUI();

    ConsolePrinter getConsolePrinter();
}
