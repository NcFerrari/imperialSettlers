package cz.games.lp.backend.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FactionJSON {

    private String faction;
    private List<String> factionProduction;
    private String saveSource;
}