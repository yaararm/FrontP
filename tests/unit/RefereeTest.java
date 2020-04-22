package unit;

import domain.Enums.RefereeRole;
import domain.Impl.Game;
import domain.Impl.Season;
import domain.Impl.Team;
import domain.Users.Owner;
import domain.Users.Referee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static domain.Enums.RefereeRole.Secondary;
import static domain.Enums.RefereeTraining.Expert;
import static domain.Enums.TeamState.active;
import static domain.Enums.UserStatus.NotActive;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RefereeTest {

    Owner tw = new Owner("rita@gmail.com", "123456789", "rita", "ora", "rita@gmail.com");
    Team team = new Team("Bayern", active, tw);
    Owner tw2 = new Owner("avner@gmail.com", "4564644554", "avner", "avner", "avner@gmail.com");
    Team team2 = new Team("Milan", active, tw2);
    Season seas = new Season(2019,System.currentTimeMillis());
    Game g = new Game(seas,team,team2);
    Referee r = new Referee("refe@gmail.com","456fdj6", 12378546,"rafi","hamud","refe@gmail.com",Expert );

    @Test
    public void test_Add_Game(){
        r.addGame(Secondary,g);

        HashMap<RefereeRole, HashSet<Game>> res = r.getGames();
        List<Game> list = new ArrayList<Game>(res.get(Secondary));
        boolean ans = list.isEmpty();
        Season s2 = list.get(0).getSeason();
        assertFalse(ans);
        assertEquals(seas,s2);


    }

    Season seas2 = new Season(2020,System.currentTimeMillis());

    @Test
    public void test_Add_Season(){
        r.addSeason(seas2);
        List<Season> list2 = new ArrayList<Season>(r.getSeasons());
        Season s3 = list2.get(0);
        assertEquals(s3,seas2);
    }

    @Test
    public void test_Delete_user()throws Exception{


        assertTrue( r.deleteUser());
        r.getStatus().compareTo(NotActive);
    }






}
