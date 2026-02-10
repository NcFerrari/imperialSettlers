package cz.games.lp.backend.json_object;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FactionJSON {

    private String faction;
    private List<String> factionProduction;
    private String saveSource;
}
