package cz.games.lp.backend.api;

import cz.games.lp.backend.service.agregates.GamePartsServices;
import cz.games.lp.gamecore.components.Card;
import cz.games.lp.gamecore.components.Faction;
import cz.games.lp.gamecore.components.enums.FactionTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/catalogs")
public class CatalogController {

    private final GamePartsServices gamePartsServices;

    public CatalogController(GamePartsServices gamePartsServices) {
        this.gamePartsServices = gamePartsServices;
    }

    @GetMapping("/factions")
    public Map<FactionTypes, Faction> getFactionsCatalog() {
        return gamePartsServices.getFactionService().factionMap();
    }

    @GetMapping("/faction")
    public Faction getFaction(@RequestParam("factionType") FactionTypes factionType) {
        return gamePartsServices.getFactionService().factionMap().get(factionType);
    }

    @GetMapping("/cards")
    public Map<String, Card> getCardCatalog() {
        return gamePartsServices.getCardService().cardMap();
    }

    @GetMapping("/card")
    public Card getCard(@RequestParam("cardID") String cardID) {
        return gamePartsServices.getCardService().cardMap().get(cardID);
    }
}
