package unit;

import BusinessLayer.Enum.RefereeRole;
import BusinessLayer.Football.Game;
import BusinessLayer.Football.Season;
import BusinessLayer.Football.Team;
import BusinessLayer.Users.Owner;
import BusinessLayer.Users.Referee;
import org.junit.Test;

import java.util.*;

import static BusinessLayer.Enum.RefereeRole.Main;
import static BusinessLayer.Enum.RefereeRole.Secondary;
import static BusinessLayer.Enum.RefereeTraining.Expert;
import static BusinessLayer.Enum.TeamState.active;
import static BusinessLayer.Enum.UserStatus.NotActive;
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
    Referee r2 = new Referee("refe2@gmail.com","456fdj6", 123758966,"rafi","hamud","refe2@gmail.com",Expert );

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
       assertTrue( r.getStatus().compareTo(NotActive) ==0);
    }
     @Test
    public void test_Delete_user2()throws Exception{
        long tommrorw = System.currentTimeMillis() +210000;
        Game g = new Game(seas,team,team2);
        g.setGameDate(tommrorw);
        g.setMainReferee(r2);
        r2.addGame(Main,g);
        assertFalse( r2.deleteUser());

        }
    }






