package domain.BusinessLayer.SeasonPolicies;

import domain.BusinessLayer.Football.Game;
import domain.BusinessLayer.Football.Season;

import java.util.HashMap;
import java.util.HashSet;

public  interface AssignPolicy {

    public String getName();

    public HashMap<Integer,HashSet<Game>> assignSeasonGames(Season season);
}
