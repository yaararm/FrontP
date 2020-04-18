package Acceptness;

import domain.Controllers.AssociationRepresentativeController;
import domain.Controllers.RefereeController;
import domain.Controllers.SystemController;
import domain.Controllers.Utils;
import domain.Enums.RefereeTraining;
import domain.Impl.League;
import domain.Impl.Season;
import domain.Users.AssociationRepresentative;
import domain.Users.Referee;
import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.*;


public class UC10 {
    //Class Fields
    static RefereeController rc;
    static Referee r;

    @BeforeClass
    public static void setUp() throws Exception {
        rc = new RefereeController();
        AssociationRepresentativeController ac = new AssociationRepresentativeController();
        AssociationRepresentative assRep = new AssociationRepresentative("rep1", "1234", "avi", "cohen", "avi@football.com");


        //define  a league
        ac.defineNewLeague(assRep, "league", RefereeTraining.Begginer);
        League l = SystemController.leagueNameLeagues.get("league");

        //define season
        ac.addSeasonToLeague(assRep, l, 2001, 1234567890);
        Season s = l.getLeaguesSeasons().get(2001);

        //add referee to system
        ac.appointReferee(assRep, 524323454, "shofet", "aaa", "aaa@ref.com", RefereeTraining.Begginer);
        r = (Referee) SystemController.userNameUser.get("aaa@ref.com");

        //add referee to season
        ac.setRefereeToSeason(assRep, s, r);
    }

    //region Test Use Case 10.1
    @Test
    public void Test_updateDetails() {
        try {
            HashMap<String,String> newVal =new HashMap();
            newVal.put("Password","123456");
            newVal.put("first name","moshik");
            rc.updateDetails(r,newVal);
            assertEquals(Utils.sha256("123456"),r.getPassword());
            assertEquals("moshik",r.getFirstName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = Exception.class)
    public void Test_updateDetailsShortPassword() throws Exception {
        HashMap<String,String> newVal =new HashMap();
        newVal.put("Password","1234");
        rc.updateDetails(r,newVal);
    }

    @Test(expected = Exception.class)
    public void Test_updateDetailsInvalidMail() throws Exception {
        HashMap<String,String> newVal =new HashMap();
        newVal.put("email","123456");
        rc.updateDetails(r,newVal);
    }

    //endregion

}
