package domain.Interfaces;

import domain.Impl.Game;
import domain.Impl.Season;

import java.util.HashMap;
import java.util.HashSet;

public  interface AssignPolicy {

    public String getName();

    public HashMap<Integer,HashSet<Game>> assignSeasonGames(Season season);
}
