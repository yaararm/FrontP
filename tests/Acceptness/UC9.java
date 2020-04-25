package Acceptness;

import domain.Controllers.AssociationRepresentativeController;
import domain.Controllers.SystemController;
import domain.Enums.RefereeTraining;
import domain.Impl.League;
import domain.Impl.Season;
import domain.SeasonPolicies.AssignPolicy1;
import domain.SeasonPolicies.ScoreComputingPolicy2;
import domain.Users.AssociationRepresentative;
import domain.Users.Referee;
import org.junit.*;

import static org.junit.Assert.*;

public class UC9 {
    //Class Fields
    static AssociationRepresentativeController ac;
    static AssociationRepresentative assRep;

    @Before
    public void setUp() {
        ac = new AssociationRepresentativeController();
        assRep = new AssociationRepresentative("rep1", "1234", "avi", "cohen", "avi@football.com");
    }


    //region Test Use Case 9.1
    //define new league
    @Test
    public void Test_defineNewLeague() {
        try {
            ac.defineNewLeague(assRep, "league1", RefereeTraining.Expert);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(SystemController.leagueNameLeagues.containsKey("league1"));
    }

    //define new league that is already exist
    @Test(expected = Exception.class)
    public void Test_defineNewLeagueAlreadyExist() throws Exception {
        ac.defineNewLeague(assRep, "league2", RefereeTraining.Expert);
        assertTrue(SystemController.leagueNameLeagues.containsKey("league2"));

        ac.defineNewLeague(assRep, "league2", RefereeTraining.Expert);
    }
    //endregion

    //region Test Use Case 9.2
    //add Season To League
    @Test
    public void Test_addSeasonToLeague() {
        League l = SystemController.leagueNameLeagues.get("league1");
        try {
            ac.addSeasonToLeague(assRep, l, 2001, 1234567890);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(l.getLeaguesSeasons().containsKey(2001));
    }

    //add Season To League
    @Test(expected = Exception.class)
    public void Test_addSeasonToLeagueAlreadyExist() throws Exception {
        League l = SystemController.leagueNameLeagues.get("league1");
        ac.addSeasonToLeague(assRep, l, 2002, 1235656890);
        assertTrue(l.getLeaguesSeasons().containsKey(2002));
        ac.addSeasonToLeague(assRep, l, 2002, 1235656890);


    }

    //add Season To League
    @Test(expected = Exception.class)
    public void Test_addSeasonToLeagueInvalidYear() throws Exception {
        League l = SystemController.leagueNameLeagues.get("league1");
        ac.addSeasonToLeague(assRep, l, 1900, 1235656890);
    }
    //endregion

    //region Test Use Case 9.3.1
    @Test
    public void Test_appointReferee() {
        try {
            ac.appointReferee(assRep, 123456789, "hashofet", "benzona", "benz@ref.com", RefereeTraining.Begginer);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertEquals("hashofet", SystemController.userNameUser.get("benz@ref.com").getFirstName());
    }

    @Test(expected = Exception.class)
    public void Test_appointRefereeEmailAlreadyExist() throws Exception {
        ac.appointReferee(assRep, 111111111, "shofet", "one", "shofet@ref.com", RefereeTraining.Begginer);
        //new ref with same email
        ac.appointReferee(assRep, 222222222, "amit", "levi", "shofet@ref.com", RefereeTraining.Begginer);

    }

    @Test(expected = Exception.class)
    public void Test_appointRefereeIDAlreadyExist() throws Exception {
        ac.appointReferee(assRep, 555555555, "shofet", "one", "2shofet@ref.com", RefereeTraining.Begginer);
        //new ref with same email
        ac.appointReferee(assRep, 555555555, "amit", "levi", "1shofet@ref.com", RefereeTraining.Begginer);

    }

    @Test(expected = Exception.class)
    public void Test_appointRefereeInvalidID() throws Exception {
        ac.appointReferee(assRep, 1, "shofet", "one", "2shofet@ref.com", RefereeTraining.Begginer);
    }

    @Test(expected = Exception.class)
    public void Test_appointRefereeInvalidEmail() throws Exception {
        ac.appointReferee(assRep, 333333333, "shofet", "two", "shofetref.com", RefereeTraining.Begginer);
    }
    //endregion

    //region Test Use Case 9.3.2
    @Test
    public void Test_removeReferee() {
        try {
            ac.appointReferee(assRep, 444444444, "shofet", "four", "four@ref.com", RefereeTraining.Begginer);
            Referee r = (Referee) SystemController.userNameUser.get("four@ref.com");
            assertTrue(ac.removeReferee(assRep, r));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test(expected = Exception.class)
    public void Test_removeRefereeNotExist() throws Exception {
        Referee referee = new Referee("aaabbb","123123123",123123123,"sss","fff","sss@sss.com",RefereeTraining.Medium);
        ac.removeReferee(assRep,referee);
    }
    //endregion

    //region Test Use Case 9.4 A
    @Test
    public void Test_getAllRefereeThatCanBeForLeague() {
        try {
            ac.appointReferee(assRep, 999999999, "shofet", "nine", "nine@ref.com", RefereeTraining.Expert);
            ac.appointReferee(assRep, 888888888, "shofet", "eight", "eight@ref.com", RefereeTraining.Expert);
            ac.appointReferee(assRep, 777777777, "shofet", "seven", "seven@ref.com", RefereeTraining.Expert);
            ac.appointReferee(assRep, 666666666, "shofet", "six", "six@ref.com", RefereeTraining.Medium);

            ac.defineNewLeague(assRep, "leagueExpert", RefereeTraining.Expert);
            ac.defineNewLeague(assRep, "leagueMedium", RefereeTraining.Medium);

            assertEquals(3, ac.getAllRefereeThatCanBeForLeague(SystemController.leagueNameLeagues.get("leagueExpert")).size());
            assertEquals(4, ac.getAllRefereeThatCanBeForLeague(SystemController.leagueNameLeagues.get("leagueMedium")).size());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    //endregion

    //region Test Use Case 9.4 B
    @Test
    public void Test_setRefereeToSeason() {
        try {
            //define league
            ac.defineNewLeague(assRep, "league3", RefereeTraining.Begginer);
            League l = SystemController.leagueNameLeagues.get("league3");

            //define season
            ac.addSeasonToLeague(assRep, l, 2001, 1234567890);
            Season s = l.getLeaguesSeasons().get(2001);

            //add referee to system
            ac.appointReferee(assRep, 524323999, "shofet", "aaa", "aaa777@ref.com", RefereeTraining.Begginer);
            Referee r = (Referee) SystemController.userNameUser.get("aaa777@ref.com");

            //add referee to season
            ac.setRefereeToSeason(assRep, s, r);


            assertTrue(s.getReferees().get(RefereeTraining.Begginer).contains(r));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    //endregion

    //region Test Use Case 9.5
    @Test
    public void Test_setScoreComputingPolicy() {
        try {
            //define league
            ac.defineNewLeague(assRep, "league4", RefereeTraining.Begginer);
            League l = SystemController.leagueNameLeagues.get("league4");

            //define season
            ac.addSeasonToLeague(assRep, l, 2020, System.currentTimeMillis() * 2);
            Season s = l.getLeaguesSeasons().get(2020);

            //set policy
            ac.setScoreComputingPolicy(assRep, s, new ScoreComputingPolicy2());

            assertTrue(s.getScorePolicy() instanceof ScoreComputingPolicy2);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = Exception.class)
    public void Test_setScoreComputingPolicyAlreadyStarted() throws Exception {
        //define league
        ac.defineNewLeague(assRep, "league5", RefereeTraining.Begginer);
        League l = SystemController.leagueNameLeagues.get("league5");

        //define season
        ac.addSeasonToLeague(assRep, l, 2020, System.currentTimeMillis());
        Season s = l.getLeaguesSeasons().get(2020);

        //set policy
        ac.setScoreComputingPolicy(assRep, s, new ScoreComputingPolicy2());

        assertTrue(s.getScorePolicy() instanceof ScoreComputingPolicy2);

    }
    //endregion

    //region Test Use Case 9.6
    @Test
    public void Test_setAssignPolicy() {
        try {
            //define league
            ac.defineNewLeague(assRep, "league6", RefereeTraining.Begginer);
            League l = SystemController.leagueNameLeagues.get("league6");

            //define season
            ac.addSeasonToLeague(assRep, l, 2020, System.currentTimeMillis() * 2);
            Season s = l.getLeaguesSeasons().get(2020);

            //set policy
            ac.setAssignPolicy(assRep, s, new AssignPolicy1());

            assertTrue(s.getAssignPolicy() instanceof AssignPolicy1);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = Exception.class)
    public void Test_setAssignPolicyAlreadyStarted() throws Exception {
        //define league
        ac.defineNewLeague(assRep, "league7", RefereeTraining.Begginer);
        League l = SystemController.leagueNameLeagues.get("league7");

        //define season
        ac.addSeasonToLeague(assRep, l, 2020, System.currentTimeMillis());
        Season s = l.getLeaguesSeasons().get(2020);

        //set policy
        ac.setAssignPolicy(assRep, s, new AssignPolicy1());

        assertTrue(s.getAssignPolicy() instanceof AssignPolicy1);

    }
    //endregion
}

//setScoreComputingPolicy 9.5
//setAssignPolicy 9.6