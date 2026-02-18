package cz.games.lp.gamecore.actions;

import cz.games.lp.gamecore.components.enums.Sources;

import java.util.List;

public record ProduceChoice(String cardID, List<Sources> source, List<Sources> orSource, Sources deal) {

}
