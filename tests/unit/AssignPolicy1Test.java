package unit;

import domain.Enums.TeamState;
import domain.Impl.Season;
import domain.Impl.Team;
import domain.Interfaces.AssignPolicy;
import domain.SeasonPolicies.AssignPolicy2;
import domain.Users.Owner;
import org.junit.*;

import java.util.HashSet;

public class AssignPolicy1Test {
    Season s;
    @Before
    public void setUp() throws Exception {
        s = new Season(2000,System.currentTimeMillis()*10);
        Owner o = new Owner("moshe@gmail.com", "123456", "moshe", "cohen","moshe@gmail.com");
        Team a = new Team("1", TeamState.active,o);
        Team b = new Team("2", TeamState.active,o);
        Team c = new Team("3", TeamState.active,o);
        Team d = new Team("4", TeamState.active,o);
        Team e = new Team("5", TeamState.active,o);
        Team f = new Team("6", TeamState.active,o);
        Team g = new Team("7", TeamState.active,o);


        HashSet<Team> teams = new HashSet<>();
        teams.add(a);
        teams.add(b);
        teams.add(c);
        teams.add(d);
        teams.add(e);
        teams.add(f);
        teams.add(g);


        s.setSeasonTeams(teams);
    }

    @Test
    public void assignTest() {
        AssignPolicy as2 = new AssignPolicy2();
        as2.assignSeasonGames(s);
    }
}
