package integration;

import domain.ServiceLayer.Controllers.AssociationRepresentativeController;
import domain.DB.SystemController;
import domain.BusinessLayer.Enum.RefereeTraining;
import domain.BusinessLayer.Football.League;
import domain.BusinessLayer.Users.AssociationRepresentative;
import domain.BusinessLayer.Users.Fan;
import domain.BusinessLayer.Users.Guest;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SystemControllerTest {
    // serach
   SystemController sc = new SystemController();

    @Test
    public void test_serch_season() throws Exception{
    AssociationRepresentative assRep = new AssociationRepresentative("fdgdfds", "1234", "avi", "cohen", "avilevi@football.com");
   // League l = new League("haal",assRep, RefereeTraining.Expert);

   // Season season = new Season(2019, System.currentTimeMillis() +10000);
   // l.addSeason(2019,season);
   // season.setLeague(l);

    AssociationRepresentativeController ac = new AssociationRepresentativeController();
    ac.defineNewLeague(assRep,"haal", RefereeTraining.Expert);
    League l = SystemController.leagueNameLeagues.get("haal");
    ac.addSeasonToLeague(assRep,l,2019,  System.currentTimeMillis() +10000);
        Guest gs  = new Guest();
    assertNotNull(SystemController.search(gs, "2019" ));

}
    // history serach
    @Test
    public void test_no_history() throws Exception{
        Fan f = new Fan("nadav@gmail.com", "12345654", "shachar", "rumney", "nadav@gmail.com");
        long start =  System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(1);
        assertNull(sc.getSearchHistory(f, start, System.currentTimeMillis()));

    }
}
