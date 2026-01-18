package cz.games.lp.backend.service;

import cz.games.lp.common.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {

    void resetPlayersStats();

    void addPlayers();

    List<PlayerDTO> getPlayers();
}
