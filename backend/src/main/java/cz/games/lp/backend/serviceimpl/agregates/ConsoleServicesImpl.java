package cz.games.lp.backend.serviceimpl.agregates;

import cz.games.lp.backend.infrstructure.console.ConsolePrinter;
import cz.games.lp.backend.infrstructure.console.ConsoleUI;
import cz.games.lp.backend.service.agregates.ConsoleServices;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class ConsoleServicesImpl implements ConsoleServices {

    private final ConsoleUI consoleUI;
    private final ConsolePrinter consolePrinter;

    public ConsoleServicesImpl(ConsoleUI consoleUI, ConsolePrinter consolePrinter) {
        this.consoleUI = consoleUI;
        this.consolePrinter = consolePrinter;
    }
}
