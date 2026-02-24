package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.enums.Sources;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProduceReport {

    private final String cardID;
    private final List<Sources> source;
    private final List<Sources> orSource;
    private final Sources deal;

    private boolean produceAnotherProduction;
    private List<String> allBuiltProductions = new ArrayList<>();

    ProduceReport(String cardID, List<Sources> source, List<Sources> orSource, Sources deal) {
        this.cardID = cardID;
        this.source = source;
        this.orSource = orSource;
        this.deal = deal;
    }
}
