package cz.games.lp.backend.infrstructure.mapping.jsonobjects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FactionJSON {

    private String factionTitle;
    private List<String> factionProduction;
    private String saveSource;
}