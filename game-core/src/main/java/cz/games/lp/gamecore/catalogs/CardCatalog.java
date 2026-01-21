package cz.games.lp.gamecore.catalogs;

import cz.games.lp.common.dto.CardDTO;

import java.util.Map;

public record CardCatalog(Map<String, CardDTO> cardMap) {
}
