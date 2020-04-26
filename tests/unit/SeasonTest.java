package unit;

import domain.BusinessLayer.Enum.RefereeTraining;
import domain.BusinessLayer.Enum.TeamState;
import domain.BusinessLayer.Football.Season;
import domain.BusinessLayer.Football.Team;
import domain.BusinessLayer.Users.Owner;
import domain.BusinessLayer.Users.Referee;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class SeasonTest {

    private static Season season;
    private static Referee referee1;
    private static Referee referee2;
    private static HashSet<Team> teams;
    private static Owner owner;
    private static Team team;

    @BeforeClass
    public static void before_class() throws ParseException {
        String myDate = "2021/10/29 18:10:45";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = sdf.parse(myDate);
        long millis = date.getTime();
        season = new Season(2021,millis);
        referee1 = new Referee("yoyo","123y",3,"yo","yo","yoyo@gmail.com", RefereeTraining.Medium);
        referee2 = new Referee("toto","123t",4,"to","to","toto@gmail.com",RefereeTraining.Medium);
        teams = new HashSet<>();
        owner = new Owner("google","123","goo","gle","google@gmail.com");
        team = new Team("inbarsTeamRool", TeamState.active,owner);
        teams.add(team);

    }

    @Test
    public void test_addReferee(){
        assertTrue(season.addReferee(referee1.getRefereeTraining(),referee1));
    }

    @Test
    public void test_removeReferee(){
        season.addReferee(referee1.getRefereeTraining(),referee1);
        assertFalse(season.removeReferee(referee2.getRefereeTraining(),referee2));
    }

    @Test
    public void test_setSeasonTeams_accept() throws Exception {
        season.setSeasonTeams(teams);
        assertTrue(season.getSeasonsTeams().contains(team));
    }

    @Test(expected = Exception.class)
    public void test_setSeasonTeams_nonaccept() throws Exception{
        Season oldSeason = new Season(2019,15012019);
        oldSeason.setSeasonTeams(teams);
    }
}
