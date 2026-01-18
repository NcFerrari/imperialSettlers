package cz.games.lp.backend.serviceimpl;

import cz.games.lp.backend.service.CardService;
import cz.games.lp.backend.service.FactionService;
import cz.games.lp.backend.service.PlayerService;
import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.dto.PlayerDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {

    private final List<PlayerDTO> players = new ArrayList<>();
    private final CardService cardService;
    private final FactionService factionService;

    private PlayerDTO activePlayer;

    public PlayerServiceImpl(CardService cardService, FactionService factionService) {
        this.cardService = cardService;
        this.factionService = factionService;
    }

    @Override
    public void resetPlayersStats() {
        log.debug("resetPlayersStats");
        players.forEach(player -> {
            player.setVictoryPoints(0);
            player.getOwnSources().replaceAll((source, value) -> 0);
            player.getCardsInHand().clear();
            player.getBuiltLocations().clear();
        });
        cardService.prepareNewFactionCardDecks(players);
    }

    @Override
    public void addPlayers() {
        log.debug("addPlayers");
        FactionDTO faction = factionService.selectFaction();
        addPlayer(faction);
    }

    private void addPlayer(FactionDTO faction) {
        log.debug("addPlayer");
        PlayerDTO player = new PlayerDTO(faction);
        players.add(player);
    }
}
