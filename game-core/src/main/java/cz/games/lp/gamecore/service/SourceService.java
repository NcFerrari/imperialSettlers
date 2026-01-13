package cz.games.lp.gamecore.service;

import cz.games.lp.common.dto.FactionDTO;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface SourceService {

    CompletableFuture<String> prepareData();

    Map<String, FactionDTO> getFactionMap();
}
