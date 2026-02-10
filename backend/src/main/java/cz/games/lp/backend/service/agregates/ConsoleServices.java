package cz.games.lp.backend.service.agregates;

import cz.games.lp.backend.infrastructure.console.ConsolePrinter;
import cz.games.lp.backend.infrastructure.console.ConsoleUI;

public interface ConsoleServices {

    ConsoleUI getConsoleUI();

    ConsolePrinter getConsolePrinter();
}
