package cz.games.lp.backend.controller;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.Factions;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.gamecore.GameData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureRestTestClient
@SpringBootTest
class GameControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @Test
    void getGameDataTest() {
        restTestClient.get().uri("http://localhost:8888/game-data").exchange().expectStatus().isOk();
    }

    @Test
    void selectFactionTest() {
        FactionDTO factionDTO = new FactionDTO();
        factionDTO.setFaction(Factions.ROMAN_F);
        factionDTO.setSaveSource(Sources.CARD);
        factionDTO.setFactionProduction(List.of(Sources.STONE, Sources.GOLD));
        restTestClient.put().uri("http://localhost:8888/select-faction").contentType(MediaType.APPLICATION_JSON).body(factionDTO).exchange().expectStatus().isOk();

        restTestClient.get().uri("http://localhost:8888/game-data").exchange().expectBody(GameData.class).value(gameData -> {
            assertNotNull(gameData);
            assertNotNull(gameData.getSelectedFaction());
            assertEquals(gameData.getSelectedFaction().getFaction(), factionDTO.getFaction());
            assertEquals(gameData.getSelectedFaction().getSaveSource(), factionDTO.getSaveSource());
            assertEquals(gameData.getSelectedFaction().getFactionProduction(), factionDTO.getFactionProduction());
        });
    }

    @Test
    void newGameTest() {
        restTestClient.get().uri("http://localhost:8888/new-game").exchange().expectStatus().is5xxServerError();

        FactionDTO factionDTO = new FactionDTO();
        factionDTO.setFaction(Factions.ROMAN_M);
        factionDTO.setSaveSource(Sources.GOLD);
        factionDTO.setFactionProduction(List.of(Sources.WOOD, Sources.WOOD));
        restTestClient.put().uri("http://localhost:8888/select-faction").contentType(MediaType.APPLICATION_JSON).body(factionDTO).exchange().expectStatus().isOk();

        restTestClient.get().uri("http://localhost:8888/new-game").exchange().expectStatus().isOk();
    }
}