package integration;

import domain.DB.SystemController;
import domain.BusinessLayer.Enum.RefereeTraining;
import domain.BusinessLayer.Football.League;
import domain.BusinessLayer.Football.Season;
import domain.BusinessLayer.Football.Team;
import domain.BusinessLayer.SeasonPolicies.AssignPolicy1;
import domain.BusinessLayer.SeasonPolicies.ScoreComputingPolicy1;
import domain.ServiceLayer.Controllers.SystemMangerController;
import domain.ServiceLayer.Controllers.TeamOwnerController;
import domain.BusinessLayer.Users.AssociationRepresentative;
import domain.BusinessLayer.Users.Owner;
import domain.BusinessLayer.Users.Referee;
import org.junit.*;
import domain.ServiceLayer.Controllers.AssociationRepresentativeController;

import java.util.HashSet;

import static org.junit.Assert.*;

public class AssociationRepresentativeControllerTest {
    static AssociationRepresentativeController ac;
    static AssociationRepresentative assRep;
    static SystemMangerController smc;
    static TeamOwnerController toc;

    @BeforeClass
    public static void setUp() {
        ac = new AssociationRepresentativeController();
        assRep = new AssociationRepresentative("rep1", "1234", "avi", "cohen", "avi@football.com");
        smc = new SystemMangerController();
    }
    @AfterClass
    public static void clean() throws Exception {
        ac.removeReferee(assRep,(Referee) SystemController.userNameUser.get("benz1@ref.com"));
        ac.removeReferee(assRep,(Referee) SystemController.userNameUser.get("benz2@ref.com"));
        ac.removeReferee(assRep,(Referee) SystemController.userNameUser.get("benz3@ref.com"));

    }


    @Test
    public void AssociationRepresentativeIntegrationTest() throws Exception {
        //define new league
        ac.defineNewLeague(assRep, "champions", RefereeTraining.Expert);
        assertTrue(SystemController.leagueNameLeagues.containsKey("champions"));
        League l = SystemController.leagueNameLeagues.get("champions");

        //add season to league
        ac.addSeasonToLeague(assRep, l, 2001, System.currentTimeMillis()*2);
        Season s = l.getLeaguesSeasons().get(2001);
        assertTrue(l.getLeaguesSeasons().containsKey(2001));

        //appoint referees
        ac.appointReferee(assRep, 111111111, "hashofet1", "benzona", "benz1@ref.com", RefereeTraining.Expert);
        ac.appointReferee(assRep, 222222222, "hashofet2", "benzona", "benz2@ref.com", RefereeTraining.Expert);
        ac.appointReferee(assRep, 333333333, "hashofet3", "benzona", "benz3@ref.com", RefereeTraining.Expert);
        Referee r1 = (Referee) SystemController.userNameUser.get("benz1@ref.com");
        Referee r2 = (Referee) SystemController.userNameUser.get("benz2@ref.com");
        Referee r3 = (Referee) SystemController.userNameUser.get("benz3@ref.com");
        assertEquals("hashofet1", r1.getFirstName());
        assertEquals("hashofet2", r2.getFirstName());
        assertEquals("hashofet3", r3.getFirstName());

        //add referee to season
        ac.setRefereeToSeason(assRep, s, r1);
        ac.setRefereeToSeason(assRep, s, r2);
        ac.setRefereeToSeason(assRep, s, r3);
        assertTrue(s.getReferees().get(RefereeTraining.Expert).contains(r1));
        assertTrue(s.getReferees().get(RefereeTraining.Expert).contains(r2));
        assertTrue(s.getReferees().get(RefereeTraining.Expert).contains(r3));

        //set policies
        ac.setScoreComputingPolicy(assRep, s, new ScoreComputingPolicy1());
        assertTrue(s.getScorePolicy() instanceof ScoreComputingPolicy1);
        ac.setAssignPolicy(assRep, s, new AssignPolicy1());
        assertTrue(s.getAssignPolicy() instanceof AssignPolicy1);

        //add owners
        Owner o1 = smc.signUpNewOwner(null,"owner1","last","owner1@gmail.com");
        Owner o2 = smc.signUpNewOwner(null,"owner2","last","owner2@gmail.com");
        Owner o3 = smc.signUpNewOwner(null,"owner3","last","owner3@gmail.com");
        Owner o4 = smc.signUpNewOwner(null,"owner4","last","owner4@gmail.com");
        assertTrue(SystemController.userNameUser.containsKey("owner1@gmail.com"));
        assertTrue(SystemController.userNameUser.containsKey("owner2@gmail.com"));
        assertTrue(SystemController.userNameUser.containsKey("owner3@gmail.com"));
        assertTrue(SystemController.userNameUser.containsKey("owner4@gmail.com"));

        //add teams
        Team t1 = TeamOwnerController.addNewTeamToSystem(o1,"team1");
        Team t2 = TeamOwnerController.addNewTeamToSystem(o2,"team2");
        Team t3 = TeamOwnerController.addNewTeamToSystem(o3,"team3");
        Team t4 = TeamOwnerController.addNewTeamToSystem(o4,"team4");
        assertTrue(SystemController.systemTeams.contains(t1));
        assertTrue(SystemController.systemTeams.contains(t2));
        assertTrue(SystemController.systemTeams.contains(t3));
        assertTrue(SystemController.systemTeams.contains(t4));

        //add teams to season
        HashSet<Team> teams = new HashSet<>();
        teams.add(t1);
        teams.add(t2);
        teams.add(t3);
        teams.add(t4);
        ac.setSeasonsTeams(assRep,s,teams);
        assertTrue(s.getSeasonsTeams().contains(t1));
        assertTrue(s.getSeasonsTeams().contains(t2));
        assertTrue(s.getSeasonsTeams().contains(t3));
        assertTrue(s.getSeasonsTeams().contains(t4));

        //assign games
        ac.assignGames(assRep,s);
        assertEquals(3, s.getGames().size());
        assertEquals(2, s.getGames().get(1).size());

        try {
            ac.setSeasonsTeams(assRep,s,teams);
            fail();
        } catch (Exception ignored) {

        }
        try {
            ac.assignGames(assRep,s);
            fail();
        } catch (Exception ignored) {

        }
    }
}
