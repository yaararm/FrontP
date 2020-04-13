package Acceptness;

import domain.Controllers.AssociationRepresentativeController;
import domain.Controllers.SystemController;
import domain.Enums.RefereeTraining;
import domain.Impl.League;
import domain.Users.AssociationRepresentative;
import domain.Users.Referee;
import org.junit.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;;

import static org.junit.Assert.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UC9 {
    //Class Fields
    static AssociationRepresentativeController ac;
    static AssociationRepresentative assRep;

    @BeforeClass
    public static void setUp() {
        ac = new AssociationRepresentativeController();
        assRep = new AssociationRepresentative("rep1", "1234", "avi", "cohen", "avi@football.com");
    }

    //region Test Use Case 9.1
    //define new league
    @Test
    @Order(1)
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
    @Order(2)
    public void Test_defineNewLeagueAlreadyExist() throws Exception {
        ac.defineNewLeague(assRep, "league2", RefereeTraining.Expert);
        assertTrue(SystemController.leagueNameLeagues.containsKey("league2"));

        ac.defineNewLeague(assRep, "league2", RefereeTraining.Expert);
    }
    //endregion

    //region Test Use Case 9.2
    //add Season To League
    @Test
    @Order(3)
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
    @Order(4)
    public void Test_addSeasonToLeagueAlreadyExist() throws Exception {
        League l = SystemController.leagueNameLeagues.get("league1");
        ac.addSeasonToLeague(assRep, l, 2002, 1235656890);
        assertTrue(l.getLeaguesSeasons().containsKey(2002));
        ac.addSeasonToLeague(assRep, l, 2002, 1235656890);


    }

    //endregion

    //region Test Use Case 9.3.1
    @Test
    @Order(5)
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
    @Order(6)
    public void Test_appointRefereeEmailAlreadyExist() throws Exception {
        ac.appointReferee(assRep, 111111111, "shofet", "one", "shofet@ref.com", RefereeTraining.Begginer);
        //new ref with same email
        ac.appointReferee(assRep, 222222222, "amit", "levi", "shofet@ref.com", RefereeTraining.Begginer);

    }

    @Test(expected = Exception.class)
    @Order(6)
    public void Test_appointRefereeIDAlreadyExist() throws Exception {
        ac.appointReferee(assRep, 555555555, "shofet", "one", "2shofet@ref.com", RefereeTraining.Begginer);
        //new ref with same email
        ac.appointReferee(assRep, 555555555, "amit", "levi", "1shofet@ref.com", RefereeTraining.Begginer);

    }

    @Test(expected = Exception.class)
    @Order(6)
    public void Test_appointRefereeInvalidEmail() throws Exception {
        ac.appointReferee(assRep, 333333333, "shofet", "two", "shofetref.com", RefereeTraining.Begginer);

    }
    //endregion

    //region Test Use Case 9.3.2
    @Test
    @Order(5)
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
    //endregion

    //region Test Use Case 9.4 A
    @Test
    @Order(5)
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

}

//setRefereeToLeague 9.4 B
